package app.bookstore.selenium.pages.productpage;

import app.bookstore.selenium.pages.BasePage;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("unused")
public class ReviewTab extends BasePage {
    public ReviewTab(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".woocommerce-Reviews-title")
    private WebElement reviewTitleText;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".stars a")
    private List<WebElement> ratingStars;

    @FindBy(css = "#comment")
    private WebElement reviewField;

    @FindBy(css = "#author")
    private WebElement nameField;

    @FindBy(css = "#email")
    private WebElement emailField;

    @FindBy(css = "#submit")
    private WebElement submitButton;

    @FindBy(css = ".wp-die-message")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[text()='« Back']")
    private WebElement backFromErrorButton;

    @FindBy(css = ".woocommerce-review__awaiting-approval")
    private WebElement reviewAwaitingApprovalText;


    @Step("Click submit button")
    public ReviewTab clickSubmitButton() {
        clickElement(submitButton);
        return this;
    }

    @Step("Click back from error button")
    public ProductPage clickBackFromErrorButton() {
        clickElement(backFromErrorButton);
        return new ProductPage(driver);
    }

    @Step("Choose rating from 1 to 5: {rating}")
    public ReviewTab chooseRatingAs(Integer rating) {
        clickElement(ratingStars.get(rating));
        return this;
    }

    @Step("Input name as {name}")
    public ReviewTab inputNameAs(String name) {
        sendKeysToElement(nameField, name);
        return this;
    }

    @Step("Input review as {review}")
    public ReviewTab inputReviewAs(String review) {
        sendKeysToElement(reviewField, review);
        return this;
    }

    @Step("Input email as {email}")
    public ReviewTab inputEmailAs(String email) {
        sendKeysToElement(emailField, email);
        return this;
    }

    @Step("Assert alert text is \"Please select a rating\"")
    public void assertPleaseSelectRatingAlertIsDisplayed() {
        String alertText = driver.switchTo().alert().getText();
        assertThat(alertText).contains("Please select a rating");
    }

    @Step("Assert all elements of review tab are displayed")
    public void assertAllElementsAreDisplayed() {
        waitForPageToLoad(3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(reviewTitleText.getText()).isEqualTo("Reviews").as("Review title text is not as expected");
            softly.assertThat(ratingStars.size()).as("There should be 5 rating stars").isEqualTo(5);
            softly.assertThat(reviewField.isDisplayed() && reviewField.isEnabled()).isTrue().as("Review field is not displayed and editable");
            softly.assertThat(nameField.isDisplayed() && nameField.isEnabled()).isTrue().as("Name field is not displayed and editable");
            softly.assertThat(emailField.isDisplayed() && emailField.isEnabled()).isTrue().as("Email field is not displayed and editable");
        });
    }

    @Step("Assert error text is: {text}")
    public void assertErrorTextIs(String text) {
        waitForElementToBeVisible(errorMessage, 3);
        assertThat(errorMessage.getText()).isEqualTo(text);
    }

    @Step("Assert review is awaiting approval")
    public void assertReviewIsAwaitingApproval() {
        waitForElementToBeVisible(reviewAwaitingApprovalText, 3);
        assertThat(reviewAwaitingApprovalText.getText()).contains("Your review is awaiting approval");
    }

}
