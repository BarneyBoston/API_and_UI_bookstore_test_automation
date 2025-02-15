package app.bookstore.selenium.mainpage;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.db.models.PostRecord;
import org.testng.annotations.Test;

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


}
