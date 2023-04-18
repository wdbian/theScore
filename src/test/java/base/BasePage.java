package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import utils.PropertyFileUtil;
import utils.TimeStampGeneration;

public class BasePage {
	protected Logger log = (Logger) LogManager.getLogger(this.getClass());
	private AppiumDriver driver;
	private WebDriverWait wait;
	
	public BasePage(AppiumDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(Long.valueOf(PropertyFileUtil.getPropertyFileUtilInstance().getValueFromProperty("driver_explicit_wait_timeout"))));
		PageFactory.initElements(this.driver, this);
	}
	
	public AppiumDriver getAppiumDriver() {
		return this.driver;
	}
	
	public WebDriverWait getWebDriverWait() {
		return this.wait;
	}
	
	public void setDriver(AppiumDriver driver) {
		this.driver = driver;
	}
	
	public void setWebDriverWait(Duration waitSecond) {
		wait = new WebDriverWait(this.driver, waitSecond);
	}
	
	public void waitForElementToDisplay(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));	
	}	
	
	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public String takeScreenshot(String screenshotName) {
		String screenshotFolderPath = System.getProperty("user.dir") + File.separator + "Screenshot";
		File screenshotDirectory = new File(screenshotFolderPath);
		if (! screenshotDirectory.exists()) {
			screenshotDirectory.mkdir();
		}
		String fileName = screenshotFolderPath + File.separator + screenshotName + "_" + TimeStampGeneration.generateTimeStampString() + "_" + Thread.currentThread().getId() + ".png";
		File source = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
