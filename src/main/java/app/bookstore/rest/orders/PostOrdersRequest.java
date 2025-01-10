package app.bookstore.rest.orders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PostOrdersRequest {
    private String payment_method;
    private String payment_method_title;
    private String set_paid;
}
