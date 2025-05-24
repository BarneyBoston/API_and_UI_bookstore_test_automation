package app.bookstore.selenium.lostpasswordpage;

import app.bookstore.BookStoreBaseWebTest;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

@Epic("Lost Password Page Tests")
public class LostPasswordPageTests extends BookStoreBaseWebTest {

    @Test(description = "Verify that Lost Password page displays all expected elements")
    public void should_lost_password_page_have_all_expected_elements_test() {
        login()
                .goToLostPasswordPage()
                .assertAllElementsAreDisplayed();
    }

    @Test(description = "Verify that submitting incorrect username or email shows error message")
    public void should_incorrect_username_or_email_fail_test() {
        login()
                .goToLostPasswordPage()
                .inputUsernameOrEmail("incorrectUsername")
                .resetPassword()
                .assertErrorMessageIs("Invalid username or email.");
    }

    @Test(description = "Verify that submitting correct username or email shows success message")
    public void should_correct_username_or_email_work_test() {
        login()
                .goToLostPasswordPage()
                .inputUsernameOrEmail("admin")
                .resetPassword()
                .assertSuccessfulMessageIs("Password reset email has been sent.");
    }
}
