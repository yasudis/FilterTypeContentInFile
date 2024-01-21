package org.yasudis.model.dataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class FloatType extends DataType {
    private BigInteger floatCount = BigInteger.ZERO;
    private BigDecimal floatAverage = BigDecimal.ZERO;
    private BigDecimal floatMin;
    private BigDecimal floatMax;
    private BigDecimal floatSum = BigDecimal.ZERO;

    public FloatType(String fileName) {
        super(fileName);
    }

    @Override
    public boolean isType(String line) {
        try {
            BigDecimal bigDecimal = new BigDecimal(line);
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
    public void calculateStatistics(String line) {
        BigDecimal number = new BigDecimal(line);
        floatCount = floatCount.add(BigInteger.ONE);

        getFloatMin(number);
        getFloatMax(number);

        sumFloat(number);
        calculateAverageFloat();
    }

    private void getFloatMin(BigDecimal number) {
        if (floatMin == null) {
            floatMin = number;
        } else {
            if (floatMin.compareTo(number) > 0) {
                floatMin = number;
            }
        }
    }

    private void getFloatMax(BigDecimal number) {
        if (floatMax == null) {
            floatMax = number;
        } else {
            if (floatMax.compareTo(number) < 0) {
                floatMax = number;
            }
        }
    }

    private void calculateAverageFloat() {
        floatAverage = floatSum.divide(new BigDecimal(floatCount), 10, RoundingMode.HALF_UP);
    }

    private void sumFloat(BigDecimal number) {
        floatSum = floatSum.add(number);
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
        if (!floatCount.equals(0)) {
            shortStats = "Количество типов Float равно: " + floatCount + ".\n";
        }

        return shortStats;
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (!floatCount.equals(0)) {
            fullStatic = "Максимальное число типа Float равно: " + floatMax + ".\n";
            fullStatic += ("Минимальное число типа Float равно: " + floatMin + ".\n");
            fullStatic += ("Сумма чисел типа Float равно: " + floatSum + ".\n");
            fullStatic += ("Среднее Арифметическое значение чисел типа Float равно: " + floatAverage + ".\n");
            fullStatic += ("-------------------------\n");
        }

        return fullStatic;
    }
}
