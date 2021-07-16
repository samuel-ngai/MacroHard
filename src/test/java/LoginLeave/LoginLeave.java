package LoginLeave;

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

public class LoginLeave {

    WebDriver browser;

    @BeforeEach
    public void setUp() {
        // Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        browser.findElement(By.id("txtUsername")).sendKeys("Admin");
        browser.findElement(By.id("txtPassword")).sendKeys("admin123");
        browser.findElement(By.id("btnLogin")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

    }

    @Test
    public void AsseignLeaveTest(){
        browser.findElement(By.xpath("//*[@id=\"menu_leave_viewLeaveModule\"]/b")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("menu_leave_assignLeave")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("assignleave_txtFromDate")).clear();
        browser.findElement(By.id("assignleave_txtFromDate")).sendKeys("2021-05-01");
        browser.findElement(By.id("assignleave_txtToDate")).clear();
        browser.findElement(By.id("assignleave_txtToDate")).sendKeys("2021-06-30");
        browser.findElement(By.name("assignleave[txtEmployee][empName]")).sendKeys("Orange Test");
        WebElement dropList = browser.findElement(By.id("assignleave_txtLeaveType"));
        Select dropList2 = new Select(dropList);
        ((Select) dropList2).selectByValue("8");


        java.lang.String before = browser.findElement(By.id("assignleave_leaveBalance")).getText();
        try {
            Thread.sleep(5000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("assignBtn")).click();

        try {
            Thread.sleep(5000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("confirmOkButton")).click();

        try {
            Thread.sleep(3000);
        } catch(InterruptedException ignored) {
        }

        assertNotEquals(browser.findElement(By.id("assignleave_leaveBalance")).getText(), before);
    }

    @Test
    public void AsseignInvalidUserLeaveTest(){
        browser.findElement(By.xpath("//*[@id=\"menu_leave_viewLeaveModule\"]/b")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("menu_leave_assignLeave")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("assignleave_txtFromDate")).clear();
        browser.findElement(By.id("assignleave_txtFromDate")).sendKeys("2021-05-01");
        browser.findElement(By.id("assignleave_txtToDate")).clear();
        browser.findElement(By.id("assignleave_txtToDate")).sendKeys("2021-06-30");
        browser.findElement(By.name("assignleave[txtEmployee][empName]")).sendKeys("jjj");
        WebElement dropList = browser.findElement(By.id("assignleave_txtLeaveType"));
        Select dropList2 = new Select(dropList);
        ((Select) dropList2).selectByValue("8");

        try {
            Thread.sleep(2000);
        } catch(InterruptedException ignored) {
        }

        assertEquals(browser.findElement(By.xpath("//*[@id=\"frmLeaveApply\"]/fieldset/ol/li[1]/span")).getText(), "Invalid");
    }

    @Test
    public void AsseignInvalidTimeLeaveTest(){
        browser.findElement(By.xpath("//*[@id=\"menu_leave_viewLeaveModule\"]/b")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("menu_leave_assignLeave")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("assignleave_txtFromDate")).clear();
        browser.findElement(By.id("assignleave_txtFromDate")).sendKeys("2021-06-01");
        browser.findElement(By.id("assignleave_txtToDate")).clear();
        browser.findElement(By.id("assignleave_txtToDate")).sendKeys("2021-05-30");
        browser.findElement(By.name("assignleave[txtEmployee][empName]")).sendKeys("Orange Test");
        WebElement dropList = browser.findElement(By.id("assignleave_txtLeaveType"));
        Select dropList2 = new Select(dropList);
        ((Select) dropList2).selectByValue("8");

        try {
            Thread.sleep(2000);
        } catch(InterruptedException ignored) {
        }

        assertEquals(browser.findElement(By.xpath("//*[@id=\"frmLeaveApply\"]/fieldset/ol/li[5]/span")).getText(), "To date should be after from date");
    }

    @Test
    public void AsseignValidTimeLeaveListTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/viewLeaveList");
        browser.findElement(By.id("calFromDate")).clear();
        browser.findElement(By.id("calFromDate")).sendKeys("2021-06-01");
        browser.findElement(By.id("calToDate")).clear();
        browser.findElement(By.id("calToDate")).sendKeys("2021-06-30");
        assertNotEquals(browser.findElement(By.xpath("//*[@id=\"frmFilterLeave\"]/fieldset/ol/li[2]/span")).getText(), "To date should be after from date");
    }

    @Test
    public void AsseignInvalidTimeLeaveListTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/viewLeaveList");
        browser.findElement(By.id("calFromDate")).clear();
        browser.findElement(By.id("calFromDate")).sendKeys("2021-06-01");
        browser.findElement(By.id("calToDate")).clear();
        browser.findElement(By.id("calToDate")).sendKeys("2021-05-30");
        assertEquals(browser.findElement(By.xpath("//*[@id=\"frmFilterLeave\"]/fieldset/ol/li[2]/span")).getText(), "To date should be after from date");
    }

    @Test
    public void ChangeLeaveTypeTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/leaveTypeList");
        browser.findElement(By.name("btnAdd")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        browser.findElement(By.id("leaveType_txtLeaveTypeName")).sendKeys("Chinese New Year2");
        browser.findElement(By.id("saveButton")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        assertEquals(browser.findElement(By.xpath("//*[@id=\"search-results\"]/div[1]/h1")).getText(), "Leave Types");
    }

    //WorkWeek_day_length_Friday
    @Test
    public void WorkWeekChangeFridayTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/defineWorkWeek");

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        browser.findElement(By.id("saveBtn")).click();
        WebElement dropList = browser.findElement(By.id("WorkWeek_day_length_Friday"));
        Select dropList2 = new Select(dropList);
        ((Select) dropList2).selectByValue("8");

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("saveBtn")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        browser.findElement(By.id("saveBtn")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        assertEquals(browser.findElement(By.xpath("//*[@id=\"WorkWeek_day_length_Friday\"]/option[3]")).getAttribute("selected"), "true");
    }

    @Test
    public void HolidayTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/viewHolidayList");
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        browser.findElement(By.id("btnAdd")).click();
        browser.findElement(By.id("holiday_description")).sendKeys("Eid");
        browser.findElement(By.id("holiday_date")).clear();
        browser.findElement(By.id("holiday_date")).sendKeys("2021-08-05");
        browser.findElement(By.id("saveBtn")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }



        assertEquals(browser.findElement(By.xpath("//*[@id=\"holiday-information\"]/a")).getText(), ">");
    }

    @Test
    public void LogOut(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admin");
        Password.sendKeys("admin123");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.findElement(By.id("welcome")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        browser.findElement(By.xpath("//*[@id=\"welcome-menu\"]/ul/li[3]/a")).click();

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("", browser.findElement(By.id("txtUsername")).getText());
    }

    // needs some revising
    @Test
    public void LogOutUsingBackNav(){
        browser.get("https://google.com");
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admin");
        Password.sendKeys("admin123");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }

        browser.navigate().back();
        browser.navigate().back();

        try {
            Thread.sleep(3000);
        } catch(InterruptedException ignored) {
        }

        //String option=dropdown2.getFirstSelectedOption().getText();
        assertNotEquals("https://opensource-demo.orangehrmlive.com/index.php/dashboard", browser.getCurrentUrl());
    }

    @Test
    public void SuccesfulLogIn(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admin");
        Password.sendKeys("admin123");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("Welcome Paul", browser.findElement(By.id("welcome")).getText());
    }

    @Test
    public void RightUsernameWrongPasswordLogIn(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admin");
        Password.sendKeys("admin12");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("Invalid credentials", browser.findElement(By.id("spanMessage")).getText());
    }

    @Test
    public void WrongUsernameWrongPasswordLogIn(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admi");
        Password.sendKeys("admin12");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("Invalid credentials", browser.findElement(By.id("spanMessage")).getText());
    }

    @Test
    public void WrongUsernameRightPasswordLogIn(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        WebElement UserName = browser.findElement(By.id("txtUsername"));
        WebElement Password = browser.findElement(By.id("txtPassword"));
        WebElement LogInBTN = browser.findElement(By.id("btnLogin"));
        UserName.sendKeys("Admi");
        Password.sendKeys("admin123");
        LogInBTN.click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("Invalid credentials", browser.findElement(By.id("spanMessage")).getText());
    }

    @Test
    public void ForgotMyPassword(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        browser.findElement(By.id("forgotPasswordLink")).click();
        browser.findElement(By.id("securityAuthentication_userName")).sendKeys("Admin");//securityAuthentication_userName
        browser.findElement(By.id("btnSearchValues")).click();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ignored) {
        }
        //String option=dropdown2.getFirstSelectedOption().getText();
        assertEquals("Instruction Sent !", browser.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div[2]/h1")).getText());
    }



    @AfterEach
    public void cleanUp() {
        browser.quit();
    }
}