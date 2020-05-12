/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NoSuchEndPoint
/*    */   extends UserException
/*    */ {
/*    */   public NoSuchEndPoint() {
/* 16 */     super(NoSuchEndPointHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoSuchEndPoint(String paramString) {
/* 22 */     super(NoSuchEndPointHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\NoSuchEndPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */