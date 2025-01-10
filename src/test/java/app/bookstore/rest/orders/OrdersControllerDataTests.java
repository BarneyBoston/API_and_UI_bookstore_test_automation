package app.bookstore.rest.orders;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.models.PostRecord;
import app.bookstore.rest.utils.DataAssertions;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class OrdersControllerDataTests extends BookStoreBaseRestTest {

    @Test()
    public void ordersDataVsDbTest() {
        var request = PostOrdersRequest.builder()
                .build();
        int randomTimes = ThreadLocalRandom.current().nextInt(1, 11);
        for (int i = 0; i < randomTimes; i++) {
            controller.postOrdersResponse(request);
        }
        var idList = controller.getOrders().stream().map(OrdersResponse::getId).toList();
        var dbList = db.selectOrders().stream().map(PostRecord::getId).toList();

        DataAssertions.verifyThatAPIvsDBListContains(idList, dbList);
    }
}