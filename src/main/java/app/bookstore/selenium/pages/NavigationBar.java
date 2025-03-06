package app.bookstore.selenium.pages;

import lombok.Getter;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
@SuppressWarnings("unused")
public class NavigationBar extends BasePage {

    @FindBy(css = ".wc-block-mini-cart__button")
    private WebElement cartPageButton;

    @FindBy(xpath = "//a[text()='My account']")
    private WebElement myAccountPageButton;

    @FindBy(xpath = "//a[text()='Wishlist']")
    private WebElement wishlistPageButton;

    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public WishlistPage clickWishlistPageButton() {
        clickElement(wishlistPageButton);
        return new WishlistPage(driver);
    }

    public PreviewCartPage clickCartPageButton() {
        clickElement(cartPageButton);
        return new PreviewCartPage(driver);
    }

    public void assertAllNavigationBarElementsVisible() {
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(cartPageButton.isDisplayed()).isTrue().as("Cart page button should be visible");
            softly.assertThat(myAccountPageButton.isDisplayed()).isTrue().as("My account page button should be visible");
            softly.assertThat(wishlistPageButton.isDisplayed()).isTrue().as("Wishlist page button should be visible");
        });
    }

    public void assertTwoNavigationBarElementsVisible() {
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(myAccountPageButton.isDisplayed()).isTrue().as("My account page button should be visible");
            softly.assertThat(wishlistPageButton.isDisplayed()).isTrue().as("Wishlist page button should be visible");
        });
    }

}
