package com.example.myapp;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Factory;

public class AppTestFactory {

	private String _saucelabsURL = "_saucelabsURL";

	@Factory
	public Object[] createInstances() throws Exception {


		DesiredCapabilities c1 = DesiredCapabilities.firefox();
		c1.setCapability("version", "5");
		c1.setCapability("platform", Platform.VISTA);
		c1.setCapability("name", "Testing FB in Sauce");
		WebDriver d1 = new RemoteWebDriver(
				new URL(_saucelabsURL), c1);

		DesiredCapabilities c2 = DesiredCapabilities.internetExplorer();		
		c2.setCapability("platform", Platform.VISTA);
		c2.setCapability("name", "Testing FB in Sauce");
		WebDriver d2 = new RemoteWebDriver(
				new URL(_saucelabsURL), c2);

		Object[] result = {				
				new AppTest(d1),
				new AppTest(d2)
		};


		return result;

	}




}
