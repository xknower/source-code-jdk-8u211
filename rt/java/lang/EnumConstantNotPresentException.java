/*    */ package java.lang;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumConstantNotPresentException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -6046998521960521108L;
/*    */   private Class<? extends Enum> enumType;
/*    */   private String constantName;
/*    */   
/*    */   public EnumConstantNotPresentException(Class<? extends Enum> paramClass, String paramString) {
/* 62 */     super(paramClass.getName() + "." + paramString);
/* 63 */     this.enumType = paramClass;
/* 64 */     this.constantName = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<? extends Enum> enumType() {
/* 72 */     return this.enumType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String constantName() {
/* 79 */     return this.constantName;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\EnumConstantNotPresentException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */