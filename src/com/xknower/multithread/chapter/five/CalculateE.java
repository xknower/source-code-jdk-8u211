package com.xknower.multithread.chapter.five;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.*;

/**
 * 计算 E
 *
 * @author xknower
 */
public class CalculateE {

    final static int LASTITER = 17;

    public static void main(String[] args) {
        //
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<BigDecimal> callable;

        callable = new Callable<BigDecimal>() {
            @Override
            public BigDecimal call() throws Exception {
                MathContext mc = new MathContext(100, RoundingMode.HALF_UP);
                BigDecimal result = BigDecimal.ZERO;
                for (int i = 0; i <= LASTITER; i++) {
                    BigDecimal factorial = factorial(new BigDecimal(i));
                    BigDecimal res = BigDecimal.ONE.divide(factorial, mc);
                    result = result.add(res);
                }
                return result;
            }

            public BigDecimal factorial(BigDecimal n) {
                if (n.equals(BigDecimal.ZERO)) {
                    return BigDecimal.ONE;
                } else {
                    return n.multiply(factorial(n.subtract(BigDecimal.ONE)));
                }
            }
        };

        //
        Future<BigDecimal> taskFuture = executorService.submit(callable);
        try {
            while (!taskFuture.isDone()) {
                System.out.println("waiting");
            }
            System.out.println(taskFuture.get());
        } catch (ExecutionException ee) {
            System.err.println("task threw an exception");
            System.err.println(ee);
        } catch (InterruptedException ie) {
            System.err.println("interrupted while waiting");
        }

        //
        executorService.shutdownNow();
    }
}
