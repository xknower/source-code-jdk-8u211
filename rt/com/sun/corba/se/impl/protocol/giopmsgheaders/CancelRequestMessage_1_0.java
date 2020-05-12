/*    */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CancelRequestMessage_1_0
/*    */   extends Message_1_0
/*    */   implements CancelRequestMessage
/*    */ {
/* 41 */   private int request_id = 0;
/*    */ 
/*    */   
/*    */   CancelRequestMessage_1_0() {}
/*    */ 
/*    */   
/*    */   CancelRequestMessage_1_0(int paramInt) {
/* 48 */     super(1195986768, false, (byte)2, 4);
/*    */     
/* 50 */     this.request_id = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequestId() {
/* 56 */     return this.request_id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(InputStream paramInputStream) {
/* 62 */     super.read(paramInputStream);
/* 63 */     this.request_id = paramInputStream.read_ulong();
/*    */   }
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 67 */     super.write(paramOutputStream);
/* 68 */     paramOutputStream.write_ulong(this.request_id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void callback(MessageHandler paramMessageHandler) throws IOException {
/* 74 */     paramMessageHandler.handleInput(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\protocol\giopmsgheaders\CancelRequestMessage_1_0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */