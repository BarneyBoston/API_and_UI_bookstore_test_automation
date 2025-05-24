package app.bookstore.selenium.myaccountpage;

import app.bookstore.BookStoreBaseWebTest;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

@Epic("My Account Page Tests")
public class MyAccountPageTests extends BookStoreBaseWebTest {

    @Test(description = "Verify all elements on My Account page are visible")
    public void should_all_elements_be_visible_test() {
        login()
                .goToMyAccountPage()
                .assertAllElementsAreVisible();
    }

    @Test(description = "Verify correct login works and success message contains username")
    public void should_correct_login_work_test() {
        login()
                .goToMyAccountPage()
                .loginAs("admin", "admin")
                .assertMessageAfterSuccessfulLoginContains("admin");
    }

    @Test(description = "Verify incorrect login triggers appropriate error message")
    public void should_incorrect_login_trigger_error_test() {
        login()
                .goToMyAccountPage()
                .loginAs("incorrectLogin", "incorrectPassword")
                .assertErrorAfterIncorrectLoginContains("Error: The username incorrectLogin is not registered on this site");
    }

    @Test(description = "Verify 'Lost your password' link redirects and displays expected elements")
    public void should_lost_your_password_redirect_test() {
        login()
                .goToMyAccountPage()
                .lostYourPassword()
                .assertAllElementsAreDisplayed();
    }
}
