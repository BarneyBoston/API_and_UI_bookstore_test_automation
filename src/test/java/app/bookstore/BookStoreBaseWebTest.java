package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.dto.Config;
import app.bookstore.selenium.WebDriverManager;
import app.bookstore.selenium.helpers.AppFlow;
import app.bookstore.selenium.helpers.BrowserFactory;
import app.bookstore.selenium.helpers.NoSuchBrowserException;
import app.bookstore.selenium.pages.MainPage;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BookStoreBaseWebTest {

    protected WebDriver driver;
    protected MainPage mainPage;

    @BeforeMethod
    public void setUp() throws NoSuchBrowserException {

        var browser = Config.getInstance().getBrowser();

        WebDriver driver = BrowserFactory.getBrowser(browser);

        WebDriverManager.setDriver(driver);

        BookStoreDB.init();

        this.driver = driver;

        mainPage = new AppFlow(driver).open();
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (!testResult.isSuccess() && testResult.getStatus() != ITestResult.SKIP && WebDriverManager.getDriver() != null) {
            saveScreenshot();
        }
        if (WebDriverManager.getDriver() != null)
            WebDriverManager.quitDriver();
        BookStoreDB.remove();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
