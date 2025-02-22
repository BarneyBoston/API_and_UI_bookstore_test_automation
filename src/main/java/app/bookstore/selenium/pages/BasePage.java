package app.bookstore.selenium.pages;

import app.bookstore.selenium.dto.HasNavigationBar;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage implements HasNavigationBar {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public NavigationBar getNavigationBar() {
        return PageFactory.initElements(driver, NavigationBar.class);
    }

    protected void clickElement(WebElement element) {
        int maxRetries = 5;
        int tries = 0;

        while (tries < maxRetries) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | NoSuchElementException |
                     TimeoutException e) {
                System.out.println("Attempt " + (tries + 1) + " failed: " + e.getClass().getSimpleName());
                tries++;
            }
        }
        throw new RuntimeException("Failed to click element after " + maxRetries + " attempts");
    }

    protected void sendKeysToElement(WebElement element, String text) {
        int maxRetries = 5;
        int tries = 0;

        while (tries < maxRetries) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOf(element));
                element.clear();
                element.sendKeys(text);
                return;
            } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException e) {
                System.out.println("Attempt " + (tries + 1) + " failed: " + e.getClass().getSimpleName());
                tries++;
            }
        }
        throw new RuntimeException("Failed to send keys to element after " + maxRetries + " attempts");
    }
}
