package MyInfo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class MyInfoEdit {
    WebDriver browser;

    public void wait(int Mseconds) {
        try {
            Thread.sleep(Mseconds);
        } catch(InterruptedException ignored) {
        }
    }

    public void login(WebDriver browser) {
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        browser.findElement(By.id("txtUsername")).sendKeys("Admin");
        browser.findElement(By.id("txtPassword")).sendKeys("admin123");
        browser.findElement(By.id("btnLogin")).click();
    }

    @BeforeEach
    public void setUp() {
        // Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        login(browser);
        wait(1000);
    }

    public static String createARandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    @Test
    void InfoEdit() {
        browser.findElement(By.id("menu_pim_viewMyDetails")).click();
        wait(2000);
        browser.findElement(By.id("btnSave")).click();
        wait(1000);
        browser.findElement(By.id("personal_txtEmpFirstName")).click();
        browser.findElement(By.id("personal_txtEmpFirstName")).clear();
        String someRandomString = createARandomString();
        browser.findElement(By.id("personal_txtEmpFirstName")).sendKeys(someRandomString);
        wait(1000);
        browser.findElement(By.id("btnSave")).click();
        wait(2000);
        assertEquals(someRandomString, browser.findElement(By.id("personal_txtEmpFirstName")).getAttribute("Value"));
    }

    @AfterEach
    public void cleanUp() {
        browser.quit();
    }
}
