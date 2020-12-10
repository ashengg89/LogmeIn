package com.raisin.demo.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.raisin.demo.constants.IWaitTimeConstants;

public class SeleniumUtil {

	private static final Logger LOG = LogManager.getLogger(SeleniumUtil.class);
	private SeleniumUtil() {
		
	}
	
	public static void typeValuesInATextBox(
            final WebElement elementToTypeValuesInto, final String valueToType,
            final WebDriver driver) {
        synchroniseUntilTheElementIsVisible(driver,
                elementToTypeValuesInto);
        if (!elementToTypeValuesInto.isEnabled()) {
            LOG.error("The element to click in not enabled: "
                    + elementToTypeValuesInto);
            Assert.fail("The element to click in not enabled: "
                    + elementToTypeValuesInto);
        }
        highlightElement(elementToTypeValuesInto, driver);
        elementToTypeValuesInto.sendKeys(valueToType);

    }
	
	public static void selectbyValuesFromDropbox(
            final WebElement dropdownElement, final String valuetoSelect,
            final WebDriver driver) {
        if (!dropdownElement.isEnabled()) {
            LOG.error("The element to click in not enabled: "
                    + dropdownElement);
            Assert.fail("The element to click in not enabled: "
                    + dropdownElement);
        }
        dropdownElement.click();
        highlightElement(dropdownElement, driver);
        Select dropdown= new Select(dropdownElement);
        dropdown.selectByValue(valuetoSelect);

    }
	
	public static void selectbyVisibleTextFromDropbox(
            final WebElement dropdownElement, final String valuetoSelect,
            final WebDriver driver) {
       // synchroniseUntilTheElementIsVisible(driver,
        	//	dropdownElement);
        if (!dropdownElement.isEnabled()) {
            LOG.error("The element to click in not enabled: "
                    + dropdownElement);
            Assert.fail("The element to click in not enabled: "
                    + dropdownElement);
        }
        dropdownElement.click();
        highlightElement(dropdownElement, driver);
        Select dropdown= new Select(dropdownElement);
        dropdown.selectByVisibleText(valuetoSelect);

    }
	
	public static void hovertoanElementandClickAddItem(
            final WebElement elementToHover, 
            final WebDriver driver) {
        synchroniseUntilTheElementIsVisible(driver,
        		elementToHover);
        if (!elementToHover.isEnabled()) {
            LOG.error("The element to click in not enabled: "
                    + elementToHover);
            Assert.fail("The element to click in not enabled: "
                    + elementToHover);
        }
        Actions builder = new Actions(driver);
        builder.moveToElement(elementToHover).build().perform();
        builder.click();

    }
	
	public static void clickUsingAssert(
            final WebElement elementToClick, 
            final WebDriver driver) {
       
        Actions builder = new Actions(driver);
        builder.moveToElement(elementToClick).click().build().perform();
        builder.click(elementToClick);

    }
	
	public static void clickElementAssert(final WebElement elementToClick,
            final WebDriver driver) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchroniseUntilTheElementIsDisplayedEnabledAndClickable(
                driver, elementToClick);
        
        if (!elementToClick.isEnabled()) {
            LOG.error("The element to click in not enabled: " + elementToClick);
            Assert.fail(
                    "The element to click in not enabled: " + elementToClick);
        }
        elementToClick.click();
        waitForSpecificTime(IWaitTimeConstants.GLOBAL_WAIT_TIME);
        
    }
	
	public static void selectCheckbox(final WebElement elementToClick,
            final WebDriver driver) {
		highlightElement(elementToClick, driver);
        if(!elementToClick.isSelected())
        	elementToClick.click();
        waitForSpecificTime(IWaitTimeConstants.GLOBAL_WAIT_TIME);
        
    }
	
	public static void clickElementNoAssert(final WebElement elementToClick) {
        elementToClick.click();
    }
	
	public static int getLinksCount(final List<WebElement> product_links) {
        List<WebElement> listOfLinks = product_links;
        return listOfLinks.size();
    }
	
	 public static void highlightElement(final WebElement element,
	            final WebDriver driver) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].style.border='2px dotted blue'",
	                element);
	    }
	
	
	 public static void synchroniseUntilTheElementIsVisible(final WebDriver driver, final WebElement element) {
	        WebDriverWait wait = new WebDriverWait(driver, IWaitTimeConstants.GLOBAL_SYNCHRONISATION_TIMEOUT);
	        wait.until(ExpectedConditions.visibilityOf(element));
	    }
	 
	 /**
	     * Synchronization method to check that an element is visible and enabled
	     * such that you can click it.
	     * 
	     * @param driver {@link WebDriver}
	     * @param webElement is the WebElement for the object to be displayed.
	     */
	    public static void synchroniseUntilTheElementIsDisplayedEnabledAndClickable(final WebDriver driver,
	            final WebElement webElement) {
	        WebDriverWait wait = new WebDriverWait(driver, IWaitTimeConstants.GLOBAL_SYNCHRONISATION_TIMEOUT);
	        wait.until(ExpectedConditions.elementToBeClickable(webElement));
	    }
	    
	    /**
	     * This method makes the program halt for the specified amount of time.
	     * 
	     * @param timeToWait
	     *            time in milliseconds you want to wait for
	     * 
	     * 
	     */

	    public static void waitForSpecificTime(final long timeToWait) {

	        try {
	            Thread.sleep(IWaitTimeConstants.GLOBAL_WAIT_TIME);
	        } catch (InterruptedException e) {
	            e.printStackTrace(System.err);
	        }

	    }
	    
	    public static ArrayList<String> getTableColumAndSort(WebDriver driver) {
	    	ArrayList<String> impressionList = new ArrayList<String>();
	    	List<WebElement> rows = driver.findElements(By.xpath(".//*[@class='s-values']/div[2]/div"));
	    	for(int i=2; i<=rows.size(); i++) {
	    		String imp = driver.findElement(By.xpath(".//*[@class='s-values']/div[2]/div[2]")).getText();
	    		if(!(imp.isEmpty())) {
	    			impressionList.add(imp);
	    		}
	    	}
	    	
	    	return impressionList;
	    }
	    
	    public static void waitForLoad(WebDriver driver) {
	        ExpectedCondition<Boolean> pageLoadCondition = new
	                ExpectedCondition<Boolean>() {
	                    public Boolean apply(WebDriver driver) {
	                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	                    }
	                };
	        WebDriverWait wait = new WebDriverWait(driver, 30);
	        wait.until(pageLoadCondition);
	    }

		public static int retriveProductCountText(WebElement product_list_txt) {
			String prodtext = product_list_txt.getText();
			int NoOfProduct = retriveIntegerFromText(prodtext);
			
			return NoOfProduct;
		}

		private static int retriveIntegerFromText(String prodtext) {
			String ash = prodtext.replace(" offers match your search", "");
			int text=  Integer.parseInt(ash);
			return text;
		}

		public static void loadUrl() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public static void verifyElementExists(WebElement element) {
			if(element.isDisplayed()) {
				Assert.assertEquals(element.isEnabled(), true);
			}
		}
	
}
