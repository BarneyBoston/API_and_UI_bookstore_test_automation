package app.bookstore.db;

import app.bookstore.db.models.PostRecord;
import app.bookstore.db.models.ProductRecord;
import app.bookstore.db.utils.BookStoreDBAccessDetails;
import app.bookstore.db.utils.DBClient;
import io.qameta.allure.Step;

import java.util.List;

public class BookStoreDB {

    private final DBClient database = new DBClient(BookStoreDBAccessDetails.getDbAccessDetails());

    @Step("SELECT products")
    public List<ProductRecord> selectProducts() {
        return database.getResultsForQuery("SELECT * FROM wp_wc_product_meta_lookup",
                ProductRecord.class);
    }

    @Step("SELECT posts")
    public List<PostRecord> selectPosts() {
        return database.getResultsForQuery("SELECT * FROM wp_posts",
                PostRecord.class);
    }

    @Step("SELECT ")
    public List<PostRecord> selectNameFromPosts(String name) {
        return database.getResultsForQuery("SELECT * FROM wp_posts WHERE post_title = \"" + name + "\"",
                PostRecord.class);
    }

}
