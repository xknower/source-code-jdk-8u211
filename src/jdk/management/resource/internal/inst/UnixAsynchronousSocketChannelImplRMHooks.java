/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
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
/*     */ @InstrumentationTarget("sun.nio.ch.UnixAsynchronousSocketChannelImpl")
/*     */ public class UnixAsynchronousSocketChannelImplRMHooks
/*     */ {
/*  32 */   protected volatile InetSocketAddress localAddress = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <A> Future<Void> implConnect(SocketAddress paramSocketAddress, A paramA, CompletionHandler<Void, ? super A> paramCompletionHandler) {
/*  40 */     boolean bool = (this.localAddress != null) ? true : false;
/*     */     
/*  42 */     if (paramCompletionHandler != null && !bool) {
/*  43 */       paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler, this);
/*     */     }
/*     */     
/*  46 */     Future<Void> future = implConnect(paramSocketAddress, paramA, paramCompletionHandler);
/*     */     
/*  48 */     if (future != null && !bool) {
/*  49 */       if (future.isDone()) {
/*  50 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/*  51 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*  52 */         long l = 0L;
/*  53 */         ResourceRequestDeniedException resourceRequestDeniedException = null;
/*     */         try {
/*  55 */           l = resourceRequest.request(1L, resourceIdImpl);
/*  56 */           if (l < 1L) {
/*  57 */             resourceRequestDeniedException = new ResourceRequestDeniedException("Resource limited: too many open sockets");
/*     */           }
/*  59 */         } catch (ResourceRequestDeniedException resourceRequestDeniedException1) {
/*  60 */           resourceRequestDeniedException = resourceRequestDeniedException1;
/*     */         } 
/*     */         
/*  63 */         if (resourceRequestDeniedException != null) {
/*  64 */           resourceRequest.request(-l, resourceIdImpl);
/*  65 */           CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  66 */           completableFuture.completeExceptionally(resourceRequestDeniedException);
/*  67 */           future = completableFuture;
/*     */           try {
/*  69 */             implClose();
/*  70 */           } catch (IOException iOException) {}
/*     */         }
/*     */         else {
/*     */           
/*  74 */           resourceRequest.request(-(l - 1L), resourceIdImpl);
/*     */         } 
/*     */       } else {
/*  77 */         future = new FutureWrapper<>(future, this);
/*     */       } 
/*     */     }
/*     */     
/*  81 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
/*     */     int i;
/*  92 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/*  93 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
/*     */     
/*  95 */     long l = 0L;
/*     */     
/*  97 */     if (paramBoolean) {
/*  98 */       i = 0;
/*  99 */       for (ByteBuffer byteBuffer : paramArrayOfByteBuffer) {
/* 100 */         i += byteBuffer.remaining();
/*     */       }
/*     */     } else {
/* 103 */       i = paramByteBuffer.remaining();
/*     */     } 
/*     */     
/*     */     try {
/* 107 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 108 */       if (l < i) {
/* 109 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 111 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 112 */       if (paramCompletionHandler != null) {
/* 113 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 114 */         return null;
/*     */       } 
/* 116 */       CompletableFuture<V> completableFuture = new CompletableFuture();
/* 117 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 118 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (paramCompletionHandler != null) {
/* 123 */       paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 126 */     Future<V> future = implRead(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */ 
/*     */     
/* 129 */     if (paramCompletionHandler == null) {
/* 130 */       if (future.isDone()) {
/* 131 */         int j = 0;
/*     */         try {
/* 133 */           j = ((Number)future.get()).intValue();
/* 134 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 137 */         j = Math.max(0, j);
/* 138 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 140 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 144 */     return future;
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
/* 155 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 156 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
/*     */     
/* 158 */     long l = 0L;
/*     */     
/* 160 */     if (paramBoolean) {
/* 161 */       i = 0;
/* 162 */       for (ByteBuffer byteBuffer : paramArrayOfByteBuffer) {
/* 163 */         i += byteBuffer.remaining();
/*     */       }
/*     */     } else {
/* 166 */       i = paramByteBuffer.remaining();
/*     */     } 
/*     */     
/*     */     try {
/* 170 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 171 */       if (l < i) {
/* 172 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 174 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 175 */       if (paramCompletionHandler != null) {
/* 176 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 177 */         return null;
/*     */       } 
/* 179 */       CompletableFuture<V> completableFuture = new CompletableFuture();
/* 180 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 181 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     if (paramCompletionHandler != null) {
/* 186 */       paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 189 */     Future<V> future = implWrite(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
/*     */ 
/*     */     
/* 192 */     if (paramCompletionHandler == null) {
/* 193 */       if (future.isDone()) {
/* 194 */         int j = 0;
/*     */         try {
/* 196 */           j = ((Number)future.get()).intValue();
/* 197 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 200 */         j = Math.max(0, j);
/* 201 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 203 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 207 */     return future;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   void implClose() throws IOException {
/*     */     try {
/* 213 */       implClose();
/*     */     }
/*     */     finally {
/*     */       
/* 217 */       if (this.localAddress != null) {
/*     */         
/* 219 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
/* 220 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 221 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\UnixAsynchronousSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */