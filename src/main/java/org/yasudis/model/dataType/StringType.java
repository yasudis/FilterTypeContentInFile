package org.yasudis.model.dataType;

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
        return !line.isEmpty();
    }

    @Override
    public void calculateStatistics(String line) {
        stringCount = stringCount.add(BigInteger.ONE);

        calculateStringCountMin(line);
        calculateStringCountMax(line);
    }

    private void calculateStringCountMin(String line) {
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

    private void calculateStringCountMax(String line) {
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

    @Override
    public String getStatistics(boolean isShortStatics, boolean isFullStatics) {
        String statistics = "";
        if (isShortStatics) {
            return getShortStatics();
        }

        if (isFullStatics) {
            statistics += getShortStatics();
            return statistics + getFullStatics();
        }

        return "Параметры выводы статистики не были заданы.";
    }

    private String getShortStatics() {
        String shortStats = "";
        if (!stringCount.equals(0)) {
            shortStats += "Количество типов String равно: " + stringCount + ".\n";
        }

        return shortStats;
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (!stringCount.equals(0)) {
            fullStatic += ("Максимальное количество символов в String равно: " + stringCountMax + ".\n");
            fullStatic += ("Минимальное количество символов в String равно: " + stringCountMin + ".\n");
        }
        return fullStatic;
    }
}
