package com.raisin.demo.base;

import org.openqa.selenium.WebDriver;

public class PageBase {

	public  WebDriver driver;
	
	public PageBase(WebDriver driver) {
		this.driver = driver;
	}
	
	public void closeApplication() {
        driver.close();
    }

}
