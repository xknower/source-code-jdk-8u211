/*    */ package com.sun.corba.se.impl.protocol;
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
/*    */ 
/*    */ 
/*    */ public class AddressingDispositionException
/*    */   extends RuntimeException
/*    */ {
/* 47 */   private short expectedAddrDisp = 0;
/*    */   
/*    */   public AddressingDispositionException(short paramShort) {
/* 50 */     this.expectedAddrDisp = paramShort;
/*    */   }
/*    */   
/*    */   public short expectedAddrDisp() {
/* 54 */     return this.expectedAddrDisp;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\protocol\AddressingDispositionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */