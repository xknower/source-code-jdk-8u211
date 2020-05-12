/*    */ package sun.reflect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ByteVectorFactory
/*    */ {
/*    */   static ByteVector create() {
/* 30 */     return new ByteVectorImpl();
/*    */   }
/*    */   
/*    */   static ByteVector create(int paramInt) {
/* 34 */     return new ByteVectorImpl(paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\reflect\ByteVectorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */