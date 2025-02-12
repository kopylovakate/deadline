package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        public String login;
        public String password;
    }

    public static AuthInfo getAuthInfo() {

        return new AuthInfo("vasya", "qwerty123");
    }

    private static Faker faker = new Faker(new Locale("en"));

    private static String getRandomLogin() {
        return faker.name().username();
    }

    private static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo getRandomUser() {

        return new AuthInfo(getRandomLogin(), getRandomPassword());
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getRandomCode() {
        return new VerificationCode(faker.numerify("12345"));
    }
}