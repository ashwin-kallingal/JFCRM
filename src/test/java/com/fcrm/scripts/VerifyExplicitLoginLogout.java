package com.fcrm.scripts;

import org.testng.annotations.Test;
import com.fcrm.base.BaseTest;
import com.fcrm.pages.HomePage;
import com.fcrm.pages.LoginPage;
import com.fcrm.util.Excel;

public class VerifyExplicitLoginLogout extends BaseTest
{
	
	public VerifyExplicitLoginLogout()
	{
		AutoLoginRequired=false;
		AutoLogoutRequired=false;
	}
	
	@Test
	
	public void testExplicitLoginLogout()
	{
		String usn = Excel.getCellValue(INPUT_FILE, INPUT_SHEET, 1, 0);
		String pwd = Excel.getCellValue(INPUT_FILE, INPUT_SHEET, 1, 1);
		
		LoginPage lp = new LoginPage(driver);
		lp.verifyLogin(usn, pwd);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		driver.switchTo().frame("mainpanel");
		HomePage hp = new HomePage(driver);
		hp.verifyLogout();	
		
		log.info("Explicit Login Logout Passed");	
		AutoLoginRequired=true;
		AutoLogoutRequired=true;
		
	}
	
	
}