package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id='dashboard']");
        heading.shouldBe(Condition.visible);
    }
}
