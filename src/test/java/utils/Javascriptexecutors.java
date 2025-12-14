package utils;

import Pages.Base;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Javascriptexecutors extends Base {

    private JavascriptExecutor js;

    // Constructor to inject WebDriver
    public Javascriptexecutors(WebDriver driver) {
        super(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Scroll to a specific WebElement
    public void scroll_to_object(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Scroll to element with smooth behavior
    public void scrollToElementSmooth(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    // Scroll down to the bottom of the page
    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Scroll to the top of the page
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    // Scroll by specific pixels
    public void scrollByPixels(int x, int y) {
        js.executeScript("window.scrollBy(" + x + "," + y + ");");
    }

    // Click element using JavaScript (useful when normal click fails)
    public void clickElement(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    // Enter text using JavaScript
    public void enterText(WebElement element, String text) {
        js.executeScript("arguments[0].value='" + text + "';", element);
    }

    // Highlight element (for debugging)
    public void highlightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    // Remove highlight from element
    public void removeHighlight(WebElement element) {
        js.executeScript("arguments[0].style.border=''", element);
    }

    // Get page title using JavaScript
    public String getPageTitle() {
        return (String) js.executeScript("return document.title;");
    }

    // Refresh page using JavaScript
    public void refreshPage() {
        js.executeScript("location.reload();");
    }

    // Get inner text of element
    public String getInnerText(WebElement element) {
        return (String) js.executeScript("return arguments[0].innerText;", element);
    }

    // Check if element is present in DOM
    public boolean isElementPresent(WebElement element) {
        try {
            return (boolean) js.executeScript("return arguments[0] != null;", element);
        } catch (Exception e) {
            return false;
        }
    }

    // Zoom page
    public void zoomPage(int percentage) {
        js.executeScript("document.body.style.zoom='" + percentage + "%'");
    }

    // Flash element (blink effect for debugging)
    public void flashElement(WebElement element) throws InterruptedException {
        String originalColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 3; i++) {
            js.executeScript("arguments[0].style.backgroundColor = 'yellow'", element);
            Thread.sleep(100);
            js.executeScript("arguments[0].style.backgroundColor = '" + originalColor + "'", element);
            Thread.sleep(100);
        }
    }

    // Get all text from page
    public String getAllPageText() {
        return (String) js.executeScript("return document.documentElement.innerText;");
    }

    // Open new tab
    public void openNewTab() {
        js.executeScript("window.open('about:blank','_blank');");
    }

    // Close current tab
    public void closeCurrentTab() {
        js.executeScript("window.close();");
    }

    // Navigate back
    public void navigateBack() {
        js.executeScript("window.history.back();");
    }

    // Navigate forward
    public void navigateForward() {
        js.executeScript("window.history.forward();");
    }

    // Generate alert
    public void generateAlert(String message) {
        js.executeScript("alert('" + message + "');");
    }

    // Get current URL
    public String getCurrentURL() {
        return (String) js.executeScript("return document.URL;");
    }

    // Check if page is fully loaded
    public boolean isPageLoaded() {
        return js.executeScript("return document.readyState").equals("complete");
    }
}
