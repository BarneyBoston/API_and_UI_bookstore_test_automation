package app.bookstore.rest.product;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ProductControllerResponseTests extends BookStoreBaseRestTest {

    @Test
    public void productResponseTest() {
        var response = controller.getProductsResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }
}
