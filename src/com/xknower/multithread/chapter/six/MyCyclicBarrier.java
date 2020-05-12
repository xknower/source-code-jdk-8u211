package com.xknower.multithread.chapter.six;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 同步屏障
 *
 * @author xknower
 */
public class MyCyclicBarrier {

    public static void main(String[] args) {
        float[][] matrix = new float[3][3];
        int counter = 0;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = counter++;
            }
        }

        dump(matrix);
        System.out.println();
        Solver solver = new Solver(matrix);
        System.out.println();
        dump(matrix);
    }

    static void dump(float[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.println(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}

class Solver {

    /**
     * 屏障线程数
     */
    private final int N;
    /**
     * 同步屏障
     */
    private final CyclicBarrier barrier;

    private final float[][] data;

    public Solver(float[][] matrix) {
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                mergeRows();
            }
        });

        for (int i = 0; i < N; ++i) {
            //
            new Thread(new Worker(i)).start();
        }

        waitUntilDone();
    }

    void mergeRows() {
        System.out.println("merging");
        synchronized ("abc") {
            //
            "abc".notify();
        }
    }

    void waitUntilDone() {
        synchronized ("abc") {
            try {
                System.out.println("main thread waiting");
                // 阻止线程继续执行, 等待
                "abc".wait();
                System.out.println("main thread notified");
            } catch (InterruptedException ie) {
                System.out.println("main thread interrupted");
            }
        }
    }

    /**
     * 工作线程
     */
    class Worker implements Runnable {
        int myRow;
        boolean done = false;

        Worker(int row) {
            myRow = row;
        }

        boolean done() {
            return done;
        }

        void processRow(int myRow) {
            System.out.println("Processing row: " + myRow);
            for (int i = 0; i < N; i++) {
                data[myRow][i] *= 10;
            }
            done = true;
        }

        @Override
        public void run() {
            while (!done()) {
                processRow(myRow);

                try {
                    // 线程等待
                    barrier.await();
                } catch (InterruptedException ie) {
                    return;
                } catch (BrokenBarrierException bbe) {
                    return;
                }
            }
        }
    }

}
