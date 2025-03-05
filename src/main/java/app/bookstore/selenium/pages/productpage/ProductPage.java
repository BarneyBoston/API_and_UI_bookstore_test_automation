package app.bookstore.selenium.pages.productpage;

import app.bookstore.selenium.pages.BasePage;
import app.bookstore.selenium.pages.CartPage;
import app.bookstore.selenium.pages.WishlistPage;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("unused")
public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(css = "[role='alert']")
    private WebElement viewCartPopUp;

    @FindBy(xpath = "//a[text()='View cart']")
    private WebElement viewCartButton;

    @FindBy(css = "[title='Qty']")
    private WebElement quantityField;

    @FindBy(xpath = "//span[text()='Add to wishlist']")
    private WebElement addToWishListButton;

    @FindBy(css = ".view-wishlist")
    private WebElement viewWishListButton;

    @FindBy(css = ".delete_item")
    private WebElement removeFromWishListButton;

    @FindBy(css = ".description_tab")
    private WebElement descriptionTabButton;

    @FindBy(css = ".contents_tab")
    private WebElement contentsTabButton;

    @FindBy(css = ".reviews_tab")
    private WebElement reviewsTabButton;

    @FindBy(css = "[class='related products']")
    private WebElement relatedProductsSection;

    @FindBy(css = "#tab-description p")
    private WebElement descriptionTabText;

    @FindBy(css = "[id='tab-contents']")
    private WebElement contentsTabSection;

    private WebElement getProductText(String product) {
        return driver.findElement(By.xpath(String.format("//h1[text()='%s']", product)));
    }

    @Step("Go to review tab")
    public ReviewTab goToReviewTab() {
        clickElement(reviewsTabButton);
        return new ReviewTab(driver);
    }

    @Step("Click add product to cart")
    public ProductPage addProductToCart() {
        clickElement(addToCartButton);
        return this;
    }

    @Step("Click view cart from notification")
    public CartPage viewCart() {
        clickElement(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Input quantity as: {quantity}")
    public ProductPage inputQuantityAs(String quantity) {
        sendKeysToElement(quantityField, quantity);
        return this;
    }

    @Step("Increase quantity by: {by}")
    public ProductPage increaseQuantityBy(Integer by) {
        waitForElementToBeVisible(quantityField, 3);
        IntStream.range(0, by).forEach(i -> quantityField.sendKeys(Keys.ARROW_UP));
        return this;
    }

    @Step("Decrease quantity by: {by}")
    public ProductPage decreaseQuantityBy(Integer by) {
        waitForElementToBeVisible(quantityField, 3);
        IntStream.range(0, by).forEach(i -> quantityField.sendKeys(Keys.ARROW_DOWN));
        return this;
    }

    @Step("Click add to wishlist")
    public ProductPage addToWishList() {
        clickElement(addToWishListButton);
        return this;
    }

    @Step("Click view to wishlist")
    public WishlistPage viewWishList() {
        clickElement(viewWishListButton);
        return new WishlistPage(driver);
    }

    @Step("Click remove from wishlist")
    public ProductPage removeFromWishList() {
        clickElement(removeFromWishListButton);
        return this;
    }

    @Step("Click contents tab")
    public ProductPage clickContentsTab() {
        clickElement(contentsTabButton);
        return this;
    }

    @Step("Assert view cart pop up is displayed and has adequate text")
    public void assertViewCartPopUpIsDisplayedAndTextIs(String message) {
        waitForElementToBeVisible(viewCartPopUp, 3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(viewCartPopUp.isDisplayed()).isTrue().as("View cart pop up is not displayed");
            softly.assertThat(viewCartPopUp.getText()).contains(message).as("Text is not as expected");
        });
    }

    @Step("Assert product's text is: {productText}")
    public void assertThatProductTextIs(String productText) {
        waitForElementToBeVisible(getProductText(productText), 3);
        assertThat(getProductText(productText).getText()).isEqualTo(productText).as("Product's text in notification doesn't match expected");
    }

    @Step("Assert product's quantity is: {quantity}")
    public void assertQuantityIs(String quantity) {
        waitForElementToBeVisible(quantityField, 3);
        assertThat(quantityField.getDomAttribute("value")).isEqualTo(quantity).as("Actual quantity differs from expected");
    }

    @Step("Assert all elements of product page are displayed")
    public void assertAllElementsAreDisplayed() {
        waitForPageToLoad(3);
        SoftAssertions.assertSoftly(softly ->
        {
            softly.assertThat(descriptionTabButton.isDisplayed() && descriptionTabButton.isEnabled()).isTrue().as("Description tab button is not displayed and enabled");
            softly.assertThat(contentsTabButton.isDisplayed() && contentsTabButton.isEnabled()).isTrue().as("Contents tab button is not displayed and enabled");
            softly.assertThat(reviewsTabButton.isDisplayed() && reviewsTabButton.isEnabled()).isTrue().as("Reviews tab button is not displayed and enabled");
            softly.assertThat(relatedProductsSection.isDisplayed()).isTrue().as("Related products section is not displayed");
        });
    }

    @Step("Assert description text is not empty")
    public void assertDescriptionTabTextIsDisplayed() {
        waitForPageToLoad(3);
        assertThat(descriptionTabText.getText()).isNotEmpty();
    }

    @Step("Assert context tab is displayed")
    public void assertContextTabIsDisplayed() {
        waitForPageToLoad(3);
        assertThat(contentsTabSection.isDisplayed()).isTrue();
    }

}
