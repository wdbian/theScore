package extent_report;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtntTestManager {
	private static final ExtntTestManager extTestManagerInstance = new ExtntTestManager();
	private static ThreadLocal<ExtentTest> threadLocalExtentTest = new ThreadLocal<ExtentTest>();
	private static Map<String, ThreadLocal<ExtentTest>> extTestMap = new ConcurrentHashMap<String, ThreadLocal<ExtentTest>>();
	
	public static ExtntTestManager getExtentTestManagerInstance() {
		return extTestManagerInstance;
	}
	
	public ExtentTest createExtentTest(ExtentReports extentReport, String extentTestName) {
		ExtentTest test = extentReport.createTest(extentTestName);
		threadLocalExtentTest.set(test);
		extTestMap.put(extentTestName, threadLocalExtentTest);
		System.out.println("Thread: '" + Thread.currentThread().getName() + "' create ExtentTest: '" + test + "'");
		return test;
	}
	
	public ExtentTest createSubExtentTest(String extentTestName, String subExtentTestName) {
		ExtentTest test = extTestMap.get(extentTestName).get().createNode(subExtentTestName);
		threadLocalExtentTest.set(test);
		extTestMap.put(subExtentTestName, threadLocalExtentTest);
		System.out.println("Thread: '" + Thread.currentThread().getName() + "' create SubExtentTest: '" + test + "'");
		return test;
	}
	
	public void removeExtentTestFromExtentTestMap(String extentTestName) {
		Iterator<Entry<String, ThreadLocal<ExtentTest>>> ite = extTestMap.entrySet().iterator();
		while (ite.hasNext()) {
			Entry<String, ThreadLocal<ExtentTest>> en = ite.next();
			if (en.getKey().contains(extentTestName)) {
				extTestMap.remove(en.getKey());
				break;
			}
		}
	}
	
	public ExtentTest getExtentTest(String extentTestName) {
		ExtentTest test = null;
		Iterator<Entry<String, ThreadLocal<ExtentTest>>> ite = extTestMap.entrySet().iterator();
		while (ite.hasNext()) {
			Entry<String, ThreadLocal<ExtentTest>> en = ite.next();
			if (en.getKey().contains(extentTestName)) {
				test = en.getValue().get();
				break;
			}
		}
		return test;
	}
	
	public ExtentTest getLatestExtentTestFromMap() {
		ExtentTest test = null;
		Iterator<Entry<String, ThreadLocal<ExtentTest>>> ite = extTestMap.entrySet().iterator();
		while (ite.hasNext()) {
			Entry<String, ThreadLocal<ExtentTest>> en = ite.next();
			if ((! en.getKey().contains("Suite")) && (! en.getKey().contains("Test"))) {
				/**
				 * make sure all @Test method name not contains either "Suite" or "Test"
				 */
				test = en.getValue().get();
				break;
			} else if (en.getKey().contains("Test")) {
				test = en.getValue().get();
			} else if (en.getKey().contains("Suite") && (! ite.hasNext())) {
				test = en.getValue().get();
			}
		}
		return test;
	}
	
	public void attachScreenshotToExtentTest(String screenShotFilePath) {
		try {
			getLatestExtentTestFromMap().addScreenCaptureFromPath(screenShotFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
