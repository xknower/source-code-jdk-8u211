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
/*    */ public class MissingFormatWidthException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 15560123L;
/*    */   private String s;
/*    */   
/*    */   public MissingFormatWidthException(String paramString) {
/* 51 */     if (paramString == null)
/* 52 */       throw new NullPointerException(); 
/* 53 */     this.s = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormatSpecifier() {
/* 62 */     return this.s;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 66 */     return this.s;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\MissingFormatWidthException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */