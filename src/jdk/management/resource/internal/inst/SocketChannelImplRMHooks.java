/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ @InstrumentationTarget("sun.nio.ch.SocketChannelImpl")
/*     */ public final class SocketChannelImplRMHooks
/*     */ {
/*     */   @InstrumentationMethod
/*     */   public SocketAddress getLocalAddress() throws IOException {
/*  27 */     return getLocalAddress();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public SocketChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*  32 */     ResourceIdImpl resourceIdImpl = null;
/*  33 */     ResourceRequest resourceRequest = null;
/*  34 */     long l = 0L;
/*     */ 
/*     */     
/*  37 */     if (getLocalAddress() == null) {
/*  38 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  39 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  42 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  43 */         if (l < 1L) {
/*  44 */           throw new IOException("Resource limited: too many open socket channels");
/*     */         }
/*  46 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  47 */         throw new IOException("Resource limited: too many open socket channels", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  51 */     boolean bool = false;
/*  52 */     SocketChannel socketChannel = null;
/*     */     try {
/*  54 */       socketChannel = bind(paramSocketAddress);
/*  55 */       bool = true;
/*     */     } finally {
/*  57 */       if (resourceRequest != null)
/*     */       {
/*  59 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return socketChannel;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean connect(SocketAddress paramSocketAddress) throws IOException {
/*  68 */     ResourceIdImpl resourceIdImpl = null;
/*  69 */     ResourceRequest resourceRequest = null;
/*  70 */     long l = 0L;
/*     */ 
/*     */     
/*  73 */     if (getLocalAddress() == null) {
/*  74 */       resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*  75 */       resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  78 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  79 */         if (l < 1L) {
/*  80 */           throw new IOException("Resource limited: too many open sockets");
/*     */         }
/*  82 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  83 */         throw new IOException("Resource limited: too many open sockets", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     boolean bool = false;
/*  88 */     boolean bool1 = false;
/*     */     try {
/*  90 */       bool1 = connect(paramSocketAddress);
/*  91 */       bool = true;
/*     */     } finally {
/*  93 */       if (resourceRequest != null)
/*     */       {
/*  95 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return bool1;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 104 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 105 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
/* 106 */     long l = 0L;
/* 107 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 109 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 110 */       if (l < i) {
/* 111 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 113 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 114 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     int j = 0;
/* 119 */     int k = 0;
/*     */     try {
/* 121 */       k = read(paramByteBuffer);
/* 122 */       j = Math.max(k, 0);
/*     */     } finally {
/*     */       
/* 125 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 128 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 134 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 135 */       return read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 138 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 139 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
/* 140 */     long l1 = 0L;
/* 141 */     int i = 0;
/* 142 */     for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
/* 143 */       i += paramArrayOfByteBuffer[j].remaining();
/*     */     }
/*     */     try {
/* 146 */       l1 = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 147 */       if (l1 < i) {
/* 148 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 150 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 151 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 155 */     long l2 = 0L;
/* 156 */     long l3 = 0L;
/*     */     try {
/* 158 */       l3 = read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/* 159 */       l2 = Math.max(l3, 0L);
/*     */     } finally {
/*     */       
/* 162 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */     } 
/*     */     
/* 165 */     return l3;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/* 170 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 171 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
/* 172 */     long l = 0L;
/* 173 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 175 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 176 */       if (l < i) {
/* 177 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 179 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 180 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 184 */     int j = 0;
/*     */     try {
/* 186 */       j = write(paramByteBuffer);
/*     */     } finally {
/*     */       
/* 189 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 192 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 198 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 199 */       return write(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 202 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 203 */     ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
/* 204 */     long l1 = 0L;
/* 205 */     int i = 0;
/* 206 */     for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
/* 207 */       i += paramArrayOfByteBuffer[j].remaining();
/*     */     }
/*     */     try {
/* 210 */       l1 = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 211 */       if (l1 < i) {
/* 212 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 214 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 215 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 219 */     long l2 = 0L;
/*     */     try {
/* 221 */       l2 = write(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     } finally {
/*     */       
/* 224 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */     } 
/*     */     
/* 227 */     return l2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */