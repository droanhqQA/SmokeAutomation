package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.applications.AppsNavFunc;
import com.connectors.*;
import com.dao.UserDAO;
import com.dao.UserLogin;
import com.utils.TakeScreenshots;


public class ApplicationTest {
	public ChromeDriver driver;
	public AppsNavFunc navFunc;
	public final String RESULT_MSG="Record(s) found";
	WebDriverWait wait;
	@BeforeMethod
	public void setUp() throws IOException
	{
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
		
		final URL resource = ApplicationTest.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		
		FileInputStream  fs = new FileInputStream(resource.getFile());
		XSSFWorkbook workbook= new XSSFWorkbook(fs);
		
		// con_sheet = workbook.getSheetAt(1);
		XSSFSheet user_sheet = workbook.getSheetAt(0);
		UserDAO userDAO = new UserDAO(fs, workbook, user_sheet);
		String u_name = userDAO.getU_name();
		String u_pass = userDAO.getU_pass();
		String os=System.getProperty("os.name").toLowerCase();
		System.out.println("OS name: "+os);
		String driver_type = os.contains("windows") ? "/chromedriver.exe" :"/chromedriver";
		final URL driver_path = ApplicationTest.class.getResource(driver_type);
	    System.out.println(driver_path.getFile());
		
		System.setProperty("webdriver.chrome.driver", driver_path.getFile());

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1500,800");
		options.addArguments("incognito");
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		 wait = new WebDriverWait(driver, Duration.ofMinutes(3));
		UserLogin user = new UserLogin(driver);
		navFunc = new AppsNavFunc(driver);
		driver.get("https://studio.dronahq.com/apps");
		//driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
		user.login(u_name,u_pass);
		workbook.close();
		
	}
	//@Ignore
	 
	@Test(priority = 1,retryAnalyzer = RetryAnalyzer.class)
	public void createApp()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		 
		        try {     
		            // Do the operation here on the element    
		    	driver.findElement(By.xpath("//div[@class='sidebar-options']"));
		    		
		        }  
		        catch(NoSuchElementException ex)
		        {

		            driver.navigate().refresh(); 
		        }
		        catch(ElementNotVisibleException ex)
		        {

		            driver.navigate().refresh(); 
		        }
		        catch(Exception ex) {    
		            driver.navigate().refresh();    
		        }
		   
