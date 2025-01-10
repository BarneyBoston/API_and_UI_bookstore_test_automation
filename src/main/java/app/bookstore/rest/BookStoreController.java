package app.bookstore.rest;

import app.bookstore.dto.Config;
import app.bookstore.rest.coupon.CouponResponse;
import app.bookstore.rest.coupon.PostCouponRequest;
import app.bookstore.rest.coupon.UpdateCouponRequest;
import app.bookstore.rest.customers.CustomersRequest;
import app.bookstore.rest.customers.CustomersResponse;
import app.bookstore.rest.customers.UpdateCustomersRequest;
import app.bookstore.rest.orders.OrdersResponse;
import app.bookstore.rest.orders.PostOrdersRequest;
import app.bookstore.rest.orders.PutOrdersRequest;
import app.bookstore.rest.product.ProductRequest;
import app.bookstore.rest.product.ProductResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.util.List;

public class BookStoreController {
    private final String consumerKey;
    private final String consumerSecret;
    private final String baseUri;

    public BookStoreController() {
        Config config = Config.getInstance();
        this.consumerKey = config.getConsumerKey();
        this.consumerSecret = config.getConsumerSecret();
        this.baseUri = config.getBaseUri();
    }

    private static final String PRODUCTS = "/products";
    private static final String COUPONS = "/coupons";
    private static final String CUSTOMERS = "/customers";
    private static final String ORDERS = "/orders";


    public RequestSpecification givenGet(String endpoint) {
        try {
            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
            HttpGet authRequest = new HttpGet(baseUri + endpoint);
            consumer.sign(authRequest);
            String authHeader = authRequest.getFirstHeader("Authorization").getValue();

            return RestAssured
                    .given()
                    .header("Authorization", authHeader)
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } catch (Exception e) {
            throw new RuntimeException("Error signing request or preparing request specification", e);
        }
    }

    public RequestSpecification givenPost(String endpoint) {
        try {
            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
            HttpPost authRequest = new HttpPost(baseUri + endpoint);
            consumer.sign(authRequest);
            String authHeader = authRequest.getFirstHeader("Authorization").getValue();

            return RestAssured
                    .given()
                    .header("Authorization", authHeader)
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } catch (Exception e) {
            throw new RuntimeException("Error signing request or preparing request specification", e);
        }
    }

    public RequestSpecification givenPut(String endpoint) {
        try {
            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
            HttpPut authRequest = new HttpPut(baseUri + endpoint);
            consumer.sign(authRequest);
            String authHeader = authRequest.getFirstHeader("Authorization").getValue();

            return RestAssured
                    .given()
                    .header("Authorization", authHeader)
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } catch (Exception e) {
            throw new RuntimeException("Error signing request or preparing request specification", e);
        }
    }

    public RequestSpecification givenDelete(String endpoint) {
        try {
            OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
            HttpDelete authRequest = new HttpDelete(baseUri + endpoint);
            consumer.sign(authRequest);
            String authHeader = authRequest.getFirstHeader("Authorization").getValue();

            return RestAssured
                    .given()
                    .header("Authorization", authHeader)
                    .baseUri(baseUri)
                    .contentType(ContentType.JSON)
                    .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } catch (Exception e) {
            throw new RuntimeException("Error signing request or preparing request specification", e);
        }
    }

    @Step("GET " + PRODUCTS)
    public Response getProductsResponse() {
        return givenGet(PRODUCTS)
                .get(PRODUCTS);
    }

    public List<ProductResponse> getProducts() {
        return getProductsResponse()
                .then()
                .extract()
                .jsonPath()
                .getList("$", ProductResponse.class);
    }

    @Step("POST " + PRODUCTS)
    public Response postProductsResponse(ProductRequest request) {
        return givenPost(PRODUCTS)
                .body(request)
                .post(PRODUCTS);
    }

    public ProductResponse postProducts(ProductRequest request) {
        return postProductsResponse(request)
                .then()
                .extract()
                .as(ProductResponse.class);
    }

    @Step("PUT " + PRODUCTS)
    public Response updateProductsResponse(String id, ProductRequest request) {
        return givenPut(PRODUCTS + "/" + id)
                .body(request)
                .put(PRODUCTS + "/" + id);
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        return updateProductsResponse(id, request)
                .then()
                .extract()
                .as(ProductResponse.class);
    }

