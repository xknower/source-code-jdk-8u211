/*    */ package com.sun.org.glassfish.gmbal;
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
/*    */ public class GmbalException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -7478444176079980162L;
/*    */   
/*    */   public GmbalException(String msg) {
/* 41 */     super(msg);
/*    */   }
/*    */   
/*    */   public GmbalException(String msg, Throwable thr) {
/* 45 */     super(msg, thr);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\gmbal\GmbalException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */