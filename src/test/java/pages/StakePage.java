package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtil;

public class StakePage {
    private final WebDriver driver;
    private final WaitUtil wait;

    private final By amountInput = By.cssSelector("input[name='stakeAmount'], input[placeholder*='Amount']");
    private final By maxBtn = By.xpath("//button[contains(.,'Max')]");
    private final By stakeBtn = By.xpath("//button[contains(.,'Stake') and not(contains(.,'Confirm'))]");
    private final By pendingIndicator = By.xpath("//*[contains(.,'Pending') or contains(.,'Confirming') or contains(@class,'spinner')]");
    private final By errorText = By.xpath("//*[contains(.,'too low') or contains(.,'gas') or contains(.,'Insufficient')]");
    private final By successToast = By.xpath("//*[contains(.,'Success') or contains(.,'Staked')]");

    public StakePage(WebDriver driver, WaitUtil wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void enterAmount(String value) {
        wait.visible(amountInput).clear();
        wait.visible(amountInput).sendKeys(value);
    }

    public void clickMax() {
        wait.visible(maxBtn).click();
    }

    public void clickStake() {
        wait.visible(stakeBtn).click();
    }

    public boolean isPending() {
        try { return driver.findElement(pendingIndicator).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean hasError() {
        try { return driver.findElement(errorText).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean successVisible() {
        try { return driver.findElement(successToast).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}
