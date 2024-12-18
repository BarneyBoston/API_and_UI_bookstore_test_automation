package app.bookstore.rest.product;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.models.ProductRecord;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.testng.annotations.Test;

public class ProductControllerDataTests extends BookStoreBaseRestTest {

    @Test
    public void productIdTest() {
        var response = controller.getProducts();
        var resources = db.getProductIds();

        AssertionsForInterfaceTypes
                .assertThat(response.stream().map(ProductResponse::getId).toList())
                .containsExactlyInAnyOrderElementsOf(resources.stream().map(ProductRecord::getProductId).toList())
                .hasSameSizeAs(resources.stream().map(ProductRecord::getProductId).toList());
    }
}