		new WebDriverWait(driver,Duration.ofMinutes(15))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='upper-container']/div/div[1]/div")));
		//wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//div[@class='upper-container']/div/div[1]/div")))));
		driver.findElement(By.xpath("//div[@class='upper-container']/div/div[1]/div")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//*[@id='showcase-wrap']/div[3]/div/div[1]/div[1]")).click();
		driver.findElement(By.xpath("//input[@class='app-name']")).sendKeys("AutoApp");
		driver.findElement(By.xpath("//textarea[@class='app-desc']")).sendKeys("Description");
		driver.findElement(By.xpath("//*[@id=\"icons-suggestion-list-container\"]/div[3]")).click();
		new WebDriverWait(driver, Duration.ofSeconds(300)).until(ExpectedConditions.textToBe(By.xpath("/html/body/div[4]/div[2]/div[2]/div[2]/div/div[1]/div[1]"), "Master Template"));
		driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]")).click();
		String winHandleBefore = driver.getWindowHandle();
		new WebDriverWait(driver, Duration.ofMinutes(5)).until(ExpectedConditions.numberOfWindowsToBe(2));
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("New Window "+tabs.get(1));
		new WebDriverWait(driver, 50)
		.until(ExpectedConditions.titleContains("AutoApp"));
		String title = driver.getTitle();
		System.out.println("Title: "+title);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		//wait.until(waitForSave());
		//driver.findElement(By.xpath("//button[@class='sideshow-next-step-button']")).click();
		//wait.until(waitForSave());
		new TakeScreenshots().takeScreenShot("CreateApp","Apps",driver);
		AssertJUnit.assertEquals(title, "AutoApp");
		
	}

	//@Ignore
	 
	@Test(dependsOnMethods = "createApp",priority = 2,retryAnalyzer = RetryAnalyzer.class)
	//@Test(priority=2)
	public void addControls()
	{
		System.out.println("Add Controls");
		navFunc.gotoApp(driver);
		navFunc.addControl(driver);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='page-body-box']/div[2]/div")).click();//click tablegrid
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	    String un_name = driver.findElement(By.xpath("//div[@class='textinput_container']/a")).getAttribute("data-value");
	    System.out.println(un_name);
	    new TakeScreenshots().takeScreenShot("AddControlsss","Apps",driver);
	    wait.until(navFunc.waitForSave());
	    AssertJUnit.assertEquals(un_name,"tablegrid");
	    
	}
	
	//@Ignore
	@Test(dependsOnMethods = "addControls",priority = 3,retryAnalyzer = RetryAnalyzer.class)
	//@Test
	 
	public void addMongoConnector()
	{
		System.out.println("Add Mongo Connector");
		 //driver.findElement(By.xpath("//div[@data-appname='AutoApp']")).click();
		navFunc.gotoApp(driver);
		navFunc.addMongo(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(navFunc.waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	//@Ignore
	 
	@Test(dependsOnMethods = "addControls",priority = 4,retryAnalyzer = RetryAnalyzer.class)
	//@Test
	public void addPostgreConnector()
	{
		System.out.println("Add Postgre Connector");
		navFunc.gotoApp(driver);
		navFunc.addPostgre(driver);
		String resMsg = navFunc.getResultMsg(driver);
		wait.until(navFunc.waitForSave());
		System.out.println(resMsg.contains(RESULT_MSG));
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
		
	}
	
	//@Ignore
	 
	@Test(dependsOnMethods = "addControls",priority = 5,retryAnalyzer = RetryAnalyzer.class)
	//@Test
	public void addAutoApi()
	{
		System.out.println("Add API Connector");
		navFunc.gotoApp(driver);
		navFunc.addRestApi(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(navFunc.waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	//@Ignore
	 
	@Test(dependsOnMethods = "addControls",priority = 6,retryAnalyzer = RetryAnalyzer.class)
	//@Test
	public void addGraphQL()
	{
		System.out.println("Add GraphQL Connector");
		navFunc.gotoApp(driver);
		navFunc.addGraphQL(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(navFunc.waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	//	
	//@Test
	@Ignore
	@Test(dependsOnMethods = "addControls",priority = 7,retryAnalyzer = RetryAnalyzer.class)
	public void ButtonFlow()
	{
		System.out.println("Button flow with google sheet");
		navFunc.gotoApp(driver);
		String msg =navFunc.buttonFlow(driver);
		AssertJUnit.assertEquals(msg, "Google Sheet connected");
	}
	//@Ignore
	@Test(priority = 8,retryAnalyzer = RetryAnalyzer.class)
	public void CheckPreview()
	{
		System.out.println("Checking for preview");
		navFunc.gotoApp(driver);
		String title=navFunc.checkPreview(driver);
		AssertJUnit.assertEquals(title.contains("Preview"),true );
	}
	

	//@Ignore
	@Test(priority = 9,retryAnalyzer = RetryAnalyzer.class)
	public void CheckPublish()
	{
		navFunc.gotoApp(driver);
		String msg = navFunc.checkPublish(driver);
		AssertJUnit.assertEquals(msg, "Publish successfully");
	}
	//@Ignore
	@Test(priority = 10,retryAnalyzer = RetryAnalyzer.class)
	public void CheckAppImport()
	{
		
		String res = navFunc.AppImport(driver);
		Assert.assertEquals("4",res);
	}
	@AfterMethod
	public void closeBrowser(ITestResult result)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"Apps",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		driver.quit();
	}


		
}
