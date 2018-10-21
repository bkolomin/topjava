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

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.testUtil.Util.assertMatch;

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

        assertMatch(user, USER, "registered", "roles");
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        assertMatch(controller.getAll(), Arrays.asList(new User[]{ADMIN}), "registered", "roles");
    }

    @Test
    public void update() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated);
        assertMatch(controller.get(USER_ID), updated, "registered", "roles");
    }
}