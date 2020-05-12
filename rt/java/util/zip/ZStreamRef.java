/*    */ package java.util.zip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ZStreamRef
/*    */ {
/*    */   private volatile long address;
/*    */   
/*    */   ZStreamRef(long paramLong) {
/* 36 */     this.address = paramLong;
/*    */   }
/*    */   
/*    */   long address() {
/* 40 */     return this.address;
/*    */   }
/*    */   
/*    */   void clear() {
/* 44 */     this.address = 0L;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\zip\ZStreamRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */