/*    */ package sun.reflect.misc;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Trampoline
/*    */ {
/*    */   static {
/* 50 */     if (Trampoline.class.getClassLoader() == null) {
/* 51 */       throw new Error("Trampoline must not be defined by the bootstrap classloader");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void ensureInvocableMethod(Method paramMethod) throws InvocationTargetException {
/* 59 */     Class<?> clazz = paramMethod.getDeclaringClass();
/* 60 */     if (clazz.equals(AccessController.class) || clazz
/* 61 */       .equals(Method.class) || clazz
/* 62 */       .getName().startsWith("java.lang.invoke.")) {
/* 63 */       throw new InvocationTargetException(new UnsupportedOperationException("invocation not supported"));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Object invoke(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws InvocationTargetException, IllegalAccessException {
/* 70 */     ensureInvocableMethod(paramMethod);
/* 71 */     return paramMethod.invoke(paramObject, paramArrayOfObject);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\misc\Trampoline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */