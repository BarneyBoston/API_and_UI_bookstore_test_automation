package app.bookstore.selenium.cartpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.BookStoreDB;
import app.bookstore.db.models.PostRecord;
import app.bookstore.rest.BookStoreController;
import app.bookstore.rest.coupon.CouponResponse;
import app.bookstore.rest.coupon.PostCouponRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CartPageTests extends BookStoreBaseWebTest {
    public BookStoreController controller;

    @BeforeClass
    public void createCouponWithApi() {
        controller = new BookStoreController();
        var request = PostCouponRequest.builder()
                .code("testCoupon")
                .discount_type("percent")
                .amount("5")
                .minimum_amount("1")
                .build();
        controller.postCoupon(request);
    }

    @Test
    public void should_cart_page_have_proper_elements_test() {
        login()
                .goToCartPage()
                .assertAllElementsAreDisplayed();
    }

    @Test
    public void should_product_name_match_chosen_product_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .assertProductNameMatchChosenProduct(bookName);
    }

    @Test
    public void should_multiple_product_names_match_chosen_products_test() {
        var bookNames = BookStoreDB.getDb().selectActiveProducts().stream().map(PostRecord::getName).toList();
        var bookName1 = bookNames.get(0);
        var bookName2 = bookNames.get(1);

        login()
                .addToCart(bookName1)
                .closePreviewCart()
                .addToCart(bookName2)
                .closePreviewCart()
                .getNavigationBar()
                .clickCartPageButton()
                .viewMyCart()
                .assertProductNamesMatchChosenProducts(List.of(bookName1, bookName2));
    }

    @Test
    public void should_update_cart_trigger_pop_up_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .increaseQuantityOfProductBy(0, 2)
                .updateCart()
                .assertMessageIs("Cart updated");
    }

    @Test
    public void should_update_cart_without_prior_updated_be_disabled_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .assertUpdateCartButtonIsDisabled();
    }

    @Test
    public void should_increase_quantity_for_one_product_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .increaseQuantityOfProductBy(0, 2)
                .updateCart()
                .assertQuantityIs(0, "3");
    }

    @Test
    public void should_increase_quantity_for_multiple_products_work_test() {
        var bookNames = BookStoreDB.getDb().selectActiveProducts().stream().map(PostRecord::getName).toList();
        var bookName1 = bookNames.get(0);
        var bookName2 = bookNames.get(1);

        login()
                .addToCart(bookName1)
                .closePreviewCart()
                .addToCart(bookName2)
                .closePreviewCart()
                .getNavigationBar()
                .clickCartPageButton()
                .viewMyCart()
                .increaseQuantityOfProductBy(0, 2)
                .increaseQuantityOfProductBy(1, 4)
                .updateCart()
                .assertQuantityIs(0, "3")
                .assertQuantityIs(1, "5");
    }

    @Test
    public void should_reduce_quantity_for_one_product_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .increaseQuantityOfProductBy(0, 5)
                .reduceQuantityOfProductBy(0, 2)
                .updateCart()
                .assertQuantityIs(0, "4");
    }

    @Test
    public void should_reduce_quantity_for_multiple_products_work_test() {
        var bookNames = BookStoreDB.getDb().selectActiveProducts().stream().map(PostRecord::getName).toList();
        var bookName1 = bookNames.get(0);
        var bookName2 = bookNames.get(1);

        login()
                .addToCart(bookName1)
                .closePreviewCart()
                .addToCart(bookName2)
                .closePreviewCart()
                .getNavigationBar()
                .clickCartPageButton()
                .viewMyCart()
                .increaseQuantityOfProductBy(0, 2)
                .reduceQuantityOfProductBy(0, 2)
                .increaseQuantityOfProductBy(1, 4)
                .reduceQuantityOfProductBy(1, 2)
                .updateCart()
                .assertQuantityIs(0, "1")
                .assertQuantityIs(1, "3");
    }

    @Test
    public void should_input_quantity_for_product_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .inputQuantityAs(0, "55")
                .updateCart()
                .assertQuantityIs(0, "55");
    }

    @Test
    public void should_update_quantity_update_subtotal_for_product_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .inputQuantityAs(0, "5")
                .updateCart()
                .assertSubTotalChangedToExpected(0);
    }

    @Test
    public void should_update_quantity_update_cart_total_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .inputQuantityAs(0, "5")
                .updateCart()
                .assertCartTotalsChangedAsExpected(0);
    }

    @Test
    public void should_correct_coupon_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .inputCouponCodeAs("testCoupon")
                .applyCoupon()
                .assertMessageIs("Coupon code applied successfully.");
    }

    @Test
    public void should_incorrect_coupon_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();
        var couponCode = "incorrectCoupon";

        login()
                .goToCartPageWithProductAs(bookName)
                .inputCouponCodeAs(couponCode)
                .applyCoupon()
                .assertErrorIs(String.format("Coupon \"%s\" does not exist!", couponCode));
    }

    @Test
    public void should_proceed_to_checkout_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToCartPageWithProductAs(bookName)
                .proceedToCheckout()
                .assertCheckoutTextIsDisplayed();
    }

    @AfterClass
    public void cleanUp() {
        var couponIds = controller.getCoupon().stream().map(CouponResponse::getId).toList();

        if (!couponIds.isEmpty()) {
            couponIds.forEach(coupon -> controller.deleteCouponResponse(coupon, true));
        }
    }
}
