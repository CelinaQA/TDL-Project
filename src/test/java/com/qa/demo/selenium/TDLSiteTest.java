package com.qa.demo.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.ActiveProfiles;

import com.qa.demo.selenium.TDLSite.TDLPortal;

@ActiveProfiles(profiles = "test")
public class TDLSiteTest {

	public TDLPortal website = PageFactory.initElements(driver, TDLPortal.class);
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
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);

		// WHEN: I navigate to the create tab
		website.navCreateTab();

		// AND: I create a list
		website.createPage.createList("My list");

		// THEN: I should see the list was created successfully
		String result = website.createPage.createListStatus();

		// Assertion
		assertEquals("Your to-do list has been created!", result);
	}

	@Test
	public void createTaskTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);

		// WHEN: I navigate to the create tab
		website.navCreateTab();

		// AND: I add a task
		website.createPage.addTask("My task description");

		// THEN: I should see the task was added successfully
		String result = website.createPage.addTaskStatus();

		// Assertion
		assertEquals("Your task has been created!", result);
	}

	@Test
	public void readListTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS);

		// WHEN: I navigate to the read list tab
		website.navReadTab();

		// AND: I select a list to read
		website.readPage.readList();

		// THEN: I should be able to see the task in my list
		String result = website.readPage.listResult();

		// Assertion
		assertEquals("1. My task description", result);
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
