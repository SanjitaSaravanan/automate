package pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import testdatabuild.orderBuild;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

    private String order_id;
    private String action;
    private String amount;
    private String description;
    private String basket;
    private String customer_email;
    private String customer_id;
    private String customer_phone;
    private String first_name;
    private String last_name;
    private String payment_page_client_id;
    private String return_url;
    private String billing_address_first_name;
    private String billing_address_last_name;
    private String shipping_address_first_name;
    private String shipping_address_last_name;
    private String shipping_address_line1;
    private String shipping_address_postal_code;
    private String billing_address_line1;
    private String billing_address_postal_code;
    private String currency;
    private String customer_dob;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_gender;
    private String customer_address_line1;
    private String customer_state;
    private String customer_pincode;
    private String customer_city;
    private String is_primary_holder;
    @JsonProperty("metadata.sdk_payload")
    private String metadataSdkPayload;
    @JsonProperty("options.get_client_auth_token")
    private String optionsGetClientAuthToken;
    private String udf8;
    private String udf7;
    private String udf6;
    private String udf2;
    private String udf9;
    @JsonProperty("metadata.webhook_url")
    private String metaDataWebhookUrl;
    @JsonProperty("metadata.policy_renewal_year")
    private String metadataPolicyRenewalYear;
    @JsonProperty("metadata.policy_start_date")
    private String metadataPolicyStartDate;
    private String beta_assets;



}
