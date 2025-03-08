package app.bookstore.selenium.checkoutpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.rest.BookStoreController;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutPageTests extends BookStoreBaseWebTest {

    BookStoreController controller;

    @BeforeClass
    private void getApiData() {
        controller = new BookStoreController();
    }

    @Test
    public void should_all_elements_be_displayed_test() {
        login()
                .goToCheckOutPage()
                .assertAllElementsAreDisplayed();
    }

    @Test
    public void should_click_here_to_login_expand_login_window_test() {
        login()
                .goToCheckOutPage()
                .clickHereToLogin()
                .assertLoginWindowIsExpanded();
    }

    @Test
    public void should_click_here_to_enter_your_code_expand_coupon_window_test() {
        login()
                .goToCheckOutPage()
                .clickHereToEnterYourCode()
                .assertCouponWindowIsExpanded();
    }

    @Test
    public void should_place_order_without_required_fields_fail_test() {
        login()
                .goToCheckOutPage()
                .placeOrder()
                .assertCardErrorMessageIs("The card number is incomplete.");
    }

    @Test
    public void should_place_order_with_card_details_point_to_other_required_fields_test() {
        String expectedErrorMessage = """
                Billing First name is a required field.
                Billing Last name is a required field.
                Billing Street address is a required field.
                Billing Postcode / ZIP is not a valid postcode / ZIP.
                Billing Town / City is a required field.
                Billing Phone is a required field.
                Billing Email address is a required field.
                """.trim();
        login()
                .goToCheckOutPage()
                .inputCardNumber("4242424242424242")
                .inputExpiryDate("12/58")
                .inputCvcNumber("123")
                .placeOrder()
                .assertDetailsErrorMessageIs(expectedErrorMessage);
    }

    @Test
    public void should_place_correct_order_work_test() {
        var dataSource = controller.getOrders().get(0).getBilling();

        login()
                .goToCheckOutPage()
                .inputFirstNameAs(dataSource.getFirst_name())
                .inputLastNameAs(dataSource.getLast_name())
                .inputHouseNumberAndStreetNameAs(dataSource.getAddress_1())
                .inputApartmentSuiteUnitAs(dataSource.getAddress_2())
                .inputPostcodeAs("12-123")
                .inputTownAs(dataSource.getCity())
                .inputPhoneAs(dataSource.getPhone())
                .inputEmailAs(dataSource.getEmail())
                .inputCardNumber("4242424242424242")
                .inputExpiryDate("12/58")
                .inputCvcNumber("123")
                .placeOrder()
                .assertOrderIsReceived();
    }
}
