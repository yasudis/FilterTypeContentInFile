package org.yasudis.model.dataType;

public abstract class DataType {
    String outputFileName;

    DataType(String fileName) {
        this.outputFileName = fileName;
    }

    public boolean isType(String line) {
        return false;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void calculateStatistics(String line) {

    }

    public String getStatistics(boolean isFullStatics, boolean isShortStatics){
        return "";
    }


}
