package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Getter
@SuppressWarnings("unused")
public class PreviewCartPage extends BasePage {
    public PreviewCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[text()='View my cart']")
    private WebElement viewMyCartButton;

    @FindBy(xpath = "//*[text()='Go to checkout']")
    private WebElement goToCheckoutButton;

    @FindBy(xpath = "//strong[text()='Your cart is currently empty!']")
    private WebElement emptyCartText;

    @FindBy(xpath = "//a[text()='Start shopping']")
    private WebElement startShoppingButton;

    @FindBy(css = ".wc-block-cart-items__row")
    private WebElement productItemBlock;

    @FindBy(css = ".wc-block-components-totals-item")
    private WebElement subtotalBlock;

    @FindBy(css = "[aria-label='Increase quantity']")
    private WebElement increaseQuantityButton;

    @FindBy(css = "[aria-label='Reduce quantity']")
    private WebElement reduceQuantityButton;

    @FindBy(css = "[type='number']")
    private WebElement productQuantity;

    @FindBy(xpath = "//button[text()='Remove item']")
    private WebElement removeItemButton;

    @Step("Click start shopping")
    public MainPage clickStartShopping() {
        clickElement(startShoppingButton);
        return new MainPage(driver);
    }

    @Step("Click increase product quantity by {number}")
    public PreviewCartPage increaseProductQuantityBy(Integer number) {
        IntStream.range(0, number).forEach(i -> clickElement(increaseQuantityButton));
        return this;
    }

    @Step("Click reduce product quantity by {number}")
    public PreviewCartPage reduceProductQuantityBy(Integer number) {
        IntStream.range(0, number).forEach(i -> clickElement(reduceQuantityButton));
        return this;
    }

    @Step("Click remove item")
    public PreviewCartPage removeItem() {
        clickElement(removeItemButton);
        return this;
    }

    @Step("Click view my cart")
    public CartPage viewMyCart() {
        clickElement(viewMyCartButton);
        return new CartPage(driver);
    }

    @Step("Click go to checkout")
    public CheckoutPage goToCheckout() {
        clickElement(goToCheckoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Assert product quantity changed to {number}")
    public void assertProductQuantityChangedTo(String number) {
        assertThat(productQuantity.getDomAttribute("value"))
                .isEqualTo(number)
                .as("Product quantity is different from expected: " + number);
    }

    @Step("Assert reduce quantity button is disabled")
    public void assertReduceQuantityButtonIsDisabled() {
        waitForElementToBeVisible(reduceQuantityButton, 3);
        assertThat(reduceQuantityButton.getDomAttribute("disabled"))
                .isNotNull()
                .as("Reduce quantity button isn't disabled");
    }

    @Step("Assert all elements of empty cart are visible")
    public void assertAllElementsOfEmptyCartAreVisible() {
        waitForElementToBeVisible(emptyCartText, 3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(emptyCartText.isDisplayed()).isTrue().as("Empty cart text is not displayed");
            softly.assertThat(startShoppingButton.isDisplayed() && startShoppingButton.isEnabled()).isTrue().as("Start shopping button is not displayed and clickable");
        });
    }

    @Step("Assert all elements of filled cart are visible")
    public void assertAllElementsOfFilledCartAreVisible() {
        waitForElementToBeVisible(goToCheckoutButton, 3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(goToCheckoutButton.isDisplayed() && goToCheckoutButton.isEnabled()).isTrue().as("Go To Checkout button is not displayed and clickable");
            softly.assertThat(viewMyCartButton.isDisplayed() && viewMyCartButton.isEnabled()).isTrue().as("View My Cart button is not displayed and clickable");
            softly.assertThat(productItemBlock.isDisplayed()).isTrue().as("Product item block is not displayed");
            softly.assertThat(subtotalBlock.isDisplayed()).isTrue().as("Subtotal block is not displayed");
        });
    }

}
