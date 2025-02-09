package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public void verify(String VerificationCode) {
        codeField.setValue(VerificationCode);
        verifyButton.click();
    }

    public void validVerify(String VerificationCode) {
        verify(VerificationCode);
        new DashboardPage();
    }

    public void verificationPageVis() {
        codeField.shouldBe(Condition.visible);
    }

    public void errorCode() {
        errorMessage.shouldBe(Condition.visible);
        errorMessage.shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз"));
    }
}