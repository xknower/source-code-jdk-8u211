/*    */ package com.sun.org.apache.xerces.internal.impl.xs.util;
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
/*    */ public final class XInt
/*    */ {
/*    */   private int fValue;
/*    */   
/*    */   XInt(int value) {
/* 34 */     this.fValue = value;
/*    */   }
/*    */   
/*    */   public final int intValue() {
/* 38 */     return this.fValue;
/*    */   }
/*    */   
/*    */   public final short shortValue() {
/* 42 */     return (short)this.fValue;
/*    */   }
/*    */   
/*    */   public final boolean equals(XInt compareVal) {
/* 46 */     return (this.fValue == compareVal.fValue);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     return Integer.toString(this.fValue);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\x\\util\XInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */