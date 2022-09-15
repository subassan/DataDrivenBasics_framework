package com.testing.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.testing.base.TestBase;
import com.testing.utilities.TestUtils;

public class OpenAccount_Test extends TestBase {

	@Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
	public void openAccount_Test(Hashtable<String,String> data) throws InterruptedException {

		Assert.fail("Open account test fail");
		
		if (!(TestUtils.isTestRunnable("openAccountTest", excel))) {

			throw new SkipException("Skipping the test " + "openAccountTest".toUpperCase() + "as the Run mode is NO");
		}

		click("OpenAc_btn_CSS");
		select("Cust_ID", data.get("Customer"));
		select("Currency_ID", data.get("Currency"));
		click("Process_btn_CSS");
		Thread.sleep(3000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

	}

}
