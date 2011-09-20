package com.example.myapp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;




/**
 * Simple tests for facebook
 */
public class AppTest implements ITest

{
	private WebDriver _driver;

	/**
	 * Create the test case
	 *
	 * @param driver Driver used at test cases
	 */
	public AppTest( WebDriver driver )    
	{
		_driver = driver;
		_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	} 


	@BeforeClass    
	public void connect2FB(ITestContext context)
	{
		_driver.get("http://www.facebook.com");
	}

	/**
	 * Login to facebook
	 */    
	@Test    
	public void loginFB()
	{    	

		// Find email and password element        
		WebElement pass = _driver.findElement(By.id("pass"));
		WebElement email = _driver.findElement(By.id("email"));

		// Enter data        
		pass.sendKeys("pass");
		email.sendKeys("email@test.com");

		// Submit
		pass.submit();

		assert loggedInFB() : "I can't login to FB!!!";
	}

	/**
	 * Logout from facebook
	 */
	@Test(dependsOnMethods = { "loginFB" }, priority=10)
	public void logoutFB()
	{    	

		WebElement logout = _driver.findElement(By.id("logout_form"));
		logout.submit();

		assert loggedInFB()==false : "Still logged!!!";
	}



	/**
	 * Update facebook status
	 */
	@Test(dependsOnMethods = { "loginFB" })
	public void update()
	{

		String date = new Date().toString();

		//Click on profile        	
		_driver.findElement(By.xpath("//ul[@id='pageNav']/li[2]/a")).click();

		//Click on wall    	
		_driver.findElement(By.xpath("//li[@id='navItem_wall']/a")).click();

		//Click on text area to expand it
		_driver.findElement(By.name("xhpc_message")).click();

		//Write time on it    	
		WebElement textArea = _driver.findElement(By.name("xhpc_message_text"));    	
		textArea.sendKeys(date);

		//Expand privacy menu
		_driver.findElement(By.xpath("//div[@id='pagelet_privacy_widget']/div/div/div/a")).click();

		//Click on custom privacy settings
		_driver.findElement(By.xpath("//div[@id='pagelet_privacy_widget']//li[3]/a")).click();    	

		//Select friends of friends and submit    	
		Select select = new Select(_driver.findElement(By.name("friends_value")));
		select.selectByValue("50");    	
		_driver.findElement(By.name("submit")).click();    	

		//Submit new status
		textArea.submit();


		assert  _driver.getPageSource().contains(date) : "Status not updated!!!";    	

	}   


	/**
	 * Quit driver
	 */
	@AfterClass
	public void quitDriver(){
		_driver.quit();
	}



	/**
	 * Check if we are logged in facebook
	 * @return True if logged
	 */
	private boolean loggedInFB()
	{
		Cookie c = _driver.manage().getCookieNamed("c_user");
		return ( c==null  ? false :  true);

	}


	/**
	 * Implements ITest to get more info in the HTML reports
	 */
	public String getTestName() {		

		if(_driver.getClass() == RemoteWebDriver.class){ 

			Capabilities cap = ((RemoteWebDriver) _driver).getCapabilities();

			String ret = cap.getBrowserName() + " " +
					cap.getVersion() + " in " +
					cap.getPlatform();

			return ret;  
		}

		return _driver.getClass().toString();
	}






}
