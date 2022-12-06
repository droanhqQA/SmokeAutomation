package com.applications;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.GetTimeouts;


public class AppsNavFunc {
	WebDriver driver;
	Properties prop;
	Long max_time,min_time,avg_time;
	WebDriverWait explicitwait ;
	By tablegrid = By.xpath("//div[@class='page-body-box']/div[2]/div/div[1]");
	public void getImplicit()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
	}
	public AppsNavFunc(WebDriver driver) {
		super();
		this.driver = driver;
		explicitwait =  new WebDriverWait(driver, Duration.ofMinutes(5));
		GetTimeouts timeouts = new GetTimeouts();
		max_time = timeouts.getMax_time();
		min_time = timeouts.getMin_time();
		avg_time = timeouts.getAvg_time();
	}
	
	public void createApp(WebDriver driver)
	{
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.findElement(By.xpath("//div[@class='showcase-new-app'][@data-type='blank']")).click();
		driver.findElement(By.xpath("//input[@class='app-name']")).sendKeys("AutoApp");
		driver.findElement(By.xpath("//textarea[@class='app-desc']")).sendKeys("Description");
		driver.findElement(By.xpath("//*[@id=\"icons-suggestion-list-container\"]/div[3]")).click();
		driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]")).click();
	}
	
	public void addControl(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
	
		new WebDriverWait(driver, Duration.ofMinutes(2))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='btnlbl' and text()='Controls']")));
		driver.findElement(By.xpath("//div[@class='btnlbl' and text()='Controls']")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("table");
	    driver.findElement(By.xpath("//*[@data-popup_doc_id='Table Grid']/parent::a")).click();
	    							 ///html/body/div[4]/div[15]/div[2]/div[1]/div/div[2]/div[1]/div[4]/div[1]/div[1]/div/div[2]/div/a[2]
	    driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	    driver.findElement(By.xpath("//div[@class='page-body-box']/div[2]")).click();//click tablegrid 
	}
	public void gotoApp(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(2));
		By App_name=By.xpath("//div[@data-appname='AutoApp']/div[2]");
		int refreshCount = 10;    
	    List<WebElement> element = driver.findElements(App_name);    
	    for (int i = 0; i < refreshCount; i++) {    
	        if (element.size() > 0) {     
	            // Do the operation here on the element    
	    		break;
	        } else {    
	            driver.navigate().refresh();    
	        }     
	    }
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.visibilityOfElementLocated(App_name));
		driver.findElement(App_name).click();
		System.out.println("Clicked on app");
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
		String winHandleBefore = driver.getWindowHandle();
		try {
			new WebDriverWait(driver, Duration.ofMinutes(5)).until(ExpectedConditions.numberOfWindowsToBe(2));
		}
		catch(TimeoutException ex)
		{
			driver.findElement(App_name).click();
			new WebDriverWait(driver, Duration.ofMinutes(5)).until(ExpectedConditions.numberOfWindowsToBe(2));
			winHandleBefore = driver.getWindowHandle();
		}
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		new WebDriverWait(driver, Duration.ofSeconds(300))
		.until(ExpectedConditions.titleContains("App"));
		String title = driver.getTitle();
		System.out.println(title+" ");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//button[@class='sideshow-next-step-button']")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
//		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip-button']"));
//		new WebDriverWait(driver, Duration.ofSeconds(60))
//		.until(ExpectedConditions.elementToBeClickable(skipBtn));
//		((JavascriptExecutor)driver).executeScript("arguments[0].click();", skipBtn);
	}
	
	public void addMongo(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
	
		getImplicit();
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(tablegrid));//click tablegrid 
		driver.findElement(tablegrid).click();
