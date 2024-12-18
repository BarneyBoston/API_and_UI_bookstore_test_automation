package app.bookstore.rest;

import app.bookstore.dto.Config;
import app.bookstore.rest.product.ProductResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.methods.HttpGet;

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


    public RequestSpecification given(String endpoint) {
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

    public Response getProductsResponse() {
        return given(PRODUCTS)
                .get(PRODUCTS);
    }

    public List<ProductResponse> getProducts() {
        return given(PRODUCTS)
                .get(PRODUCTS)
                .then()
                .extract()
                .jsonPath()
                .getList("$", ProductResponse.class);
    }

    public Response getCouponResponse() {
        return given(COUPONS)
                .get(COUPONS);
    }

//    public RequestSpecification given(String endpoint, String method, Map<String, String> additionalParams) {
//        Map<String, String> oauthParams = generateOAuthParams(endpoint, method, additionalParams);
//
//        // Tworzenie request z parametrami OAuth w query string
//        RequestSpecification request = RestAssured
//                .given()
//                .baseUri(baseUri)
//                .contentType(ContentType.JSON)
//                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//
//        for (Map.Entry<String, String> entry : oauthParams.entrySet()) {
//            request.queryParam(entry.getKey(), entry.getValue());
//        }
//
//        return request;
//    }
//
//    public void sendGetRequest() {
//        given("/products", "GET", null)
//                .get()
//                .then()
//                .statusCode(200);
//    }
//
//    public void sendPostRequest(Map<String, Object> body) {
//        given("/products", "POST", null)
//                .body(body)
//                .post()
//                .then()
//                .statusCode(201);
//    }
//
//    private Map<String, String> generateOAuthParams(String endpoint, String httpMethod, Map<String, String> additionalParams) {
//        // Generowanie wymaganych parametrów OAuth
//        long timestamp = System.currentTimeMillis() / 1000;
//        String nonce = generateNonce();
//        String signatureMethod = "HMAC-SHA1";
//
//        Map<String, String> params = new TreeMap<>();
//        params.put("oauth_consumer_key", consumerKey);
//        params.put("oauth_nonce", nonce);
//        params.put("oauth_signature_method", signatureMethod);
//        params.put("oauth_timestamp", String.valueOf(timestamp));
//
//        if (additionalParams != null) {
//            params.putAll(additionalParams); // Dodanie dodatkowych parametrów do podpisu
//        }
//
//        // Generowanie podpisu
//        String signature = generateSignature(httpMethod, baseUri + endpoint, params);
//        params.put("oauth_signature", signature);
//
//        return params;
//    }
//
//    private String generateSignature(String method, String url, Map<String, String> params) {
//        try {
//            // Budowanie signature base string
//            StringBuilder paramString = new StringBuilder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                if (paramString.length() > 0) paramString.append("&");
//                paramString.append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));
//            }
//
//            String baseString = method.toUpperCase() + "&" +
//                    encode(url) + "&" +
//                    encode(paramString.toString());
//
//            // Tworzenie klucza podpisu
//            String signingKey = encode(consumerSecret) + "&";
//
//            // Generowanie HMAC-SHA1 podpisu
//            Mac mac = Mac.getInstance("HmacSHA1");
//            SecretKeySpec keySpec = new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
//            mac.init(keySpec);
//            byte[] rawHmac = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
//            return Base64.getEncoder().encodeToString(rawHmac);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate OAuth signature", e);
//        }
//    }
//
//    private String encode(String value) {
//        try {
//            return URLEncoder.encode(value, StandardCharsets.UTF_8.name())
//                    .replace("+", "%20")
//                    .replace("*", "%2A")
//                    .replace("%7E", "~");
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to encode URL", e);
//        }
//    }
//
//    private String generateNonce() {
//        // Generowanie losowego ciągu 32 znaków
//        return Long.toHexString(Double.doubleToLongBits(Math.random()));
//    }
}
