package stepdefinition;

import io.cucumber.java.en.Given;
import testdatabuild.orderBuild;
import pojo.ArgsSpecified;
import utils.TestContext;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.SpecBuilders.authorization;


public class CreateOrderstepdf {

    public TestContext context;

    public CreateOrderstepdf(TestContext context) {
        this.context = context;
    }
    ArgsSpecified ags =  new ArgsSpecified();


// cashify
    @Given("Add SessionCreate Payload with session curl {string} {string} {string} {string}")
    public void add_session_create_payload_with_session_curl(String amount, String customer_email, String customer_id, String customer_phone) throws IOException {
        orderBuild orderbuild = context.objectManager.getorderbuild();
        ags.setAmount(amount);
        ags.setEmail(customer_email);
        ags.setCustomer_id(customer_id);
        ags.setCustomer_phone(customer_phone);
        context.orderRequest = orderbuild.createPayload(ags);
        context.request = given().spec(authorization()).body(context.orderRequest);
    }


// starhealth
    @Given("Add SessionCreate Payload with starhealth session curl {string} {string}")
    public void addSessionCreatePayloadWithStarhealthSessionCurl(String amount, String age) throws IOException {
        orderBuild orderbuild = context.objectManager.getorderbuild();
        ags.setAmount(amount);
        ags.getAmount();
        ags.setAge(age);
        context.orderRequest = orderbuild.createPayload(ags);
        context.request = given().spec(authorization()).body(context.orderRequest);
    }
}
