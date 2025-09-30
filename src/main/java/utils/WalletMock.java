package utils;

import org.openqa.selenium.WebDriver;

/**
 * Lightweight wallet mock injected into the page to simulate window.ethereum,
 * account switching, balances, and typical dApp flows without controlling a real wallet.
 *
 * This relies on the app using window.ethereum / EIP-1193 and reacting to 'accountsChanged' events.
 */
public class WalletMock {
    private final WebDriver driver;
    private final JsUtil js;

    public WalletMock(WebDriver driver) {
        this.driver = driver;
        this.js = new JsUtil(driver);
    }

    public void inject() {
        String script =
                "(() => {\n" +
                        "  if (window.__walletMockInjected) return;\n" +
                        "  const listeners = { accountsChanged: [] };\n" +
                        "  let accounts = ['0x1234567890abcdef1234567890abcdef12345678'];\n" +
                        "  let chainId = '0x1';\n" +
                        "  let balances = {\n" +
                        "    '0x1234567890abcdef1234567890abcdef12345678': 10,\n" + // in ETH for UI display
                        "    '0xabcdefabcdefabcdefabcdefabcdefabcdefabcd': 3.5\n" +
                        "  };\n" +
                        "  const ethereum = {\n" +
                        "    isMetaMask: true,\n" +
                        "    request: async ({ method, params }) => {\n" +
                        "      switch (method) {\n" +
                        "        case 'eth_requestAccounts':\n" +
                        "        case 'eth_accounts':\n" +
                        "          return accounts;\n" +
                        "        case 'eth_chainId':\n" +
                        "          return chainId;\n" +
                        "        default:\n" +
                        "          return null;\n" +
                        "      }\n" +
                        "    },\n" +
                        "    on: (event, handler) => {\n" +
                        "      if (!listeners[event]) listeners[event] = [];\n" +
                        "      listeners[event].push(handler);\n" +
                        "    },\n" +
                        "    removeListener: (event, handler) => {\n" +
                        "      if (!listeners[event]) return;\n" +
                        "      listeners[event] = listeners[event].filter(h => h !== handler);\n" +
                        "    }\n" +
                        "  };\n" +
                        "  window.ethereum = ethereum;\n" +
                        "  window.__walletMockInjected = true;\n" +
                        "  window.__walletMockAPI = {\n" +
                        "    setAccount(addr) {\n" +
                        "      accounts = [addr];\n" +
                        "      (listeners['accountsChanged'] || []).forEach(fn => fn(accounts));\n" +
                        "    },\n" +
                        "    setBalance(addr, eth) { balances[addr] = eth; },\n" +
                        "    getBalance(addr) { return balances[addr] ?? 0; },\n" +
                        "    has(addr) { return !!balances[addr]; },\n" +
                        "    accounts() { return accounts; },\n" +
                        "    chainId() { return chainId; }\n" +
                        "  };\n" +
                        "})();";
        js.exec(script);
    }

    public void setAccount(String address) {
        js.exec("window.__walletMockAPI.setAccount(arguments[0]);", address);
    }

    public void setBalance(String address, double eth) {
        js.exec("window.__walletMockAPI.setBalance(arguments[0], arguments[1]);", address, eth);
    }

    public String getActiveAccount() {
        Object res = js.exec("return window.__walletMockAPI.accounts()[0];");
        return res == null ? null : String.valueOf(res);
    }

    public boolean hasAccount(String address) {
        Object res = js.exec("return window.__walletMockAPI.has(arguments[0]);", address);
        return Boolean.TRUE.equals(res);
    }
}
