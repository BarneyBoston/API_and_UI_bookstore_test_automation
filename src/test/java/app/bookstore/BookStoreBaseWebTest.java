package app.bookstore;

import app.bookstore.dto.Config;
import app.bookstore.selenium.helpers.BrowserFactory;
import app.bookstore.selenium.helpers.NoSuchBrowserException;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BookStoreBaseWebTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        var browser = Config.getInstance().getBrowser();
        try {
            driver = BrowserFactory.getBrowser(browser);
            driver.get(Config.getInstance().getBaseUrl());
        } catch (NoSuchBrowserException e) {
            throw new RuntimeException(e);
        }
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
