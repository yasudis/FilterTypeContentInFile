package org.yasudis.model.dataType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerType extends DataType {
    private BigInteger intCount = BigInteger.ZERO;
    private BigInteger intMin;
    private BigInteger intMax;
    private BigInteger intSum = BigInteger.ZERO;
    private BigDecimal intAverage = BigDecimal.ZERO;

    public IntegerType(String fileName) {
        super(fileName);
    }

    @Override
    public boolean isType(String line) {
        try {
            BigInteger bigInteger = new BigInteger(line);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getOutputFileName() {
        return outputFileName;
    }

    @Override
    public void calculateStatistics(String line, boolean isFullStatics) {
        BigInteger number = new BigInteger(line);
        intCount = intCount.add(BigInteger.ONE);

        if (isFullStatics) {
            calculateIntMin(number);
            calculateIntMax(number);

            sumInt(number);
            calculateAverageInt();
        }
    }

    private void calculateIntMin(BigInteger number) {
        if (intMin == null) {
            intMin = number;
        } else {
            if (intMin.compareTo(number) > 0) {
                intMin = number;
            }
        }
    }

    private void calculateIntMax(BigInteger number) {
        if (intMax == null) {
            intMax = number;
        } else {
            if (intMax.compareTo(number) < 0) {
                intMax = number;
            }
        }
    }

    private void calculateAverageInt() {
        intAverage = new BigDecimal(intSum.divide(intCount), 10);
    }

    private void sumInt(BigInteger number) {
        intSum = intSum.add(number);
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

        if (!intCount.equals(0)) {
            shortStats = "Количество типов Integer равно: " + intCount + ".\n";
        }

        return shortStats;
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (!intCount.equals(0)) {
            fullStatic = "Максимальное число типа Integer равно: " + intMax + ".\n";
            fullStatic += ("Минимальное число типа Integer равно: " + intMin + ".\n");
            fullStatic += ("Сумма чисел типа Integer равно: " + intSum + ".\n");
            fullStatic += ("Среднее Арифметическое значение чисел типа Integer равно: " + intAverage + ".\n");
            fullStatic += ("-------------------------\n");
        }
        return fullStatic;
    }
}
