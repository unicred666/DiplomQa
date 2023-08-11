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
import ru.netology.pages.OrderCardPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPayServiceTest {
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
        CreditPage creditPage = startPage.creditPage();//уже возвращенный объект creditPage
        var cardInfo = DataHelper.getApprovedCard();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
        //Покупка тура в кредит при помощи карты № "4444 4444 4444 4442" и вводе валидных данных.
    void creditPositiveAllFieldValidDeclined() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getDeclinedCard();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
        //Покупка тура в кредит при помощи карты с номером отличным от представленных и вводом валидных данных.
    void creditNegativeCardNotInDatabase() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка оплаты с незаполненной формой.
    void creditNegativeAllFieldEmpty() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getEmptyCard();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Отправка формы покупки тура в кредит, заполненной валидными данными, кроме месяца действия карты.
    void CreditNegativeMonthThisYear() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthThisYear();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }


    @Test
        //Попытка покупки в кредит с невалидным номером карты:
    void creditNegativeNumberCard13Symbols() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard13Symbols();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки в кредит с истекшим сроком "годности" действия карты.
    void creditNegativeYearUnderThisYear() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным месяцом выпуска карты:
    void creditNegativeMonthOver12() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты.
    void creditNegativeOwnerCirillic() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты в виде цифр.
    void creditNegativeOwnerNumeric() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным именем владельца карты в виде спецсимволов.
    void creditNegativeOwnerSpecialSymbols() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с невалидным cvc/cvv.
    void creditNegativeCvv1Symbol() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым номер карты.
    void creditNegativeNumberCard0Symbols() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard00Symbols();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым месяцом карты.
    void creditNegativeMonth00ThisYear() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым годом.
    void creditNegativeYear00() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardYear00();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с нулевым CVC/CVV кодом.
    void creditNegativeCvv0Symbols() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv0Symbols();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        // Попытка покупки тура в кредит с незаполненным номером карты.
    void creditNegativeCardNullSymbol() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getNumberCardNullSymbol();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем год.
    void creditNegativeYearClear() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardYearClear();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем CVC/CVV код.
    void creditNegativeCvvClearSymbols() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardCvvClaerSymbols();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
        //Попытка покупки тура в кредит с незаполненным полем владелец карты.
    void creditNegativeOwnerClear() {
        CreditPage creditPage = startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderClear();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
