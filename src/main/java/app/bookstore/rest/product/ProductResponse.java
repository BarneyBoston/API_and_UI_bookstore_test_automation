package app.bookstore.rest.product;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    private Integer id;
    private String name;
    private double price;
    private String status;
    private String slug;
    @JsonAlias("date_created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateCreated;
}
