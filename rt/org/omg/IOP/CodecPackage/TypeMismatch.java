/*    */ package org.omg.IOP.CodecPackage;
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
/*    */ public final class TypeMismatch
/*    */   extends UserException
/*    */ {
/*    */   public TypeMismatch() {
/* 16 */     super(TypeMismatchHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeMismatch(String paramString) {
/* 22 */     super(TypeMismatchHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\IOP\CodecPackage\TypeMismatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */