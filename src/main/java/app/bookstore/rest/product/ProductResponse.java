package app.bookstore.rest.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    private int id;
    private String name;
    private double price;
}
