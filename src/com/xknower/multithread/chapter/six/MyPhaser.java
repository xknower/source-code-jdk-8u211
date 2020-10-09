package com.xknower.multithread.chapter.six;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Phaser 同步器 [使用Phaser来控制一个一次性的动作, 该动作作用于可变数量的线程上]
 *
 * @author xknower
 */
public class MyPhaser {

    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();

        tasks.add(() -> System.out.printf("%s running at %d%n",
                Thread.currentThread().getName(), System.currentTimeMillis()));

        tasks.add(() -> System.out.printf("%s running at %d%n",
                Thread.currentThread().getName(), System.currentTimeMillis()));

        tasks.add(() -> System.out.printf("%s running at %d%n",
                Thread.currentThread().getName(), System.currentTimeMillis()));

        runTasks(tasks);
    }

    /**
     * 同时执行任务
     *
     * @param tasks 任务列表
     */
    static void runTasks(List<Runnable> tasks) {
        // "1" (register self)
        final Phaser phaser = new Phaser(1);

        // create and start threads
        for (final Runnable task : tasks) {
            phaser.register();
            Runnable r = () -> {
                try {
                    Thread.sleep(1000 + (int) (Math.random() * 300));
                } catch (InterruptedException ie) {
                    System.out.println("interrupted thread");
                }

                // await the creation of all tasks
                phaser.arriveAndAwaitAdvance();

                //
                task.run();
            };

            Executors.newSingleThreadExecutor().execute(r);
        }

        // allow threads to start and deregister self
        phaser.arriveAndDeregister();
    }
}
