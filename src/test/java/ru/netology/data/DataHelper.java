package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    public static CardInfo getApprovedCard() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getDeclinedCard() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444442",month, year, holder, cvv);
    }

    public static CardInfo getEmptyCard() {

        return new CardInfo("", "", "", "", "");
    }

    public static String getShiftedMonth() {
        int shift = (int) (Math.random() * 10);
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getShiftedYear(int yearCount) {
        return LocalDate.now().plusYears(yearCount).format(DateTimeFormatter.ofPattern("YY"));
    }

    public static CardInfo getCardNotInDatabase() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444444", month, year, holder, cvv);
    }

    public static CardInfo getCardMonthThisYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getShiftedYear(0);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "", year, holder, cvv);
    }

    public static CardInfo getNumberCard13Symbols() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        String number = faker.number().digits(13);
        return new CardInfo(number, month, year, holder, cvv);
    }

    public static CardInfo getCardMonthOver12() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "13", year, holder, cvv);
    }

    public static CardInfo getCardYearUnderThisYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(-1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardHolderCirillic() {
        Faker faker = new Faker(new Locale("ru"));
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardHolderNumeric() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.number().digit();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardSpecialSymbols() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " %$ * &";
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardCvv1Symbol() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(1);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getNumberCard00Symbols() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("00000000000000", month, year, holder, cvv);
    }

    public static CardInfo getCardMonth00ThisYear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String year = getShiftedYear(0);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "00", year, holder, cvv);
    }

    public static CardInfo getCardYear00() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, "00", holder, cvv);
    }

    public static CardInfo getCardCvv0Symbols() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(0);
        return new CardInfo("4444444444444441", month, year, holder, "000");
    }

    public static CardInfo getNumberCardNullSymbol() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("", month, year, holder, cvv);
    }

    public static CardInfo getCardYearClear() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, "", holder, cvv);
    }

    public static CardInfo getCardCvvClaerSymbols() {
        Faker faker = new Faker();
        String holder = faker.name().firstName() + " " + faker.name().lastName();
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        return new CardInfo("4444444444444441", month, year, holder, "");
    }

    public static CardInfo getCardHolderClear() {
        Faker faker = new Faker(new Locale("ru"));
        String month = getShiftedMonth();
        String year = getShiftedYear(1);
        String cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, "", cvv);
    }


    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String cardHolder;
        String cvc;
    }
}

