package com.raisin.demo.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import com.raisin.demo.base.PageBase;
import com.raisin.demo.constants.IOurOffersConstants;
import com.raisin.demo.utils.SeleniumUtil;

public class OurOffersPage extends PageBase  {
	SoftAssert softAssert = new SoftAssert();
	
	public OurOffersPage(WebDriver driver) {
		super(driver); 
	}

	
	@FindBy(xpath = IOurOffersConstants.EASYACCESS_CBOX)
	public WebElement easyAccess_cbox;

	@FindBy(xpath = IOurOffersConstants.PRODUCT_LINK)
	public List<WebElement> product_links;


	@FindBy(xpath = IOurOffersConstants.PRODUCT_LIST_TEXT)
	public WebElement product_list_txt;

	@FindBy(xpath = IOurOffersConstants.SHOWMOREPRODUCTS_BTN)
	public WebElement showMoreProducts_btn;

	public void checkEasyAccessCheckbox() {
		SeleniumUtil.selectCheckbox(easyAccess_cbox, driver);
	}


	public void verifyNoffersmatchyoursearch() {
		if(showMoreProducts_btn.isDisplayed()) {
			SeleniumUtil.clickElementAssert(showMoreProducts_btn, driver);
		}
		int noOfProducts=SeleniumUtil.getLinksCount(product_links);
		int prodText = SeleniumUtil.retriveProductCountText(product_list_txt);
		softAssert.assertEquals(prodText, noOfProducts, "Verified and both are equal");

	}

	public String findOfferforHighInterestRate() {
		if(showMoreProducts_btn.isDisplayed()) {
			SeleniumUtil.clickElementAssert(showMoreProducts_btn, driver);
		}
		List<WebElement> listOfLinks = product_links;
		List<String> intertestRates = new ArrayList<String>();

		for(WebElement a: listOfLinks) {
			String rate= a.findElement(By.xpath(".//div[@class='prot-offer-header-interest-rate']/div[1]")).getText();
			intertestRates.add(rate);
		}

		Collections.sort(intertestRates);

		String highIntertestRate = intertestRates.get(listOfLinks.size()-1);
		return highIntertestRate;
	}
	public void clickRegisterForHighInterestRate() {
		List<WebElement> listOfLinks = product_links;
		for(WebElement a: listOfLinks) {
			String rate= a.findElement(By.xpath(".//div[@class='prot-offer-header-interest-rate']/div[1]")).getText();
			System.out.println(rate);
			if(rate.contentEquals(findOfferforHighInterestRate())) {
				a.findElement(By.xpath("//a[@class='product-cta ng-scope']")).click();
				break;
			}
		}

		SeleniumUtil.waitForSpecificTime(3000);	

	}


}
