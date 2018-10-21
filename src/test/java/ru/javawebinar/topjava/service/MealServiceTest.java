package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.testUtil.Util.assertMatch;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
//@Sql(scripts = "classpath:db/initDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL4_ID, USER_ID);

        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        Meal meal = service.get(MEAL4_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL4_ID, USER_ID); //USER_MEAL_1

        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, Arrays.asList(new Meal[]{USER_MEAL_3, USER_MEAL_2}));
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(MEAL4_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, Arrays.asList(new Meal[]{ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1}));
    }

    @Test
    public void update() {
        Meal updated = new Meal(ADMIN_MEAL_1);

        updated.setDescription("update");
        updated.setCalories(999);
        updated.setDateTime(LocalDateTime.of(2017, Month.JANUARY, 1, 1, 1, 1));

        service.update(updated, ADMIN_ID);

        assertMatch(updated, service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        Meal updated = new Meal(ADMIN_MEAL_1);

        updated.setDescription("update");
        updated.setCalories(999);
        updated.setDateTime(LocalDateTime.of(2017, Month.JANUARY, 1, 1, 1, 1));

        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(ADMIN_MEAL_1);

        newMeal.setId(null); // new

        service.create(newMeal, USER_ID);

        assertMatch(newMeal, service.get(newMeal.getId(), USER_ID));
    }
}