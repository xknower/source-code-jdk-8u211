/*     */ package jdk.management.resource.internal;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.StringJoiner;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.stream.Stream;
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
/*     */ public class TotalResourceContext
/*     */   implements ResourceContext
/*     */ {
/*  23 */   private static final TotalResourceContext totalContext = new TotalResourceContext("Total");
/*     */   
/*  25 */   final ConcurrentHashMap<ResourceType, TotalMeter> totalMeters = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private TotalResourceContext(String paramString) {
/*  33 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  38 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TotalResourceContext getTotalContext() {
/*  48 */     return totalContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceRequest getResourceRequest(ResourceType paramResourceType) {
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TotalMeter getMeter(ResourceType paramResourceType) {
/*  65 */     return this.totalMeters.get(paramResourceType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateMeter(ResourceType paramResourceType) {
/*  75 */     totalContext.totalMeters.computeIfAbsent(paramResourceType, paramResourceType -> new TotalMeter(paramResourceType, 0L, 0L));
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceMeter> meters() {
/*  80 */     return this.totalMeters.entrySet().stream().map(paramEntry -> (TotalMeter)paramEntry.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     StringJoiner stringJoiner = new StringJoiner("; ", this.name + "[", "]");
/*  86 */     meters().forEach(paramResourceMeter -> paramStringJoiner.add(paramResourceMeter.toString()));
/*  87 */     return stringJoiner.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class TotalMeter
/*     */     implements ResourceMeter
/*     */   {
/*     */     private final ResourceType type;
/*     */ 
/*     */     
/*     */     private long value;
/*     */ 
/*     */     
/*     */     private long allocated;
/*     */ 
/*     */ 
/*     */     
/*     */     TotalMeter(ResourceType param1ResourceType, long param1Long1, long param1Long2) {
/* 106 */       this.type = param1ResourceType;
/* 107 */       this.value = param1Long1;
/* 108 */       this.allocated = param1Long2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void addValue(long param1Long) {
/* 117 */       this.value += param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void addAllocated(long param1Long) {
/* 126 */       this.allocated += param1Long;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getValue() {
/* 131 */       long l = 0L;
/* 132 */       synchronized (this) {
/* 133 */         l += this.value;
/*     */       } 
/* 135 */       l += SimpleResourceContext.getContexts().reduceValuesToLong(1000L, param1SimpleResourceContext -> { ResourceMeter resourceMeter = param1SimpleResourceContext.getMeter(this.type); return (resourceMeter == null) ? 0L : resourceMeter.getValue(); }0L, (param1Long1, param1Long2) -> param1Long1 + param1Long2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getAllocated() {
/* 148 */       long l = 0L;
/* 149 */       synchronized (this) {
/* 150 */         l += this.allocated;
/*     */       } 
/* 152 */       l += SimpleResourceContext.getContexts().reduceValuesToLong(1000L, param1SimpleResourceContext -> { ResourceMeter resourceMeter = param1SimpleResourceContext.getMeter(this.type); return (resourceMeter == null) ? 0L : resourceMeter.getAllocated(); }0L, (param1Long1, param1Long2) -> param1Long1 + param1Long2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceType getType() {
/* 164 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 169 */       return this.type.toString() + ": " + Long.toString(getValue()) + "/" + Long.toString(getAllocated());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\internal\TotalResourceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */