package com.qa.demo.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.ActiveProfiles;

import com.qa.demo.selenium.TDLSite.TDLPortal;

@ActiveProfiles(profiles = "test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@Order(1)
	public void createListTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);
		// driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		// WHEN: I navigate to the create tab
		website.navCreateTab();

		// AND: I create a list
		website.createPage.createList("My list");

		// THEN: I should see the list was created successfully
		String result = "";
		synchronized (result) {
			result.wait(1000);
			result = website.createPage.createListStatus();
		}

		// Assertion
		assertEquals("Your to-do list has been created!", result);
	}

	@Test
	@Order(2)
	public void createTaskTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);

		// WHEN: I navigate to the create tab
		website.navCreateTab();

		// AND: I add a task
		website.createPage.addTask("My task description");

		// THEN: I should see the task was added successfully
		String result = "";
		synchronized (result) {
			result.wait(1000);
			result = website.createPage.addTaskStatus();
		}

		// Assertion
		assertEquals("Your task has been created!", result);
	}

	@Test
	@Order(3)
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
	@Order(4)
	public void updateTaskTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS);

		// WHEN: I navigate to the update list tab
		website.navUpdateTab();

		// AND: I update a task in a list
		website.updatePage.updateTask("My updated task description");

		// THEN: I should see the task was updated successfully
		String status = "";
		synchronized (status) {
			status.wait(1000);
			status = website.updatePage.updateTaskStatus();
		}

		// AND: See my updated task description
		website.navReadTab();
		website.readPage.readList();
		String result = website.readPage.listResult();

		// Assertion
		assertEquals("Your list has been updated!", status);
		assertEquals("1. My updated task description", result);
	}

	@Test
	@Order(5)
	public void deleteTaskTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);
		
		// AND: I have a task in list two
		website.navCreateTab();
		website.deletePage.createTaskToDelete("Delete me");

		// WHEN: I navigate to the delete tab
		website.navDeleteTab();

		// AND: I delete a task in list two
		website.deletePage.deleteTask();

		// THEN: I should see the task was deleted successfully
		String result = "";
		synchronized (result) {
			result.wait(1000);
			result = website.deletePage.deleteTaskStatus();
		}

		// Assertion
		assertEquals("Your task has been deleted!", result);
	}

	@Test
	@Order(6)
	public void deleteListTest() throws InterruptedException {
		// GIVEN: that I can access my TDL Web Application
		driver.get(url);

		// WHEN: I navigate to the delete tab
		website.navDeleteTab();

		// AND: I delete a list
		website.deletePage.deleteList();

		// THEN: I should see the list was created successfully
		String result = "";
		synchronized (result) {
			result.wait(1000);
			result = website.deletePage.deleteListStatus();
		}

		// Assertion
		assertEquals("Your to-do list has been deleted!", result);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

}
