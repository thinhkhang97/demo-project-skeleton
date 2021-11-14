package models.components.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartComponent extends AbstractCartComponent {

    public CartComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    public By qtySel() {
        return By.cssSelector(".qty-input");
    }

    @Override
    public Boolean isSummaryCartComponent() {
        return false;
    }
}
