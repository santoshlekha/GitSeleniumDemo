package com.testng.JavaseleniumProject;


import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
public class SeleniumTestNgTest {

	
	private static WebDriver driver = null;
   
	// setting expected title from the ebay.com/Daraz.lk
	static String expectedTitle = "Swag Labs";
	static String expectedTitledaraz ="Online Shopping Sri Lanka: Clothes, Electronics & Phones | Daraz.lk";
	// Electronics & Phones | Daraz.lk";
	// create the htmlReporter object
	ExtentSparkReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test1, test2;
	
	
		

	@BeforeSuite
	public void initDriver() throws Throwable {
		htmlReporter = new ExtentSparkReporter("extentReport.html");
		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		this.takeSnapShot(driver, "d://test.png") ;
		
	}
	
	@Test
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File(fileWithPath);
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
		}

	@Test(priority = 0)
	public void firstTest() throws Exception {
		test1 = extent.createTest("saucedemo.com launch test", "test to validate launch of saucedemo.com ");
		test1.log(Status.INFO, "Starting test case");

		driver.get("https://www.saucedemo.com/");
		// Retrieving web page title
		String title = driver.getTitle();
		System.out.println("The page title is : " + title);
		Assert.assertEquals(title, expectedTitle);
		// Locating web element
		WebElement uName = driver.findElement(By.id("user-name"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(uName));
		WebElement pswd = driver.findElement(By.id("password"));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(pswd));

		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

		WebElement loginBtn = driver.findElement(By.id("login-button"));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(loginBtn));

		// Peforming actions on web elements
		uName.sendKeys("standard_user");
		pswd.sendKeys("secret_sauce");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		takeSnapShot(driver, "d://test.png") ;
		loginBtn.click();

		// Putting implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			// Locating web element
			WebElement expBtn = driver.findElement(By.id("react-burger-menu-btn"));
			expBtn.click();
			WebElement logoutBtn = driver.findElement(By.id("logout_sidebar_link"));
			// Validating presence of element
			if (logoutBtn.isDisplayed()) {

				// Performing action on web element
				// logoutBtn.click();
				System.out.println("LogOut button appeared so login is successful!");
			}
		} catch (Exception e) {
			System.out.println("Incorrect Usernane or Password is entered....");
		}

		}
		@Test (priority = 1)
		 public void test2() throws InterruptedException {
		//creates a toggle for the given test, add all log events under it
		  test2 = extent.createTest("Daraz Search Test", "Test to validate search box ");
		test2.log(Status.INFO, "Starting test case");
		//maximize the window 
		  driver.manage().window().maximize();
		  test2.pass("Maximize has done");
		  
		  //Navigate to daraz.lk
		  driver.get("https://www.daraz.lk/#");
		  Thread.sleep(1000);
		  test2.pass("Navigate to Daraz.lk");
		//compare whether the title id matching
		  String actualTitle = driver.getTitle();
		  Assert.assertEquals(actualTitle, expectedTitledaraz);
		  test2.pass("Title is correct");
		//enter in the TextBox
		  driver.findElement(By.xpath("//*[@id=\"q\"]")).sendKeys("Mobile");
		  test2.pass("Entered thex in the text box");
		  //hit enter
		  driver.findElement(By.xpath("//*[@id=\"topActionHeader\"]/div/div[2]/div/div[2]/form/div/div[2]/button")).sendKeys(Keys.RETURN); 
		  test2.pass("Press enter from keyboard");
		  
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
		test1.pass("closed the browser");
		test2.pass("closed the browser");
		test1.info("test completed");
		test2.info("test completed");
		  
		  //write results into the file
		  extent.flush();
	}

}
