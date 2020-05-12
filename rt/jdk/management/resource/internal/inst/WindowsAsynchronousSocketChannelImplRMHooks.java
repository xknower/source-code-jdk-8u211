/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import jdk.internal.instrumentation.InstrumentationMethod;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
/*     */ import jdk.management.resource.internal.ApproverGroup;
/*     */ import jdk.management.resource.internal.CompletionHandlerWrapper;
/*     */ import jdk.management.resource.internal.FutureWrapper;
/*     */ import jdk.management.resource.internal.ResourceIdImpl;
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
/*     */ @InstrumentationTarget("sun.nio.ch.WindowsAsynchronousSocketChannelImpl")
/*     */ public class WindowsAsynchronousSocketChannelImplRMHooks
/*     */ {
/*  32 */   protected final FileDescriptor fd = null;
/*  33 */   protected volatile InetSocketAddress localAddress = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     int i;
/*  52 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/*  53 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
/*     */     
/*  55 */     long l = 0L;
/*     */     
/*  57 */     if (paramBoolean) {
/*  58 */       i = 0;
/*  59 */       for (ByteBuffer byteBuffer : paramArrayOfByteBuffer) {
/*  60 */         i += byteBuffer.remaining();
/*     */       }
/*     */     } else {
/*  63 */       i = paramByteBuffer.remaining();
/*     */     } 
/*     */     
/*     */     try {
/*  67 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/*  68 */       if (l < i) {
/*  69 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/*  71 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  72 */       if (paramCompletionHandler != null) {
/*  73 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/*  74 */         return null;
/*     */       } 
/*  76 */       CompletableFuture<V> completableFuture = new CompletableFuture();
/*  77 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/*  78 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     if (paramCompletionHandler != null) {
/*  83 */       paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/*  86 */     Future<V> future = implRead(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */ 
/*     */     
/*  89 */     if (paramCompletionHandler == null) {
/*  90 */       if (future.isDone()) {
/*  91 */         int j = 0;
/*     */         try {
/*  93 */           j = ((Number)future.get()).intValue();
/*  94 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/*  97 */         j = Math.max(0, j);
/*  98 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 100 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 104 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <V extends Number, A> Future<V> implWrite(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     int i;
/* 115 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 116 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
/*     */     
/* 118 */     long l = 0L;
/*     */     
/* 120 */     if (paramBoolean) {
/* 121 */       i = 0;
/* 122 */       for (ByteBuffer byteBuffer : paramArrayOfByteBuffer) {
/* 123 */         i += byteBuffer.remaining();
/*     */       }
/*     */     } else {
/* 126 */       i = paramByteBuffer.remaining();
/*     */     } 
/*     */     
/*     */     try {
/* 130 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 131 */       if (l < i) {
/* 132 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 134 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 135 */       if (paramCompletionHandler != null) {
/* 136 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 137 */         return null;
/*     */       } 
/* 139 */       CompletableFuture<V> completableFuture = new CompletableFuture();
/* 140 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 141 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 145 */     if (paramCompletionHandler != null) {
/* 146 */       paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 149 */     Future<V> future = implWrite(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */ 
/*     */     
/* 152 */     if (paramCompletionHandler == null) {
/* 153 */       if (future.isDone()) {
/* 154 */         int j = 0;
/*     */         try {
/* 156 */           j = ((Number)future.get()).intValue();
/* 157 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 160 */         j = Math.max(0, j);
/* 161 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 163 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 167 */     return future;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   void implClose() throws IOException {
/*     */     try {
/* 173 */       implClose();
/*     */     } finally {
/* 175 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fd);
/* 176 */       if (resourceIdImpl != null) {
/* 177 */         ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 178 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 183 */       if (this.localAddress != null) {
/*     */         
/* 185 */         resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 186 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 187 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\WindowsAsynchronousSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */