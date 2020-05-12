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
/*    */ public final class FormatMismatch
/*    */   extends UserException
/*    */ {
/*    */   public FormatMismatch() {
/* 16 */     super(FormatMismatchHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FormatMismatch(String paramString) {
/* 22 */     super(FormatMismatchHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\IOP\CodecPackage\FormatMismatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */