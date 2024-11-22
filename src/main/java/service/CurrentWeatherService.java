package service;

import model.WeatherRequest;
import weather.WeatherConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class CurrentWeatherService extends AbstractWeatherService {

    public CurrentWeatherService(String cityName) {
        super(cityName);
    }

    @Override
    public String fetchWeatherData() throws IOException {
        // Создаем объект WeatherRequest с использованием методов класса WeatherConfig
        WeatherRequest request = new WeatherRequest(
                WeatherConfig.getApiKey(), // Используем метод для получения API ключа
                cityName,
                "current"
        );
        String url = request.buildUrl();
        Request httpRequest = new Request.Builder().url(url).build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public String getCurrentWeather() throws IOException {
        String responseData = fetchWeatherData();
        saveData(responseData);
        return formatCurrentWeather(responseData);
    }

    private String formatCurrentWeather(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonObject current = jsonObject.getAsJsonObject("current");
        JsonObject location = jsonObject.getAsJsonObject("location");

        return String.format("Текущая температура в %s: %.1f°C\n" +
                        "Погодное условие: %s\n" +
                        "Скорость ветра: %.1f км/ч\n" +
                        "Влажность: %.1f%%\n" +
                        "Давление: %.1f мбар\n" +
                        "Видимость: %.1f км\n" +
                        "Дата и время: %s\n",
                location.get("name").getAsString(), current.get("temp_c").getAsDouble(),
                current.getAsJsonObject("condition").get("text").getAsString(),
                current.get("wind_kph").getAsDouble(),
                current.get("humidity").getAsDouble(),
                current.get("pressure_mb").getAsDouble(),
                current.get("vis_km").getAsDouble(),
                location.get("localtime").getAsString());
    }
}
