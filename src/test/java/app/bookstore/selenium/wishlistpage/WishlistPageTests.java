package app.bookstore.selenium.wishlistpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.BookStoreDB;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

@Epic("Wishlist Page Tests")
public class WishlistPageTests extends BookStoreBaseWebTest {

    @Test(description = "Verify that empty wishlist displays all expected elements")
    public void should_empty_wishlist_have_proper_elements_test() {
        login()
                .goToWishlistPage()
                .assertAllElementsOfEmptyWishlistAreDisplayed();
    }

    @Test(description = "Verify that clicking on added product in wishlist redirects to product page")
    public void should_added_product_clicked_redirect_to_product_page_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addToWishList()
                .viewWishList()
                .clickChosenProduct(bookName)
                .assertAllElementsAreDisplayed();
    }

    @Test(description = "Verify that adding a product from wishlist to cart works correctly")
    public void should_add_to_cart_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addToWishList()
                .viewWishList()
                .clickAddToCartByProduct(bookName)
                .viewMyCart()
                .assertProductNameMatchChosenProduct(bookName);
    }
}
