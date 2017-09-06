<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: SUSTAVOV
  Date: 07.09.2017
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: dodgerblue}
        .exceeded {color: rosybrown}
    </style>
</head>
<body>
<section>
<hr>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<hr>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed" />
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>
                <%=TimeUtil.toString(meal.getDateTime())%>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</section>
</body>
</html>
