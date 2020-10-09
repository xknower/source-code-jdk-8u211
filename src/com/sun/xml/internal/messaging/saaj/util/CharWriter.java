/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.io.CharArrayWriter;
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
/*    */ public class CharWriter
/*    */   extends CharArrayWriter
/*    */ {
/*    */   public CharWriter() {}
/*    */   
/*    */   public CharWriter(int size) {
/* 38 */     super(size);
/*    */   }
/*    */   
/*    */   public char[] getChars() {
/* 42 */     return this.buf;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 46 */     return this.count;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\CharWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */