package com.xknower.utils.aop.watch;

import com.xknower.utils.aop.AbstractAopAdvice;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

import java.util.concurrent.TimeUnit;

/**
 * 方法运行效率监控执行器
 */
@Aspect
public class WatchAdvice extends AbstractAopAdvice {

    @Override
    public void processAround(ProceedingJoinPoint point, final Object result, long current, long processTime) throws Throwable {
        Watch watch = getAnnotation(point, Watch.class);
        long offset = watch.limitUnit().convert(processTime, TimeUnit.MILLISECONDS);
        if (offset > watch.limit()) {
            String log = String.format("[ %s ][ %s ]超过阀值[ %s ], 最大[ %s ], 单位[ %s ]",
                    point.getTarget().getClass(),
                    watch.value(),
                    offset,
                    watch.limit(),
                    watch.limitUnit()
            );
            System.out.println(log);
        }
    }
}