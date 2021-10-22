package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShippingAddress {
    private final WebDriver driver;

    private final By continueBtnSel = By.cssSelector("#opc-shipping .new-address-next-step-button");

    public ShippingAddress(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return this.driver.findElement(continueBtnSel);
    }
}
