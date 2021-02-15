package com.qa.demo.selenium.TDLSite.child;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class deleteTab {
	
	@FindBy(xpath = "//*[@id=\"selectListAddTask\"]")
	private WebElement selectListCreate;
	@FindBy(xpath = "//*[@id=\"selectListAddTask\"]/option[2]")
	private WebElement selectList2Create;
	@FindBy(id = "newTaskDesc")
	private WebElement inputTaskDesc;
	@FindBy(xpath = "//*[@id=\"create\"]/div/div[3]/form/button")
	private WebElement submitTaskBtn;
	
	
	
	@FindBy(xpath = "//*[@id=\"selectListDeleteTask1\"]")
	private WebElement selectListDelete;
	@FindBy(xpath = "//*[@id=\"selectListDeleteTask1\"]/option[2]")
	private WebElement selectList2Delete;
	@FindBy(xpath = "//*[@id=\"delete\"]/div/div[3]/form/button")
	private WebElement deleteTaskBtn;
	@FindBy(xpath = "//*[@id=\"delete\"]/div/div[1]/form/button")
	private WebElement deleteListBtn;
	
	@FindBy(id = "onDeleteTask")
	private WebElement deleteTaskStatus;
	@FindBy(id = "onDeleteList")
	private WebElement deleteListStatus;
	
	public void createTaskToDelete(String taskDesc) {
		selectListCreate.click();
		selectList2Create.click();
		this.inputTaskDesc.sendKeys(taskDesc);
		submitTaskBtn.click();
	}
	
	public void deleteTask() {
		selectListDelete.click();
		selectList2Delete.click();
		deleteTaskBtn.click();
	}
	
	public String deleteTaskStatus() {
		return deleteTaskStatus.getText();
	}
	
	public void deleteList() {
		deleteListBtn.click();
	}
	
	public String deleteListStatus() {
		return deleteListStatus.getText();
	}

}
