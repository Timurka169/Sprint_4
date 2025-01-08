package example.test;

import example.page.MainPage;
import example.page.OrderPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PlacingOrderTest extends BaseTest {

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

    @Test
    public void checkPlacingOrder() {
        driver.get(BASE_URL);

        MainPage objMainPage = new MainPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

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

        objMainPage.clickCookieAcceptButton();
        objOrderPage.waitForOrderAboutUserLabel();
        objOrderPage.addUserInfoInOrder(userName, userSurname, address, subwayStation, userPhone);
        objOrderPage.waitForOrderAboutRentingLabel();
        objOrderPage.addRentingInfoInOrder(rentalStartDate, rentalPeriod, scooterColor, comment);
        objOrderPage.waitForOrderConfirmationLabel();
        objOrderPage.clickOrderConfirmationButton();
        
        //проверка текста сообщения Заказ оформлен
        assertTrue("Заказ должен быть оформлен", objOrderPage.isOrderConfirmed());
    }
}
