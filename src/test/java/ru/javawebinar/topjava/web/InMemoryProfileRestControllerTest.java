package ru.javawebinar.topjava.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.util.Arrays;

import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.testUtil.Util.assertMatch;
import static ru.javawebinar.topjava.testUtil.Util.assertMatchUser;

@ContextConfiguration("/spring-app-test.xml")
@RunWith(SpringRunner.class)
public class InMemoryProfileRestControllerTest {

    @Autowired
    private ProfileRestController controller;

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void get() {
        User user = controller.get(USER_ID);

        assertMatchUser(user, USER);
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        assertMatchUser(controller.getAll(), Arrays.asList(new User[]{ADMIN}));
    }

    @Test
    public void update() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated);
        assertMatchUser(controller.get(USER_ID), updated);
    }
}