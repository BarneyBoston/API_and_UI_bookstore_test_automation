package app.bookstore.rest.orders;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;

@Epic("Orders Controller Response Tests")
public class OrdersControllerResponseTests extends BookStoreBaseRestTest {

    @Test(priority = 1, description = "Verify that POST /orders returns status code 201")
    public void postOrdersResponseTest() {
        var request = PostOrdersRequest.builder()
                .build();
        var response = controller.postOrdersResponse(request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(201);
    }

    @Test(priority = 2, description = "Verify that GET /orders returns status code 200")
    public void getOrdersResponseTest() {
        var response = controller.getOrdersResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 3, description = "Verify that PUT /orders/{id} returns status code 200")
    public void updateOrdersResponseTest() {
        var id = controller.getOrders().stream().map(OrdersResponse::getId).toList().get(0);

        var request = PutOrdersRequest.builder()
                .build();
        var response = controller.updateOrdersResponse(id.toString(), request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 4, description = "Verify that DELETE /orders/{id} returns status code 200")
    public void deleteOrdersResponseTest() {
        var id = controller.getOrders().stream().map(OrdersResponse::getId).toList().get(0);

        var response = controller.deleteOrdersResponse(id.toString(), true);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @AfterClass
    public void cleanUp() {
        var orderIds = controller.getOrders().stream().map(OrdersResponse::getId).toList();

        if (!orderIds.isEmpty()) {
            orderIds.forEach(order -> controller.deleteOrdersResponse(order.toString(), true));
        }
    }
}
