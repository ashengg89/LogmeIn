package com.raisin.demo.utils;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import com.raisin.demo.constants.IGlobalConstants;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;



/**
 * ReportManager is to give an instance of ExtentReports. This is created as
 * Singleton such that each VM will have one instance of ExtentReport running
 * 

 *
 */
public final class ReportManager {

    /** Extent reports instance variable. */
    private static ExtentReports extent;

    // to make a singleton hide the constructor
    // to ensure no other instance of ReportManager exists in a machine
    private ReportManager() {

    }

    /**
     * getInstance method returns an instance of extent report.
     * 
     * @return {@link ExtentReports}
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            // dynamic report file creation
            Date d = new Date();
            String fileName = d.toString().replace(" ", "_").replace(":", "_");
            String reportFileNamePath = System.getProperty("user.dir") + IGlobalConstants.REPORT_FILE_PATH + fileName
                    + ".html";
            // 1.creating new instance of extent report
            extent = new ExtentReports(reportFileNamePath, true, DisplayOrder.NEWEST_FIRST);
            // 2. loading the config xml --> customize the html report
            extent.loadConfig(new File(System.getProperty("user.dir") + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                    + "extent-config.xml"));
            // 3.System info
            extent.addSystemInfo("Selenium Version", "3.16");
            extent.addSystemInfo("Environment", IGlobalConstants.ENVIRONMENT);
            Properties property = DataUtil.getPropertyName(System.getProperty("user.dir") + IGlobalConstants.ENVIRONMENT_PROPERTIES_PATH);
            String browserName = DataUtil.getEnvParametersSpecificToCustomer("testBrowser");
            String applicationURL = DataUtil.getEnvParametersSpecificToCustomer("applicationUrl");
            extent.addSystemInfo("Airline", property.getProperty("customer"));
            extent.addSystemInfo("Browser", browserName);
            extent.addSystemInfo("Application URL", applicationURL);
        }
        return extent;
    }
}
