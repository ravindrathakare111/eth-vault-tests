package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtil(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public WebElement visible(By locator) {
        return wait.until(d -> {
            try {
                WebElement el = d.findElement(locator);
                return el.isDisplayed() ? el : null;
            } catch (NoSuchElementException e) {
                return null;
            }
        });
    }

    public boolean invisible(By locator) {
        return wait.until(d -> {
            try {
                return !d.findElement(locator).isDisplayed();
            } catch (NoSuchElementException e) {
                return true;
            }
        });
    }

    public void pageLoaded() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
