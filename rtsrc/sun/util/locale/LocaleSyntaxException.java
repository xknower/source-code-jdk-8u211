/*    */ package sun.util.locale;
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
/*    */ public class LocaleSyntaxException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 38 */   private int index = -1;
/*    */   
/*    */   public LocaleSyntaxException(String paramString) {
/* 41 */     this(paramString, 0);
/*    */   }
/*    */   
/*    */   public LocaleSyntaxException(String paramString, int paramInt) {
/* 45 */     super(paramString);
/* 46 */     this.index = paramInt;
/*    */   }
/*    */   
/*    */   public int getErrorIndex() {
/* 50 */     return this.index;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\su\\util\locale\LocaleSyntaxException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */