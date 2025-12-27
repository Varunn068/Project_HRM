package Stepdefination;

import Pages.Attendance;
import Pages.homepage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static Pages.Base.driver;
import static org.junit.Assert.assertTrue;
import static utils.Hooks.softassert;

public class Orangehrm_time {

    private homepage hp;
    private Attendance attendance;

    @Given("User in Home page")
    public void user_in_home_page() throws InterruptedException {
        Thread.sleep(2000);
        hp = new homepage(driver);
        attendance = new Attendance(driver);

        //assertTrue("User is not on the home page", hp.isOnHomePage());
        softassert.get().assertTrue(hp.isOnHomePage(),"User is not on the home page");
        System.out.println("✓ User is on Home page");
    }

    @When("User clicks on Time")
    public void user_clicks_on_time() throws InterruptedException {
        Thread.sleep(1000);
        if (hp == null) {
            hp = new homepage(driver);
        }
        hp.attendance();
    }

    @Then("User is navigated to customer table")
    public void user_is_navigated_to_customer_table() throws InterruptedException {
        Thread.sleep(2000);
        if (attendance == null) {
            attendance = new Attendance(driver);
        }

        // Navigate to customers page
        attendance.customers_info();

        //assertTrue("User is not on the Attendance page", attendance.isOnAttendancePage());
        softassert.get().assertTrue(attendance.isOnAttendancePage(),"User is not on the Attendance page");
        System.out.println("✓ User is navigated to Customers page");

        //assertTrue("Customer table check", attendance.customer_table());
        softassert.get().assertTrue(attendance.customer_table(),"Customer table check");
        System.out.println("✓ Customer list available");
    }

    @Given("User in customer page")
    public void user_in_customer_page() throws InterruptedException {
        if (hp == null) {
            hp = new homepage(driver);
        }
        if (attendance == null) {
            attendance = new Attendance(driver);
        }

        Thread.sleep(1000);
        hp.attendance();
        Thread.sleep(1000);
        attendance.customers_info();
        Thread.sleep(2000);

        //assertTrue("Customer table check", attendance.customer_table());
        softassert.get().assertTrue(attendance.customer_table(),"Customer table check");
        System.out.println("✓ User is on customer page");
    }

    @Then("Check table contain data")
    public void check_table_contain_data() throws InterruptedException {
        Thread.sleep(1000);
        if (attendance == null) {
            attendance = new Attendance(driver);
        }
        attendance.check_data();
    }

    @When("User clicks Attendance and Employee records")
    public void User_clicks_Attendance_and_Employee_records()
    {
        attendance.attendance_employee();

    }

    @Then("Checks timesheet table")
    public void Checks_timesheet_table()
    {
        attendance.pagination_table();
    }
}