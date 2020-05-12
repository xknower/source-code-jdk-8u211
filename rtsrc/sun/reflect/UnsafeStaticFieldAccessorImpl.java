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
/*    */ abstract class UnsafeStaticFieldAccessorImpl
/*    */   extends UnsafeFieldAccessorImpl
/*    */ {
/*    */   protected final Object base;
/*    */   
/*    */   static {
/* 42 */     Reflection.registerFieldsToFilter(UnsafeStaticFieldAccessorImpl.class, new String[] { "base" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   UnsafeStaticFieldAccessorImpl(Field paramField) {
/* 49 */     super(paramField);
/* 50 */     this.base = unsafe.staticFieldBase(paramField);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\UnsafeStaticFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */