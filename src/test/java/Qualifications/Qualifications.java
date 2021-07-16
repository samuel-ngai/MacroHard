package Qualifications;

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

public class Qualifications {
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
        getById("menu_admin_viewAdminModule").click();
        getById("menu_admin_Qualifications").click();
        wait(1000);
    }

    public static String createARandomString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = size;
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

    public static WebElement getById(String id) {
        return browser.findElement(By.id(id));
    }

    @Test
    void AddDeleteSkill() {
        // Adding
        getById("menu_admin_viewSkills").click();
        wait(500);
        getById("btnAdd").click();
        getById("skill_name").click();
        getById("skill_name").sendKeys(createARandomString(10));
        getById("skill_description").click();
        getById("skill_description").sendKeys(createARandomString(70));
        wait(1000);
        getById("btnSave").click();


        // Deleting
        browser.findElement(By.className("checkboxAtch")).click();
        wait(500);
        getById("btnDel").click();
        wait(1000);


    }

    @Test
    void AddDeleteEducation() {
        // Adding
        getById("menu_admin_viewEducation").click();
        getById("btnAdd").click();
        getById("education_name").click();
        getById("education_name").sendKeys(createARandomString(10));
        wait(1000);
        getById("btnSave").click();


        // Deleting
        browser.findElement(By.className("checkboxAtch")).click();
        wait(500);
        getById("btnDel").click();
        wait(1000);

    }

    @Test
    void AddDeleteLicense() {
        // Adding
        getById("menu_admin_viewLicenses").click();
        getById("btnAdd").click();
        getById("license_name").click();
        getById("license_name").sendKeys(createARandomString(10));
        wait(1000);
        getById("btnSave").click();


        //Deleting
        browser.findElement(By.className("checkboxAtch")).click();
        wait(500);
        getById("btnDel").click();
        wait(1000);

    }

    @Test
    void AddDeleteLanguage() {
        // Adding
        getById("menu_admin_viewLanguages").click();
        getById("btnAdd").click();
        getById("language_name").click();
        getById("language_name").sendKeys(createARandomString(10));
        wait(1000);
        getById("btnSave").click();


        // Deleting
        browser.findElement(By.className("checkboxAtch")).click();
        wait(500);
        getById("btnDel").click();
        wait(1000);
    }

    @Test
    void AddDeleteMembership() {
        getById("menu_admin_membership").click();
        getById("btnAdd").click();
        getById("membership_name").click();
        getById("membership_name").sendKeys(createARandomString(10));
        wait(1000);
        getById("btnSave").click();
        wait(1000);


        // Deleting
        browser.findElement(By.name("chkSelectRow[]")).click();
        wait(500);
        getById("btnDelete").click();
        wait(1000);
        getById("dialogDeleteBtn").click();
        wait(3000);
    }


    @AfterEach
    public void cleanUp() {
        browser.quit();
    }
}
