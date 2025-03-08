package app.bookstore.selenium.myaccountpage;

import app.bookstore.BookStoreBaseWebTest;
import org.testng.annotations.Test;

public class MyAccountPageTests extends BookStoreBaseWebTest {

    @Test
    public void should_all_elements_be_visible_test() {
        login()
                .goToMyAccountPage()
                .assertAllElementsAreVisible();
    }

    @Test
    public void should_correct_login_work_test() {
        login()
                .goToMyAccountPage()
                .loginAs("admin", "admin")
                .assertMessageAfterSuccessfulLoginContains("admin");
    }

    @Test
    public void should_incorrect_login_trigger_error_test() {
        login()
                .goToMyAccountPage()
                .loginAs("incorrectLogin", "incorrectPassword")
                .assertErrorAfterIncorrectLoginContains("Error: The username incorrectLogin is not registered on this site");
    }

    @Test
    public void should_lost_your_password_redirect_test() {
        login()
                .goToMyAccountPage()
                .lostYourPassword()
                .assertAllElementsAreDisplayed();
    }
}
