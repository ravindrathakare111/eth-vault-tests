package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JsUtil {
    private final WebDriver driver;

    public JsUtil(WebDriver driver) {
        this.driver = driver;
    }

    public Object exec(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    public Object exec(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public void setLocalStorage(String key, String value) {
        exec("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public String getLocalStorage(String key) {
        Object val = exec("return window.localStorage.getItem(arguments[0]);", key);
        return val == null ? null : String.valueOf(val);
    }
}
