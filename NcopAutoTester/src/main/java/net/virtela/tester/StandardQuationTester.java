package net.virtela.tester;

import net.virtela.config.ServerInfo;
import net.virtela.utils.CommonHelper;
import net.virtela.utils.Constants;
import net.virtela.utils.PageElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class StandardQuationTester extends NcopTester {

	private static final String REPORT_FILE_NAME = "/StandardQuationTester.html";
	private static final String REPORT_NAME = "Standard Quote Pricing Test";

	@Override
	protected String getReportFileName() {
		return Constants.REPORT_PATH + REPORT_FILE_NAME;
	}

	@Override
	protected String getReportName() {
		return REPORT_NAME;
	}

	@Test(priority = 0, alwaysRun = true)
	public void loginToNcop() {

		final ExtentTest loginTest = this.extentReport.startTest("Login to Ncop", "Using Internal AD account to login to NCOP");

		this.driver.get(ServerInfo.getNcopUrl());
		this.wait.until(ExpectedConditions.presenceOfElementLocated(By.name(PageElement.LOGIN_USERNAME_INPUT_TEXT)));
		this.driver.manage().window().maximize();

		this.driver.findElement(By.name(PageElement.LOGIN_USERNAME_INPUT_TEXT)).sendKeys(CommonHelper.readConfig(Constants.CONFIG_KEY_USERNAME));
		this.driver.findElement(By.name(PageElement.LOGIN_PASSWORD_INPUT_TEXT)).sendKeys(CommonHelper.readConfig(Constants.CONFIG_KEY_PASSWORD));
		this.driver.findElement(By.id(PageElement.LOGIN_SUBMIT_BUTTON)).click();
		loginTest.log(LogStatus.PASS, "Login Successful");

		this.extentReport.endTest(loginTest);

	}

	@Test(dependsOnMethods = { "loginToNcop" }, alwaysRun = true, priority = 1)
	public void createManualProject() {
		final ExtentTest pmDashTest = this.extentReport.startTest("PM Dashboard", "Open PM Dashboard to view Projects in the queue");
		this.openPricingDashboard();
		pmDashTest.log(LogStatus.PASS, "PM Dashboard Opened");
		this.extentReport.endTest(pmDashTest);
	}

}
