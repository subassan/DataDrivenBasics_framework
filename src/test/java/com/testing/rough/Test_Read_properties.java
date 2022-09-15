package com.testing.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Test_Read_properties {

	public static void main(String[] args) throws IOException {
		
		//for config.properties file
		Properties config = new Properties();
		
		//for OR.properties
		Properties OR = new Properties();
		
		//reading from config.properties file
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		
		System.out.println(config.getProperty("browser"));
		
		
		//reading from OR file
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		
		System.out.println(OR.getProperty("bmlbutton"));
		//this is how we can use the property to find Element
		//driver.findElement(By.cssSelector(Or.getProperty("bmlbutton"))).click();
		
	}
	
	
	
}
