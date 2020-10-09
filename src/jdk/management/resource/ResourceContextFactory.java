/*     */ package jdk.management.resource;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.stream.Stream;
/*     */ import jdk.management.resource.internal.ResourceNatives;
/*     */ import jdk.management.resource.internal.SimpleResourceContext;
/*     */ import jdk.management.resource.internal.TotalResourceContext;
/*     */ import jdk.management.resource.internal.UnassignedContext;
/*     */ import jdk.management.resource.internal.WrapInstrumentation;
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
/*     */ public final class ResourceContextFactory
/*     */ {
/*  54 */   private static final ResourceContextFactory instance = new ResourceContextFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ResourceContext unassigned;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static Set<ResourceType> supportedResourceTypes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean initialized = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceContextFactory() {
/*  77 */     this.unassigned = ResourceNatives.isEnabled() ? UnassignedContext.getUnassignedContext() : null;
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
/*     */   public static boolean isEnabled() {
/*  90 */     SecurityManager securityManager = System.getSecurityManager();
/*  91 */     if (securityManager != null) {
/*  92 */       securityManager.checkPermission(new RuntimePermission("jdk.management.resource.getResourceContextFactory"));
/*     */     }
/*     */     
/*  95 */     return ResourceNatives.isEnabled();
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
/*     */   public static ResourceContextFactory getInstance() {
/* 107 */     SecurityManager securityManager = System.getSecurityManager();
/* 108 */     if (securityManager != null) {
/* 109 */       securityManager.checkPermission(new RuntimePermission("jdk.management.resource.getResourceContextFactory"));
/*     */     }
/*     */     
/* 112 */     if (!ResourceNatives.isEnabled()) {
/* 113 */       throw new UnsupportedOperationException("Resource management is not enabled");
/*     */     }
/*     */     
/* 116 */     instance.initInstrumentation();
/*     */     
/* 118 */     return instance;
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
/*     */   private synchronized void initInstrumentation() throws InternalError {
/* 130 */     if (!this.initialized) {
/*     */       
/* 132 */       ThreadLocalRandom.current();
/* 133 */       new SecureRandom();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 138 */         Class<?> clazz = Class.forName("jdk.management.resource.internal.inst.InitInstrumentation");
/* 139 */         Runnable runnable = (Runnable)clazz.newInstance();
/* 140 */         runnable.run();
/* 141 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */       
/* 144 */       } catch (IllegalAccessException|InstantiationException illegalAccessException) {
/* 145 */         throw new InternalError("Resource management instrumentation failed", illegalAccessException);
/*     */       } 
/*     */       
/* 148 */       if (!(new WrapInstrumentation()).wrapComplete()) {
/* 149 */         throw new InternalError("Resource management instrumentation failed");
/*     */       }
/* 151 */       initPreBoundThreads();
/* 152 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initPreBoundThreads() {
/* 162 */     AccessController.doPrivileged(() -> {
/*     */           ThreadGroup threadGroup;
/*     */           for (threadGroup = Thread.currentThread().getThreadGroup(); threadGroup.getParent() != null; threadGroup = threadGroup.getParent());
/*     */           Thread[] arrayOfThread = new Thread[threadGroup.activeCount() * 2];
/*     */           int i = threadGroup.enumerate(arrayOfThread, true);
/*     */           for (byte b = 0; b < i; b++) {
/*     */             if (arrayOfThread[b] != null) {
/*     */               UnassignedContext unassignedContext = arrayOfThread[b].getThreadGroup().equals(threadGroup) ? UnassignedContext.getSystemContext() : UnassignedContext.getUnassignedContext();
/*     */               unassignedContext.bindThreadContext(arrayOfThread[b]);
/*     */             } 
/*     */           } 
/*     */           return null;
/*     */         });
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
/*     */ 
/*     */   
/*     */   public ResourceContext create(String paramString) {
/* 198 */     return SimpleResourceContext.create(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceContext lookup(String paramString) {
/* 208 */     return SimpleResourceContext.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceContext getThreadContext() {
/* 219 */     return getThreadContext(Thread.currentThread());
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
/*     */   public ResourceContext getThreadContext(Thread paramThread) {
/* 231 */     return SimpleResourceContext.getThreadContext(paramThread);
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
/*     */   public ResourceRequest getResourceRequest(ResourceType paramResourceType) {
/* 244 */     return getThreadContext().getResourceRequest(paramResourceType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceContext getUnassignedContext() {
/* 254 */     return this.unassigned;
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
/*     */   public ResourceContext getTotalsContext() {
/* 267 */     return TotalResourceContext.getTotalContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<ResourceContext> contexts() {
/* 276 */     return SimpleResourceContext.contexts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<ResourceType> supportedResourceTypes() {
/* 284 */     synchronized (this) {
/* 285 */       if (supportedResourceTypes == null) {
/* 286 */         Set<ResourceType> set = ResourceType.builtinTypes();
/* 287 */         if (!ResourceNatives.isHeapRetainedEnabled()) {
/* 288 */           set.remove(ResourceType.HEAP_RETAINED);
/*     */         }
/*     */         
/* 291 */         supportedResourceTypes = Collections.unmodifiableSet(set);
/*     */       } 
/*     */       
/* 294 */       return supportedResourceTypes;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ResourceContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */