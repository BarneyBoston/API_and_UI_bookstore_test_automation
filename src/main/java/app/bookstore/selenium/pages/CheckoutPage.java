package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("unused")
public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[text()='Checkout']")
    private WebElement checkoutText;

    @Step("Assert that checkout page text is displayed")
    public void assertCheckoutTextIsDisplayed() {
        assertThatElementIsVisible(checkoutText, "Checkout page text is not visible");
    }
}
