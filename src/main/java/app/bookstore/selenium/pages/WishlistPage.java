package app.bookstore.selenium.pages;

import app.bookstore.selenium.pages.productpage.ProductPage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("unused")
public class WishlistPage extends BasePage {
    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".wishlist-title")
    private WebElement wishlistTitle;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = "tr[id] .product-name")
    private List<WebElement> productNames;

    @FindBy(css = ".wishlist-empty")
    private WebElement emptyWishlistText;

    private WebElement getProductName(String productName) {
        return driver.findElement(By.xpath(String.format("//tr[@id]//td[contains(@class, 'product-name')]//a[contains(text(), '%s')]", productName)));
    }

    private WebElement getAddToCartByProduct(String product) {
        return driver.findElement(By.cssSelector(String.format("a[aria-label='Add “%s” to your cart']", product)));
    }

    public ProductPage clickChosenProduct(String productName) {
        clickElement(getProductName(productName));
        return new ProductPage(driver);
    }

    public PreviewCartPage clickAddToCartByProduct(String productName) {
        clickElement(getAddToCartByProduct(productName));
        return new PreviewCartPage(driver);
    }

    public void assertProductIsAddedToWishList(String name) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(wishlistTitle.getText()).isEqualTo("My wishlist");
            softly.assertThat(productNames.get(0).getText()).isEqualTo(name);
        });
    }

    public void assertNoProductsAreAddedToWishList() {
        assertThat(emptyWishlistText.getText()).isEqualTo("No products added to the wishlist");
    }

    public void assertAllElementsOfEmptyWishlistAreDisplayed() {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(wishlistTitle.getText()).isEqualTo("My wishlist");
            softly.assertThat(emptyWishlistText.getText()).isEqualTo("No products added to the wishlist");
        });
    }
}
