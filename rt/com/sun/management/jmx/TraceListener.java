/*     */ package com.sun.management.jmx;
/*     */ 
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class TraceListener
/*     */   implements NotificationListener
/*     */ {
/*     */   protected PrintStream out;
/*     */   protected boolean needTobeClosed = false;
/*     */   protected boolean formated = false;
/*     */   
/*     */   public TraceListener() {
/*  59 */     this.out = System.out;
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
/*     */   public TraceListener(PrintStream paramPrintStream) throws IllegalArgumentException {
/*  72 */     if (paramPrintStream == null)
/*  73 */       throw new IllegalArgumentException("An PrintStream object should be specified."); 
/*  74 */     this.out = paramPrintStream;
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
/*     */   public TraceListener(String paramString) throws IOException {
/*  86 */     this.out = new PrintStream(new FileOutputStream(paramString, true));
/*     */     
/*  88 */     this.needTobeClosed = true;
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
/*     */   public void setFormated(boolean paramBoolean) {
/* 100 */     this.formated = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleNotification(Notification paramNotification, Object paramObject) {
/* 107 */     if (paramNotification instanceof TraceNotification) {
/* 108 */       TraceNotification traceNotification = (TraceNotification)paramNotification;
/*     */       
/* 110 */       if (this.formated) {
/* 111 */         this.out.print("\nGlobal sequence number: " + traceNotification.globalSequenceNumber + "     Sequence number: " + traceNotification.sequenceNumber + "\nLevel: " + 
/* 112 */             Trace.getLevel(traceNotification.level) + "     Type: " + Trace.getType(traceNotification.type) + "\nClass  Name: " + new String(traceNotification.className) + "\nMethod Name: " + new String(traceNotification.methodName) + "\n");
/*     */ 
/*     */ 
/*     */         
/* 116 */         if (traceNotification.exception != null) {
/* 117 */           traceNotification.exception.printStackTrace(this.out);
/* 118 */           this.out.println();
/*     */         } 
/*     */         
/* 121 */         if (traceNotification.info != null)
/* 122 */           this.out.println("Information: " + traceNotification.info); 
/*     */       } else {
/* 124 */         this.out.print("(" + traceNotification.className + " " + traceNotification.methodName + ") ");
/*     */         
/* 126 */         if (traceNotification.exception != null) {
/* 127 */           traceNotification.exception.printStackTrace(this.out);
/* 128 */           this.out.println();
/*     */         } 
/*     */         
/* 131 */         if (traceNotification.info != null) {
/* 132 */           this.out.println(traceNotification.info);
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
/*     */   public void setFile(String paramString) throws IOException {
/* 145 */     PrintStream printStream = new PrintStream(new FileOutputStream(paramString, true));
/*     */     
/* 147 */     if (this.needTobeClosed) {
/* 148 */       this.out.close();
/*     */     }
/* 150 */     this.out = printStream;
/* 151 */     this.needTobeClosed = true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\TraceListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */