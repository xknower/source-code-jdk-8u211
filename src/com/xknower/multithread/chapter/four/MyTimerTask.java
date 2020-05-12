package com.xknower.multithread.chapter.four;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器框架
 *
 * @author xknower
 */
public class MyTimerTask {

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + " alarm going off");
                // Timer 为非守护线程执行时, 程序需要等到非守护任务执行线程终止之后才能终止
                // 主动终止程序
//                System.exit(0);
            }
        };

        //
        Timer timer = new Timer();
        // Execute one-shot timer task after 2-second delay.
        timer.schedule(task, 2000);

        // 固定时间间隔重复执行任务
//        timer.schedule(task, 0, 1000);
    }
}
