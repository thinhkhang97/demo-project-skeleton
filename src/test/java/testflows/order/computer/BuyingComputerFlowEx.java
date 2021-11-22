package testflows.order.computer;

import models.components.cart.AbstractCartComponent;
import models.components.product.computer.ComputerEssentialComponent;
import models.pages.CheckoutOptionPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyingComputerFlowEx<T extends ComputerEssentialComponent> {
    private final WebDriver driver;
    private T essentialCompGeneric;
    private final ShoppingCartPage shoppingCartPage;
    private Map<ComputerType, ComputerOrder> computerOrderMap;

    public BuyingComputerFlowEx(WebDriver driver) {
        this.driver = driver;
        this.shoppingCartPage = new ShoppingCartPage(driver);
        this.computerOrderMap = new HashMap<>();
    }

    public BuyingComputerFlowEx<T> withComputerEssentialComp(Class<T> computerType) {
        try {
            essentialCompGeneric = computerType.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public void buildComputer(ComputerType computerType, ComputerDataObject compData, int quantity) {
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

        // Set quantity
        essentialCompGeneric.setQuantity(quantity);

        // Add To cart
        essentialCompGeneric.clickOnAddToCartBtn();

        // Store added computer to cart
        this.computerOrderMap.put(computerType, new ComputerOrder(computerType, compData, quantity));
        try {
            detailsPage.waitUntilItemAddedToCart();
        } catch (Exception e) {
            throw new Error("[ERR] Item is not added after 15s!");
        }
    }

    public Double calculateBuyingComputerPrice(ComputerDataObject computerDataObject) {
        double additionalFees = 0.0;
        additionalFees += ComputerSpec.valueOf(computerDataObject.getProcessorType()).additionPrice();
        additionalFees += ComputerSpec.valueOf(computerDataObject.getRam()).additionPrice();
        additionalFees += ComputerSpec.valueOf(computerDataObject.getHdd()).additionPrice();

        return additionalFees + computerDataObject.getBasePrice();
    }

    public void verifyAddedComputer() {
        Map<String, AbstractCartComponent.CartItemRowData> cartItemRowDataMap = new HashMap<>();
        List<AbstractCartComponent.CartItemRowData> cartItemRowData = this.shoppingCartPage.cartComponent().cartItemRowData();
        cartItemRowData.forEach(cartItemRow -> cartItemRowDataMap.put(cartItemRow.getProductName(), cartItemRow));

        this.computerOrderMap.keySet().forEach(computerType -> {
            ComputerDataObject computerDataObject = this.computerOrderMap.get(computerType).getComputerDataObject();
            // Get Total current price for computer
            double currentCompPrice = calculateBuyingComputerPrice(computerDataObject);

            AbstractCartComponent.CartItemRowData computerInCartRowData = cartItemRowDataMap.get(computerType.getType());
            // Get actually total price
            double actuallyTotalPrice = computerInCartRowData.getPrice();

            // Verify total price
            Assert.assertEquals(currentCompPrice, actuallyTotalPrice, "[ERR] Total price is not correct!");

            // Verify processor type
            Assert.assertTrue(computerInCartRowData.getProductAttributes().contains(
                            ComputerSpec.valueOf(computerDataObject.getProcessorType()).value()),
                    "[ERR] Missing CPU information");

            // Verify HDD
            Assert.assertTrue(computerInCartRowData.getProductAttributes().contains((
                    ComputerSpec.valueOf(computerDataObject.getHdd()).value()
            )), "[ERR] Missing HDD information");

            // Verify name and link
            Assert.assertNotNull(computerInCartRowData.getProductName(), "[ERR] Missing product name");
            Assert.assertNotNull(computerInCartRowData.getProductNameLink(), "[ERR] Missing product name link");
        });

    }

    public void verifyTotalPayment() {
        Map<String, Double> priceMap = this.shoppingCartPage.cartFooterComponent().cartTotalComponent().priceMap();
        Double subTotal = priceMap.get(ComputerPriceType.subTotal);

        List<AbstractCartComponent.CartItemRowData> cartItemRowData = this.shoppingCartPage.cartComponent().cartItemRowData();
        Double totalOfSubInCart = 0.0;
        for(AbstractCartComponent.CartItemRowData cartItemRow : cartItemRowData) {
            totalOfSubInCart += cartItemRow.getSubTotal();
        }
        Assert.assertEquals(subTotal, totalOfSubInCart, "[ERR] Sub total payment is not correct!");

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
}
