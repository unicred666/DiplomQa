package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPayServiceTest {
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
        //Покупка тура в кредит при помощи карты № "4444 4444 4444 4441" и вводе валидных данных.
    void creditPositiveAllFieldValidApproved() {
        startPage.creditPage();
        var cardInfo = DataHelper.getApprovedCard();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
        //Покупка тура в кредит при помощи карты № "4444 4444 4444 4442" и вводе валидных данных.
    void creditPositiveAllFieldValidDeclined() {
        startPage.creditPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
        //Покупка тура в кредит при помощи карты с номером отличным от представленных и вводом валидных данных.
    void creditNegativeCardNotInDatabase() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненной формой.
    void creditNegativeAllFieldEmpty() {
        startPage.creditPage();
        var cardInfo = DataHelper.getEmptyCard();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Отправка формы покупки тура в кредит, заполненной валидными данными, кроме месяца действия карты.
    void CreditNegativeMonthThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthThisYear();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }


    @Test
        //Попытка покупки в кредит с невалидным номером карты:
    void creditNegativeNumberCard13Symbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard13Symbols();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки в кредит с истекшим сроком "годности" действия карты.
    void creditNegativeYearUnderThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным месяцом выпуска карты:
    void creditNegativeMonthOver12() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты.
    void creditNegativeOwnerCirillic() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты в виде цифр.
    void creditNegativeOwnerNumeric() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты в виде спецсимволов.
    void creditNegativeOwnerSpecialSymbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным cvc/cvv.
    void creditNegativeCvv1Symbol() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым номер карты.
    void creditNegativeNumberCard00Symbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard00Symbols();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым месяцом карты.
    void creditNegativeMonth00ThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым годом.
    void creditNegativeYear00() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYear00();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым CVC/CVV кодом.
    void creditNegativeCvv0Symbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv0Symbols();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        // Попытка покупки тура в кредит с незаполненным номером карты.
    void creditNegativeCardNullSymbol() {
        startPage.creditPage();
        var cardInfo = DataHelper.getNumberCardNullSymbol();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем год.
    void creditNegativeYearClear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearClear();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем CVC/CVV код.
    void creditNegativeCvvClearSymbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvvClaerSymbols();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем владелец карты.
    void creditNegativeOwnerClear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderClear();
        var creditPage = new CreditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
