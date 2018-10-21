package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
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

    /*
  (100000, 500,  'Админ завтрак',  '2018-01-01 09:00:10'),
  (100000, 1000, 'Админ обед',  '2018-01-01 12:00:20'),
  (100000, 1500, 'Админ ужин',  '2018-01-01 18:00:30'),
  (100001, 501,  'Юзер ланч',   '2018-01-01 09:00:40'),
  (100001, 1001, 'Юзер обед',   '2018-01-01 12:00:50'),
  (100001, 1501, 'Юзер ужин',   '2018-01-01 18:00:59')
     */

}

