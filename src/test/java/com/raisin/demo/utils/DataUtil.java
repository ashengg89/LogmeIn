package com.raisin.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.raisin.demo.constants.IGlobalConstants;




public class DataUtil {
	private static final Logger LOG = LogManager.getLogger(DataUtil.class);
	
	 private static FileInputStream fileInputStream = null;
	public DataUtil() {
		// TODO Auto-generated constructor stub
	}
	
	 /**
     * loads the specific column data from EnvironmentData.xlsx according to the environment Name passed.
     * 
     * @param envName name of the environment you want to get
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> loadEnvironmentData(final String envName) {

        if (envName == null) {
            LOG.info("No environment is selected to run: ");
        }
        int colNum = getColumnNumberFromSpreadSheet("EnvironmentData.xlsx", "Data", envName);
        HashMap<String, String> map = loadRowHeadersValuesForAColumnFromSpreadSheet("EnvironmentData.xlsx", "Data",
                colNum);

        return map;
    }
    
    public static int getColumnNumberFromSpreadSheet(final String excelWorkBookName, final String workSheetName,
            final String columnHeader) {
        int colNumber = 0;
        final String datatablepath = System.getProperty("user.dir") + IGlobalConstants.GLOBAL_DATA_FOLDER
                + excelWorkBookName;
        ExcelReader datatable = new ExcelReader(datatablepath, workSheetName);

        if (columnHeader != null) {

            int colCount = datatable.getColumnCount();

            for (int colNum = 0; colNum < colCount; colNum++) {
                String cv = datatable.getCellValue(1, colNum);
                if (cv != null && columnHeader.equals(cv)) {
                    colNumber = colNum;
                    break;
                }

            }

            return colNumber;

        } else {
            LOG.warn("Null columnHeader when searching in file:" + datatablepath + " Sheet: " + workSheetName);
            throw new IllegalArgumentException(
                    "Null columnHeader when searching in file:" + datatablepath + " Sheet: " + workSheetName);
        }
    }
    
    public static HashMap<String, String> loadRowHeadersValuesForAColumnFromSpreadSheet(final String excelWorkBookName,
            final String workSheetName, final int columnNum) {

        ExcelReader datatable = new ExcelReader(
                System.getProperty("user.dir") + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName,
                workSheetName);

        String rowheader;
        String rowvalue;
        int rowCount = datatable.getRowCount();

        HashMap<String, String> map = new HashMap<String, String>();

        for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
            rowheader = datatable.getCellValue(rowNum, 0);
            rowvalue = datatable.getCellValue(rowNum, columnNum);
            map.put(rowheader, rowvalue);
        }

        return map;
    }
    
    /**
     * reads the properties file and returns the data as Properties.
     * 
     * @param envPropertyFilePath file path
     * @return Properties {@link Properties}
     */
    public static Properties getPropertyName(final String envPropertyFilePath) {
        File file = new File(envPropertyFilePath);
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
        }
        Properties prop = new Properties();
        try {
            prop.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return prop;

    }
    
    /**
     * reads the environment parameters from spreadsheet and returns the data as
     * String.
     * 
     * @param parameter reads the specific customer data
     * @return String
     */
    public static String getEnvParametersSpecificToCustomer(final String parameter) {
        Properties property = getPropertyName(
                System.getProperty("user.dir") + IGlobalConstants.ENVIRONMENT_PROPERTIES_PATH);
        HashMap<String, String> map = DataUtil.loadEnvironmentData(property.getProperty("customer"));
        Set<String> keySet = map.keySet();
        Iterator<String> keySetIterator = keySet.iterator();
        while (keySetIterator.hasNext()) {

            String key = keySetIterator.next();
            if (key.equals(parameter)) {
                return map.get(key);
            }
        }
        return "";

    }

    /**
     * Reads the data from spreadsheet and returns the data as a dynamic Object
     * array.
     *
     * @param excelWorkBookName
     *            - excel sheets name placed in the "Data" folder
     * @param workSheetName
     *            - work sheets name
     * @return Object[][]
     */
    public static Object[][] getDataFromSpreadSheet(final String excelWorkBookName, final String workSheetName) {
        ExcelReader datatable = new ExcelReader(
                System.getProperty("user.dir") + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName,
                workSheetName);

        int rowCount = datatable.getRowCount();
        int colCount = datatable.getColumnCount();
        Object[][] data = new Object[rowCount - 1][colCount];
        for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
            // loop all the available row values
            for (int colNum = 0; colNum < colCount; colNum++) {
                // while returning the data[][] you should not send the header
                // values
                data[rowNum - 2][colNum] = datatable.getCellValue(rowNum, colNum);
            }
        }

        return data;
    }
}
