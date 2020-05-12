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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IllegalFormatCodePointException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19080630L;
/*    */   private int c;
/*    */   
/*    */   public IllegalFormatCodePointException(int paramInt) {
/* 53 */     this.c = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCodePoint() {
/* 63 */     return this.c;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 67 */     return String.format("Code point = %#x", new Object[] { Integer.valueOf(this.c) });
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\IllegalFormatCodePointException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */