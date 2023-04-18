package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import utils.PropertyFileUtil;
import utils.TimeStampGeneration;

public class BaseTestCase {
	private String appiumServerUrl = "http://127.0.0.1:4723/wd/hub";
	private AppiumDriver driver = null;
	private LoggerContext loggerContext;
	private DesiredCapabilities dc = new DesiredCapabilities();
	
	
	@BeforeSuite
	public void setupBeforeSuite(ITestContext testContext) {
		//configure log4j2
		String log4jPropertyFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "log4j2.properties";
		try {
			ConfigurationSource config = new ConfigurationSource(new FileInputStream(log4jPropertyFile));
			loggerContext = Configurator.initialize(null, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//load general.properties file
		PropertyFileUtil.getPropertyFileUtilInstance().loadPropertiesFromFile("general");
	}
	
	@AfterSuite
	public void tearDownAterSuite() {
		loggerContext.close();
	}
	
	@BeforeTest
	@Parameters({"platformType", "platformVersion", "deviceName"})
	public void setupBeforeTest(String platformType, String platformVersion, String deviceName) {
		if (platformType.contains("android")) {
			createAndroidDriver(platformVersion, deviceName);
		} else {
			createIOSDriver(platformVersion, deviceName);
		}
	}
	
	@AfterTest
	public void tearDownAfterTest() {
		driver.quit();
	}
	
	public void createAndroidDriver(String platformVersion, String deviceName) {
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		
		dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("android_app_package"));
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("android_app_activity"));
		
		try {
			URL url = new URL(appiumServerUrl);
			driver = new AndroidDriver(url, dc);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.valueOf(PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("driver_load_timeout"))));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.valueOf(PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("driver_implicit_wait_timeout"))));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}
	
	public void createIOSDriver(String platformVersion, String deviceName) {
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		
		dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("ios_app_bundleid"));
		dc.setCapability(IOSMobileCapabilityType.APP_NAME, PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("ios_app_name"));
		
		try {
			URL url = new URL(appiumServerUrl);
			driver = new IOSDriver(url, dc);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.valueOf(PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("driver_load_timeout"))));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.valueOf(PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("driver_implicit_wait_timeout"))));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public AppiumDriver getDriver() {
		return driver;
	}
	
	public String takeScreenshot(String screenshotName) {
		String screenshotFolderPath = System.getProperty("user.dir") + File.separator + "Screenshot";
		File screenshotDirectory = new File(screenshotFolderPath);
		if (! screenshotDirectory.exists()) {
			screenshotDirectory.mkdir();
		}
		String fileName = screenshotFolderPath + File.separator + screenshotName + "_" + TimeStampGeneration.generateTimeStampString() + "_" + Thread.currentThread().getId() + ".png";
		File source = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}
