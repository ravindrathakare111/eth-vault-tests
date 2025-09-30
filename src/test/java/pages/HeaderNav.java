package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtil;

public class HeaderNav {
    private final WebDriver driver;
    private final WaitUtil wait;

    private final By depositLink = By.xpath("//a[contains(.,'Deposit')]");
    private final By stakeLink = By.xpath("//a[contains(.,'Stake')]");
    private final By leaderboardLink = By.xpath("//a[contains(.,'Leaderboard')]");
    private final By dashboardLinkOrLogo = By.xpath("//a[contains(.,'Dashboard')] | //a[contains(@class,'logo')]");

    private final By activeLink = By.cssSelector(".nav a.active, .nav .active");

    public HeaderNav(WebDriver driver, WaitUtil wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickDeposit() { wait.visible(depositLink).click(); }

    public void clickStake() { wait.visible(stakeLink).click(); }

    public void clickLeaderboard() { wait.visible(leaderboardLink).click(); }

    public void clickDashboard() { wait.visible(dashboardLinkOrLogo).click(); }

    public boolean isActiveLinkHighlighted() {
        try {
            return driver.findElement(activeLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String currentPath() {
        return driver.getCurrentUrl();
    }
}
