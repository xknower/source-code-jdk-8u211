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
/*    */ abstract class UnsafeQualifiedStaticFieldAccessorImpl
/*    */   extends UnsafeStaticFieldAccessorImpl
/*    */ {
/*    */   protected final boolean isReadOnly;
/*    */   
/*    */   UnsafeQualifiedStaticFieldAccessorImpl(Field paramField, boolean paramBoolean) {
/* 42 */     super(paramField);
/* 43 */     this.isReadOnly = paramBoolean;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\UnsafeQualifiedStaticFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */