/*    */ package sun.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class UnsafeQualifiedFieldAccessorImpl
/*    */   extends UnsafeFieldAccessorImpl
/*    */ {
/*    */   protected final boolean isReadOnly;
/*    */   
/*    */   UnsafeQualifiedFieldAccessorImpl(Field paramField, boolean paramBoolean) {
/* 49 */     super(paramField);
/* 50 */     this.isReadOnly = paramBoolean;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\UnsafeQualifiedFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */