package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.netology.web.data.DataHelper.*;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashBoardPage dashBoardPage;
    private static TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.login(login, password);
        dashBoardPage = verificationPage.validVerify(String.valueOf(12345));
        dashBoardPage.verifyDashBoardPage();
    }

    @Когда("пользователь переводит {string} рублей с карты с номером 5559_0000_0000_0002 на свою 1 карту с главной страницы")
    public void transferFromSecondCardToFirstCard(String amount) {
        transferPage = dashBoardPage.selectCardToTransfer(getFirstCardInfo());
        dashBoardPage = transferPage.makeValidTransfer(amount, getSecondCardInfo());
        dashBoardPage.reloadDashBoardPage();
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать {string} рублей")
    public void checkFirstCardBalance(String expectedBalance) {
        assertAll(() -> dashBoardPage.checkCardBalance(getFirstCardInfo(), expectedBalance));
    }

//    var amount = generateValidAmount(secondCardBalance);
//    var expectedFirstCardBalance = firstCardBalance + amount;
//    var expectedSecondCardBalance = secondCardBalance - amount;
//    var transferPage = dashBoardPage.selectCardToTransfer(firstCardInfo);
//    dashBoardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
//        dashBoardPage.reloadDashBoardPage();
//    assertAll(() -> dashBoardPage.checkCardBalance(firstCardInfo, expectedFirstCardBalance),
//            () -> dashBoardPage.checkCardBalance(secondCardInfo, expectedSecondCardBalance));
}
