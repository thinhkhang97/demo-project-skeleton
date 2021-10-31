package models.pages.cart;

import models.components.cart.CartComponent;
import models.components.cart.CartFooterComponent;
import models.components.cart.ShoppingCartItemComponent;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage {

    private final WebDriver driver;

    private CartComponent cartComponent;

    private CartFooterComponent cartFooterComponent;

    private ShoppingCartItemComponent shoppingCartItemComp;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public ShoppingCartItemComponent shoppingCartItemComp() {
        if (this.shoppingCartItemComp == null) {
            this.shoppingCartItemComp = new ShoppingCartItemComponent(driver);
        }
        return this.shoppingCartItemComp;
    }

    public CartFooterComponent cartFooterComponent() {
        if (this.cartFooterComponent == null) {
            this.cartFooterComponent = new CartFooterComponent(driver);
        }
        return this.cartFooterComponent;
    }

    public CartComponent cartComponent() {
        if(this.cartComponent == null) {
            this.cartComponent = new CartComponent(this.driver);
        }
        return this.cartComponent;
    }
}
