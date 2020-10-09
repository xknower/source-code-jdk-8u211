/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
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
/*     */ @InstrumentationTarget("sun.nio.ch.ServerSocketChannelImpl")
/*     */ public final class ServerSocketChannelImplRMHooks
/*     */ {
/*     */   private static NativeDispatcher nd;
/*     */   
/*     */   abstract class NativeDispatcher
/*     */   {
/*     */     abstract void close(FileDescriptor param1FileDescriptor) throws IOException;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public SocketAddress getLocalAddress() throws IOException {
/*  35 */     return getLocalAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public SocketChannel accept() throws IOException {
/*  41 */     long l1 = 0L;
/*  42 */     long l2 = 0L;
/*  43 */     SocketChannel socketChannel = null;
/*  44 */     ResourceIdImpl resourceIdImpl = null;
/*  45 */     ResourceRequest resourceRequest = null;
/*     */     try {
/*  47 */       socketChannel = accept();
/*  48 */       if (socketChannel != null) {
/*  49 */         resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(socketChannel);
/*  50 */         resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*     */         try {
/*  52 */           l1 = resourceRequest.request(1L, resourceIdImpl);
/*  53 */           if (l1 < 1L) {
/*     */             try {
/*  55 */               socketChannel.close();
/*  56 */             } catch (IOException iOException) {}
/*     */ 
/*     */             
/*  59 */             throw new IOException("Resource limited: too many open socket channels");
/*     */           } 
/*  61 */         } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*     */           try {
/*  63 */             socketChannel.close();
/*  64 */           } catch (IOException iOException) {}
/*     */ 
/*     */           
/*  67 */           throw new IOException("Resource limited: too many open socket channels", resourceRequestDeniedException);
/*     */         } 
/*  69 */         l2 = 1L;
/*     */       } 
/*     */     } finally {
/*  72 */       if (resourceRequest != null)
/*     */       {
/*  74 */         resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */       }
/*     */     } 
/*  77 */     return socketChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {}
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   private int accept(FileDescriptor paramFileDescriptor1, FileDescriptor paramFileDescriptor2, InetSocketAddress[] paramArrayOfInetSocketAddress) throws IOException {
/*  87 */     int i = accept(paramFileDescriptor1, paramFileDescriptor2, paramArrayOfInetSocketAddress);
/*     */     
/*  89 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(paramFileDescriptor2);
/*     */     
/*  91 */     if (resourceIdImpl != null) {
/*  92 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor2);
/*     */       
/*  94 */       long l1 = 0L;
/*  95 */       long l2 = 0L;
/*     */       try {
/*     */         try {
/*  98 */           l1 = resourceRequest.request(1L, resourceIdImpl);
/*  99 */           if (l1 < 1L) {
/* 100 */             throw new IOException("Resource limited: too many open file descriptors");
/*     */           }
/* 102 */         } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 103 */           throw new IOException("Resource limited: too many open file descriptors", resourceRequestDeniedException);
/*     */         } 
/* 105 */         l2 = 1L;
/*     */       } finally {
/* 107 */         if (l2 == 0L) {
/*     */           try {
/* 109 */             nd.close(paramFileDescriptor2);
/* 110 */           } catch (IOException iOException) {}
/*     */         }
/*     */         else {
/*     */           
/* 114 */           resourceRequest.request(-(l1 - 1L), resourceIdImpl);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return i;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public ServerSocketChannel bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/* 124 */     ResourceIdImpl resourceIdImpl = null;
/* 125 */     ResourceRequest resourceRequest = null;
/* 126 */     long l = 0L;
/*     */ 
/*     */     
/* 129 */     if (getLocalAddress() == null) {
/* 130 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/* 131 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/* 133 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 134 */       if (l < 1L) {
/* 135 */         throw new ResourceRequestDeniedException("Resource limited: too many open socket channels");
/*     */       }
/*     */     } 
/*     */     
/* 139 */     boolean bool = false;
/* 140 */     ServerSocketChannel serverSocketChannel = null;
/*     */     try {
/* 142 */       serverSocketChannel = bind(paramSocketAddress, paramInt);
/* 143 */       bool = true;
/*     */     } finally {
/* 145 */       if (resourceRequest != null)
/*     */       {
/* 147 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return serverSocketChannel;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\ServerSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */