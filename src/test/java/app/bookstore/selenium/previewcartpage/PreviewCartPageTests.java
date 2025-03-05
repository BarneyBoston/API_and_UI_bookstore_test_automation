package app.bookstore.selenium.previewcartpage;

import app.bookstore.BookStoreBaseWebTest;
import org.testng.annotations.Test;

public class PreviewCartPageTests extends BookStoreBaseWebTest {

    @Test
    public void empty_preview_cart_elements_test() {
        login()
                .goToPreviewCartPageWithCartButton()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test
    public void should_start_shopping_return_user_to_main_page() {
        login()
                .goToPreviewCartPageWithCartButton()
                .clickStartShopping()
                .assertThatMainPageElementsAreVisible();
    }

    @Test
    public void preview_cart_with_item_elements_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertAllElementsOfFilledCartAreVisible();
    }

    @Test
    public void should_increase_quantity_change_value_of_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .assertProductQuantityChangedTo("2");
    }

    @Test
    public void should_reduce_quantity_change_value_of_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .reduceProductQuantityBy(1)
                .assertProductQuantityChangedTo("1");
    }

    @Test
    public void should_reduce_quantity_be_disabled_for_one_product_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertReduceQuantityButtonIsDisabled();
    }

    @Test
    public void should_remove_item_open_empty_preview_cart_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .removeItem()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test
    public void should_go_to_checkout_redirect_to_checkout_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .goToCheckout()
                .assertCheckoutTextIsDisplayed();
    }

    @Test
    public void should_view_my_cart_redirect_to_cart_page_test() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .viewMyCart()
                .assertCartTextIsDisplayed();
    }
}
