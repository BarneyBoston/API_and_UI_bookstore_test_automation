package app.bookstore.selenium.dto;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class NamedPageNavigation<T extends HasNavigationBar> {

    private final String pageName;
    private final Supplier<T> navigationSupplier;

    public NamedPageNavigation(String pageName, Supplier<T> navigationSupplier) {
        this.pageName = pageName;
        this.navigationSupplier = navigationSupplier;
    }

    public T navigate() {
        return navigationSupplier.get();
    }

    @Override
    public String toString() {
        return pageName;
    }
}