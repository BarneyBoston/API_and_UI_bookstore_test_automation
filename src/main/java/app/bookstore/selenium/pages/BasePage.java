package app.bookstore.selenium.pages;

import app.bookstore.selenium.dto.HasNavigationBar;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("unused")
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

    protected void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementToBePresent(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
    }

    public void waitForPageToLoad(int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState"), "complete")
        );
    }

    protected void assertThatElementIsVisible(WebElement element, String text) {
        waitForElementToBeVisible(element, 3);
        assertThat(element.isDisplayed())
                .isTrue()
                .as(text);
    }

    protected void assertThatElementIsEnabled(WebElement element, String text) {
        waitForElementToBeVisible(element, 3);
        assertThat(element.isEnabled())
                .isTrue()
                .as(text);
    }

    protected void assertThatElementIsDisplayedAndEnabled(WebElement element, String text) {
        waitForElementToBeVisible(element, 3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(element.isDisplayed()).isTrue().as(text);
            softly.assertThat(element.isEnabled()).isTrue().as(text);
        });
    }
}
