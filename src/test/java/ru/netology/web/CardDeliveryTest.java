package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @Test
    void shouldRescheduleDelivery() {
        DataGenerator.UserInfo user = DataGenerator.generateUser();

        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);

        open("http://localhost:9999");

        // Первое бронирование
        $("[data-test-id='city'] input")
                .setValue(user.getCity());

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .setValue(firstDate);

        $("[data-test-id='name'] input")
                .setValue(user.getName());

        $("[data-test-id='phone'] input")
                .setValue(user.getPhone());

        $("[data-test-id='agreement']")
                .click();

        $$("button")
                .findBy(Condition.text("Запланировать"))
                .click();

        // Проверяем первое успешное бронирование
        $$(".notification__title")
                .findBy(Condition.visible)
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));

        $$(".notification__content")
                .findBy(Condition.visible)
                .shouldHave(
                        Condition.text("Встреча успешно запланирована на " + firstDate),
                        Duration.ofSeconds(15)
                );

        // Повторно вводим те же данные, но с другой датой

        $("[data-test-id='city'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='city'] input")
                .setValue(user.getCity());

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .setValue(secondDate);

        $("[data-test-id='name'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='name'] input")
                .setValue(user.getName());

        $("[data-test-id='phone'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='phone'] input")
                .setValue(user.getPhone());

        // Отправляем форму второй раз
        $$("button")
                .findBy(Condition.text("Запланировать"))
                .click();

        // Проверяем окно подтверждения перепланирования
        $$(".notification")
                .findBy(Condition.text("Необходимо подтверждение"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

        $$("button")
                .findBy(Condition.text("Перепланировать"))
                .click();

        $$(".notification__title")
                .findBy(Condition.visible)
                .shouldHave(
                        Condition.text("Успешно!"),
                        Duration.ofSeconds(15)
                );

        $$(".notification__content")
                .findBy(Condition.visible)
                .shouldHave(
                        Condition.text("Встреча успешно запланирована на " + secondDate),
                        Duration.ofSeconds(15)
                );
    }
}