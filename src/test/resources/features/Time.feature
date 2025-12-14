Feature: Verifying Punching

  Background:
    Given User in login
    When User enter "Admin" and "admin123" and hits enter
    Then user is navigated to Home page


  Scenario:Verifying the Customer page
      Given User in Home page
      When  User clicks on Time
      Then  User is navigated to customer table

  Scenario:Validating the Content in table
    Given User in customer page
    Then Check table contain data

  Scenario: Validating Timesheets Pending Action
    Given User in Home page
    When  User clicks on Time
    When  User clicks Attendance and Employee records
    Then  Checks timesheet table



