package app.bookstore.rest.product;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.models.ProductRecord;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ProductControllerResponseTests extends BookStoreBaseRestTest {

    @Test
    public void getProductResponseTest() {
        var response = controller.getProductsResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test
    public void postProductResponseTest() {
        var request = ProductRequest.builder()
                .build();
        var response = controller.postProductsResponse(request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(201);
    }

    @Test
    public void updateProductResponseTest() {
        var idFromDb = db.selectProducts().stream()
                .map(ProductRecord::getProductId)
                .toList()
                .get(0);

        var request = ProductRequest.builder()
                .regular_price("12.00")
                .build();

        var response = controller.updateProductsResponse(idFromDb.toString(), request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test
    public void deleteProductResponseTest() {
        var request = ProductRequest.builder()
                .build();

        var id = controller.postProducts(request).getId();

        var response = controller.deleteProductsResponse(id.toString());

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }
}
