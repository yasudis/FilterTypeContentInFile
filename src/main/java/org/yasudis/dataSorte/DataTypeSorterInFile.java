package org.yasudis.dataSorte;

import org.yasudis.sortOption.SorterParameterByDataType;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class DataTypeSorterInFile extends DataSorter {
    private SorterParameterByDataType sorterParameterByDataType;
    private FileWriter integerWriter;
    private FileWriter floatWriter;
    private FileWriter stringWriter;
    private int intCount; // количество целых чисел
    private int floatCount; // количество натуральных чисел
    private int stringCount; // количество строк
    private int intMin; // минимальное целое число
    private int intMax; // максимальное целое число
    private int intSum; // сумма целых чисел
    private double floatMin; // минимальное натуральное число
    private double floatMax; // максимальное натуральное число
    private double floatSum; // сумма натуральных чисел
    private int stringMin; // длина самой короткой строки
    private int stringMax; // длина самой длинной строки


    public DataTypeSorterInFile(String[] args) {
        sorterParameterByDataType = new SorterParameterByDataType(args);
        readFile(sorterParameterByDataType.getInputFiles());
    }

    public void readFile(List<File> files) {

        FileWriter integerWriter;
        FileWriter floatWriter;
        FileWriter stringWriter;
        FileWriter resultWriter;
        try {
            integerWriter = new FileWriter("int.txt");
            floatWriter = new FileWriter("float.txt");
            stringWriter = new FileWriter("strings.txt");
            resultWriter = new FileWriter("result.txt");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        List<BufferedReader> readers = new ArrayList<>();
        for (File file : files) {
            try {
                readers.add(new BufferedReader(new FileReader(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < readers.size(); i++) {
                try {
                    // Читаем строку из i-го файла
                    String line = readers.get(i).readLine();
                    if (line != null) {
                        // Выводим строку на экран
                        sortTypeData(line, integerWriter, floatWriter, stringWriter);
                        // Устанавливаем флаг, что еще не достигли конца всех файлов
                        done = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (BufferedReader reader : readers) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            integerWriter.

                    close();
            floatWriter.

                    close();
            stringWriter.

                    close();
        } catch (
                IOException e) {
            e.

                    printStackTrace();
        }
    }

    public static void sortTypeData(String line, FileWriter intWriter, FileWriter floatWriter, FileWriter
            stringWriter) {
        try {

            //result(line);
            if (Pattern.matches("^-?\\d+$", line)) {
                intWriter.write(line + "\n");
            } else if (Pattern.matches("^-?\\d+(\\.\\d+)?$", line)) {
                floatWriter.write(line + "\n"); //вещественное число
            } else {
                stringWriter.write(line + "\n");//строка
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void writeFileInt(String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("int.txt"))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeFileString(String line) throws IOException {
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

    class Pair implements Comparable<Pair> {
        String fileName;
        String currentLine;
        BufferedReader bufferedReader;

        public Pair(String fileName, BufferedReader bufferedReader) {
            this.fileName = fileName;
            this.bufferedReader = bufferedReader;
            try {
                this.currentLine = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int compareTo(Pair other) {
            return this.currentLine.compareTo(other.currentLine);
        }
    }

}