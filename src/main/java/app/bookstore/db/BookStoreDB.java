package app.bookstore.db;

import app.bookstore.db.models.ProductRecord;
import app.bookstore.db.utils.BookStoreDBAccessDetails;
import app.bookstore.db.utils.DBClient;

import java.util.List;

public class BookStoreDB {

    DBClient database = new DBClient(BookStoreDBAccessDetails.getDbAccessDetails());

    public List<ProductRecord> getProductIds() {
        return database.getResultsForQuery("SELECT * FROM wp_wc_product_meta_lookup",
                ProductRecord.class);
    }
}
