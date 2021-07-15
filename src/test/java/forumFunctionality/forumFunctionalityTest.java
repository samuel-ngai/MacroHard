package forumFunctionality;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


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

        waitForPage();
        browser.get("https://opensource-demo.orangehrmlive.com/index.php/buzz/viewBuzz");
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
        String id = createReply(replyText);
        assertEquals(replyText, browser.findElement(By.xpath("//div[@id='commentListContainer_" + id + "']//div[@class='commentContent']")).getText());
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

    @Test
    public void LikeAReply(){
        createPost(postText);
        waitForPage();
        String id = createReply(replyText);
        likeReply(id);
        waitForPage();
        assertEquals("1", browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//div[@id='noOfLikesLinknew']/a/span")).getText());
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

    // Fails as the functionality is wrong
    @Test
    public void UnlikeAReply(){
        createPost(postText);
        waitForPage();
        String id = createReply(replyText);
        likeReply(id);
        waitForPage();
        unlikeReply(id);
        assertEquals("0", browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//div[@id='noOfLikesLinknew']/a/span")).getText());
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

    @Test
    public void DeleteAReply(){
        createPost(postText);
        waitForPage();
        String id = createReply(replyText);
        deleteReply(id);
        waitForPage();
        try {
            browser.findElement(By.xpath("//div[@id='commentListContainer_" + id + "']//div[@class='commentContent']")).getText();
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
        WebElement textbox = browser.findElement(By.id("createPost_content"));
        textbox.sendKeys(text);
        browser.findElement(By.id("postSubmitBtn")).click();
    }

    public String createReply(String text) {
        waitForPage();
        String num = browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//div[@class='postCommentBox']//form")).getAttribute("class");
        //WebElement textbox = browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@name='createComment[comment]']"));
        WebElement textbox = browser.findElement(By.id("commentBoxNew_listId" + num));
        textbox.click();
        textbox.sendKeys(text);

        //alternate:
        //JavascriptExecutor executor = (JavascriptExecutor)browser;
        //executor.executeScript("arguments[0].value = 'Testing Create Reply';", textbox);
        browser.findElement(By.xpath("//form[@class='" + num + "']/input[@class='commentSubmitBtn submitBtn']")).click();
        waitForPage();
        return num;
    }

    public void deletePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodyFirstRow\"]/div[@id=\"postFirstRowColumnThree\"]/div")).click();
        waitForPage();
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]/div[@id=\"postBodyFirstRow\"]/div[@id=\"postFirstRowColumnThree\"]/div/div/div/ul//*[@class=\"deleteShare\"]")).click();
        browser.findElement(By.id("delete_confirm")).click();
    }

    public void deleteReply(String id) {
        browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//div[@id='commentColumnThree']/div/div/a")).click();
        waitForPage();
        browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//a[@class='deleteComment']")).click();
    }

    public void likePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@class='likeLinknew']")).click();
    }

    public void unlikePost() {
        browser.findElement(By.xpath("//*[@id=\"buzz\"]/li/div[@id=\"postBody\"]//*[@class='likeLinknew']")).click();
    }

    public void likeReply(String id) {
        browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//div[@class='likeCommentnew']")).click();
    }

    public void unlikeReply(String id) {
        browser.findElement(By.xpath("//*[@id='commentListContainer_" + id + "']//div[@class='likeCommentnew']")).click();
    }


    @AfterEach
    public void cleanUp() {
        browser.quit();
    }

}
