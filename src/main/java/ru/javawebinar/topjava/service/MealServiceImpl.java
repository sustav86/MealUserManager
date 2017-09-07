package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.repository.MealRepository;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void printRepository(MealRepository repository) {
        System.out.println(repository.toString());
    }
}