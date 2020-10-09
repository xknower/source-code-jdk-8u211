/*    */ package com.sun.xml.internal.messaging.saaj.packaging.mime.util;
/*    */ 
/*    */ import java.io.OutputStream;
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
/*    */ public class BEncoderStream
/*    */   extends BASE64EncoderStream
/*    */ {
/*    */   public BEncoderStream(OutputStream out) {
/* 51 */     super(out, 2147483647);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int encodedLength(byte[] b) {
/* 60 */     return (b.length + 2) / 3 * 4;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\packaging\mim\\util\BEncoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */