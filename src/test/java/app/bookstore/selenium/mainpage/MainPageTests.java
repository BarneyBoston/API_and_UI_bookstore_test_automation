package app.bookstore.selenium.mainpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.models.PostRecord;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPageTests extends BookStoreBaseWebTest {

    @Test
    public void should_all_product_titles_from_ui_match_db() {
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
    public void sort_by_default() {
        var uiTitles = login()
                .selectSortingOption("Default sorting")
                .getProductTitles();

        assertThat(uiTitles).isSortedAccordingTo(Comparator.naturalOrder());
    }


    @DataProvider(name = "sorting_options")
    public Object[][] page_two_elements_navigation_data() {
        return new Object[][]{
                {"Sort by price: low to high", Comparator.naturalOrder()},
                {"Sort by price: high to low", Comparator.naturalOrder().reversed()},
        };
    }

    @Test(dataProvider = "sorting_options")
    public void sort_by(String sortingOption, Comparator<Double> comparator) {
        var prices = login()
                .selectSortingOption(sortingOption)
                .getPrices();

        assertThat(prices).isSortedAccordingTo(comparator);
    }

    @Test
    public void should_search_product_redirect_to_product_page() {
        var dbTitle = db.selectRandomActiveProduct().getName();

        login()
                .searchForProduct(dbTitle);

        assertThat(driver.getCurrentUrl())
                .contains("/product")
                .describedAs("Searching Product should redirect to product page");
    }

    @Test
    public void should_add_to_cart_open_cart_preview() {
        var bookName = db.selectRandomActiveProduct().getName();

        login()
                .addToCart(bookName)
                .assertThatPreviewCartPageIsOpened();
    }

    @Test
    public void should_all_elements_of_main_page_be_visible() {
        login()
                .assertThatMainPageElementsAreVisible();
    }

}
