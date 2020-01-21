package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class findBrokenLinksSelenium {
	
	
	public static WebDriver driver;
	public static String baseUrl = "http://demo.guru99.com/test/newtours/";
	String url = "";
	HttpURLConnection huc = null;
    int respCode = 200;
	

	@BeforeMethod
	public void setUP() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	@Test
	public void FindBrokenLinks()
	{
		driver.get(baseUrl);
	List<WebElement> links= driver.findElements(By.tagName("a"));
	Iterator<WebElement> it=links.iterator();
	while(it.hasNext())
	{
		url=it.next().getAttribute("href");
		System.out.println(url);
		if(url==null||url.isEmpty())
		{
			System.out.println("url is either not configured for anchor tag or it is empty");
			continue;
		}
		if(!url.startsWith("http://demo.guru99.com/")){
            System.out.println("URL belongs to another domain, skipping it.");
            continue;
        }
		try {
            huc = (HttpURLConnection)(new URL(url).openConnection());
            
            huc.setRequestMethod("HEAD");
            
            huc.connect();
            
            respCode = huc.getResponseCode();
            
            if(respCode >= 400){
                System.out.println(url+" is a broken link");
            }
            else{
                System.out.println(url+" is a valid link");
            }
                
        }
		catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	}
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
