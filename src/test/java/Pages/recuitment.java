package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Javascriptexecutors;

import java.time.Duration;
import java.util.List;

public class recuitment extends Base {

    @FindBy(xpath = "(//button[@type='button' and contains(@class,'oxd-table-cell-action-space')])[17]")
    public WebElement Joen;

    @FindBy(xpath = "//button[@title=\"Help\"]")
    private WebElement Help;

    @FindBy(xpath="//div[@class=\"orangehrm-file-preview\"]")
    private WebElement pdf;

    // Multiple possible XPaths for Schedule Interview button
    @FindBy(xpath = "//button[contains(text(),'Schedule Interview')]")
    private WebElement schedule_interview;

    @FindBy(xpath = "//label[text()='Interview Title']/parent::div/following-sibling::div/input")
    private WebElement interview_title;

    @FindBy(xpath = "//i[@class=\"oxd-icon bi-calendar oxd-date-input-icon\"]")
    private WebElement calendar;

    @FindBy(xpath = "//input[@placeholder=\"Type for hints...\"]")
    private WebElement Interviwer;

    @FindBy(xpath = "(//div[@class=\"oxd-calendar-date\"])[15]")
    private WebElement interview_date;

    @FindBy(xpath = "//i[@class=\"oxd-icon bi-clock oxd-time-input--clock\"]")
    private WebElement clock;

    @FindBy(xpath = "//i[@role=\"none\"]")
    private WebElement time_up;

    @FindBy(xpath = "//textarea[@placeholder=\"Type here\"]")
    private WebElement message;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement submit;

    @FindBy(xpath = "//div[@id='oxd-toaster_1']")
    private WebElement toastMessage;

    @FindBy(xpath = "(//button[@type=\"button\"])[5]")
     private WebElement  scheduleBtn;

    Javascriptexecutors js;

    public recuitment(WebDriver driver) {
        super(driver);
        this.js = new Javascriptexecutors(driver);
    }

    public void click_on() {
        wait.until(ExpectedConditions.visibilityOf(Joen));
        Joen.click();
        System.out.println("✓ Clicked on candidate");

        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void check_profile() {
        try {
            // Wait for the page to fully load after clicking candidate
            Thread.sleep(1000);

            // Print current URL for debugging
            System.out.println("Current URL: " + driver.getCurrentUrl());
             pdf.click();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("✗ Error in check_profile: " + e.getMessage());
        }
    }

    public void setSchedule_interview() {
        try {
            System.out.println("=== Starting Interview Scheduling ===");
            System.out.println("Current URL: " + driver.getCurrentUrl());




            // Click the button
            scheduleBtn.click();
            System.out.println("✓ Schedule Interview button clicked");

            // Wait for modal/form to appear
            Thread.sleep(1500);

            // Wait for interview title field
            wait.until(ExpectedConditions.visibilityOf(interview_title));
            interview_title.clear();
            interview_title.sendKeys("QA Position Interview");
            System.out.println("✓ Interview-Title: QA Position Interview");

            // Wait for interviewer field and enter text
            wait.until(ExpectedConditions.visibilityOf(Interviwer));
            Interviwer.clear();
            Interviwer.sendKeys("a");
            Thread.sleep(1500); // Wait for dropdown to populate

            // Select interviewer from dropdown
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            List<WebElement> options = shortWait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//div[@role='option']")
                    )
            );

            if (!options.isEmpty()) {
                options.get(0).click(); // Select first available interviewer
                System.out.println("✓ Interviewer selected: " + options.get(0).getText());
            } else {
                throw new Exception("No interviewers found in dropdown");
            }

            // Select date
            Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(calendar));
            calendar.click();
            Thread.sleep(500);

            wait.until(ExpectedConditions.visibilityOf(interview_date));
            interview_date.click();
            System.out.println("✓ Date selected");

            // Set time
            Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(clock));
            clock.click();
            Thread.sleep(500);

            wait.until(ExpectedConditions.visibilityOf(time_up));
            for(int i = 0; i < 9; i++) {
                time_up.click();
                Thread.sleep(100);
            }
            System.out.println("✓ Time set");

            // Enter message
            wait.until(ExpectedConditions.visibilityOf(message));
            message.clear();
            message.sendKeys("Interview scheduled for QA position");
            System.out.println("✓ Message entered");

            // Submit
            wait.until(ExpectedConditions.elementToBeClickable(submit));
            submit.click();
            System.out.println("✓ Submit clicked - waiting for toast message...");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("✗ Error in setSchedule_interview: " + e.getMessage());

            // Take screenshot for debugging
            try {
                System.out.println("Page Source (first 500 chars): " +
                        driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
            } catch (Exception ex) {
                // Ignore
            }

            throw new RuntimeException("Failed to schedule interview: " + e.getMessage(), e);
        }
    }

    public boolean verifySuccessMessage() {
        try {
            System.out.println("=== Waiting for Toast Message ===");

            // Wait for toast to become visible (increased timeout)
            WebDriverWait toastWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            toastWait.until(ExpectedConditions.visibilityOf(toastMessage));

            // Get the toast text
            String messageText = toastMessage.getText();
            System.out.println("✓ Toast Message Captured: '" + messageText + "'");

            // Verify it contains success message
            if (messageText.contains("Success") ||
                    messageText.contains("Successfully") ||
                    messageText.contains("Saved")) {

                System.out.println("✓✓✓ Interview scheduled successfully! ✓✓✓");

                // Wait a moment before toast disappears
                Thread.sleep(1000);

                // Optional: Wait for toast to disappear
                toastWait.until(ExpectedConditions.invisibilityOf(toastMessage));
                return true;
            } else {
                System.out.println("✗ Unexpected toast message: " + messageText);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("✗ Failed to capture toast message: " + e.getMessage());

            // Try alternative toast locators
            try {
                System.out.println("Trying alternative toast locators...");
                List<WebElement> toasts = driver.findElements(By.xpath("//div[contains(@class,'toast') or contains(@class,'message')]"));
                if (!toasts.isEmpty()) {
                    System.out.println("Found toast with alternative locator: " + toasts.get(0).getText());
                }
            } catch (Exception ex) {
                // Ignore
            }

            return false;
        }
    }
}