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

public class ReportFunctionality {

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
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        login(browser);
        wait(1000);
    }

    @AfterEach
    public void cleanUp() {
        //Faster than browser.close();
        browser.quit();
    }


    @Test
    public void AsseignValidTimeLeavelistTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/viewMyLeaveList");
        browser.findElement(By.id("calFromDate")).clear();
        browser.findElement(By.id("calFromDate")).sendKeys("2021-06-01");
        browser.findElement(By.id("calToDate")).clear();
        browser.findElement(By.id("calToDate")).sendKeys("2021-06-30");
        assertNotEquals(browser.findElement(By.xpath("//*[@id=\"frmFilterLeave\"]/fieldset/ol/li[2]/span")).getText(), "To date should be after from date");
    }

    @Test
    public void EmployeeReportTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/core/viewDefinedPredefinedReports/reportGroup/3/reportType/PIM_DEFINED");
        wait(2000);
        browser.findElement(By.id("search_search")).clear();
        browser.findElement(By.id("search_search")).sendKeys("All Employee Sub Unit Hierarchy Report");
        browser.findElement(By.name("_search")).click();
        wait(2000);
        assertEquals(browser.findElement(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td[2]")).getText(), "All Employee Sub Unit Hierarchy Report");
    }

    @Test
    public void EmployeeEntitelmentReportTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/leave/viewLeaveBalanceReport");
        wait(2);

        WebElement droplist = browser.findElement(By.id("leave_balance_report_type"));
        droplist.click();
        Select dropdown = new Select(droplist);
        dropdown.selectByValue("2");

        wait(2000);
        WebElement input = browser.findElement(By.id("leave_balance_employee_empName"));
        input.sendKeys("Jacqueline White");
        wait(2000);
        browser.findElement(By.xpath("/html/body/div[4]/ul/li")).click();
        wait(2000);
        WebElement droplist2 = browser.findElement(By.id("period"));
        wait(2000);
        droplist2.click();
        wait(2000);
        Select dropdown2 = new Select(droplist);
        //dropdown2.selectByValue("2020-01-01$$2020-12-31");
        wait(2000);
        browser.findElement(By.id("viewBtn")).click();
        wait(2000);

        assertEquals(browser.findElement(By.xpath("//*[@id=\"report-results\"]/div/table/tbody/tr[1]/td[2]/a")).getText(), "0.00");
    }

    @Test
    public void EmployeeReportAddition(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
        wait(2000);
        browser.findElement(By.id("firstName")).sendKeys("Omar");
        browser.findElement(By.id("lastName")).sendKeys("Attia");
        WebElement btn = browser.findElement(By.id("btnSave"));
        btn.click();
        assertEquals(browser.findElement(By.xpath("//*[@id=\"profile-pic\"]/h1")).getText(),"Omar Attia");
    }

    @Test
    public void EmployeeReportDeletion(){

        browser.get("https://opensource-demo.orangehrmlive.com/index.php/pim/viewEmployeeList/reset/1");
        wait(1000);
        browser.findElement(By.id("ohrmList_chkSelectRecord_2")).click();
        wait(1000);

        WebElement btn = browser.findElement(By.id("btnDelete"));
        btn.click();

        assertNotEquals(browser.findElement(By.xpath("//*[@id=\"employee-information\"]/a")).getText(),"Employee Information");

    }

    @Test
    void ProjecTimetReportValidDatesTime(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/time/displayProjectReportCriteria?reportId=1");
        WebElement droplist = browser.findElement(By.id("time_project_name"));
        droplist.click();
        Select dropdown = new Select(droplist);
        dropdown.selectByValue("2");

        browser.findElement(By.id("project_date_range_from_date")).clear();
        browser.findElement(By.id("project_date_range_from_date")).sendKeys("2021-07-30");
        browser.findElement(By.id("project_date_range_to_date")).clear();
        browser.findElement(By.id("project_date_range_to_date")).sendKeys("2021-08-30");
        browser.findElement(By.id("viewbutton")).click();
        assertEquals("Project Report",browser.findElement(By.xpath("//*[@id=\"search-results\"]/div[1]/h1")).getText());
    }

    @Test
    void EmployeeTimeReport(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/time/displayEmployeeReportCriteria?reportId=2");
        WebElement name = browser.findElement(By.id("employee_empName"));
        name.sendKeys("O");
        browser.findElement(By.xpath("/html/body/div[5]/ul/li[1]")).click();
        WebElement droplist = browser.findElement(By.id("time_project_name"));
        droplist.click();
        Select dropdown = new Select(droplist);
        dropdown.selectByValue("2");

        WebElement droplist2 = browser.findElement(By.id("time_activity_name"));
        droplist2.click();
        Select dropdown2 = new Select(droplist);
        dropdown2.selectByValue("2");

        browser.findElement(By.id("project_date_range_from_date")).clear();
        browser.findElement(By.id("project_date_range_from_date")).sendKeys("2021-07-30");
        browser.findElement(By.id("project_date_range_to_date")).clear();
        browser.findElement(By.id("project_date_range_to_date")).sendKeys("2021-08-30");
        wait(1000);
        browser.findElement(By.id("viewbutton")).click();

        assertEquals(browser.findElement(By.xpath("//*[@id=\"search-results\"]/div[1]/h1")).getText(),"Employee Report");

    }

    @Test
    void EmployeeAttendenceTest(){
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/time/displayAttendanceSummaryReportCriteria?reportId=4");
        WebElement name = browser.findElement(By.id("employee_name"));
        name.sendKeys("O");
        browser.findElement(By.xpath("/html/body/div[5]/ul/li[1]")).click();
        browser.findElement(By.id("viewbutton")).click();
        assertEquals(browser.findElement(By.xpath("//*[@id=\"search-results\"]/div[2]/div/dl/dt")).getText(),"Employee Name");
    }
}
