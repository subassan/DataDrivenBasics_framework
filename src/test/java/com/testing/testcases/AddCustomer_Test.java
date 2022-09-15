package com.testing.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.testing.base.TestBase;
import com.testing.utilities.TestUtils;

public class AddCustomer_Test extends TestBase {

	@Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
	public void addCustomer_Test(Hashtable<String, String> data) throws InterruptedException {

		Assert.fail("Add customer test fail");
		click("AddCust_button_CSS");

		if (!data.get("runmode").equals("Y")) {

			throw new SkipException("Skipping the test case a runmode is NO for test data");
		}

		type("firstName_CSS", data.get("firstname"));
		type("lastName_CSS", data.get("lastname"));
		type("postcode_CSS", data.get("postcode"));
		click("add_btn_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		// verify the alert
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));

		alert.accept();
	}

}
