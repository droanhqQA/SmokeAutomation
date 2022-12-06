package com.manageusers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dao.UserLogin;

public class AddCheckUsers {
	WebDriver driver;
	public String new_email="";
	public AddCheckUsers(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public String generateEmail()
	{
		String new_email ="test" + System.nanoTime() + "@studio.com";
		return new_email;
	}
	public String addCheckUser(String email)
	{
		String u_name = "naxep99573@ceoshub.com";
		String u_pass = "qwerty77";
	
		UserLogin user = new UserLogin(driver);
		user.login(u_name,u_pass);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='sidebar-options']/div[@data-header='Users']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='invite-user-button']")).click();
		new WebDriverWait(driver, 180)
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='invite-u-name-ip form-control w-30']")));
		driver.findElement(By.xpath("//input[@class='invite-u-name-ip form-control w-30']")).sendKeys("Test");
	
		System.out.println(email);
//		emailSaver.setEmail(EmailAddress);
		driver.findElement(By.xpath("//input[@class='invite-u-email-ip form-control w-30']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@class='invite-u-password-ip form-control w-40 true ']")).sendKeys("Test@123");
		driver.findElement(By.id("selectUserRoleUserLst")).click();
		driver.findElement(By.xpath("//*[@id=\"selectUserRoleUserLst\"]/div[2]/div[1]")).click();
//		System.out.println(emailSaver.getEmail());
		driver.findElement(By.xpath("//div[@class='invite-user-actions']/div")).click();
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(waitWrappertoHide());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@class='search-user-input']")).sendKeys(new_email);
		driver.findElement(By.xpath("//div[text()='Apply Filters']")).click();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checkEmail="";
		List<WebElement> emailAfter = driver.findElements(By.className("u-m-email"));
	for (WebElement webElement : emailAfter) {
		checkEmail = webElement.getText();
		System.out.println("Inside ForEach: "+webElement.getText());
	}
	return checkEmail;
	}
	 public static ExpectedCondition<Boolean> waitWrappertoHide() {
		  System.out.println("Wait for application to load entirely");
	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	    	  Boolean res= true;
	    	  WebElement wrapper = driver.findElement(By.xpath("//div[contains(@class,'user-sheetview-wrapper')]"));
	    	  String w_class= wrapper.getAttribute("class");
	    	  res = w_class.contains("hide")?true:false;
	    	  System.out.println(w_class+" "+res);
	    	  return res;
	      }
	    };
	  }
}
