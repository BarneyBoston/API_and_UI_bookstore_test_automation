package app.bookstore.rest.customers;

import app.bookstore.BookStoreBaseRestTest;
import io.qameta.allure.Epic;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.UUID;

@Epic("Customers Controller Response Tests")
public class CustomersControllerResponseTests extends BookStoreBaseRestTest {

    @Test(priority = 1, description = "Verify GET customers response status is 200 OK")
    public void getCustomersResponseTest() {
        var response = controller.getCustomersResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 2, description = "Verify POST customers response status is 201 Created")
    public void postCustomersResponseTest() {
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        CustomersRequest request = CustomersRequest.builder()
                .email("test" + randomString +"@gmail.com")
                .firstName("test")
                .lastName("test")
                .build();
        var response = controller.postCustomersResponse(request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(201);
    }

    @Test(priority = 3, description = "Verify UPDATE customers response status is 200 OK")
    public void updateCustomersResponseTest() {
        var id = controller.getCustomers().get(0).getId().toString();
        var request = UpdateCustomersRequest.builder()
                .first_name("updatedTest")
                .build();
        var response = controller.updateCustomersResponse(id, request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 4, description = "Verify DELETE customers response status is 200 OK")
    public void deleteCustomersResponseTest() {
        var id = controller.getCustomers().get(0).getId().toString();

        var response = controller.deleteCustomersResponse(id, true);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

}
