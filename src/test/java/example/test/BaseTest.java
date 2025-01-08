package example.test;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;

    @Before
    public void startUp() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Указываем путь к geckodriver.exe из файла конфигурации
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));

        // Инициализируем драйвер
        driver = new FirefoxDriver();
    }

    @After
    public void teardown() {
            driver.quit();
        
    }
}
