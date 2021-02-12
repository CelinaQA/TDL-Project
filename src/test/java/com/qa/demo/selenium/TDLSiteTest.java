package com.qa.demo.selenium;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TDLSiteTest {
	
	private static RemoteWebDriver driver;
	private final String url = "http://thedemosite.co.uk/";
	
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

	@AfterAll
	public static void afterAll() {

		driver.quit();

	}
	
	

}
