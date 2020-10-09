/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
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
/*    */ public class ByteInputStream
/*    */   extends ByteArrayInputStream
/*    */ {
/* 34 */   private static final byte[] EMPTY_ARRAY = new byte[0];
/*    */   
/*    */   public ByteInputStream() {
/* 37 */     this(EMPTY_ARRAY, 0);
/*    */   }
/*    */   
/*    */   public ByteInputStream(byte[] buf, int length) {
/* 41 */     super(buf, 0, length);
/*    */   }
/*    */   
/*    */   public ByteInputStream(byte[] buf, int offset, int length) {
/* 45 */     super(buf, offset, length);
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 49 */     return this.buf;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 53 */     return this.count;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 57 */     reset();
/*    */   }
/*    */   
/*    */   public void setBuf(byte[] buf) {
/* 61 */     this.buf = buf;
/* 62 */     this.pos = 0;
/* 63 */     this.count = buf.length;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\ByteInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */