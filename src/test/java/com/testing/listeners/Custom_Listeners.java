package com.testing.listeners;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.testing.base.TestBase;
import com.testing.utilities.TestUtils;

public class Custom_Listeners extends TestBase implements ITestListener {

	public void onTestFailure(ITestResult arg0) {

		// setting property for reportNG file
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		// capturing screenshot
		try {
			TestUtils.CaptureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//this is for ReportNG
		Reporter.log("Capturing Screenshot !!! ");
		Reporter.log("<a target=\"_blank\" href=" + TestUtils.screenshotPath + ">Screenshot</a>");
		Reporter.log("<br/>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtils.screenshotPath + "><img src=" + TestUtils.screenshotPath
				+ " height=200 widht=200></a>");
				
		//this is for Extent report
//		test.log(LogStatus.FAIL, arg0.getName().toUpperCase()+" Failed with exception : "+arg0.getThrowable());
//		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtils.screenshotPath));		
//		rep.endTest(test);
//		rep.flush();
		
	}
	

	public void onTestStart(ITestResult arg0) {
		
//		test = rep.startTest(arg0.getName().toUpperCase());
		
	}
	
	public void onTestSuccess(ITestResult arg0) {
	
//		test.log(LogStatus.PASS, arg0.getName().toUpperCase()+" PASS");
//		rep.endTest(test);
//		rep.flush();
	}

	public void onTestSkipped(ITestResult arg0) {
//		test.log(LogStatus.SKIP, arg0.getName().toUpperCase()+" Test get Skipped as runmode is NO");
//		rep.endTest(test);
//		rep.flush();
	}
}
