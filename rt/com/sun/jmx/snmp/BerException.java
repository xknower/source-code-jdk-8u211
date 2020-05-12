/*    */ package com.sun.jmx.snmp;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BerException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 494709767137042951L;
/*    */   public static final int BAD_VERSION = 1;
/* 47 */   private int errorType = 0;
/*    */   
/*    */   public BerException() {
/* 50 */     this.errorType = 0;
/*    */   }
/*    */   
/*    */   public BerException(int paramInt) {
/* 54 */     this.errorType = paramInt;
/*    */   }
/*    */   
/*    */   public boolean isInvalidSnmpVersion() {
/* 58 */     if (this.errorType == 1) {
/* 59 */       return true;
/*    */     }
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\BerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */