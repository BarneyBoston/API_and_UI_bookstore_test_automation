package app.bookstore.selenium;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {

    private WebDriverManager() {
        // Prevent instantiation
    }

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance){
        driver.set(driverInstance);
    }

    public static void quitDriver(){
        WebDriver driverInstance = driver.get();
        if (driverInstance != null) {
            driverInstance.quit();
            driver.remove();
        }
    }

}
