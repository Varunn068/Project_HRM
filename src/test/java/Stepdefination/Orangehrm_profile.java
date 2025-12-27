package Stepdefination;

import Pages.homepage;
import Pages.recuitment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.Javascriptexecutors;

import static Pages.Base.driver;
import static utils.Hooks.softassert;

public class Orangehrm_profile {

    private homepage hp;
    private Javascriptexecutors ex;
    private recuitment rt;

    @And("user clicks on recuritment")
    public void click_rec()
    {
        if(hp==null)
        {
            hp = new homepage(driver);
        }
     hp.recuitment_click();
        String currentUrl = driver.getCurrentUrl();
        //Assert.assertTrue("URL did not navigate to recruitment page", currentUrl.contains("recruitment"));
        softassert.get().assertTrue(currentUrl.contains("recruitment"),"URL did not navigate to recruitment page");
        System.out.println("✓ Admin is navigated to Recruitment");
    }

    @When("Admin clicks on view profile")
    public void admin_click()
    {
            if (rt == null) {
                rt = new recuitment(driver);
            }
            rt.click_on();
            System.out.println("✓ Admin clicked on John Doe's profile");

    }

    @Then("Application form opens")
    public void forms()
    {
        if(rt==null)
        {
            rt = new recuitment(driver);
        }
     rt.check_profile();
        System.out.println("✓ Admin viewed   John Doe");
    }

    @Given("Admin in candidate profile")
    public void pro_candidate() {
        if(hp==null) {
            hp = new homepage(driver);
        }
        hp.recuitment_click();

        System.out.println("✓ Navigated to Recruitment page");

        if (rt == null) {
            rt = new recuitment(driver);
        }
        rt.click_on();

        System.out.println("✓ Clicked on candidate profile");

        // Add a small wait to ensure page is loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rt.check_profile();

        System.out.println("✓ Admin is now in candidate profile");
    }

    @When("Admin clicks on schedule interview")
    public void schedule_interview()
    {
        if (rt == null) {
            rt = new recuitment(driver);
        }
        rt.setSchedule_interview();
    }

    @Then("confirmation message received")
    public void message_alert()
    {
        if (rt == null) {
            rt = new recuitment(driver);
        }

        // Verify the toast message
        boolean isSuccess = rt.verifySuccessMessage();

        // Assert the message was displayed
        //Assert.assertTrue("Toast message was not displayed or did not contain success text", isSuccess);
        softassert.get().assertTrue(isSuccess,"Toast message was not displayed or did not contain success text");
        System.out.println("✓ Test Passed: Confirmation message received");
    }

}
