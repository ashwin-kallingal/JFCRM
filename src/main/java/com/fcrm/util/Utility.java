package com.fcrm.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.fcrm.common.AutomationConstant;

public class Utility 
{
	
	public static String getFormattedDateTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_YYYY_hh_mm_ss");
		return sdf.format(new Date());		
	}
	
	public static String getScreenshot(WebDriver driver, String name)
	{
		String path = "E:\\JobTest\\JobRevision\\snap"+"-"+name+".png";
		try
		{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(path));
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
		return path;
	}
	
	public static void getDesktopScreenShot(String imgFolder)
	{
		
		String timeStamp = getFormattedDateTime();
		
		try
		{
			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle scrnRect = new Rectangle(d);
			
			Robot r = new Robot();
			BufferedImage img = r.createScreenCapture(scrnRect);
			File output = new File(imgFolder+timeStamp+".png");
			ImageIO.write(img, "png", output);
			
		}catch(Exception e)
		{			
			e.printStackTrace();
		}		
	}
	
	public static void switchFrame(WebDriver driver)
	{
		driver.switchTo().frame("mainpanel");
	}
	
	
}