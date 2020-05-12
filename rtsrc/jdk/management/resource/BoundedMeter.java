/*     */ package jdk.management.resource;
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
/*     */ public class BoundedMeter
/*     */   extends NotifyingMeter
/*     */   implements ResourceMeter, ResourceRequest
/*     */ {
/*     */   private volatile long bound;
/*     */   
/*     */   public static BoundedMeter create(ResourceType paramResourceType, long paramLong) {
/*  32 */     return create(paramResourceType, paramLong, (ResourceRequest)null, (ResourceApprover)null);
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
/*     */   public static BoundedMeter create(ResourceType paramResourceType, long paramLong, ResourceRequest paramResourceRequest) {
/*  45 */     return create(paramResourceType, paramLong, paramResourceRequest, (ResourceApprover)null);
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
/*     */   public static BoundedMeter create(ResourceType paramResourceType, long paramLong, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  61 */     return new BoundedMeter(paramResourceType, paramLong, paramResourceRequest, paramResourceApprover);
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
/*     */   public static BoundedMeter create(ResourceType paramResourceType, long paramLong, ResourceApprover paramResourceApprover) {
/*  75 */     return create(paramResourceType, paramLong, (ResourceRequest)null, paramResourceApprover);
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
/*     */   protected BoundedMeter(ResourceType paramResourceType, long paramLong, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  88 */     super(paramResourceType, paramResourceRequest, paramResourceApprover);
/*  89 */     if (paramLong < 0L) {
/*  90 */       throw new IllegalArgumentException("bound must be zero or greater");
/*     */     }
/*  92 */     this.bound = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long validate(long paramLong1, long paramLong2, ResourceId paramResourceId) {
/*  97 */     ResourceApprover resourceApprover = getApprover();
/*  98 */     long l = paramLong2;
/*  99 */     if (resourceApprover != null) {
/* 100 */       long l1 = getGranularity();
/* 101 */       long l2 = paramLong1 + paramLong2;
/* 102 */       long l3 = Math.floorDiv(paramLong1, l1);
/* 103 */       long l4 = Math.floorDiv(l2, l1);
/* 104 */       if (l3 != l4 || this.bound - l2 < 0L) {
/*     */         
/* 106 */         l = resourceApprover.request(this, paramLong1, paramLong2, paramResourceId);
/* 107 */         if (l != paramLong2 && l != 0L)
/*     */         {
/* 109 */           l = paramLong2;
/*     */         }
/*     */       } 
/*     */     } 
/* 113 */     if (this.bound - paramLong1 + l < 0L) {
/* 114 */       l = 0L;
/*     */     }
/* 116 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized long getBound() {
/* 125 */     return this.bound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized long setBound(long paramLong) {
/* 136 */     if (paramLong < 0L) {
/* 137 */       throw new IllegalArgumentException("bound must be zero or greater");
/*     */     }
/* 139 */     long l = this.bound;
/* 140 */     this.bound = paramLong;
/* 141 */     return l;
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
/*     */   synchronized long setGranularityInternal(long paramLong) {
/* 155 */     return super.setGranularityInternal(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     return super.toString() + "; bound: " + Long.toString(this.bound);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\BoundedMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */