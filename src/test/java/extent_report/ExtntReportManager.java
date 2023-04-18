package extent_report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtntReportManager {
	private static final ExtntReportManager extntReportManagerInstance = new ExtntReportManager();
	private ExtentReports extentReport;
	
	public static ExtntReportManager getExtntReportManagerInstance() {
		return extntReportManagerInstance;
	}
	
	public ExtentReports createExtentReport() {
		extentReport = new ExtentReports();
		ExtentHtmlReporter htmlReporter = ExtntReporterManager.getExtntReporterManagerInstance().createAndStartExtentReporter();
		extentReport.attachReporter(htmlReporter);
		extentReport.setSystemInfo("Project Name", "Test Project"); //shown in extent report dashboard
		extentReport.setSystemInfo("Environment", "Test Environment");
		extentReport.setSystemInfo("Operation System", System.getProperty("os.name"));
		System.out.println("Thread: '" + Thread.currentThread().getName() 
							+ "'\nCreate ExtentReport: '" + extentReport 
							+ "'\nAttaching ExtentHtmlReporter: '" + htmlReporter + "'");
		System.out.println("********************************************");
		return extentReport;
	}
	
	public ExtentReports getExtentReport() {
		System.out.println("Thread: '" + Thread.currentThread().getName() 
							+ "'\nGet ExtentReport: '" + extentReport + "'");
		System.out.println("********************************************");
		return extentReport;
	}
}
