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
/*    */ public class UnknownFormatFlagsException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 19370506L;
/*    */   private String flags;
/*    */   
/*    */   public UnknownFormatFlagsException(String paramString) {
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
/*    */   
/*    */   public String getMessage() {
/* 66 */     return "Flags = " + this.flags;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\UnknownFormatFlagsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */