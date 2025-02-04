package app.bookstore.rest.product;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.models.PostRecord;
import app.bookstore.db.models.ProductRecord;
import app.bookstore.rest.utils.DataAssertions;
import app.bookstore.rest.utils.DataTestParameters;
import app.bookstore.rest.utils.MatrixCreator;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductControllerDataTests extends BookStoreBaseRestTest {

    private ProductControllerParametersFactory parametersFactory;

    @BeforeClass
    public void setupFactory() {
        parametersFactory = new ProductControllerParametersFactory();
    }

    @DataProvider
    public Object[][] productData() {
        return MatrixCreator.arrayToMatrix(
                parametersFactory.productId(),
                parametersFactory.minPrice(),
                parametersFactory.maxPrice()
        );
    }

    @Test(dataProvider = "productData")
    public <T> void productDataTest(DataTestParameters<ProductResponse, ProductRecord, T> parameters) {
        var responseList = controller.getProducts().stream()
                .map(parameters.responseMap())
                .toList();

        var productList = db.selectProducts().stream()
                .map(parameters.dbMap())
                .toList();

        DataAssertions.verifyThatAPIvsDBListContains(responseList, productList);
    }

    @DataProvider
    public Object[][] postData() {
        return MatrixCreator.arrayToMatrix(
                parametersFactory.name(),
                parametersFactory.postStatus(),
                parametersFactory.slug(),
                parametersFactory.postDate()
        );
    }

    @Test(dataProvider = "postData")
    public <T> void postDataTest(DataTestParameters<ProductResponse, PostRecord, T> parameters) {
        var responseList = controller.getProducts().stream()
                .map(parameters.responseMap())
                .toList();

        var productList = db.selectPosts().stream()
                .map(parameters.dbMap())
                .toList();

        DataAssertions.verifyThatAPIvsDBListContains(responseList, productList);
    }

    @Test
    public void postProductNameTest() {
        var request = ProductRequest.builder()
                .name("NAME")
                .build();
        var responseName = controller.postProducts(request).getName();

        var dbName = db.selectNameFromPosts("NAME")
                .stream()
                .map(PostRecord::getName)
                .toList()
                .get(0);

        AssertionsForInterfaceTypes.assertThat(responseName).isEqualTo(dbName);
    }

    @Test
    public void updateProductDataTest() {
        var idFromDb = db.selectProducts().stream()
                .map(ProductRecord::getProductId)
                .toList()
                .get(0);

        var request = ProductRequest.builder()
                .regular_price("12.0")
                .build();

        var response = controller.updateProduct(idFromDb.toString(), request);

        AssertionsForInterfaceTypes.assertThat(response.getPrice()).isEqualTo(12.0);
    }

    @AfterClass
    public void clear() {
        var productIds = controller.getProducts()
                .stream()
                .filter(element -> element.getName().equals("NAME") || element.getName().equals("Product") || element.getName().equals("New Book"))
                .map(ProductResponse::getId)
                .toList();

        productIds.forEach(productId -> controller.deleteProductsResponse(productId.toString()));
    }
}
