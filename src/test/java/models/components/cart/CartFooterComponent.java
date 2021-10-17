package models.components.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFooterComponent {
    private final WebDriver webDriver;

    public CartFooterComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public CartShippingComponent cartShippingComponent() {
        return new CartShippingComponent(webDriver);
    }


    public CartTotalComponent cartTotalComponent() {
        return new CartTotalComponent(webDriver);
    }

    public static class CartShippingComponent {
        private final WebDriver webDriver;

        public CartShippingComponent(WebDriver webDriver) {
            this.webDriver = webDriver;
        }
    }

    public static class CartTotalComponent {
        private final WebDriver webDriver;

        private final By cartTotalTableRowSel = By.cssSelector("table[class='cart-total'] tr");
        private final By termOfServiceSel = By.cssSelector("#termsofservice");
        private final By checkoutBtnSel = By.cssSelector("#checkout");

        public CartTotalComponent(WebDriver webDriver) {
            this.webDriver = webDriver;
        }

        public WebElement termOfService() {
            return this.webDriver.findElement(this.termOfServiceSel);
        }

        public WebElement checkoutBtn() {
            return this.webDriver.findElement(this.checkoutBtnSel);
        }

        public Map<String, Double> priceMap() {
            Map<String, Double> map = new HashMap<>();
            List<WebElement> cartTotalRows = this.webDriver.findElements(this.cartTotalTableRowSel);
            for (WebElement row : cartTotalRows) {
                String key = row.findElement(By.cssSelector(".cart-total-left")).getText().trim().replace(":", "");
                Double value = Double.parseDouble(row.findElement(By.cssSelector(".cart-total-right")).getText().trim());
                map.put(key, value);
            }
            return map;
        }
    }
}
