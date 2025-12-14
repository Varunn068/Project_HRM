Feature: To Test employees details

  Background:
    Given User in login
    When User enter "Admin" and "admin123" and hits enter
    Then user is navigated to Home page

  Scenario: Checking the candidates details
    Given User in Home page
    And user clicks on recuritment
    When Admin clicks on view profile
    Then  Application form opens

  Scenario: Interview scheduling
    Given Admin in candidate profile
    When Admin clicks on schedule interview
    Then confirmation message received

