package app.bookstore.selenium.pages;

import app.bookstore.selenium.dto.HasNavigationBar;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @FindBy(css = ".wc-block-mini-cart__template-part")
    private WebElement previewCartContext;

    @FindBy(css = "#primary")
    private WebElement shopContext;

    @FindBy(css = "#block-9")
    private WebElement searchContext;

    @FindBy(css = "#block-10")
    private WebElement favouritesContext;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Go to preview cart page with add to cart button")
    public PreviewCartPage goToPreviewCartPageWithAddToCart() {
        clickElement(addToCartButton);
        return new PreviewCartPage(driver);
    }

    @Step("Go to preview cart page with cart button")
    public PreviewCartPage goToPreviewCartPageWithCartButton() {
        clickElement(getNavigationBar().getCartPageButton());
        return new PreviewCartPage(driver);
    }

    @Step("Go to cart page")
    public CartPage goToCartPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getViewMyCartButton());
        return new CartPage(driver);
    }

    @Step("Go to checkout page")
    public CheckoutPage goToCheckOutPage() {
        clickElement(goToPreviewCartPageWithAddToCart().getGoToCheckoutButton());
        return new CheckoutPage(driver);
    }

    @Step("Go to my account page")
    public MyAccountPage goToMyAccountPage() {
        clickElement(getNavigationBar().getMyAccountPageButton());
        return new MyAccountPage(driver);
    }

    @Step("Go to product page")
    public ProductPage goToProductPage() {
        clickElement(productPageImages.get(0));
        return new ProductPage(driver);
    }

    @Step("Go to wishlist page")
    public WishlistPage goToWishlistPage() {
        clickElement(getNavigationBar().getWishlistPageButton());
        return new WishlistPage(driver);
    }

    @Step("Go to lost password page")
    public LostPasswordPage goToLostPasswordPage() {
        clickElement(myAccountPageButton);
        clickElement(lostYouPasswordPageButton);
        return new LostPasswordPage(driver);
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

    @Step("Add to cart book named {bookName}")
    public MainPage addToCart(String bookName) {
        clickElement(driver.findElement(By.cssSelector(String.format("[aria-label='Add “%s” to your cart']", bookName))));
        return this;
    }

    @Step("Assert that preview cart page is opened")
    public void assertThatPreviewCartPageIsOpened() {
        waitForElementToBeVisible(previewCartContext, 5);
        assertThat(previewCartContext.isDisplayed())
                .isTrue()
                .describedAs("Preview cart page hasn't been opened");
    }

    @Step("Assert that preview cart page is opened}")
    public void assertThatMainPageElementsAreVisible() {
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(shopContext.isDisplayed()).isTrue().as("Shop context is not displayed");
            softly.assertThat(searchContext.isDisplayed()).isTrue().as("Search context is not displayed");
            softly.assertThat(favouritesContext.isDisplayed()).isTrue().as("Favourites context is not displayed");
        });
    }
}
