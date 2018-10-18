package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static final List<User> USERS = Arrays.asList(
            new User(null, "admin", "admin@test.com", "123", 0, true, new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER))),
            new User(null, "admin2", "admin2@test.com", "123", 0, true, new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER))),
            new User(null, "user1", "test1@test.com", "123", 0, true, new HashSet<>(Arrays.asList(Role.ROLE_USER)))
    );

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        USERS.forEach(this::save);
    }

    @Override
    public boolean isAdmin(int id){
        User user = get(id);

        return user.getRoles().contains(Role.ROLE_ADMIN);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if(repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");

        List list = new ArrayList(repository.values());

        list.sort(Comparator.comparing(User::getName));

        return list;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        Optional<User> user = repository.values().stream()
                .filter(user_ -> email.equalsIgnoreCase(user_.getEmail()))
                .findAny();

        return user.get();
    }
}
