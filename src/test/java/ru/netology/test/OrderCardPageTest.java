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
    public static String url = System.getProperty("sut.url");
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
        startPage.orderCardPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
        //Покупка тура при помощи карты № "4444 4444 4444 4442" и вводе валидных данных.
    void buyPositiveAllFieldValidDeclined() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
        //Покупка тура при помощи карты с номером отличным от предоставленных и вводом валидных данных.
    void buyNegativeCardNotInDatabase() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненной формой.
    void buyNegativeAllFieldEmpty() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getEmptyCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Отправка формы покупки тура, заполненной валидными данными, кроме месяца действия карты.
    void buyNegativeMonthThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным номером карты.
    void buyNegativeNumberCard13Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard13Symbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным месяцом выпуска карты
    void buyNegativeMonthOver12() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с истекшим сроком "годности" действия карты.
    void buyNegativeYearUnderThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты.
    void buyNegativeOwnerCirillic() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты в виде цифр.
    void buyNegativeOwnerNumeric() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным именем владельца карты в виде спецсимволов.
    void buyNegativeOwnerSpecialSymbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с невалидным cvc/cvv:
    void buyNegativeCvv1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым номер карты:
    void buyNegativeNumberCard00Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard00Symbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым месяцом карты:
    void buyNegativeMonth00ThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с нулевым годом:
    void buyNegativeYear00() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear00();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        // Попытка оплаты с нулевым CVC/CVV кодом:
    void buyNegativeCvv0Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv0Symbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным номером карты:
    void buyNegativeCardNullSymbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCardNullSymbol();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем год:
    void buyNegativeYearClear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearClear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем CVC/CVV код:
    void buyNegativeCvvClearSymbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvvClaerSymbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненным полем владелец карты:
    void buyNegativeOwnerClear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderClear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}