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
/*    */ public class StringArrayWrapper
/*    */ {
/*    */   private String[] m_string;
/*    */   
/*    */   public StringArrayWrapper(String[] arg) {
/* 34 */     this.m_string = arg;
/*    */   }
/*    */   
/*    */   public String getString(int index) {
/* 38 */     return this.m_string[index];
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 42 */     return this.m_string.length;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\res\StringArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */