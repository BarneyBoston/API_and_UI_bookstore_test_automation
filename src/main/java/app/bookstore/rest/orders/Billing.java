package app.bookstore.rest.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Billing {
    private String first_name;
    private String last_name;
    private String company;
    private String address_1;
    private String address_2;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String email;
    private String phone;

    @JsonCreator
    @SuppressWarnings("unused")
    public static Billing create(
            @JsonProperty("first_name") String first_name,
            @JsonProperty("last_name") String last_name,
            @JsonProperty("company") String company,
            @JsonProperty("address_1") String address_1,
            @JsonProperty("address_2") String address_2,
            @JsonProperty("city") String city,
            @JsonProperty("state") String state,
            @JsonProperty("postcode") String postcode,
            @JsonProperty("country") String country,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone) {
        return Billing.builder()
                .first_name(first_name)
                .last_name(last_name)
                .company(company)
                .address_1(address_1)
                .address_2(address_2)
                .city(city)
                .state(state)
                .postcode(postcode)
                .country(country)
                .email(email)
                .phone(phone)
                .build();
    }
}
