package ru.yandex.praktikum.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentPage {
    private WebDriver driver;

    //Локатор на поле "Имя"
    private By nameInput = By.xpath(".//input[@placeholder= '* Имя']");

    //Локатор на поле "Фамилия"
    private By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");

    //Локатор на поле "Адрес"
    private By addressInput = By.xpath(".//input[@placeholder= '* Адрес: куда привезти заказ']");

    //Локатор на поле "Станция метро"
    private By metroStationInput = By.xpath(".//input[@placeholder= '* Станция метро']");

    //Локатор на поле "Телефон"
    private By phoneNumberInput = By.xpath(".//input[@placeholder= '* Телефон: на него позвонит курьер']");

    //Локатор на клавишу "Далее"
    private By nextButton = By.xpath(".//div[@class = 'Order_NextButton__1_rCA']/button[text()='Далее']");

    //Локатор на поле "Когда привезти самокат"
    private By dateDeliveryInput = By.xpath(".//input[@placeholder = '* Когда привезти самокат']");

    //Локатор на поле "Cрок аренды"
    private By rentalPeriodInput = By.className("Dropdown-root");

    //Локатор на поле "Комментарий для курьера"
    private By commentInput = By.xpath(".//input[@placeholder = 'Комментарий для курьера']");

    //Локатор на клавишу "Заказать"
    private By orderButton = By.xpath(".//div[@class = 'Order_Buttons__1xGrp']/button[text()='Заказать']");

    //Локатор на клавишу "Да"
    private By confirmationOrderButton = By.xpath(".//div[@class = 'Order_Buttons__1xGrp']/button[text() = 'Да']");

    public RentPage(WebDriver driver) {
        this.driver = driver;
    }

    // Вводим имя в поле
    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    //Вводим фамилию в поле
    public void setSurname(String surname) {
        driver.findElement(surnameInput).sendKeys(surname);
    }

    // Вводим адрес в поле
    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    // Вводим номер телефона в поле
    public void setPhoneNumber(String phoneNumber) {
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
    }
    //Выбираем станцию метро
    public void chooseMetroStation() {
        driver.findElement(metroStationInput).click();
        driver.findElement(metroStationInput).sendKeys(Keys.DOWN);
        driver.findElement(metroStationInput).sendKeys(Keys.ENTER);
    }
    public void fillAllRequiredFields(String name, String surName, String address, String phoneNumber) {
        setName(name);
        setSurname(surName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        chooseMetroStation();
    }

    //Кликаем на кливишу "Далее"
    public void clickNextButton(){
        driver.findElement(nextButton).click();
    }

    public void waitRentPageWillBeLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(dateDeliveryInput));
    }

    //Выбираем дату доставки, через получение текущей даты и прибавление 1го дня
    public void enterValidDeliveryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = LocalDate.now().plusDays(1).format(formatter);
        driver.findElement(dateDeliveryInput).sendKeys(date);
        driver.findElement(dateDeliveryInput).sendKeys(Keys.ENTER);
    }

    //Выбираем срок аренды из выподающего списка
    public void chooseRentalPeriod(String rentalPeriod) {
        driver.findElement(rentalPeriodInput).click();
        String rentalPeriodLocator = String.format(".//div[text() = '%s']", rentalPeriod);
        driver.findElement(By.xpath(rentalPeriodLocator)).click();
    }

    //Выбираем цвет самоката, кликом на чек бокс
    public void chooseColour(String colour) {
        driver.findElement(By.id(colour)).click();
    }

    //Вводим комментарий в поле
    public void setComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }
    public void fillRequiredFields(String rentalPeriod, String colour, String comment) {
        enterValidDeliveryDate();
        chooseRentalPeriod(rentalPeriod);
        chooseColour(colour);
        setComment(comment);
    }

    //Кликаем по клавише "Заказать"
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    //Кликаем по клавише "Да" в всплывающем окне подтверждения
    public void clickConfirmationOrderButton() {
        driver.findElement(confirmationOrderButton).click();
    }
}