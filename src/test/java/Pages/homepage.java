package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class homepage extends Base {

    @FindBy(xpath = "//span[text()='Dashboard']")
    private WebElement dashboardHeader;

    @FindBy(xpath = "//span[text()='Time']")
    private WebElement timeMenu;

    @FindBy(xpath = "//a[text()='Attendance']")
    private WebElement attendanceLink;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement userDropdown;

    @FindBy(xpath = "//a[text()='Logout']")
    private WebElement logoutLink;

    @FindBy(xpath = "(//div[@class='orangehrm-dashboard-widget-header'])[7]")
    public WebElement panel;

    @FindBy(xpath = "//li[@class=\"oxd-main-menu-item-wrapper\"][5]")
    public WebElement recuitement_key;

    public homepage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnHomePage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dashboardHeader));
            return dashboardHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDashboardText() {
        wait.until(ExpectedConditions.visibilityOf(dashboardHeader));
        return dashboardHeader.getText().trim();
    }

    public void attendance() {
        wait.until(ExpectedConditions.elementToBeClickable(timeMenu));
        timeMenu.click();
        System.out.println("✓ Clicked on Time");
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown));
        userDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();

        System.out.println("✓ User clicked on logout");
    }

    public  void recuitment_click()
    {
            wait.until(ExpectedConditions.visibilityOf(recuitement_key));
            recuitement_key.click();

    }
}