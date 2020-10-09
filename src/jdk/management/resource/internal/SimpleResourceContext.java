/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.StringJoiner;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.stream.Stream;
/*     */ import jdk.management.resource.ResourceAccuracy;
/*     */ import jdk.management.resource.ResourceContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleResourceContext
/*     */   implements ResourceContext
/*     */ {
/*  41 */   private final ConcurrentHashMap<ResourceType, ResourceMeter> meters = new ConcurrentHashMap<>();
/*     */   
/*  43 */   private static final WeakKeyConcurrentHashMap<Thread, ResourceContext> currContext = new WeakKeyConcurrentHashMap<>();
/*     */   
/*  45 */   private static final ConcurrentHashMap<String, SimpleResourceContext> contexts = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int contextId;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleResourceContext(String paramString) {
/*  63 */     this(Objects.<String>requireNonNull(paramString, "name"), ResourceNatives.createResourceContext(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SimpleResourceContext(String paramString, int paramInt) {
/*  70 */     this.name = Objects.<String>requireNonNull(paramString, "name");
/*  71 */     this.contextId = paramInt;
/*  72 */     this.closed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  77 */     return this.name;
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
/*     */   public static ResourceContext create(String paramString) {
/*  89 */     if (!contexts.containsKey(paramString)) {
/*  90 */       SimpleResourceContext simpleResourceContext = new SimpleResourceContext(paramString);
/*  91 */       ResourceContext resourceContext = contexts.putIfAbsent(paramString, simpleResourceContext);
/*  92 */       if (resourceContext == null) {
/*  93 */         return simpleResourceContext;
/*     */       }
/*     */       
/*  96 */       ResourceNatives.destroyResourceContext(simpleResourceContext.contextId, 0);
/*     */     } 
/*     */     
/*  99 */     throw new IllegalArgumentException("ResourceContext already exists for name: " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceContext get(String paramString) {
/* 109 */     Objects.requireNonNull(paramString, "name");
/* 110 */     return contexts.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 115 */     synchronized (this) {
/* 116 */       if (!this.closed) {
/*     */         
/* 118 */         this.closed = true;
/*     */         
/* 120 */         contexts.remove(getName());
/*     */ 
/*     */         
/* 123 */         UnassignedContext unassignedContext = UnassignedContext.getUnassignedContext();
/* 124 */         ResourceNatives.destroyResourceContext(this.contextId, unassignedContext.nativeThreadContext());
/*     */ 
/*     */         
/* 127 */         boundThreads().forEach(paramThread -> paramUnassignedContext.bindThreadContext(paramThread));
/*     */ 
/*     */         
/* 130 */         this.meters.forEach((paramResourceType, paramResourceMeter) -> {
/*     */               removeResourceMeter(paramResourceMeter);
/*     */               ApproverGroup approverGroup = ApproverGroup.getGroup(paramResourceType);
/*     */               if (approverGroup != null) {
/*     */                 approverGroup.purgeResourceContext(this);
/*     */               }
/*     */             });
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
/*     */   static ConcurrentHashMap<String, SimpleResourceContext> getContexts() {
/* 148 */     return contexts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Stream<ResourceContext> contexts() {
/* 157 */     return getContexts().values().stream().map(paramSimpleResourceContext -> paramSimpleResourceContext);
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
/*     */   public static ResourceContext getThreadContext(Thread paramThread) {
/* 169 */     ResourceContext resourceContext = currContext.get(paramThread);
/* 170 */     if (resourceContext == null) {
/* 171 */       resourceContext = UnassignedContext.getUnassignedContext();
/*     */     }
/* 173 */     return resourceContext;
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
/*     */   public ResourceContext bindThreadContext() {
/* 186 */     illegalStateIfClosed();
/* 187 */     Thread thread = Thread.currentThread();
/* 188 */     ResourceContext resourceContext = currContext.put(thread, this);
/* 189 */     if (resourceContext == null) {
/* 190 */       resourceContext = UnassignedContext.getUnassignedContext();
/*     */     }
/* 192 */     ThreadMetrics.updateCurrentThreadMetrics(resourceContext);
/*     */     
/* 194 */     ResourceNatives.setThreadResourceContext(this.contextId);
/* 195 */     return resourceContext;
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
/*     */   public ResourceContext bindThreadContext(Thread paramThread) {
/* 212 */     illegalStateIfClosed();
/* 213 */     ResourceContext resourceContext = paramThread.isAlive() ? currContext.put(paramThread, this) : currContext.remove(paramThread);
/* 214 */     if (resourceContext == null) {
/* 215 */       resourceContext = UnassignedContext.getUnassignedContext();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 220 */       ThreadMetrics.updateThreadMetrics(resourceContext, paramThread);
/* 221 */       ResourceNatives.setThreadResourceContext(paramThread.getId(), this.contextId);
/* 222 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 224 */       currContext.remove(paramThread);
/*     */     } 
/* 226 */     return resourceContext;
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
/*     */   public void bindNewThreadContext(Thread paramThread) {
/* 238 */     currContext.put(paramThread, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeThreadContext() {
/* 246 */     currContext.remove(Thread.currentThread());
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
/*     */   public static ResourceContext unbindThreadContext() {
/* 262 */     return UnassignedContext.getUnassignedContext().bindThreadContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int nativeThreadContext() {
/* 270 */     return this.contextId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<Thread> boundThreads() {
/* 275 */     return currContext.keysForValue(this).filter(paramThread -> paramThread.isAlive());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceRequest getResourceRequest(ResourceType paramResourceType) {
/* 283 */     ResourceMeter resourceMeter = this.meters.get(paramResourceType);
/* 284 */     return (resourceMeter instanceof ResourceRequest) ? (ResourceRequest)resourceMeter : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceMeter(ResourceMeter paramResourceMeter) {
/* 290 */     illegalStateIfClosed();
/* 291 */     if (paramResourceMeter.getType().equals(ResourceType.HEAP_RETAINED) && !ResourceNatives.isHeapRetainedEnabled()) {
/* 292 */       throw new UnsupportedOperationException("ResourceType not supported by the current garbage collector: " + ResourceType.HEAP_RETAINED);
/*     */     }
/*     */     
/* 295 */     ResourceMeter resourceMeter = this.meters.putIfAbsent(paramResourceMeter.getType(), paramResourceMeter);
/* 296 */     if (resourceMeter != null) {
/* 297 */       throw new IllegalArgumentException("ResourceType already added to meter: " + paramResourceMeter
/* 298 */           .getType().getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (paramResourceMeter.getType().equals(ResourceType.THREAD_CPU) || paramResourceMeter
/* 304 */       .getType().equals(ResourceType.HEAP_ALLOCATED)) {
/* 305 */       ThreadMetrics.init();
/*     */     }
/*     */     
/* 308 */     if (paramResourceMeter.getType().equals(ResourceType.HEAP_RETAINED))
/*     */     {
/* 310 */       HeapMetrics.init();
/*     */     }
/* 312 */     TotalResourceContext.validateMeter(paramResourceMeter.getType());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeResourceMeter(ResourceMeter paramResourceMeter) {
/* 317 */     ResourceMeter resourceMeter = this.meters.remove(paramResourceMeter.getType());
/* 318 */     if (resourceMeter != null) {
/* 319 */       TotalResourceContext totalResourceContext = TotalResourceContext.getTotalContext();
/*     */       
/* 321 */       TotalResourceContext.TotalMeter totalMeter = totalResourceContext.getMeter(resourceMeter.getType());
/* 322 */       totalMeter.addValue(resourceMeter.getValue());
/* 323 */       totalMeter.addAllocated(resourceMeter.getAllocated());
/*     */       
/* 325 */       return true;
/*     */     } 
/* 327 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceMeter getMeter(ResourceType paramResourceType) {
/* 333 */     return this.meters.get(paramResourceType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceMeter> meters() {
/* 338 */     return this.meters.entrySet().stream().map(paramEntry -> (ResourceMeter)paramEntry.getValue());
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
/*     */   public void requestAccurateUpdate(ResourceAccuracy paramResourceAccuracy) {
/* 360 */     Objects.requireNonNull(paramResourceAccuracy, "accuracy");
/* 361 */     if (!ResourceNatives.isHeapRetainedEnabled()) {
/* 362 */       throw new UnsupportedOperationException("ResourceType not supported by the current garbage collector: " + ResourceType.HEAP_RETAINED);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     int[] arrayOfInt = new int[1];
/* 369 */     arrayOfInt[0] = this.contextId;
/* 370 */     ResourceNatives.computeRetainedMemory(arrayOfInt, paramResourceAccuracy.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void illegalStateIfClosed() {
/* 379 */     if (this.closed) {
/* 380 */       throw new IllegalStateException("ResourceContext is closed: " + getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 386 */     StringJoiner stringJoiner = new StringJoiner("; ", this.name + "[", "]");
/* 387 */     this.meters.forEach((paramResourceType, paramResourceMeter) -> paramStringJoiner.add(paramResourceMeter.toString()));
/* 388 */     return stringJoiner.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\SimpleResourceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */