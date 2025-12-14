package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timeStamp + ".html";
            createInstance(reportPath);
        }
        return extent;
    }

    public static ExtentReports createInstance(String reportPath) {
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);


        spark.config().setDocumentTitle("Automation Execution Report");
        spark.config().setReportName("Test Execution Results");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", "Varun.N");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent;
    }
}