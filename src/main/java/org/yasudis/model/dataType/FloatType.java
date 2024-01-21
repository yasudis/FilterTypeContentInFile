package org.yasudis.model.dataType;

import java.io.FileWriter;
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
        dataTypes.add(this);
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
    public void calculateFullStatistics(String line) {
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
        if (floatCount.equals(0)) {
            shortStats = "Количество типов Integer равно: " + floatCount + ".\n";
        }

        return shortStats;
    }

    private String getFullStatics() {
        String fullStatic = "";

        if (floatCount.equals(0)) {
            fullStatic = "Максимальное число типа Integer равно: " + floatMax + ".\n";
            fullStatic += ("Минимальное число типа Integer равно: " + floatMin + ".\n");
            fullStatic += ("Сумма чисел типа Integer равно: " + floatSum + ".\n");
            fullStatic += ("Среднее Арифметическое значение чисел типа Integer равно: " + floatAverage + ".\n");
            fullStatic += ("-------------------------\n");
        }
        return fullStatic;
    }
}
