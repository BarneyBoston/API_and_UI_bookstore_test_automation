package app.bookstore.selenium.pages;

import org.assertj.core.api.SoftAssertions;
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

    public void assertProductIsAddedToWishList(String name) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(wishlistTitle.getText()).isEqualTo("My wishlist");
            softly.assertThat(productNames.get(0).getText()).isEqualTo(name);
        });
    }

    public void assertNoProductsAreAddedToWishList() {
        assertThat(emptyWishlistText.getText()).isEqualTo("No products added to the wishlist");
    }
}
