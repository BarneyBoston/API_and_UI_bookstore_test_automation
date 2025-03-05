package app.bookstore.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public MyAccountPage loginAsAdmin() {
        sendKeysToElement(usernameField, "admin");
        sendKeysToElement(passwordField, "admin");
        clickElement(loginButton);
        return this;
    }


}
