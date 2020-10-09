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
/*     */ @InstrumentationTarget("java.io.FileOutputStream")
/*     */ public final class FileOutputStreamRMHooks
/*     */ {
/*  24 */   private final FileDescriptor fd = null;
/*  25 */   private final String path = null;
/*  26 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean closed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   private void open(String paramString, boolean paramBoolean) throws FileNotFoundException {
/*  37 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*  38 */     ResourceRequest resourceRequest1 = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/*  39 */     long l1 = 0L;
/*     */     try {
/*  41 */       l1 = resourceRequest1.request(1L, resourceIdImpl);
/*  42 */       if (l1 < 1L) {
/*  43 */         throw new FileNotFoundException(paramString + ": resource limited: too many open files");
/*     */       }
/*  45 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  46 */       FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open files");
/*  47 */       fileNotFoundException.initCause(resourceRequestDeniedException);
/*  48 */       throw fileNotFoundException;
/*     */     } 
/*     */     
/*  51 */     ResourceRequest resourceRequest2 = null;
/*  52 */     long l2 = 0L;
/*  53 */     boolean bool = false;
/*     */     try {
/*  55 */       FileDescriptor fileDescriptor = null;
/*     */       try {
/*  57 */         fileDescriptor = getFD();
/*  58 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/*  62 */       resourceRequest2 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(fileDescriptor);
/*     */       try {
/*  64 */         l2 = resourceRequest2.request(1L, resourceIdImpl);
/*  65 */         if (l2 < 1L) {
/*  66 */           throw new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*     */         }
/*  68 */       } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/*  69 */         FileNotFoundException fileNotFoundException = new FileNotFoundException(paramString + ": resource limited: too many open file descriptors");
/*  70 */         fileNotFoundException.initCause(resourceRequestDeniedException);
/*  71 */         throw fileNotFoundException;
/*     */       } 
/*     */       
/*  74 */       open(paramString, paramBoolean);
/*  75 */       bool = true;
/*     */     } finally {
/*     */       
/*  78 */       resourceRequest2.request(-(l2 - bool), resourceIdImpl);
/*  79 */       resourceRequest1.request(-(l1 - bool), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public final FileDescriptor getFD() throws IOException {
/*  85 */     return getFD();
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public void write(int paramInt) throws IOException {
/*     */     ResourceRequest resourceRequest;
/*  90 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/*  92 */     FileDescriptor fileDescriptor = getFD();
/*  93 */     if (fileDescriptor == FileDescriptor.err) {
/*  94 */       resourceRequest = ApproverGroup.STDERR_WRITE_GROUP.getApprover(this);
/*  95 */     } else if (fileDescriptor == FileDescriptor.out) {
/*  96 */       resourceRequest = ApproverGroup.STDOUT_WRITE_GROUP.getApprover(this);
/*     */     } else {
/*  98 */       resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/*     */     } 
/* 100 */     long l = 0L;
/*     */     try {
/* 102 */       l = resourceRequest.request(1L, resourceIdImpl);
/* 103 */       if (l < 1L) {
/* 104 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 106 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 107 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 110 */     boolean bool = false;
/*     */     try {
/* 112 */       write(paramInt);
/* 113 */       bool = true;
/*     */     } finally {
/*     */       
/* 116 */       resourceRequest.request(-(l - bool), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public void write(byte[] paramArrayOfbyte) throws IOException {
/*     */     ResourceRequest resourceRequest;
/* 122 */     if (paramArrayOfbyte == null) {
/* 123 */       write(paramArrayOfbyte);
/*     */       return;
/*     */     } 
/* 126 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/* 128 */     FileDescriptor fileDescriptor = getFD();
/* 129 */     if (fileDescriptor == FileDescriptor.err) {
/* 130 */       resourceRequest = ApproverGroup.STDERR_WRITE_GROUP.getApprover(this);
/* 131 */     } else if (fileDescriptor == FileDescriptor.out) {
/* 132 */       resourceRequest = ApproverGroup.STDOUT_WRITE_GROUP.getApprover(this);
/*     */     } else {
/* 134 */       resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/*     */     } 
/* 136 */     int i = paramArrayOfbyte.length;
/* 137 */     long l = 0L;
/*     */     try {
/* 139 */       l = resourceRequest.request(i, resourceIdImpl);
/* 140 */       if (l < i) {
/* 141 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 143 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 144 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 147 */     int j = 0;
/*     */     try {
/* 149 */       write(paramArrayOfbyte);
/* 150 */       j = i;
/*     */     } finally {
/*     */       
/* 153 */       resourceRequest.request(-(l - j), resourceIdImpl);
/*     */     } 
/*     */   }
/*     */   @InstrumentationMethod
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     ResourceRequest resourceRequest;
/* 159 */     if (paramInt2 < 0) {
/* 160 */       write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */     
/* 166 */     FileDescriptor fileDescriptor = getFD();
/* 167 */     if (fileDescriptor == FileDescriptor.err) {
/* 168 */       resourceRequest = ApproverGroup.STDERR_WRITE_GROUP.getApprover(this);
/* 169 */     } else if (fileDescriptor == FileDescriptor.out) {
/* 170 */       resourceRequest = ApproverGroup.STDOUT_WRITE_GROUP.getApprover(this);
/*     */     } else {
/* 172 */       resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/*     */     } 
/* 174 */     long l = 0L;
/*     */     try {
/* 176 */       l = resourceRequest.request(paramInt2, resourceIdImpl);
/* 177 */       if (l < paramInt2) {
/* 178 */         throw new IOException("Resource limited: insufficient bytes approved");
/*     */       }
/* 180 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 181 */       throw new IOException("Resource limited", resourceRequestDeniedException);
/*     */     } 
/*     */     
/* 184 */     int i = 0;
/*     */     try {
/* 186 */       write(paramArrayOfbyte, paramInt1, paramInt2);
/* 187 */       i = paramInt2;
/*     */     } finally {
/*     */       
/* 190 */       resourceRequest.request(-(l - i), resourceIdImpl);
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
/* 203 */     synchronized (this.closeLock) {
/* 204 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     try {
/* 213 */       l = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 214 */       if (l == -1L) {
/* 215 */         l = javaIOFileDescriptorAccess.get(this.fd);
/*     */       }
/* 217 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 218 */       l = javaIOFileDescriptorAccess.get(this.fd);
/*     */     } 
/*     */     
/*     */     try {
/* 222 */       close();
/*     */     } finally {
/*     */       long l1;
/*     */       try {
/* 226 */         l1 = javaIOFileDescriptorAccess.getHandle(this.fd);
/* 227 */         if (l1 == -1L) {
/* 228 */           l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */         }
/* 230 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/* 231 */         l1 = javaIOFileDescriptorAccess.get(this.fd);
/*     */       } 
/*     */       
/* 234 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.path);
/*     */ 
/*     */       
/* 237 */       if (l1 != l) {
/* 238 */         ResourceRequest resourceRequest1 = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
/* 239 */         resourceRequest1.request(-1L, resourceIdImpl);
/*     */       } 
/*     */       
/* 242 */       ResourceRequest resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 243 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\FileOutputStreamRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */