/*    */ package org.omg.IOP.CodecFactoryPackage;
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
/*    */ public final class UnknownEncoding
/*    */   extends UserException
/*    */ {
/*    */   public UnknownEncoding() {
/* 16 */     super(UnknownEncodingHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UnknownEncoding(String paramString) {
/* 22 */     super(UnknownEncodingHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\IOP\CodecFactoryPackage\UnknownEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */