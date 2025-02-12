package app.bookstore.selenium.pages;

import lombok.Getter;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class NavigationBar {

    @FindBy(css = ".wc-block-mini-cart__button")
    private WebElement cartPageButton;

    @FindBy(xpath = "//a[text()='My account']")
    private WebElement myAccountPageButton;

    @FindBy(xpath = "//a[text()='Wishlist']")
    private WebElement wishlistPageButton;

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
