/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.AsynchronousSocketChannel;
/*     */ import java.nio.channels.CompletionHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompletionHandlerWrapper<V, A>
/*     */   implements CompletionHandler<V, A>
/*     */ {
/*     */   private final CompletionHandler<V, ? super A> handler;
/*     */   private final ResourceId id;
/*     */   private final ResourceRequest ra;
/*     */   private final long approved;
/*     */   private Object clientChannel;
/*     */   
/*     */   public CompletionHandlerWrapper(CompletionHandler<V, ? super A> paramCompletionHandler, ResourceId paramResourceId, ResourceRequest paramResourceRequest, long paramLong) {
/*  36 */     this.handler = paramCompletionHandler;
/*  37 */     this.id = paramResourceId;
/*  38 */     this.ra = paramResourceRequest;
/*  39 */     this.approved = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletionHandlerWrapper(CompletionHandler<V, ? super A> paramCompletionHandler) {
/*  44 */     this(paramCompletionHandler, null, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletionHandlerWrapper(CompletionHandler<V, ? super A> paramCompletionHandler, Object paramObject) {
/*  50 */     this(paramCompletionHandler, null, null, 0L);
/*  51 */     this.clientChannel = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public void completed(V paramV, A paramA) {
/*  56 */     if (paramV instanceof Number) {
/*     */       
/*  58 */       int i = ((Number)paramV).intValue();
/*  59 */       if (i == -1) {
/*  60 */         this.ra.request(-this.approved, this.id);
/*     */       } else {
/*  62 */         this.ra.request(-(this.approved - i), this.id);
/*     */       } 
/*  64 */     } else if (paramV instanceof AsynchronousSocketChannel || this.clientChannel != null) {
/*     */       AsynchronousSocketChannel asynchronousSocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       if (paramV != null) {
/*  73 */         asynchronousSocketChannel = (AsynchronousSocketChannel)paramV;
/*     */       } else {
/*  75 */         asynchronousSocketChannel = (AsynchronousSocketChannel)this.clientChannel;
/*     */       } 
/*     */       
/*  78 */       ResourceIdImpl resourceIdImpl = null;
/*     */       try {
/*  80 */         resourceIdImpl = ResourceIdImpl.of(asynchronousSocketChannel.getLocalAddress());
/*  81 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/*  85 */       ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(asynchronousSocketChannel);
/*     */       
/*  87 */       long l = 0L;
/*  88 */       ResourceRequestDeniedException resourceRequestDeniedException = null;
/*     */       try {
/*  90 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  91 */         if (l < 1L) {
/*  92 */           resourceRequestDeniedException = new ResourceRequestDeniedException("Resource limited: too many open server socket channels");
/*     */         }
/*  94 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException1) {
/*  95 */         resourceRequestDeniedException = resourceRequestDeniedException1;
/*     */       } 
/*     */       
/*  98 */       if (resourceRequestDeniedException == null) {
/*  99 */         resourceRequest.request(-(l - 1L), resourceIdImpl);
/*     */       } else {
/* 101 */         resourceRequest.request(-l, resourceIdImpl);
/*     */         try {
/* 103 */           asynchronousSocketChannel.close();
/* 104 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 107 */         if (this.handler != null) {
/* 108 */           this.handler.failed(resourceRequestDeniedException, paramA);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 114 */     if (this.handler != null) {
/* 115 */       this.handler.completed(paramV, paramA);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void failed(Throwable paramThrowable, A paramA) {
/* 125 */     if (this.ra != null && this.id != null) {
/* 126 */       this.ra.request(-this.approved, this.id);
/*     */     }
/*     */     
/* 129 */     if (this.handler != null)
/* 130 */       this.handler.failed(paramThrowable, paramA); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\CompletionHandlerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */