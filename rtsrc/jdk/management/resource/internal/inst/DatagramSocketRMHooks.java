/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
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
/*     */ @InstrumentationTarget("java.net.DatagramSocket")
/*     */ public final class DatagramSocketRMHooks
/*     */ {
/*     */   @InstrumentationMethod
/*     */   public InetAddress getLocalAddress() {
/*  28 */     return getLocalAddress();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isBound() {
/*  33 */     return isBound();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public synchronized void bind(SocketAddress paramSocketAddress) throws SocketException {
/*  38 */     ResourceIdImpl resourceIdImpl = null;
/*  39 */     ResourceRequest resourceRequest = null;
/*  40 */     long l = 0L;
/*     */ 
/*     */     
/*  43 */     if (!isBound()) {
/*  44 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  45 */       resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  48 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  49 */         if (l < 1L) {
/*  50 */           throw new SocketException("Resource limited: too many open datagram sockets");
/*     */         }
/*  52 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  53 */         SocketException socketException = new SocketException("Resource limited: too many open datagram sockets");
/*  54 */         socketException.initCause(resourceRequestDeniedException);
/*  55 */         throw socketException;
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     boolean bool = false;
/*     */     try {
/*  61 */       bind(paramSocketAddress);
/*  62 */       bool = true;
/*     */     } finally {
/*  64 */       if (resourceRequest != null)
/*     */       {
/*  66 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   private synchronized void connectInternal(InetAddress paramInetAddress, int paramInt) throws SocketException {
/*  73 */     ResourceIdImpl resourceIdImpl = null;
/*  74 */     ResourceRequest resourceRequest = null;
/*  75 */     long l = 0L;
/*     */ 
/*     */     
/*  78 */     if (!isBound()) {
/*  79 */       resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*  80 */       resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  83 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  84 */         if (l < 1L) {
/*  85 */           throw new SocketException("Resource limited: too many open datagram sockets");
/*     */         }
/*  87 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  88 */         SocketException socketException = new SocketException("Resource limited: too many open datagram sockets");
/*  89 */         socketException.initCause(resourceRequestDeniedException);
/*  90 */         throw socketException;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     boolean bool = false;
/*     */     try {
/*  96 */       connectInternal(paramInetAddress, paramInt);
/*  97 */       bool = true;
/*     */     } finally {
/*  99 */       if (resourceRequest != null)
/*     */       {
/* 101 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public synchronized void receive(DatagramPacket paramDatagramPacket) throws IOException {
/* 108 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 109 */     ResourceRequest resourceRequest = ApproverGroup.DATAGRAM_RECEIVED_GROUP.getApprover(this);
/* 110 */     long l = 0L;
/*     */     try {
/* 112 */       l = Math.max(resourceRequest.request(1L, resourceIdImpl), 0L);
/* 113 */       if (l < 1L) {
/* 114 */         throw new IOException("Resource limited: too many received datagrams");
/*     */       }
/* 116 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 117 */       throw new IOException("Resource limited: too many received datagrams", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 120 */     int i = Math.max(paramDatagramPacket.getLength(), 0);
/* 121 */     if (i > 0) {
/* 122 */       ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_READ_GROUP.getApprover(this);
/*     */       try {
/* 124 */         l = Math.max(resourceRequest1.request(i, resourceIdImpl), 0L);
/* 125 */         if (l < i) {
/* 126 */           resourceRequest.request(-1L, resourceIdImpl);
/* 127 */           throw new IOException("Resource limited: insufficient bytes approved");
/*     */         } 
/* 129 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 130 */         resourceRequest.request(-1L, resourceIdImpl);
/* 131 */         throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */       } 
/*     */       
/* 134 */       int j = 0;
/* 135 */       byte b = 0;
/*     */       try {
/* 137 */         receive(paramDatagramPacket);
/* 138 */         j = paramDatagramPacket.getLength();
/* 139 */         b = 1;
/*     */       } finally {
/* 141 */         resourceRequest1.request(-(l - j), resourceIdImpl);
/* 142 */         resourceRequest.request(-(1 - b), resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void send(DatagramPacket paramDatagramPacket) throws IOException {
/* 150 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 151 */     ResourceRequest resourceRequest = ApproverGroup.DATAGRAM_SENT_GROUP.getApprover(this);
/* 152 */     long l = 0L;
/*     */     try {
/* 154 */       l = Math.max(resourceRequest.request(1L, resourceIdImpl), 0L);
/* 155 */       if (l < 1L) {
/* 156 */         throw new IOException("Resource limited: too many sent datagrams");
/*     */       }
/* 158 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 159 */       throw new IOException("Resource limited: too many sent datagrams", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 162 */     int i = Math.max(paramDatagramPacket.getLength(), 0);
/* 163 */     if (i > 0) {
/* 164 */       ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_WRITE_GROUP.getApprover(this);
/*     */       try {
/* 166 */         l = Math.max(resourceRequest1.request(i, resourceIdImpl), 0L);
/* 167 */         if (l < i) {
/* 168 */           resourceRequest.request(-1L, resourceIdImpl);
/* 169 */           throw new IOException("Resource limited: insufficient bytes approved");
/*     */         } 
/* 171 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 172 */         resourceRequest.request(-1L, resourceIdImpl);
/* 173 */         throw new IOException("Resource limited: too many sent datagrams", resourceRequestDeniedException);
/*     */       } 
/*     */       
/* 176 */       int j = 0;
/*     */       try {
/* 178 */         send(paramDatagramPacket);
/* 179 */         j = paramDatagramPacket.getLength();
/*     */       } finally {
/* 181 */         resourceRequest1.request(-(l - j), resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isClosed() {
/* 188 */     return isClosed();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isConnected() {
/* 193 */     return isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void close() {
/* 199 */     if (isClosed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 204 */     boolean bool = isBound();
/* 205 */     InetAddress inetAddress = getLocalAddress();
/*     */     try {
/* 207 */       close();
/*     */     } finally {
/* 209 */       if (bool) {
/* 210 */         ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(inetAddress);
/* 211 */         ResourceRequest resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/* 212 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\DatagramSocketRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */