package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportMaker {

    public static void WriteToFile(){
        // Указываем путь к файлу
        String filePath = "C:\\report.txt";

        // Создаем объект File для представления файла по указанному пути
        File file = new File(filePath);

        try {
            // Проверяем, существует ли файл. Если нет, то создаем новый
            if (!file.exists()) {
                file.createNewFile();
            }

            // Создаем объект FileWriter для записи в файл
            FileWriter fileWriter = new FileWriter(file);

            // Создаем объект BufferedWriter для более эффективной записи
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Записываем информацию в файл
            bufferedWriter.write("Привет, это пример записи в файл!");
            bufferedWriter.newLine(); // Добавляем новую строку

            // Закрываем потоки
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Информация успешно записана в файл.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
