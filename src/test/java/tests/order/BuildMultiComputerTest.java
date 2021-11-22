package tests.order;

import io.qameta.allure.Description;
import models.components.product.computer.CheapComputerEssentialComponent;
import models.components.product.computer.StandardEssentialComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testdata.purchasing.ComputerDataObject;
import testdata.purchasing.ComputerType;
import testdata.url.URL;
import testflows.order.computer.BuyingComputerFlow;
import testflows.order.computer.BuyingComputerFlowEx;
import tests.BaseTest;
import utils.data.ComputerTestDataGenerator;

import java.security.SecureRandom;

public class BuildMultiComputerTest extends BaseTest {
    @Test(description = "Buying a cheap and a standard computer")
    @Description(value = "Using a set of utils.data with different computer specs and check total price in cart")
    public void testBuildingMultiComputer() {
        ComputerDataObject[] cheapComputerData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/CheapComputerDataList.json");

        ComputerDataObject[] standardComputerData =
                ComputerTestDataGenerator
                        .getTestDataFrom("/src/test/java/testdata/purchasing/StandardComputerDataList.json");

        ComputerDataObject randomCheapComputer = cheapComputerData[new SecureRandom().nextInt(cheapComputerData.length)];
        ComputerDataObject randomStandardComputer = standardComputerData[new SecureRandom().nextInt(standardComputerData.length)];

        WebDriver driver = getDriver();
        BuyingComputerFlowEx buyingComputerFlow = new BuyingComputerFlowEx(driver);

        goTo(URL.CHEAP_COMP_DETAILS);
        buyingComputerFlow.withComputerEssentialComp(CheapComputerEssentialComponent.class);
        buyingComputerFlow.buildComputer(ComputerType.CHEAP_COMPUTER, randomCheapComputer, 2);

        goTo(URL.STANDARD_COMP_DETAILS);
        buyingComputerFlow.withComputerEssentialComp(StandardEssentialComponent.class);
        buyingComputerFlow.buildComputer(ComputerType.STANDARD_COMPUTER, randomStandardComputer, 3);

        goTo(URL.CART);
        buyingComputerFlow.verifyAddedComputer();
        buyingComputerFlow.verifyTotalPayment();
    }
}
