/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import jdk.internal.instrumentation.InstrumentationMethod;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
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
/*     */ 
/*     */ @InstrumentationTarget("java.io.RandomAccessFile")
/*     */ public final class RandomAccessFileRMHooks
/*     */ {
/*     */   private FileDescriptor fd;
/*  24 */   private final String path = null;
/*  25 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean closed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   private void open(String paramString, int paramInt) throws FileNotFoundException {
/*  36 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*  37 */     ResourceRequest resourceRequest1 = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/*  38 */     long l1 = 0L;
/*  39 */     long l2 = 0L;
/*     */     try {
/*  41 */       l2 = resourceRequest1.request(1L, resourceIdImpl);
/*  42 */       if (l2 < 1L) {
/*  43 */         throw new FileNotFoundException(paramString + ": resource limited: too many open files");
/*     */       }
/*  45 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  46 */       FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open files");
/*  47 */       fileNotFoundException.initCause(resourceRequestDeniedException);
/*  48 */       throw fileNotFoundException;
/*     */     } 
/*     */     
/*  51 */     ResourceRequest resourceRequest2 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/*  52 */     boolean bool = false;
/*     */     try {
/*     */       try {
/*  55 */         l1 = resourceRequest2.request(1L, resourceIdImpl);
/*  56 */         if (l1 < 1L) {
/*  57 */           throw new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*     */         }
/*  59 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  60 */         FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*  61 */         fileNotFoundException.initCause(resourceRequestDeniedException);
/*  62 */         throw fileNotFoundException;
/*     */       } 
/*     */       
/*  65 */       open(paramString, paramInt);
/*  66 */       bool = true;
/*     */     } finally {
/*     */       
/*  69 */       resourceRequest2.request(-(l1 - bool), resourceIdImpl);
/*  70 */       resourceRequest1.request(-(l2 - bool), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read() throws IOException {
/*  76 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*  77 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/*  78 */     long l = 0L;
/*     */     try {
/*  80 */       l = resourceRequest.request(1L, resourceIdImpl);
/*  81 */       if (l < 1L) {
/*  82 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/*  84 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  85 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/*  89 */     int i = -1;
/*     */     try {
/*  91 */       i = read();
/*     */     } finally {
/*     */       
/*  94 */       resourceRequest.request(-(l - ((i == -1) ? 0L : 1L)), resourceIdImpl);
/*     */     } 
/*     */     
/*  97 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(byte[] paramArrayOfbyte) throws IOException {
/*     */     int k;
/* 104 */     if (paramArrayOfbyte == null) {
/* 105 */       return read(paramArrayOfbyte);
/*     */     }
/*     */     
/* 108 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 109 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 110 */     int i = paramArrayOfbyte.length;
/* 111 */     long l = 0L;
/*     */     try {
/* 113 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 114 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 115 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 119 */     int j = 0;
/*     */     
/*     */     try {
/* 122 */       if (l < i) {
/*     */ 
/*     */         
/* 125 */         resourceRequest.request(-l, resourceIdImpl);
/* 126 */         k = read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 127 */         j = Math.max(k, 0);
/*     */       } else {
/* 129 */         k = read(paramArrayOfbyte);
/* 130 */         j = Math.max(k, 0);
/*     */       } 
/*     */     } finally {
/*     */       
/* 134 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 137 */     return k;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 144 */     if (paramInt2 < 0) {
/* 145 */       return read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 148 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 149 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 150 */     long l = 0L;
/*     */     try {
/* 152 */       l = Math.max(resourceRequest.request(paramInt2, resourceIdImpl), 0L);
/* 153 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 154 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     paramInt2 = Math.min(paramInt2, (int)l);
/*     */ 
/*     */     
/* 161 */     int i = 0;
/* 162 */     int j = 0;
/*     */     try {
/* 164 */       j = read(paramArrayOfbyte, paramInt1, paramInt2);
/* 165 */       i = Math.max(j, 0);
/*     */     } finally {
/*     */       
/* 168 */       resourceRequest.request(-(l - i), resourceIdImpl);
/*     */     } 
/*     */     
/* 171 */     return j;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void write(int paramInt) throws IOException {
/* 176 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 177 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 178 */     long l = 0L;
/*     */     try {
/* 180 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 181 */       if (l < 1L) {
/* 182 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 184 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 185 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 188 */     boolean bool = false;
/*     */     try {
/* 190 */       write(paramInt);
/* 191 */       bool = true;
/*     */     } finally {
/*     */       
/* 194 */       resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void write(byte[] paramArrayOfbyte) throws IOException {
/* 200 */     if (paramArrayOfbyte == null) {
/* 201 */       write(paramArrayOfbyte);
/*     */       
/*     */       return;
/*     */     } 
/* 205 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 206 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 207 */     int i = paramArrayOfbyte.length;
/* 208 */     long l = 0L;
/*     */     try {
/* 210 */       l = resourceRequest.request(i, resourceIdImpl);
/* 211 */       if (l < i) {
/* 212 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 214 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 215 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 218 */     int j = 0;
/*     */     try {
/* 220 */       write(paramArrayOfbyte);
/* 221 */       j = i;
/*     */     } finally {
/*     */       
/* 224 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 230 */     if (paramInt2 < 0) {
/* 231 */       write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */       
/*     */       return;
/*     */     } 
/* 235 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 236 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 237 */     long l = 0L;
/*     */     try {
/* 239 */       l = resourceRequest.request(paramInt2, resourceIdImpl);
/* 240 */       if (l < paramInt2) {
/* 241 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 243 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 244 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 247 */     int i = 0;
/*     */     try {
/* 249 */       write(paramArrayOfbyte, paramInt1, paramInt2);
/* 250 */       i = paramInt2;
/*     */     } finally {
/*     */       
/* 253 */       resourceRequest.request(-(l - i), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final void writeBytes(String paramString) throws IOException {
/* 259 */     if (paramString == null) {
/* 260 */       writeBytes(paramString);
/*     */       
/*     */       return;
/*     */     } 
/* 264 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 265 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 266 */     int i = paramString.length();
/* 267 */     long l = 0L;
/*     */     try {
/* 269 */       l = resourceRequest.request(i, resourceIdImpl);
/* 270 */       if (l < i) {
/* 271 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 273 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 274 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 277 */     int j = 0;
/*     */     try {
/* 279 */       writeBytes(paramString);
/* 280 */       j = i;
/*     */     } finally {
/*     */       
/* 283 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final void writeChars(String paramString) throws IOException {
/* 289 */     if (paramString == null) {
/* 290 */       writeChars(paramString);
/*     */       
/*     */       return;
/*     */     } 
/* 294 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/* 295 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 296 */     int i = 2 * paramString.length();
/* 297 */     long l = 0L;
/*     */     try {
/* 299 */       l = resourceRequest.request(i, resourceIdImpl);
/* 300 */       if (l < i) {
/* 301 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 303 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 304 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 307 */     int j = 0;
/*     */     try {
/* 309 */       writeChars(paramString);
/* 310 */       j = i;
/*     */     } finally {
/*     */       
/* 313 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void close() throws IOException {
/*     */     long l;
/* 326 */     synchronized (this.closeLock) {
/* 327 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 334 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     try {
/* 336 */       l = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 337 */       if (l == -1L) {
/* 338 */         l = javaIOFileDescriptorAccess.get(this.fd);
/*     */       }
/* 340 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 341 */       l = javaIOFileDescriptorAccess.get(this.fd);
/*     */     } 
/*     */     
/*     */     try {
/* 345 */       close();
/*     */     } finally {
/*     */       long l1;
/*     */       try {
/* 349 */         l1 = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 350 */         if (l1 == -1L) {
/* 351 */           l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */         }
/* 353 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 354 */         l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */       } 
/*     */       
/* 357 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */ 
/*     */       
/* 360 */       if (l1 != l) {
/* 361 */         ResourceRequest resourceRequest1 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 362 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       } 
/*     */       
/* 365 */       ResourceRequest resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 366 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\RandomAccessFileRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */