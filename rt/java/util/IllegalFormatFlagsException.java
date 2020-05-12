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
/*    */ public class IllegalFormatFlagsException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 790824L;
/*    */   private String flags;
/*    */   
/*    */   public IllegalFormatFlagsException(String paramString) {
/* 50 */     if (paramString == null)
/* 51 */       throw new NullPointerException(); 
/* 52 */     this.flags = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFlags() {
/* 61 */     return this.flags;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 65 */     return "Flags = '" + this.flags + "'";
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\IllegalFormatFlagsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */