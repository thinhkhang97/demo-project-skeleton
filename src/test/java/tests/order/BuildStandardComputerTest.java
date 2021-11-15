package tests.order;

import io.qameta.allure.Description;
import models.components.product.computer.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.user.UserDataObject;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

import static utils.data.CommonData.buildUserDataObject;

public class BuildStandardComputerTest extends BaseTest {

    @Test(dataProvider = "standardCompsDataSet", description = "Buying a standard computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingStandardComputer(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        BuyingComputerFlow<StandardEssentialComponent> orderingComputerFlow = new BuyingComputerFlow<>(driver);

        // Go to cheap computer item page
        goTo(URL.STANDARD_COMP_DETAILS);
        orderingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        orderingComputerFlow.buildComputer(computerDataObject);

        // Go to Shopping cart Page
        goTo(URL.CART);
        orderingComputerFlow.verifyComputerAdded(computerDataObject);
        orderingComputerFlow.verifyTotalPayment();

        UserDataObject userDataObject = buildUserDataObject("/src/test/java/testdata/user/DefaultUserData.json");
//        userDataObject.setFirstName("Khang");
//        userDataObject.setLastName("Nguyen");
//        userDataObject.setEmail("thinhkhang97@gmail.com");
//        userDataObject.setCountry("United States");
//        userDataObject.setState("Alabama");
//        userDataObject.setCity("NY");
//        userDataObject.setAddress1("12/4/13 street 9");
//        userDataObject.setZipCode("12345");
//        userDataObject.setPhone("123456789");
        orderingComputerFlow.fillCheckoutInformation(userDataObject, computerDataObject);
    }

    @DataProvider()
    public ComputerDataObject[] standardCompsDataSet() {
        ComputerDataObject[] computerTestData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/StandardComputerDataList.json");
        return computerTestData;
    }

}
