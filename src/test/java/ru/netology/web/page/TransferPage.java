package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferHeader = $(byText("Пополнение карты"));
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        transferHeader.should(Condition.visible);
    }

    public void makeTransfer(String amountToTransfer, String cardNumber) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardNumber);
        transferButton.click();
    }

    public DashBoardPage makeValidTransfer(String amountToTransfer, String cardNumber) {
        makeTransfer(amountToTransfer, cardNumber);
        return new DashBoardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.should(Condition.and("Проверка сообщения об ошибке", Condition.text(expectedText), Condition.visible),
                Duration.ofSeconds(15));
    }
}

