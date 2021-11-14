package models.components.checkout;

import models.components.cart.AbstractCartComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SummaryCartComponent extends AbstractCartComponent {
    public SummaryCartComponent(WebDriver driver) {
        super(driver);
    }

    @Override
    public By qtySel() {
        return By.cssSelector(".qty");
    }

    @Override
    public Boolean isSummaryCartComponent() {
        return true;
    }
}
