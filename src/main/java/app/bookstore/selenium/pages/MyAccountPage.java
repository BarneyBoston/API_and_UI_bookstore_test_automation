package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("unused")
public class MyAccountPage extends BasePage {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#username")
    private WebElement usernameField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(css = "[name='login']")
    private WebElement loginButton;

    @FindBy(css = ".lost_password a")
    private WebElement lostPasswordLink;

    @FindBy(css = "#rememberme")
    private WebElement rememberMeBox;

    @FindBy(css = ".woocommerce-notices-wrapper + p")
    private WebElement successfulLoginNoticeText;

    @FindBy(css = ".woocommerce-error li")
    private WebElement incorrectLoginMessageText;

    @Step("Login as admin")
    public MyAccountPage loginAs(String username, String password) {
        sendKeysToElement(usernameField, username);
        sendKeysToElement(passwordField, password);
        clickElement(loginButton);
        return this;
    }

    @Step("Click lost your password link")
    public LostPasswordPage lostYourPassword() {
        clickElement(lostPasswordLink);
        return new LostPasswordPage(driver);
    }

    @Step("Assert all elements of My Account page are displayed.")
    public void assertAllElementsAreVisible() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(usernameField.isDisplayed()).isTrue().as("Username field is not displayed");
            softly.assertThat(passwordField.isDisplayed()).isTrue().as("Password field is not displayed");
            softly.assertThat(loginButton.isDisplayed()).isTrue().as("Login button is not displayed");
            softly.assertThat(lostPasswordLink.isDisplayed()).isTrue().as("Lost password link is not displayed");
            softly.assertThat(rememberMeBox.isDisplayed()).isTrue().as("Lost password link is not displayed");
        });
    }

    @Step("Assert login was successful with username as {username}")
    public void assertMessageAfterSuccessfulLoginContains(String username) {
        waitForElementToBeVisible(successfulLoginNoticeText, 3);
        assertThat(successfulLoginNoticeText.getText()).contains(username);
    }

    @Step("Assert login failed with username as {username}")
    public void assertErrorAfterIncorrectLoginContains(String username) {
        waitForElementToBeVisible(incorrectLoginMessageText, 3);
        assertThat(incorrectLoginMessageText.getText()).contains(username);
    }


}
