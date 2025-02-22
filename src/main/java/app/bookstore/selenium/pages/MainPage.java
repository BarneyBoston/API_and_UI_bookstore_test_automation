package app.bookstore.selenium.pages;

import app.bookstore.selenium.dto.HasNavigationBar;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@SuppressWarnings("unused")
public class MainPage extends BasePage implements HasNavigationBar {

    @FindBy(css = ".wc-block-mini-cart__button")
    private WebElement cartPageButton;

    @FindBy(xpath = "//a[text()='My account']")
    private WebElement myAccountPageButton;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".attachment-woocommerce_thumbnail")
    private List<WebElement> productPageImages;

    @FindBy(xpath = "//a[text()='Wishlist']")
    private WebElement wishlistPageButton;

    @FindBy(xpath = "//a[text()='Lost your password?']")
    private WebElement lostYouPasswordPageButton;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement addToCartButton;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = ".woocommerce-loop-product__title")
    private List<WebElement> productTitles;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = "[class='price']")
    private List<WebElement> mainProductPrices;

    @FindBy(css = "[name='orderby']")
    private WebElement sortingDropdown;

    @FindBy(css = "[type='search']")
    private WebElement searchField;

    @FindBy(css = "[type='submit']")
    private WebElement submitButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to preview cart page with add to cart button")
    public PreviewCartPage goToPreviewCartPageWithAddToCart() {
        clickElement(addToCartButton);
        return PageFactory.initElements(driver, PreviewCartPage.class);
    }

    @Step("Go to preview cart page with cart button")
    public PreviewCartPage goToPreviewCartPageWithCartButton() {
        clickElement(getNavigationBar().getCartPageButton());
        return PageFactory.initElements(driver, PreviewCartPage.class);
    }

    @Step("Go to cart page")
    public CartPage goToCartPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getViewMyCartButton());
        return PageFactory.initElements(driver, CartPage.class);
    }

    @Step("Go to checkout page")
    public CheckoutPage goToCheckOutPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getGoToCheckoutButton());
        return PageFactory.initElements(driver, CheckoutPage.class);
    }

    @Step("Go to my account page")
    public MyAccountPage goToMyAccountPage() {
        clickElement(getNavigationBar().getMyAccountPageButton());
        return PageFactory.initElements(driver, MyAccountPage.class);
    }

    @Step("Go to product page")
    public ProductPage goToProductPage() {
        clickElement(productPageImages.get(0));
        return PageFactory.initElements(driver, ProductPage.class);
    }

    @Step("Go to wishlist page")
    public WishlistPage goToWishlistPage() {
        clickElement(getNavigationBar().getWishlistPageButton());
        return PageFactory.initElements(driver, WishlistPage.class);
    }

    @Step("Go to lost password page")
    public LostPasswordPage goToLostPasswordPage() {
        clickElement(myAccountPageButton);
        clickElement(lostYouPasswordPageButton);
        return PageFactory.initElements(driver, LostPasswordPage.class);
    }

    @Step("Get all product titles")
    public List<String> getProductTitles() {
        return productTitles.stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Select sorting option as {optionText}")
    public MainPage selectSortingOption(String optionText) {
        Select select = new Select(sortingDropdown);
        select.selectByVisibleText(optionText);
        return this;
    }

    @Step("Get all product prices")
    public List<Double> getPrices() {
        return mainProductPrices.stream()
                .map(priceElement -> {
                    List<WebElement> discountedPrices = priceElement.findElements(By.tagName("ins"));
                    if (!discountedPrices.isEmpty()) {
                        return discountedPrices.get(0).getText();
                    } else {
                        return priceElement.getText();
                    }
                })
                .map(price -> price.replace("€", "").replace(",", ".").trim())
                .map(Double::parseDouble)
                .toList();
    }

    @Step("Search for product {product}")
    public void searchForProduct(String product) {
        clickElement(searchField);
        sendKeysToElement(searchField, product);
        clickElement(submitButton);
    }

}
