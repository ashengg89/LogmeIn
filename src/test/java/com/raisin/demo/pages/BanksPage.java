package com.raisin.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.raisin.demo.base.PageBase;
import com.raisin.demo.constants.IBanksConstants;
import com.raisin.demo.utils.SeleniumUtil;

public class BanksPage extends PageBase  {
	
	public BanksPage(WebDriver driver) {
		super(driver);
	}

	
	@FindBy(id = IBanksConstants.SORTBY_DDWN)
    public WebElement sortBy_ddwn;
	
	@FindBy(xpath = IBanksConstants.INVESTNOW_BTN)
    public WebElement investNow_btn;
	
	
	
	public void sortBankByCountryRating(String countryvalue) {
		SeleniumUtil.selectbyValuesFromDropbox(sortBy_ddwn, "country-name-asc", driver);
	}
	
	public void clickOnHighestCountryRatingBank() {
		WebElement bankElement = driver.findElement(By.xpath(".//*[@id='banks-archive-item-wrapper-cnt']/div[1]/div/div[1]/div/a"));
		bankElement.click();
	}
	
	public void clickInvestNow() {
		SeleniumUtil.clickElementAssert(investNow_btn, driver);
	}
	
	

}
