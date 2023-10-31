package ru.yandex.praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.PageObject.MainPage;
import ru.yandex.praktikum.PageObject.RentPage;
import ru.yandex.praktikum.PageObject.SuccessOrderCreationPage;

import static ru.yandex.praktikum.PageObject.OrderButton.DOWN_BUTTON;
import static ru.yandex.praktikum.PageObject.OrderButton.UP_BUTTON;

@RunWith(Parameterized.class)
public class OrderScooterTest extends BaseTest {

    private final String orderButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String phoneNumber;
    private final String rentalPeriod;
    private final String colour;
    private final String comment;
    private boolean actual;


    public OrderScooterTest(String orderButton, String name, String surname, String address, String phoneNumber,
                            String rentalPeriod, String colour, String comment) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderForm() {
        return new Object[][] {
                {UP_BUTTON, "Жарков", "Дмитрий", "Москва", "+79998887766", "двое суток", "black", "Очень хочу самокат"},
                {DOWN_BUTTON, "Иванов", "Иван", "Калининград", "88005353535", "сутки", "grey", "Я надеюсь, что вы сможете привезти"}
        };
    }

    @Test
    public void checkOrderScooterValidData_expectScooterIsOrdered() {
        super.implicitlyWait(10);

        MainPage mainPage = new MainPage(driver);
        mainPage.Open();
        mainPage.clickCookie();
        mainPage.clickOrderButton(orderButton);

        RentPage rentPage = new RentPage(driver);
        rentPage.fillAllRequiredFields(name, surname, address, phoneNumber);
        rentPage.clickNextButton();
        rentPage.fillRequiredFields(rentalPeriod, colour, comment);
        rentPage.clickOrderButton();
        rentPage.waitRentPageWillBeLoaded();
        rentPage.clickConfirmationOrderButton();

        SuccessOrderCreationPage successOrderCreationPage = new SuccessOrderCreationPage(driver);
        actual = successOrderCreationPage.isSuccessOrderCreationMessageVisible();
        Assert.assertTrue("Expected: a message is displayed that the order was created successfully ",
                actual);
    }

}