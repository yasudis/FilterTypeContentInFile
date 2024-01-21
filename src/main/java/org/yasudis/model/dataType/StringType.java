package org.yasudis.model.dataType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StringType extends DataType {
    private BigInteger stringCount = BigInteger.ZERO;
    private BigInteger stringCountMin;
    private BigInteger stringCountMax;

    public StringType(String fileName) {
        super(fileName);
    }

    @Override
    public boolean isType(String line) {
        if (line.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String getOutputFileName() {
        return outputFileName;
    }

    @Override
    public void calculateFullStatistics(String line) {
        stringCount = stringCount.add(BigInteger.ONE);

        getStringCountMin(line);
        getIntMax(line);
    }

    private void getStringCountMin(String line) {
        BigInteger count = new BigInteger(String.valueOf(line.length()));
        stringCount = stringCount.add(BigInteger.ONE);

        if (stringCountMin == null) {
            stringCountMin = count;
        } else {
            if (stringCountMin.compareTo(count) > 0) {
                stringCountMin = count;
            }
        }
    }

    private void getIntMax(String line) {
        BigInteger count = new BigInteger(String.valueOf(line.length()));
        stringCount = stringCount.add(BigInteger.ONE);

        if (stringCountMax == null) {
            stringCountMax = count;
        } else {
            if (stringCountMax.compareTo(count) < 0) {
                stringCountMax = count;
            }
        }
    }

    public String getStatics(boolean isFullStatics, boolean isShortStatics) {
        if (isFullStatics) {
            return getFullStatics();
        }

        if (isShortStatics) {
            return getShortStatics();
        }

        return "Параметры выводы статистики не были заданы.";
    }

    private String getShortStatics() {
        String shortStats = "";
        if (stringCount.equals(0)) {
            shortStats += "Количество типов String равно: " + stringCount + ".\n";
        }

        return shortStats;
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (stringCount.equals(0)) {
            fullStatic += ("Количество типов String равно: " + stringCount + ".\n");
            fullStatic += ("Максимальное количество символов в String равно: " + stringCountMax + ".\n");
            fullStatic += ("Минимальное количество символов в String равно: " + stringCountMin + ".\n");
        }
        return fullStatic;
    }
}
