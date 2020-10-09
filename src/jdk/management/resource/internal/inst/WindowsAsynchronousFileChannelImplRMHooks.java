/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import jdk.internal.instrumentation.InstrumentationMethod;
/*     */ import jdk.internal.instrumentation.InstrumentationTarget;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceRequestDeniedException;
/*     */ import jdk.management.resource.internal.ApproverGroup;
/*     */ import jdk.management.resource.internal.CompletionHandlerWrapper;
/*     */ import jdk.management.resource.internal.FutureWrapper;
/*     */ import jdk.management.resource.internal.ResourceIdImpl;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.ch.ThreadPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @InstrumentationTarget("sun.nio.ch.WindowsAsynchronousFileChannelImpl")
/*     */ public final class WindowsAsynchronousFileChannelImplRMHooks
/*     */ {
/*  33 */   protected final FileDescriptor fdObj = null;
/*  34 */   protected final ReadWriteLock closeLock = new ReentrantReadWriteLock();
/*     */   
/*     */   protected volatile boolean closed;
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public static AsynchronousFileChannel open(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, ThreadPool paramThreadPool) {
/*     */     long l1;
/*  42 */     AsynchronousFileChannel asynchronousFileChannel = open(paramFileDescriptor, paramBoolean1, paramBoolean2, paramThreadPool);
/*     */ 
/*     */     
/*  45 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     
/*     */     try {
/*  48 */       l1 = javaIOFileDescriptorAccess.getHandle(paramFileDescriptor);
/*  49 */       if (l1 == -1L) {
/*  50 */         l1 = javaIOFileDescriptorAccess.get(paramFileDescriptor);
/*     */       }
/*  52 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/*  53 */       l1 = javaIOFileDescriptorAccess.get(paramFileDescriptor);
/*     */     } 
/*     */     
/*  56 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l1));
/*  57 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor);
/*     */     
/*  59 */     long l2 = 0L;
/*  60 */     boolean bool = false;
/*     */     try {
/*  62 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/*  63 */       if (l2 < 1L) {
/*  64 */         throw new ResourceRequestDeniedException("Resource limited: too many open file descriptors");
/*     */       }
/*  66 */       bool = true;
/*     */     } finally {
/*  68 */       if (!bool) {
/*  69 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */         try {
/*  71 */           asynchronousFileChannel.close();
/*  72 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  78 */     bool = false;
/*  79 */     resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(asynchronousFileChannel);
/*     */     try {
/*  81 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/*  82 */       if (l2 < 1L) {
/*     */         try {
/*  84 */           asynchronousFileChannel.close();
/*  85 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/*  88 */         throw new ResourceRequestDeniedException("Resource limited: too many open files");
/*     */       } 
/*  90 */       bool = true;
/*     */     } finally {
/*  92 */       if (!bool) {
/*  93 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */         try {
/*  95 */           asynchronousFileChannel.close();
/*  96 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     return asynchronousFileChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <A> Future<Integer> implRead(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 110 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fdObj);
/* 111 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 112 */     long l = 0L;
/* 113 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 115 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 116 */       if (l < i) {
/* 117 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 119 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 120 */       if (paramCompletionHandler != null) {
/* 121 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 122 */         return null;
/*     */       } 
/* 124 */       CompletableFuture<Integer> completableFuture = new CompletableFuture();
/* 125 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 126 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     CompletionHandlerWrapper<Integer, A> completionHandlerWrapper = null;
/* 131 */     if (paramCompletionHandler != null) {
/* 132 */       completionHandlerWrapper = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 135 */     Future<Integer> future = implRead(paramByteBuffer, paramLong, paramA, completionHandlerWrapper);
/*     */     
/* 137 */     if (paramCompletionHandler == null) {
/* 138 */       if (future.isDone()) {
/* 139 */         int j = 0;
/*     */         try {
/* 141 */           j = ((Integer)future.get()).intValue();
/* 142 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 145 */         j = Math.max(0, j);
/* 146 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 148 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 152 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <A> Future<Integer> implWrite(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 160 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fdObj);
/* 161 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 162 */     long l = 0L;
/* 163 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 165 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 166 */       if (l < i) {
/* 167 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 169 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 170 */       if (paramCompletionHandler != null) {
/* 171 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 172 */         return null;
/*     */       } 
/* 174 */       CompletableFuture<Integer> completableFuture = new CompletableFuture();
/* 175 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 176 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     CompletionHandlerWrapper<Integer, A> completionHandlerWrapper = null;
/* 181 */     if (paramCompletionHandler != null) {
/* 182 */       completionHandlerWrapper = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 185 */     Future<Integer> future = implWrite(paramByteBuffer, paramLong, paramA, completionHandlerWrapper);
/*     */     
/* 187 */     if (paramCompletionHandler == null) {
/* 188 */       if (future.isDone()) {
/* 189 */         int j = 0;
/*     */         try {
/* 191 */           j = ((Integer)future.get()).intValue();
/* 192 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 195 */         j = Math.max(0, j);
/* 196 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 198 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 202 */     return future;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void close() throws IOException {
/* 207 */     this.closeLock.writeLock().lock();
/*     */     try {
/* 209 */       if (this.closed)
/*     */         return; 
/*     */     } finally {
/* 212 */       this.closeLock.writeLock().unlock();
/*     */     } 
/*     */     
/*     */     try {
/* 216 */       close();
/*     */     } finally {
/*     */       
/* 219 */       JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/* 220 */       long l = javaIOFileDescriptorAccess.getHandle(this.fdObj);
/* 221 */       if (l == -1L) {
/* 222 */         l = javaIOFileDescriptorAccess.get(this.fdObj);
/*     */       }
/* 224 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l));
/* 225 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fdObj);
/* 226 */       resourceRequest.request(-1L, resourceIdImpl);
/* 227 */       resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 228 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\WindowsAsynchronousFileChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */