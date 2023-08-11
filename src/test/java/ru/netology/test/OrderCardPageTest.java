package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.OrderCardPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardPageTest {
    StartPage startPage = open("http://localhost:8080/", StartPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }

    @Test
        //Покупка тура при помощи карты № "4444 4444 4444 4441" и вводе валидных данных.
    void buyPositiveAllFieldValidApproved() {
        OrderCardPage orderCardPage = startPage.orderCardPage();//уже возвращенный объект orderCardPage
        var cardInfo = DataHelper.getApprovedCard();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
        //Покупка тура при помощи карты № "4444 4444 4444 4442" и вводе валидных данных.
    void buyPositiveAllFieldValidDeclined() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getDeclinedCard();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
        //Покупка тура при помощи карты с номером отличным от предоставленных и вводом валидных данных.
    void buyNegativeCardNotInDatabase() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненной формой.
    void buyNegativeAllFieldEmpty() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getEmptyCard();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Отправка формы покупки тура, заполненной валидными данными, кроме месяца действия карты.
    void buyNegativeMonthThisYear() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthThisYear();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным номером карты.
    void buyNegativeNumberCard13Symbols() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard13Symbols();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным месяцом выпуска карты
    void buyNegativeMonthOver12() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с истекшим сроком "годности" действия карты.
    void buyNegativeYearUnderThisYear() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты.
    void buyNegativeOwnerCirillic() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты в виде цифр.
    void buyNegativeOwnerNumeric() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты в виде спецсимволов.
    void buyNegativeOwnerSpecialSymbols() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным cvc/cvv:
    void buyNegativeCvv1Symbol() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым номер карты:
    void buyNegativeNumberCard15Symbols() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard00Symbols();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым месяцом карты:
    void buyNegativeMonth00ThisYear() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым годом:
    void buyNegativeYear00() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear00();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        // Попытка оплаты с нулевым CVC/CVV кодом:
    void buyNegativeCvv0Symbols() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv0Symbols();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным номером карты:
    void buyNegativeCardNullSymbol() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCardNullSymbol();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем год:
    void buyNegativeYearClear() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearClear();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем CVC/CVV код:
    void buyNegativeCvvClearSymbols() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvvClaerSymbols();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем владелец карты:
    void buyNegativeOwnerClear() {
        OrderCardPage orderCardPage = startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderClear();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}