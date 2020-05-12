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
/*    */ public class ShortSignature
/*    */   implements BaseType
/*    */ {
/* 32 */   private static final ShortSignature singleton = new ShortSignature();
/*    */ 
/*    */   
/*    */   public static ShortSignature make() {
/* 36 */     return singleton;
/*    */   }
/*    */   public void accept(TypeTreeVisitor<?> paramTypeTreeVisitor) {
/* 39 */     paramTypeTreeVisitor.visitShortSignature(this);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\generics\tree\ShortSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */