/*    */ package com.sun.xml.internal.stream.util;
/*    */ 
/*    */ import java.lang.ref.SoftReference;
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
/*    */ public class ThreadLocalBufferAllocator
/*    */ {
/* 42 */   private static ThreadLocal tlba = new ThreadLocal();
/*    */   
/*    */   public static BufferAllocator getBufferAllocator() {
/* 45 */     SoftReference<BufferAllocator> bAllocatorRef = tlba.get();
/* 46 */     if (bAllocatorRef == null || bAllocatorRef.get() == null) {
/* 47 */       bAllocatorRef = new SoftReference<>(new BufferAllocator());
/* 48 */       tlba.set(bAllocatorRef);
/*    */     } 
/*    */     
/* 51 */     return bAllocatorRef.get();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\strea\\util\ThreadLocalBufferAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */