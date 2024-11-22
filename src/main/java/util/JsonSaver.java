package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonSaver {
    public static void saveToFile(String cityName, String jsonResponse) throws IOException {
        // Путь к папке для хранения файлов
        String directoryPath = "weather_data";
        File directory = new File(directoryPath);

        // Создаем папку, если она не существует
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Полный путь к файлу
        String filename = directoryPath + "/" + cityName + "_" + System.currentTimeMillis() + ".json";

        // Сохраняем данные в файл
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonResponse);
            System.out.println("Данные о погоде сохранены в файл: " + filename);
        }
    }
}