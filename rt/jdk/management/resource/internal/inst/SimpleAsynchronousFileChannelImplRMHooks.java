/*     */ package jdk.management.resource.internal.inst;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.CompletionHandler;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Future;
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
/*     */ 
/*     */ 
/*     */ @InstrumentationTarget("sun.nio.ch.SimpleAsynchronousFileChannelImpl")
/*     */ public final class SimpleAsynchronousFileChannelImplRMHooks
/*     */ {
/*  33 */   protected final FileDescriptor fdObj = null;
/*     */   
/*     */   protected volatile boolean closed;
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   public static AsynchronousFileChannel open(FileDescriptor paramFileDescriptor, boolean paramBoolean1, boolean paramBoolean2, ThreadPool paramThreadPool) {
/*     */     long l1;
/*  41 */     AsynchronousFileChannel asynchronousFileChannel = open(paramFileDescriptor, paramBoolean1, paramBoolean2, paramThreadPool);
/*     */ 
/*     */     
/*  44 */     JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */     
/*     */     try {
/*  47 */       l1 = javaIOFileDescriptorAccess.getHandle(paramFileDescriptor);
/*  48 */       if (l1 == -1L) {
/*  49 */         l1 = javaIOFileDescriptorAccess.get(paramFileDescriptor);
/*     */       }
/*  51 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/*  52 */       l1 = javaIOFileDescriptorAccess.get(paramFileDescriptor);
/*     */     } 
/*     */     
/*  55 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Long.valueOf(l1));
/*  56 */     ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(paramFileDescriptor);
/*     */     
/*  58 */     long l2 = 0L;
/*  59 */     boolean bool = false;
/*     */     try {
/*  61 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/*  62 */       if (l2 < 1L) {
/*  63 */         throw new ResourceRequestDeniedException("Resource limited: too many open file descriptors");
/*     */       }
/*  65 */       bool = true;
/*     */     } finally {
/*  67 */       if (!bool) {
/*  68 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */         try {
/*  70 */           asynchronousFileChannel.close();
/*  71 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  77 */     bool = false;
/*  78 */     resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(asynchronousFileChannel);
/*     */     try {
/*  80 */       l2 = resourceRequest.request(1L, resourceIdImpl);
/*  81 */       if (l2 < 1L) {
/*     */         try {
/*  83 */           asynchronousFileChannel.close();
/*  84 */         } catch (IOException iOException) {}
/*     */ 
/*     */         
/*  87 */         throw new ResourceRequestDeniedException("Resource limited: too many open files");
/*     */       } 
/*  89 */       bool = true;
/*     */     } finally {
/*  91 */       if (!bool) {
/*  92 */         resourceRequest.request(-1L, resourceIdImpl);
/*     */         try {
/*  94 */           asynchronousFileChannel.close();
/*  95 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     return asynchronousFileChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <A> Future<Integer> implRead(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 109 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fdObj);
/* 110 */     ResourceRequest resourceRequest = ApproverGroup.FILE_READ_GROUP.getApprover(this);
/* 111 */     long l = 0L;
/* 112 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 114 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 115 */       if (l < i) {
/* 116 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 118 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 119 */       if (paramCompletionHandler != null) {
/* 120 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 121 */         return null;
/*     */       } 
/* 123 */       CompletableFuture<Integer> completableFuture = new CompletableFuture();
/* 124 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 125 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     CompletionHandlerWrapper<Integer, A> completionHandlerWrapper = null;
/* 130 */     if (paramCompletionHandler != null) {
/* 131 */       completionHandlerWrapper = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 134 */     Future<Integer> future = implRead(paramByteBuffer, paramLong, paramA, completionHandlerWrapper);
/*     */     
/* 136 */     if (paramCompletionHandler == null) {
/* 137 */       if (future.isDone()) {
/* 138 */         int j = 0;
/*     */         try {
/* 140 */           j = ((Integer)future.get()).intValue();
/* 141 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 144 */         j = Math.max(0, j);
/* 145 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 147 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 151 */     return future;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @InstrumentationMethod
/*     */   <A> Future<Integer> implWrite(ByteBuffer paramByteBuffer, long paramLong, A paramA, CompletionHandler<Integer, ? super A> paramCompletionHandler) {
/* 159 */     ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fdObj);
/* 160 */     ResourceRequest resourceRequest = ApproverGroup.FILE_WRITE_GROUP.getApprover(this);
/* 161 */     long l = 0L;
/* 162 */     int i = paramByteBuffer.remaining();
/*     */     try {
/* 164 */       l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
/* 165 */       if (l < i) {
/* 166 */         throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved");
/*     */       }
/* 168 */     } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
/* 169 */       if (paramCompletionHandler != null) {
/* 170 */         paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
/* 171 */         return null;
/*     */       } 
/* 173 */       CompletableFuture<Integer> completableFuture = new CompletableFuture();
/* 174 */       completableFuture.completeExceptionally(resourceRequestDeniedException);
/* 175 */       return completableFuture;
/*     */     } 
/*     */ 
/*     */     
/* 179 */     CompletionHandlerWrapper<Integer, A> completionHandlerWrapper = null;
/* 180 */     if (paramCompletionHandler != null) {
/* 181 */       completionHandlerWrapper = new CompletionHandlerWrapper<>(paramCompletionHandler, resourceIdImpl, resourceRequest, l);
/*     */     }
/*     */     
/* 184 */     Future<Integer> future = implWrite(paramByteBuffer, paramLong, paramA, completionHandlerWrapper);
/*     */     
/* 186 */     if (paramCompletionHandler == null) {
/* 187 */       if (future.isDone()) {
/* 188 */         int j = 0;
/*     */         try {
/* 190 */           j = ((Integer)future.get()).intValue();
/* 191 */         } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
/*     */ 
/*     */         
/* 194 */         j = Math.max(0, j);
/* 195 */         resourceRequest.request(-(l - j), resourceIdImpl);
/*     */       } else {
/* 197 */         future = new FutureWrapper<>(future, resourceIdImpl, resourceRequest, l);
/*     */       } 
/*     */     }
/*     */     
/* 201 */     return future;
/*     */   }
/*     */   
/*     */   @InstrumentationMethod
/*     */   public void close() throws IOException {
/* 206 */     synchronized (this.fdObj) {
/* 207 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 213 */       close();
/*     */     } finally {
/*     */       
/* 216 */       JavaIOFileDescriptorAccess javaIOFileDescriptorAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/* 217 */       ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(Integer.valueOf(javaIOFileDescriptorAccess.get(this.fdObj)));
/* 218 */       ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fdObj);
/* 219 */       resourceRequest.request(-1L, resourceIdImpl);
/* 220 */       resourceRequest = ApproverGroup.FILE_OPEN_GROUP.getApprover(this);
/* 221 */       resourceRequest.request(-1L, resourceIdImpl);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\inst\SimpleAsynchronousFileChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */