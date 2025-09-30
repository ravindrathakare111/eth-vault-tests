#  ETHVault Selenium UI Test Framework (Gradle)

##  Overview
This project is a **Selenium + Java + TestNG** automation framework for testing the **ETHVault dApp** running locally at `http://localhost:3000`.

It includes:
- **Page Object Model (POM)** for clean separation of UI logic
- **WalletMock** utility to simulate `window.ethereum` (connect, account switch, balances)
- **Responsive layout tests** for tablet and mobile
- **Edge case coverage** (account switching, dust amounts, rejected transactions)
- **Gradle build system** with TestNG integration

---


---

##  Prerequisites
- **Java 17+**
- **Gradle 7+** (or use the Gradle wrapper `./gradlew`)
- **Google Chrome** installed

---

## Dependencies
Defined in `build.gradle`:
- Selenium Java 4.25.0
- TestNG 7.10.2
- WebDriverManager 5.9.2

---
###  Wallet Mock
Because Selenium cannot interact with real MetaMask popups, the framework injects a WalletMock into the page:

Provides eth_requestAccounts, eth_accounts, and eth_chainId

Simulates account switching (accountsChanged event)

Allows tests to set balances per account

Ensures consistent, automated flows without manual wallet interaction

###  Test Coverage
DashboardTests → TC-1 to TC-7

DepositTests → TC-8 to TC-10

NavigationTests → TC-11

ResponsiveLayoutTests → TC-12, TC-13

EdgeCaseTests → Stake entire balance, account switch, reject tx, dust amount, refresh during pending


## Running Tests

### Run all tests
```bash
gradle clean test

### Run all tests
gradle clean test --info

### OR in bash run with following

./gradlew clean test

### Reports to be found 
build/reports/tests/test/index.html# eth-vault-tests
