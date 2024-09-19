package stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import resources.APIresource;
import testdatabuild.ObjectManager;
import utils.StoreResponse;
import utils.TestBase;
import utils.TestContext;
import utils.WebDriverActions;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class CommonStepDefinition {

    APIresource resourceAPI;
    public TestContext context;
    public ObjectManager objectManager;
    public Response response;
    private WebDriver driver;
    private final TestBase testBase;


    public CommonStepDefinition(TestContext context) {
        this.context = context;
        this.objectManager = context.objectManager;
        this.testBase = new TestBase();
    }

    @When("user calls {string} with {string} method")
    public void userCallsWithMethod(String resource, String method) throws IOException {
        resourceAPI = APIresource.valueOf(resource);
        TestContext.endPoint = resourceAPI.getResource();
        if(method.equalsIgnoreCase("POST"))
        {
            context.response = context.request.when().post(TestContext.endPoint);
        }
        responseBody();
    }

    public void responseBody() {
        TestContext.responseBody = context.response.getBody().asString();
        System.out.println(TestContext.responseBody + "from when response");
    }

    @Then("api got status code as {int}")
    public void apiGotStatusCodeAs(int statuscode) {
        assertEquals(Integer.valueOf(context.response.getStatusCode()), Integer.valueOf(statuscode));
    }

    @And("store the required response")
    public void storeTheRequiredResponse() throws IOException {
        StoreResponse storeResponse = new StoreResponse();
        StoreResponse.WriteToFile(TestContext.responseBody.toString());
//        System.out.println(TestContext.responseBody + "from when response");
    }

    @And("Redirect to the payment url via web driver")
    public void redirectToThePaymentUrlViaWebDriver() throws InterruptedException, IOException {
        if (driver == null) {
            driver = testBase.initializeDriver(); // Initialize WebDriver
        }
        if (TestContext.weblink == null || TestContext.weblink.isEmpty()) {
            throw new RuntimeException("Web link is not provided in TestContext.");
        }
//        driver.get(TestContext.weblink);
//        driver.findElement(By.xpath("//*[@testid='nvb_loanmarketplace']")).click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[@id=\"2000057\"]")).click();
//        Thread.sleep(2000);
//        driver.close();
        WebDriverActions webDriverActions = new WebDriverActions(driver);
        webDriverActions.navigateToPaymentUrl(TestContext.weblink);
        webDriverActions.closeDriver();
    }
}
