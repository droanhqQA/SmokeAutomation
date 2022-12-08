package testcases;

import org.testng.annotations.Test;

import com.dao.UserDAO;
import com.dao.UserLogin;
import com.manageusers.AddCheckUsers;
import com.utils.TakeScreenshots;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.AssertJUnit;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddUserTest {
	ChromeDriver driver ;
	FileInputStream fs ;
	String email,pass,new_email;
	AddCheckUsers checkUsers;
	@BeforeMethod
	public void setUp() throws IOException
	{
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
		String os=System.getProperty("os.name").toLowerCase();
		System.out.println("OS name: "+os);
		String driver_type = os.contains("windows") ? "/chromedriver.exe" :"/chromedriver";
		final URL driver_path = AddUserTest.class.getResource(driver_type);
	       System.out.println(driver_path);
		
		System.setProperty("webdriver.chrome.driver", "/var/lib/jenkins/driver/chromedriver");
				ChromeOptions options = new ChromeOptions();
						options.addArguments("headless");
						options.addArguments("incognito");
						options.addArguments("window-size=1920,1080");
	
		driver = new ChromeDriver(options);
		driver.get("https://studio.dronahq.com/apps");
		
		driver.manage().window().maximize();
		checkUsers = new AddCheckUsers(driver);
	 
	}
	//@Ignore
	@Test
	public void AddUser()
	{	
		new_email = checkUsers.generateEmail();
		String checkEmail = checkUsers.addCheckUser(new_email);
		System.out.println(checkEmail.contains(new_email));
		AssertJUnit.assertEquals(checkEmail, new_email);
	}
	//@Ignore
	@Test(dependsOnMethods = "AddUser" )
	public void LoginUser()
	{
		System.out.println("Email: "+new_email);
		UserLogin user = new UserLogin(driver);
		user.login(new_email,"Test@123");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@class='new-profile-icon']")).click();
		String emailAfter = driver.findElement(By.xpath("//div[@class='profile-email']")).getText();
		System.out.println( new_email+"\n"+emailAfter);
		AssertJUnit.assertEquals(emailAfter, new_email);
	}
	@AfterMethod
	public void closeBrowser(ITestResult result)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"ManageUser",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
}
