package app.bookstore;

import app.bookstore.db.BookStoreDB;
import app.bookstore.rest.BookStoreController;
import org.testng.annotations.BeforeClass;

public class BookStoreBaseRestTest {

    public BookStoreController controller;
    public BookStoreDB db;

    @BeforeClass
    public void setup() {
        controller = new BookStoreController();
        db = new BookStoreDB();
    }
}
