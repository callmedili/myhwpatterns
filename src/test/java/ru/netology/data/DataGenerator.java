package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Locale;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static UserInfo generateUser() {
        return new UserInfo(
                faker.options().option(
                        "Москва",
                        "Санкт-Петербург",
                        "Казань",
                        "Пермь",
                        "Екатеринбург",
                        "Новосибирск",
                        "Омск",
                        "Самара",
                        "Уфа",
                        "Красноярск"
                ),
                faker.name().fullName(),
                "+7" + faker.number().digits(10)
        );
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}