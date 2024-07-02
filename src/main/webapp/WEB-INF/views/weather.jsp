<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weather Information</title>
</head>
<body>
    <h2>Weather Information</h2>
    <c:choose>
        <c:when test="${not empty weatherData}">
            <p><strong>Location:</strong> ${weatherData.name}</p>
            <p><strong>Temperature:</strong> ${temp} °C</p>
            <p><strong>Feels Like:</strong> ${feels_like} °C</p>
            <p><strong>Min Temperature:</strong> ${temp_min} °C</p>
            <p><strong>Max Temperature:</strong> ${temp_max} °C</p>
            <p><strong>Pressure:</strong> ${weatherData.main.pressure} hPa</p>
            <p><strong>Humidity:</strong> ${weatherData.main.humidity} %</p>
            <p><strong>Weather:</strong> ${weatherData.weather[0].description}</p>
            <p><strong>Cloudiness:</strong> ${weatherData.clouds.all} %</p>
            <p><strong>Wind Speed:</strong> ${weatherData.wind.speed} m/s</p>
            <p><strong>Wind Direction:</strong> ${weatherData.wind.deg} °</p>
            <p><strong>Sunrise:</strong> <fmt:formatDate value="${sunriseDate}" pattern="HH:mm:ss" /></p>
            <p><strong>Sunset:</strong> <fmt:formatDate value="${sunsetDate}" pattern="HH:mm:ss" /></p>
        </c:when>
        <c:otherwise>
            <p>No weather data available</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
