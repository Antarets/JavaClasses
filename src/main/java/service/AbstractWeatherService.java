package service;
import util.JsonSaver;
import okhttp3.*;
import java.io.IOException;

public abstract class AbstractWeatherService {
    protected String cityName;
    protected OkHttpClient client;

    public AbstractWeatherService(String cityName) {
        this.cityName = cityName;
        this.client = new OkHttpClient();
    }

    // Абстрактный метод для получения данных о погоде
    public abstract String fetchWeatherData() throws IOException;

    protected void saveData(String jsonData) throws IOException {
        JsonSaver.saveToFile(cityName, jsonData);
    }
}
