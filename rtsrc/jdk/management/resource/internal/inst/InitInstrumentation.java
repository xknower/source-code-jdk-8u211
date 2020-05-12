/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.util.Arrays;
/*     */ import java.util.PropertyPermission;
/*     */ import jdk.internal.instrumentation.Logger;
/*     */ import jdk.internal.instrumentation.Tracer;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public final class InitInstrumentation
/*     */   implements Runnable
/*     */ {
/*     */   volatile boolean initialized = false;
/*     */   static final Class<?>[] hooks;
/*     */   
/*     */   static {
/*  30 */     Class[] arrayOfClass2, arrayOfClass1 = { AbstractInterruptibleChannelRMHooks.class, AbstractPlainDatagramSocketImplRMHooks.class, AbstractPlainSocketImplRMHooks.class, AsynchronousServerSocketChannelImplRMHooks.class, AsynchronousSocketChannelImplRMHooks.class, BaseSSLSocketImplRMHooks.class, DatagramChannelImplRMHooks.class, DatagramDispatcherRMHooks.class, DatagramSocketRMHooks.class, FileChannelImplRMHooks.class, FileInputStreamRMHooks.class, FileOutputStreamRMHooks.class, NetRMHooks.class, RandomAccessFileRMHooks.class, ServerSocketRMHooks.class, ServerSocketChannelImplRMHooks.class, SocketChannelImplRMHooks.class, SocketDispatcherRMHooks.class, SocketInputStreamRMHooks.class, SocketOutputStreamRMHooks.class, SocketRMHooks.class, SSLSocketImplRMHooks.class, SSLServerSocketImplRMHooks.class, ThreadRMHooks.class, WrapInstrumentationRMHooks.class };
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
/*  58 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"), (AccessControlContext)null, new Permission[] { new PropertyPermission("os.name", "read") });
/*     */     
/*  60 */     if (str.startsWith("Windows")) {
/*     */       
/*  62 */       arrayOfClass2 = new Class[] { WindowsAsynchronousFileChannelImplRMHooks.class, WindowsAsynchronousServerSocketChannelImplRMHooks.class, WindowsAsynchronousSocketChannelImplRMHooks.class };
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  69 */       arrayOfClass2 = new Class[] { SimpleAsynchronousFileChannelImplRMHooks.class, UnixAsynchronousServerSocketChannelImplRMHooks.class, UnixAsynchronousSocketChannelImplRMHooks.class };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     hooks = new Class[arrayOfClass1.length + arrayOfClass2.length];
/*  77 */     System.arraycopy(arrayOfClass1, 0, hooks, 0, arrayOfClass1.length);
/*  78 */     System.arraycopy(arrayOfClass2, 0, hooks, arrayOfClass1.length, arrayOfClass2.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void run() {
/*  86 */     if (!this.initialized) {
/*     */       try {
/*  88 */         Tracer tracer = Tracer.getInstance();
/*  89 */         tracer.addInstrumentations(Arrays.asList(hooks), TestLogger.tlogger);
/*  90 */       } catch (ClassNotFoundException classNotFoundException) {
/*  91 */         TestLogger.tlogger.error("Unable to load class: " + classNotFoundException.getMessage(), classNotFoundException);
/*  92 */       } catch (Exception exception) {
/*  93 */         TestLogger.tlogger.error("Unable to load class: " + exception.getMessage(), exception);
/*     */       } 
/*  95 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class TestLogger
/*     */     implements Logger
/*     */   {
/* 104 */     static final TestLogger tlogger = new TestLogger();
/*     */ 
/*     */     
/*     */     public void debug(String param1String) {
/* 108 */       System.out.printf("TestLogger debug: %s%n", new Object[] { param1String });
/*     */     }
/*     */ 
/*     */     
/*     */     public void error(String param1String) {
/* 113 */       System.out.printf("TestLogger error: %s%n", new Object[] { param1String });
/*     */     }
/*     */ 
/*     */     
/*     */     public void error(String param1String, Throwable param1Throwable) {
/* 118 */       System.out.printf("TestLogger error: %s, ex: %s%n", new Object[] { param1String, param1Throwable });
/* 119 */       param1Throwable.printStackTrace();
/*     */     }
/*     */ 
/*     */     
/*     */     public void info(String param1String) {
/* 124 */       System.out.printf("TestLogger info: %s%n", new Object[] { param1String });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void trace(String param1String) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void warn(String param1String) {
/* 134 */       System.out.printf("TestLogger warning: %s%n", new Object[] { param1String });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\InitInstrumentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */