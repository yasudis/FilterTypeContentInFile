package org.yasudis.model.sortOption;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SorterParameterByDataType extends SortParameter {
    private static final String DEFAULT_INTEGERS_FILE = "integers.txt";
    private static final String DEFAULT_FLOATS_FILE = "floats.txt";
    private static final String DEFAULT_STRINGS_FILE = "strings.txt";
    private String outputPath;
    private String outputFileInteger;
    private String outputFileFloat;
    private String outputFileString;
    private String prefix;
    private boolean append;
    private boolean shortStats;
    private boolean fullStats;
    private List<File> inputFiles = new ArrayList<>();

    public SorterParameterByDataType(String[] args) {
        // Значения по умолчанию
        outputPath = ""; // текущая папка
        prefix = ""; // пустой префикс
        append = false; // режим перезаписи
        shortStats = false; // без краткой статистики
        fullStats = false; // без полной статистики
        outputFileInteger = DEFAULT_INTEGERS_FILE;
        outputFileFloat = DEFAULT_FLOATS_FILE;
        outputFileString = DEFAULT_STRINGS_FILE;

        // Индекс для перебора аргументов
        int i = 0;

        // Пока не достигнут конец массива или не встречен аргумент без дефиса
        while (i < args.length && args[i].startsWith("-")) {
            // Получаем текущий аргумент
            String arg = args[i];

            // Проверяем, какая опция задана
            switch (arg) {
                case "-o": // опция для пути для результатов
                    // Проверяем, что есть следующий аргумент и он не начинается с дефиса
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        // Присваиваем полю outputPath значение следующего аргумента
                        outputPath = args[i + 1];
                        addOutputPath(outputPath);
                        // Увеличиваем индекс на 2
                        i += 2;
                    } else {
                        // Иначе выводим сообщение об ошибке и завершаем программу
                        System.err.println("Опция -o требует аргумента");
                        System.exit(1);
                    }
                    break;
                case "-p": // опция для префикса имен выходных файлов
                    // Проверяем, что есть следующий аргумент и он не начинается с дефиса
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        // Присваиваем полю prefix значение следующего аргумента
                        prefix = args[i + 1];
                        addOutputFileName(prefix);
                        // Увеличиваем индекс на 2
                        i += 2;
                    } else {
                        // Иначе выводим сообщение об ошибке и завершаем программу
                        System.err.println("Опция -p требует аргумента");
                        System.exit(1);
                    }
                    break;
                case "-a": // опция для режима добавления в существующие файлы
                    // Присваиваем полю append значение true
                    append = true;
                    // Увеличиваем индекс на 1
                    i++;
                    break;
                case "-s": // опция для краткой статистики
                    // Присваиваем полю shortStats значение true
                    shortStats = true;
                    // Увеличиваем индекс на 1
                    i++;
                    break;
                case "-f": // опция для полной статистики
                    // Присваиваем полю fullStats значение true
                    fullStats = true;
                    // Увеличиваем индекс на 1
                    i++;
                    break;
                default: // неизвестная опция
                    // Выводим сообщение об ошибке и завершаем программу
                    System.err.println("Неизвестная опция: " + arg);
                    System.exit(1);
            }
        }
        // Проверяем, что есть хотя бы один аргумент без дефиса (имя входного файла)
        if (i == args.length) {
            // Иначе выводим сообщение об ошибке и завершаем программу
            System.err.println("Не указано имя входного файла");
            System.exit(1);
        } else {
            while (i < args.length) {
                inputFiles.add(new File(args[i]));
                i++;
            }
        }
    }

    private void addOutputPath(String string) {
        outputFileInteger += string + "/";
        outputFileFloat += string + "/";
        outputFileString += string + "/";
    }

    private void addOutputFileName(String string) {
        outputFileInteger += string + DEFAULT_INTEGERS_FILE;
        outputFileFloat += string + DEFAULT_FLOATS_FILE;
        outputFileString += string + DEFAULT_STRINGS_FILE;
    }

    public String getOutputFileInteger() {
        return outputFileInteger;
    }

    public String getOutputFileFloat() {
        return outputFileFloat;
    }

    public String getOutputFileString() {
        return outputFileString;
    }

    // Геттеры для полей
    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isShortStats() {
        return shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }
}
