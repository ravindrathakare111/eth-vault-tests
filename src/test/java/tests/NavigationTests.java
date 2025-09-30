package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HeaderNav;
import utils.WaitUtil;

public class NavigationTests extends BaseTest {

    @Test(description = "TC-11: Header navigation links work correctly")
    public void tc11_navigationLinks() {
        WaitUtil w = wait;
        HeaderNav nav = new HeaderNav(driver, w);

        nav.clickDeposit();
        w.sleep(200);
        Assert.assertTrue(nav.currentPath().contains("/deposit"), "Should navigate to /deposit");
        Assert.assertTrue(nav.isActiveLinkHighlighted(), "Active link highlighted");

        nav.clickStake();
        w.sleep(200);
        Assert.assertTrue(nav.currentPath().contains("/stake"), "Should navigate to /stake");

        nav.clickLeaderboard();
        w.sleep(200);
        Assert.assertTrue(nav.currentPath().contains("/leaderboard"), "Should navigate to /leaderboard");

        nav.clickDashboard();
        w.sleep(200);
        Assert.assertTrue(nav.currentPath().matches(".*/$|.*/dashboard.*"), "Should return to dashboard");
    }
}
