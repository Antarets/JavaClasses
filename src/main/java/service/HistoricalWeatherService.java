package service;

import model.WeatherRequest;
import weather.WeatherConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class HistoricalWeatherService extends AbstractWeatherService {
    private String date;

    public HistoricalWeatherService(String cityName, String date) {
        super(cityName);
        this.date = date;
    }

    @Override
    public String fetchWeatherData() throws IOException {
        // Создаем объект WeatherRequest с использованием методов класса WeatherConfig
        WeatherRequest request = new WeatherRequest(
                WeatherConfig.getApiKey(), // Используем метод для получения API ключа
                cityName,
                "history"
        );
        String url = request.buildUrl() + "&dt=" + date;
        Request httpRequest = new Request.Builder().url(url).build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public String getWeatherHistory() throws IOException {
        String responseData = fetchWeatherData();
        saveData(responseData);
        return formatHistoricalWeather(responseData);
    }

    private String formatHistoricalWeather(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonObject forecastDay = jsonObject.getAsJsonObject("forecast").getAsJsonArray("forecastday").get(0).getAsJsonObject();
        JsonObject dayInfo = forecastDay.getAsJsonObject("day");
        double maxTemp = dayInfo.get("maxtemp_c").getAsDouble();
        double minTemp = dayInfo.get("mintemp_c").getAsDouble();
        double avgTemp = dayInfo.get("avgtemp_c").getAsDouble();
        double precipitation = dayInfo.get("totalprecip_mm").getAsDouble();
        String condition = dayInfo.getAsJsonObject("condition").get("text").getAsString();

        return String.format("Исторические данные для даты: %s\n" +
                        "Максимальная температура: %.1f°C\n" +
                        "Минимальная температура: %.1f°C\n" +
                        "Средняя температура: %.1f°C\n" +
                        "Общее количество осадков: %.1f мм\n" +
                        "Погодное условие: %s",
                date, maxTemp, minTemp, avgTemp, precipitation, condition);
    }
}
