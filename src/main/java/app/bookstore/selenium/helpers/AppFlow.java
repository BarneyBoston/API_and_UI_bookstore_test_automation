package app.bookstore.selenium.helpers;

import app.bookstore.dto.Config;
import app.bookstore.selenium.pages.MainPage;
import org.openqa.selenium.WebDriver;

public class AppFlow {

    private final WebDriver driver;

    public AppFlow(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage open() {
        driver.get(Config.getInstance().getBaseUrl());
        return new MainPage(driver);
    }
}
