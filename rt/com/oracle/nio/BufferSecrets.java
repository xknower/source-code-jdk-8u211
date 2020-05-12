/*     */ package com.oracle.nio;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import sun.misc.JavaNioAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.ch.DirectBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BufferSecrets<A>
/*     */ {
/*  34 */   private static final JavaNioAccess javaNioAccess = SharedSecrets.getJavaNioAccess();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final BufferSecrets<?> theBufferSecrets = new BufferSecrets();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A> BufferSecrets<A> instance() {
/*  55 */     SecurityManager securityManager = System.getSecurityManager();
/*  56 */     if (securityManager != null)
/*  57 */       securityManager.checkPermission(new BufferSecretsPermission("access")); 
/*  58 */     return (BufferSecrets)theBufferSecrets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer newDirectByteBuffer(long paramLong, int paramInt, A paramA) {
/*  90 */     if (paramInt < 0)
/*  91 */       throw new IllegalArgumentException("Negative capacity: " + paramInt); 
/*  92 */     return javaNioAccess.newDirectByteBuffer(paramLong, paramInt, paramA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long address(Buffer paramBuffer) {
/* 103 */     if (paramBuffer instanceof DirectBuffer)
/* 104 */       return ((DirectBuffer)paramBuffer).address(); 
/* 105 */     if (paramBuffer == null) {
/* 106 */       throw new NullPointerException();
/*     */     }
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public A attachment(Buffer paramBuffer) {
/* 120 */     if (paramBuffer instanceof DirectBuffer)
/* 121 */       return (A)((DirectBuffer)paramBuffer).attachment(); 
/* 122 */     if (paramBuffer == null)
/* 123 */       throw new NullPointerException(); 
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void truncate(Buffer paramBuffer) {
/* 147 */     javaNioAccess.truncate(paramBuffer);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\oracle\nio\BufferSecrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */