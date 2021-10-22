package models.components.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BillingAddress {
    private final WebDriver driver;

    private final By inputFirstNameSel = By.cssSelector("#BillingNewAddress_FirstName");
    private final By inputLastNameSel = By.cssSelector("#BillingNewAddress_LastName");
    private final By inputEmailSel = By.cssSelector("#BillingNewAddress_Email");
    private final By selectCountrySel = By.cssSelector("#BillingNewAddress_CountryId");
    private final By selectStateSel = By.cssSelector("#BillingNewAddress_StateProvinceId");
    private final By inputCitySel = By.cssSelector("#BillingNewAddress_City");
    private final By inputAddress1Sel = By.cssSelector("#BillingNewAddress_Address1");
    private final By inputZipCodeSel = By.cssSelector("#BillingNewAddress_ZipPostalCode");
    private final By inputPhoneSel = By.cssSelector("#BillingNewAddress_PhoneNumber");
    private final By continueButtonSel = By.cssSelector("#opc-billing .new-address-next-step-button");

    public BillingAddress(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement inputFirstName() {
        return this.driver.findElement(inputFirstNameSel);
    }

    public WebElement inputLastName() {
        return this.driver.findElement(inputLastNameSel);
    }

    public WebElement inputEmail() {
        return this.driver.findElement(inputEmailSel);
    }

    public WebElement selectCountry() {
        return this.driver.findElement(selectCountrySel);
    }

    public WebElement selectState() {
        return this.driver.findElement(selectStateSel);
    }

    public WebElement inputCity() {
        return this.driver.findElement(inputCitySel);
    }

    public WebElement inputAddress1() {
        return this.driver.findElement(inputAddress1Sel);
    }

    public WebElement inputZipCode() {
        return this.driver.findElement(inputZipCodeSel);
    }

    public WebElement inputPhone() {
        return this.driver.findElement(inputPhoneSel);
    }

    public WebElement continueBtn() {
        return this.driver.findElement(continueButtonSel);
    }

    public void selectCountryByName(String name) {
        Select select = new Select(this.selectCountry());
        select.selectByVisibleText(name);
    }

    public void selectStateByName(String name) {
        Select select = new Select(this.selectState());
        select.selectByVisibleText(name);
    }
}
