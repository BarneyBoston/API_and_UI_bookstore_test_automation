package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[text()='Cart']")
    private WebElement cartText;

    @Step("Assert cart page text is displayed")
    public void assertCartTextIsDisplayed() {
        assertThatElementIsVisible(cartText, "Cart page text is not displayed");
    }
}
