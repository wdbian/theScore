package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.BasePage;
import enumeration.League;
import extent_report.ExtntTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ChooseFavoriteLeaguePage extends BasePage {
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'Choose your favorite leagues')]")
	@iOSXCUITFindBy(xpath="")
	private WebElement textLabel;
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@resource-id='com.fivemobile.thescore:id/btn_primary' and ./*[@text='Continue']]")
	@iOSXCUITFindBy(xpath="")
	private WebElement continueButton;
	
	public ChooseFavoriteLeaguePage(AppiumDriver driver) {
		super(driver);
	}

	public void chooseFavoriteLeague(League league) {
		waitForElementToDisplay(textLabel);
		String leagueXpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.fivemobile.thescore:id/recyclerView']/android.view.ViewGroup/android.widget.TextView[@text='%s']", league.getDescription());
		getAppiumDriver().findElement(By.xpath(leagueXpath)).click();
		log.info("select '" + league.getDescription() + "' as favorite league in choose favorite league page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "select '" + league.getDescription() + "' as favorite league in choose favorite league page");
	}
	
	public boolean isSelectedFavoriteLeagueDisplayed(League league) {
		String leagueXpath = String.format("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/label' and @text='%s']", league.getShortDescription());
		return (getAppiumDriver().findElement(By.xpath(leagueXpath)).isDisplayed()) ? true : false;
	}
	
	public void clickContinueButton() {
		continueButton.click();
		log.info("click Continue button in choose favorite league page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click Continue button in choose favorite league page");
	}
}
