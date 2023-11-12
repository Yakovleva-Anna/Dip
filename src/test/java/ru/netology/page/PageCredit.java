package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PageCredit {

    private SelenideElement buyCreditCardButton = $(byText("Купить в кредит"));
    private SelenideElement heading = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cardOwnerField = $$("[class='input__control']").get(3);
    private SelenideElement codeField = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement failNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement necessaryMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement wrongExpirationMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));


    public PageCredit() {
        buyCreditCardButton.click();
        heading
                .shouldBe(visible)
                .shouldHave(text("Кредит по данным карты"));
    }

    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        cardNumberField.sendKeys(cardNumber);
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        cardOwnerField.sendKeys(cardOwner);
        codeField.sendKeys(code);
        continueButton.click();
    }

    public void clearFields() {
        cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cardOwnerField.doubleClick().sendKeys(Keys.BACK_SPACE);
        codeField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForSuccessNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitForFailNotification() {
        failNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitForNecessaryMessage() {
        necessaryMessage.shouldBe(visible);
    }

    public void waitForWrongFormatMessage() {

        wrongFormatMessage.shouldBe(visible);
    }

    public void waitForWrongExpirationMessage() {

        wrongExpirationMessage.shouldBe(visible);
    }

    public void waitForCardExpiredMessage() {

        cardExpiredMessage.shouldBe(visible);
    }
}
