Feature: OrangeHRM Login and Attendance Functionality

  Background:
    Given User in login

  @ValidLogin
  Scenario: Successful login with valid credentials
    When User enter "Admin" and "admin123" and hits enter
    Then user is navigated to Home page

  @InvalidLogin
  Scenario: Login with invalid credentials
    When User enter "Admin" and "wrongpassword" and hits enter
    Then user sees invalid credentials error

  @Logout
  Scenario Outline: User logout functionality
    When User enter "<username>" and "<password>" and hits enter
    Then user is navigated to Home page
    Given user in homepage
    When user clicks on logut
    Then user is returned to login page after logout

    Examples:
      | username | password  |
      | Admin    | admin123  |

  @NegativeFlow
  Scenario Outline: Login with multiple invalid credentials
    When User enter "<username>" and "<password>" and hits enter
    Then user sees invalid credentials error

    Examples:
      | username     | password      |
      | InvalidUser  | admin123      |
      | Admin        | wrongpass     |