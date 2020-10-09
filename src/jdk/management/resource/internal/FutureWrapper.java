/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import jdk.management.resource.ResourceId;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
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
/*     */ public class FutureWrapper<T>
/*     */   implements Future<T>
/*     */ {
/*     */   private final Future<T> future;
/*     */   private final ResourceId id;
/*     */   private final ResourceRequest ra;
/*     */   private final long approved;
/*     */   private Object clientChannel;
/*     */   private boolean isInvoked = false;
/*     */   
/*     */   public FutureWrapper(Future<T> paramFuture, ResourceId paramResourceId, ResourceRequest paramResourceRequest, long paramLong) {
/*  34 */     this.future = paramFuture;
/*  35 */     this.id = paramResourceId;
/*  36 */     this.ra = paramResourceRequest;
/*  37 */     this.approved = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public FutureWrapper(Future<T> paramFuture) {
/*  42 */     this(paramFuture, null, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FutureWrapper(Future<T> paramFuture, Object paramObject) {
/*  48 */     this(paramFuture, null, null, 0L);
/*  49 */     this.clientChannel = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean paramBoolean) {
/*  54 */     return this.future.cancel(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  59 */     return this.future.isCancelled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  64 */     return this.future.isDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public T get() throws InterruptedException, ExecutionException {
/*  69 */     T t = this.future.get();
/*  70 */     processResult(t);
/*  71 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/*  76 */     T t = this.future.get(paramLong, paramTimeUnit);
/*  77 */     processResult(t);
/*  78 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void processResult(T paramT) {
/*  89 */     if (this.isInvoked) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     this.isInvoked = true;
/*     */     
/*  95 */     if (paramT instanceof Number) {
/*     */       
/*  97 */       int i = ((Number)paramT).intValue();
/*  98 */       if (i == -1) {
/*  99 */         this.ra.request(-this.approved, this.id);
/*     */       } else {
/* 101 */         this.ra.request(-(this.approved - i), this.id);
/*     */       } 
/* 103 */     } else if (paramT instanceof AsynchronousSocketChannel || this.clientChannel != null) {
/*     */ 
/*     */ 
/*     */       
/* 107 */       AsynchronousSocketChannel asynchronousSocketChannel = (AsynchronousSocketChannel)paramT;
/* 108 */       if (paramT != null) {
/* 109 */         asynchronousSocketChannel = (AsynchronousSocketChannel)paramT;
/*     */       } else {
/* 111 */         asynchronousSocketChannel = (AsynchronousSocketChannel)this.clientChannel;
/*     */       } 
/*     */       
/* 114 */       ResourceIdImpl resourceIdImpl = null;
/*     */       try {
/* 116 */         resourceIdImpl = ResourceIdImpl.of(asynchronousSocketChannel.getLocalAddress());
/* 117 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 120 */       ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(asynchronousSocketChannel);
/*     */       
/* 122 */       long l = 0L;
/* 123 */       ResourceRequestDeniedException resourceRequestDeniedException = null;
/*     */       try {
/* 125 */         l = resourceRequest.request(1L, resourceIdImpl);
/* 126 */         if (l < 1L) {
/* 127 */           resourceRequestDeniedException = new ResourceRequestDeniedException("Resource limited: too many open server socket channels");
/*     */         }
/* 129 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException1) {
/* 130 */         resourceRequestDeniedException = resourceRequestDeniedException1;
/*     */       } 
/*     */       
/* 133 */       if (resourceRequestDeniedException == null) {
/* 134 */         resourceRequest.request(-(l - 1L), resourceIdImpl);
/*     */       } else {
/* 136 */         resourceRequest.request(-l, resourceIdImpl);
/*     */         try {
/* 138 */           asynchronousSocketChannel.close();
/* 139 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\FutureWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */