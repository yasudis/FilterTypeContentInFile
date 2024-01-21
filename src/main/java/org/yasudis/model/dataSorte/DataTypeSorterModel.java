package org.yasudis.model.dataSorte;

import org.yasudis.model.dataType.DataType;
import org.yasudis.model.dataType.FloatType;
import org.yasudis.model.dataType.IntegerType;
import org.yasudis.model.dataType.StringType;
import org.yasudis.model.sortOption.SorterParameterByDataType;

import java.io.*;
import java.util.*;

/**
 * Сортировка через ловлю исключения преобразования типов BigInteger (для целых чисел) и BigDecimal (для вещественных чисел).
 * Сначала проверяется по списку BigInteger, затем BigDecimal, остальные не нулевые строки будут являться String.
 * Для дополнения новыми типами переменных, нужно создать класс с наследованием DataType, а также добавить в список в классе
 *      DataTypeSorterModel в поле ArrayList<DataType> dataTypes.
 * При добавлении нового типа нужно учесть, что BigDecimal может преобразовать любое число (вещественное, целое), а String
 *      любую строку, поэтому эти операции лучше добавлять в список ArrayList<DataType> dataTypes последними.
 */
/*
   Сортировка через регулярные выражения, не видит научные нотации
        ("-?\\d+([]\\d+)?([Ee]+)?\\d+", line)) - целое
        ("^-?\\d+(\\.\\d+)?$", line)) - вещественное
    NumberUtil - не корректно реализует определение типов, не определяет научные нотации и не все дробные числа.
    Сделал через преобразования BigInteger и BigDecimal - тоже не видит научные нотации как целые числа, но он переводит
        как понимает стандарт чисел Java. Выбрал этот метод из рассуждения, что эти типы распространенные, а значит и ошибок
        будет меньше при использовании другими программами.
 */

public class DataTypeSorterModel extends DataSorter {
    private SorterParameterByDataType sorterParameterByDataType;
    private HashSet<BufferedReader> readers = new HashSet<>();
    private ArrayList<DataType> dataTypes = new ArrayList<>();

    @Override
    public void setSorterParameter(String[] args) {
        sorterParameterByDataType = new SorterParameterByDataType(args);
        dataTypes.add(new IntegerType(sorterParameterByDataType.getOutputFileInteger()));
        dataTypes.add(new FloatType(sorterParameterByDataType.getOutputFileFloat()));
        dataTypes.add(new StringType(sorterParameterByDataType.getOutputFileString()));
    }

    @Override
    public void run() {
        openReaderStreams();
        while (!sortOneLineInFiles()) {

        }

        closeReaderStreams();
    }

    @Override
    public String getStatics() {
        String statistics = "";

        for (DataType dataType : dataTypes) {
            statistics += dataType.getStatistics(sorterParameterByDataType.isShortStatics(), sorterParameterByDataType.isFullStatics());
        }

        return statistics;
    }

    private boolean sortOneLineInFiles() {
        boolean isLine = true;

        for (BufferedReader reader : readers) {
            try {
                // Читаем строку из i-го файла
                String line = reader.readLine();
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


    public void sortTypeData(String line) {
        for (DataType dataType : dataTypes) {
            if (dataType.isType(line)) {
                dataType.getOutputFileName();
                dataType.calculateStatistics(line);
                FileWriter fileWriter = openWriteStreams(dataType.getOutputFileName(), sorterParameterByDataType.isAppend());
                writeInFile(fileWriter, line);
                closeWriterStreams(fileWriter);
                break;
            }
        }
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

    private void writeInFile(FileWriter fileWriter, String line) {
        try {
            fileWriter.write(line + "\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private FileWriter openWriteStreams(String fileName, boolean append) {
        try {
            return new FileWriter(fileName, append);

        } catch (IOException ex) {
            throw new RuntimeException( ex);
        }
    }

    private void closeWriterStreams(FileWriter fileWriter) {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}