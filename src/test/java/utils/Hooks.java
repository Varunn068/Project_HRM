package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;



import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.HashMap;
import java.util.Objects;

import static Pages.Base.driver;

public class Hooks {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

   @Before
    public void setup() {

       ChromeOptions opt = new ChromeOptions();
       HashMap<String, Object> prefs = new HashMap<>();
       prefs.put("plugins.always_open_pdf_externally", true);
       prefs.put("download.default_directory","C:\\Varun_Folder\\Honeywell\\test-output");
       opt.setExperimentalOption("prefs",prefs);
       opt.addArguments("--Incognito");
       opt.addArguments("--headless");
        driver = new ChromeDriver(opt);
        driver.manage().window().maximize();
        System.out.println("========================================");
        System.out.println("✓ Browser started successfully");
        System.out.println("========================================");
    }

   @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("========================================");
            System.out.println("✓ Browser closed successfully");
            System.out.println("========================================");

        }
    }


}