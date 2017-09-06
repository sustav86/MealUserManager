package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by SUSTAVOV on 07.09.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       LOG.info("get MEAL_LIST list");
       request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(MealsUtil.MEAL_LIST, LocalTime.MIN, LocalTime.MAX, 2000));
       request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
