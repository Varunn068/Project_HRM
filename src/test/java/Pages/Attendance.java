package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Javascriptexecutors;

import java.util.List;

public class Attendance extends Base {

    @FindBy(xpath = "//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-level']")
    private WebElement attendanceHeader;

    @FindBy(xpath = "//button[contains(text(),'Punch In')]")
    private WebElement punchInButton;

    @FindBy(xpath = "//button[contains(text(),'Punch Out')]")
    private WebElement punchOutButton;

    @FindBy(css = ".oxd-table")
    private WebElement attendanceTable;

    @FindBy(xpath = "//li[@class='oxd-topbar-body-nav-tab --parent'][3]")
    private WebElement Projectinfo;

    @FindBy(xpath = "//a[text()='Customers']")
    private WebElement customers;

    @FindBy(xpath = "//div[@role='table']")
    public WebElement table;

    @FindBy(xpath = "(//span[@class=\"oxd-topbar-body-nav-tab-item\"])[2]")
    public WebElement attendance_key;

    @FindBy(xpath = "//a[text()=\"Employee Records\"]")
    public WebElement employee_records;

    @FindBy(xpath = "//div[@role=\"table\"]")
    public WebElement headings;

    @FindBy(xpath = "(//button[@class='oxd-pagination-page-item oxd-pagination-page-item--page'])[1]")
    public WebElement button1;

    @FindBy(xpath = "(//button[@class='oxd-pagination-page-item oxd-pagination-page-item--page'])[2]")
    public WebElement button2;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    public WebElement view;


    Javascriptexecutors js;

    public Attendance(WebDriver driver) {
        super(driver);
        this.js = new Javascriptexecutors(driver);  // ← ADD THIS LINE
    }

    public boolean isOnAttendancePage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(attendanceHeader));
            return attendanceHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void punchIn() {
        wait.until(ExpectedConditions.elementToBeClickable(punchInButton));
        punchInButton.click();
        System.out.println("✓ Punched In successfully");
    }

    public void punchOut() {
        wait.until(ExpectedConditions.elementToBeClickable(punchOutButton));
        punchOutButton.click();
        System.out.println("✓ Punched Out successfully");
    }

    public boolean isAttendanceTableDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(attendanceTable));
            return attendanceTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void customers_info() {
        wait.until(ExpectedConditions.elementToBeClickable(Projectinfo)).click();
        wait.until(ExpectedConditions.elementToBeClickable(customers)).click();
    }

    public boolean customer_table() {
        try {
            wait.until(ExpectedConditions.visibilityOf(table));
            return table.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void check_data() {
        // Get all rows
        List<WebElement> rows = table.findElements(By.xpath(".//div[@role='row']"));
        System.out.println("\n========== TABLE DATA ==========");
        System.out.println("Total Rows: " + rows.size());
        System.out.println("================================\n");

        // Print first row data
        if (!rows.isEmpty()) {
            System.out.println("--- FIRST ROW DATA ---");
            WebElement firstRow = rows.get(0);
            List<WebElement> cells = firstRow.findElements(By.xpath(".//div[@role='row']"));

            System.out.println("Total Columns: " + cells.size());
            for (int i = 0; i < cells.size(); i++) {
                String cellText = cells.get(i).getText();
                System.out.println("Column " + (i + 1) + ": " + cellText);
            }
            System.out.println();
        }

        // Print all rows
        System.out.println("--- ALL ROWS DATA ---");
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.xpath(".//div[@role='cell']"));
            System.out.print("Row " + (i + 1) + ": ");

            for (int j = 0; j < cells.size(); j++) {
                System.out.print(cells.get(j).getText() + " | ");
            }
            System.out.println();
        }

        System.out.println("\n✓ Table data printed successfully");

    }

    public void attendance_employee()
    {
        wait.until(ExpectedConditions.visibilityOf(attendance_key));
        attendance_key.click();
        System.out.println("✓ Attendance");

        wait.until(ExpectedConditions.visibilityOf(employee_records));
        employee_records.click();
        System.out.println("✓ Employee Records");
    }

    public void pagination_table()
    {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(button1));
            js.scroll_to_object(button1);
            Thread.sleep(1000);
            button1.click();

            wait.until(ExpectedConditions.visibilityOf(view));

            // Page 2
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(button2));
            js.scroll_to_object(button2);
            button2.click();

            wait.until(ExpectedConditions.visibilityOf(view));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
