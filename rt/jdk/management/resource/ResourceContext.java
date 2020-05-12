/*     */ package jdk.management.resource;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import jdk.management.resource.internal.SimpleResourceContext;
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
/*     */ public interface ResourceContext
/*     */   extends AutoCloseable
/*     */ {
/*     */   void close();
/*     */   
/*     */   String getName();
/*     */   
/*     */   default ResourceContext bindThreadContext() {
/*  68 */     throw new UnsupportedOperationException("bind not supported by " + getName());
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
/*     */   static ResourceContext unbindThreadContext() {
/*  84 */     return SimpleResourceContext.unbindThreadContext();
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
/*     */   default Stream<Thread> boundThreads() {
/* 100 */     throw new UnsupportedOperationException("boundThreads not supported by " + getName());
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
/*     */   ResourceRequest getResourceRequest(ResourceType paramResourceType);
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
/*     */   default void addResourceMeter(ResourceMeter paramResourceMeter) {
/* 128 */     throw new UnsupportedOperationException("addResourceMeter not supported by " + getName());
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
/*     */   default boolean removeResourceMeter(ResourceMeter paramResourceMeter) {
/* 142 */     throw new UnsupportedOperationException("removeResourceMeter not supported by " + getName());
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
/*     */   ResourceMeter getMeter(ResourceType paramResourceType);
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
/*     */   Stream<ResourceMeter> meters();
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
/*     */   default void requestAccurateUpdate(ResourceAccuracy paramResourceAccuracy) {
/* 179 */     throw new UnsupportedOperationException("requestAccurateUpdate not supported by " + getName());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ResourceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */