package com.fcrm.common;

import java.util.ArrayList;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.fcrm.base.BaseTest;
import com.fcrm.util.Excel;
import com.fcrm.util.Utility;

public class MyTestListener implements ITestListener
{
	public static int passCount=0;
	public static int failCount=0;
	public static int skipCount=0;
	
	List<String> passCases = new ArrayList<String>(); 
	List<String> failCases = new ArrayList<String>(); 
	List<String> skipCases = new ArrayList<String>(); 

	@Override
	public void onFinish(ITestContext result) {
		Reporter.log("Pass Count:"+passCount,true);
		Reporter.log("Fail Count:"+failCount,true);
		Reporter.log("Skip Count:"+skipCount,true);
		
		Reporter.log("Pass Cases:"+passCases,true);
		Reporter.log("Fail Cases:"+failCases,true);
		Reporter.log("Skip Cases:"+skipCases,true);		
	}

	@Override
	public void onStart(ITestContext result) {
		Reporter.log("Test Case Started:"+result.getName());		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Excel.setCellValue(result.getName(), "FAIL", Utility.getFormattedDateTime(), BaseTest.myBrowser);
		failCases.add(result.getName());
		failCount++;	
		Utility.getDesktopScreenShot(AutomationConstant.SCREENSHOT_PATH);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Excel.setCellValue(result.getName(), "SKIP", Utility.getFormattedDateTime(), BaseTest.myBrowser);
		skipCases.add(result.getName());
		skipCount++;		
	}

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log("Test Started:"+result.getName());		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	Excel.setCellValue(result.getName(), "PASS", Utility.getFormattedDateTime(), BaseTest.myBrowser);
	passCases.add(result.getName());
	passCount++;		
	}
}