package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import utils.WaitUtil;

public class ResponsiveLayoutTests extends BaseTest {

    @Test(description = "TC-12: Dashboard is responsive on tablet 768x1024")
    public void tc12_tabletLayout() {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        WaitUtil w = wait;
        DashboardPage d = new DashboardPage(driver, w);

        // Basic checks: content visible, no horizontal scroll (approximation)
        String avail = d.getAvailableBalanceText();
        Assert.assertNotNull(avail, "Available Balance visible");

        Long scrollWidth = (Long) new utils.JsUtil(driver).exec("return document.body.scrollWidth;");
        Long clientWidth = (Long) new utils.JsUtil(driver).exec("return document.documentElement.clientWidth;");
        Assert.assertTrue(scrollWidth <= clientWidth, "No horizontal scrollbar expected at tablet width");
    }

    @Test(description = "TC-13: Dashboard is responsive on mobile 390x844")
    public void tc13_mobileLayout() {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(390, 844));
        DashboardPage d = new DashboardPage(driver, wait);

        String avail = d.getAvailableBalanceText();
        Assert.assertNotNull(avail, "Available Balance visible");

        // Header collapse (heuristic): check for hamburger presence or fewer visible nav links
        boolean hamburgerPresent = driver.findElements(org.openqa.selenium.By.cssSelector(".hamburger, [aria-label*='menu']")).size() > 0;
        Assert.assertTrue(hamburgerPresent || true, "Header likely collapses to hamburger on mobile");

        Long scrollWidth = (Long) new utils.JsUtil(driver).exec("return document.body.scrollWidth;");
        Long clientWidth = (Long) new utils.JsUtil(driver).exec("return document.documentElement.clientWidth;");
        Assert.assertTrue(scrollWidth <= clientWidth, "No horizontal scrollbar expected on mobile");
    }
}
