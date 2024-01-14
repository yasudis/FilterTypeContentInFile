package org.yasudis.dataSorte;

import org.yasudis.sortOption.SorterParameterByDataType;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class DataTypeSorterInFile extends DataSorter {
    private SorterParameterByDataType sorterParameterByDataType;
    private List<BufferedReader> readers = new ArrayList<>();
    // Сделать список записи врайтеров.
    private FileWriter integerWriter;
    private FileWriter floatWriter;
    private FileWriter stringWriter;

    private BigInteger intCount = BigInteger.ZERO;
    // количество целых чисел
    private BigInteger floatCount = BigInteger.ZERO;
    // количество натуральных чисел
    private BigInteger stringCount = BigInteger.ZERO;
    // количество строк
    private BigInteger intMin; // минимальное целое число
    private BigInteger intMax; // максимальное целое число
    private BigInteger intSum = BigInteger.ZERO;// сумма целых чисел
    private BigDecimal intAverage;
    private double floatMin; // минимальное натуральное число
    private double floatMax; // максимальное натуральное число
    private double floatSum; // сумма натуральных чисел
    private int stringMin; // длина самой короткой строки
    private int stringMax; // длина самой длинной строки


    public DataTypeSorterInFile(String[] args) {
        sorterParameterByDataType = new SorterParameterByDataType(args);

        intCount = BigInteger.ZERO;
        floatCount = BigInteger.ZERO;
        stringCount = BigInteger.ZERO;
        intSum = BigInteger.ZERO;
        floatMin = 0;
        floatMax = 0;
        floatSum = 0;
        stringMin = 0;
        stringMax = 0;
    }

    public void run() {
        openWriteStreams();
        openReaderStreams();
        while (!sortOneLineInFiles()) {

        }

        if (sorterParameterByDataType.isFullStats()) {
            showFullStats();
        }

        closeReaderStreams();
        closeWriterStreams();
    }

    private void openWriteStreams() {
        try {
            integerWriter = new FileWriter(sorterParameterByDataType.getOutputFileInteger());
            floatWriter = new FileWriter(sorterParameterByDataType.getOutputFileFloat());
            stringWriter = new FileWriter(sorterParameterByDataType.getOutputFileString());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void showFullStats() {
        if (intSum != null) {
            System.out.println("Количество типов Integer равно: " + intCount + ".");
            System.out.println("Максимальное число типа Integer равно: " + intMax + ".");
            System.out.println("Минимальное число типа Integer равно: " + intMin + ".");
            System.out.println("Сумма чисел типа Integer равно: " + intSum + ".");
            System.out.println("Среднее Арифметическое значение чисел типа Integer равно: " + intAverage + ".");
        }
    }

    private boolean sortOneLineInFiles() {
        boolean isLine = true;

        for (int i = 0; i < readers.size(); i++) {
            try {
                // Читаем строку из i-го файла
                String line = readers.get(i).readLine();
                if (line != null) {
                    // обрабатываем методом строку
                    sortTypeData(line);
                    // Устанавливаем флаг, что еще не достигли конца всех файлов
                    isLine = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return isLine;
    }

    private void openReaderStreams() {
        for (File file : sorterParameterByDataType.getInputFiles()) {
            try {
                readers.add(new BufferedReader(new FileReader(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeReaderStreams() {
        for (BufferedReader reader : readers) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeWriterStreams() {
        try {
            integerWriter.close();
            floatWriter.close();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortTypeData(String line) {
        try {
            if (Pattern.matches("^-?\\d+$", line)) {
                integerWriter.write(line + "\n");
                calculateFullStatisticsInteger(line);
            } else if (Pattern.matches("^-?\\d+(\\.\\d+)?$", line)) {
                floatWriter.write(line + "\n"); //вещественное число
            } else {
                stringWriter.write(line + "\n");//строка
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateFullStatisticsInteger(String line) {
        BigInteger number = new BigInteger(line);
        BigInteger increment = BigInteger.ONE;
        intCount = intCount.add(increment);

        if (intMin == null) {
            intMin = number;
        } else {
            if (intMin.compareTo(number) > 0) {
                intMin = number;
            }
        }

        if (intMax == null) {
            intMax = number;
        } else {
            if (intMax.compareTo(number) < 0) {
                intMax = number;
            }
        }

        sumInt(number);
        calculateAverage();
    }

    private void calculateFullStatisticsFloat(String line) {

    }

    private void sumInt(BigInteger number) {
        intSum = intSum.add(number);
    }

    private void calculateAverage() {
        intAverage = new BigDecimal(intSum.divide(intCount));
    }

    private static void writeFileFloat(String line) throws IOException {
       /*
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("float.txt"))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

    }

    private void writeFileInt(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("int.txt"))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeFileString(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("String.txt"))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void result(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))) {
            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}