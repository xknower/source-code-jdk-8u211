package com.xknower.multithread.chapter.one;

import java.math.BigDecimal;

/**
 * 线程等待
 *
 * @author xknower
 */
public class MyThreadJoin {

    /**
     * constant used in pi computation
     */
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);

    /**
     * rounding made to use during pi computation
     */
    private static final int rouundingMode = BigDecimal.ROUND_HALF_EVEN;

    private static BigDecimal result;

    /**
     * 计算常量PI, 精确到 50000 位
     * <p>
     * Compute the value of pi to the specified number of digits after the decimal point.
     * The value is computed using Machin's formula:
     * <p>
     * pi/4 = 4 * arctan(1/5) - arctan(1/239)
     * <p>
     * and a power series expansion of arctan(x) to sufficient precision.
     */
    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
        return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Compute the value, in radians, of the arctangent of the inverse of
     * the supplied integer to the specified number of digits after the decimal point.
     * The value is computed using the power series expansion for the arc tangent:
     * <p>
     * arctan(x) = x- (x^3)/3 + (x^5)/5 - (x^7)/7 + (x^9)/9 ...
     */
    public static BigDecimal arctan(int inverseX, int scale) {
        BigDecimal result, number, term;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
        number = BigDecimal.ONE.divide(invX, scale, rouundingMode);
        result = number;
        int i = 1;
        do {
            number = number.divide(invX2, scale, rouundingMode);
            int denom = 2 * i + 1;
            term = number.divide(BigDecimal.valueOf(denom), scale, rouundingMode);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        } while (term.compareTo(BigDecimal.ZERO) != 0);

        return result;
    }

    public static void main(String[] args) {
        //
        Runnable r = () -> {
            result = computePi(500000);
        };

        Thread t = new Thread(r);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie) {
            // should never arrive here because interrupt() is never called.
        }

        System.out.println(result);
    }
}
