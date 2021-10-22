package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmOrder {
    private final WebDriver driver;

    private final By confirmBtnSel = By.cssSelector(".confirm-order-next-step-button");
    private final By completedBtnSel = By.cssSelector(".order-completed-continue-button");

    public ConfirmOrder(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement confirmBtn() {
        return this.driver.findElement(confirmBtnSel);
    }

    public WebElement completedBtn() {
        return this.driver.findElement(completedBtnSel);
    }
}
