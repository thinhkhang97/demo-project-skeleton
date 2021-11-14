package models.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutCompletedPage {
    private final WebDriver driver;

    private final By pageTitleSel = By.cssSelector(".page-title");
    private final By detailsSel = By.cssSelector(".details li");
    private final By finishBtnSel = By.cssSelector(".order-completed-continue-button");

    public CheckoutCompletedPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement pageTitle() {
        return this.driver.findElement(pageTitleSel);
    }

    public String orderNumberText() {
        final int ORDER_STR_INDEX = 1;
        String orderNumberStr = this.driver.findElement(detailsSel).getText();
        return orderNumberStr.split(": ")[ORDER_STR_INDEX];
    }

    public String orderDetailLink() {
        final int LINK_INDEX = 1;
        WebElement linkElem = this.driver.findElements(detailsSel).get(LINK_INDEX);
        return linkElem.findElement(By.cssSelector("a")).getAttribute("href");
    }

    public WebElement finishBtn() {
        return this.driver.findElement(finishBtnSel);
    }
}
