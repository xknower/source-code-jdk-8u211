/*    */ package com.sun.xml.internal.bind.util;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ class SecureLoader
/*    */ {
/*    */   static ClassLoader getContextClassLoader() {
/* 37 */     if (System.getSecurityManager() == null) {
/* 38 */       return Thread.currentThread().getContextClassLoader();
/*    */     }
/* 40 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*    */         {
/*    */           public ClassLoader run() {
/* 43 */             return Thread.currentThread().getContextClassLoader();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   static ClassLoader getClassClassLoader(final Class c) {
/* 50 */     if (System.getSecurityManager() == null) {
/* 51 */       return c.getClassLoader();
/*    */     }
/* 53 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*    */         {
/*    */           public ClassLoader run() {
/* 56 */             return c.getClassLoader();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   static ClassLoader getSystemClassLoader() {
/* 63 */     if (System.getSecurityManager() == null) {
/* 64 */       return ClassLoader.getSystemClassLoader();
/*    */     }
/* 66 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*    */         {
/*    */           public ClassLoader run() {
/* 69 */             return ClassLoader.getSystemClassLoader();
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bin\\util\SecureLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */