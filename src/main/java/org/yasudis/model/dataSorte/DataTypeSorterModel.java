package org.yasudis.model.dataSorte;

import org.yasudis.model.sortOption.SorterParameterByDataType;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class DataTypeSorterModel extends DataSorter {
    private SorterParameterByDataType sorterParameterByDataType;
    private List<BufferedReader> readers = new ArrayList<>();
    private FileWriter integerWriter;
    private FileWriter floatWriter;
    private FileWriter stringWriter;

    private BigInteger intCount = BigInteger.ZERO;
    private BigInteger floatCount = BigInteger.ZERO;
    private BigInteger stringCount = BigInteger.ZERO;
    // количество строк
    private BigInteger intMin;
    private BigInteger intMax;
    private BigInteger intSum = BigInteger.ZERO;
    private BigDecimal intAverage = BigDecimal.ZERO;
    private BigDecimal floatAverage = BigDecimal.ZERO;

    private BigDecimal floatMin;
    private BigDecimal floatMax;
    private BigDecimal floatSum = BigDecimal.ZERO;
    private BigInteger stringCountMin;
    private BigInteger stringCountMax;


    public DataTypeSorterModel() {

    }

    public void run() {
        openWriteStreams();
        openReaderStreams();
        while (!sortOneLineInFiles()) {

        }

        closeReaderStreams();
        closeWriterStreams();
    }

    public void setSorterParameterByDataType(String[] args) {
        sorterParameterByDataType = new SorterParameterByDataType(args);
    }

    public String getStatics() {
        if (sorterParameterByDataType.isFullStatics()) {
            return getFullStatics();
        }

        if (sorterParameterByDataType.isShortStatics()) {
            return getShortStatics();
        }

        return "Параметры выводы статистики не были заданы.";
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (intMax != null) {
            fullStatic = "Максимальное число типа Integer равно: " + intMax + ".\n";
            fullStatic += ("Минимальное число типа Integer равно: " + intMin + ".\n");
            fullStatic += ("Сумма чисел типа Integer равно: " + intSum + ".\n");
            fullStatic += ("Среднее Арифметическое значение чисел типа Integer равно: " + intAverage + ".\n");
        }

        if (floatMax != null) {
            fullStatic += ("Количество типов Float равно: " + floatCount + ".\n");
            fullStatic += ("Максимальное число типа Float равно: " + floatMax + ".\n");
            fullStatic += ("Минимальное число типа Float равно: " + floatMin + ".\n");
            fullStatic += ("Сумма чисел типа Float равно: " + floatSum + ".");
            fullStatic += ("Среднее Арифметическое значение чисел типа Float равно: " + floatAverage + ".\n");
        }

        if (stringCount != null) {
            fullStatic += ("Количество типов String равно: " + stringCount + ".\n");
            fullStatic += ("Максимальное количество символов в String равно: " + stringCountMax + ".\n");
            fullStatic += ("Минимальное количество символов в String равно: " + stringCountMin + ".\n");
        }

        return fullStatic;
    }

    private String getShortStatics() {
        String shortStats = "";
        if (intMax != null) {
            shortStats = "Количество типов Integer равно: " + intCount + ".\n";

        }

        if (floatMax != null) {
            shortStats += "Количество типов Float равно: " + floatCount + ".\n";

        }

        if (stringCount != null) {
            shortStats += "Количество типов String равно: " + stringCount + ".\n";
        }

        return shortStats;
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



    public void sortTypeData(String line) {
        try {
            if (Pattern.matches("^-?\\d+$", line)) {
                integerWriter.write(line + "\n");
                calculateFullStatisticsInteger(line);
            } else if (Pattern.matches("^-?\\d+(\\.\\d+)?$", line)) {
                floatWriter.write(line + "\n"); //вещественное число
                calculateFullStatisticsFloat(line);
            } else {
                stringWriter.write(line + "\n");//строка
                calculateFullStatisticsString(line);
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
        calculateAverageInt();
    }

    private void calculateFullStatisticsFloat(String line) {
        BigDecimal number = new BigDecimal(line);
        BigInteger increment = BigInteger.ONE;
        floatCount = floatCount.add(increment);

        if (floatMin == null) {
            floatMin = number;
        } else {
            if (floatMin.compareTo(number) > 0) {
                floatMin = number;
            }
        }

        if (floatMax == null) {
            floatMax = number;
        } else {
            if (floatMax.compareTo(number) < 0) {
                floatMax = number;
            }
        }

        sumFloat(number);
        calculateAverageFloat();
    }

    private void calculateFullStatisticsString(String line) {
        BigInteger count = new BigInteger(String.valueOf(line.length()));
        stringCount = stringCount.add(BigInteger.ONE);

        if (stringCountMin == null) {
            stringCountMin = count;
        } else {
            if (stringCountMin.compareTo(count) > 0) {
                stringCountMin = count;
            }
        }

        if (stringCountMax == null) {
            stringCountMax = count;
        } else {
            if (stringCountMax.compareTo(count) < 0) {
                stringCountMax = count;
            }
        }
    }

    private void sumInt(BigInteger number) {
        intSum = intSum.add(number);
    }

    private void sumFloat(BigDecimal number) {
        floatSum = floatSum.add(number);
    }

    private void calculateAverageInt() {
        intAverage = new BigDecimal(intSum.divide(intCount));
    }

    private void calculateAverageFloat() {
        floatAverage = floatSum.divide(new BigDecimal(floatCount));
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
    private void closeWriterStreams() {
        try {
            integerWriter.close();
            floatWriter.close();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}