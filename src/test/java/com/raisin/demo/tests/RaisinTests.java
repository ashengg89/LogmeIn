package com.raisin.demo.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.raisin.demo.utils.DataUtil;
import com.raisin.demo.base.TestBase;
import com.raisin.demo.pages.BanksPage;
import com.raisin.demo.pages.LaunchPage;
import com.raisin.demo.pages.OurOffersPage;

public class RaisinTests extends TestBase{
	private static final Logger log = LogManager.getLogger(RaisinTests.class);
	SoftAssert softAssert = new SoftAssert ();
	private OurOffersPage ourOffersPage;
	private LaunchPage launchPage;
	private BanksPage banksPage;
	
	@BeforeTest
    public void setUp() {
		ourOffersPage=PageFactory.initElements(getDriver(), OurOffersPage.class);
		launchPage = PageFactory.initElements(getDriver(), LaunchPage.class);
		banksPage = PageFactory.initElements(getDriver(), BanksPage.class);
		getDriver().navigate().refresh();
        waitUntilPageLoadFinishesCompletely();
		
	}
	
	@Test(testName = "N offers match your search", priority = 1)
	public void Test1() {
		test = getReport().startTest("Find the no of Product list and verify if it mataches with the products displayed");
		
		reportLog(log, test, "info", "Navigate to the URL");
		launchPage.navigateToLoginPage(getEnvironmentData().get("applicationURL"));
		
		reportLog(log, test, "info", "Click \"Our Offers\" link");
		launchPage.clickOurOffersLink();
		
		reportLog(log, test, "info", "Select the Easy Access checkbox");
		ourOffersPage.checkEasyAccessCheckbox();
		
		reportLog(log, test, "info", "Verify the no of products displayed matches with the List of Products displayed");
		ourOffersPage.verifyNoffersmatchyoursearch();
	}
	
	@Test(testName = "Register Product with High Interest Rate", priority = 2)
	public void Test2() {
		test = getReport().startTest("Register Product with High Interest Rate");
		
		reportLog(log, test, "info", "Navigate to the URL");
		launchPage.navigateToLoginPage(getEnvironmentData().get("applicationURL"));

		reportLog(log, test, "info", "Click Our Offers Link");
		launchPage.clickOurOffersLink();
		
		reportLog(log, test, "info", "Check Easy Access checkbox");
		ourOffersPage.checkEasyAccessCheckbox();
		
		reportLog(log, test, "info", "Click Register Button for High Interest product");
		ourOffersPage.clickRegisterForHighInterestRate();
	}
	
	@Test(testName = "Look for the bank with the highest S&P Country rating", priority = 2, dataProvider = "Test3")
	public void Test3(String countryValue) {
		test = getReport().startTest("Look for the bank with the highest S&P Country rating");
		
		reportLog(log, test, "info", "Navigate to the URL");
		launchPage.navigateToLoginPage(getEnvironmentData().get("applicationURL"));
		
		reportLog(log, test, "info", "Click Banks Link");
		launchPage.clickBankLink();
		
		reportLog(log, test, "info", "Sort the Banks using the S&P Country rating");
		banksPage.sortBankByCountryRating(countryValue);
		
		reportLog(log, test, "info", "Click the bank with the high S&P Country rating Link");
		banksPage.clickOnHighestCountryRatingBank();
		
		reportLog(log, test, "info", "Click Invest now button");
		banksPage.clickInvestNow();
		
		reportLog(log, test, "info", "Verify Register now Form");
		
		
	}
	
	
	@DataProvider(name = "Test3")
    public Object[][] getData() {
        return DataUtil.getDataFromSpreadSheet("TestDatas.xlsx", "Test3");

    }
	
	@AfterTest
    public void tearDown() {
        report.endTest(test);
        report.flush();
        launchPage.closeApplication();
      
    }

}
