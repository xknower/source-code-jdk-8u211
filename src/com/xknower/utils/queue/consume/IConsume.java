package com.xknower.utils.queue.consume;

import com.xknower.utils.queue.IMsg;
import com.xknower.utils.queue.IQueue;

/**
 * 消息队列消费者
 *
 * @param <T>
 * @author xknower
 */
public class IConsume<T extends IMsg> extends Thread {

    /**
     * 关闭消费者
     */
    private boolean close = false;

    /**
     * 暂停消费
     */
    private boolean pause = false;

    private final IQueue<T> msgQueue;

    public IConsume(String name, IQueue<T> msgQueue) {
        this.close = false;
        this.pause = false;
        this.msgQueue = msgQueue;
        this.setDaemon(true);
        this.setName(name == null ? "消费队列[" + msgQueue.name() + "]" : name);
    }

    public void startRun() {
        this.pause = false;
    }

    public void pause() {
        this.pause = true;
    }

    public void close() {
        this.pause = true;
        this.close = true;
    }

    /**
     * 消费队列
     *
     * @throws Exception
     */
    public void consume() throws Exception {
        if (!pause) {
            System.out.println(String.format("execute consume of %s -> %s", this.getName(), this.msgQueue.name()));
            Thread.sleep(5 * 1000L);
        }
    }

    @Override
    public void run() {
        while (!close) {
            try {
                consume();
            } catch (Exception e) {
            }
        }
    }
}
