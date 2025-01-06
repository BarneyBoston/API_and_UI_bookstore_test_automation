package app.bookstore.rest.product;

import app.bookstore.BookStoreBaseRestTest;
import app.bookstore.db.models.ProductRecord;
import app.bookstore.rest.utils.DataAssertions;
import app.bookstore.rest.utils.DataTestParameters;
import app.bookstore.rest.utils.MatrixCreator;
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
    public Object[][] data() {
        return MatrixCreator.arrayToMatrix(
                parametersFactory.responseId(),
                parametersFactory.minPrice(),
                parametersFactory.maxPrice()
        );
    }

    @Test(dataProvider = "data")
    public <T> void dataTest(DataTestParameters<ProductResponse, ProductRecord, T> parameters) {
        var responseIds = controller.getProducts().stream()
                .map(parameters.responseMap())
                .toList();

        var productIds = db.getProductIds().stream()
                .map(parameters.dbMap())
                .toList();

        DataAssertions.verifyThatListsMatch(responseIds, productIds);
    }
}
