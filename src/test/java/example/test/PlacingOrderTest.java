package example.test;

import example.page.MainPage;
import example.page.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PlacingOrderTest {

    private WebDriver driver;
    private final int orderButtonPlace;
    private final String userName;
    private final String userSurname;
    private final String address;
    private final String subwayStation;
    private final String userPhone;
    private final String rentalStartDate;
    private final String rentalPeriod;
    private final String scooterColor;
    private final String comment;

    public PlacingOrderTest(int orderButtonPlace, String userName, String userSurname, String address,
                            String subwayStation, String userPhone, String rentalStartDate, String rentalPeriod,
                            String scooterColor, String comment) {
        this.orderButtonPlace = orderButtonPlace;
        this.userName = userName;
        this.userSurname = userSurname;
        this.address = address;
        this.subwayStation = subwayStation;
        this.userPhone = userPhone;
        this.rentalStartDate = rentalStartDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getInputData() {
        return new Object[][]{
                {1, "Иван", "Иванов", "Москва, ул. Тверская, д. 1", "Тверская", "+79001234567", "10.12.2024", "двое суток", "чёрный", "Позвонить заранее"},
                {2, "Мария", "Петрова", "Москва, ул. Арбат, д. 10", "Арбатская", "+79007654321", "15.12.2024", "трое суток", "серый", "Не звонить"}
        };
    }

    @Before
    public void startUp() {
        // Указываем путь к geckodriver.exe
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\12tim\\IdeaProjects\\test4\\src\\main\\resources\\geckodriver.exe");

        // Инициализируем драйвер
        driver = new FirefoxDriver();
    }

    @Test
    public void checkPlacingOrder() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        // Кликаем на нужную кнопку заказа
        switch (orderButtonPlace) {
            case 1:
                objMainPage.clickOrderUpperButton();
                break;
            case 2:
                objMainPage.clickOrderLowerButton();
                break;
            default:
                throw new IllegalArgumentException("Некорректное значение orderButtonPlace: " + orderButtonPlace);
        }

        // Принимаем куки (если это необходимо)
        objMainPage.clickCookieAcceptButton();

        // Заполняем информацию о пользователе
        objOrderPage.waitForOrderAboutUserLabel();
        objOrderPage.addUserInfoInOrder(userName, userSurname, address, subwayStation, userPhone);

        // Заполняем информацию об аренде
        objOrderPage.waitForOrderAboutRentingLabel();
        objOrderPage.addRentingInfoInOrder(rentalStartDate, rentalPeriod, scooterColor, comment);

        // Подтверждаем заказ
        objOrderPage.waitForOrderConfirmationLabel();
        objOrderPage.clickOrderConfirmationButton();

        // Проверяем, что заказ был успешно оформлен
        assertTrue("Заказ должен быть подтвержден", objOrderPage.isOrderConfirmed());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
