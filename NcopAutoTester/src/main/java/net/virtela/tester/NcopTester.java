package net.virtela.tester;

import net.virtela.config.ServerInfo;
import net.virtela.model.Mail;
import net.virtela.utils.CommonHelper;
import net.virtela.utils.Constants;
import net.virtela.utils.PageElement;
import net.virtela.utils.SmtpMailer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;

public abstract class NcopTester {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Actions action;
	protected ExtentReports extentReport;
	
	protected abstract String getReportFileName();
	protected abstract String getReportName();

	@BeforeClass
	public void setUp() {
		//Initialise Selenium
		this.driver = new FirefoxDriver();
		this.wait = new WebDriverWait(driver, 120);
		this.action = new Actions(this.driver);
		//Initialise the report
		this.extentReport = new ExtentReports(this.getReportFileName(), true);
		this.extentReport.config().reportName(this.getReportName());
		this.extentReport.addSystemInfo("Selenium Version", "2.45.0");
		this.extentReport.addSystemInfo("Environment", CommonHelper.readConfig(Constants.CONFIG_KEY_MODE));
		this.extentReport.addSystemInfo("Browser", "FireFox");	
	}
	
	@AfterSuite
	public void quitDriver() throws Exception {
		this.extentReport.flush();
		this.driver.quit();
		if (CommonHelper.isBooleanFlagActive(CommonHelper.readConfig(Constants.CONFIG_KEY_SEND_REPORT_MAIL))) {
			final Mail mail = new Mail();
			mail.setRecipient(CommonHelper.readConfig(Constants.CONFIG_KEY_REPORT_MAILING_LIST));
			mail.setSubject("[Selenium] NCOP Test Result");
			mail.setBody("Please see attachedment for the report.");
			mail.setAttachment(this.getReportFileName());
			SmtpMailer.sendMail(mail);
		}
	}

	protected void openPricingDashboard() {
		this.wait.until(ExpectedConditions.presenceOfElementLocated(By.id(PageElement.MAIN_RFQ_MANANGEMENT_MENU)));
		final WebElement we = this.driver.findElement(By.id(PageElement.MAIN_RFQ_MANANGEMENT_MENU));
		this.action.moveToElement(we).moveToElement(this.driver.findElement(By.id(PageElement.MAIN_PRCING_SUB_MENU))).click().build().perform();
	}

}
