package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

//        UserRepository userRepository = (UserRepository) appCtx.getBean("mockUserRepository");
//        UserRepository userRepository = appCtx.getBean(UserRepository.class);
//        userRepository.getAll();

        UserService userService = appCtx.getBean(UserService.class);
//        userService.printReposiroty(userRepository);
        userService.save(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

        MealRepository mealRepository = appCtx.getBean(MealRepository.class);
        mealRepository.getAll();

        MealService mealService = appCtx.getBean(MealService.class);
        mealService.printRepository(mealRepository);

        appCtx.close();
    }
}
