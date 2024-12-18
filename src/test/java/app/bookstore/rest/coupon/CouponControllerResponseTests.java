package app.bookstore.rest.coupon;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class CouponControllerResponseTests extends BookStoreBaseRestTest {

    @Test
    public void couponTest() {
        var response = controller.getCouponResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }
}
