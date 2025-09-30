package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtil;

public class DepositModalPage {
    private final WebDriver driver;
    private final WaitUtil wait;

    private final By modalRoot = By.cssSelector(".modal, [role='dialog']");
    private final By amountInput = By.cssSelector("input[name='depositAmount'], input[placeholder*='Amount']");
    private final By confirmBtn = By.xpath("//button[contains(.,'Confirm Deposit') or contains(.,'Confirm')]");
    private final By successToast = By.xpath("//*[contains(.,'Success') or contains(.,'Deposited')]");
    private final By errorText = By.xpath("//*[contains(.,'Insufficient') or contains(.,'Please enter a valid amount')]");
    private final By closeBtn = By.xpath("//button[contains(.,'Close') or contains(.,'Cancel')]");

    public DepositModalPage(WebDriver driver, WaitUtil wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isOpen() {
        try {
            return driver.findElement(modalRoot).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterAmount(String value) {
        wait.visible(amountInput).clear();
        wait.visible(amountInput).sendKeys(value);
    }

    public void clickConfirm() {
        wait.visible(confirmBtn).click();
    }

    public boolean isConfirmEnabled() {
        try {
            return driver.findElement(confirmBtn).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasError() {
        try {
            return driver.findElement(errorText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean successVisible() {
        try {
            return driver.findElement(successToast).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void close() {
        try { wait.visible(closeBtn).click(); } catch (Exception ignored) {}
    }
}
