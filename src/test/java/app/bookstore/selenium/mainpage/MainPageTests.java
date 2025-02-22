package app.bookstore.selenium.mainpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.models.PostRecord;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPageTests extends BookStoreBaseWebTest {

    @Test
    public void shouldAllProductTitlesFromUIMatchDb() {
        var dbTitles = db.selectActiveProducts()
                .stream()
                .map(PostRecord::getName)
                .toList();

        var uiTittles = login().getProductTitles();

        assertThat(dbTitles)
                .containsExactlyInAnyOrderElementsOf(uiTittles)
                .hasSameSizeAs(uiTittles);
    }

    @Test
    public void sortByDefault() {
        var uiTitles = login()
                .selectSortingOption("Default sorting")
                .getProductTitles();

        assertThat(uiTitles).isSortedAccordingTo(Comparator.naturalOrder());
    }


    @DataProvider(name = "sortingOptions")
    public Object[][] pageTwoElementsNavigationData() {
        return new Object[][]{
                {"Sort by price: low to high", Comparator.naturalOrder()},
                {"Sort by price: high to low", Comparator.naturalOrder().reversed()},
        };
    }

    @Test(dataProvider = "sortingOptions")
    public void sortBy(String sortingOption, Comparator<Double> comparator) {
        var prices = login()
                .selectSortingOption(sortingOption)
                .getPrices();

        assertThat(prices).isSortedAccordingTo(comparator);
    }

    @Test
    public void shouldSearchProductRedirectToProductPage() {
        var dbTitle = db.selectRandomActiveProduct().getName();

        login()
                .searchForProduct(dbTitle);

        assertThat(driver.getCurrentUrl())
                .contains("/product")
                .describedAs("Searching Product should redirect to product page");
    }

    @Test
    public void shouldAddToCartOpenCartPreview() {
        var bookName = db.selectRandomActiveProduct().getName();

        login()
                .addToCart(bookName)
                .assertThatPreviewCartPageIsOpened();
    }

    @Test
    public void shouldAllElementsOfMainPageBeVisible() {
        login()
                .assertThatMainPageElementsAreVisible();
    }

}
