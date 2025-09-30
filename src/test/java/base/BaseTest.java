package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.WaitUtil;
import utils.WalletMock;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WaitUtil wait;
    protected WalletMock wallet;

    @Parameters({"baseUrl"})
    @BeforeMethod
    public void setUp(@Optional("http://localhost:3000") String baseUrl) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(baseUrl);

        wait = new WaitUtil(driver, Duration.ofSeconds(15));
        wait.pageLoaded();

        // Inject wallet mock for consistent automation
        wallet = new WalletMock(driver);
        wallet.inject();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
