package app.bookstore.selenium.productpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.BookStoreDB;
import app.bookstore.rest.BookStoreController;
import app.bookstore.rest.product.ProductReviewResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductPageTests extends BookStoreBaseWebTest {
    public BookStoreController controller;

    @BeforeClass
    public void setUpController() {
        controller = new BookStoreController();
    }

    @Test
    public void should_product_page_has_all_elements_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .assertAllElementsAreDisplayed();
    }

    @Test
    public void should_verify_product_title_after_navigation_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .assertThatProductTextIs(bookName);
    }

    @Test
    public void should_add_to_cart_trigger_notification_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addProductToCart()
                .assertViewCartPopUpIsDisplayedAndTextIs(bookName);
    }

    @Test
    public void should_go_to_view_cart_from_notification_redirect_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addProductToCart()
                .viewCart()
                .assertCartTextIsDisplayed();
    }

    @Test
    public void should_input_quantity_as_update_value_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .inputQuantityAs("5")
                .addProductToCart()
                .assertQuantityIs("5");
    }

    @Test
    public void should_increase_quantity_update_value_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .increaseQuantityBy(3)
                .addProductToCart()
                .assertQuantityIs("4");
    }

    @Test
    public void should_decrease_quantity_update_value_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .inputQuantityAs("5")
                .decreaseQuantityBy(3)
                .addProductToCart()
                .assertQuantityIs("2");
    }

    @Test
    public void should_add_to_wish_list_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addToWishList()
                .viewWishList()
                .assertProductIsAddedToWishList(bookName);
    }

    @Test
    public void should_remove_from_wish_list_work_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .addToWishList()
                .removeFromWishList()
                .getNavigationBar()
                .clickWishlistPageButton()
                .assertNoProductsAreAddedToWishList();
    }

    @Test
    public void should_description_tab_be_populated_with_text_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .assertDescriptionTabTextIsDisplayed();
    }

    @Test
    public void should_contents_tab_content_be_displayed_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .clickContentsTab()
                .assertContextTabIsDisplayed();
    }

    @Test
    public void should_review_tab_display_all_elements_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .assertAllElementsAreDisplayed();
    }

    @Test
    public void should_review_tab_empty_submit_trigger_pop_up_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .clickSubmitButton()
                .assertPleaseSelectRatingAlertIsDisplayed();
    }

    @Test
    public void should_review_tab_with_rating_submit_redirect_to_error_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .chooseRatingAs(3)
                .clickSubmitButton()
                .assertErrorTextIs("Error: Please fill the required fields.");
    }

    @Test
    public void should_review_tab_with_rating_submit_and_back_redirect_to_description_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .chooseRatingAs(3)
                .clickSubmitButton()
                .clickBackFromErrorButton()
                .assertDescriptionTabTextIsDisplayed();
    }

    @Test
    public void should_review_tab_without_name_submit_popup_proper_error_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .chooseRatingAs(3)
                .inputEmailAs("testUser")
                .clickSubmitButton()
                .assertErrorTextIs("Error: Please fill the required fields.");
    }

    @Test
    public void should_review_tab_with_incorrect_email_submit_popup_proper_error_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .chooseRatingAs(3)
                .inputReviewAs("Great book")
                .inputNameAs("testUser")
                .inputEmailAs("testUser")
                .clickSubmitButton()
                .assertErrorTextIs("Error: Please enter a valid email address.");
    }

    @Test
    public void should_review_tab_with_correct_details_submitted_add_review_test() {
        var bookName = BookStoreDB.getDb().selectRandomActiveProduct().getName();

        login()
                .goToProductPage(bookName)
                .goToReviewTab()
                .chooseRatingAs(3)
                .inputReviewAs("Great book" + ((int) (Math.random() * 900) + 100))
                .inputNameAs("testUser")
                .inputEmailAs("testUser@test.pl")
                .clickSubmitButton()
                .assertReviewIsAwaitingApproval();
    }

    @AfterMethod
    public void cleanUp() {
        var reviewIds = controller.getReviews().stream().map(ProductReviewResponse::getId).toList();

        if (!reviewIds.isEmpty()) {
            reviewIds.forEach(reviewId -> controller.deleteReviews(reviewId, true));
        }
    }

}
