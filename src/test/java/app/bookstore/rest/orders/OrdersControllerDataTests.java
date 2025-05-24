package app.bookstore.rest.orders;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.BookStoreDB;
import app.bookstore.db.models.PostRecord;
import app.bookstore.rest.utils.DataAssertions;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

@Epic("Orders Controller Data Tests")
public class OrdersControllerDataTests extends BookStoreBaseRestTest {

    @Test(description = "Verify that list of orders from API matches orders from the database")
    public void ordersDataVsDbTest() {
        var request = PostOrdersRequest.builder()
                .billing(Billing.builder()
                        .first_name("testName")
                        .last_name("testSurname")
                        .company("testCompany")
                        .address_1("testAddress1")
                        .address_2("testAddress2")
                        .city("testCity")
                        .state("testState")
                        .postcode("12-123")
                        .country("testCountry")
                        .email("testEmail@example.com")
                        .phone("123456789")
                        .build())
                .build();
        int randomTimes = ThreadLocalRandom.current().nextInt(1, 11);
        for (int i = 0; i < randomTimes; i++) {
            controller.postOrdersResponse(request);
        }
        var idList = controller.getOrders().stream().map(OrdersResponse::getId).toList();
        var dbList = BookStoreDB.getDb().selectOrders().stream().map(PostRecord::getId).toList();

        DataAssertions.verifyThatAPIvsDBListContains(idList, dbList);
    }
}
