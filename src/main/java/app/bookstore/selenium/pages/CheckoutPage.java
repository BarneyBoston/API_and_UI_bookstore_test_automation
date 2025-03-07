package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("unused")
public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[text()='Checkout']")
    private WebElement checkoutText;

    @FindBy(css = ".showlogin")
    private WebElement clickHereToLoginButton;

    @FindBy(css = ".showcoupon")
    private WebElement clickHereToEnterYourCodeButton;

    @FindBy(css = "#username")
    private WebElement usernameOrEmailField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(css = "#coupon_code")
    private WebElement couponCodeField;

    @FindBy(css = "[name='apply_coupon']")
    private WebElement applyCouponButton;

    @FindBy(css = ".woocommerce-billing-fields")
    private WebElement billingDetailsSection;

    @FindBy(css = ".woocommerce-additional-fields")
    private WebElement additionalInformationSection;

    @FindBy(css = ".woocommerce-checkout-review-order")
    private WebElement checkoutOrderSection;

    @FindBy(css = "#place_order")
    private WebElement placeOrderButton;

    @FindBy(css = ".woocommerce_error")
    private WebElement cardErrorMessage;

    @FindBy(css = ".woocommerce-error")
    private WebElement detailsErrorMessage;

    @FindBy(css = "[name='cardnumber']")
    private WebElement cardNumberField;

    @FindBy(css = "[name='exp-date']")
    private WebElement expiryDateField;

    @FindBy(css = "[name='cvc']")
    private WebElement cvcField;

    @FindBy(css = "[title='Secure card number input frame']")
    private WebElement cardNumberIframe;

    @FindBy(css = "[title='Secure expiration date input frame']")
    private WebElement expiryDateIframe;

    @FindBy(css = "[title='Secure CVC input frame']")
    private WebElement cvcIframe;

    @FindBy(css = "#billing_first_name")
    private WebElement firstNameField;

    @FindBy(css = "#billing_last_name")
    private WebElement lastNameField;

    @FindBy(css = "#billing_address_1")
    private WebElement houseNumberAndStreetNameField;

    @FindBy(css = "#billing_address_2")
    private WebElement apartmentSuiteUnitField;

    @FindBy(css = "#billing_postcode")
    private WebElement postcodeField;

    @FindBy(css = "#billing_city")
    private WebElement townField;

    @FindBy(css = "#billing_phone")
    private WebElement phoneField;

    @FindBy(css = "#billing_email")
    private WebElement emailField;

    @FindBy(xpath = "//h1[text()='Order received']")
    private WebElement orderReceivedMessage;

    @Step("Click here to login")
    public CheckoutPage clickHereToLogin() {
        clickElement(clickHereToLoginButton);
        return this;
    }

    @Step("Click here to enter your code")
    public CheckoutPage clickHereToEnterYourCode() {
        clickElement(clickHereToEnterYourCodeButton);
        return this;
    }

    @Step("Click place order")
    public CheckoutPage placeOrder() {
        clickElement(placeOrderButton);
        return this;
    }

    @Step("Input card number")
    public CheckoutPage inputCardNumber(String number) {
        switchToIframe(cardNumberIframe);
        sendKeysToElement(cardNumberField, number);
        switchToDefaultContent();
        return this;
    }

    @Step("Input expiry date")
    public CheckoutPage inputExpiryDate(String date) {
        switchToIframe(expiryDateIframe);
        sendKeysToElement(expiryDateField, date);
        switchToDefaultContent();
        return this;
    }

    @Step("Input cvc number")
    public CheckoutPage inputCvcNumber(String number) {
        switchToIframe(cvcIframe);
        sendKeysToElement(cvcField, number);
        switchToDefaultContent();
        return this;
    }

    @Step("Input first name as {name}")
    public CheckoutPage inputFirstNameAs(String name) {
        sendKeysToElement(firstNameField, name);
        return this;
    }

    @Step("Input last name as {name}")
    public CheckoutPage inputLastNameAs(String name) {
        sendKeysToElement(lastNameField, name);
        return this;
    }

    @Step("Input postcode as {postcode}")
    public CheckoutPage inputPostcodeAs(String postcode) {
        sendKeysToElement(postcodeField, postcode);
        return this;
    }

    @Step("Input town as {town}")
    public CheckoutPage inputTownAs(String town) {
        sendKeysToElement(townField, town);
        return this;
    }

    @Step("Input phone as {phone}")
    public CheckoutPage inputPhoneAs(String phone) {
        sendKeysToElement(phoneField, phone);
        return this;
    }

    @Step("Input email as {email}")
    public CheckoutPage inputEmailAs(String email) {
        sendKeysToElement(emailField, email);
        return this;
    }

    @Step("Input house number and street name as {name}")
    public CheckoutPage inputHouseNumberAndStreetNameAs(String name) {
        sendKeysToElement(houseNumberAndStreetNameField, name);
        return this;
    }

    @Step("Input apartment, suite, unit as {name}")
    public CheckoutPage inputApartmentSuiteUnitAs(String name) {
        sendKeysToElement(apartmentSuiteUnitField, name);
        return this;
    }

    @Step("Assert that checkout page text is displayed")
    public void assertCheckoutTextIsDisplayed() {
        assertThatElementIsVisible(checkoutText, "Checkout page text is not visible");
    }

    @Step("Assert that login window is expanded")
    public void assertLoginWindowIsExpanded() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(usernameOrEmailField.isDisplayed()).isTrue();
            softly.assertThat(passwordField.isDisplayed()).isTrue();
        });
    }

    @Step("Assert that coupon window is expanded")
    public void assertCouponWindowIsExpanded() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(couponCodeField.isDisplayed()).isTrue();
            softly.assertThat(applyCouponButton.isDisplayed()).isTrue();
        });
    }

    @Step("Assert that coupon window is expanded")
    public void assertAllElementsAreDisplayed() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(billingDetailsSection.isDisplayed()).isTrue();
            softly.assertThat(checkoutOrderSection.isDisplayed()).isTrue();
            softly.assertThat(additionalInformationSection.isDisplayed()).isTrue();
        });
    }

    @Step("Assert error message is {message}")
    public void assertCardErrorMessageIs(String message) {
        waitForElementToBeVisible(cardErrorMessage, 3);
        assertThat(cardErrorMessage.getText()).contains(message);
    }

    @Step("Assert error message is {message}")
    public void assertDetailsErrorMessageIs(String message) {
        waitForElementToBeVisible(detailsErrorMessage, 3);
        assertThat(detailsErrorMessage.getText()).contains(message);
    }

    @Step("Assert order is received")
    public void assertOrderIsReceived() {
        waitForElementToBeVisible(orderReceivedMessage, 10);
        assertThat(orderReceivedMessage.isDisplayed()).isTrue();
    }


}
