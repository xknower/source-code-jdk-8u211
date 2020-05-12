/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketOptions;
/*     */ import jdk.internal.instrumentation.InstrumentationMethod;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
/*     */ import jdk.internal.instrumentation.TypeMapping;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
/*     */ import jdk.management.resource.internal.ApproverGroup;
/*     */ import jdk.management.resource.internal.ResourceIdImpl;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @InstrumentationTarget("java.net.Socket")
/*     */ @TypeMapping(from = "jdk.management.resource.internal.inst.SocketRMHooks$SocketImpl", to = "java.net.SocketImpl")
/*     */ public final class SocketRMHooks
/*     */ {
/*     */   private boolean created = false;
/*     */   SocketImpl impl;
/*     */   
/*     */   abstract class SocketImpl
/*     */     implements SocketOptions
/*     */   {
/*     */     protected FileDescriptor fd;
/*     */     
/*     */     protected FileDescriptor getFileDescriptor() {
/*  35 */       return this.fd;
/*     */     }
/*     */   }
/*     */   
/*     */   public InetAddress getLocalAddress() {
/*  40 */     return getLocalAddress();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/*  45 */     ResourceIdImpl resourceIdImpl = null;
/*  46 */     ResourceRequest resourceRequest = null;
/*  47 */     long l = 0L;
/*     */ 
/*     */     
/*  50 */     if (!isBound()) {
/*  51 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  52 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  55 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  56 */         if (l < 1L) {
/*  57 */           throw new IOException("Resource limited: too many open sockets");
/*     */         }
/*  59 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  60 */         throw new IOException("Resource limited: too many open sockets", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     boolean bool = false;
/*     */     try {
/*  66 */       bind(paramSocketAddress);
/*  67 */       bool = true;
/*     */     } finally {
/*  69 */       if (resourceRequest != null)
/*     */       {
/*  71 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isBound() {
/*  78 */     return isBound();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  83 */     ResourceIdImpl resourceIdImpl = null;
/*  84 */     ResourceRequest resourceRequest = null;
/*  85 */     long l = 0L;
/*     */ 
/*     */     
/*  88 */     if (!isBound()) {
/*  89 */       resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*  90 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  93 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  94 */         if (l < 1L) {
/*  95 */           throw new IOException("Resource limited: too many open sockets");
/*     */         }
/*  97 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  98 */         throw new IOException("Resource limited: too many open sockets", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     boolean bool = false;
/*     */     try {
/* 104 */       connect(paramSocketAddress, paramInt);
/* 105 */       bool = true;
/*     */     } finally {
/* 107 */       if (resourceRequest != null)
/*     */       {
/* 109 */         resourceRequest.request(-(l - bool), resourceIdImpl); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   final void postAccept() {
/*     */     long l1;
/* 116 */     postAccept();
/*     */     
/* 118 */     FileDescriptor fileDescriptor = this.impl.getFileDescriptor();
/*     */ 
/*     */     
/* 121 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     
/*     */     try {
/* 124 */       l1 = javaIOFileDescriptorAccess.getHandle(fileDescriptor);
/* 125 */       if (l1 == -1L) {
/* 126 */         l1 = javaIOFileDescriptorAccess.get(fileDescriptor);
/*     */       }
/* 128 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 129 */       l1 = javaIOFileDescriptorAccess.get(fileDescriptor);
/*     */     } 
/*     */     
/* 132 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l1));
/* 133 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(fileDescriptor);
/*     */     
/* 135 */     long l2 = 0L;
/* 136 */     boolean bool = false;
/*     */     try {
/* 138 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/* 139 */       if (l2 < 1L) {
/* 140 */         throw new ResourceRequestDeniedException("Resource limited: too many open file descriptors");
/*     */       }
/* 142 */       bool = true;
/*     */     } finally {
/* 144 */       if (!bool) {
/*     */         
/*     */         try {
/* 147 */           close();
/* 148 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 151 */         resourceRequest.request(-Math.max(0L, l2 - 1L), resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isClosed() {
/* 158 */     return isClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public synchronized void close() throws IOException {
/* 164 */     if (isClosed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 169 */     boolean bool = isBound();
/* 170 */     InetAddress inetAddress = getLocalAddress();
/*     */     
/*     */     try {
/* 173 */       close();
/*     */     } finally {
/* 175 */       if (bool) {
/* 176 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(inetAddress);
/* 177 */         ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/* 178 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SocketRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */