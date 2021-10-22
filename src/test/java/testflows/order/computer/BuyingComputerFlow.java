package testflows.order.computer;

import models.components.checkout.BillingAddress;
import models.components.product.computer.ComputerEssentialComponent;
import models.pages.CheckoutOptionPage;
import models.pages.CheckoutPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.BaseComputerPrice;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerSpec;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BuyingComputerFlow<T extends ComputerEssentialComponent>{

    private final WebDriver driver;
    private T essentialCompGeneric;
    private final ShoppingCartPage shoppingCartPage;

    public BuyingComputerFlow(WebDriver driver) {
        this.driver = driver;
        this.shoppingCartPage = new ShoppingCartPage(driver);
    }

    public BuyingComputerFlow<T> withComputerEssentialComp(Class<T> computerType) {
        try {
            essentialCompGeneric = computerType.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public void buildComputer(ComputerDataObject compData) {
        if (essentialCompGeneric == null) {
            throw new RuntimeException("Please call withComputerType to specify computer type!");
        }
        ItemDetailsPage detailsPage = new ItemDetailsPage(driver);

        // Build Comp specs
        essentialCompGeneric.selectProcessorType(compData.getProcessorType());
        essentialCompGeneric.selectRAM(compData.getRam());
        essentialCompGeneric.selectHDD(compData.getHdd());

        // Unselect all check boxes
        essentialCompGeneric.unselectAllOption();

        // Add To cart
        essentialCompGeneric.clickOnAddToCartBtn();
        try {
            detailsPage.waitUntilItemAddedToCart();
        } catch (Exception e) {
            throw new Error("[ERR] Item is not added after 15s!");
        }
    }

    public void verifyComputerAdded(ComputerDataObject simpleComputer) {

        double baseComputerPrice = BaseComputerPrice.valueOf(this.essentialCompGeneric.getType()).getValue();

        // Get additional fee
        double additionalFees = 0.0;
        additionalFees += ComputerSpec.valueOf(simpleComputer.getProcessorType()).additionPrice();
        additionalFees += ComputerSpec.valueOf(simpleComputer.getRam()).additionPrice();
        additionalFees += ComputerSpec.valueOf(simpleComputer.getHdd()).additionPrice();

        // Get Total current price for computer
        double currentCompPrice = baseComputerPrice + additionalFees;

        // Compare
        double itemTotalPrice = shoppingCartPage.shoppingCartItemComp().itemTotalPrice();
        Assert.assertEquals(currentCompPrice, itemTotalPrice, "[ERR] Total price is not correct!");
    }

    public void verifyTotalPayment() {
        Map<String, Double> priceMap = this.shoppingCartPage.cartFooterComponent().cartTotalComponent().priceMap();
        Double subTotal = priceMap.get(ComputerPriceType.subTotal);
        Double shipping = priceMap.get(ComputerPriceType.shipping);
        Double tax = priceMap.get(ComputerPriceType.tax);
        Double expectedTotal = subTotal + shipping + tax;
        Double total = priceMap.get(ComputerPriceType.total);

        Assert.assertEquals(total, expectedTotal, "[ERR] Total payment is not correct!");

        this.shoppingCartPage.cartFooterComponent().cartTotalComponent().termOfService().click();
        this.shoppingCartPage.cartFooterComponent().cartTotalComponent().checkoutBtn().click();

        CheckoutOptionPage checkoutOptionPage = new CheckoutOptionPage(this.driver);
        checkoutOptionPage.checkoutAsGuestOrRegisterComponent().checkoutAsGuessBtn().click();
    }

    public void fillCheckoutInformation() {
        CheckoutPage checkoutPage = new CheckoutPage(this.driver);
        BillingAddress billingAddress = checkoutPage.billingAddress();

        billingAddress.inputFirstName().sendKeys("Khang");
        billingAddress.inputLastName().sendKeys("Nguyen");
        billingAddress.inputEmail().sendKeys("thinhkhang97@gmail.com");
        billingAddress.selectCountryByName("United States");
        billingAddress.selectStateByName("Alabama");
        billingAddress.inputCity().sendKeys("NY");
        billingAddress.inputAddress1().sendKeys("12 street 8");
        billingAddress.inputZipCode().sendKeys("7000000");
        billingAddress.inputPhone().sendKeys("+123456789");
        billingAddress.continueBtn().click();
    }
}
