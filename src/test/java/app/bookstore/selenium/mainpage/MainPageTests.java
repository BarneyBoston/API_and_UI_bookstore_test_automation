package app.bookstore.selenium.mainpage;

import app.bookstore.BookStoreBaseWebTest;
import org.testng.annotations.Test;

public class MainPageTests extends BookStoreBaseWebTest {

    @Test
    public void test() {
        login().goToCartPage();
    }
}
