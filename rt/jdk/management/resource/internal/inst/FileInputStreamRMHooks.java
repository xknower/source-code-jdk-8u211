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
/*     */ 
/*     */ @InstrumentationTarget("java.io.FileInputStream")
/*     */ public final class FileInputStreamRMHooks
/*     */ {
/*  24 */   private final FileDescriptor fd = null;
/*  25 */   private String path = null;
/*  26 */   private final Object closeLock = new Object();
/*     */   private volatile boolean closed = false;
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final FileDescriptor getFD() throws IOException {
/*  31 */     return getFD();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   private void open(String paramString) throws FileNotFoundException {
/*  42 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*  43 */     ResourceRequest resourceRequest1 = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/*  44 */     long l1 = 0L;
/*     */     try {
/*  46 */       l1 = resourceRequest1.request(1L, resourceIdImpl);
/*  47 */       if (l1 < 1L) {
/*  48 */         throw new FileNotFoundException(paramString + ": resource limited: too many open files");
/*     */       }
/*  50 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  51 */       FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open files");
/*  52 */       fileNotFoundException.initCause(resourceRequestDeniedException);
/*  53 */       throw fileNotFoundException;
/*     */     } 
/*     */     
/*  56 */     ResourceRequest resourceRequest2 = null;
/*  57 */     long l2 = 0L;
/*  58 */     boolean bool = false;
/*     */     try {
/*  60 */       FileDescriptor fileDescriptor = null;
/*     */       try {
/*  62 */         fileDescriptor = getFD();
/*  63 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/*  67 */       resourceRequest2 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(fileDescriptor);
/*     */       try {
/*  69 */         l2 = resourceRequest2.request(1L, resourceIdImpl);
/*  70 */         if (l2 < 1L) {
/*  71 */           throw new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*     */         }
/*  73 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  74 */         FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*  75 */         fileNotFoundException.initCause(resourceRequestDeniedException);
/*  76 */         throw fileNotFoundException;
/*     */       } 
/*     */       
/*  79 */       open(paramString);
/*  80 */       bool = true;
/*     */     } finally {
/*     */       
/*  83 */       resourceRequest2.request(-(l2 - bool), resourceIdImpl);
/*  84 */       resourceRequest1.request(-(l1 - bool), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public int read() throws IOException {
/*     */     ResourceRequest resourceRequest;
/*  90 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/*  92 */     if (getFD() == FileDescriptor.in) {
/*  93 */       resourceRequest = ApproverGroup.STDIN_READ_GROUP.getApprover(this);
/*     */     } else {
/*  95 */       resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/*     */     } 
/*  97 */     long l = 0L;
/*     */     try {
/*  99 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 100 */       if (l < 1L) {
/* 101 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 103 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 104 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     int i = -1;
/*     */     try {
/* 110 */       i = read();
/*     */     } finally {
/*     */       
/* 113 */       resourceRequest.request(-(l - ((i == -1) ? 0L : 1L)), resourceIdImpl);
/*     */     } 
/*     */     
/* 116 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(byte[] paramArrayOfbyte) throws IOException {
/*     */     ResourceRequest resourceRequest;
/* 123 */     if (paramArrayOfbyte == null) {
/* 124 */       return read(paramArrayOfbyte);
/*     */     }
/* 126 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/* 128 */     if (getFD() == FileDescriptor.in) {
/* 129 */       resourceRequest = ApproverGroup.STDIN_READ_GROUP.getApprover(this);
/*     */     } else {
/* 131 */       resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/*     */     } 
/* 133 */     int i = paramArrayOfbyte.length;
/* 134 */     long l = 0L;
/*     */     try {
/* 136 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 137 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 138 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 142 */     int j = 0;
/* 143 */     int k = 0;
/*     */     try {
/* 145 */       if (l < i) {
/*     */ 
/*     */         
/* 148 */         resourceRequest.request(-l, resourceIdImpl);
/* 149 */         k = read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 150 */         j = Math.max(k, 0);
/*     */       } else {
/* 152 */         k = read(paramArrayOfbyte);
/* 153 */         j = Math.max(k, 0);
/*     */       } 
/*     */     } finally {
/*     */       
/* 157 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */     
/* 160 */     return k;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     ResourceRequest resourceRequest;
/*     */     int j;
/* 167 */     if (paramInt2 < 0) {
/* 168 */       return read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 171 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/* 173 */     if (getFD() == FileDescriptor.in) {
/* 174 */       resourceRequest = ApproverGroup.STDIN_READ_GROUP.getApprover(this);
/*     */     } else {
/* 176 */       resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/*     */     } 
/* 178 */     long l = 0L;
/*     */     try {
/* 180 */       l = Math.max(resourceRequest.request(paramInt2, resourceIdImpl), 0L);
/* 181 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 182 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */ 
/*     */     
/* 186 */     paramInt2 = Math.min(paramInt2, (int)l);
/*     */ 
/*     */     
/* 189 */     int i = 0;
/*     */     
/*     */     try {
/* 192 */       j = read(paramArrayOfbyte, paramInt1, paramInt2);
/* 193 */       i = Math.max(j, 0);
/*     */     } finally {
/*     */       
/* 196 */       resourceRequest.request(-(l - i), resourceIdImpl);
/*     */     } 
/*     */     
/* 199 */     return j;
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
/* 211 */     synchronized (this.closeLock) {
/* 212 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 219 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     try {
/* 221 */       l = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 222 */       if (l == -1L) {
/* 223 */         l = javaIOFileDescriptorAccess.get(this.fd);
/*     */       }
/* 225 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 226 */       l = javaIOFileDescriptorAccess.get(this.fd);
/*     */     } 
/*     */     
/*     */     try {
/* 230 */       close();
/*     */     } finally {
/*     */       long l1;
/*     */       try {
/* 234 */         l1 = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 235 */         if (l1 == -1L) {
/* 236 */           l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */         }
/* 238 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 239 */         l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */       } 
/*     */       
/* 242 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */ 
/*     */       
/* 245 */       if (l1 != l) {
/* 246 */         ResourceRequest resourceRequest1 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 247 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       } 
/*     */       
/* 250 */       ResourceRequest resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 251 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\FileInputStreamRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */