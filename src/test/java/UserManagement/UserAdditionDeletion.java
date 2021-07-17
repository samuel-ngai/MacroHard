package UserAdditionDeletion;

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

import java.awt.*;
import java.util.List;
import java.util.Random;
public class UserAdditionDeletion {
    //Omar Attia
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
        wait(500);
    }

    @BeforeEach
    public void setUp() {
        // Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver"); //change this to "chromedriver"
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        login(browser);
        getById("menu_admin_viewAdminModule").click();
        wait(500);
        getById("menu_admin_UserManagement").click();
        wait(1000);
        getById("menu_admin_viewSystemUsers").click();
        wait(100);

    }

    public static WebElement getById(String id) {
        return browser.findElement(By.id(id));
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

    public void generateUsers(int n){
        for(int i = 0; i < n; i++){
            wait(1000);
            browser.findElement(By.xpath("//*[@id=\"btnAdd\"]")).click();
            wait(1000);

            String testingName = "Aaliyah Haq";
            String testingUserName = createARandomString();
            String testingPassword = createARandomString();



            WebElement employeeName = browser.findElement(By.name("systemUser[employeeName][empName]"));
            WebElement username = browser.findElement(By.name("systemUser[userName]"));
            WebElement password = browser.findElement(By.name("systemUser[password]"));
            WebElement confirmPass = browser.findElement(By.name("systemUser[confirmPassword]"));
            Select dropDown = new Select(browser.findElement(By.id("systemUser_userType")));

            dropDown.selectByValue("2"); //Ess
            employeeName.click();
            employeeName.sendKeys(testingName);
            username.click();
            username.sendKeys(testingUserName);
            password.click();
            password.sendKeys(testingPassword);
            confirmPass.click();
            confirmPass.sendKeys(testingPassword);
            wait(3000);
            browser.findElement(By.xpath("//*[@id=\"btnSave\"]")).click();
            wait(1000);
        }


    }

    @Test
    public void addEss(){

        getById("btnAdd").click();
        wait(1000);

        String testingName = "Garry White";
        String testingUserName = createARandomString();
        String testingPassword = createARandomString();



        WebElement employeeName = browser.findElement(By.name("systemUser[employeeName][empName]"));
        WebElement username = browser.findElement(By.name("systemUser[userName]"));
        WebElement password = browser.findElement(By.name("systemUser[password]"));
        WebElement confirmPass = browser.findElement(By.name("systemUser[confirmPassword]"));
        Select dropDown = new Select(browser.findElement(By.id("systemUser_userType")));

        dropDown.selectByValue("2"); //Ess
        employeeName.click();
        employeeName.sendKeys(testingName);
        username.click();
        username.sendKeys(testingUserName);
        password.click();
        password.sendKeys(testingPassword);
        confirmPass.click();
        confirmPass.sendKeys(testingPassword);

        browser.findElement(By.name("btnSave")).click();
        wait(1000);



    }

    @Test
    public void addAdmin(){

        getById("btnAdd").click();
        wait(1000);

        String testingName = "Aaliyah Haq"; // might not work
        String testingUserName = createARandomString();
        String testingPassword = createARandomString();

        WebElement employeeName = browser.findElement(By.name("systemUser[employeeName][empName]"));
        WebElement username = browser.findElement(By.name("systemUser[userName]"));
        WebElement password = browser.findElement(By.name("systemUser[password]"));
        WebElement confirmPass = browser.findElement(By.name("systemUser[confirmPassword]"));
        Select dropDown = new Select(browser.findElement(By.id("systemUser_userType")));



        dropDown.selectByValue("1"); //Admin
        employeeName.click();
        employeeName.sendKeys(testingName);
        username.click();
        username.sendKeys(testingUserName);
        password.click();
        password.sendKeys(testingPassword);
        confirmPass.click();
        confirmPass.sendKeys(testingPassword);

        browser.findElement(By.name("btnSave")).click();
        wait(1000);
    }

    @Test
    public void addEssInvalid(){
        getById("btnAdd").click();
        wait(1000);

        String testingName = "a";
        String testingUserName = createARandomString();
        String testingPassword = createARandomString();

        WebElement employeeName = browser.findElement(By.name("systemUser[employeeName][empName]"));
        WebElement username = browser.findElement(By.name("systemUser[userName]"));
        WebElement password = browser.findElement(By.name("systemUser[password]"));
        WebElement confirmPass = browser.findElement(By.name("systemUser[confirmPassword]"));
        Select dropDown = new Select(browser.findElement(By.id("systemUser_userType")));

        dropDown.selectByValue("2"); //Ess
        employeeName.click();
        employeeName.sendKeys(testingName);
        wait(500);
        employeeName.click();
        wait(500);

        assertEquals(browser.findElement(By.xpath("//*[@id=\"frmSystemUser\"]/fieldset/ol/li[2]/span")).getText(), "Employee does not exist");
        wait(1000);


    }

    @Test
    public void addAdminInvalid(){
        getById("btnAdd").click();
        wait(1000);

        String testingName = "a";
        String testingUserName = createARandomString();
        String testingPassword = createARandomString();

        WebElement employeeName = browser.findElement(By.name("systemUser[employeeName][empName]"));
        WebElement username = browser.findElement(By.name("systemUser[userName]"));
        WebElement password = browser.findElement(By.name("systemUser[password]"));
        WebElement confirmPass = browser.findElement(By.name("systemUser[confirmPassword]"));
        Select dropDown = new Select(browser.findElement(By.id("systemUser_userType")));

        dropDown.selectByValue("1"); //Admin
        employeeName.click();
        employeeName.sendKeys(testingName);
        wait(500);
        employeeName.click();
        wait(500);

        assertEquals(browser.findElement(By.xpath("//*[@id=\"frmSystemUser\"]/fieldset/ol/li[2]/span")).getText(), "Employee does not exist");
        wait(1000);
    }

    @Test
    void deleteUser(){
        generateUsers(1);

        browser.findElement(By.name("chkSelectRow[]")).click();
        wait(1000);
        browser.findElement(By.name("btnDelete")).click();
        wait(1000);
        browser.findElement(By.id("dialogDeleteBtn")).click();
        wait(1000);


    }

    @Test
    void deleteMultiple(){
        generateUsers(3);
        browser.findElement(By.name("chkSelectAll")).click();
        wait(1000);
        browser.findElement(By.name("btnDelete")).click();
        wait(1000);
        browser.findElement(By.id("dialogDeleteBtn")).click();
        wait(1000);

    }

    @Test
    void deleteAndSearch(){
        generateUsers(3);
        wait(1000);

        String aCurrentUser = "Aaliyah Haq";
        browser.findElement(By.name("chkSelectAll")).click();
        wait(1000);
        browser.findElement(By.name("btnDelete")).click();
        wait(1000);
        browser.findElement(By.id("dialogDeleteBtn")).click();
        wait(1000);
        WebElement searchBar = browser.findElement(By.name("searchSystemUser[userName]"));
        searchBar.sendKeys(aCurrentUser);
        wait(500);
        WebElement searchBtn = browser.findElement(By.id("searchBtn"));
        wait(500);
        searchBtn.click();
        wait(1000);
        browser.findElement(By.id("resetBtn")).click();
        wait(1000);




    }



    @AfterEach
    public void cleanUp() {
        browser.quit();
    }

}
