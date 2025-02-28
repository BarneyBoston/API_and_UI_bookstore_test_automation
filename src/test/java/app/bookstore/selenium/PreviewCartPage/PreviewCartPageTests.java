package app.bookstore.selenium.PreviewCartPage;

import app.bookstore.BookStoreBaseWebTest;
import org.testng.annotations.Test;

public class PreviewCartPageTests extends BookStoreBaseWebTest {

    @Test
    public void emptyPreviewCartElementsTest() {
        login()
                .goToPreviewCartPageWithCartButton()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test
    public void shouldStartShoppingReturnUserToMainPage() {
        login()
                .goToPreviewCartPageWithCartButton()
                .clickStartShopping()
                .assertThatMainPageElementsAreVisible();
    }

    @Test
    public void previewCartWithItemElementsTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertAllElementsOfFilledCartAreVisible();
    }

    @Test
    public void shouldIncreaseQuantityChangeValueOfProductTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .assertProductQuantityChangedTo("2");
    }

    @Test
    public void shouldReduceQuantityChangeValueOfProductTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .increaseProductQuantityBy(1)
                .reduceProductQuantityBy(1)
                .assertProductQuantityChangedTo("1");
    }

    @Test
    public void shouldReduceQuantityBeDisabledForOneProductTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .assertReduceQuantityButtonIsDisabled();
    }

    @Test
    public void shouldRemoveItemOpenEmptyPreviewCartPageTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .removeItem()
                .assertAllElementsOfEmptyCartAreVisible();
    }

    @Test
    public void shouldGoToCheckoutRedirectToCheckoutPageTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .goToCheckout()
                .assertCheckoutTextIsDisplayed();
    }

    @Test
    public void shouldViewMyCartRedirectToCartPageTest() {
        login()
                .goToPreviewCartPageWithAddToCart()
                .viewMyCart()
                .assertCartTextIsDisplayed();
    }
}
