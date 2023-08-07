package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement heading = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement cardHolder = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvc = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement buttonNext = $(byText("Продолжить"));
    private SelenideElement success = $(".notification_status_ok");
    private SelenideElement error = $(".notification_status_error");
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private ElementsCollection wrongFormat4Error = $$(byText("Неверный формат"));
    private SelenideElement cardExpirationDateError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения"));



    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void insertCardData(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardHolder.setValue(cardInfo.getCardHolder());
        cvc.setValue(cardInfo.getCvc());
        buttonNext.click();
    }

    public void waitNotificationApproved() {
        success.shouldBe(visible, Duration.ofSeconds(30));
    }

    public void waitNotificationFailure() {
        error.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationWrongFormat() {
        wrongFormatError.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationExpirationDateError() {
        cardExpirationDateError.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationExpiredError() {
        cardExpiredError.shouldBe(visible, Duration.ofSeconds(15));
    }
    public void waitNotificationWrongFormat4Fields() {
        wrongFormat4Error.shouldHave(size(4));
        requiredFieldError.shouldBe(visible, Duration.ofSeconds(15));
    }
}