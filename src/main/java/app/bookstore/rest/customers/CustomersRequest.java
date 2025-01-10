package app.bookstore.rest.customers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomersRequest {
    private String email;
    private String firstName;
    private String lastName;
}
