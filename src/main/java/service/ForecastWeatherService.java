package service;

import model.WeatherRequest;
import weather.WeatherConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class ForecastWeatherService extends AbstractWeatherService {

    public ForecastWeatherService(String cityName) {
        super(cityName);
    }

    @Override
    public String fetchWeatherData() throws IOException {
        // Создаем объект WeatherRequest с использованием методов класса WeatherConfig
        WeatherRequest request = new WeatherRequest(
                WeatherConfig.getApiKey(), // Используем метод для получения API ключа
                cityName,
                "forecast"
        );
        String url = request.buildUrl() + "&days=3"; // 3 дня прогноза
        Request httpRequest = new Request.Builder().url(url).build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public String getForecast() throws IOException {
        String responseData = fetchWeatherData();
        saveData(responseData);
        return formatForecastWeather(responseData);
    }

    private String formatForecastWeather(String jsonData) {
        StringBuilder forecastBuilder = new StringBuilder();
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray forecastDays = jsonObject.getAsJsonObject("forecast").getAsJsonArray("forecastday");

        for (int i = 0; i < forecastDays.size(); i++) {
            JsonObject dayObject = forecastDays.get(i).getAsJsonObject();
            String date = dayObject.get("date").getAsString();
            double maxTemp = dayObject.getAsJsonObject("day").get("maxtemp_c").getAsDouble();
            double minTemp = dayObject.getAsJsonObject("day").get("mintemp_c").getAsDouble();
            double avgTemp = dayObject.getAsJsonObject("day").get("avgtemp_c").getAsDouble();
            double precipitation = dayObject.getAsJsonObject("day").get("totalprecip_mm").getAsDouble();
            String condition = dayObject.getAsJsonObject("day").getAsJsonObject("condition").get("text").getAsString();

            forecastBuilder.append(String.format("Дата: %s\n" +
                            "Максимальная температура: %.1f°C\n" +
                            "Минимальная температура: %.1f°C\n" +
                            "Средняя температура: %.1f°C\n" +
                            "Общее количество осадков: %.1f мм\n" +
                            "Погодное условие: %s\n\n",
                    date, maxTemp, minTemp, avgTemp, precipitation, condition));
        }

        return forecastBuilder.toString();
    }
}
