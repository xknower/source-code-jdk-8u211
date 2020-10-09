/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.util.Arrays;
/*     */ import jdk.management.resource.ResourceAccuracy;
/*     */ import jdk.management.resource.ResourceContext;
/*     */ import jdk.management.resource.ResourceId;
/*     */ import jdk.management.resource.ResourceMeter;
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
/*     */ class HeapMetrics
/*     */   implements Runnable
/*     */ {
/*  33 */   private static final HeapMetrics instance = new HeapMetrics();
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile Thread thread;
/*     */ 
/*     */   
/*  40 */   private static ResourceIdImpl[] idWithAccuracy = new ResourceIdImpl[] {
/*  41 */       ResourceIdImpl.of("Heap", ResourceAccuracy.LOW), 
/*  42 */       ResourceIdImpl.of("Heap", ResourceAccuracy.MEDIUM), 
/*  43 */       ResourceIdImpl.of("Heap", ResourceAccuracy.HIGH), 
/*  44 */       ResourceIdImpl.of("Heap", ResourceAccuracy.HIGHEST)
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static ResourceIdImpl[] idWithAccuracyForced = new ResourceIdImpl[] {
/*  51 */       ResourceIdImpl.of("Heap", ResourceAccuracy.LOW, true), 
/*  52 */       ResourceIdImpl.of("Heap", ResourceAccuracy.MEDIUM, true), 
/*  53 */       ResourceIdImpl.of("Heap", ResourceAccuracy.HIGH, true), 
/*  54 */       ResourceIdImpl.of("Heap", ResourceAccuracy.HIGHEST, true)
/*     */     };
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
/*     */   static void init() {
/*  69 */     synchronized (instance) {
/*  70 */       if (thread != null) {
/*  71 */         if (thread.isAlive()) {
/*     */           return;
/*     */         }
/*  74 */         terminate();
/*     */       } 
/*     */       
/*  77 */       thread = AccessController.<Thread>doPrivileged(() -> { ThreadGroup threadGroup; for (threadGroup = Thread.currentThread().getThreadGroup(); threadGroup.getParent() != null; threadGroup = threadGroup.getParent()); ResourceContext resourceContext = SimpleResourceContext.getThreadContext(Thread.currentThread()); UnassignedContext.getSystemContext().bindThreadContext(); thread = new Thread(threadGroup, instance, "HeapMetrics"); thread.setDaemon(true); resourceContext.bindThreadContext(); return thread; }(AccessControlContext)null, new Permission[] { new RuntimePermission("modifyThreadGroup"), new RuntimePermission("modifyThread") });
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
/*  96 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void terminate() {
/* 106 */     synchronized (instance) {
/* 107 */       if (thread != null) {
/* 108 */         Thread thread = thread;
/* 109 */         thread = null;
/* 110 */         thread.interrupt();
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
/*     */   private ResourceId selectId(int paramInt, boolean paramBoolean) {
/* 122 */     return paramBoolean ? idWithAccuracyForced[paramInt] : idWithAccuracy[paramInt];
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
/*     */   public void run() {
/* 137 */     Object object = new Object();
/* 138 */     ResourceNatives.setRetainedMemoryNotificationEnabled(object);
/*     */ 
/*     */     
/* 141 */     UnassignedContext.getSystemContext().bindThreadContext();
/* 142 */     UnassignedContext unassignedContext = UnassignedContext.getUnassignedContext();
/*     */     
/* 144 */     long[] arrayOfLong = new long[1];
/* 145 */     int[] arrayOfInt = new int[1];
/* 146 */     byte[] arrayOfByte = new byte[1];
/*     */     
/* 148 */     synchronized (object) {
/*     */       
/* 150 */       while (Thread.currentThread().equals(thread)) {
/*     */         try {
/* 152 */           boolean bool = false;
/*     */ 
/*     */ 
/*     */           
/*     */           do {
/* 157 */             SimpleResourceContext[] arrayOfSimpleResourceContext = (SimpleResourceContext[])SimpleResourceContext.getContexts().values().toArray((Object[])new SimpleResourceContext[0]);
/* 158 */             int i = arrayOfSimpleResourceContext.length;
/*     */ 
/*     */ 
/*     */             
/* 162 */             if (i + 1 != arrayOfLong.length) {
/* 163 */               arrayOfLong = new long[i + 1];
/* 164 */               arrayOfInt = new int[i + 1];
/* 165 */               arrayOfByte = new byte[i + 1];
/*     */             } 
/*     */             
/*     */             byte b;
/* 169 */             for (b = 0; b < i; b++) {
/* 170 */               arrayOfInt[b] = arrayOfSimpleResourceContext[b].nativeThreadContext();
/*     */             }
/* 172 */             arrayOfInt[i] = unassignedContext.nativeThreadContext();
/*     */             
/* 174 */             Arrays.fill(arrayOfLong, -1L);
/* 175 */             bool = ResourceNatives.getContextsRetainedMemory(arrayOfInt, arrayOfLong, arrayOfByte);
/* 176 */             for (b = 0; b <= i; b++) {
/* 177 */               if (arrayOfLong[b] != -1L) {
/*     */                 
/* 179 */                 SimpleResourceContext simpleResourceContext = (b < i) ? arrayOfSimpleResourceContext[b] : unassignedContext;
/* 180 */                 ResourceRequest resourceRequest = simpleResourceContext.getResourceRequest(ResourceType.HEAP_RETAINED);
/* 181 */                 if (resourceRequest != null) {
/*     */                   
/* 183 */                   long l = arrayOfLong[b] - ((ResourceMeter)resourceRequest).getValue();
/* 184 */                   boolean bool1 = (arrayOfByte[b] >= ResourceAccuracy.HIGH.ordinal()) ? true : false;
/* 185 */                   if (l != 0L || bool1)
/*     */                   {
/* 187 */                     resourceRequest.request(l, selectId(arrayOfByte[b], bool1));
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/* 192 */           } while (bool);
/*     */ 
/*     */           
/* 195 */           object.wait();
/*     */         }
/* 197 */         catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\HeapMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */