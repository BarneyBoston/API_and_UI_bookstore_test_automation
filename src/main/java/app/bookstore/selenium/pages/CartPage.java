package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unused")
public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[text()='Cart']")
    private WebElement cartText;

    @FindBy(css = ".shop_table.cart")
    private WebElement cartTable;

    @FindBy(css = ".cart_totals")
    private WebElement cartTotalsTable;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".product-name>a")
    private List<WebElement> productNames;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".qty")
    private List<WebElement> quantityFields;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".cart_item [data-title='Subtotal'] .amount")
    private List<WebElement> cartTableSubtotals;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".product-price .amount")
    private List<WebElement> cartTablePrices;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".cart_totals .amount")
    private List<WebElement> cartTotalsPrices;

    @FindBy(css = "[name='update_cart']")
    private WebElement updateCartButton;

    @FindBy(css = ".woocommerce-message")
    private WebElement updatedMessage;

    @FindBy(css = ".woocommerce-error")
    private WebElement updatedError;

    @FindBy(css = "#coupon_code")
    private WebElement couponCodeField;

    @FindBy(css = "[name='apply_coupon']")
    private WebElement applyCoupon;

    @FindBy(css = ".checkout-button")
    private WebElement checkoutButton;

    @Step("Input quantity of product at index {index} as: {quantity}")
    public CartPage inputQuantityAs(int index, String quantity) {
        sendKeysToElement(quantityFields.get(index), quantity);
        return this;
    }

    @Step("Input coupon code as: {couponCode}")
    public CartPage inputCouponCodeAs(String couponCode) {
        sendKeysToElement(couponCodeField, couponCode);
        return this;
    }

    @Step("Increase quantity of product at index {index} by {by}")
    public CartPage increaseQuantityOfProductBy(int index, int by) {
        waitForElementToBeVisible(quantityFields.get(index), 3);
        IntStream.range(0, by).forEach(i -> quantityFields.get(index).sendKeys(Keys.ARROW_UP));
        return this;
    }

    @Step("Reduce quantity of product at index {index} by {by}")
    public CartPage reduceQuantityOfProductBy(int index, int by) {
        waitForElementToBeVisible(quantityFields.get(index), 3);
        IntStream.range(0, by).forEach(i -> quantityFields.get(index).sendKeys(Keys.ARROW_DOWN));
        return this;
    }

    @Step("Click update cart")
    public CartPage updateCart() {
        clickElement(updateCartButton);
        waitForLoadingSpinnersDisappear();
        return this;
    }

    @Step("Click apply coupon")
    public CartPage applyCoupon() {
        clickElement(applyCoupon);
        waitForLoadingSpinnersDisappear();
        return this;
    }

    @Step("Click proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        clickElement(checkoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Assert subtotal at index {index} changed to expected")
    public void assertSubTotalChangedToExpected(int index) {
        double quantity = Double.parseDouble(Objects.requireNonNull(quantityFields.get(index).getDomAttribute("value")).trim());
        double price = Double.parseDouble(cartTablePrices.get(index).getText().replaceAll("[^0-9.,]", "").replace(",", ".").trim());
        double expectedSubtotal = quantity * price;

        double actualSubtotal = Double.parseDouble(cartTableSubtotals.get(index).getText().replaceAll("[^0-9.,]", "").replace(",", ".").trim());

        assertThat(actualSubtotal)
                .as("Subtotal at index %d does not match expected value", index)
                .isEqualTo(expectedSubtotal);
    }

    @Step("Assert subtotal at index {index} changed to expected")
    public void assertCartTotalsChangedAsExpected(int index) {
        double quantity = Double.parseDouble(Objects.requireNonNull(quantityFields.get(index).getDomAttribute("value")).trim());
        double price = Double.parseDouble(cartTablePrices.get(index).getText().replaceAll("[^0-9.,]", "").replace(",", ".").trim());
        double expectedSubtotal = quantity * price;

        double actualSubtotal = Double.parseDouble(cartTotalsPrices.get(index).getText().replaceAll("[^0-9.,]", "").replace(",", ".").trim());

        assertThat(actualSubtotal)
                .as("Subtotal at index %d does not match expected value", index)
                .isEqualTo(expectedSubtotal);
    }

    @Step("Assert product's {index} quantity is: {quantity}")
    public CartPage assertQuantityIs(int index, String quantity) {
        waitForLoadingSpinnersDisappear();
        AssertionsForClassTypes.assertThat(quantityFields.get(index).getDomAttribute("value")).isEqualTo(quantity).as("Actual quantity differs from expected");
        return this;
    }


    @Step("Assert cart page text is displayed")
    public void assertCartTextIsDisplayed() {
        assertThatElementIsVisible(cartText, "Cart page text is not displayed");
    }

    @Step("Assert all elements of cart page are displayed")
    public void assertAllElementsAreDisplayed() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cartTable.isDisplayed()).isTrue().as("Cart table is not displayed");
            softly.assertThat(cartTotalsTable.isDisplayed()).isTrue().as("Cart totals table is not displayed");
            softly.assertThat(cartText.isDisplayed()).isTrue().as("Cart text is not displayed");
        });
    }

    @Step("Assert product names match chosen products")
    public void assertProductNamesMatchChosenProducts(List<String> bookNames) {
        assertThatElementIsVisible(productNames.get(0), "Product name is not displayed");
        List<String> actualProductNames = productNames.stream()
                .map(WebElement::getText)
                .toList();

        AssertionsForInterfaceTypes.assertThat(actualProductNames)
                .as("Product names do not match expected values")
                .containsExactlyInAnyOrderElementsOf(bookNames);
    }

    @Step("Assert product name match chosen product")
    public void assertProductNameMatchChosenProduct(String bookName) {
        assertThatElementIsVisible(productNames.get(0), "Product name is not displayed");
        assertThat(productNames.get(0).getText())
                .as("Product name do not match expected value")
                .isEqualTo(bookName);
    }

    @Step("Assert message is {message}")
    public void assertMessageIs(String message) {
        assertThat(updatedMessage.getText()).contains(message);
    }

    @Step("Assert update cart button is disabled")
    public void assertUpdateCartButtonIsDisabled() {
        assertThat(updateCartButton.isEnabled())
                .as("Update cart button should be disabled")
                .isFalse();
    }

    @Step("Assert error is {message}")
    public void assertErrorIs(String message) {
        assertThat(updatedError.getText()).containsIgnoringCase(message);
    }

}
