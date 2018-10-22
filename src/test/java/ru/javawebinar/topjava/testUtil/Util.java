package ru.javawebinar.topjava.testUtil;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class Util<T> {

    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static <T> void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatchUser(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }

    public static void assertMatchUser(Iterable<User> actual, User... expected) {
        assertMatchUser(actual, Arrays.asList(expected));
    }

    public static void assertMatchUser(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}

