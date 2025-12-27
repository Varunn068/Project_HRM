package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;



import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileReader;

import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import static Pages.Base.driver;



public class Hooks {

    public Properties p;
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();


    // Changed to ThreadLocal for parallel execution safety
    public static ThreadLocal<SoftAssert> softassert = ThreadLocal.withInitial(SoftAssert::new);


    @Before
    public void setup()throws Exception {
       FileReader file = new FileReader(".//src//test//resources//Misc//config.properties");
       p = new Properties();
       p.load(file);
       if(p.getProperty("browser").equals("chrome"))
       {
           ChromeOptions opt = new ChromeOptions();
           HashMap<String, Object> prefs = new HashMap<>();
           prefs.put("plugins.always_open_pdf_externally", true);
           prefs.put("download.default_directory","C:\\Varun_Folder\\Honeywell\\test-output");
           opt.setExperimentalOption("prefs",prefs);
           opt.addArguments("--Incognito");
           opt.addArguments("--headless");
           driver = new ChromeDriver(opt);
           driver.manage().window().maximize();
       }
       else {
           EdgeOptions opt = new EdgeOptions();
           HashMap<String, Object> prefs = new HashMap<>();
           prefs.put("plugins.always_open_pdf_externally", true);
           prefs.put("download.default_directory","C:\\Varun_Folder\\Honeywell\\test-output");
           opt.setExperimentalOption("prefs",prefs);
           opt.addArguments("--Incognito");
           opt.addArguments("--headless");
           driver = new EdgeDriver(opt);
           driver.manage().window().maximize();
       }

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
            softassert.get().assertAll();
        }
    }


}