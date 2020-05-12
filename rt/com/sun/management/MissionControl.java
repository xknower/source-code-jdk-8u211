/*     */ package com.sun.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.StandardMBean;
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
/*     */ public final class MissionControl
/*     */   extends StandardMBean
/*     */   implements MissionControlMXBean
/*     */ {
/*  32 */   private static final ObjectName MBEAN_NAME = Util.newObjectName("com.sun.management:type=MissionControl");
/*     */ 
/*     */   
/*     */   private MBeanServer server;
/*     */ 
/*     */ 
/*     */   
/*     */   public MissionControl() {
/*  40 */     super(MissionControlMXBean.class, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  46 */     this.server = paramMBeanServer;
/*  47 */     return MBEAN_NAME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterMBeans() {
/*  52 */     doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */           public Void run() {
/*  54 */             MissionControl.FlightRecorderHelper.unregisterWithMBeanServer(MissionControl.this.server);
/*  55 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMBeans() {
/*  62 */     doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */           public Void run() throws MalformedObjectNameException {
/*  64 */             if (!MissionControl.this.server.isRegistered(new ObjectName("com.oracle.jrockit:type=FlightRecorder"))) {
/*     */               
/*     */               try {
/*  67 */                 MissionControl.FlightRecorderHelper.registerWithMBeanServer(MissionControl.this.server);
/*  68 */               } catch (IllegalStateException illegalStateException) {}
/*     */             }
/*     */ 
/*     */             
/*  72 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void doPrivileged(PrivilegedExceptionAction<Void> paramPrivilegedExceptionAction) {
/*     */     try {
/*  79 */       AccessController.doPrivileged(paramPrivilegedExceptionAction);
/*  80 */     } catch (PrivilegedActionException privilegedActionException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FlightRecorderHelper
/*     */   {
/*     */     static final String MBEAN_NAME = "com.oracle.jrockit:type=FlightRecorder";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Class<?> getClass(String param1String) {
/*     */       try {
/*  95 */         return Class.forName(param1String, true, FlightRecorderHelper.class.getClassLoader());
/*  96 */       } catch (ClassNotFoundException classNotFoundException) {
/*  97 */         throw new InternalError("jfr.jar missing?", classNotFoundException);
/*     */       } 
/*     */     }
/*     */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/*     */       try {
/* 102 */         return param1Class.getMethod(param1String, param1VarArgs);
/* 103 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 104 */         throw new InternalError(noSuchMethodException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Object invokeStatic(Method param1Method, Object... param1VarArgs) {
/*     */       try {
/* 113 */         return param1Method.invoke(null, param1VarArgs);
/* 114 */       } catch (InvocationTargetException invocationTargetException) {
/* 115 */         Throwable throwable = invocationTargetException.getCause();
/* 116 */         if (throwable instanceof RuntimeException)
/* 117 */           throw (RuntimeException)throwable; 
/* 118 */         if (throwable instanceof Error) {
/* 119 */           throw (Error)throwable;
/*     */         }
/* 121 */         throw new InternalError(throwable);
/* 122 */       } catch (IllegalAccessException illegalAccessException) {
/* 123 */         throw new InternalError(illegalAccessException);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 128 */     private static final Class<?> FLIGHTRECORDER_CLASS = getClass("com.oracle.jrockit.jfr.FlightRecorder");
/*     */ 
/*     */     
/* 131 */     private static final Method REGISTERWITHMBEANSERVER_METHOD = getMethod(FLIGHTRECORDER_CLASS, "registerWithMBeanServer", new Class[] { MBeanServer.class });
/*     */ 
/*     */     
/* 134 */     private static final Method UNREGISTERWITHMBEANSERVER_METHOD = getMethod(FLIGHTRECORDER_CLASS, "unregisterWithMBeanServer", new Class[] { MBeanServer.class });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static void registerWithMBeanServer(MBeanServer param1MBeanServer) {
/* 140 */       invokeStatic(REGISTERWITHMBEANSERVER_METHOD, new Object[] { param1MBeanServer });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static void unregisterWithMBeanServer(MBeanServer param1MBeanServer) {
/* 147 */       invokeStatic(UNREGISTERWITHMBEANSERVER_METHOD, new Object[] { param1MBeanServer });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\MissionControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */