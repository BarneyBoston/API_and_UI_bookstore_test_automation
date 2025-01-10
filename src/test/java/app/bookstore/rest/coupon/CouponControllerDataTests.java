package app.bookstore.rest.coupon;

import app.bookstore.BookStoreBaseRestTest;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Random;

public class CouponControllerDataTests extends BookStoreBaseRestTest {

    @Test(priority = 1)
    public void postCouponDataTest() {
        var number = String.valueOf(Math.abs(new Random().nextInt()));
        var request = PostCouponRequest.builder()
                .code(number)
                .discount_type("percent")
                .amount("5")
                .minimum_amount("1")
                .build();
        var response = controller.postCoupon(request);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response)
                .describedAs("Code, amount and status of posted coupon doesn't match posting")
                .extracting("code", "amount", "status")
                .containsExactly(number, "5.00", "publish");
        softly.assertAll();
    }

    @Test(priority = 2)
    public void getCouponDataTest() {
        var response = controller.getCoupon();

        Assertions.assertThat(response)
                .describedAs("Response shouldn't be empty")
                .isNotEmpty();
    }

    @Test(priority = 3)
    public void updateCouponDataTest() {
        var couponId = controller.getCoupon().stream().map(CouponResponse::getId).toList().get(0);

        var request = UpdateCouponRequest.builder()
                .amount("10")
                .build();

        var response = controller.updateCoupon(couponId, request);

        Assertions.assertThat(response.getAmount())
                .describedAs("Validate if amount was updated")
                .isEqualTo("10.00");
    }

    @Test(priority = 4)
    public void deleteCouponDataTest() {
        var couponId = controller.getCoupon().stream().map(CouponResponse::getId).toList().get(0);

        controller.deleteCouponResponse(couponId, true);
        var response = controller.getCoupon();

        Assertions.assertThat(response).isEmpty();
    }

    @AfterClass
    public void cleanUp() {
        var couponIds = controller.getCoupon().stream().map(CouponResponse::getId).toList();

        if (!couponIds.isEmpty()) {
            couponIds.forEach(coupon -> controller.deleteCouponResponse(coupon, true));
        }
    }
}
