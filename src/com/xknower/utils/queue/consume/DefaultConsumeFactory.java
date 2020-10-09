package com.xknower.utils.queue.consume;

import com.xknower.utils.queue.IQueue;
import com.xknower.utils.queue.impl.DefaultQueueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 消息队列及其消费者工厂类
 *
 * @author xknower
 */
public class DefaultConsumeFactory extends DefaultQueueFactory {

    /**
     * 消息队列个数
     */
    private int messageQueueCount = 1;
    /**
     * 消息队列中消息容量个数
     */
    private int messageQueueSize = 1;
    /**
     * 一个消息队列, 对应消费者倍数
     */
    private int multiple = 1;

    /**
     * 消费队列 , 一个队列对应一个消费者 (初始化时根据配置创建)
     */
    private List<List<IConsume>> consumeList;

    public DefaultConsumeFactory() {
    }

    public DefaultConsumeFactory(int messageQueueCount, int messageQueueSize, int multiple) {
        this.messageQueueCount = messageQueueCount;
        this.messageQueueSize = messageQueueSize;
        this.multiple = multiple;
    }

    public List<IConsume> build(int index) {
        System.out.println(String.format("创建消息消费队列 => [ %s ]", index));

        // 创建并获取消息队列
        IQueue msgQueue = super.build(index, this.messageQueueSize);

        // 为消息队列, 创建相应消费者
        return IntStream.range(0, this.multiple)
                .mapToObj((i) -> {
                    IConsume consume = new IConsume(index * i + "", msgQueue);
                    consume.start();
                    return consume;
                })
                .collect(Collectors.toList());
    }

    public void build() throws Exception {
        this.consumeList = IntStream
                .range(0, messageQueueCount)
                .mapToObj(this::build)
                .collect(Collectors.toList());
    }

    public void destroy() throws Exception {
        consumeList.forEach(is -> is.forEach(i -> i.close()));
    }
}
