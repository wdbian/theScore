package extent_report;

import java.io.File;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.TimeStampGeneration;

public class ExtntReporterManager {
	private static String extentReportFolderPath = System.getProperty("user.dir") + File.separator + "Report";
	private static final ExtntReporterManager extntReporterManagerInstance = new ExtntReporterManager();
	private ExtentHtmlReporter htmlReporter;
	
	public static ExtntReporterManager getExtntReporterManagerInstance() {
		return extntReporterManagerInstance;
	}
	
	public ExtentHtmlReporter createAndStartExtentReporter() {
		File reporterDir = new File(extentReportFolderPath);
		if (! reporterDir.exists()) {
			reporterDir.mkdir();
		}
		String reportName = String.format("Test_Execution_ExtentReport-%s.html", TimeStampGeneration.generateTimeStampString());
		String reportFullPath = extentReportFolderPath + File.separator + reportName;
		htmlReporter = new ExtentHtmlReporter(reportFullPath);
		htmlReporter.config().enableTimeline(true);
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("yyyy-MM-dd, hh:mm");
		htmlReporter.config().setDocumentTitle("Test Report");
		htmlReporter.start();
		System.out.println("Thread: '" + Thread.currentThread().getName() + "' create and start ExtentHTMLReporter instance '" + htmlReporter + "'");
		System.out.println("********************************************");
		return htmlReporter;
	}
	
	public ExtentHtmlReporter getExtentReporter() {
		return htmlReporter;
	}
	
	public void stopExtentReporter() {
		htmlReporter.stop();
	}
}
