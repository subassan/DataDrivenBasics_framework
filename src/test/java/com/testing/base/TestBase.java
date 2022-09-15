package com.testing.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.testing.listeners.ExtentListeners;
import com.testing.utilities.DbManager;
import com.testing.utilities.ExcelReader;
import com.testing.utilities.ExtentManager;
import com.testing.utilities.MonitoringMail;
import com.testing.utilities.TestUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	/*
	 * Here we initialize the following Web Driver Properties Logs Extent Reports DB
	 * Excel Mail
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public static MonitoringMail mail = new MonitoringMail();
	
//	//creating instance for extent reports
//	static Date d = new Date();
//	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
//	public static ExtentReports rep = ExtentManager.createInstance(System.getProperty("user.dir")+"\\reports\\"+fileName);
//	public static ExtentTest test;

	@BeforeSuite
	public void setUp() throws IOException {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		log.info("OR file Loaded !!!");
		
		//loading config properties
		fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
		config.load(fis);
		log.info("Config file Loaded !!!");

		if (driver == null) {

			if (config.getProperty("browser").equals("firefox")) {
				
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.info("Firefox browser launched !!!");
			}

			else if (config.getProperty("browser").equals("chrome")) {
				
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.info("Chrome browser launched !!!");
			}

			else if (config.getProperty("browser").equals("edge")) {
				
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				log.info("Edge browser launched !!!");
			}

			driver.get(config.getProperty("testsiteURL"));
			log.info("Navigated to: " + config.getProperty("testsiteURL"));
			driver.manage().window().maximize();
			driver.manage().timeouts()
					.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
//			wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
		}
		
		try {
			DbManager.setMysqlDbConnection();
			log.info("DB Connection established !!!");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
		log.info("Test Execution completed !!!");

	}
	
	

	public static boolean isElementPresent(String locatorKey) {

		try {
			if (locatorKey.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locatorKey)));
			}
		} 
		catch (Throwable t) {

			log.info("Element not found : " + locatorKey);
			ExtentListeners.test.log(Status.INFO, "Element not found : " + locatorKey);
			return false;

		}

		log.info("Finding an Element : " + locatorKey);
		ExtentListeners.test.log(Status.INFO, "Finding an Element : " + locatorKey);
		return true;
	}

	
	
	public void click(String locator) {
		try {

			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).click();
			}
			else if(locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
			log.info("Clicking on an Element : "+locator);
			ExtentListeners.test.log(Status.INFO, "Clicking on : " + locator);
			
		}
		catch(Throwable t) {
			
			log.error("Error while Clicking on an Element: "+locator+" error message : "+t.getMessage());
			ExtentListeners.test.log(Status.FAIL, "Error while Clicking on an Element: "+locator+" error message : "+t.getMessage());
			Assert.fail(t.getMessage());
		}
	}
	
	

	public void type(String locator, String value) {

		try {
			
			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
			}
			else if(locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			}
			else if(locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
			}
			log.info("Typing in an Element : " + locator + " entered value as " + value);
			ExtentListeners.test.log(Status.INFO, "Typing in : " + locator + " entered value as " + value);
			
		}
		catch(Throwable t) {
			log.info("Error while typing in an Element : " + locator + " error messager :  " + t.getMessage());
			ExtentListeners.test.log(Status.INFO, "Error while typing in an Element : " + locator + " error messager :  " + t.getMessage());
			Assert.fail(t.getMessage());
		}
	}
	
	
	static WebElement dropdown;
	public void select(String locator, String value) {
		
		try {
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}
		else if(locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		}
		else if(locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.info("Selecting an Element : " + locator + " selected the value as " + value);
		ExtentListeners.test.log(Status.INFO, "Selecting an Element : " + locator + " selected the value as " + value);
		}
		catch(Throwable t) {
			log.info("Error while selecting an Element : " + locator + " error messager :  " + t.getMessage());
			ExtentListeners.test.log(Status.INFO, "Error while selecting an Element : " + locator + " error messager :  " + t.getMessage());
			Assert.fail(t.getMessage());
		}
	}
	
	
	
	public static void verifyEquals(String actual, String expected) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
		}
		catch(Throwable t) {
			TestUtils.CaptureScreenshot();
			
			//this is for reportNG
			Reporter.log("<br>"+"Verfication failure : "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtils.screenshotPath + "><img src=" + TestUtils.screenshotPath
					+ " height=200 widht=200></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//this is for Extent Report
			ExtentListeners.test.log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
			ExtentListeners.test.fail("<b>" + "<font color=" + "red>" + "Screenshot of verifyEquals failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotPath)
					.build());
		}
		
	}

}
