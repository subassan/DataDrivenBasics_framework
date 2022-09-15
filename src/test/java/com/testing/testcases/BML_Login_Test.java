package com.testing.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.testing.base.TestBase;

public class BML_Login_Test extends TestBase{
	
	@Test(priority=1)
	public void bml_Login_Test() throws InterruptedException, IOException {
		
		
		//softAssertion Title Test
		verifyEquals("XYX","adfa");
		
		
		log.info("Inside Login Test");
		click("BML_button_CSS");
		
		Assert.assertTrue(isElementPresent("AddCust_button_CSS"),"Login not Successfull");
		log.info("Login Test Successfully Executed !!!");
		
		//forcefully fail the test
		Assert.fail("Login not successfully!!");
		
	}
	
	
}
