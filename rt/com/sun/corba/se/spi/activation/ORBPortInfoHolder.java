/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ORBPortInfoHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ORBPortInfo value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ORBPortInfoHolder() {}
/*    */ 
/*    */   
/*    */   public ORBPortInfoHolder(ORBPortInfo paramORBPortInfo) {
/* 20 */     this.value = paramORBPortInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ORBPortInfoHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ORBPortInfoHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ORBPortInfoHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\ORBPortInfoHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */