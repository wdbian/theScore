package page;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.BasePage;
import extent_report.ExtntTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class WelcomePage extends BasePage {
	 @AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_welcome']")
	 @iOSXCUITFindBy(xpath="")
	 private WebElement welcomeTextLabel;
	 
	 @AndroidFindBy(xpath="//android.view.ViewGroup[@resource-id='com.fivemobile.thescore:id/btn_primary']")
	 @iOSXCUITFindBy(xpath="")
	 private WebElement getStartedButton;
	 
	 @AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_sign_in']")
	 @iOSXCUITFindBy(xpath="")
	 private WebElement loginButton;
	
	public WelcomePage(AppiumDriver driver) {
		super(driver);
	}
	
	public void clickGetStatedButton() {
		waitForElementToDisplay(welcomeTextLabel);
		ExtntTestManager.getExtentTestManagerInstance().attachScreenshotToExtentTest(takeScreenshot("WelcomePage"));
		
		getStartedButton.click();
		log.info("click 'Get Started' button in welcome page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click 'Get Started' button in welcome page");
	}
	
	
}
