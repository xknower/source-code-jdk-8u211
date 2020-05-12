/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import jdk.management.resource.ResourceContext;
/*     */ import jdk.management.resource.ResourceRequest;
/*     */ import jdk.management.resource.ResourceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ThreadMetrics
/*     */ {
/*  34 */   static final WeakKeyConcurrentHashMap<Thread, ThreadMetrics> threadMetrics = new WeakKeyConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private long cputime = 0L;
/*  52 */   private long allocatedHeap = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized long usedCputime(long paramLong) {
/*  62 */     long l = (this.cputime == 0L) ? 0L : (paramLong - this.cputime);
/*  63 */     this.cputime = paramLong;
/*  64 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized long usedAllocatedHeap(long paramLong) {
/*  74 */     long l = (this.allocatedHeap == 0L) ? 0L : (paramLong - this.allocatedHeap);
/*  75 */     this.allocatedHeap = paramLong;
/*  76 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateCurrentThreadMetrics(ResourceContext paramResourceContext) {
/*  85 */     ResourceRequest resourceRequest1 = paramResourceContext.getResourceRequest(ResourceType.THREAD_CPU);
/*  86 */     ResourceRequest resourceRequest2 = paramResourceContext.getResourceRequest(ResourceType.HEAP_ALLOCATED);
/*  87 */     if (resourceRequest1 == null && resourceRequest2 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     updateCurrentThreadMetrics(resourceRequest1, resourceRequest2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateThreadMetrics(ResourceContext paramResourceContext) {
/* 100 */     ResourceRequest resourceRequest1 = paramResourceContext.getResourceRequest(ResourceType.THREAD_CPU);
/* 101 */     ResourceRequest resourceRequest2 = paramResourceContext.getResourceRequest(ResourceType.HEAP_ALLOCATED);
/* 102 */     if (resourceRequest1 == null && resourceRequest2 == null) {
/*     */       return;
/*     */     }
/* 105 */     Thread[] arrayOfThread = paramResourceContext.boundThreads().<Thread>toArray(paramInt -> new Thread[paramInt]);
/* 106 */     if (arrayOfThread.length > 0) {
/* 107 */       updateThreadMetrics(arrayOfThread, resourceRequest1, resourceRequest2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void updateThreadMetrics(ResourceContext paramResourceContext, Thread paramThread) {
/* 118 */     ResourceRequest resourceRequest1 = paramResourceContext.getResourceRequest(ResourceType.THREAD_CPU);
/* 119 */     ResourceRequest resourceRequest2 = paramResourceContext.getResourceRequest(ResourceType.HEAP_ALLOCATED);
/* 120 */     if (resourceRequest1 == null && resourceRequest2 == null) {
/*     */       return;
/*     */     }
/* 123 */     Thread[] arrayOfThread = { paramThread };
/* 124 */     updateThreadMetrics(arrayOfThread, resourceRequest1, resourceRequest2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateCurrentThreadMetrics(ResourceRequest paramResourceRequest1, ResourceRequest paramResourceRequest2) {
/* 139 */     long l1 = ResourceNatives.getCurrentThreadCPUTime();
/* 140 */     long l2 = ResourceNatives.getCurrentThreadAllocatedHeap();
/*     */     
/* 142 */     Thread thread = Thread.currentThread();
/* 143 */     ThreadMetrics threadMetrics = getThreadMetrics(thread);
/* 144 */     updateMetrics(threadMetrics, thread.getId(), paramResourceRequest1, l1, paramResourceRequest2, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateThreadMetrics(Thread[] paramArrayOfThread, ResourceRequest paramResourceRequest1, ResourceRequest paramResourceRequest2) {
/* 160 */     long[] arrayOfLong1 = new long[paramArrayOfThread.length];
/* 161 */     long[] arrayOfLong2 = new long[paramArrayOfThread.length];
/* 162 */     long[] arrayOfLong3 = new long[paramArrayOfThread.length]; byte b;
/* 163 */     for (b = 0; b < paramArrayOfThread.length; b++) {
/* 164 */       arrayOfLong3[b] = (paramArrayOfThread[b] != null) ? paramArrayOfThread[b].getId() : Long.MIN_VALUE;
/*     */     }
/* 166 */     ResourceNatives.getThreadStats(arrayOfLong3, arrayOfLong1, arrayOfLong2);
/*     */ 
/*     */     
/* 169 */     for (b = 0; b < paramArrayOfThread.length; b++) {
/* 170 */       if (arrayOfLong1[b] != -1L && arrayOfLong2[b] != -1L) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 175 */         ThreadMetrics threadMetrics = getThreadMetrics(paramArrayOfThread[b]);
/* 176 */         updateMetrics(threadMetrics, arrayOfLong3[b], paramResourceRequest1, arrayOfLong1[b], paramResourceRequest2, arrayOfLong2[b]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateMetrics(ThreadMetrics paramThreadMetrics, long paramLong1, ResourceRequest paramResourceRequest1, long paramLong2, ResourceRequest paramResourceRequest2, long paramLong3) {
/* 200 */     long l1 = paramThreadMetrics.usedCputime(paramLong2);
/* 201 */     ResourceIdImpl resourceIdImpl = null;
/* 202 */     if (l1 > 0L && paramResourceRequest1 != null) {
/* 203 */       resourceIdImpl = ResourceIdImpl.of(Long.valueOf(paramLong1));
/*     */       try {
/* 205 */         paramResourceRequest1.request(l1, resourceIdImpl);
/* 206 */       } catch (RuntimeException runtimeException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     long l2 = paramThreadMetrics.usedAllocatedHeap(paramLong3);
/* 212 */     if (l2 > 0L && paramResourceRequest2 != null) {
/* 213 */       if (resourceIdImpl == null) {
/* 214 */         resourceIdImpl = ResourceIdImpl.of(Long.valueOf(paramLong1));
/*     */       }
/*     */       try {
/* 217 */         paramResourceRequest2.request(l2, resourceIdImpl);
/* 218 */       } catch (RuntimeException runtimeException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ThreadMetrics getThreadMetrics(Thread paramThread) {
/* 231 */     return threadMetrics.computeIfAbsent(paramThread, paramThread -> new ThreadMetrics());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void init() {
/* 243 */     int i = ResourceNatives.sampleInterval();
/* 244 */     if (i != 0) {
/*     */       
/* 246 */       if (i < 0) {
/* 247 */         i = 100;
/*     */       }
/* 249 */       ThreadSampler.init(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ThreadSampler
/*     */     implements Runnable
/*     */   {
/* 262 */     private static ThreadSampler samplerRunnable = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     private static ScheduledFuture<?> samplerFuture = null;
/*     */ 
/*     */     
/*     */     private final long interval;
/*     */ 
/*     */     
/*     */     private static final ScheduledExecutorService scheduledExecutor;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 278 */       scheduledExecutor = AccessController.<ScheduledExecutorService>doPrivileged(() -> {
/*     */             ThreadGroup threadGroup1;
/*     */             for (threadGroup1 = Thread.currentThread().getThreadGroup(); threadGroup1.getParent() != null; threadGroup1 = threadGroup1.getParent());
/*     */             ThreadGroup threadGroup2 = threadGroup1;
/*     */             ThreadFactory threadFactory = ();
/*     */             return Executors.newScheduledThreadPool(1, threadFactory);
/*     */           }(AccessControlContext)null, new Permission[] { new RuntimePermission("modifyThreadGroup"), new RuntimePermission("modifyThread") });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static synchronized void init(long param1Long) {
/* 310 */       if (samplerRunnable == null || param1Long != samplerRunnable.interval) {
/* 311 */         terminate();
/*     */         
/* 313 */         samplerRunnable = new ThreadSampler(param1Long);
/* 314 */         samplerFuture = scheduledExecutor.scheduleAtFixedRate(samplerRunnable, param1Long, param1Long, TimeUnit.MILLISECONDS);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static synchronized void terminate() {
/* 322 */       samplerRunnable = null;
/* 323 */       if (samplerFuture != null) {
/* 324 */         samplerFuture.cancel(false);
/*     */       }
/*     */     }
/*     */     
/*     */     private ThreadSampler(long param1Long) {
/* 329 */       this.interval = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 338 */       UnassignedContext.getSystemContext().bindThreadContext();
/*     */       try {
/* 340 */         SimpleResourceContext.getContexts().forEachValue(2147483647L, param1SimpleResourceContext -> ThreadMetrics.updateThreadMetrics(param1SimpleResourceContext));
/*     */       }
/* 342 */       catch (RuntimeException runtimeException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\ThreadMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */