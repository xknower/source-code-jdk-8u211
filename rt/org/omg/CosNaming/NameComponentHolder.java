/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class NameComponentHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public NameComponent value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NameComponentHolder() {}
/*    */ 
/*    */   
/*    */   public NameComponentHolder(NameComponent paramNameComponent) {
/* 20 */     this.value = paramNameComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = NameComponentHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     NameComponentHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return NameComponentHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\CosNaming\NameComponentHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */