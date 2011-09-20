package com.example.myapp;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Factory;

public class AppTestFactory {

	private String _saucelabsURL = "saucelabsURL"; //Replace with real URL

	/**
	 * Generates various test instances
	 * @return 
	 * @throws Exception
	 */
	@Factory
	public Object[] createInstances() throws Exception {


		// Test Firefox 5 on Windows Vista
		DesiredCapabilities c1 = DesiredCapabilities.firefox();
		c1.setCapability("version", "5");
		c1.setCapability("platform", Platform.VISTA);
		c1.setCapability("name", "Testing FB in Sauce");
		WebDriver d1 = new RemoteWebDriver(
				new URL(_saucelabsURL), c1);

		// Test Explorer on Windows Vista
		DesiredCapabilities c2 = DesiredCapabilities.internetExplorer();		
		c2.setCapability("platform", Platform.VISTA);
		c2.setCapability("name", "Testing FB in Sauce");
		WebDriver d2 = new RemoteWebDriver(
				new URL(_saucelabsURL), c2);

		// Test instances to run
		Object[] result = {				
				new AppTest(d1),
				new AppTest(d2)
		};


		return result;

	}




}
