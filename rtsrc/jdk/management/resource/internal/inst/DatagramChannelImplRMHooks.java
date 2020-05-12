/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.DatagramChannel;
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
/*     */ @InstrumentationTarget("sun.nio.ch.DatagramChannelImpl")
/*     */ public final class DatagramChannelImplRMHooks
/*     */ {
/*     */   @InstrumentationMethod
/*     */   public SocketAddress getLocalAddress() throws IOException {
/*  26 */     return getLocalAddress();
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public boolean isConnected() {
/*  31 */     return isConnected();
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public DatagramChannel bind(SocketAddress paramSocketAddress) throws IOException {
/*     */     DatagramChannel datagramChannel;
/*  36 */     ResourceIdImpl resourceIdImpl = null;
/*  37 */     ResourceRequest resourceRequest = null;
/*  38 */     long l = 0L;
/*     */ 
/*     */     
/*  41 */     if (getLocalAddress() == null) {
/*  42 */       resourceIdImpl = ResourceIdImpl.of(paramSocketAddress);
/*  43 */       resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  46 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  47 */         if (l < 1L) {
/*  48 */           throw new IOException("Resource limited: too many open datagram channels");
/*     */         }
/*  50 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  51 */         throw new IOException("Resource limited: too many open datagram channels", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     boolean bool = false;
/*     */     
/*     */     try {
/*  58 */       datagramChannel = bind(paramSocketAddress);
/*  59 */       bool = true;
/*     */     } finally {
/*  61 */       if (resourceRequest != null)
/*     */       {
/*  63 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return datagramChannel;
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public DatagramChannel connect(SocketAddress paramSocketAddress) throws IOException {
/*     */     DatagramChannel datagramChannel;
/*  72 */     ResourceIdImpl resourceIdImpl = null;
/*  73 */     ResourceRequest resourceRequest = null;
/*  74 */     long l = 0L;
/*     */ 
/*     */     
/*  77 */     if (getLocalAddress() == null) {
/*  78 */       resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*  79 */       resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/*     */       
/*     */       try {
/*  82 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  83 */         if (l < 1L) {
/*  84 */           throw new IOException("Resource limited: too many open datagram channels");
/*     */         }
/*  86 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  87 */         throw new IOException("Resource limited: too many open datagram channels", resourceRequestDeniedException);
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     boolean bool = false;
/*     */     
/*     */     try {
/*  94 */       datagramChannel = connect(paramSocketAddress);
/*  95 */       bool = true;
/*     */     } finally {
/*  97 */       if (resourceRequest != null)
/*     */       {
/*  99 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     return datagramChannel;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public SocketAddress receive(ByteBuffer paramByteBuffer) throws IOException {
/* 108 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 109 */     ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_RECEIVED_GROUP.getApprover(this);
/* 110 */     long l = 0L;
/*     */     try {
/* 112 */       l = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 113 */       if (l < 1L) {
/* 114 */         throw new IOException("Resource limited: too many received datagrams");
/*     */       }
/* 116 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 117 */       throw new IOException("Resource limited: too many received datagrams", resourceRequestDeniedException);
/*     */     } 
/* 119 */     resourceRequest1.request(-(l - 1L), resourceIdImpl);
/*     */     
/* 121 */     int i = paramByteBuffer.remaining();
/* 122 */     ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_READ_GROUP.getApprover(this);
/*     */     try {
/* 124 */       l = Math.max(resourceRequest2.request(i, resourceIdImpl), 0L);
/* 125 */       if (l < i) {
/* 126 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 127 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       } 
/* 129 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 130 */       resourceRequest1.request(-1L, resourceIdImpl);
/* 131 */       throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 134 */     int j = 0;
/* 135 */     SocketAddress socketAddress = null;
/*     */     try {
/* 137 */       int k = paramByteBuffer.position();
/* 138 */       socketAddress = receive(paramByteBuffer);
/* 139 */       j = paramByteBuffer.position() - k;
/*     */     } finally {
/* 141 */       if (socketAddress == null) {
/* 142 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       }
/* 144 */       resourceRequest2.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 147 */     return socketAddress;
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public int send(ByteBuffer paramByteBuffer, SocketAddress paramSocketAddress) throws IOException {
/*     */     int i;
/* 152 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/*     */     
/* 154 */     long l = 0L;
/*     */ 
/*     */     
/* 157 */     if (getLocalAddress() == null) {
/* 158 */       ResourceRequest resourceRequest = ApproverGroup.DATAGRAM_OPEN_GROUP.getApprover(this);
/*     */       try {
/* 160 */         l = resourceRequest.request(1L, resourceIdImpl);
/* 161 */         if (l < 1L) {
/* 162 */           throw new IOException("Resource limited: too many open datagram channels");
/*     */         }
/* 164 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 165 */         throw new IOException("Resource limited: too many open datagram channels", resourceRequestDeniedException);
/*     */       } 
/* 167 */       resourceRequest.request(-(l - 1L), resourceIdImpl);
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (isConnected()) {
/*     */ 
/*     */ 
/*     */       
/* 175 */       i = send(paramByteBuffer, paramSocketAddress);
/*     */     } else {
/* 177 */       ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_SENT_GROUP.getApprover(this);
/* 178 */       l = 0L;
/*     */       try {
/* 180 */         l = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 181 */         if (l < 1L) {
/* 182 */           throw new IOException("Resource limited: too many sent datagrams");
/*     */         }
/* 184 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 185 */         throw new IOException("Resource limited: too many sent datagrams", resourceRequestDeniedException);
/*     */       } 
/* 187 */       resourceRequest1.request(-(l - 1L), resourceIdImpl);
/*     */       
/* 189 */       int j = paramByteBuffer.remaining();
/* 190 */       ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_WRITE_GROUP.getApprover(this);
/*     */       try {
/* 192 */         l = Math.max(resourceRequest2.request(j, resourceIdImpl), 0L);
/* 193 */         if (l < j) {
/* 194 */           resourceRequest1.request(-1L, resourceIdImpl);
/* 195 */           throw new IOException("Resource limited: insufficient bytes approved");
/*     */         } 
/* 197 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 198 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 199 */         throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */       } 
/*     */       
/* 202 */       i = 0;
/*     */       try {
/* 204 */         i = send(paramByteBuffer, paramSocketAddress);
/*     */       } finally {
/* 206 */         if (i == 0) {
/* 207 */           resourceRequest1.request(-1L, resourceIdImpl);
/*     */         }
/* 209 */         resourceRequest2.request(-(l - i), resourceIdImpl);
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return i;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 218 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 219 */     ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_RECEIVED_GROUP.getApprover(this);
/* 220 */     long l = 0L;
/*     */     try {
/* 222 */       l = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 223 */       if (l < 1L) {
/* 224 */         throw new IOException("Resource limited: too many received datagrams");
/*     */       }
/* 226 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 227 */       throw new IOException("Resource limited: too many received datagrams", resourceRequestDeniedException);
/*     */     } 
/* 229 */     resourceRequest1.request(-(l - 1L), resourceIdImpl);
/*     */     
/* 231 */     ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_READ_GROUP.getApprover(this);
/* 232 */     l = 0L;
/* 233 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 235 */       l = Math.max(resourceRequest2.request(i, resourceIdImpl), 0L);
/* 236 */       if (l < i) {
/* 237 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 238 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       } 
/* 240 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 241 */       resourceRequest1.request(-1L, resourceIdImpl);
/* 242 */       throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     int j = 0;
/* 247 */     int k = 0;
/*     */     try {
/* 249 */       k = read(paramByteBuffer);
/* 250 */       j = Math.max(k, 0);
/*     */     } finally {
/*     */       
/* 253 */       resourceRequest2.request(-(l - j), resourceIdImpl);
/* 254 */       if (j == 0) {
/* 255 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 259 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 265 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 266 */       return read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 269 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 270 */     ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_RECEIVED_GROUP.getApprover(this);
/* 271 */     long l1 = 0L;
/*     */     try {
/* 273 */       l1 = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 274 */       if (l1 < 1L) {
/* 275 */         throw new IOException("Resource limited: too many received datagrams");
/*     */       }
/* 277 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 278 */       throw new IOException("Resource limited: too many received datagrams", resourceRequestDeniedException);
/*     */     } 
/* 280 */     resourceRequest1.request(-(l1 - 1L), resourceIdImpl);
/*     */     
/* 282 */     ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_READ_GROUP.getApprover(this);
/* 283 */     l1 = 0L;
/* 284 */     int i = 0;
/* 285 */     for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
/* 286 */       i += paramArrayOfByteBuffer[j].remaining();
/*     */     }
/*     */     try {
/* 289 */       l1 = Math.max(resourceRequest2.request(i, resourceIdImpl), 0L);
/* 290 */       if (l1 < i) {
/* 291 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 292 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       } 
/* 294 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 295 */       resourceRequest1.request(-1L, resourceIdImpl);
/* 296 */       throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 300 */     long l2 = 0L;
/* 301 */     long l3 = 0L;
/*     */     try {
/* 303 */       l3 = read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/* 304 */       l2 = Math.max(l3, 0L);
/*     */     } finally {
/*     */       
/* 307 */       resourceRequest2.request(-(l1 - l2), resourceIdImpl);
/* 308 */       if (l2 == 0L) {
/* 309 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     return l3;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/* 318 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 319 */     ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_SENT_GROUP.getApprover(this);
/* 320 */     long l = 0L;
/*     */     try {
/* 322 */       l = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 323 */       if (l < 1L) {
/* 324 */         throw new IOException("Resource limited: too many sent datagrams");
/*     */       }
/* 326 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 327 */       throw new IOException("Resource limited: too many sent datagrams", resourceRequestDeniedException);
/*     */     } 
/* 329 */     resourceRequest1.request(-(l - 1L), resourceIdImpl);
/*     */     
/* 331 */     ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_WRITE_GROUP.getApprover(this);
/* 332 */     l = 0L;
/* 333 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 335 */       l = Math.max(resourceRequest2.request(i, resourceIdImpl), 0L);
/* 336 */       if (l < i) {
/* 337 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 338 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       } 
/* 340 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 341 */       resourceRequest1.request(-1L, resourceIdImpl);
/* 342 */       throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 346 */     int j = 0;
/*     */     try {
/* 348 */       j = write(paramByteBuffer);
/*     */     } finally {
/*     */       
/* 351 */       resourceRequest2.request(-(l - j), resourceIdImpl);
/* 352 */       if (j == 0) {
/* 353 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 357 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 363 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 364 */       return write(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 367 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(getLocalAddress());
/* 368 */     ResourceRequest resourceRequest1 = ApproverGroup.DATAGRAM_SENT_GROUP.getApprover(this);
/* 369 */     long l1 = 0L;
/*     */     try {
/* 371 */       l1 = Math.max(resourceRequest1.request(1L, resourceIdImpl), 0L);
/* 372 */       if (l1 < 1L) {
/* 373 */         throw new IOException("Resource limited: too many sent datagrams");
/*     */       }
/* 375 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 376 */       throw new IOException("Resource limited: too many sent datagrams", resourceRequestDeniedException);
/*     */     } 
/* 378 */     resourceRequest1.request(-(l1 - 1L), resourceIdImpl);
/*     */     
/* 380 */     ResourceRequest resourceRequest2 = ApproverGroup.DATAGRAM_WRITE_GROUP.getApprover(this);
/* 381 */     l1 = 0L;
/* 382 */     int i = 0;
/* 383 */     for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
/* 384 */       i += paramArrayOfByteBuffer[j].remaining();
/*     */     }
/*     */     try {
/* 387 */       l1 = Math.max(resourceRequest2.request(i, resourceIdImpl), 0L);
/* 388 */       if (l1 < i) {
/* 389 */         resourceRequest1.request(-1L, resourceIdImpl);
/* 390 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       } 
/* 392 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 393 */       resourceRequest1.request(-1L, resourceIdImpl);
/* 394 */       throw new IOException("Resource limited: insufficient bytes approved", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 398 */     long l2 = 0L;
/*     */     try {
/* 400 */       l2 = write(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     } finally {
/*     */       
/* 403 */       resourceRequest2.request(-(l1 - l2), resourceIdImpl);
/* 404 */       if (l2 == 0L) {
/* 405 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/* 409 */     return l2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\DatagramChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */