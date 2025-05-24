package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.dto.Config;
import app.bookstore.selenium.WebDriverManager;
import app.bookstore.selenium.helpers.BrowserFactory;
import app.bookstore.selenium.helpers.NoSuchBrowserException;
import app.bookstore.selenium.pages.MainPage;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BookStoreBaseWebTest {

    @BeforeMethod
    public void setUp() {
        var browser = Config.getInstance().getBrowser();
        try {
            WebDriverManager.setDriver(BrowserFactory.getBrowser(browser));
        } catch (NoSuchBrowserException e) {
            throw new RuntimeException(e);
        }
        BookStoreDB.init();
    }

    protected MainPage login() {
        WebDriverManager.getDriver().get(Config.getInstance().getBaseUrl());
        MainPage mainPage = new MainPage(WebDriverManager.getDriver());
        PageFactory.initElements(new AjaxElementLocatorFactory(WebDriverManager.getDriver(), 10), mainPage);
        return mainPage;
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (!testResult.isSuccess() && testResult.getStatus() != ITestResult.SKIP) {
            if (WebDriverManager.getDriver() != null) {
                saveScreenshot();
            }
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
