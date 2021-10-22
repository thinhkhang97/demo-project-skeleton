package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentInformation {
    private final WebDriver driver;

    private final By continueBtnSel = By.cssSelector("#opc-payment_info .payment-info-next-step-button");

    public PaymentInformation(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement continueBtn() {
        return this.driver.findElement(continueBtnSel);
    }

}
