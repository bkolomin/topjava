package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;
    public static final int MEAL4_ID = START_SEQ + 5;
    public static final int MEAL5_ID = START_SEQ + 6;
    public static final int MEAL6_ID = START_SEQ + 7;

    public static final Meal ADMIN_MEAL_1 = new Meal(MEAL1_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 9, 0, 10), "Админ завтрак",500);
    public static final Meal ADMIN_MEAL_2 = new Meal(MEAL2_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 12, 0, 20), "Админ обед",1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(MEAL3_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 18, 0, 30), "Админ ужин",1500);
    public static final Meal USER_MEAL_1 = new Meal(MEAL4_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 9, 0, 40), "Юзер ланч",501);
    public static final Meal USER_MEAL_2 = new Meal(MEAL5_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 12, 0, 50), "Юзер обед",1001);
    public static final Meal USER_MEAL_3 = new Meal(MEAL6_ID, LocalDateTime.of(2018,Month.JANUARY, 1, 18, 1, 00), "Юзер ужин",1501);

}


