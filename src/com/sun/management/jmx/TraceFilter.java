/*    */ package com.sun.management.jmx;
/*    */ 
/*    */ import javax.management.Notification;
/*    */ import javax.management.NotificationFilter;
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
/*    */ @Deprecated
/*    */ public class TraceFilter
/*    */   implements NotificationFilter
/*    */ {
/*    */   protected int levels;
/*    */   protected int types;
/*    */   
/*    */   public TraceFilter(int paramInt1, int paramInt2) throws IllegalArgumentException {
/* 39 */     this.levels = paramInt1;
/* 40 */     this.types = paramInt2;
/*    */   }
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
/*    */   public boolean isNotificationEnabled(Notification paramNotification) {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLevels() {
/* 62 */     return this.levels;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTypes() {
/* 71 */     return this.types;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\management\jmx\TraceFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */