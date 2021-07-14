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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class UVicTest {

    WebDriver browser;

    @BeforeEach
    public void setUp() {
        // Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        browser = new ChromeDriver();

        // Firefox
        // System.setProperty("webdriver.gecko.driver", "*****LOCATION OF YOUR WEBDRIVER*****");
        // browser = new FirefoxDriver();

        // Safari
        // browser = new SafariDriver();

        browser.manage().window().maximize();
    }

    //new WebDriverWait(browser,5).until(ExpectedConditions.titleIs("uvic -Google Search"));

    @AfterEach
    public void cleanUp() {
        browser.quit();
    }

    public WebElement validifyById(String id) {
        WebElement element = browser.findElement(By.id(id));
        assertNotNull(element);
        return element;
    }


    @Test
    public void googleSearchResultsAppear() {
        browser.get("https://www.google.com");
        WebElement inputBox = browser.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"));
        WebElement searchButton = browser.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]"));
        inputBox.sendKeys("uvic");
        searchButton.click();
        new WebDriverWait(browser, 5).until(ExpectedConditions.titleIs("uvic - Google Search"));
        assertEquals("uvic - Google Search", browser.getTitle());
    }

    @Test
    public void pageTitle() {
        browser.get("https://www.uvic.ca");
        assertEquals("Home - University of Victoria", browser.getTitle()); // task 1

        WebElement phoneNumber = browser.findElement(By.linkText("1-250-721-7211"));
        assertNotNull(phoneNumber); // task 6
    }

    @Test
    public void useSearchBar() {
        browser.get("https://www.uvic.ca");

        new WebDriverWait(browser,5).until(ExpectedConditions.titleIs("Home - University of Victoria"));

        WebElement searchBtn = validifyById("search-btn"); // task 2

        searchBtn.click();
        WebElement searchBar = validifyById("searchUVic"); // task 3

        searchBar.sendKeys("csc");
        assertEquals("csc", searchBar.getAttribute("value")); // task 4

        searchBar.sendKeys(Keys.ENTER);
        assertEquals("Search - University of Victoria", browser.getTitle()); // task 5
    }

}
