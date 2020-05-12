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
/*    */ public final class NotEmpty
/*    */   extends UserException
/*    */ {
/*    */   public NotEmpty() {
/* 16 */     super(NotEmptyHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NotEmpty(String paramString) {
/* 22 */     super(NotEmptyHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\NotEmpty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */