/*    */ package java.util;
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
/*    */ public class IllegalFormatPrecisionException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 18711008L;
/*    */   private int p;
/*    */   
/*    */   public IllegalFormatPrecisionException(int paramInt) {
/* 48 */     this.p = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPrecision() {
/* 57 */     return this.p;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 61 */     return Integer.toString(this.p);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\IllegalFormatPrecisionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */