package com.xknower.multithread.chapter.two;

/**
 * @author xknower
 */
public class ThreadStopping {

    public static void main(String[] args) {
        class StoppedThread extends Thread {
            // defaults to false
            private volatile boolean stopped;

            @Override
            public void run() {
                while (!stopped) {
                    System.out.println("running");
                }
            }

            void stopThread() {
                stopped = true;
            }
        }

        StoppedThread thd = new StoppedThread();
        thd.start();

        try {
            // sleep for 1 second
            Thread.sleep(10 * 1000);
        } catch (InterruptedException ie) {
        }

        thd.stopThread();
    }
}
