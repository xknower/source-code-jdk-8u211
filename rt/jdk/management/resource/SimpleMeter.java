/*     */ package jdk.management.resource;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import jdk.management.resource.internal.ResourceIdImpl;
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
/*     */ public class SimpleMeter
/*     */   implements ResourceMeter, ResourceRequest
/*     */ {
/*     */   private final ResourceType type;
/*     */   private final AtomicLong value;
/*     */   private final AtomicLong allocated;
/*     */   private final ResourceRequest parent;
/*     */   
/*     */   public static SimpleMeter create(ResourceType paramResourceType) {
/*  33 */     return new SimpleMeter(paramResourceType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleMeter create(ResourceType paramResourceType, ResourceRequest paramResourceRequest) {
/*  43 */     return new SimpleMeter(paramResourceType, paramResourceRequest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleMeter(ResourceType paramResourceType, ResourceRequest paramResourceRequest) {
/*  52 */     this.type = Objects.<ResourceType>requireNonNull(paramResourceType, "type");
/*  53 */     this.parent = paramResourceRequest;
/*  54 */     this.value = new AtomicLong();
/*  55 */     this.allocated = new AtomicLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getValue() {
/*  60 */     return this.value.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long getAllocated() {
/*  65 */     return this.allocated.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ResourceType getType() {
/*  70 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ResourceRequest getParent() {
/*  78 */     return this.parent;
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
/*     */   public final long request(long paramLong, ResourceId paramResourceId) {
/*  96 */     if (paramLong == 0L) {
/*  97 */       Object object = null;
/*  98 */       if (paramResourceId == null || !(paramResourceId instanceof ResourceIdImpl) || !((ResourceIdImpl)paramResourceId).isForcedUpdate()) {
/*  99 */         return 0L;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     long l = 0L;
/* 104 */     if (paramLong > 0L) {
/*     */       
/*     */       try {
/* 107 */         long l1 = this.value.getAndAdd(paramLong);
/* 108 */         l = validate(l1, paramLong, paramResourceId);
/*     */       } finally {
/*     */         
/* 111 */         long l1 = paramLong - l;
/* 112 */         if (l1 != 0L) {
/* 113 */           this.value.getAndAdd(-l1);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 118 */       long l1 = getValue();
/* 119 */       l = validate(l1, paramLong, paramResourceId);
/* 120 */       this.value.getAndAdd(l);
/*     */     } 
/*     */     
/* 123 */     if (this.parent != null) {
/* 124 */       long l1 = l;
/* 125 */       l = 0L;
/*     */       try {
/* 127 */         l = this.parent.request(l1, paramResourceId);
/*     */       } finally {
/*     */         
/* 130 */         long l2 = l1 - l;
/* 131 */         if (l2 != 0L) {
/* 132 */           this.value.getAndAdd(-l2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     if (l > 0L)
/*     */     {
/* 139 */       this.allocated.getAndAdd(l);
/*     */     }
/*     */     
/* 142 */     return l;
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
/*     */   protected long validate(long paramLong1, long paramLong2, ResourceId paramResourceId) throws ResourceRequestDeniedException {
/* 163 */     return paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 173 */     long l1 = this.value.get();
/* 174 */     long l2 = this.allocated.get();
/* 175 */     return this.type.toString() + ": " + Long.toString(l1) + "/" + l2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 185 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/* 196 */     return super.equals(paramObject);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\SimpleMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */