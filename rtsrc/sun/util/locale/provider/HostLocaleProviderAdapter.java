/*    */ package sun.util.locale.provider;
/*    */ 
/*    */ import java.lang.reflect.Method;
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
/*    */ public class HostLocaleProviderAdapter
/*    */   extends AuxLocaleProviderAdapter
/*    */ {
/*    */   public LocaleProviderAdapter.Type getAdapterType() {
/* 45 */     return LocaleProviderAdapter.Type.HOST;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected <P extends java.util.spi.LocaleServiceProvider> P findInstalledProvider(Class<P> paramClass) {
/*    */     try {
/* 52 */       Method method = HostLocaleProviderAdapterImpl.class.getMethod("get" + paramClass
/* 53 */           .getSimpleName(), (Class[])null);
/* 54 */       return (P)method.invoke(null, (Object[])null);
/* 55 */     } catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException noSuchMethodException) {
/*    */ 
/*    */ 
/*    */       
/* 59 */       LocaleServiceProviderPool.config((Class)HostLocaleProviderAdapter.class, noSuchMethodException.toString());
/*    */       
/* 61 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\su\\util\locale\provider\HostLocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */