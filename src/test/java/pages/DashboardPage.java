package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtil;

public class DashboardPage {
    private final WebDriver driver;
    private final WaitUtil wait;

    // Adjust locators to match your actual DOM
    private final By connectWalletBtn = By.xpath("//button[contains(.,'Connect Wallet')]");
    private final By connectedAddressChip = By.cssSelector("[data-testid='connected-address'], .address-chip");
    private final By availableBalanceCard = By.xpath("//div[contains(.,'Available Balance')]/following-sibling::div");
    private final By stakedBalanceCard = By.xpath("//div[contains(.,'Staked Balance')]/following-sibling::div");
    private final By totalProtocolStaked = By.xpath("//*[contains(.,'Total Protocol Staked')]");
    private final By totalStakers = By.xpath("//*[contains(.,'Total Stakers')]");
    private final By refreshBtn = By.xpath("//button[contains(.,'Refresh')]");
    private final By copyIcon = By.cssSelector("[data-testid='copy-address'], .copy-icon");
    private final By copyTooltip = By.xpath("//*[contains(.,'Copy address') or contains(.,'Copy Address')]");
    private final By copyConfirm = By.xpath("//*[contains(.,'Address Copied') or contains(.,'Copied')]");

    private final By depositBtn = By.xpath("//button[contains(.,'Deposit')]");
    private final By stakeBtn = By.xpath("//button[contains(.,'Stake')]");

    public DashboardPage(WebDriver driver, WaitUtil wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isConnectWalletVisible() {
        try {
            return driver.findElement(connectWalletBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickConnectWallet() {
        wait.visible(connectWalletBtn).click();
    }

    public String getConnectedAddressChip() {
        try {
            return driver.findElement(connectedAddressChip).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getAvailableBalanceText() {
        return wait.visible(availableBalanceCard).getText();
    }

    public String getStakedBalanceText() {
        return wait.visible(stakedBalanceCard).getText();
    }

    public void clickRefresh() {
        wait.visible(refreshBtn).click();
    }

    public void hoverCopyIcon() {
        WebElement icon = wait.visible(copyIcon);
        // basic hover: move slightly (Selenium doesn't have native hover; can use Actions if needed)
        // Fallback: just click to ensure tooltip area is visible
        icon.click();
    }

    public void clickCopyIcon() {
        wait.visible(copyIcon).click();
    }

    public boolean isCopyTooltipVisible() {
        try {
            return driver.findElement(copyTooltip).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCopyConfirmationVisible() {
        try {
            return driver.findElement(copyConfirm).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDeposit() {
        wait.visible(depositBtn).click();
    }

    public void clickStake() {
        wait.visible(stakeBtn).click();
    }

    public boolean isConnectModalVisible() {
        // modal that prompts connect
        try {
            return driver.findElement(By.xpath("//*[contains(.,'Connect Wallet') and contains(@class,'modal')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean protocolStatsPresent() {
        try {
            return driver.findElement(totalProtocolStaked).isDisplayed()
                    && driver.findElement(totalStakers).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
