package forumFunctionality;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class forumFunctionalityTest {

    WebDriver browser;
    String postText = "Testing Create Post";
    String replyText = "Testing Create Reply";

    @BeforeEach
    public void setUp() {
        // Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
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
    public void CreateAPost(){
        createPost(postText);
        waitForPage();
        assertEquals(postText, browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodySecondRow\"]/div")).getText());
        deletePost();
    }

    @Test
    public void CreateAReply(){
        createPost(postText);
        waitForPage();
        createReply(replyText);
        assertEquals(replyText, browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@class='commentContent']")).getText());
        deletePost();
    }

    @Test
    public void LikeAPost(){
        createPost(postText);
        waitForPage();
        likePost();
        waitForPage();
        assertEquals("1", browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//div[@id='noOfLikesLinknew']/a/span")).getText());
        deletePost();
    }

    // Fails as the functionality is wrong
    @Test
    public void UnlikeAPost(){
        createPost(postText);
        waitForPage();
        likePost();
        waitForPage();
        unlikePost();
        assertEquals("0", browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//div[@id='noOfLikesLinknew']/a/span")).getText());
        deletePost();
    }

    @Test
    public void DeleteAPost(){
        createPost(postText);
        waitForPage();
        deletePost();
        waitForPage();
        try {
            browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodySecondRow\"]/div[0]")).getText();
            fail();
        } catch (NoSuchElementException e) {
            // We want it to pass if there is an exception!
        }
    }

    public void waitForPage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public void createPost(String text) {
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/buzz/viewBuzz");
        WebElement textbox = browser.findElement(By.id("createPost_content"));
        textbox.sendKeys(text);
        browser.findElement(By.id("postSubmitBtn")).click();
    }

    public void createReply(String text) {
        waitForPage();
        WebElement textbox = browser.findElement(By.xpath("//*[@name='createComment[comment]'][@class='commentBox']"));

        textbox.sendKeys(text);

        //alternate:
        //JavascriptExecutor executor = (JavascriptExecutor)browser;
        //executor.executeScript("arguments[0].value = 'Testing Create Reply';", textbox);
        browser.findElement(By.xpath("//*[@class='commentSubmitBtn submitBtn']")).click();
    }

    public void deletePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodyFirstRow\"]/div[@id=\"postFirstRowColumnThree\"]/div")).click();
        waitForPage();
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodyFirstRow\"]/div[@id=\"postFirstRowColumnThree\"]/div/div/div/ul//*[@class=\"deleteShare\"]")).click();
        browser.findElement(By.id("delete_confirm")).click();
    }

    public void likePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@class='likeLinknew']")).click();
    }

    public void unlikePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@class='likeLinknew']")).click();
    }


    @AfterEach
    public void cleanUp() {
        browser.quit();
    }

}
