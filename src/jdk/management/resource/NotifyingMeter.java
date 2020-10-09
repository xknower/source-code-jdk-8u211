/*     */ package jdk.management.resource;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotifyingMeter
/*     */   extends SimpleMeter
/*     */ {
/*     */   private final ResourceApprover approver;
/*     */   private long granularity;
/*     */   
/*     */   public static NotifyingMeter create(ResourceType paramResourceType, ResourceApprover paramResourceApprover) {
/*  45 */     return new NotifyingMeter(paramResourceType, null, paramResourceApprover);
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
/*     */   public static NotifyingMeter create(ResourceType paramResourceType, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  58 */     return new NotifyingMeter(paramResourceType, paramResourceRequest, paramResourceApprover);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NotifyingMeter(ResourceType paramResourceType, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  69 */     super(paramResourceType, paramResourceRequest);
/*  70 */     this.approver = paramResourceApprover;
/*  71 */     this.granularity = 1L;
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
/*     */   protected long validate(long paramLong1, long paramLong2, ResourceId paramResourceId) {
/*  88 */     long l = paramLong2;
/*  89 */     if (this.approver != null) {
/*  90 */       long l1 = Math.floorDiv(paramLong1, this.granularity);
/*  91 */       long l2 = Math.floorDiv(paramLong1 + paramLong2, this.granularity);
/*  92 */       if (l1 != l2 || (paramLong2 == 0L && paramResourceId != null && paramResourceId instanceof ResourceIdImpl && ((ResourceIdImpl)paramResourceId)
/*     */         
/*  94 */         .isForcedUpdate())) {
/*  95 */         l = this.approver.request(this, paramLong1, paramLong2, paramResourceId);
/*  96 */         if (l != paramLong2 && l != 0L)
/*     */         {
/*  98 */           l = paramLong2;
/*     */         }
/*     */       } 
/*     */     } 
/* 102 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized long getGranularity() {
/* 111 */     return this.granularity;
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
/*     */   public final long setGranularity(long paramLong) {
/* 123 */     return setGranularityInternal(paramLong);
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
/*     */   synchronized long setGranularityInternal(long paramLong) {
/* 136 */     if (paramLong <= 0L) {
/* 137 */       throw new IllegalArgumentException("granularity must be greater than zero");
/*     */     }
/* 139 */     long l = this.granularity;
/* 140 */     this.granularity = paramLong;
/* 141 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ResourceApprover getApprover() {
/* 150 */     return this.approver;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\NotifyingMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */