package com.raisin.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.raisin.demo.base.PageBase;
import com.raisin.demo.constants.IRegisterNowConstants;
import com.raisin.demo.utils.SeleniumUtil;


public class RegisterNowPage extends PageBase{

	public RegisterNowPage(WebDriver driver) {
		 super(driver); 
	}

	@FindBy(id = IRegisterNowConstants.EMAIL_TBOX)
	public WebElement email_tbox;

	@FindBy(xpath = IRegisterNowConstants.REGISTER_BTN)
	public WebElement register_btn;
	
	public void verifyElementsInregisterForm() {
		SeleniumUtil.verifyElementExists(email_tbox);
		SeleniumUtil.verifyElementExists(register_btn);
	}

}
