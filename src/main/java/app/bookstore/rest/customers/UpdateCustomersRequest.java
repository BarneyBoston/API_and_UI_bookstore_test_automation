package app.bookstore.rest.customers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCustomersRequest {
    private String first_name;
}
