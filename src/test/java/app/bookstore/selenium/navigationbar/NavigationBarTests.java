package app.bookstore.selenium.navigationbar;

import app.bookstore.BookStoreBaseWebTest;
import app.bookstore.dto.MatrixCreator;
import app.bookstore.selenium.dto.HasNavigationBar;
import app.bookstore.selenium.dto.NamedPageNavigation;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NavigationBarTests extends BookStoreBaseWebTest {

    private final NavigationBarDataFactory dataFactory = new NavigationBarDataFactory(this::login);


    @DataProvider(name = "pageAllElementsNavigationData")
    public Object[][] pageAllElementsNavigationData() {
        return MatrixCreator.arrayToMatrix(
                dataFactory.mainPage(),
                dataFactory.lostPasswordPage(),
                dataFactory.myAccountPage(),
                dataFactory.productPage(),
                dataFactory.wishlistPage()
        );
    }

    @DataProvider(name = "pageTwoElementsNavigationData")
    public Object[][] pageTwoElementsNavigationData() {
        return MatrixCreator.arrayToMatrix(
                dataFactory.cartPage()
        );
    }

    @Test(dataProvider = "pageAllElementsNavigationData")
    public void should_all_navigation_bar_elements_be_visible(NamedPageNavigation<? extends HasNavigationBar> namedPageNavigation) {
        var page = namedPageNavigation.navigate();
        page.getNavigationBar().assertAllNavigationBarElementsVisible();
    }

    @Test(dataProvider = "pageTwoElementsNavigationData")
    public void should_two_navigation_bar_elements_be_visible(NamedPageNavigation<? extends HasNavigationBar> namedPageNavigation) {
        var page = namedPageNavigation.navigate();
        page.getNavigationBar().assertTwoNavigationBarElementsVisible();
    }
}

