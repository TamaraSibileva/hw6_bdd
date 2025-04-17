package ru.netology.web.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.netology.web.data.DataHelper.*;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashBoardPage dashBoardPage;
    private static TransferPage transferPage;

    @BeforeEach
    void setUp() {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--start-maximized");
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("credentials_enable_service", false);
      prefs.put("password_manager_enabled", false);
      options.setExperimentalOption("prefs", prefs);
      Configuration.browserCapabilities = options;
    }

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.login(login, password);
        dashBoardPage = verificationPage.validVerify(String.valueOf(12345));
        dashBoardPage.verifyDashBoardPage();
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою карту с номером {string} с главной страницы")
    public void transferFromCardToCard(String amount, String from, String to) {
        transferPage = dashBoardPage.selectCardToTransfer(to);
        dashBoardPage = transferPage.makeValidTransfer(String.valueOf(amount), from);
        dashBoardPage.reloadDashBoardPage();
    }

    @Тогда("баланс его карты с номером {string} должен стать {string} рублей")
    public void checkCardToBalance(String to, String expectedBalance) {
        assertAll(() -> dashBoardPage.checkCardBalance(to, expectedBalance));
    }
}
