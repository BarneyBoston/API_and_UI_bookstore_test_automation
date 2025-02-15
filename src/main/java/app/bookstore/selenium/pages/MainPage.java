package app.bookstore.selenium.pages;

import app.bookstore.selenium.dto.HasNavigationBar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends BasePage implements HasNavigationBar {

    @FindBy(css = ".wc-block-mini-cart__button")
    private WebElement cartPageButton;

    @FindBy(xpath = "//a[text()='My account']")
    private WebElement myAccountPageButton;

    @FindBy(css = ".attachment-woocommerce_thumbnail")
    private List<WebElement> productPageImages;

    @FindBy(xpath = "//a[text()='Wishlist']")
    private WebElement wishlistPageButton;

    @FindBy(xpath = "//a[text()='Lost your password?']")
    private WebElement lostYouPasswordPageButton;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(css = ".woocommerce-loop-product__title")
    private List<WebElement> productTitles;


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public PreviewCartPage goToPreviewCartPageWithAddToCart() {
        clickElement(addToCartButton);
        return PageFactory.initElements(driver, PreviewCartPage.class);
    }

    public PreviewCartPage goToPreviewCartPageWithCartButton() {
        clickElement(getNavigationBar().getCartPageButton());
        return PageFactory.initElements(driver, PreviewCartPage.class);
    }

    public CartPage goToCartPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getViewMyCartButton());
        return PageFactory.initElements(driver, CartPage.class);
    }

    public CheckoutPage goToCheckOutPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getGoToCheckoutButton());
        return PageFactory.initElements(driver, CheckoutPage.class);
    }

    public MyAccountPage goToMyAccountPage() {
        clickElement(getNavigationBar().getMyAccountPageButton());
        return PageFactory.initElements(driver, MyAccountPage.class);
    }

    public ProductPage goToProductPage() {
        clickElement(productPageImages.get(0));
        return PageFactory.initElements(driver, ProductPage.class);
    }

    public WishlistPage goToWishlistPage() {
        clickElement(getNavigationBar().getWishlistPageButton());
        return PageFactory.initElements(driver, WishlistPage.class);
    }

    public LostPasswordPage goToLostPasswordPage() {
        clickElement(myAccountPageButton);
        clickElement(lostYouPasswordPageButton);
        return PageFactory.initElements(driver, LostPasswordPage.class);
    }

    public List<String> getProductTitles() {
        return productTitles.stream()
                .map(WebElement::getText)
                .toList();
    }

}
