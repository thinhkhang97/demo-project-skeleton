package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrder {
    private final WebDriver driver;

    private final By confirmBtnSel = By.cssSelector(".confirm-order-next-step-button");
    private final By completedBtnSel = By.cssSelector(".order-completed-continue-button");

    private BillingInfoComponent billingInfoComponent;
    private ShippingInfoComponent shippingInfoComponent;

    public ConfirmOrder(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement confirmBtn() {
        return this.driver.findElement(confirmBtnSel);
    }

    public WebElement completedBtn() {
        return this.driver.findElement(completedBtnSel);
    }

    public BillingInfoComponent billingInfoComponent() {
        if (billingInfoComponent == null) {
            this.billingInfoComponent = new BillingInfoComponent(this.driver);
        }
        return billingInfoComponent;
    }

    public ShippingInfoComponent shippingInfoComponent() {
        if(shippingInfoComponent == null) {
            this.shippingInfoComponent = new ShippingInfoComponent(this.driver);
        }
        return shippingInfoComponent;
    }

    public static abstract class OrderOverViewDataInformation {
        private final WebDriver driver;
        private final By nameSel = By.cssSelector(".name");
        private final By emailSel = By.cssSelector(".email");
        private final By phoneSel = By.cssSelector(".phone");
        private final By faxSel = By.cssSelector(".fax");
        private final By address1Sel = By.cssSelector(".address1");
        private final By cityStateZipSel = By.cssSelector(".city-state-zip");
        private final By countrySel = By.cssSelector(".country");

        protected WebElement specificDataComponent;
        protected abstract By getSpecificDataComponentSel();

        OrderOverViewDataInformation(WebDriver driver) {
            this.driver = driver;
            this.specificDataComponent = driver.findElement(this.getSpecificDataComponentSel());
        }

        public WebElement getNameComponent() {
            return this.specificDataComponent.findElement(nameSel);
        }

        public WebElement getEmailComponent() {
            return this.specificDataComponent.findElement(emailSel);
        }

        public WebElement getPhoneComponent() {
            return this.specificDataComponent.findElement(phoneSel);
        }

        public WebElement getFaxComponent() {
            return this.specificDataComponent.findElement(faxSel);
        }

        public WebElement getAddress1Component() {
            return this.specificDataComponent.findElement(address1Sel);
        }

        public WebElement getCityStateZipComponent() {
            return this.specificDataComponent.findElement(cityStateZipSel);
        }

        public WebElement getCountryComponent() {
            return this.specificDataComponent.findElement(countrySel);
        }
    }

    public static class BillingInfoComponent extends OrderOverViewDataInformation {
        BillingInfoComponent(WebDriver driver) {
            super(driver);
        }

        @Override
        protected By getSpecificDataComponentSel() {
            return By.cssSelector(".billing-info");
        }
    }

    public static class ShippingInfoComponent extends OrderOverViewDataInformation {
        ShippingInfoComponent(WebDriver driver) {
            super(driver);
        }

        @Override
        protected By getSpecificDataComponentSel() {
            return By.cssSelector(".shipping-info");
        }
    }
}
