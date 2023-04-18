package listener;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseTestCase;
import extent_report.ExtntReportManager;
import extent_report.ExtntReporterManager;
import extent_report.ExtntTestManager;


public class ExtentReportListener extends BaseTestCase implements ITestListener, ISuiteListener {
ExtentReports extentReport;
	
	public void onStart(ISuite suite) {
		extentReport = ExtntReportManager.getExtntReportManagerInstance().createExtentReport();
		System.out.println("From Listener - onStart(ISuite)"
							+ "\n\tThread: '" + Thread.currentThread().getName()   
							+ "\n\tExtentReport: '" + extentReport + "' is created");
	}
	
	public void onFinish(ISuite suite) {
		extentReport.flush();
		ExtntReporterManager.getExtntReporterManagerInstance().stopExtentReporter();
		System.out.println("From Listener - onFinish(ISuite)"
							+ "\n\tThread: '" + Thread.currentThread().getName() 
							+ "'\n\tTest Suite: '" + suite.getXmlSuite().getName() 
							+ "\n\tExtentReport: '" + extentReport + "' is flushed");	
	}
	
	public void onStart(ITestContext testContext) {	

	}
	
	public void onFinish(ITestContext testContext) {

	}
	
	public void onTestStart(ITestResult result) {
		String suiteName = result.getTestContext().getSuite().getXmlSuite().getName();
		String testName = result.getTestContext().getCurrentXmlTest().getName();
		String testMethodName = result.getMethod().getConstructorOrMethod().getMethod().getName();
		
		ExtentTest currentTest = ExtntTestManager.getExtentTestManagerInstance().createExtentTest(extentReport, testMethodName);
		currentTest.log(Status.INFO, "Test Method: '" + testMethodName + "' is started");
		
		System.out.println("From Listener - onTestStart(ITestResult)"
							+ "\n\tThread: '" + Thread.currentThread().getName() 
							+ "'\n\tSuite: '" + suiteName 
							+ "'\n\tTest: '" + testName 
							+ "'\n\tTest Method: '" + testMethodName 
							+ "'\n\tExtentTest: '" + currentTest + "' is started for"
							+ "\n\tExtentReport: '" + currentTest.getExtent() + "'");
	}
	
	public void onTestSuccess(ITestResult result) {
		String suiteName = result.getTestContext().getSuite().getXmlSuite().getName();
		String testName = result.getTestContext().getCurrentXmlTest().getName();
		String testMethodName = result.getMethod().getConstructorOrMethod().getMethod().getName();
		String description = "Test Method: '" + testMethodName + "' is <b>Passed</b>";
		
		ExtentTest currentTest = ExtntTestManager.getExtentTestManagerInstance().getExtentTest(testMethodName);
		currentTest.log(Status.PASS, description);	
		
		System.out.println("From Listener - onTestSuccess(ITestResult)"
							+ "\n\tThread: '" + Thread.currentThread().getName() 
							+ "'\n\tSuite: '" + suiteName 
							+ "'\n\tTest: '" + testName 
							+ "'\n\tTest Method: '" + testMethodName 
							+ "'\n\tExtentTest: '" + currentTest + "' is Passed and removed"
							+ "\n\tFrom ExtentReport: '" + currentTest.getExtent() + "'");
		
		ExtntTestManager.getExtentTestManagerInstance().removeExtentTestFromExtentTestMap(testMethodName);		
	}
	
	public void onTestFailure(ITestResult result) {
		String suiteName = result.getTestContext().getSuite().getXmlSuite().getName();
		String testName = result.getTestContext().getCurrentXmlTest().getName();
		String testMethodName = result.getMethod().getConstructorOrMethod().getMethod().getName();
		String description = "Test Method: '" + testMethodName + "' is <b>Failed</b>";
		
		ExtentTest currentTest = ExtntTestManager.getExtentTestManagerInstance().getExtentTest(testMethodName);
		currentTest.log(Status.FAIL, description).fail(result.getThrowable());
		try {
			currentTest.addScreenCaptureFromPath(takeScreenshot(testMethodName));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		System.out.println("From Listener - onTestFailure(ITestResult)"
							+ "\n\tThread: '" + Thread.currentThread().getName() 
							+ "'\n\tSuite: '" + suiteName 
							+ "'\n\tTest: '" + testName 
							+ "'\n\tTest Method: '" + testMethodName 
							+ "'\n\tExtentTest: '" + currentTest + "' is Failed and removed"
							+ "\n\tFrom ExtentReport: '" + currentTest.getExtent() + "'");
		
		ExtntTestManager.getExtentTestManagerInstance().removeExtentTestFromExtentTestMap(testMethodName);	
	}
	
	public void onTestSkipped(ITestResult result) {
		String suiteName = result.getTestContext().getSuite().getXmlSuite().getName();
		String testName = result.getTestContext().getCurrentXmlTest().getName();
		String testMethodName = result.getMethod().getConstructorOrMethod().getMethod().getName();
		String description = "Test Method: '" + result.getMethod().getMethodName() + "' is <b>Skipped</b>";
		
		ExtentTest currentTest = ExtntTestManager.getExtentTestManagerInstance().getExtentTest(testMethodName);
		currentTest.log(Status.SKIP, description);	
		
		System.out.println("From Listener - onTestFailure(ITestResult)"
							+ "\n\tThread: '" + Thread.currentThread().getName() 
							+ "'\n\tSuite: '" + suiteName 
							+ "'\n\tTest: '" + testName 
							+ "'\n\tTest Method: '" + testMethodName 
							+ "'\n\tExtentTest: '" + currentTest + "' is Skipped and removed"
							+ "\n\tFrom ExtentReport: '" + currentTest.getExtent() + "'");
		
		ExtntTestManager.getExtentTestManagerInstance().removeExtentTestFromExtentTestMap(testMethodName);		
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		onTestSuccess(result);
	}
	
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}
	
}
