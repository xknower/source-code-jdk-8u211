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
/*    */ public class IntArrayWrapper
/*    */ {
/*    */   private int[] m_int;
/*    */   
/*    */   public IntArrayWrapper(int[] arg) {
/* 34 */     this.m_int = arg;
/*    */   }
/*    */   
/*    */   public int getInt(int index) {
/* 38 */     return this.m_int[index];
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 42 */     return this.m_int.length;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\res\IntArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */