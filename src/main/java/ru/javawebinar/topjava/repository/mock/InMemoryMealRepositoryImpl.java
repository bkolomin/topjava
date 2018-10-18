package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for(Meal meal: MealsUtil.MEALS){
            this.save(meal.getUserId(), meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if(MealsUtil.checkNullAndAccess(userId, meal)) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // treat case: update, but absent in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }else {
            return null;
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal meal = repository.get(id);

        if(MealsUtil.checkNullAndAccess(userId, meal)) {
            repository.remove(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = repository.get(id);

        if(MealsUtil.checkNullAndAccess(userId, meal))
            return meal;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> list = repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());

        return list;
    }
}

