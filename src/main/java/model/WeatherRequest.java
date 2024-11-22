package model;

import weather.WeatherConfig;

public class WeatherRequest {
    private String apiKey;
    private String cityName;
    private String type; // Тип запроса: current, forecast, history

    public WeatherRequest(String apiKey, String cityName, String type) {
        this.apiKey = apiKey;
        this.cityName = cityName;
        this.type = type;
    }

    public String buildUrl() {
        String baseUrl;
        switch (type) {
            case "current":
                baseUrl = WeatherConfig.getBaseUrlCurrent();
                break;
            case "forecast":
                baseUrl = WeatherConfig.getBaseUrlForecast();
                break;
            case "history":
                baseUrl = WeatherConfig.getBaseUrlHistorical();
                break;
            default:
                throw new IllegalArgumentException("Unknown request type: " + type);
        }
        return String.format("%s?key=%s&q=%s", baseUrl, apiKey, cityName);
    }
}
