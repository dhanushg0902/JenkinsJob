package Jenkins.Jenkins;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SauceDemoLoginTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test(dataProvider="testdata")
    public void validateLogin(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        passwordInput.sendKeys(password);
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();
        
        try {
            WebElement inventoryContainer = driver.findElement(By.className("inventory_container"));
            Assert.assertTrue(inventoryContainer.isDisplayed(), "Login failed. Inventory container not displayed.");
        } catch (NoSuchElementException e) {
            Assert.fail("Login failed. Inventory container not found.");
        }
    }

    @DataProvider(name="testdata")
    public Object[][] testDataSupplier() {
        return new Object[][] {
            {"error_user", "secret_sauce"}
        };
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
