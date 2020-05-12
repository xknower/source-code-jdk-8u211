/*    */ package org.omg.PortableInterceptor;
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
/*    */ public final class InvalidSlot
/*    */   extends UserException
/*    */ {
/*    */   public InvalidSlot() {
/* 16 */     super(InvalidSlotHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidSlot(String paramString) {
/* 22 */     super(InvalidSlotHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\PortableInterceptor\InvalidSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */