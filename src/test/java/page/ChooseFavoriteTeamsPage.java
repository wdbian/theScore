package page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import base.BasePage;
import enumeration.League;
import enumeration.NFLTeam;
import enumeration.NHLTeam;
import extent_report.ExtntTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ChooseFavoriteTeamsPage extends BasePage {
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'Choose your favorite teams')]")
	@iOSXCUITFindBy(xpath="")
	private WebElement textLabel;
		
	@AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id='com.fivemobile.thescore:id/recyclerView']/android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath="")
	private List<WebElement> teamNamesDisplayedList;
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@resource-id='com.fivemobile.thescore:id/btn_primary' and ./*[@text='Continue']]")
	@iOSXCUITFindBy(xpath="")
	private WebElement continueButton;
	
	@AndroidFindBy(xpath="//android.widget.ImageButton[@content-desc='Navigate up']")
	@iOSXCUITFindBy(xpath="")
	private WebElement backButton;
	
	public ChooseFavoriteTeamsPage(AppiumDriver driver) {
		super(driver);
	}
	
	public void clickSpecificLeagueTab(League league) {
		waitForElementToDisplay(textLabel);
		String leaugueXpath = String.format("//*[@resource-id='com.fivemobile.thescore:id/tabLayout']/android.widget.LinearLayout/android.widget.LinearLayout[@content-desc='%s']", league.getShortDescription());
		getAppiumDriver().findElement(By.xpath(leaugueXpath)).click();
		log.info("select '" + league.getShortDescription() + "' in choose favorite teams page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "select '" + league.getShortDescription() + "' in choose favorite teams page");
	}
	
	public List<String> getAllTeamsNameOfSpecificLeague(League league) {
		// this method is used to add all enumerated teams' names into a list based on selected league
		List<String> teamNameList = new ArrayList<>();
		switch(league.getShortDescription()) {
			case "NFL":
				for (NFLTeam team : NFLTeam.values()) {
					teamNameList.add(team.getDescription());
				}
			break;
			case "NHL":
				for (NHLTeam team : NHLTeam.values()) {
					teamNameList.add(team.getDescription());
				}
			break;
		}
		return teamNameList;
	}
	
	public boolean isCorrectTeamIncluded(League league) {
		// this method is used to compare the enumerated teams' names contains displayed names
		List<String> teamNameList = new ArrayList<>();
		for (int i = 0; i < teamNamesDisplayedList.size(); i++) {
			teamNameList.add(teamNamesDisplayedList.get(i).getText().trim());
		}
		
		List<String> teams = getAllTeamsNameOfSpecificLeague(league);
		return teams.containsAll(teamNameList);
	}
	
	public void selectFavoriteTeam(League league, String teamName) {
		String selectedTeam = null;
		List<String> lst = getAllTeamsNameOfSpecificLeague(league);
		for (String name : lst) {
			if (name.contains(teamName)) {
				selectedTeam = name;
				break;
			}
		}	
		String teamXpath = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.fivemobile.thescore:id/recyclerView']/android.view.ViewGroup/android.widget.TextView[@text='%s']", selectedTeam);
		getAppiumDriver().findElement(By.xpath(teamXpath)).click();
		log.info("select '" + teamName + "' as favorite team in chhose favorite team page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "select '" + teamName + "' as favorite team in chhose favorite team page");
	}
	
	public boolean isSelectedFavoriteTeamDisplayed(League league, String teamName) {
		//this method is used to verify correct team is selected as favorite 
		//after calling selectFavoriteTeam() method
		
		String teamShortName = null;
		switch(league.getShortDescription()) {
			case "NFL":
				for (NFLTeam team : NFLTeam.values()) {
					if (team.getDescription().contains(teamName)) {
						teamShortName = team.getShortDescription();
						break;
					}
				}
			break;
			case "NHL":
				for (NHLTeam team : NHLTeam.values()) {
					if (team.getDescription().contains(teamName)) {
						teamShortName = team.getShortDescription();
						break;
					}
				}
			break;
		}
		String path = String.format("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.fivemobile.thescore:id/horizontal_recycler_view']/android.view.ViewGroup/android.widget.TextView[@text='%s']", teamShortName);
		return getAppiumDriver().findElement(By.xpath(path)).isDisplayed();
	}
	
	public void clickContinueButton() {
		continueButton.click();
		log.info("click Continue button in choose favorite teams page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click Continue button in choose favorite teams page");
	}
	
	public void clickBackButton() {
		backButton.click();
		log.info("click navigation back button in choose favorite teams page");
		ExtntTestManager.getExtentTestManagerInstance().getLatestExtentTestFromMap().log(Status.INFO, "click navigation back button in choose favorite teams page");
	}
}
