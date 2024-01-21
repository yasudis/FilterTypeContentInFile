package org.yasudis.model.dataType;

import java.util.HashSet;

public abstract class DataType {
    public static HashSet<DataType> dataTypes = new HashSet<>();
    String outputFileName;

    DataType(String fileName) {
        //dataTypes.add(dataType);
        this.outputFileName = fileName;
    }

    public boolean isType(String line) {
        return false;
    }

  public String getOutputFileName() {
        return outputFileName;
    }

    void calculateFullStatistics(String line) {

    }


}
