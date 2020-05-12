/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AsynchronousServerSocketChannel;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
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
/*     */ @InstrumentationTarget("sun.nio.ch.AsynchronousServerSocketChannelImpl")
/*     */ public class AsynchronousServerSocketChannelImplRMHooks
/*     */ {
/*     */   @InstrumentationMethod
/*     */   public final SocketAddress getLocalAddress() throws IOException {
/*  32 */     return getLocalAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final AsynchronousServerSocketChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  38 */     ResourceIdImpl resourceIdImpl = null;
/*  39 */     ResourceRequest resourceRequest = null;
/*  40 */     long l = 0L;
/*     */ 
/*     */     
/*  43 */     if (getLocalAddress() == null) {
/*  44 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  45 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  48 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  49 */         if (l < 1L) {
/*  50 */           throw new IOException("Resource limited: too many open socket channels");
/*     */         }
/*  52 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  53 */         throw new IOException("Resource limited: too many open socket channels", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     boolean bool = false;
/*  58 */     AsynchronousServerSocketChannel asynchronousServerSocketChannel = null;
/*     */     try {
/*  60 */       asynchronousServerSocketChannel = bind(paramSocketAddress, paramInt);
/*  61 */       bool = true;
/*     */     } finally {
/*  63 */       if (resourceRequest != null)
/*     */       {
/*  65 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return asynchronousServerSocketChannel;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final Future<AsynchronousSocketChannel> accept() {
/*  74 */     Future<AsynchronousSocketChannel> future = accept();
/*     */     
/*  76 */     if (future.isDone()) {
/*     */       AsynchronousSocketChannel asynchronousSocketChannel;
/*     */       try {
/*  79 */         asynchronousSocketChannel = future.get();
/*  80 */       } catch (InterruptedException interruptedException) {
/*  81 */         CompletableFuture<AsynchronousSocketChannel> completableFuture = new CompletableFuture();
/*  82 */         completableFuture.completeExceptionally(interruptedException);
/*  83 */         return completableFuture;
/*  84 */       } catch (ExecutionException executionException) {
/*  85 */         CompletableFuture<AsynchronousSocketChannel> completableFuture = new CompletableFuture();
/*  86 */         completableFuture.completeExceptionally(executionException.getCause());
/*  87 */         return completableFuture;
/*     */       } 
/*     */       
/*  90 */       ResourceIdImpl resourceIdImpl = null;
/*     */       try {
/*  92 */         resourceIdImpl = ResourceIdImpl.of(asynchronousSocketChannel.getLocalAddress());
/*  93 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/*  96 */       ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(asynchronousSocketChannel);
/*     */       
/*  98 */       long l = 0L;
/*  99 */       ResourceRequestDeniedException resourceRequestDeniedException = null;
/*     */       try {
/* 101 */         l = resourceRequest.request(1L, resourceIdImpl);
/* 102 */         if (l < 1L) {
/* 103 */           resourceRequestDeniedException = new ResourceRequestDeniedException("Resource limited: too many open server socket channels");
/*     */         }
/* 105 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException1) {
/* 106 */         resourceRequestDeniedException = resourceRequestDeniedException1;
/*     */       } 
/*     */       
/* 109 */       if (resourceRequestDeniedException == null) {
/* 110 */         resourceRequest.request(-(l - 1L), resourceIdImpl);
/*     */       } else {
/* 112 */         resourceRequest.request(-l, resourceIdImpl);
/*     */         try {
/* 114 */           asynchronousSocketChannel.close();
/* 115 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 118 */         CompletableFuture<AsynchronousSocketChannel> completableFuture = new CompletableFuture();
/* 119 */         completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 120 */         return completableFuture;
/*     */       } 
/*     */     } else {
/* 123 */       FutureWrapper<AsynchronousSocketChannel> futureWrapper = new FutureWrapper<>(future);
/*     */       
/* 125 */       future = futureWrapper;
/*     */     } 
/*     */     
/* 128 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final <A> void accept(A paramA, CompletionHandler<AsynchronousSocketChannel, ? super A> paramCompletionHandler) {
/* 135 */     if (paramCompletionHandler == null) {
/* 136 */       throw new NullPointerException("'handler' is null");
/*     */     }
/* 138 */     paramCompletionHandler = new CompletionHandlerWrapper<>(paramCompletionHandler);
/* 139 */     accept(paramA, paramCompletionHandler);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\AsynchronousServerSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */