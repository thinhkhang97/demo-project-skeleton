package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartTotalComponent {
    private final WebDriver webDriver;

    private final By cartTotalTableRowSel = By.cssSelector("table[class='cart-total'] tr");

    public CartTotalComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
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