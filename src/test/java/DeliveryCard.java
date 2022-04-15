
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCard {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    public String chooseDate(int days) {
        return
                LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSendForm() {
        $x("//input[@type='text']").val("Воронеж");
        $x("//input[@name='name']").val("Барсуков Иван");
        $x("//input[@name='phone']").val("+79204069356");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    void shouldSendFormWithDate() {

        String chooseDate = chooseDate(8);
        $x("//input[@type='text']").val("Воронеж");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(chooseDate);
        $x("//input[@name='name']").val("Барсуков Иван");
        $x("//input[@name='phone']").val("+79204069356");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $x("//*[contains(text(),'Успешно!')]").should(appear, Duration.ofSeconds(15));

    }

    @Test
    void shouldSendFormWithWrongCity() {

        $x("//input[@type='text']").val("Минск");
        $x("//input[@name='name']").val("Барсуков Иван");
        $x("//input[@name='phone']").val("+79204069356");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).should(visible);

    }

    @Test
    void shouldSendFormWithWrongDate() {
        String chooseDate = chooseDate(2);
        $x("//input[@type='text']").val("Воронеж");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(chooseDate);
        $x("//input[@name='name']").val("Гончарук-Иванова Анна");
        $x("//input[@name='phone']").val("+79204069356");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).should(visible);
    }

    @Test
    void shouldSendFormWithWrongName() {

        $x("//input[@type='text']").val("Воронеж");
        $x("//input[@name='name']").val("Barsykov Ivan");
        $x("//input[@name='phone']").val("+79204069356");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Имя и Фамилия")).should(visible);
    }

    @Test
    void shouldSendFormWithWrongTel() {

        $x("//input[@type='text']").val("Воронеж");
        $x("//input[@name='name']").val("Васильев Станислав");
        $x("//input[@name='phone']").val("+79206");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).should(visible);
    }

    @Test
    void shouldSendFormWithoutAgreement() {

        $x("//input[@type='text']").val("Воронеж");
        $x("//input[@name='name']").val("Васильев Станислав");
        $x("//input[@name='phone']").val("+79206895656");
        $(withText("Забронировать")).click();
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).should(visible);
    }

}
