package ru.javawebinar.topjava.web;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.*;


import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.testUtil.Util.assertMatch;
import static ru.javawebinar.topjava.testUtil.Util.assertMatchUser;

public class InMemoryAdminRestControllerTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static ConfigurableApplicationContext appCtx;

    private static AdminRestController controller;

    private static InMemoryUserRepositoryImpl repository;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext(new String[]{"/spring-app-test.xml"});
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");

        controller = appCtx.getBean(AdminRestController.class);
        repository = appCtx.getBean(InMemoryUserRepositoryImpl.class);
        repository.init();
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    public void setUp() throws Exception {
        // re-initialize
        //InMemoryUserRepositoryImpl repository = appCtx.getBean(InMemoryUserRepositoryImpl.class);
        //repository.init();
    }

    @Test
    public void get() throws Exception {
        User user = controller.get(USER_ID);
        assertMatchUser(user, USER);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = controller.getByMail("user@yandex.ru");
        assertMatchUser(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        controller.delete(10);
    }

    @Test
    public void getAll() {
        List<User> all = controller.getAll();

        assertMatchUser(all, ADMIN, USER);
    }

    @Test(expected = NotFoundException.class)
    public void duplicateMailCreate() throws Exception {
        controller.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        controller.delete(999);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        controller.get(1);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated, USER_ID);
        assertMatchUser(controller.get(USER_ID), updated);

        //restore
        repository.init();
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = controller.create(newUser);
        newUser.setId(created.getId());
        assertMatchUser(controller.getAll(), ADMIN, newUser, USER);

        // restore
        repository.init();
    }

    @Test
    public void delete() throws Exception {
        controller.delete(ADMIN_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(1, users.size());

        assertMatchUser(controller.getAll(), new User[]{USER});

        // restore
        repository.init();

    }

}