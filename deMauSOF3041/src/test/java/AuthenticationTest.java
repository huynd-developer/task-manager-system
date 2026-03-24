import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationTest {
    private static WebDriver driver;
    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @ParameterizedTest
    @CsvSource({
            "Nguyễn Đình Huy,huyprobuild@gmail.com,huynd3@1234"
    })
    public void registration(String name,String email,String password){
        driver.get("https://hoctotlamhay.vn/register");
        WebElement nameFiel = driver.findElement(By.id("name"));
        WebElement emailFiel = driver.findElement(By.id("user_email"));
        WebElement passwordFiel = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Đăng ký')]"));
        nameFiel.sendKeys(name);
        emailFiel.sendKeys(email);
        passwordFiel.sendKeys(password);
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlToBe("https://hoctotlamhay.vn/login")
        ));
        String url =  driver.getCurrentUrl();
        assertEquals("https://hoctotlamhay.vn/login", url);
    }
    @ParameterizedTest
    @CsvSource({
            "huyprobuild5@gmail.com,huynd3@1234,Nguyễn Đình Huy"
    })
    public void loginTest(String email,String password,String expectedName){
        driver.get("https://hoctotlamhay.vn/login");
        WebElement emailFiel = driver.findElement(By.id("user_email"));
        WebElement passwordFiel = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Đăng nhập')]"));
        emailFiel.sendKeys(email);
        passwordFiel.sendKeys(password);
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlContains("https://hoctotlamhay.vn/"));
        WebElement nameElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'"+expectedName+"')]")
        ));
        assertTrue(nameElement.isDisplayed(),"Không tìm thấy tên: "+expectedName);

    }
    @AfterAll
    public static void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}