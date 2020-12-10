package com.raisin.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.raisin.demo.base.PageBase;
import com.raisin.demo.constants.ILaunchPageConstants;
import com.raisin.demo.utils.SeleniumUtil;



public class LaunchPage extends PageBase{

    public LaunchPage(WebDriver driver) {
        super(driver); 
    }
    
    @FindBy(name = ILaunchPageConstants.COUNTRY_DDWN)
    public WebElement country_ddwn;
    
    @FindBy(xpath = ILaunchPageConstants.OK_BTN)
    public WebElement ok_btn;
    
    @FindBy(xpath = ILaunchPageConstants.OUR_OFFERS_LTXT)
    public WebElement ourOffers_ltxt;
    
    @FindBy(xpath = ILaunchPageConstants.BANKS_LTXT)
    public WebElement bank_ltxt;
    
    //Launch Page to load the application URL
    public void navigateToLoginPage(String urlToNavigate) {
        this.driver.get(urlToNavigate);
        SeleniumUtil.waitForLoad(driver);
        SeleniumUtil.loadUrl();
        selectCountryList();
    }
    
    public void selectCountryList() {
    	 if(country_ddwn.isDisplayed()) {
         	SeleniumUtil.selectbyValuesFromDropbox(country_ddwn, "other", driver);
         	SeleniumUtil.clickElementNoAssert(ok_btn);
         }
    }
    
    public void clickOurOffersLink() {
    	SeleniumUtil.clickElementAssert(ourOffers_ltxt, driver);
    }
    
    public void clickBankLink() {
    	SeleniumUtil.clickElementAssert(bank_ltxt, driver);
    }
    
    public void closeApplication() {
        driver.close();
    }
 

}

