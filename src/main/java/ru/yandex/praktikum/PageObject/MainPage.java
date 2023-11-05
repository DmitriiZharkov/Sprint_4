package ru.yandex.praktikum.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.yandex.praktikum.PageObject.OrderButton.DOWN_BUTTON;
import static ru.yandex.praktikum.PageObject.OrderButton.UP_BUTTON;

public class MainPage {

    private final WebDriver driver;
    //Локатор верхней кнопки заказать
    private final By HeaderOrderButton =
            By.xpath(".//div[@class = 'Header_Nav__AGCXC']/button[text() = 'Заказать']");
    //Локатор нижней кнопки заказать
    private final By DownOrderButton =
            By.xpath(".//div[@class = 'Home_FinishButton__1_cWm']/button[text() = 'Заказать']");
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открытие главной страницы сервиса
    public void Open() {
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    //Нажатие на верхнюю или нижнюю кнопку заказать
    public void clickOrderButton(String orderButton) {
        if (orderButton.equals(UP_BUTTON)) {
            driver.findElement(HeaderOrderButton).click();
        } else if (orderButton.equals(DOWN_BUTTON)) {
            WebElement lowerOrderButton = driver.findElement(DownOrderButton);
            ((JavascriptExecutor) driver).
                    executeScript("arguments[0].scrollIntoView();", lowerOrderButton);
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(DownOrderButton));
            lowerOrderButton.click();
        }
    }

    //Клик на вопрос в разделе "вопросы о важном"
    public String clickQuest(int index) {
        By question = By.id(String.format("accordion__heading-%s", index));
        WebElement questionElement = driver.findElement(question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", questionElement);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(question));
        questionElement.click();
        return questionElement.getText();
    }

    //Получение текста ответа, после нажатия на клавишу вопроса
    public String answerDisplayed(int index) {
        WebElement answerElement = driver.findElement(By.id(String.format("accordion__panel-%s", index)));
        return answerElement.getText();
    }

    //Клик на клавишу принятия кук на сайте
    public void clickCookie() {
        driver.findElement(By.id("rcc-confirm-button")).click();
    }
}