//		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(
//				By.xpath("//*[@data-tab='header']")));
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		getImplicit();
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		//Finding the remove connector btn
		getImplicit();
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		getImplicit();
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoTestMongo']")).click(); //connectors finding
		getImplicit();
		System.out.println("Clicked on Mongo");
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();
		getImplicit();
		System.out.println("Selected Query");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		getImplicit();
	}
	public void addPostgre(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		getImplicit();
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
//		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(tablegrid));//click tablegrid 
		driver.findElement(tablegrid).click();
		getImplicit();
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		addControl(driver);
//		if(driver.findElement(By.xpath("//div[@class='event-button-click']"))!=null)
//			driver.findElement(By.xpath("//div[@class='event-button-click']")).click();
		System.out.println(driver.findElement(By.xpath("/html/body/div[4]/div[15]/div[1]/div[2]/div[3]/button[4]")).isEnabled());
		explicitwait.until(ExpectedConditions.elementToBeClickable(tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		getImplicit();
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoTestPostgre']")).click(); //connectors finding
		getImplicit();
		System.out.println("Clicked on Postgre");
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();
		getImplicit();
		System.out.println("Selected Query");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		getImplicit();
		System.out.println("Clicked on Continue");

	}
	public void addRestApi(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		getImplicit();
		//explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]")));
		driver.findElement(tablegrid).click();//click tablegrid 
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		addControl(driver);
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(tablegrid));//click tablegrid 
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoApi']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();//First Api Query
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Clicked on Continue");
		driver.findElement(By.xpath("//div[@class='sel-keys-dp-wrap d-flex f-column']/div[1]")).click();//dropList
		driver.findElement(By.xpath("//div[@title='records.id']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields.Name']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields.Status']")).click();
	}
	
	public void addGraphQL(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		getImplicit();
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]")));
		driver.findElement(tablegrid).click();//click tablegrid 
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		addControl(driver);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(tablegrid));//click tablegrid 
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoGraphQL']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();//First Api Query
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Clicked on Continue");
		driver.findElement(By.xpath("//div[@class='sel-keys-dp-wrap d-flex f-column']/div[1]")).click();//dropList
		driver.findElement(By.xpath("//div[@title='data.launchesPast']")).click();
		driver.findElement(By.xpath("//div[@title='data.launchesPast.mission_name']")).click();
		driver.findElement(By.xpath("//div[@title='data.launchesPast.launch_date_local']")).click();
		driver.findElement(By.xpath("//div[@title='data.launchesPast.rocket.rocket_name']")).click();
		
		
	}
	public String getResultMsg(WebDriver driver)
	{
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action finish-service-config']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='ui simple button fml-actn-button save-bindapi-formula']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		WebElement msgElement = driver.findElement(By.xpath("//*[@data-type='bindapi']/div[@class='ui form opt-body']/div/div[3]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", msgElement);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		msgElement.click();
		String resMsg = msgElement.getText();
		System.out.println(resMsg);
		return resMsg;
	}
	public String buttonFlow(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(tablegrid).click();//click tablegrid 
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		driver.findElement(By.xpath("//*[@data-tooltip='Desktop view']")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("bu");
		driver.findElement(By.xpath("//a[@data-field-type='Button1']/span")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		
		//going in action
		driver.findElement(By.xpath("//div[@class='control-button filled-theme']")).click();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[3]")).click();
		driver.findElement(By.xpath("//div[@class='flex-center action-discription']")).click();
		System.out.println("Inside Action");
		driver.findElement(By.xpath("//div[contains(@class,'actionflow-window')]/div[2]/descendant::i")).click();
		driver.findElement(By.xpath("//label[@for='hq-tabs-B']")).click();
		//adding google sheet
		
		//input[@type='search']
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Sheets");
		driver.findElement(By.xpath("//div[@title='Google Sheets']")).click();
		System.out.println("Inside Google sheet");
		getImplicit();
		driver.findElement(By.xpath("//div[@class='list vertical']/div[4]")).click();
//		driver.findElement(By.xpath("//*[@value='production']/parent::div/div/*[text()='Account']")).click();
//		driver.findElement(By.xpath("//div[@class='menu transition visible']/*[text()='AutoSheet']")).click();
		driver.findElement(By.xpath("//a[@class='icon footer-action config-service hq-primary-btn d-flex align-center pointer']")).click();//continue btn
		//driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[6]/div[2]/div[3]/div[2]/div[4]/div[2]/div[1]")).click();
		System.out.println("Select from drop down");
		//selecting acc
		driver.findElement(By.xpath("//div[@data-key='param.range']")).click();
		driver.findElement(By.xpath("//*[@data-label='Select Spreadsheet']/parent::div/div[4]/div[2]/div[2]")).click();
		driver.findElement(By.xpath("//span[text()='AutoSheet']//parent::div")).click();
		//driver.findElement(By.xpath("//textarea[@data-key='param.sheetName']")).sendKeys("Sheet1");
		driver.findElement(By.xpath("//div[@data-key='param.sheetName']/div/div[2]/i")).click();
		driver.findElement(By.xpath("//div[@data-value='Sheet1']")).click();
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding success popup
		
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[3]/div[2]/i")).click();
		
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
//		driver.findElement(By.xpath("//textarea[@data-key='message']")).clear();
//		driver.findElement(By.xpath("//textarea[@data-key='message']")).sendKeys("Google Sheet connected");
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Google Sheet connected');");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding failure popup
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[1]/div[2]/i")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		driver.findElement(By.xpath("//div[@class='ui input revamped']/div/div/input[2]")).click();
		driver.findElement(By.xpath("//div[@data-value='error']")).click();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).clear();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).sendKeys("Error");
//		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Google Sheet not connected");
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Google Sheet not connected');");
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).sendKeys("Google Sheet not connected");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		driver.findElement(By.xpath("/html/body/div[4]/div[15]/div[2]/div[11]/div/div[1]/div[2]/button[3]")).click();
		//button click complete
		explicitwait.until(waitForSave());
		checkPreview(driver);
		getImplicit();
		driver.findElement(By.xpath("//div[contains(@class,'ui button control-button')]")).click();
		getImplicit();
		String msg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
		System.out.println(msg);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	driver.findElement(By.xpath("/html/body/div[7]/div/div[3]/button[1]")).click();
		//driver.findElement(By.xpath("//*[@class='cross']")).click();
		return msg;
	}
	 public static ExpectedCondition<Boolean> waitForSave() {
		  System.out.println("Wait for application to load entirely");
	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return (Boolean) ((JavascriptExecutor) driver)
	          .executeScript("return document.querySelector('.js-save-form').disabled");
	      }
	    };
	  }

	public String checkPreview(WebDriver driver)
	{
		driver.findElement(By.xpath("/html/body/div[4]/div[15]/div[1]/div[2]/div[3]/button[4]/img")).click();//preview btn
		
			try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		String text = driver.findElement(By.xpath("/html/head/title")).getAttribute("innerHTML");
		System.out.println("Frame Title: "+text);
		List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));
		System.out.println("The total number of iframes are " + iframeElements.size());
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='previewFrame']")));
	
		
		String title = driver.findElement(By.xpath("/html/head/title")).getAttribute("innerHTML");
		System.out.println("Frame Title: "+title.trim());
		return title;
	}
	public String checkPublish(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.id("exportAndPublish")).click();
		driver.findElement(By.className("release-notes")).sendKeys("Automation Test");
		driver.findElement(By.id("publishDhqPackage")).click();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.id("exportAndPublish")).click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String msg = jsExecutor.executeScript("return document.getElementsByClassName('ui floated right red respMessage hide')[0].innerHTML;").toString();
		System.out.println(msg);
		return msg;
	}
	public String AppImport(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		final URL resource = AppsNavFunc.class.getResource("/ExportAuto.json");
	       System.out.println(resource);
		
		String exportApp_path = ((resource.toString().substring("file:/".length(),resource.toString().length())));
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.findElement(By.xpath("//*[@data-type='upload']")).click();
		driver.findElement(By.xpath("//*[@id='json-file-import']"))
		.sendKeys(exportApp_path);
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer upload-app-btn']")).click();
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer final-install-template']")).click();
		String success = driver.findElement(By.xpath("//*[@class='event-head']")).getText();
		System.out.println(success);
		
		driver.findElement(By.xpath("//*[@class='event-button-click']")).click();
		

		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		while(tabs.size()!=2)
		{
			 tabs = new ArrayList<String>(driver.getWindowHandles());
		}
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
//		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip close']"));
//		new WebDriverWait(driver,30)
//		.until(ExpectedConditions.visibilityOf(skipBtn));
		String title = driver.getTitle();
		System.out.println(title+" ");
		String no_of_controls = ((JavascriptExecutor)driver)
				.executeScript("return 	document.querySelectorAll('.fb-field-container').length","").toString();
		System.out.println(no_of_controls);
		return no_of_controls;
	}
}
