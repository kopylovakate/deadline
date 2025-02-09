package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanBase;

public class AuthTest {

    @AfterAll
    public static void shouldCleanBase() {
        cleanBase();
    }

    @Test
    public void shouldAuthSuccess() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVis();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    public void shouldInvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getErrorMessage("Ошибка! " + "Неверно указан логин или пароль");
    }

    @Test
    public void shouldInvalidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfo);
        loginPage.getErrorMessage("Ошибка! " + "Неверно указан логин или пароль");
    }

    @Test
    public void shouldInvalidVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVis();
        var verificationCode = DataHelper.getRandomCode().getCode();
        verificationPage.verify(verificationCode);
        verificationPage.errorCode();
    }

    @Test
    public void shouldBlockAccessAfterThreeTimeLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfoFirst = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoFirst);
        loginPage.getErrorMessage("Ошибка! " + "Неверно указан логин или пароль");
        loginPage.cleanForm();
        clearBrowserCookies();
        var authInfoSecond = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoSecond);
        loginPage.getErrorMessage("Ошибка! " + "Неверно указан логин или пароль");
        loginPage.cleanForm();
        clearBrowserCookies();
        var authInfoThird = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoThird);
        loginPage.getErrorMessage("Ошибка! " + "Пользователь заблокирован");
    }
}