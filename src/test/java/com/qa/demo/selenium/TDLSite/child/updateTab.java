package com.qa.demo.selenium.TDLSite.child;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class updateTab {
	
	@FindBy(xpath = "//*[@id=\"update\"]/div/div[1]/form/button[1]")
	private WebElement selectListBtn;
	@FindBy(xpath = "//*[@id=\"update\"]/div/button")
	private WebElement saveChangesBtn;
	@FindBy(id = "taskDesc-4")
	private WebElement inputTaskDesc;
	@FindBy(id = "onUpdate")
	private WebElement updateTaskStatus;
	
	public updateTab(WebDriver driver) {
		super();
	}
	
	public void updateTask(String newTaskDesc) {
		selectListBtn.click();
		this.inputTaskDesc.clear();
		this.inputTaskDesc.sendKeys(newTaskDesc);
		saveChangesBtn.click();
	}
	
	public String updateTaskStatus() {
		return updateTaskStatus.getText();
	}

}
