Feature: Validate Session curl for IDFC

  Scenario Outline: Create order via session curl with all valid cases
    Given Add SessionCreate Payload with session curl "<amount>" "<customer_email>" "<customer_id>" "<customer_phone>"
    When user calls "createOrder" with "POST" method
    Then api got status code as 200
    And store the required response
    And Redirect to the payment url via web driver

#    When the user calls "createOrder" with "POST" http request
#    Then the API call got status code as 200
#    And verify the "CreateOrder" response as per given request
#    And store the "CreateOrder" values from the response


    Examples:
      | amount | customer_email        | customer_id  | customer_phone |
      | 40000  | sanjita.s@juspay.in   | juspayjuspay | 7019976635     |
#      | 2000   | sanjita.s@juspay.in   | juspayjuspay | 7019976635     |
#      | 20000  | sanjita.s@juspay.in   | juspayjuspay | 7029976602     |