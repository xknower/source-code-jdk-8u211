/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class InitialNameServiceHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public InitialNameService value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public InitialNameServiceHolder() {}
/*    */ 
/*    */   
/*    */   public InitialNameServiceHolder(InitialNameService paramInitialNameService) {
/* 20 */     this.value = paramInitialNameService;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = InitialNameServiceHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     InitialNameServiceHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return InitialNameServiceHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\InitialNameServiceHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */