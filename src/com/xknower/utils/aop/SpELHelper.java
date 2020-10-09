package com.xknower.utils.aop;
//
//import org.springframework.expression.Expression;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// *
// */
//class SpELHelper {
//
//    private static ConcurrentHashMap<String, Expression> m = new ConcurrentHashMap<>();
//
//    /**
//     * 动态执行
//     */
//    public static <T> T exec(String exp, Map<String, Object> values) {
//        Expression expression;
//        if (m.contains(exp)) {
//            expression = m.get(exp);
//        } else {
//            expression = new SpelExpressionParser().parseExpression(exp);
//            m.put(exp, expression);
//        }
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setVariables(values);
//        return (T) expression.getValue(context);
//    }
//}
