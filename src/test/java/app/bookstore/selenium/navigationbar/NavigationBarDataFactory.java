package app.bookstore.selenium.navigationbar;

import app.bookstore.selenium.dto.NamedPageNavigation;
import app.bookstore.selenium.pages.*;
import app.bookstore.selenium.pages.productpage.ProductPage;

import java.util.function.Supplier;

public class NavigationBarDataFactory {

    private final Supplier<MainPage> mainPageSupplier;

    public NavigationBarDataFactory(Supplier<MainPage> loginSupplier) {
        this.mainPageSupplier = loginSupplier;
    }

    public NamedPageNavigation<MainPage> mainPage() {
        return new NamedPageNavigation<>("MainPage", mainPageSupplier);
    }

    public NamedPageNavigation<CartPage> cartPage() {
        return new NamedPageNavigation<>("CartPage", () -> mainPageSupplier.get().goToCartPage());
    }

    public NamedPageNavigation<LostPasswordPage> lostPasswordPage() {
        return new NamedPageNavigation<>("LostPasswordPage", () -> mainPageSupplier.get().goToLostPasswordPage());
    }

    public NamedPageNavigation<MyAccountPage> myAccountPage() {
        return new NamedPageNavigation<>("MyAccountPage", () -> mainPageSupplier.get().goToMyAccountPage());
    }

    public NamedPageNavigation<ProductPage> productPage() {
        return new NamedPageNavigation<>("ProductPage", () -> mainPageSupplier.get().goToRandomProductPage());
    }

    public NamedPageNavigation<WishlistPage> wishlistPage() {
        return new NamedPageNavigation<>("WishlistPage", () -> mainPageSupplier.get().goToWishlistPage());
    }
}
