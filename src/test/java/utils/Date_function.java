package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static Pages.Base.driver;

public class Date_function {

    // Locators for navigation buttons - adjust these based on your actual calendar UI
    private By nextMonthButton = By.xpath("//button[@aria-label='Next month']");
    private By previousMonthButton = By.xpath("//button[@aria-label='Previous month']");

    public void Navigate(WebDriver driver, int targetYear, int targetMonth, int targetDay,
                         int currentMonth, int currentYear) {
        int diff = (currentYear - targetYear) * 12 + (currentMonth - targetMonth);

        if (diff > 0) {
            // Need to go forward in time
            for (int i = 0; i < diff; i++) {
                driver.findElement(nextMonthButton).click();
            }
        } else {
            // Need to go backward in time
            int stepback = Math.abs(diff);
            for (int i = 0; i < stepback; i++) {
                driver.findElement(previousMonthButton).click();
            }
        }

        // Click on the target day
        WebElement dayElement = driver.findElement(
                By.xpath("//td[@data-day='" + targetDay + "' or text()='" + targetDay + "']")
        );
        dayElement.click();
    }

    public void slot(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        Date targetdate = sdf.parse(date);
        Date currentdate = new Date();

        Calendar target = Calendar.getInstance();
        target.setTime(targetdate);

        Calendar current = Calendar.getInstance();
        current.setTime(currentdate);

        int target_day = target.get(Calendar.DATE);
        int target_month = target.get(Calendar.MONTH);
        int target_year = target.get(Calendar.YEAR);

        int current_month = current.get(Calendar.MONTH);
        int current_year = current.get(Calendar.YEAR);

        Navigate(driver, target_year, target_month, target_day, current_month, current_year);
    }
}