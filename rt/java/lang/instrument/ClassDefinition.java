/*    */ package java.lang.instrument;
/*    */ 
/*    */ import java.lang.instrument.ClassDefinition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClassDefinition
/*    */ {
/*    */   private final Class<?> mClass;
/*    */   private final byte[] mClassFile;
/*    */   
/*    */   public ClassDefinition(Class<?> paramClass, byte[] paramArrayOfbyte) {
/* 62 */     if (paramClass == null || paramArrayOfbyte == null) {
/* 63 */       throw new NullPointerException();
/*    */     }
/* 65 */     this.mClass = paramClass;
/* 66 */     this.mClassFile = paramArrayOfbyte;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getDefinitionClass() {
/* 76 */     return this.mClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getDefinitionClassFile() {
/* 86 */     return this.mClassFile;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\instrument\ClassDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */