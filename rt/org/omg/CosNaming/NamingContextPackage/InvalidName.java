/*    */ package org.omg.CosNaming.NamingContextPackage;
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
/*    */ public final class InvalidName
/*    */   extends UserException
/*    */ {
/*    */   public InvalidName() {
/* 16 */     super(InvalidNameHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidName(String paramString) {
/* 22 */     super(InvalidNameHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\InvalidName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */