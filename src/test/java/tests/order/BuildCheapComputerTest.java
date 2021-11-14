package tests.order;

import io.qameta.allure.Description;
import models.components.product.computer.CheapComputerEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.UserDataObject;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

public class BuildCheapComputerTest extends BaseTest {

    @Test(dataProvider = "cheapCompsDataSet", description = "Buying a cheap computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingCheapComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlow<CheapComputerEssentialComponent> orderingComputerFlow = new BuyingComputerFlow<>(driver);

        // Go to cheap computer item page
        goTo(URL.CHEAP_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        orderingComputerFlow.buildComputer(computerDataObject);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded(computerDataObject);
        orderingComputerFlow.verifyTotalPayment();

        UserDataObject userDataObject = new UserDataObject();
        userDataObject.setFirstName("Khang");
        userDataObject.setLastName("Nguyen");
        userDataObject.setEmail("thinhkhang97@gmail.com");
        userDataObject.setCountry("United States");
        userDataObject.setState("Alabama");
        userDataObject.setCity("NY");
        userDataObject.setAddress1("12/4/13 street 9");
        userDataObject.setZipCode("12345");
        userDataObject.setPhone("123456789");

        orderingComputerFlow.fillCheckoutInformation(userDataObject, computerDataObject);
    }

    @DataProvider()
    public ComputerDataObject[] cheapCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/CheapComputerDataList.json");
        return computerTestData;
    }

}
