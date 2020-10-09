/*    */ package sun.nio.fs;
/*    */ 
/*    */ import sun.misc.Cleaner;
/*    */ import sun.misc.Unsafe;
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
/*    */ class NativeBuffer
/*    */ {
/* 36 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*    */   
/*    */   private final long address;
/*    */   private final int size;
/*    */   private final Cleaner cleaner;
/*    */   private Object owner;
/*    */   
/*    */   private static class Deallocator
/*    */     implements Runnable
/*    */   {
/*    */     private final long address;
/*    */     
/*    */     Deallocator(long param1Long) {
/* 49 */       this.address = param1Long;
/*    */     }
/*    */     public void run() {
/* 52 */       NativeBuffer.unsafe.freeMemory(this.address);
/*    */     }
/*    */   }
/*    */   
/*    */   NativeBuffer(int paramInt) {
/* 57 */     this.address = unsafe.allocateMemory(paramInt);
/* 58 */     this.size = paramInt;
/* 59 */     this.cleaner = Cleaner.create(this, new Deallocator(this.address));
/*    */   }
/*    */   
/*    */   void release() {
/* 63 */     NativeBuffers.releaseNativeBuffer(this);
/*    */   }
/*    */   
/*    */   long address() {
/* 67 */     return this.address;
/*    */   }
/*    */   
/*    */   int size() {
/* 71 */     return this.size;
/*    */   }
/*    */   
/*    */   Cleaner cleaner() {
/* 75 */     return this.cleaner;
/*    */   }
/*    */ 
/*    */   
/*    */   void setOwner(Object paramObject) {
/* 80 */     this.owner = paramObject;
/*    */   }
/*    */ 
/*    */   
/*    */   Object owner() {
/* 85 */     return this.owner;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\fs\NativeBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */