package app.bookstore.rest.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private String type;
    private String regular_price;
    private String description;
    private String short_description;
}
