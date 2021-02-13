package com.qa.demo.selenium.TDLSite.child;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class readTab {
	
	@FindBy(xpath = "//*[@id=\"read\"]/div/div[1]/form/button[1]")
	private WebElement readListBtn;
	@FindBy(xpath = "//*[@id=\"displayList\"]/p[1]")
	private WebElement taskResult;
	
	public readTab(WebDriver driver) {
		super();
	}
		
	public WebElement getTaskResult() {
		return taskResult;
	}

	public void readList() {
		readListBtn.click();
	}
	
	public String listResult() {
		return taskResult.getText();
	}

}
