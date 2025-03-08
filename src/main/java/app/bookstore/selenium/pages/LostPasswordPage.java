package app.bookstore.selenium.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("unused")
public class LostPasswordPage extends BasePage {
    public LostPasswordPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".entry-title")
    private WebElement entryTitleText;

    @FindBy(css = "#user_login")
    private WebElement usernameOrEmailField;

    @FindBy(css = "[value='Reset password']")
    private WebElement resetPasswordButton;

    @FindBy(css = ".woocommerce-error li")
    private WebElement errorMessageText;

    @FindBy(css = ".woocommerce-message")
    private WebElement successfulMessageText;

    @Step("Input username or email as {username}")
    public LostPasswordPage inputUsernameOrEmail(String username) {
        sendKeysToElement(usernameOrEmailField, username);
        return this;
    }

    @Step("Click reset password")
    public LostPasswordPage resetPassword() {
        clickElement(resetPasswordButton);
        return this;
    }

    @Step("Assert error message text is {text}")
    public void assertErrorMessageIs(String text) {
        waitForElementToBeVisible(errorMessageText, 3);
        assertThat(errorMessageText.getText()).contains(text);
    }

    @Step("Assert successful message text is {text}")
    public void assertSuccessfulMessageIs(String text) {
        waitForElementToBeVisible(successfulMessageText, 3);
        assertThat(successfulMessageText.getText()).contains(text);
    }

    @Step("Assert all elements of lost password page are displayed")
    public void assertAllElementsAreDisplayed() {
        waitForElementToBeVisible(entryTitleText, 3);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(entryTitleText.getText()).isEqualTo("Lost password").as("Lost Password text is not displayed");
            softly.assertThat(usernameOrEmailField.isDisplayed() && usernameOrEmailField.isEnabled()).isTrue().as("Username field is not displayed or enabled");
            softly.assertThat(resetPasswordButton.isDisplayed() && resetPasswordButton.isEnabled()).isTrue().as("Username field is not displayed or enabled");
        });
    }
}
