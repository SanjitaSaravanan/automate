package testdatabuild;

import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.OrderRequest;
import pojo.ArgsSpecified;
import utils.ReusableMethods;

import java.io.IOException;
import java.util.Random;
import static utils.ReusableMethods.generateRandomNumber;
import static utils.ReusableMethods.generateRandomString;

public class orderBuild {
    private static Random rand = new Random();
    OrderRequest orderRequest = new OrderRequest();
    ReusableMethods reusableMethods = new ReusableMethods();

    public OrderRequest createPayload(ArgsSpecified arguments) throws IOException {
        String orderId;
        orderId = "Test" + generateRandomString(4) + generateRandomNumber(4);
        orderRequest.setOrder_id(orderId);
        orderRequest.setAmount(arguments.getAmount());
        orderRequest.setAction("paymentPage");
        orderRequest.setPayment_page_client_id(ReusableMethods.getGlobalValue("payment_page_client_id"));



//      cashify specific payload
        if (orderRequest.getPayment_page_client_id() != null && orderRequest.getPayment_page_client_id().equalsIgnoreCase("cashify")) {
            orderRequest.setBasket("[{\"id\":\"MK-RMB-32613-ARGE-SUP-B1C1\", \"quantity\":1, \"unitPrice\":3999, \"description\":\"Realme 7i - Refurbished - null - Superb\", \"sku\":\"MK-RMB-32613-ARGE-SUP-B1C1\"} ]");
            orderRequest.setCustomer_email(arguments.getEmail());
            orderRequest.setCustomer_id(arguments.getCustomer_id());
            orderRequest.setCustomer_phone(arguments.getCustomer_phone());
            orderRequest.setDescription("Complete Payment");
        }

//      starhealth specific payload
        else if (orderRequest.getPayment_page_client_id() != null && orderRequest.getPayment_page_client_id().equalsIgnoreCase("starhealth")) {
            orderRequest.setDescription("Policy 12345");
            orderRequest.setCurrency("INR");
            orderRequest.setCustomer_id((String) ReusableMethods.getGlobalValue("phone-number"));
            orderRequest.setCustomer_email((String) ReusableMethods.getGlobalValue("email"));
            orderRequest.setCustomer_phone((String) ReusableMethods.getGlobalValue("phone-number"));
            orderRequest.setCustomer_dob(arguments.getAge());
            orderRequest.setFirst_name("test user");
            orderRequest.setLast_name("test user");
            orderRequest.setReturn_url("https://pg01.api.beta.cashify.in/v1/redirect/juspay/transaction");
            orderRequest.setBilling_address_first_name("test user");
            orderRequest.setBilling_address_last_name("test user");
            orderRequest.setShipping_address_first_name("test user");
            orderRequest.setShipping_address_last_name("test user");
            orderRequest.setShipping_address_line1("test user");
            orderRequest.setShipping_address_postal_code("test user");
            orderRequest.setBilling_address_line1("test user");
            orderRequest.setBilling_address_postal_code("test user");
            if(ReusableMethods.getGlobalValue("lender").equalsIgnoreCase("AXIO")) {
                orderRequest.setCustomer_email("amoghayya.wadeyar_1@capitalfloat.com");
                orderRequest.setCustomer_first_name("Testing");
                orderRequest.setCustomer_last_name("Testing");
                orderRequest.setCustomer_gender("Male");
                orderRequest.setCustomer_dob("21-07-1980");
            }
                orderRequest.setCustomer_address_line1("address line 1");
                orderRequest.setCustomer_state("Karnataka");
                orderRequest.setCustomer_pincode("560037");
                orderRequest.setCustomer_city("Bengaluru");
                orderRequest.setIs_primary_holder("true");
                orderRequest.setMetadataSdkPayload("false");
                orderRequest.setOptionsGetClientAuthToken("true");
                orderRequest.setPayment_page_client_id(ReusableMethods.getGlobalValue("payment_page_client_id"));
                orderRequest.setUdf8("Care Classic");
                orderRequest.setUdf7("731");
                orderRequest.setUdf6("CH06");
                orderRequest.setUdf2("NEW");
                orderRequest.setUdf9("17171471724750325649143");
                orderRequest.setMetaDataWebhookUrl("https://selleruat.adityabirlahealth.com/UAT/JustPayController/webhook_url_response");
                orderRequest.setMetadataPolicyRenewalYear("2");
                orderRequest.setBeta_assets("false");
                orderRequest.setReturn_url("https://selleruat.adityabirlahealth.com/prodreplica/#thankyoupage");
        }





        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Serialize the orderRequest object to JSON
            String json = objectMapper.writeValueAsString(orderRequest);

            // Print the serialized JSON
            System.out.println(json);

            // You can also send this JSON in an HTTP request body, log it, or save it to a file

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(orderRequest + "from orderbuild");
        return orderRequest;
    }
}
