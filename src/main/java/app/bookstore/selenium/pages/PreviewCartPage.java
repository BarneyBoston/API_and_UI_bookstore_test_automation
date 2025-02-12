package app.bookstore.selenium.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class PreviewCartPage extends BasePage {
    public PreviewCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[text()='View my cart']")
    private WebElement viewMyCartButton;

    @FindBy(xpath = "//*[text()='Go to checkout']")
    private WebElement goToCheckoutButton;


}
