package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );


        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        for(UserMealWithExceed userMealWithExceed: list){

            System.out.println(userMealWithExceed);

        }

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        /*
        // МЕТОД 1
        List<UserMealWithExceed> list = new ArrayList<>();

        HashMap<LocalDate, Integer> caloriesSum = new HashMap<>();

        for (UserMeal userMeal: mealList) {
            LocalDate key =   userMeal.getDateTime().toLocalDate();

            caloriesSum.merge(key, userMeal.getCalories(), (oldVal, newVal) -> oldVal + newVal);
        }

        for (UserMeal userMeal: mealList) {
            LocalDate key =   userMeal.getDateTime().toLocalDate();

            Integer totalCalories = caloriesSum.get(key);

            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)){
                list.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), (totalCalories > caloriesPerDay)));
            }
        }
        */

        // МЕТОД 2
        Map<LocalDate, Integer> caloriesSum =
                mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExceed> list =
                mealList.stream()
                        .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                        .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesSum.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());

        return list;
    }
}
