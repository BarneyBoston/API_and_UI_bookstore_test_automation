package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.dto.Config;
import app.bookstore.selenium.helpers.BrowserFactory;
import app.bookstore.selenium.helpers.NoSuchBrowserException;
import app.bookstore.selenium.pages.MainPage;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BookStoreBaseWebTest {
    protected WebDriver driver;
    public BookStoreDB db;

    @BeforeMethod
    public void setUp() {
        var browser = Config.getInstance().getBrowser();
        try {
            driver = BrowserFactory.getBrowser(browser);
        } catch (NoSuchBrowserException e) {
            throw new RuntimeException(e);
        }
        db = BookStoreDB.getInstance();
    }

    protected MainPage login() {
        driver.get(Config.getInstance().getBaseUrl());
        return PageFactory.initElements(driver, MainPage.class);
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (!testResult.isSuccess() && testResult.getStatus() != ITestResult.SKIP) {
            if (driver != null) {
                saveScreenshot();
            }
        }
        if (driver != null)
            driver.quit();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
