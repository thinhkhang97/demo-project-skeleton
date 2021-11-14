package testflows.order.computer;

import models.components.cart.AbstractCartComponent;
import models.components.cart.CartComponent;
import models.components.checkout.*;
import models.components.product.computer.ComputerEssentialComponent;
import models.pages.CheckoutCompletedPage;
import models.pages.CheckoutOptionPage;
import models.pages.CheckoutPage;
import models.pages.ItemDetailsPage;
import models.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import testdata.purchasing.BaseComputerPrice;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerSpec;
import testdata.purchasing.UserDataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BuyingComputerFlow<T extends ComputerEssentialComponent> {

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

    public void verifyCartComponent(AbstractCartComponent cartComponent, ComputerDataObject computerDataObject) {
        for (CartComponent.CartItemRowData cartItemRowData : cartComponent.cartItemRowData()) {

            // Verify processor type
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains(
                            ComputerSpec.valueOf(computerDataObject.getProcessorType()).value()),
                    "[ERR] Missing CPU information");

            // Verify HDD
            Assert.assertTrue(cartItemRowData.getProductAttributes().contains((
                    ComputerSpec.valueOf(computerDataObject.getHdd()).value()
            )), "[ERR] Missing HDD information");

            // Verify name and link
            Assert.assertNotNull(cartItemRowData.getProductName(), "[ERR] Missing product name");
            Assert.assertNotNull(cartItemRowData.getProductNameLink(), "[ERR] Missing product name link");
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

        // Verify detail in cart
        verifyCartComponent(shoppingCartPage.cartComponent(), simpleComputer);
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

    public void verifyConfirmOrderComponent(ConfirmOrder.OrderOverViewDataInformation component, UserDataObject userDataObject) {
        // Verify name
        final int FIRST_NAME_INDEX = 0;
        final int LAST_NAME_INDEX = 1;
        String fullName = component.getNameComponent().getText();
        String[] names = fullName.split(" ");
        String firstName = names[FIRST_NAME_INDEX];
        String lastName = names[LAST_NAME_INDEX];
        Assert.assertTrue(userDataObject.getFirstName().equals(firstName), "[ERR]: Wrong first name");
        Assert.assertTrue(userDataObject.getLastName().equals(lastName), "[ERR]: Wrong last name");

        // Verify email
        String emailStr = component.getEmailComponent().getText();
        String email = emailStr.replace("Email: ", "");
        Assert.assertTrue(userDataObject.getEmail().equals(email), "[ERR]: Wrong email");

        // Verify phone
        String phoneStr = component.getPhoneComponent().getText();
        String phone = phoneStr.replace("Phone: ", "");
        Assert.assertTrue(userDataObject.getPhone().equals(phone), "[ERR]: Wrong phone");

        // Verify address 1
        String address1 = component.getAddress1Component().getText();
        Assert.assertTrue(userDataObject.getAddress1().equals(address1), "[ERR]: Wrong address 1");

        // Verify city state zip
        String cityStateZip = component.getCityStateZipComponent().getText();
        String[] cityStateZipList = cityStateZip.trim().split(",");
        final int CITY_INDEX = 0;
        final int STATE_ZIP_INDEX = 1;
        final int STATE_INDEX = 0;
        final int ZIP_INDEX = 1;

        String[] stateZipList = cityStateZipList[STATE_ZIP_INDEX].trim().split(" ");
        String cityStr = cityStateZipList[CITY_INDEX].trim();
        String stateStr = stateZipList[STATE_INDEX].trim();
        String zipStr = stateZipList[ZIP_INDEX].trim();

        Assert.assertTrue(userDataObject.getCity().equals(cityStr), "[ERR]: Wrong city");
        Assert.assertTrue(userDataObject.getState().equals(stateStr), "[ERR]: Wrong state");
        Assert.assertTrue(userDataObject.getZipCode().equals(zipStr), "[ERR]: Wrong zip code");

        // Verify country
        String countryStr = component.getCountryComponent().getText().trim();
        Assert.assertTrue(userDataObject.getCountry().equals(countryStr), "[ERR]: Wrong country");

        // TODO: Verify payment method
    }

    public void fillCheckoutInformation(UserDataObject userDataObject, ComputerDataObject computerDataObject) {
        CheckoutPage checkoutPage = new CheckoutPage(this.driver);
        CheckoutCompletedPage checkoutCompletedPage = new CheckoutCompletedPage(this.driver);

        BillingAddress billingAddress = checkoutPage.billingAddress();
        ShippingAddress shippingAddress = checkoutPage.shippingAddress();
        ShippingMethod shippingMethod = checkoutPage.shippingMethod();
        PaymentMethod paymentMethod = checkoutPage.paymentMethod();
        PaymentInformation paymentInformation = checkoutPage.paymentInformation();
        ConfirmOrder confirmOrder = checkoutPage.confirmOrder();

        billingAddress.inputFirstName().sendKeys(userDataObject.getFirstName());
        billingAddress.inputLastName().sendKeys(userDataObject.getLastName());
        billingAddress.inputEmail().sendKeys(userDataObject.getEmail());
        billingAddress.selectCountryByName(userDataObject.getCountry());
        billingAddress.selectStateByName(userDataObject.getState());
        billingAddress.inputCity().sendKeys(userDataObject.getCity());
        billingAddress.inputAddress1().sendKeys(userDataObject.getAddress1());
        billingAddress.inputZipCode().sendKeys(userDataObject.getZipCode());
        billingAddress.inputPhone().sendKeys(userDataObject.getPhone());
        billingAddress.continueBtn().click();

        shippingAddress.continueBtn().click();

        shippingMethod.methodItems().get(1).click();
        shippingMethod.continueBtn().click();

        paymentMethod.methodItems().get(0).click();
        paymentMethod.continueBtn().click();

        paymentInformation.continueBtn().click();

        // Verify confirm order
        verifyConfirmOrderComponent(confirmOrder.billingInfoComponent(), userDataObject);
        verifyConfirmOrderComponent(confirmOrder.shippingInfoComponent(), userDataObject);

        // Verify summary cart component
        verifyCartComponent(checkoutPage.summaryCartComponent(), computerDataObject);

        // Verify cart total component
        // Verify sub total
        Double sumOfItemSubTotalInCart = 0.0;
        for (AbstractCartComponent.CartItemRowData cartItemRowData : checkoutPage.summaryCartComponent().cartItemRowData()) {
            sumOfItemSubTotalInCart += cartItemRowData.getSubTotal();
        }
        CartTotalComponent cartTotalComponent = checkoutPage.cartTotalComponent();
        Map<String, Double> priceMap = cartTotalComponent.priceMap();
        Double subTotalInCartTotal = priceMap.get(ComputerPriceType.subTotal);
        Assert.assertEquals(sumOfItemSubTotalInCart, subTotalInCartTotal, "[ERR]: Sub total is wrong");

        Double billTotal = 0.0;
        for (String key : priceMap.keySet()) {
            System.out.println(key);
            billTotal += priceMap.get(key);
        }
        Double finalTotal = priceMap.get(ComputerPriceType.total);
        billTotal -= finalTotal;
        Assert.assertEquals(billTotal, finalTotal, "[ERR] Final total is wrong");

        confirmOrder.confirmBtn().click();

        // Verify order id
        String orderId = checkoutCompletedPage.orderNumberText();
        String orderLink = checkoutCompletedPage.orderDetailLink();
        Assert.assertTrue(orderLink.endsWith(orderId), "[ERR] Not correct order id");

        checkoutCompletedPage.finishBtn();
    }
}
