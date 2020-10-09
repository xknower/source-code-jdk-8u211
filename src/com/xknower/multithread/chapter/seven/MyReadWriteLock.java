package com.xknower.multithread.chapter.seven;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author xknower
 */
public class MyReadWriteLock {
    public static void main(String[] args) {
        final String[] words = {
                "hypocalcemia",
                "prolixity",
                "assiduous",
                "indefatigable",
                "castellan"
        };

        final String[] definitions = {
                "a deficiency of calcium in the blood",
                "unduly pro longed or drawn out",
                "showing great care, attention, and effort",

                "able to work or continue fro a lengthy time with out tiring",
                "the govenor or warden of a castle or fort"
        };

        final Map<String, String> dictionary = new HashMap<>();

        //
        ReadWriteLock rwl = new ReentrantReadWriteLock(true);
        final Lock readLock = rwl.readLock();
        final Lock writeLock = rwl.writeLock();

        //
        Runnable reader = () -> {
            while (true) {
                readLock.lock();
                try {
                    int i = (int) (Math.random() * words.length);
                    System.out.println("reader accessing " + words[i] + ": " + dictionary.get(words[i]) + " entry");
                } finally {
                    readLock.unlock();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(reader);

        //
        Runnable writer = () -> {
            for (int i = 0; i < words.length; i++) {
                writeLock.lock();
                try {
                    dictionary.put(words[i], definitions[i]);
                    System.out.println("writer storing " + words[i] + " entry");
                } finally {
                    writeLock.unlock();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    System.err.println("writer interrupted");
                }
            }
        };

        executorService = Executors.newFixedThreadPool(1);
        executorService.submit(writer);
    }
}
