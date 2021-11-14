package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private final WebDriver driver;
    private  ConfirmOrder confirmOrder;
    private PaymentInformation paymentInformation;
    private ShippingAddress shippingAddress;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private SummaryCartComponent summaryCartComponent;
    private CartTotalComponent cartTotalComponent;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public BillingAddress billingAddress() {
        return new BillingAddress(this.driver);
    }

    public ConfirmOrder confirmOrder() {
        if(confirmOrder == null) {
            this.confirmOrder = new ConfirmOrder(this.driver);
        }
        return this.confirmOrder;
    }

    public PaymentInformation paymentInformation() {
        if(paymentInformation == null) {
            this.paymentInformation = new PaymentInformation(this.driver);
        }
        return this.paymentInformation;
    }

    public ShippingAddress shippingAddress() {
        if(shippingAddress == null) {
            this.shippingAddress = new ShippingAddress(this.driver);
        }
        return this.shippingAddress;
    }

    public ShippingMethod shippingMethod() {
        if(shippingMethod == null) {
            this.shippingMethod = new ShippingMethod(this.driver);
        }
        return this.shippingMethod;
    }

    public PaymentMethod paymentMethod() {
        if(paymentMethod == null) {
            this.paymentMethod = new PaymentMethod(this.driver);
        }
        return this.paymentMethod;
    }

    public SummaryCartComponent summaryCartComponent() {
        if (this.summaryCartComponent == null) {
            this.summaryCartComponent = new SummaryCartComponent(this.driver);
        }
        return this.summaryCartComponent;
    }

    public CartTotalComponent cartTotalComponent() {
        if(this.cartTotalComponent == null) {
            this.cartTotalComponent = new CartTotalComponent(this.driver);
        }

        return this.cartTotalComponent;
    }
}
