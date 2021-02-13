package com.qa.demo.selenium.TDLSite.child;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class createTab {
	
	@FindBy(id = "newListName")
	private WebElement inputListName;
	@FindBy(xpath = "//*[@id=\"create\"]/div/div[1]/form/div/button")
	private WebElement submitListBtn;
	@FindBy(id = "onCreation")
	private WebElement createListStatus;

	@FindBy(id = "newTaskDesc")
	private WebElement inputTaskDesc;
	@FindBy(xpath = "//*[@id=\"create\"]/div/div[3]/form/button")
	private WebElement submitTaskBtn;
	@FindBy(id = "onAddition")
	private WebElement addTaskStatus;
	
	public createTab(WebDriver driver) {
		super();
	}

	public void createList(String listName) {
		this.inputListName.sendKeys(listName);
		submitListBtn.click();
	}
	
	public String createListStatus() {
		return createListStatus.getText();
	}

	public void addTask(String taskDesc) {
		this.inputTaskDesc.sendKeys(taskDesc);
		submitTaskBtn.click();
	}

}
