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
/*     */ 
/*     */ public class ThrottledMeter
/*     */   extends NotifyingMeter
/*     */ {
/*     */   private volatile long ratePerSec;
/*     */   private final Object mutex;
/*     */   private long availableBytes;
/*     */   private long availableTimestamp;
/*     */   
/*     */   public static ThrottledMeter create(ResourceType paramResourceType, long paramLong, ResourceApprover paramResourceApprover) {
/*  35 */     return new ThrottledMeter(paramResourceType, paramLong, null, paramResourceApprover);
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
/*     */   public static ThrottledMeter create(ResourceType paramResourceType, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  50 */     return new ThrottledMeter(paramResourceType, Long.MAX_VALUE, paramResourceRequest, paramResourceApprover);
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
/*     */   public static ThrottledMeter create(ResourceType paramResourceType, long paramLong, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  67 */     return new ThrottledMeter(paramResourceType, paramLong, paramResourceRequest, paramResourceApprover);
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
/*     */   ThrottledMeter(ResourceType paramResourceType, long paramLong, ResourceRequest paramResourceRequest, ResourceApprover paramResourceApprover) {
/*  83 */     super(paramResourceType, paramResourceRequest, paramResourceApprover);
/*  84 */     if (paramLong <= 0L) {
/*  85 */       throw new IllegalArgumentException("ratePerSec must be greater than zero");
/*     */     }
/*  87 */     this.ratePerSec = paramLong;
/*  88 */     this.mutex = new Object();
/*  89 */     this.availableBytes = 0L;
/*  90 */     this.availableTimestamp = 0L;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long validate(long paramLong1, long paramLong2, ResourceId paramResourceId) {
/* 119 */     long l = super.validate(paramLong1, paramLong2, paramResourceId);
/* 120 */     if (l <= 0L) {
/* 121 */       return l;
/*     */     }
/*     */     
/* 124 */     synchronized (this.mutex) {
/* 125 */       while (this.availableBytes - paramLong2 < 0L) {
/* 126 */         long l1 = this.ratePerSec;
/* 127 */         long l2 = this.availableBytes;
/* 128 */         long l3 = System.currentTimeMillis();
/* 129 */         long l4 = Math.max(l3 - this.availableTimestamp, 0L);
/*     */         
/* 131 */         long l5 = l1 * l4 / 1000L;
/* 132 */         this.availableBytes = Math.min(this.availableBytes + l5, l1);
/* 133 */         this.availableTimestamp = l3;
/*     */         
/* 135 */         if (this.availableBytes - paramLong2 >= 0L) {
/*     */           break;
/*     */         }
/*     */         
/* 139 */         if (paramLong2 > l1 && l2 > 0L) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 145 */         long l6 = Math.min(paramLong2 - this.availableBytes, l1);
/* 146 */         l4 = l6 * 1000L / l1;
/*     */         try {
/* 148 */           this.mutex.wait(Math.max(l4, 10L));
/* 149 */         } catch (InterruptedException interruptedException) {
/* 150 */           return 0L;
/*     */         } 
/*     */       } 
/* 153 */       this.availableBytes -= paramLong2;
/*     */     } 
/* 155 */     return paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getCurrentRate() {
/* 165 */     synchronized (this.mutex) {
/* 166 */       long l1 = this.ratePerSec;
/* 167 */       long l2 = System.currentTimeMillis();
/* 168 */       long l3 = l2 - this.availableTimestamp;
/*     */       
/* 170 */       long l4 = l1 * l3 / 1000L;
/* 171 */       this.availableBytes = Math.min(this.availableBytes + l4, l1);
/* 172 */       this.availableTimestamp = l2;
/*     */ 
/*     */       
/* 175 */       return l1 - this.availableBytes;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized long getRatePerSec() {
/* 186 */     return this.ratePerSec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized long setRatePerSec(long paramLong) {
/* 197 */     if (paramLong <= 0L) {
/* 198 */       throw new IllegalArgumentException("ratePerSec must be greater than zero");
/*     */     }
/* 200 */     long l = paramLong;
/* 201 */     this.ratePerSec = paramLong;
/* 202 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     return super.toString() + "; ratePerSec: " + 
/* 208 */       Long.toString(this.ratePerSec) + "; currentRate: " + 
/* 209 */       Long.toString(getCurrentRate());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jdk\management\resource\ThrottledMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */