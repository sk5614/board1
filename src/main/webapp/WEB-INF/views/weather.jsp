<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weather Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .weather-container {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .weather-details {
            margin-left: 20px;
        }
        .weather-details p {
            margin: 5px 0;
        }
        .weather-icon {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
    <h2>Weather Information</h2>
    <c:choose>
        <c:when test="${not empty weatherData}">
            <div class="weather-container">
                <!-- Weather Icon -->
                <div class="weather-icon">
                    <img src="http://openweathermap.org/img/wn/${weatherData.weather[0].icon}@2x.png" alt="Weather Icon">
                </div>
                <!-- Weather Details -->
                <div class="weather-details">
                    <p><strong>위치:</strong> ${weatherData.name}</p>
                    <p><strong>온도:</strong> ${temp} °C</p>
                    <p><strong>체감 온도:</strong> ${feels_like} °C</p>
                    <p><strong>최저 온도:</strong> ${temp_min} °C</p>
                    <p><strong>최고 온도:</strong> ${temp_max} °C</p>
                    <p><strong>기압:</strong> ${weatherData.main.pressure} hPa</p>
                    <p><strong>습도:</strong> ${weatherData.main.humidity} %</p>
                    <p><strong>날씨:</strong> ${weatherData.weather[0].description}</p>
                    <p><strong>구름:</strong> ${weatherData.clouds.all} %</p>
                    <p><strong>풍속:</strong> ${weatherData.wind.speed} m/s</p>
                    <p><strong>풍향:</strong> ${weatherData.wind.deg} °</p>
                    <p><strong>일출:</strong> <fmt:formatDate value="${sunriseDate}" pattern="HH:mm:ss" /></p>
                    <p><strong>일몰:</strong> <fmt:formatDate value="${sunsetDate}" pattern="HH:mm:ss" /></p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <p>No weather data available</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
