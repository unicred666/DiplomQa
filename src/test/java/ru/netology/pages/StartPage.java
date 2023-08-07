package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class StartPage {
    private SelenideElement heading = $(byText("Путешествие дня"));
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public StartPage() {
        heading.shouldBe(visible);
    }

    public OrderCardPage orderCardPage() {
        buyButton.click();
        return new OrderCardPage();
    }

    public CreditPage creditPage() {
        creditButton.click();
        return new CreditPage();
    }
}
