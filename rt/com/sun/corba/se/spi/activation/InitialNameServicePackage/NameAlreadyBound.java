/*    */ package com.sun.corba.se.spi.activation.InitialNameServicePackage;
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
/*    */ public final class NameAlreadyBound
/*    */   extends UserException
/*    */ {
/*    */   public NameAlreadyBound() {
/* 16 */     super(NameAlreadyBoundHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NameAlreadyBound(String paramString) {
/* 22 */     super(NameAlreadyBoundHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\InitialNameServicePackage\NameAlreadyBound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */