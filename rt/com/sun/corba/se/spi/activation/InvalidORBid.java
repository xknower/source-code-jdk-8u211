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
/*    */ public final class InvalidORBid
/*    */   extends UserException
/*    */ {
/*    */   public InvalidORBid() {
/* 16 */     super(InvalidORBidHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidORBid(String paramString) {
/* 22 */     super(InvalidORBidHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\InvalidORBid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */