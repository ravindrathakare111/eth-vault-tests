package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.DepositModalPage;
import utils.WaitUtil;

public class DepositTests extends BaseTest {

    @Test(description = "TC-8: User can successfully deposit ETH")
    public void tc8_depositSuccess() {
        WaitUtil w = wait;
        DashboardPage d = new DashboardPage(driver, w);
        d.clickConnectWallet();
        String addr = wallet.getActiveAccount();
        wallet.setBalance(addr, 5.0);

        d.clickDeposit();
        DepositModalPage modal = new DepositModalPage(driver, w);
        Assert.assertTrue(modal.isOpen(), "Deposit modal should open");

        modal.enterAmount("1");
        Assert.assertTrue(modal.isConfirmEnabled(), "Confirm button should be enabled for valid amount");
        modal.clickConfirm();

        // App shows success when tx succeeds (mocked)
        w.sleep(500);
        Assert.assertTrue(modal.successVisible(), "Success notification should be visible");

        modal.close();
    }

    @Test(description = "TC-9: Prevent depositing more than wallet balance")
    public void tc9_depositInsufficient() {
        DashboardPage d = new DashboardPage(driver, wait);
        d.clickConnectWallet();
        String addr = wallet.getActiveAccount();
        wallet.setBalance(addr, 1.5);

        d.clickDeposit();
        DepositModalPage modal = new DepositModalPage(driver, wait);
        Assert.assertTrue(modal.isOpen(), "Deposit modal should open");

        modal.enterAmount("2");
        Assert.assertTrue(modal.hasError(), "Error message should appear");
        Assert.assertFalse(modal.isConfirmEnabled(), "Confirm should be disabled for invalid amount");
        modal.close();
    }

    @Test(description = "TC-10: Invalid deposit amount handling")
    public void tc10_depositInvalidAmounts() {
        DashboardPage d = new DashboardPage(driver, wait);
        d.clickConnectWallet();

        d.clickDeposit();
        DepositModalPage modal = new DepositModalPage(driver, wait);

        modal.enterAmount("abc");
        Assert.assertTrue(modal.hasError(), "Error for non-numeric input");
        Assert.assertFalse(modal.isConfirmEnabled(), "Confirm disabled");

        modal.enterAmount("-5");
        Assert.assertTrue(modal.hasError(), "Error for negative input");
        Assert.assertFalse(modal.isConfirmEnabled(), "Confirm disabled");

        modal.enterAmount("0");
        Assert.assertTrue(modal.hasError(), "Error for zero input");
        Assert.assertFalse(modal.isConfirmEnabled(), "Confirm disabled");

        modal.close();
    }
}
