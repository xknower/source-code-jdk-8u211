/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.io.CharArrayReader;
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
/*    */ public class CharReader
/*    */   extends CharArrayReader
/*    */ {
/*    */   public CharReader(char[] buf, int length) {
/* 34 */     super(buf, 0, length);
/*    */   }
/*    */   
/*    */   public CharReader(char[] buf, int offset, int length) {
/* 38 */     super(buf, offset, length);
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


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\CharReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */