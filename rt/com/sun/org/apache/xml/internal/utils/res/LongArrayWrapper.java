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
/*    */ public class LongArrayWrapper
/*    */ {
/*    */   private long[] m_long;
/*    */   
/*    */   public LongArrayWrapper(long[] arg) {
/* 34 */     this.m_long = arg;
/*    */   }
/*    */   
/*    */   public long getLong(int index) {
/* 38 */     return this.m_long[index];
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 42 */     return this.m_long.length;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\res\LongArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */