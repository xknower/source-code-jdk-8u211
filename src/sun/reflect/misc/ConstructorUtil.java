/*    */ package sun.reflect.misc;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
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
/*    */ public final class ConstructorUtil
/*    */ {
/*    */   public static Constructor<?> getConstructor(Class<?> paramClass, Class<?>[] paramArrayOfClass) throws NoSuchMethodException {
/* 37 */     ReflectUtil.checkPackageAccess(paramClass);
/* 38 */     return paramClass.getConstructor(paramArrayOfClass);
/*    */   }
/*    */   
/*    */   public static Constructor<?>[] getConstructors(Class<?> paramClass) {
/* 42 */     ReflectUtil.checkPackageAccess(paramClass);
/* 43 */     return paramClass.getConstructors();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\misc\ConstructorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */