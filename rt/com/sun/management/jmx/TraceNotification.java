/*     */ package com.sun.management.jmx;
/*     */ 
/*     */ import javax.management.Notification;
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
/*     */ public class TraceNotification
/*     */   extends Notification
/*     */ {
/*     */   public int level;
/*     */   public int type;
/*     */   public String className;
/*     */   public String methodName;
/*     */   public String info;
/*     */   public Throwable exception;
/*     */   public long globalSequenceNumber;
/*     */   public long sequenceNumber;
/*     */   
/*     */   public TraceNotification(Object paramObject, long paramLong1, long paramLong2, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, Throwable paramThrowable) {
/*  93 */     super(null, paramObject, paramLong1);
/*     */     
/*  95 */     this.sequenceNumber = paramLong1;
/*  96 */     this.globalSequenceNumber = paramLong2;
/*  97 */     this.level = paramInt1;
/*  98 */     this.type = paramInt2;
/*  99 */     this.className = (paramString1 != null) ? paramString1 : "";
/* 100 */     this.methodName = (paramString2 != null) ? paramString2 : "";
/* 101 */     this.info = (paramString3 != null) ? paramString3 : null;
/* 102 */     this.exception = paramThrowable;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\TraceNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */