package com.raisin.demo.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.raisin.demo.constants.IGlobalConstants;
import com.raisin.demo.utils.DataUtil;
import com.raisin.demo.utils.ReportManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;





public class TestBase {

	private WebDriver driver = null;
	protected ExtentTest test;
	private HashMap<String, String> environmentData = null;
	private Properties envConfig = null;
	private static final Logger log = LogManager.getLogger(TestBase.class);
	public ExtentReports report =ReportManager.getInstance();
	public TestBase() {
		setEnvConfig(readConfigProperties(System.getProperty("user.dir") + IGlobalConstants.ENVIRONMENT_PROPERTIES_PATH));
        setEnvironmentData(DataUtil.loadEnvironmentData(getEnvConfig().getProperty("customer")));
        setBrowserType();
	}
	
	private void setBrowserType() {
		if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("Firefox")) {
            initializeFirefoxHeadless(false);
        } else if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("FirefoxHeadless")) {
            initializeFirefoxHeadless(true);
        } else if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + IGlobalConstants.IE_DRIVER_PATH);
            setDriver(new InternetExplorerDriver());
        } else if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("Chrome")) {
            initializeChromeHeadless(false);
        } else if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver",
                    System.getProperty("user.dir") + IGlobalConstants.EDGE_DRIVER_PATH);
            setDriver(new EdgeDriver());
        } else if (getEnvironmentData().get("testBrowser").equalsIgnoreCase("ChromeHeadless")) {
            initializeChromeHeadless(true);
        } else {
            log.error("Invalid browserName specified. Please check environment.properties config");
            throw new RuntimeException("Invalid browserName specified: "+getEnvironmentData().get("testBrowser"));
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
	}
	
	 /**
     * initialises the FirefoxDriver
     */
    private void initializeFirefoxHeadless(final boolean headless) {
        if (!headless) {
            System.setProperty("webdriver.gecko.driver",
                    System.getProperty("user.dir") + System.getProperty("user.dir") + IGlobalConstants.GECKO_DRIVER_PATH);
            setDriver(new FirefoxDriver());
        } else {
        	 System.setProperty("webdriver.gecko.driver",
                     System.getProperty("user.dir") + System.getProperty("user.dir") + IGlobalConstants.GECKO_DRIVER_PATH);
             setDriver(new FirefoxDriver()); 
        }
              
    }
    
    /**
     * initialises the ChromeDriver
     */
    private void initializeChromeHeadless(final boolean headless) {
        if (!headless) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + IGlobalConstants.CHROME_DRIVER_PATH);
            setDriver(new ChromeDriver());
        } else{
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + IGlobalConstants.CHROME_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("window-size=1200x600");
            setDriver(new ChromeDriver(options));
        } 
    }
    
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	 /**
     * private Getter to get the envConfig.
     * 
     * @return the envConfig
     */
    public Properties getEnvConfig() {
        return this.envConfig;
    }

    /**
     * private Setter to set the envConfig.
     * 
     * @param configuration
     *            the envConfig to set
     */
    public void setEnvConfig(final Properties envConfig) {
        this.envConfig = envConfig;
    }
	
	/** * Gets the environmentData field. * * @return the environmentData */

    public HashMap<String, String> getEnvironmentData() {
        return environmentData;
    }

    /**
     * * Sets the environmentData field. * * @param environmentData the
     * environmentData to set
     */

    public void setEnvironmentData(HashMap<String, String> environmentData) {
        this.environmentData = environmentData;
    }
	
	
    /**
     * Reads the properties file passed and returns it
     * 
     * @param fileName
     * @return Properties
     */
    private Properties readConfigProperties(final String fileName) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
    
	
	
	 /**
     * Helper method to wait until the page load finishes completely
     */
    protected void waitUntilPageLoadFinishesCompletely() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return the report
     */
    public ExtentReports getReport() {
        return report;
    }

    /**
     * @param report
     *            the report to set
     */
    public void setReport(ExtentReports report) {
        this.report = report;
    }
    
    /**
     * to report to both log and to the extent report simultaneously
     * 
     * @param log
     *            - {@link Logger}
     * @param test
     *            - {@link ExtentTest}
     * @param status
     *            - info, error, fail, warn, fatal
     * @param message
     *            - String description of the message to log
     */
    public void reportLog(final Logger log, final ExtentTest test, final String status, final Object message) {

        if (status.equalsIgnoreCase("info")) {
            log.info(message);
            test.log(LogStatus.INFO, message.toString());
        } else if (status.equalsIgnoreCase("error")) {
            log.info(message);
            test.log(LogStatus.ERROR, message.toString());
            takeScreenShot(test);
        } else if (status.equalsIgnoreCase("fail")) {
            log.info(message);
            test.log(LogStatus.FAIL, message.toString());
            takeScreenShot(test);
        } else if (status.equalsIgnoreCase("warn")) {
            log.info(message);
            test.log(LogStatus.WARNING, message.toString());
        } else if (status.equalsIgnoreCase("fatal")) {
            log.info(message);
            test.log(LogStatus.FATAL, message.toString());
            takeScreenShot(test);
        } else if (status.equalsIgnoreCase("pass")) {
            log.info(message);
            test.log(LogStatus.PASS, message.toString());
        } else {
            log.info("Invalid log input provided");
            test.log(LogStatus.INFO, "Invalid log input provided");
        }

    }
    
    /**
     * takeScreenShot - this method takes the screenshots and saves under
     * "screenshots" folder under the current directory of test.
     *
     * @param test is the ExtentTest object
     **/
    public void takeScreenShot(final ExtentTest test) {
        Date d = new Date();
        String fileName = d.toString().replace(" ", "_").replace(":", "_");

        String filePath = System.getProperty("user.dir") + IGlobalConstants.GLOBAL_SCREENSHOTS_FOLDER + fileName
                + ".jpg";
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
        } catch (Exception e) {
            log.error("Test failed due to IOException");
            log.error("IOException " + e.getMessage());
        }
        test.log(LogStatus.INFO, test.addScreenCapture(filePath));
    }

	
    
    

}
