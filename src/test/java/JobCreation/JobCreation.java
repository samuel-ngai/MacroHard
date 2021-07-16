package JobCreation;

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

public class JobCreation {
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

    // NOTE: We test the add and delete together for title and categories (as in one function for titles and one for categories)
    @Test
    void titles() {

        // ADDING
        getById("menu_admin_viewAdminModule").click();
        wait(1000);
        getById("menu_admin_Job").click();
        wait(1000);
        getById("menu_admin_viewJobTitleList").click();
        wait(2000);
        getById("btnAdd").click();
        wait(1000);
        getById("jobTitle_jobTitle").click();
        getById("jobTitle_jobTitle").sendKeys(createARandomString(7));
        getById("jobTitle_jobDescription").click();
        getById("jobTitle_jobDescription").sendKeys(createARandomString(10));
        getById("jobTitle_note").click();
        getById("jobTitle_note").sendKeys(createARandomString(55));
        wait(1000);
        getById("btnSave").click();
        wait(3000);

        // DELETING this will only work once because we test it once using a specific id which will get deleted
        String DeleteItemIndex = "ohrmList_chkSelectRecord_";
        DeleteItemIndex += String.valueOf(new Integer(1));
        getById(DeleteItemIndex).click();
        getById("btnDelete").click();
        getById("dialogDeleteBtn").click();
        wait(2000);
    }


    @Test
    void Categories() {

        // Adding
        getById("menu_admin_viewAdminModule").click();
        wait(1000);
        getById("menu_admin_Job").click();
        wait(1000);
        getById("menu_admin_jobCategory").click();
        getById("btnAdd").click();
        getById("jobCategory_name").click();
        getById("jobCategory_name").sendKeys(createARandomString(10));
        wait(1000);
        getById("btnSave").click();
        wait(3000);


        // DELETING this will only work once because we test it once using a specific id which will get deleted
        String CategoryTobeDeleted = "ohrmList_chkSelectRecord_";
        CategoryTobeDeleted += String.valueOf(new Integer(1));
        getById(CategoryTobeDeleted).click();
        wait(1000);
        getById("btnDelete").click();
        wait(2000);
        getById("dialogDeleteBtn").click();
        wait(2000);

    }


    @AfterEach
    public void cleanUp() {
        browser.quit();
    }
}
