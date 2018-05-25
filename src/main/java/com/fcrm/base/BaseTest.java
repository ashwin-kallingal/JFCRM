package com.fcrm.base;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.fcrm.common.AutomationConstant;
import com.fcrm.common.MyTestListener;
import com.fcrm.pages.HomePage;
import com.fcrm.pages.LoginPage;
import com.fcrm.util.Property;
import com.fcrm.util.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(MyTestListener.class)
public class BaseTest implements AutomationConstant
{
	public Logger log;
	public WebDriver driver;
	public ExtentTest testReport;
	
	public static String url;
	public static String un;
	public static String pw;
	public static String loginpage;
	public static String homepage;
	public static String myBrowser;
	public static long timeout;
	public static ExtentReports eReport;
	public static boolean AutoLoginRequired=true;
	public static boolean AutoLogoutRequired=true;
	
	
	public BaseTest()
	{
		log=Logger.getLogger(this.getClass());
		Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	}
	
	@BeforeSuite
	
	public void initExtentReport()
	{
		log.info("Initializing Extent Report");
		eReport = new ExtentReports(AutomationConstant.REPORT_PATH+"/"+"ExtentReport"+".html");
		url=Property.getPropertyValue(CONFIG_FILE, "URL");
		un=Property.getPropertyValue(CONFIG_FILE, "UN");
		pw=Property.getPropertyValue(CONFIG_FILE, "PW");
		loginpage=Property.getPropertyValue(CONFIG_FILE, "LOGINPAGE");
		homepage=Property.getPropertyValue(CONFIG_FILE, "HOMEPAGE");
		timeout=Long.parseLong(Property.getPropertyValue(CONFIG_FILE, "IMPLICIT"));				
	}
	
	@AfterSuite
	
	public void closeExtentReport()
	{
		log.info("Closing Extent Report");
		eReport.flush();		
	}
	
	@BeforeTest
	@Parameters({"browser"})
	
	public void initBrowser(@Optional("Chrome") String browser)
	{
		log.info("Execution Started in the Browser:"+browser);
		myBrowser=browser.toUpperCase();
	}
	
	@AfterTest
	@Parameters({"browser"})
	
	public void closeBrowser(@Optional("Chrome") String browser)
	{
		log.info("Execution Ended in the Browser:"+browser);		
	}
	
	@BeforeClass
    @Parameters({"browser"})
	
	public void initApp(@Optional("Chrome") String browser)
	{
		log.info("Application Opened");
		if(browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty(CHROME_KEY, CHROME_FILE);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else
		{
			System.setProperty(GECKO_KEY, GECKO_FILE);
			driver=new FirefoxDriver();
		}
		
		driver.navigate().to(url);
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass
	
	public void closeApp()
	{
		log.info("Closing Application");
		driver.quit();
	}
	
	@BeforeMethod
	
	public void preCondition(Method method)
	{
		testReport = eReport.startTest(method.getName());
		
		if(AutoLoginRequired)
		{
			log.info("Implicit Login");
			LoginPage lp = new LoginPage(driver);
			lp.verifyLogin(un, pw);
			
			
			
		}
		else
		{
			log.warn("Explicit Login");
		}
		
		AutoLoginRequired=true;
	}
	
	
	@AfterMethod
	
	public void postCondition(ITestResult result)
	{
		if(AutoLogoutRequired)
		{
			log.info("Implicit Logout");
			driver.switchTo().frame("mainpanel");
			HomePage hp = new HomePage(driver);
			hp.verifyLogout();
		}
		else
		{
			log.warn("Explicit Logout");
		}
		
		if(result.getStatus()==ITestResult.FAILURE)
		{
			String imgPath=Utility.getScreenshot(driver, result.getName());
			String img = testReport.addScreenCapture(imgPath);
			
			testReport.log(LogStatus.FAIL, result.getName(), "See Log for more Details");
			testReport.log(LogStatus.FAIL, result.getThrowable());
			testReport.log(LogStatus.FAIL, img);
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			testReport.log(LogStatus.SKIP, result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			testReport.log(LogStatus.PASS, result.getName());
		}		
		AutoLogoutRequired=true;
		eReport.endTest(testReport);
	}
}