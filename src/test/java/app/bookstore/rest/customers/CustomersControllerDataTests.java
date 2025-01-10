package app.bookstore.rest.customers;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.UUID;

public class CustomersControllerDataTests extends BookStoreBaseRestTest {

    @Test()
    public void postCustomersDataTest() {
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        CustomersRequest request = CustomersRequest.builder()
                .email("test" + randomString + "@gmail.com")
                .firstName("testData" + randomString)
                .lastName("testData" + randomString)
                .build();
        var list = controller.postCustomers(request);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(list)
                .describedAs("Email and username of posted customer doesn't match posting")
                .extracting("email", "username")
                .containsExactly(("test" + randomString + "@gmail.com"), ("test" + randomString));
        softly.assertAll();
    }

}
