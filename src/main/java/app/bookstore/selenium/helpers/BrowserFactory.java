package app.bookstore.selenium.helpers;

import app.bookstore.dto.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {

    static Boolean isHeadless = Config.getInstance().getIsHeadless();

    public static WebDriver getBrowser(String browser) throws NoSuchBrowserException {
        switch (browser) {
            case "firefox" -> {
                return getFirefoxInstance();
            }
            case "chrome" -> {
                return getChromeInstance();
            }
            case "edge" -> {
                return getEdgeInstance();
            }
            default -> throw new NoSuchBrowserException(browser);
        }
    }

    private static WebDriver getChromeInstance() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if (isHeadless) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver getFirefoxInstance() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        if (isHeadless) {
            options.addArguments("--headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver getEdgeInstance() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
        if (isHeadless) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }
}
