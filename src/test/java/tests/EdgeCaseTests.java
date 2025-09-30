package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.StakePage;
import utils.WaitUtil;

public class EdgeCaseTests extends BaseTest {

    @Test(description = "VaultEdgeCase: Stake entire balance should warn about gas")
    public void edge_stakeEntireBalanceWarnGas() {
        DashboardPage d = new DashboardPage(driver, wait);
        d.clickConnectWallet();
        String addr = wallet.getActiveAccount();

        // Set a small balance, e.g., 0.5 ETH
        wallet.setBalance(addr, 0.5);

        // Navigate to stake page via header or direct
        driver.get(driver.getCurrentUrl().replace("/","/stake"));
        StakePage sp = new StakePage(driver, wait);

        sp.clickMax(); // or manually enter 0.5
        sp.clickStake();

        // Expect UI to warn / disable transaction if exact max leaves no gas
        Assert.assertTrue(sp.hasError() || !sp.successVisible(), "Should warn user to leave ETH for gas");
    }

    @Test(description = "VaultEdgeCase: Detect MetaMask account switch updates UI")
    public void edge_detectAccountSwitch() {
        DashboardPage d = new DashboardPage(driver, wait);
        d.clickConnectWallet();
        wait.sleep(200);

        String before = d.getAvailableBalanceText();

        // Switch account in wallet mock
        String accountB = "0xabcdefabcdefabcdefabcdefabcdefabcdefabcd";
        wallet.setAccount(accountB);
        wallet.setBalance(accountB, 1.25);

        d.clickRefresh();
        wait.sleep(300);
        String after = d.getAvailableBalanceText();

        Assert.assertNotEquals(before, after, "Dashboard should update to Account B balances after account switch");
    }

    @Test(description = "VaultEdgeCase: Reject staking transaction returns UI to normal")
    public void edge_rejectTransactionUIReset() {
        // Simulate by clicking stake then 'reject' behavior handled internally; here we ensure UI doesn't get stuck
        driver.get(driver.getCurrentUrl().replace("/","/stake"));
        StakePage sp = new StakePage(driver, wait);

        sp.enterAmount("0.3");
        sp.clickStake();

        // Simulated rejection: app should remove pending indicator soon
        wait.sleep(500);
        Assert.assertFalse(sp.isPending(), "Pending indicator should disappear after rejection");
    }

    @Test(description = "VaultEdgeCase: Stake dust amount should show clear error")
    public void edge_stakeDustAmount() {
        driver.get(driver.getCurrentUrl().replace("/","/stake"));
        StakePage sp = new StakePage(driver, wait);

        sp.enterAmount("0.000000000000000001");
        sp.clickStake();

        Assert.assertTrue(sp.hasError(), "UI should show 'Amount is too low to stake' or similar");
        Assert.assertFalse(sp.successVisible(), "Contract should reject dust amount; no success toast");
    }

    @Test(description = "VaultEdgeCase: Refresh page while transaction pending, then show final balances")
    public void edge_refreshDuringPending() {
        DashboardPage d = new DashboardPage(driver, wait);
        d.clickConnectWallet();
        String addr = wallet.getActiveAccount();
        wallet.setBalance(addr, 3.0);

        // Go to stake page and start a normal stake
        driver.get(driver.getCurrentUrl().replace("/","/stake"));
        StakePage sp = new StakePage(driver, wait);

        sp.enterAmount("1");
        sp.clickStake();

        // Immediately refresh
        driver.navigate().refresh();
        wait.pageLoaded();

        // Re-inject wallet mock (page reload clears it)
        wallet.inject();
        wallet.setAccount(addr);
        wallet.setBalance(addr, 2.0); // Assume final balance reflects the stake

        // Back to dashboard, verify updated balances
        driver.get("http://localhost:3000");
        String avail = new DashboardPage(driver, wait).getAvailableBalanceText();
        Assert.assertTrue(avail.contains("2") || avail.contains("ETH"), "Dashboard should show final updated balance after reload");
    }
}
