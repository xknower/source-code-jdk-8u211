package com.xknower.utils.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 1> 定义切点及注解
 * 2> 实现切面处理业务
 *
 * @author xknower
 */
public abstract class AbstractAopAdvice {

    // 定义切面和处理逻辑

    /**
     * 定义切点 - 扫描所有带 [Aop] 注解的方法
     */
    @Pointcut("execution(* com.xknower.utils.aop..*(..)) && @annotation(com.xknower.utils.aop.Aop)")
    public void pointcut() {
    }

    /**
     * 环绕通知方式处理，出现异常向上一层抛出
     *
     * @param point
     * @return
     * @throws Throwable 向外抛出异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 01 记录执行时间
        long startCurrent = System.currentTimeMillis();
        // 请求执行 -> 抛出异常 Throwable
        final Object result = point.proceed();
        // 计算处理时间
        long processTime = System.currentTimeMillis() - startCurrent;

        // 处理数据
        processAround(point, result, startCurrent, processTime);

        // 返回请求执行结果
        return result;
    }

    /**
     * 处理业务实现
     *
     * @param point       ProceedingJoinPoint
     * @param result      执行结果
     * @param current     执行开始时间
     * @param processTime 执行时长
     */
    protected abstract void processAround(ProceedingJoinPoint point, final Object result, long current, long processTime) throws Throwable;

    // 公共方法

    protected <T> T getAnnotation(JoinPoint point, Class clazz) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
        return (T) method.getAnnotation(clazz);
    }

    protected String getMethodName(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        return method.getName();
    }

//    protected Map<String, Object> getArgs(JoinPoint point) // throws NotFoundException
//    {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Map<String, Object> map = new HashMap<>();
////        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
////        String[] parameterNames = u.getParameterNames(signature.getMethod());
////        for (int i = 0; i < parameterNames.length; i++) {
////            // paramNames 即参数名
////            map.put(parameterNames[i], point.getArgs()[i]);
////        }
//        //
//        return map;
//    }
}
