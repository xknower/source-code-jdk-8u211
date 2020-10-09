/*    */ package sun.reflect.generics.tree;
/*    */ 
/*    */ import sun.reflect.generics.visitor.TypeTreeVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloatSignature
/*    */   implements BaseType
/*    */ {
/* 32 */   private static final FloatSignature singleton = new FloatSignature();
/*    */ 
/*    */   
/*    */   public static FloatSignature make() {
/* 36 */     return singleton;
/*    */   } public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 38 */     paramTypeTreeVisitor.visitFloatSignature(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\generics\tree\FloatSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */