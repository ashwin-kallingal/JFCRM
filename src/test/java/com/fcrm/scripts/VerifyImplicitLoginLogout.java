package com.fcrm.scripts;

import org.testng.annotations.Test;

import com.fcrm.base.BaseTest;

public class VerifyImplicitLoginLogout extends BaseTest
{
	
	public VerifyImplicitLoginLogout()
	{
		AutoLoginRequired=true;
		AutoLogoutRequired=true;
	}
	
	@Test
	
	public void testImplicitLoginLogout()
	{
		log.info("Implicit Login Logout Passed");	
	}
}


