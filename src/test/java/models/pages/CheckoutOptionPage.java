package models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutOptionPage {
    private final WebDriver driver;

    public CheckoutOptionPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckoutAsGuestOrRegisterComponent checkoutAsGuestOrRegisterComponent() {
        return new CheckoutAsGuestOrRegisterComponent(this.driver);
    }

    public static class CheckoutAsGuestOrRegisterComponent {
        private final WebDriver driver;

        private final By checkoutAsGuessBtnSel = By.cssSelector(".checkout-as-guest-button");

        CheckoutAsGuestOrRegisterComponent(WebDriver driver) {
            this.driver = driver;
        }

        public WebElement checkoutAsGuessBtn() {
            return this.driver.findElement(checkoutAsGuessBtnSel);
        }
    }
}
