package ru.javawebinar.topjava.testUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class Util<T>{

    public static <T> void assertMatch(T actual, T expected, String... ignoringFields) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, ignoringFields);
    }

    public static <T> void assertMatch(Iterable<T> actual, T[] expected, String... ignoringFields) {
        assertMatch(actual, Arrays.asList(expected), ignoringFields);
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected, String... ignoringFields) {
        assertThat(actual).usingElementComparatorIgnoringFields(ignoringFields).isEqualTo(expected);
    }
}
