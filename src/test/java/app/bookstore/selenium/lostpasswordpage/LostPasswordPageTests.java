package app.bookstore.selenium.lostpasswordpage;

import app.bookstore.BookStoreBaseWebTest;
import org.testng.annotations.Test;

public class LostPasswordPageTests extends BookStoreBaseWebTest {

    @Test
    public void should_lost_password_page_have_all_expected_elements_test() {
        login()
                .goToLostPasswordPage()
                .assertAllElementsAreDisplayed();
    }

    @Test
    public void should_incorrect_username_or_email_fail_test() {
        login()
                .goToLostPasswordPage()
                .inputUsernameOrEmail("incorrectUsername")
                .resetPassword()
                .assertErrorMessageIs("Invalid username or email.");
    }

    @Test
    public void should_correct_username_or_email_work_test() {
        login()
                .goToLostPasswordPage()
                .inputUsernameOrEmail("admin")
                .resetPassword()
                .assertSuccessfulMessageIs("Password reset email has been sent.");
    }
}
