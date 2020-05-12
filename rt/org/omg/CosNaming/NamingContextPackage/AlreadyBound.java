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
/*    */ public final class AlreadyBound
/*    */   extends UserException
/*    */ {
/*    */   public AlreadyBound() {
/* 16 */     super(AlreadyBoundHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AlreadyBound(String paramString) {
/* 22 */     super(AlreadyBoundHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\AlreadyBound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */