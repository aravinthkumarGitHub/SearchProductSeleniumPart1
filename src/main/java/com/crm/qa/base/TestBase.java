package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.crm.qa.util.WebEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static long WAIT_TIME_IN_SECONDS = 40;

	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/crm"
					+ "/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void initialization(){
		String browserName = prop.getProperty("browser");

		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
		}


		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));

	}


	JavascriptExecutor js = (JavascriptExecutor) driver;

	public void waitForWebElementToBeVisible(WebElement element){
		try {
			WebDriverWait wait = new WebDriverWait(driver,WAIT_TIME_IN_SECONDS);
			if(wait.until(ExpectedConditions.visibilityOf(element))!=null){
				Reporter.log("Waiting for element to load");
			}
			else{
				Reporter.log("element is not visible");
			}

		} catch (Exception e){
			Reporter.log("element is not visible"+ e.getStackTrace());
		}
	}

	public void clickElement (WebElement element){
		try {

			waitForWebElementToBeVisible(element);
			if(element.isDisplayed()){
				scrollToElement(element);
				setImplicitWait();
				element.click();
				setImplicitWait();
				Reporter.log( element.getText() +" is clicked");
			}

		} catch (Exception e){
			Reporter.log("Element is not clickable"+ e.getStackTrace());
		}
	}


	public void setValueToTextBox (WebElement element, String inputText){
		try {
			waitForWebElementToBeVisible(element);
			if(element.isDisplayed()){
				scrollToElement(element);
				setImplicitWait();
				element.clear();
				element.sendKeys(inputText);
				Reporter.log(  inputText +"is entered in "+element.getText());
			}

		} catch (Exception e){
			Reporter.log(inputText +" is not entered into the text box"+ e.getStackTrace());
		}
	}

	public void mouseHoverOverElement (WebElement element){
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
		setImplicitWait();
		Reporter.log(  " mouse is hovered over "+element.getText());
	}

	public void scrollToElement(WebElement element){
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void setImplicitWait(){
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
}

