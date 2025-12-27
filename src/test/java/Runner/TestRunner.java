package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/login.feature",  // Fixed: removed /login.feature to run all features
        glue = {"Stepdefination", "utils"},
        monochrome = true,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "utils.ExtentCucumberListener"

        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}