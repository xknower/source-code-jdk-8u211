/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import jdk.internal.instrumentation.InstrumentationMethod;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
/*     */ import jdk.management.resource.internal.ApproverGroup;
/*     */ import jdk.management.resource.internal.ResourceIdImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @InstrumentationTarget("java.net.ServerSocket")
/*     */ final class ServerSocketRMHooks
/*     */ {
/*     */   private Object closeLock;
/*     */   
/*     */   @InstrumentationMethod
/*     */   public Socket accept() throws IOException {
/*  28 */     long l1 = 0L;
/*  29 */     long l2 = 0L;
/*  30 */     Socket socket = null;
/*  31 */     ResourceIdImpl resourceIdImpl = null;
/*  32 */     ResourceRequest resourceRequest = null;
/*     */     try {
/*  34 */       socket = accept();
/*  35 */       l2 = 1L;
/*  36 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(socket);
/*  37 */       resourceIdImpl = ResourceIdImpl.of(socket.getLocalAddress());
/*     */       try {
/*  39 */         l1 = resourceRequest.request(1L, resourceIdImpl);
/*  40 */         if (l1 < 1L) {
/*     */           try {
/*  42 */             socket.close();
/*  43 */           } catch (IOException iOException) {}
/*     */ 
/*     */           
/*  46 */           throw new IOException("Resource limited: too many open sockets");
/*     */         } 
/*  48 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*     */         try {
/*  50 */           socket.close();
/*  51 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/*  54 */         throw new IOException("Resource limited: too many open sockets", resourceRequestDeniedException);
/*     */       } 
/*  56 */       l2 = 1L;
/*     */     } finally {
/*  58 */       if (resourceRequest != null)
/*     */       {
/*  60 */         resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  64 */     return socket;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public InetAddress getInetAddress() {
/*  69 */     return getInetAddress();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  74 */     ResourceIdImpl resourceIdImpl = null;
/*  75 */     ResourceRequest resourceRequest = null;
/*  76 */     long l = 0L;
/*     */ 
/*     */     
/*  79 */     if (!isBound()) {
/*  80 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  81 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*  83 */       l = resourceRequest.request(1L, resourceIdImpl);
/*  84 */       if (l < 1L) {
/*  85 */         throw new ResourceRequestDeniedException("Resource limited: too many open sockets");
/*     */       }
/*     */     } 
/*     */     
/*  89 */     boolean bool = false;
/*     */     try {
/*  91 */       bind(paramSocketAddress, paramInt);
/*  92 */       bool = true;
/*     */     } finally {
/*  94 */       if (resourceRequest != null)
/*     */       {
/*  96 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isBound() {
/* 103 */     return isBound();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isClosed() {
/* 108 */     return isClosed();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void close() throws IOException {
/* 113 */     synchronized (this.closeLock) {
/* 114 */       if (isClosed()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     boolean bool = isBound();
/* 120 */     InetAddress inetAddress = getInetAddress();
/*     */     try {
/* 122 */       close();
/*     */     } finally {
/* 124 */       if (bool) {
/* 125 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(inetAddress);
/* 126 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 127 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\ServerSocketRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */