package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.netology.web.data.DataHelper.*;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashBoardPage dashBoardPage;
    private static TransferPage transferPage;
    CardInfo cardFrom;
    CardInfo cardTo;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.login(login, password);
        dashBoardPage = verificationPage.validVerify(String.valueOf(12345));
        dashBoardPage.verifyDashBoardPage();
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою карту с номером {string} с главной страницы")
    public void transferFromCardToCard(String amount, String from, String to) {
        if (from.equals(getFirstCardInfo().getCardNumber())) {
            cardFrom = getFirstCardInfo();
        } else {
            cardFrom = getSecondCardInfo();
        }

        if (to.equals(getFirstCardInfo().getCardNumber())) {
            cardTo = getFirstCardInfo();
        } else {
            cardTo = getSecondCardInfo();
        }

        transferPage = dashBoardPage.selectCardToTransfer(cardTo);
        dashBoardPage = transferPage.makeValidTransfer(String.valueOf(amount), cardFrom);
        dashBoardPage.reloadDashBoardPage();
    }

    @Тогда("баланс его карты с номером {string} должен стать {string} рублей")
    public void checkCardToBalance(String to, String expectedBalance) {
        if (to.equals(getFirstCardInfo().getCardNumber())) {
            cardTo = getFirstCardInfo();
        } else {
            cardTo = getSecondCardInfo();
        }

        assertAll(() -> dashBoardPage.checkCardBalance(cardTo, expectedBalance));
    }
}
