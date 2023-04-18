package tc;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTestCase;
import enumeration.League;
import io.appium.java_client.AppiumDriver;
import page.AllowOrDenyLocationAccessPage;
import page.ChooseFavoriteLeaguePage;
import page.ChooseFavoriteTeamsPage;
import page.TailoredContentPage;
import page.WelcomePage;

public class SelectFavoriteLeagueAndTeam extends BaseTestCase {
	AppiumDriver driver;
	
	@Test
	public void addFavoriteLeagueAndTeam() {
		driver = getDriver();
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.clickGetStatedButton();
		
		TailoredContentPage tailorContentPage = new TailoredContentPage(driver);
		tailorContentPage.clickAllowLocationButtonIfTailoredContentPageDisplay();
		
		AllowOrDenyLocationAccessPage allowOrDenyAccessPage = new AllowOrDenyLocationAccessPage(driver);
		allowOrDenyAccessPage.clickAllowIfLocationAccessPageDisplay();
		
		ChooseFavoriteLeaguePage chooseFavoriteLeaguePage = new ChooseFavoriteLeaguePage(driver);
		chooseFavoriteLeaguePage.chooseFavoriteLeague(League.NHL);
		Assert.assertEquals(chooseFavoriteLeaguePage.isSelectedFavoriteLeagueDisplayed(League.NHL), true);
		chooseFavoriteLeaguePage.clickContinueButton();
		
		ChooseFavoriteTeamsPage chooseFavoriteTeamPage = new ChooseFavoriteTeamsPage(driver);
		Assert.assertEquals(chooseFavoriteTeamPage.isCorrectTeamIncluded(League.NHL), true);
		chooseFavoriteTeamPage.selectFavoriteTeam(League.NHL, "Boston Bruins");
		Assert.assertEquals(chooseFavoriteTeamPage.isSelectedFavoriteTeamDisplayed(League.NHL, "Boston Bruins"), true);
		chooseFavoriteTeamPage.clickBackButton();
		
		chooseFavoriteLeaguePage.refreshPage();
		Assert.assertEquals(chooseFavoriteLeaguePage.isSelectedFavoriteLeagueDisplayed(League.NHL), true);	
	}
}
