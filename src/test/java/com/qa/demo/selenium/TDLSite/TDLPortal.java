package com.qa.demo.selenium.TDLSite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.selenium.TDLSite.child.createTab;
import com.qa.demo.selenium.TDLSite.child.deleteTab;
import com.qa.demo.selenium.TDLSite.child.readTab;
import com.qa.demo.selenium.TDLSite.child.updateTab;

public class TDLPortal {
	
	@FindBy(id = "create-tab")
	private WebElement createTab_link;
	
	@FindBy(id = "read-tab")
	private WebElement readTab_link;
	
	@FindBy(id = "update-tab")
	private WebElement updateTab_link;

	@FindBy(id = "delete-tab")
	private WebElement deleteTab_link;
	
	public createTab createPage;
	public readTab readPage;
	public updateTab updatePage;
	public deleteTab deletePage;
	
	public TDLPortal(WebDriver driver) {
		super();
		createPage = PageFactory.initElements(driver, createTab.class);
		readPage = PageFactory.initElements(driver, readTab.class);
		updatePage = PageFactory.initElements(driver, updateTab.class);
		deletePage = PageFactory.initElements(driver, deleteTab.class);
	}
	
	public void navCreateTab() {
		createTab_link.click();
	}
	
	public void navReadTab() {
		readTab_link.click();
	}

	public void navUpdateTab() {
		updateTab_link.click();
	}
	
	public void navDeleteTab() {
		deleteTab_link.click();
	}

}
