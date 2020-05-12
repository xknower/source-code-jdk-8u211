/*    */ package com.sun.org.apache.xml.internal.utils.res;
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
/*    */ public class CharArrayWrapper
/*    */ {
/*    */   private char[] m_char;
/*    */   
/*    */   public CharArrayWrapper(char[] arg) {
/* 34 */     this.m_char = arg;
/*    */   }
/*    */   
/*    */   public char getChar(int index) {
/* 38 */     return this.m_char[index];
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 42 */     return this.m_char.length;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\res\CharArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */