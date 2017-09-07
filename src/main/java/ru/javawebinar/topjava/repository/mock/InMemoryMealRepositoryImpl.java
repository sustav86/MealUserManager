package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(u -> save(u, InMemoryUserRepositoryImpl.USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userID) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(meal.getId(), userID) == null) {
            return null;
        }
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(meal.getId(), ConcurrentHashMap::new);
        userMeals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userID) {
        Map<Integer, Meal> userMeals = repository.get(userID);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userID) {
        Map<Integer, Meal> meals = repository.get(userID);
        if (meals != null) {
            return meals.get(userID);
        }else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userID) {
        return getAllAsStream(userID).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userID) {
        return getAllAsStream(userID)
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime().toLocalTime(), startDate.toLocalTime(), endDate.toLocalTime()))
                .collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(int userID) {
        Map<Integer, Meal> meals = repository.get(userID);
        return meals == null ?
                Stream.empty() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }

}

