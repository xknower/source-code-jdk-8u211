package com.xknower.utils.queue.impl;

import com.xknower.utils.HexUtils;
import com.xknower.utils.queue.consume.DefaultConsumeFactory;

public class QuMain {

    public static void main(String[] args) throws Exception {
        DefaultMsg defaultMsg = ParserFactory.parser(HexUtils.toByteArray(""), DefaultMsg.class);
        System.out.println(defaultMsg);
        DefaultConsumeFactory defaultConsumeFactory = new DefaultConsumeFactory();
        defaultConsumeFactory.build();
        System.out.println();
        Thread.sleep(30 * 1000L);
    }
}
