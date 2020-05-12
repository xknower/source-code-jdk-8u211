/*    */ package com.sun.jmx.mbeanserver;
/*    */ 
/*    */ import javax.management.loading.ClassLoaderRepository;
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
/*    */ final class SecureClassLoaderRepository
/*    */   implements ClassLoaderRepository
/*    */ {
/*    */   private final ClassLoaderRepository clr;
/*    */   
/*    */   public SecureClassLoaderRepository(ClassLoaderRepository paramClassLoaderRepository) {
/* 48 */     this.clr = paramClassLoaderRepository;
/*    */   }
/*    */   
/*    */   public final Class<?> loadClass(String paramString) throws ClassNotFoundException {
/* 52 */     return this.clr.loadClass(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public final Class<?> loadClassWithout(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 57 */     return this.clr.loadClassWithout(paramClassLoader, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public final Class<?> loadClassBefore(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
/* 62 */     return this.clr.loadClassBefore(paramClassLoader, paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\mbeanserver\SecureClassLoaderRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */