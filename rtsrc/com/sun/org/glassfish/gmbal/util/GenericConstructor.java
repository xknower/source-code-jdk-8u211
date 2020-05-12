/*     */ package com.sun.org.glassfish.gmbal.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenericConstructor<T>
/*     */ {
/*  40 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private String typeName;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<T> resultType;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> type;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?>[] signature;
/*     */ 
/*     */ 
/*     */   
/*     */   private Constructor constructor;
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericConstructor(Class<T> type, String className, Class<?>... signature) {
/*  65 */     this.resultType = type;
/*  66 */     this.typeName = className;
/*  67 */     this.signature = (Class[])signature.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getConstructor() {
/*  72 */     synchronized (this.lock) {
/*  73 */       if (this.type == null || this.constructor == null) {
/*     */         try {
/*  75 */           this.type = Class.forName(this.typeName);
/*  76 */           this.constructor = AccessController.<Constructor>doPrivileged(new PrivilegedExceptionAction<Constructor>()
/*     */               {
/*     */                 public Constructor run() throws Exception {
/*  79 */                   synchronized (GenericConstructor.this.lock) {
/*  80 */                     return GenericConstructor.this.type.getDeclaredConstructor(GenericConstructor.this.signature);
/*     */                   } 
/*     */                 }
/*     */               });
/*  84 */         } catch (Exception exc) {
/*     */           
/*  86 */           Logger.getLogger("com.sun.org.glassfish.gmbal.util").log(Level.FINE, "Failure in getConstructor", exc);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized T create(Object... args) {
/* 103 */     synchronized (this.lock) {
/* 104 */       T result = null;
/*     */       
/* 106 */       for (int ctr = 0; ctr <= 1; ctr++) {
/* 107 */         getConstructor();
/* 108 */         if (this.constructor == null) {
/*     */           break;
/*     */         }
/*     */         
/*     */         try {
/* 113 */           result = this.resultType.cast(this.constructor.newInstance(args));
/*     */           break;
/* 115 */         } catch (Exception exc) {
/*     */ 
/*     */           
/* 118 */           this.constructor = null;
/* 119 */           Logger.getLogger("com.sun.org.glassfish.gmbal.util")
/* 120 */             .log(Level.WARNING, "Error invoking constructor", exc);
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       return result;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\gmba\\util\GenericConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */