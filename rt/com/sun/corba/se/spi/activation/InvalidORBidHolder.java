/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class InvalidORBidHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public InvalidORBid value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidORBidHolder() {}
/*    */ 
/*    */   
/*    */   public InvalidORBidHolder(InvalidORBid paramInvalidORBid) {
/* 20 */     this.value = paramInvalidORBid;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = InvalidORBidHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     InvalidORBidHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return InvalidORBidHelper.type();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\activation\InvalidORBidHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */