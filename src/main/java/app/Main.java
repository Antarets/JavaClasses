package app;

import service.CurrentWeatherService;
import service.ForecastWeatherService;
import service.HistoricalWeatherService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем экземпляры для городов
        CurrentWeatherService ryazan = new CurrentWeatherService("Ryazan");
        CurrentWeatherService moscow = new CurrentWeatherService("Moscow");

        // Получаем и выводим текущую погоду для Рязани
        System.out.println("Текущая погода для Рязани:");
        System.out.println(ryazan.getCurrentWeather());

        // Получаем и выводим текущую погоду для Москвы
        System.out.println("\nТекущая погода для Москвы:");
        System.out.println(moscow.getCurrentWeather());

        // Получаем и выводим прогноз погоды на 2 дня для Рязани
        ForecastWeatherService ryazanForecast = new ForecastWeatherService("Ryazan");
        System.out.println("\nПрогноз погоды на 2 дня для Рязани:");
        System.out.println(ryazanForecast.getForecast());

        // Получаем и выводим прогноз погоды на 2 дня для Москвы
        ForecastWeatherService moscowForecast = new ForecastWeatherService("Moscow");
        System.out.println("\nПрогноз погоды на 2 дня для Москвы:");
        System.out.println(moscowForecast.getForecast());

        // Получаем и выводим исторические данные о погоде для Рязани
        HistoricalWeatherService ryazanHistory = new HistoricalWeatherService("Ryazan", "2024-09-28");
        System.out.println("\nИсторические данные о погоде для Рязани:");
        System.out.println(ryazanHistory.getWeatherHistory());

//        // Получаем и выводим исторические данные о погоде для Москвы
//        HistoricalWeatherService moscowHistory = new HistoricalWeatherService("Moscow", 55.7558, 37.6173, "2024-09-28");
//        System.out.println("\nИсторические данные о погоде для Москвы:");
//        System.out.println(moscowHistory.getWeatherHistory());
    }
}
