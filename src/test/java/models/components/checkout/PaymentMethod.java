package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PaymentMethod {
    private final WebDriver driver;

    private final By methodItemSel = By.cssSelector("#opc-payment_method ul[class=method-list] li");
    private final By continueBtnSel = By.cssSelector("#opc-payment_method .payment-method-next-step-button");

    public PaymentMethod(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> methodItems() {
        return this.driver.findElements(methodItemSel);
    }

    public WebElement continueBtn() {
        return this.driver.findElement(continueBtnSel);
    }
}
