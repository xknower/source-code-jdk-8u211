/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ @InstrumentationTarget("sun.nio.ch.FileChannelImpl")
/*     */ public final class FileChannelImplRMHooks
/*     */ {
/*  25 */   private final FileDescriptor fd = null;
/*  26 */   private String path = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public static FileChannel open(FileDescriptor paramFileDescriptor, String paramString, boolean paramBoolean1, boolean paramBoolean2, Object paramObject) {
/*  32 */     long l = 0L;
/*  33 */     boolean bool = false;
/*  34 */     FileChannel fileChannel = null;
/*  35 */     ResourceIdImpl resourceIdImpl = null;
/*  36 */     ResourceRequest resourceRequest = null;
/*     */     try {
/*  38 */       fileChannel = open(paramFileDescriptor, paramString, paramBoolean1, paramBoolean2, paramObject);
/*  39 */       resourceIdImpl = ResourceIdImpl.of(paramString);
/*  40 */       resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(fileChannel);
/*  41 */       boolean bool1 = false;
/*     */       try {
/*  43 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  44 */         if (l < 1L) {
/*  45 */           throw new ResourceRequestDeniedException(paramString + ": resource limited: too many open files");
/*     */         }
/*  47 */         bool1 = true;
/*     */       } finally {
/*  49 */         if (!bool1) {
/*     */           try {
/*  51 */             fileChannel.close();
/*  52 */           } catch (IOException iOException) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  57 */       bool = true;
/*     */     } finally {
/*  59 */       if (resourceRequest != null)
/*     */       {
/*  61 */         resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */       }
/*     */     } 
/*     */     
/*  65 */     return fileChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public static FileChannel open(FileDescriptor paramFileDescriptor, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Object paramObject) {
/*  72 */     FileChannel fileChannel = open(paramFileDescriptor, paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramObject);
/*     */     
/*  74 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(paramString);
/*  75 */     ResourceRequest resourceRequest = null;
/*  76 */     long l = 0L;
/*  77 */     boolean bool = false;
/*     */     
/*  79 */     if (paramObject == null) {
/*  80 */       resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor);
/*     */       
/*     */       try {
/*  83 */         l = resourceRequest.request(1L, resourceIdImpl);
/*  84 */         if (l < 1L) {
/*  85 */           throw new ResourceRequestDeniedException(paramString + ": resource limited: too many open file descriptors");
/*     */         }
/*  87 */         bool = true;
/*     */       } finally {
/*  89 */         if (!bool) {
/*  90 */           resourceRequest.request(-1L, resourceIdImpl);
/*     */           try {
/*  92 */             fileChannel.close();
/*  93 */           } catch (IOException iOException) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 100 */     bool = false;
/* 101 */     resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(fileChannel);
/*     */     try {
/* 103 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 104 */       if (l < 1L) {
/*     */         try {
/* 106 */           fileChannel.close();
/* 107 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/* 110 */         throw new ResourceRequestDeniedException(paramString + ": resource limited: too many open files");
/*     */       } 
/* 112 */       bool = true;
/*     */     } finally {
/* 114 */       if (!bool) {
/* 115 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */         try {
/* 117 */           fileChannel.close();
/* 118 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     return fileChannel;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 129 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 130 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 131 */     long l = 0L;
/* 132 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 134 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 135 */       if (l < i) {
/* 136 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 138 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 139 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     int j = 0;
/* 144 */     int k = 0;
/*     */     try {
/* 146 */       k = read(paramByteBuffer);
/* 147 */       j = Math.max(k, 0);
/*     */     } finally {
/*     */       
/* 150 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 153 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long read(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 159 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 160 */       return read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 163 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 164 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 165 */     long l1 = 0L;
/* 166 */     int i = 0;
/* 167 */     int j = paramInt1 + paramInt2;
/* 168 */     for (int k = paramInt1; k < j; k++) {
/* 169 */       i += paramArrayOfByteBuffer[k].remaining();
/*     */     }
/*     */     try {
/* 172 */       l1 = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 173 */       if (l1 < i) {
/* 174 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 176 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 177 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 181 */     long l2 = 0L;
/* 182 */     long l3 = 0L;
/*     */     try {
/* 184 */       l3 = read(paramArrayOfByteBuffer, paramInt1, paramInt2);
/* 185 */       l2 = Math.max(l3, 0L);
/*     */     } finally {
/*     */       
/* 188 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */     } 
/*     */     
/* 191 */     return l3;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/* 196 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 197 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 198 */     long l = 0L;
/* 199 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 201 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 202 */       if (l < i) {
/* 203 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 205 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 206 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 210 */     int j = 0;
/* 211 */     int k = 0;
/*     */     try {
/* 213 */       k = read(paramByteBuffer, paramLong);
/* 214 */       j = Math.max(k, 0);
/*     */     } finally {
/*     */       
/* 217 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 220 */     return k;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int write(ByteBuffer paramByteBuffer) throws IOException {
/* 225 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 226 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 227 */     long l = 0L;
/* 228 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 230 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 231 */       if (l < i) {
/* 232 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 234 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 235 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 239 */     int j = 0;
/*     */     try {
/* 241 */       j = write(paramByteBuffer);
/*     */     } finally {
/*     */       
/* 244 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 247 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public long write(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws IOException {
/* 253 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfByteBuffer.length - paramInt2) {
/* 254 */       return write(paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 257 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 258 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 259 */     long l1 = 0L;
/* 260 */     int i = 0;
/* 261 */     int j = paramInt1 + paramInt2;
/* 262 */     for (int k = paramInt1; k < j; k++) {
/* 263 */       i += paramArrayOfByteBuffer[k].remaining();
/*     */     }
/*     */     try {
/* 266 */       l1 = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 267 */       if (l1 < i) {
/* 268 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 270 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 271 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 275 */     long l2 = 0L;
/*     */     try {
/* 277 */       l2 = Math.max(write(paramArrayOfByteBuffer, paramInt1, paramInt2), 0L);
/*     */     } finally {
/*     */       
/* 280 */       resourceRequest.request(-(l1 - l2), resourceIdImpl);
/*     */     } 
/*     */     
/* 283 */     return l2;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int write(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/* 288 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 289 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 290 */     long l = 0L;
/* 291 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 293 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 294 */       if (l < i) {
/* 295 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 297 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 298 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 302 */     int j = 0;
/*     */     try {
/* 304 */       j = write(paramByteBuffer, paramLong);
/*     */     } finally {
/*     */       
/* 307 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 310 */     return j;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   protected void implCloseChannel() throws IOException {
/*     */     try {
/* 316 */       implCloseChannel();
/*     */     } finally {
/* 318 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 319 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 320 */       resourceRequest.request(-1L, resourceIdImpl);
/* 321 */       resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 322 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\FileChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */