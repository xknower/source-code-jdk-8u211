/*    */ package org.omg.CosNaming.NamingContextPackage;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class CannotProceedHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public CannotProceed value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public CannotProceedHolder() {}
/*    */ 
/*    */   
/*    */   public CannotProceedHolder(CannotProceed paramCannotProceed) {
/* 20 */     this.value = paramCannotProceed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = CannotProceedHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     CannotProceedHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return CannotProceedHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NamingContextPackage\CannotProceedHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */