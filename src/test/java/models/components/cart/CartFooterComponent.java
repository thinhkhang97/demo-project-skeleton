package models.components.cart;

import org.openqa.selenium.WebDriver;

public class CartFooterComponent {
    private final WebDriver webDriver;

    public CartFooterComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static class CartShippingComponent {
        private final WebDriver webDriver;

        public CartShippingComponent(WebDriver webDriver) {
            this.webDriver = webDriver;
        }
    }

    public static class CartTotalComponent {
        private final WebDriver webDriver;

        public CartTotalComponent(WebDriver webDriver) {
            this.webDriver = webDriver;
        }
    }
}
