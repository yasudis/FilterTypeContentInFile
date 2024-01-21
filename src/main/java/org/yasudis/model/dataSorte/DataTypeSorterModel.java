package org.yasudis.model.dataSorte;

import org.yasudis.model.dataType.DataType;
import org.yasudis.model.dataType.FloatType;
import org.yasudis.model.dataType.IntegerType;
import org.yasudis.model.dataType.StringType;
import org.yasudis.model.sortOption.SorterParameterByDataType;

import java.io.*;
import java.util.*;

public class DataTypeSorterModel extends DataSorter {
    private SorterParameterByDataType sorterParameterByDataType;
    private HashSet<BufferedReader> readers = new HashSet<>();
    private ArrayList<DataType> dataType = new ArrayList<>();

    public DataTypeSorterModel() {

    }

    public void run() {
        openReaderStreams();
        while (!sortOneLineInFiles()) {

        }

        closeReaderStreams();

    }

    public void setSorterParameter(String[] args) {
        sorterParameterByDataType = new SorterParameterByDataType(args);
        dataType.add(new IntegerType(sorterParameterByDataType.getOutputFileInteger()));
        dataType.add(new FloatType(sorterParameterByDataType.getOutputFileFloat()));
        dataType.add(new StringType(sorterParameterByDataType.getOutputFileString()));
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


        return fullStatic;
    }


    private String getShortStatics() {
        String shortStats = "";


        return shortStats;
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

    /*
         // сортировка через регулярные выражения, не видит научные нотации
           ("-?\\d+([]\\d+)?([Ee]+)?\\d+", line)){

                    } else if (Pattern.matches("^-?\\d+(\\.\\d+)?$", line)) {

                    } else {
                        stringWriter.write(line + "\n");//строка
                        calculateFullStatisticsString(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
     */
    //NumberUtil - не корректно реализует определение типов, не определяет научные нотации и не все дробные числа.
    // Сделал через преобразования BigInteger и BigDecimal - тоже не видит научные нотации как целые числа, но он переводит как понимает стандарт чисел Java.
   /*
    private void sortTypeData(String line) {
        if (isInteger(line)) {
            integerWriter = openWriteStreams(sorterParameterByDataType.getOutputFileInteger(), sorterParameterByDataType.isAppend());

            writeInFile(integerWriter, line);

            calculateFullStatisticsInteger(line);

            closeWriterStreams(integerWriter);
        } else if (isFloat(line)) {
            floatWriter = openWriteStreams(sorterParameterByDataType.getOutputFileFloat(), sorterParameterByDataType.isAppend());

            writeInFile(floatWriter, line);

            calculateFullStatisticsFloat(line);

            closeWriterStreams(floatWriter);
        } else {
            stringWriter = openWriteStreams(sorterParameterByDataType.getOutputFileString(), sorterParameterByDataType.isAppend());

            writeInFile(stringWriter, line);

            calculateFullStatisticsString(line);

            closeWriterStreams(stringWriter);
        }
    }

    */

    public void sortTypeData(String line) {
        for (DataType dataType : dataType) {
            if (dataType.isType(line)) {
                dataType.getOutputFileName();
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
            throw new RuntimeException(ex);
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