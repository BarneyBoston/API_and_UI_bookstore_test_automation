package app.bookstore.selenium.previewcartpage;

import app.bookstore.BookStoreBaseWebTest;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

@Epic("Preview Cart Page Tests")
public class PreviewCartPageTests extends BookStoreBaseWebTest {

    @Test(description = "Verify all elements of an empty preview cart are visible")
    public void empty_preview_cart_elements_test() {
        login()
                .goToPreviewCartPageWithCartButton()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test(description = "Start shopping button returns user to main page")
    public void should_start_shopping_return_user_to_main_page() {
        login()
                .goToPreviewCartPageWithCartButton()
                .clickStartShopping()
                .assertThatMainPageElementsAreVisible();
    }

    @Test(description = "Verify all elements of a filled preview cart are visible")
    public void preview_cart_with_item_elements_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertAllElementsOfFilledCartAreVisible();
    }

    @Test(description = "Increasing product quantity updates the product quantity value")
    public void should_increase_quantity_change_value_of_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .assertProductQuantityChangedTo("2");
    }

    @Test(description = "Reducing product quantity updates the product quantity value")
    public void should_reduce_quantity_change_value_of_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .reduceProductQuantityBy(1)
                .assertProductQuantityChangedTo("1");
    }

    @Test(description = "Reduce quantity button is disabled when product quantity is one")
    public void should_reduce_quantity_be_disabled_for_one_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertReduceQuantityButtonIsDisabled();
    }

    @Test(description = "Removing an item opens the empty preview cart page")
    public void should_remove_item_open_empty_preview_cart_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .removeItem()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test(description = "Going to checkout redirects to the checkout page")
    public void should_go_to_checkout_redirect_to_checkout_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .goToCheckout()
                .assertCheckoutTextIsDisplayed();
    }

    @Test(description = "View My Cart redirects to the cart page")
    public void should_view_my_cart_redirect_to_cart_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .viewMyCart()
                .assertCartTextIsDisplayed();
    }
}
