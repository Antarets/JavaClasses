package weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherConfig {
    private static String apiKey;
    private static String baseUrlCurrent;
    private static String baseUrlForecast;
    private static String baseUrlHistorical;

    // Для инициализации конфигурации
    static {
        try (InputStream input = WeatherConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Извините, не удалось найти файл config.properties");
            } else {

                Properties prop = new Properties();
                prop.load(input);

                // Считываем значения из файла properties
                apiKey = prop.getProperty("api.key");
                baseUrlCurrent = prop.getProperty("base.url.current");
                baseUrlForecast = prop.getProperty("base.url.forecast");
                baseUrlHistorical = prop.getProperty("base.url.historical");

                // Вывод значений для отладки
                System.out.println("API Key: " + apiKey);
                System.out.println("Base URL Current: " + baseUrlCurrent);
                System.out.println("Base URL Forecast: " + baseUrlForecast);
                System.out.println("Base URL Historical: " + baseUrlHistorical);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Методы для получения значений
    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrlCurrent() {
        return baseUrlCurrent;
    }

    public static String getBaseUrlForecast() {
        return baseUrlForecast;
    }

    public static String getBaseUrlHistorical() {
        return baseUrlHistorical;
    }
}
