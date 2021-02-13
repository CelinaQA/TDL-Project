package com.qa.demo.selenium;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.demosite.DemoSitePortal;
import com.qa.demo.demosite.child.DemoLogin;

public class TDLSiteTest {

	private static RemoteWebDriver driver;
	private final String url = "http://127.0.0.1:8080/index.html";

	// Designed to return ChromeOptions to configure new ChromeDrivers in Selenium
	public static ChromeOptions chromeCfg() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		ChromeOptions cOptions = new ChromeOptions();

		// Settings
		prefs.put("profile.default_content_setting_values.cookies", 2);
		prefs.put("network.cookie.cookieBehavior", 2);
		prefs.put("profile.block_third_party_cookies", true);

		// Create ChromeOptions to disable Cookies pop-up
		cOptions.setExperimentalOption("prefs", prefs);

		return cOptions;
	}

	@BeforeAll
	public static void beforeAll() {
		// system.property
		System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chrome/chromedriver.exe");
		driver = new ChromeDriver(chromeCfg());

	}

	@Test
	public void createListTest() throws InterruptedException {

		// GIVEN: that I can access http://thedemosite.co.uk/
		driver.get(url);

		DemoSitePortal website = PageFactory.initElements(driver, DemoSitePortal.class);
		DemoLogin loginPage = PageFactory.initElements(driver, DemoLogin.class);

	}

	@Test
	public void createTaskTest() throws InterruptedException {

	}

	@Test
	public void readListTest() throws InterruptedException {

	}

	@Test
	public void readTaskTest() throws InterruptedException {

	}

	@Test
	public void updateListTest() throws InterruptedException {

	}

	@Test
	public void updateTaskTest() throws InterruptedException {

	}

	@Test
	public void deleteListTest() throws InterruptedException {

	}

	@Test
	public void deleteTaskTest() throws InterruptedException {

	}

	@AfterAll
	public static void afterAll() {

		driver.quit();

	}

}
