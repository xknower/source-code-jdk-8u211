/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.ReaderThread;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderThreadImpl
/*     */   implements ReaderThread, Work
/*     */ {
/*     */   private ORB orb;
/*     */   private Connection connection;
/*     */   private Selector selector;
/*     */   private boolean keepRunning;
/*     */   private long enqueueTime;
/*     */   
/*     */   public ReaderThreadImpl(ORB paramORB, Connection paramConnection, Selector paramSelector) {
/*  53 */     this.orb = paramORB;
/*  54 */     this.connection = paramConnection;
/*  55 */     this.selector = paramSelector;
/*  56 */     this.keepRunning = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  66 */     return this.connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  71 */     if (this.orb.transportDebugFlag) {
/*  72 */       dprint(".close: " + this.connection);
/*     */     }
/*     */     
/*  75 */     this.keepRunning = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doWork() {
/*     */     try {
/*  87 */       if (this.orb.transportDebugFlag) {
/*  88 */         dprint(".doWork: Start ReaderThread: " + this.connection);
/*     */       }
/*  90 */       while (this.keepRunning) {
/*     */         
/*     */         try {
/*  93 */           if (this.orb.transportDebugFlag) {
/*  94 */             dprint(".doWork: Start ReaderThread cycle: " + this.connection);
/*     */           }
/*     */ 
/*     */           
/*  98 */           if (this.connection.read()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 103 */           if (this.orb.transportDebugFlag) {
/* 104 */             dprint(".doWork: End ReaderThread cycle: " + this.connection);
/*     */           
/*     */           }
/*     */         }
/* 108 */         catch (Throwable throwable) {
/* 109 */           if (this.orb.transportDebugFlag) {
/* 110 */             dprint(".doWork: exception in read: " + this.connection, throwable);
/*     */           }
/* 112 */           this.orb.getTransportManager().getSelector(0)
/* 113 */             .unregisterForEvent(getConnection().getEventHandler());
/* 114 */           getConnection().close();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 118 */       if (this.orb.transportDebugFlag) {
/* 119 */         dprint(".doWork: Terminated ReaderThread: " + this.connection);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnqueueTime(long paramLong) {
/* 126 */     this.enqueueTime = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getEnqueueTime() {
/* 131 */     return this.enqueueTime;
/*     */   }
/*     */   public String getName() {
/* 134 */     return "ReaderThread";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 143 */     ORBUtility.dprint("ReaderThreadImpl", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString, Throwable paramThrowable) {
/* 148 */     dprint(paramString);
/* 149 */     paramThrowable.printStackTrace(System.out);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\transport\ReaderThreadImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */