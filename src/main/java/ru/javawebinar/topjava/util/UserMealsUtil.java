package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
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
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> filteredWithExceededLambda = getFilteredWithExceededLambda(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println("Circle:");
        filteredWithExceeded.forEach(System.out::println);
        System.out.println("Lambda");
        filteredWithExceeded.forEach(System.out::println);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDay = new HashMap<>();
        for (UserMeal meal: mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
//            caloriesSumByDay.put(mealDate, caloriesSumByDay.getOrDefault(mealDate, 0) + meal.getCalories());
            caloriesSumByDay.put(mealDate, caloriesSumByDay.merge(mealDate, meal.getCalories(), (f, s) -> f + s));
        }

        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal meal: mealList) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(mealTime, startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay));
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededLambda(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy(umd -> umd.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), caloriesSumByDay.getOrDefault(um.getDateTime().toLocalDate(), 0) > caloriesPerDay))
                .collect(Collectors.toList());

    }
}
