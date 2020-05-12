/*    */ package com.sun.nio.sctp;
/*    */ 
/*    */ import jdk.Exported;
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
/*    */ @Exported
/*    */ public abstract class AssociationChangeNotification
/*    */   implements Notification
/*    */ {
/*    */   public abstract Association association();
/*    */   
/*    */   public abstract AssocChangeEvent event();
/*    */   
/*    */   @Exported
/*    */   public enum AssocChangeEvent
/*    */   {
/* 47 */     COMM_UP,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     COMM_LOST,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     RESTART,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     SHUTDOWN,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     CANT_START;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\nio\sctp\AssociationChangeNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */