package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.rest.BookStoreController;
import org.testng.annotations.BeforeMethod;

public class BookStoreBaseRestTest {

    public BookStoreController controller;

    @BeforeMethod
    public void setup() {
        controller = new BookStoreController();
        BookStoreDB.init();
    }
}
