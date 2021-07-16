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

public class MyInfoPhoto {
    private static WebDriver browser;

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

    public static WebElement getById(String id) {
        return browser.findElement(By.id(id));
    }

    @Test
    void MyInfoPhotoFieldUpload() {
        browser.findElement(By.id("menu_pim_viewMyDetails")).click();
        wait(2000);
        getById("btnAddAttachment").click();
        wait(1000);
        getById("ufile").click();
        wait(5000);
        getById("btnSaveAttachment").click();
        wait(3000);

    }

    @AfterEach
    public void cleanUp() {
        browser.quit();
    }
}
