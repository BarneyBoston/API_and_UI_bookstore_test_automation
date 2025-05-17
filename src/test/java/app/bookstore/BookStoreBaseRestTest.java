package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.rest.BookStoreController;
import org.testng.annotations.BeforeClass;

public class BookStoreBaseRestTest {

    public BookStoreController controller;

    @BeforeClass
    public void setup() {
        controller = new BookStoreController();
        BookStoreDB.init();
    }
}
