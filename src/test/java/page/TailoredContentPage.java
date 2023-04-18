package page;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.BasePage;
import extent_report.ExtntTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class TailoredContentPage extends BasePage {
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/location_title' and @text='Tailored Content']")
	@iOSXCUITFindBy(xpath="")
	private List<WebElement> tailoredContentTextLabel;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/btn_allow' and @text='Allow Location']")
	@iOSXCUITFindBy(xpath="")
	private WebElement allowLocationButton;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/btn_disallow']")
	@iOSXCUITFindBy(xpath="")
	private WebElement maybeLaterButton;
	
	public TailoredContentPage(AppiumDriver driver) {
		super(driver);
	}

	public void clickAllowLocationButtonIfTailoredContentPageDisplay() {
		if (tailoredContentTextLabel.size() > 0) {
			allowLocationButton.click();
			log.info("click 'Allow Location' button in tailored content page");
			ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click 'Allow Location' button in tailored content page");
		}
	}
	
	public void clickMaybeLaterButtonIfTailoredContentPageDisplay() {
		if (tailoredContentTextLabel.size() > 0) {
			maybeLaterButton.click();
			log.info("click 'Maybe Later' button in tailored content page");
			ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click 'Maybe Later' button in tailored content page");
		}
	}
}