    @Step("DELETE " + PRODUCTS)
    public Response deleteProductsResponse(String id) {
        return givenDelete(PRODUCTS + "/" + id)
                .delete(PRODUCTS + "/" + id);
    }

    @Step("GET " + COUPONS)
    public Response getCouponResponse() {
        return givenGet(COUPONS)
                .get(COUPONS);
    }

    public List<CouponResponse> getCoupon() {
        return getCouponResponse()
                .then()
                .extract()
                .jsonPath()
                .getList("$", CouponResponse.class);
    }

    @Step("POST " + COUPONS)
    public Response postCouponResponse(PostCouponRequest request) {
        return givenPost(COUPONS)
                .body(request)
                .post(COUPONS);
    }

    public CouponResponse postCoupon(PostCouponRequest request) {
        return givenPost(COUPONS)
                .body(request)
                .post(COUPONS)
                .then()
                .extract()
                .as(CouponResponse.class);
    }

    @Step("PUT " + COUPONS)
    public Response updateCouponResponse(String id, UpdateCouponRequest request) {
        return givenPut(COUPONS + "/" + id)
                .body(request)
                .put(COUPONS + "/" + id);
    }

    public CouponResponse updateCoupon(String id, UpdateCouponRequest request) {
        return givenPut(COUPONS + "/" + id)
                .body(request)
                .put(COUPONS + "/" + id)
                .then()
                .extract()
                .as(CouponResponse.class);
    }

    @Step("DELETE " + COUPONS)
    public Response deleteCouponResponse(String id, Boolean force) {
        return givenDelete(COUPONS + "/" + id + "?force=true")
                .queryParam("force", force)
                .delete(COUPONS + "/" + id);
    }

    @Step("GET " + CUSTOMERS)
    public Response getCustomersResponse() {
        return givenGet(CUSTOMERS)
                .get(CUSTOMERS);
    }

    public List<CustomersResponse> getCustomers() {
        return getCustomersResponse()
                .then()
                .extract()
                .jsonPath()
                .getList(".", CustomersResponse.class);
    }

    @Step("POST " + CUSTOMERS)
    public Response postCustomersResponse(CustomersRequest request) {
        return givenPost(CUSTOMERS)
                .body(request)
                .post(CUSTOMERS);
    }

    public CustomersResponse postCustomers(CustomersRequest request) {
        return postCustomersResponse(request)
                .then()
                .extract()
                .as(CustomersResponse.class);
    }

    @Step("PUT " + CUSTOMERS)
    public Response updateCustomersResponse(String id, UpdateCustomersRequest request) {
        return givenPut(CUSTOMERS + "/" + id)
                .body(request)
                .put(CUSTOMERS + "/" + id);
    }

    @Step("DELETE " + CUSTOMERS)
    public Response deleteCustomersResponse(String id, Boolean force) {
        return givenDelete(CUSTOMERS + "/" + id + "?force=true")
                .queryParam("force", force)
                .delete(CUSTOMERS + "/" + id);
    }

    @Step("GET " + ORDERS)
    public Response getOrdersResponse() {
        return givenGet(ORDERS)
                .get(ORDERS);
    }

    public List<OrdersResponse> getOrders() {
        return getOrdersResponse()
                .then()
                .extract()
                .jsonPath()
                .getList(".", OrdersResponse.class);
    }

    @Step("POST " + ORDERS)
    public Response postOrdersResponse(PostOrdersRequest request) {
        return givenPost(ORDERS)
                .body(request)
                .post(ORDERS);
    }

    @Step("PUT " + ORDERS)
    public Response updateOrdersResponse(String id, PutOrdersRequest request) {
        return givenPut(ORDERS + "/" + id)
                .body(request)
                .put(ORDERS + "/" + id);
    }

    @Step("DELETE " + ORDERS)
    public Response deleteOrdersResponse(String id, Boolean force) {
        return givenDelete(ORDERS + "/" + id + "?force=true")
                .queryParam("force", force)
                .delete(ORDERS + "/" + id);
    }
}
