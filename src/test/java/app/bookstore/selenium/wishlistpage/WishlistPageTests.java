package app.bookstore.selenium.wishlistpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.BookStoreDB;
import org.testng.annotations.Test;

public class WishlistPageTests extends BookStoreBaseWebTest {

    @Test
    public void should_empty_wishlist_have_proper_elements_test() {
        login()
                .goToWishlistPage()
                .assertAllElementsOfEmptyWishlistAreDisplayed();
    }

    @Test
    public void should_added_product_clicked_redirect_to_product_page_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addToWishList()
                .viewWishList()
                .clickChosenProduct(bookName)
                .assertAllElementsAreDisplayed();
    }

    @Test
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
