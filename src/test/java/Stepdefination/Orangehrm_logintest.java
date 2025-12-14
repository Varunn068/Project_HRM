package Stepdefination;

import Pages.loginpage;
import Pages.homepage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static Pages.Base.driver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Orangehrm_logintest {

    private loginpage lp;
    private homepage hp;
    private WebDriverWait wait;

    @Given("User in login")
    public void user_in_login() throws Exception {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        lp = new loginpage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        assertTrue("Login page not loaded", lp.isOnLoginPage());
        System.out.println("✓ User is on login page");
    }

    @When("User enter {string} and {string} and hits enter")
    public void user_enter_and(String user, String password) throws InterruptedException {
        Thread.sleep(1000);
        lp.login(user, password);
        System.out.println("✓ Credentials entered: " + user);
    }

    @Then("user is navigated to Home page")
    public void user_is_navigated_to_home_page() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        hp = new homepage(driver);

        wait.until(ExpectedConditions.urlContains("dashboard"));
        Thread.sleep(1000);

        assertTrue("User is not on the home page", hp.isOnHomePage());

        String expected = "Dashboard";
        String actual = hp.getDashboardText();

        assertEquals("Dashboard page not displayed!", expected, actual);
        System.out.println("✓ User successfully navigated to Home page");
    }

    @Then("user sees invalid credentials error")
    public void user_sees_invalid_credentials_error() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.xpath("//p[text()='Invalid credentials']")
        ));

        assertTrue("Error message not displayed", lp.isErrorMessageDisplayed());

        String expected = "Invalid credentials";
        String actual = lp.getErrorMessageText();

        assertEquals("Error message mismatch", expected, actual);
        System.out.println("✓ Invalid credentials error displayed correctly");
    }

    @Given("user in homepage")
    public void user_in_homepage() throws InterruptedException {
        Thread.sleep(1000);
        if (hp == null) {
            hp = new homepage(driver);
        }
        assertTrue("User is not on the home page", hp.isOnHomePage());
        System.out.println("✓ User is on homepage");
    }

    @When("user clicks on logut")
    public void user_clicks_on_logut() throws InterruptedException {
        Thread.sleep(1000);
        hp.logout();
    }

    @Then("user is returned to login page after logout")
    public void user_is_returned_to_login_page_after_logout() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(2000);
        lp = new loginpage(driver);

        wait.until(ExpectedConditions.urlContains("login"));

        assertTrue("User is not on the login page", lp.isOnLoginPage());
        assertTrue("Branding image not displayed", lp.isBrandingImageDisplayed());

        System.out.println("✓ User successfully returned to login page after logout");
    }
}
