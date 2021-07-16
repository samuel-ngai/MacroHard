package organizationsFunctionality;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class organizationTests {

    WebDriver browser;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        //browser.manage().window().maximize();
    }

    @AfterEach
    public void cleanUp() {
        //Faster than browser.close();
        browser.quit();
    }

    @Test
    public void testEditGeneralInformation() {

        logIn();
        navigateAdminOrg();

        browser.findElement(By.id("menu_admin_viewOrganizationGeneralInformation")).click();

        WebElement taxID = browser.findElement(By.name("organization[taxId]"));
        WebElement phoneNumber = browser.findElement(By.name("organization[phone]"));
        WebElement faxNumber = browser.findElement(By.name("organization[fax]"));
        WebElement zipCode = browser.findElement(By.name("organization[zipCode]"));

        browser.findElement(By.name("btnSaveGenInfo")).click();

        //Input new tax ID
        clickClearSendkeys(taxID, "123456");

        //Input new phone
        clickClearSendkeys(phoneNumber, "6041231234");

        //Input new Fax address
        clickClearSendkeys(faxNumber, "+1 123 123 1234");

        //Input new postal code
        clickClearSendkeys(zipCode, "V4V4M9");

        //Save changes
        browser.findElement(By.name("btnSaveGenInfo")).click();

        //Reloading elements after saving
        taxID = browser.findElement(By.name("organization[taxId]"));
        phoneNumber = browser.findElement(By.name("organization[phone]"));
        faxNumber = browser.findElement(By.name("organization[fax]"));
        zipCode = browser.findElement(By.name("organization[zipCode]"));

        //Assert changes
        assertEquals(taxID.getAttribute("value"), "123456");
        assertEquals(phoneNumber.getAttribute("value"), "6041231234");
        assertEquals(faxNumber.getAttribute("value"), "+1 123 123 1234");
        assertEquals(zipCode.getAttribute("value"), "V4V4M9");

    }

    @Test
    public void testAddLocation() {

        logIn();
        wait(500);
        navigateAdminOrg();
        wait(500);
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        browser.findElement(By.name("btnAdd")).click();
        wait(500);

        WebElement name = browser.findElement(By.name("location[name]"));
        WebElement country = browser.findElement(By.name("location[country]"));
        WebElement province = browser.findElement(By.name("location[province]"));
        WebElement city = browser.findElement(By.name("location[city]"));
        WebElement address = browser.findElement(By.name("location[address]"));
        WebElement zipCode = browser.findElement(By.name("location[zipCode]"));
        WebElement phone = browser.findElement(By.name("location[phone]"));
        WebElement fax = browser.findElement(By.name("location[fax]"));
        WebElement notes = browser.findElement(By.name("location[notes]"));

        //Set location name
        clickClearSendkeys(name, "UVic");

        //Set location's country (drop down tab)
        country.click();
        Select dropdown = new Select(country);
        dropdown.selectByValue("CA");

        //Set location's province
        clickClearSendkeys(province, "British Columbia");

        //Set location's city
        clickClearSendkeys(city, "Victoria");

        //Set location's address
        clickClearSendkeys(address, "123456");

        //Set location's zipCode
        clickClearSendkeys(zipCode, "123456");

        //Set phone number
        clickClearSendkeys(phone, "6041231234");

        //Set fax number
        clickClearSendkeys(fax, "+1 123 123 1234");

        //Set notes
        clickClearSendkeys(notes, "Test Location");

        browser.findElement(By.name("btnSave")).click();

        //Find elements in locations table
        String locationName = "";
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        for(WebElement element: table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                locationName = element.getText();
            }
        }
        assertEquals(locationName, "UVic Victoria Canada 6041231234 0");
        deleteLocation("UVic Victoria Canada 6041231234 0");
    }


    @Test
    public void testDeleteLocation() {

        logIn();
        navigateAdminOrg();
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        wait(500);
        addLocation();  //Adding test location
        wait(500);

        //Find elements in locations table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        for(WebElement element:table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                element.findElement(By.name("chkSelectRow[]")).click();
            }
        }
        //Delete location
        browser.findElement(By.id("btnDelete")).click();
        browser.findElement(By.id("dialogDeleteBtn")).click();

        //Reload table list elements & search for UVic
        table = browser.findElements(By.tagName("tr"));
        for(WebElement element: table) {
            String location = element.getText();
            assertNotEquals(location, "UVic Victoria Canada 6041231234 0");
        }
    }

    @Test
    public void testSearchLocationByName() {

        logIn();
        navigateAdminOrg();
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        wait(500);
        addLocation();  //Adding test location
        wait(500);

        //Search for UVic by name
        WebElement searchBox = browser.findElement(By.name("searchLocation[name]"));
        clickClearSendkeys(searchBox, "UVic");
        browser.findElement(By.id("btnSearch")).click();

        //Find elements in locations table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        String location = "";
        for(WebElement element:table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                location = element.getText();
            }
        }
        assertEquals(location, "UVic Victoria Canada 6041231234 0");
        deleteLocation("UVic Victoria Canada 6041231234 0");
    }

    @Test
    public void testSearchLocationByCity() {

        logIn();
        navigateAdminOrg();
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        wait(500);
        addLocation();  //Adding test location
        wait(500);

        //Search for Victoria locations
        WebElement searchBox = browser.findElement(By.name("searchLocation[city]"));
        clickClearSendkeys(searchBox, "Victoria");
        browser.findElement(By.id("btnSearch")).click();

        //Find elements in locations table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        String str = "";
        for(WebElement element:table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                str = element.getText();
            }
        }
        assertEquals(str, "UVic Victoria Canada 6041231234 0");
        deleteLocation("UVic Victoria Canada 6041231234 0");

    }

    @Test
    public void testSearchLocationByCountry() {

        logIn();
        navigateAdminOrg();
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        wait(500);
        addLocation();  //Adding test location
        wait(500);

        WebElement country = browser.findElement(By.name("searchLocation[country]"));
        Select dropdown = new Select(country);
        dropdown.selectByValue("CA");
        browser.findElement(By.id("btnSearch")).click();

        //Find elements in locations table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        String locationName = "";
        for(WebElement element:table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                locationName = element.getText();
            }
        }
        assertEquals(locationName, "UVic Victoria Canada 6041231234 0");
        deleteLocation("UVic Victoria Canada 6041231234 0");
    }

    @Test
    public void testEditOrgStructure(){

        logIn();
        //wait(500);
        navigateAdminOrg();
        //wait(500);
        browser.findElement(By.id("menu_admin_viewLocations")).click();
        wait(500);
        addLocation();
        wait(500);

        //Find elements in locations table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        for(WebElement element:table) {
            if(element.getText().equals("UVic Victoria Canada 6041231234 0")) {
                WebElement button = browser.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/form/div[4]/table/tbody/tr[5]/td[2]/a"));
                button.click();
            }
        }

        //Change location's information (UVic -> University Of Victoria)
        browser.findElement(By.name("btnSave")).click();
        WebElement locationName = browser.findElement(By.name("location[name]"));
        clickClearSendkeys(locationName, "University Of Victoria");
        browser.findElement(By.name("btnSave")).click();

        String locationInfo = "";
        table = browser.findElements(By.tagName("tr"));     //Update table list
        for(WebElement element:table) {
            if(element.getText().equals("University Of Victoria Victoria Canada 6041231234 0")) {
                locationInfo = element.getText();
            }
        }
        assertEquals(locationInfo, "University Of Victoria Victoria Canada 6041231234 0");
        deleteLocation("University Of Victoria Victoria Canada 6041231234 0");
    }

    public void logIn() {
        browser.get("https://opensource-demo.orangehrmlive.com/");
        WebElement username = browser.findElement(By.name("txtUsername"));
        username.sendKeys("Admin");
        WebElement password = browser.findElement(By.name("txtPassword"));
        password.sendKeys("admin123");
        WebElement Login = browser.findElement(By.name("Submit"));
        Login.click();
    }

    public void navigateAdminOrg(){
        browser.findElement(By.xpath("/html/body/div[1]/div[2]/ul/li[1]/a/b")).click();     //Admin dropdown menu
        browser.findElement(By.id("menu_admin_Organization")).click();
    }

    public void clickClearSendkeys(WebElement webElement, String string) {
        webElement.click();
        webElement.clear();            //Clears existing text in webElement
        webElement.sendKeys(string);
    }

    public void addLocation() {
        browser.findElement(By.name("btnAdd")).click();

        WebElement name = browser.findElement(By.name("location[name]"));
        WebElement country = browser.findElement(By.name("location[country]"));
        WebElement province = browser.findElement(By.name("location[province]"));
        WebElement city = browser.findElement(By.name("location[city]"));
        WebElement address = browser.findElement(By.name("location[address]"));
        WebElement zipCode = browser.findElement(By.name("location[zipCode]"));
        WebElement phone = browser.findElement(By.name("location[phone]"));
        WebElement fax = browser.findElement(By.name("location[fax]"));
        WebElement notes = browser.findElement(By.name("location[notes]"));

        //Set location information
        clickClearSendkeys(name, "UVic");
        country.click();
        Select dropdown = new Select(country);
        dropdown.selectByValue("CA");
        clickClearSendkeys(province, "British Columbia");
        clickClearSendkeys(city, "Victoria");
        clickClearSendkeys(address, "123456");
        clickClearSendkeys(zipCode, "123456");
        clickClearSendkeys(phone, "6041231234");
        clickClearSendkeys(fax, "+1 123 123 1234");
        clickClearSendkeys(notes, "Test Location");
        browser.findElement(By.name("btnSave")).click();

        //Refresh webpage
        browser.navigate().refresh();
    }

    public void deleteLocation(String name) {
        //Find all elements in location table
        List<WebElement> table = browser.findElements(By.tagName("tr"));
        for(WebElement element:table) {
            if(element.getText().equals(name)) {
                element.findElement(By.name("chkSelectRow[]")).click();
            }
        }
        browser.findElement(By.id("btnDelete")).click();
        browser.findElement(By.id("dialogDeleteBtn")).click();
    }

    public void wait(int Mseconds) {
        try {
            Thread.sleep(Mseconds);
        } catch(InterruptedException ignored) {
        }
    }
}
