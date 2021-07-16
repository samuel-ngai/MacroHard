package Timesheet;

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


import java.util.concurrent.TimeUnit;

public class TimeSheet {
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


    @Test
    void SearchTimeSheetForAnEmployee() {
        browser.findElement(By.id("menu_time_viewTimeModule")).click();
        wait(1000);
        browser.findElement(By.id("employee")).click();
        wait(1000);
        browser.findElement(By.id("employee")).sendKeys("Orange Test");
        wait(1000);
        browser.findElement(By.id("btnView")).click();
        wait(5000);
    }

    @AfterEach
    public void cleanUp() {
        browser.quit();
    }

}
