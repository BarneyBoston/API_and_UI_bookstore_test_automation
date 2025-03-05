package app.bookstore.selenium.navigationbar;

import app.bookstore.selenium.dto.NamedPageNavigation;
import app.bookstore.selenium.pages.*;
import app.bookstore.selenium.pages.productpage.ProductPage;

import java.util.function.Supplier;

public class NavigationBarDataFactory {

    private final Supplier<MainPage> loginSupplier;

    public NavigationBarDataFactory(Supplier<MainPage> loginSupplier) {
        this.loginSupplier = loginSupplier;
    }

    public NamedPageNavigation<MainPage> mainPage() {
        return new NamedPageNavigation<>("MainPage", loginSupplier);
    }

    public NamedPageNavigation<CartPage> cartPage() {
        return new NamedPageNavigation<>("CartPage", () -> loginSupplier.get().goToCartPage());
    }

    public NamedPageNavigation<LostPasswordPage> lostPasswordPage() {
        return new NamedPageNavigation<>("LostPasswordPage", () -> loginSupplier.get().goToLostPasswordPage());
    }

    public NamedPageNavigation<MyAccountPage> myAccountPage() {
        return new NamedPageNavigation<>("MyAccountPage", () -> loginSupplier.get().goToMyAccountPage());
    }

    public NamedPageNavigation<ProductPage> productPage() {
        return new NamedPageNavigation<>("ProductPage", () -> loginSupplier.get().goToRandomProductPage());
    }

    public NamedPageNavigation<WishlistPage> wishlistPage() {
        return new NamedPageNavigation<>("WishlistPage", () -> loginSupplier.get().goToWishlistPage());
    }
}
