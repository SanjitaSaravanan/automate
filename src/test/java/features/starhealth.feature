Feature: Creating orders for starhealth

  Scenario Outline: Create order via starhealth session curl with all valid cases
    Given Add SessionCreate Payload with starhealth session curl "<amount>" "<dob>"
    When user calls "createOrder" with "POST" method
    Then api got status code as 200
    And store the required response
    And Redirect to the payment url via web driver
    Examples:
      | amount | dob         |
      | 15000  | 12-12-1990  |
#      | 15000  | 60  |
#      | 15000  | 61  |
#      | 15000  | 19  |
#      | 14999  | 20  |
#      | 14999  | 60  |
#      | 14999  | 19  |
#      | 14999  | 61  |
#      | 25000  | 20  |
#      | 45000  | 30  |
#      | 30000  | 25  |