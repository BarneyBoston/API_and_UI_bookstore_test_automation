package app.bookstore.rest.coupon;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Random;

public class CouponControllerResponseTests extends BookStoreBaseRestTest {

    @Test(priority = 1)
    public void postCouponResponseTest() {
        var request = PostCouponRequest.builder()
                .code(String.valueOf(Math.abs(new Random().nextInt())))
                .discount_type("percent")
                .amount(String.valueOf(Math.abs(new Random().nextInt())))
                .minimum_amount("1")
                .build();
        var response = controller.postCouponResponse(request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(201);
    }

    @Test(priority = 2)
    public void getCouponResponseTest() {
        var response = controller.getCouponResponse();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 3)
    public void updateCouponResponseTest() {
        var couponId = controller.getCoupon().stream().map(CouponResponse::getId).toList().get(0);

        var request = UpdateCouponRequest.builder()
                .amount("5")
                .build();

        var response = controller.updateCouponResponse(couponId, request);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test(priority = 4)
    public void deleteCouponResponseTest() {
        var couponId = controller.getCoupon().stream().map(CouponResponse::getId).toList().get(0);

        var response = controller.deleteCouponResponse(couponId, true);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @AfterClass
    public void cleanUp() {
        var couponIds = controller.getCoupon().stream().map(CouponResponse::getId).toList();

        if (!couponIds.isEmpty()){
            couponIds.forEach(coupon -> controller.deleteCouponResponse(coupon, true));
        }
    }
}
