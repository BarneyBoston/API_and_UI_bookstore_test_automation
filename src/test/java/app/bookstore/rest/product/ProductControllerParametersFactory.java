package app.bookstore.rest.product;

import app.bookstore.db.models.ProductRecord;
import app.bookstore.rest.utils.DataTestParameters;

public class ProductControllerParametersFactory {

    public DataTestParameters<ProductResponse, ProductRecord, Integer> responseId() {
        return new DataTestParameters<>(
                "productId",
                ProductResponse::getId,
                ProductRecord::getProductId
        );
    }

    public DataTestParameters<ProductResponse, ProductRecord, Double> minPrice() {
        return new DataTestParameters<>(
                "minPrice",
                ProductResponse::getPrice,
                ProductRecord::getMinPrice
        );
    }

    public DataTestParameters<ProductResponse, ProductRecord, Double> maxPrice() {
        return new DataTestParameters<>(
                "maxPrice",
                ProductResponse::getPrice,
                ProductRecord::getMaxPrice
        );
    }

}
