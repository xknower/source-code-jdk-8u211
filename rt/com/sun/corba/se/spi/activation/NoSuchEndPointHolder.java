/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class NoSuchEndPointHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public NoSuchEndPoint value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NoSuchEndPointHolder() {}
/*    */ 
/*    */   
/*    */   public NoSuchEndPointHolder(NoSuchEndPoint paramNoSuchEndPoint) {
/* 20 */     this.value = paramNoSuchEndPoint;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = NoSuchEndPointHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     NoSuchEndPointHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return NoSuchEndPointHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\NoSuchEndPointHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */