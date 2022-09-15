package com.testing.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testing.base.TestBase;

public class ExtentManager extends TestBase {

	private static ExtentReports extent;

	public static ExtentReports createInstance(String fileName) {

		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setReportName(fileName);
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Automation Tester", "Sumit Bassan");
		extent.setSystemInfo("Organization", "Bebo");
		extent.setSystemInfo("Build No", "B-2A-1285");

		return extent;
	}

	public static String screenshotPath;

	public static void captureScreenshot() {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		String sc_name = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		screenshotPath = System.getProperty("user.dir") + "\\reports\\screenshots\\" + sc_name ;
		try {
			FileUtils.copyFile(scrFile, new File(screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String elementPath;
	public static void captureElementScreenshot(WebElement element) throws IOException {
		
		Date d = new Date();
		String eleSC_Name = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

		elementPath = System.getProperty("user.dir") + "\\reports\\element_screenshots\\" + eleSC_Name ;
		
		File screeshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screeshot, new File(elementPath));
	}

}
