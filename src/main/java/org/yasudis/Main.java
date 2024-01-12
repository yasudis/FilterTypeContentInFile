package org.yasudis;

import org.yasudis.commandParser.CommandParser;
import org.yasudis.dataSorte.DataSorter;
import org.yasudis.dataSorte.DataTypeSorterInFile;
import org.yasudis.sortOption.SortParameter;
import org.yasudis.sortOption.SorterParameterByDataType;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SorterParameterByDataType sorterParameterByDataType = CommandParser.sortParameterTypes(args);
        DataTypeSorterInFile dataTypeSorterInFile = new DataTypeSorterInFile(sorterParameterByDataType);


       /*
       if (Pattern.matches("^-?\\d+$", line)) {
            ints.add(line); //целое число
        } else if (Pattern.matches("^-?\\d+(\\.\\d+)?$", line)) {
            floats.add(line); //вещественное число
        } else {
            strings.add(line);//строка
        }

        */
    }
}