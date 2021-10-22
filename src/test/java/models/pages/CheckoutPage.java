package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private final WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public BillingAddress billingAddress() {
        return new BillingAddress(this.driver);
    }

    public ConfirmOrder confirmOrder() {
        return new ConfirmOrder(this.driver);
    }

    public PaymentInformation paymentInformation() {
        return new PaymentInformation(this.driver);
    }

    public ShippingAddress shippingAddress() {
        return new ShippingAddress(this.driver);
    }

    public ShippingMethod shippingMethod() {
        return new ShippingMethod(this.driver);
    }
}
