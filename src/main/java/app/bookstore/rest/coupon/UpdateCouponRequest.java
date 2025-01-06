package app.bookstore.rest.coupon;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCouponRequest {
    private String amount;
}
