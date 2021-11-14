package models.components.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCartComponent {

    WebDriver driver;

    private final By cartItemRowSel = By.cssSelector("table[class=cart] .cart-item-row");
    private final By productImageSel = By.cssSelector(".product-picture");
    private final By productNameSel = By.cssSelector(".product-name");
    private final By productAttributeSel = By.cssSelector(".attributes");
    private final By productUnitPriceSel = By.cssSelector(".product-unit-price");
    private final By productSubTotalSel = By.cssSelector(".product-subtotal");
    private final By editSel = By.cssSelector(".edit-item");

    public AbstractCartComponent(WebDriver driver) {
        this.driver = driver;
    }

    public abstract By qtySel();

    public abstract Boolean isSummaryCartComponent();

    public List<CartItemRowData> cartItemRowData() {
        List<CartItemRowData> cartItemRowDataList = new ArrayList<>();
        List<WebElement> cartItemRowComponents = this.driver.findElements(this.cartItemRowSel);

        for (WebElement cartItemRowComponent : cartItemRowComponents) {
            String productImage = cartItemRowComponent.findElement(this.productImageSel).getAttribute("src");
            String productName = cartItemRowComponent.findElement(this.productNameSel).getText();
            String productNameLink = cartItemRowComponent.findElement(this.productNameSel).getAttribute("href");

            List<WebElement> productAttributeList = cartItemRowComponent.findElements(productAttributeSel);
            String productAttributes = productAttributeList.isEmpty() ? null : productAttributeList.get(0).getText();

            Double productUnitPrice = Double.parseDouble(cartItemRowComponent.findElement(productUnitPriceSel).getText());

            Integer productQty = this.isSummaryCartComponent() ? Integer.parseInt(cartItemRowComponent.findElement(qtySel()).getText()) :
                    Integer.parseInt(cartItemRowComponent.findElement(qtySel()).getAttribute("value"));

            Double productSubTotal = Double.parseDouble(cartItemRowComponent.findElement(productSubTotalSel).getText());

            List<WebElement> productEditComponentList = cartItemRowComponent.findElements(editSel);
            String editLink = productEditComponentList.isEmpty() ? null : productEditComponentList.get(0).getAttribute("href");

            CartItemRowData newRowData = new CartItemRowData(productImage, productName, productNameLink, productAttributes, editLink, productUnitPrice, productQty, productSubTotal);

            cartItemRowDataList.add(newRowData);
        }

        return cartItemRowDataList;

    }

    public static class CartItemRowData {
        private final String imgSource;

        private final String productName;

        private final String productNameLink;

        private final String productAttributes;

        private final String productEditLink;

        private final Double price;

        private final Integer quantity;

        private final Double subTotal;

        public CartItemRowData(String imgSource, String productName, String productNameLink, String productAttributes, String productEditLink, Double price, Integer quantity, Double subTotal) {
            this.imgSource = imgSource;
            this.productName = productName;
            this.productNameLink = productNameLink;
            this.productAttributes = productAttributes;
            this.productEditLink = productEditLink;
            this.price = price;
            this.quantity = quantity;
            this.subTotal = subTotal;
        }

        public String getImgSource() {
            return imgSource;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductNameLink() {
            return productNameLink;
        }

        public String getProductAttributes() {
            return productAttributes;
        }

        public String getProductEditLink() {
            return productEditLink;
        }

        public Double getPrice() {
            return price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Double getSubTotal() {
            return subTotal;
        }
    }
}
