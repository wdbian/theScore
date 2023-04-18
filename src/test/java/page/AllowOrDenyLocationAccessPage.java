package page;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.BasePage;
import extent_report.ExtntTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class AllowOrDenyLocationAccessPage extends BasePage {
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.android.packageinstaller:id/permission_message' and contains(@text, 'Allow theScore to access this device')]")
	@iOSXCUITFindBy(xpath="")
	private List<WebElement> textLabel;
	
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='com.android.packageinstaller:id/permission_allow_button']")
	@iOSXCUITFindBy(xpath="")
	private WebElement allowButton;
	
	public AllowOrDenyLocationAccessPage(AppiumDriver driver) {
		super(driver);
	}

	public void clickAllowIfLocationAccessPageDisplay() {
		if (textLabel.size() > 0) {
			allowButton.click();
			log.info("click Allow button in Location Access page");
			ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click Allow button in Location Access page");
		}
	}
}
