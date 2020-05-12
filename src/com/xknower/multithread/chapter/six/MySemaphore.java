package com.xknower.multithread.chapter.six;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 *
 * @author xknower
 */
public class MySemaphore {

    public static void main(String[] args) {
        // 创建资源池
        final Pool pool = new Pool();

        // 获取资源, 执行业务, 释放资源
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                try {
                    while (true) {
                        String item;
                        // 获取信号量
                        System.out.println(name + " acquiring" + (item = pool.getItem()));
                        // 获取到执行条件, 执行
                        Thread.sleep(500 + (int) (Math.random() * 100));
                        System.out.println(name + " putting back " + item);
                        // 释放信号量
                        pool.putItem(item);
                    }
                } catch (InterruptedException ie) {
                    System.out.println(name + "interrupted");
                }
            }
        };

        ExecutorService[] executorServices = new ExecutorService[Pool.MAX_AVAILABLE + 1];

        for (int i = 0; i < executorServices.length; i++) {
            executorServices[i] = Executors.newSingleThreadExecutor();
            executorServices[i].execute(r);
        }
    }
}

final class Pool {

    public static final int MAX_AVAILABLE = 10;

    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    private final String[] items;
    private final boolean[] used = new boolean[MAX_AVAILABLE];

    Pool() {
        items = new String[MAX_AVAILABLE];
        for (int i = 0; i < items.length; i++) {
            items[i] = "I" + i;
        }
    }

    /**
     * 获取信号量
     *
     * @return
     * @throws InterruptedException
     */
    String getItem() throws InterruptedException {
        available.acquire();
        return getNextAvailableItem();
    }

    /**
     * 释放信号量
     *
     * @param item
     */
    void putItem(String item) {
        if (markAsUnused(item)) {
            available.release();
        }
    }

    private synchronized String getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (!used[i]) {
                // 设置标识已使用
                used[i] = true;
                return items[i];
            }
        }
        // not reached
        return null;
    }

    private synchronized boolean markAsUnused(String item) {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items[i]) {
                if (used[i]) {
                    // 设置标识未使用
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
