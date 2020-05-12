/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractTask<P_IN, P_OUT, R, K extends AbstractTask<P_IN, P_OUT, R, K>>
/*     */   extends CountedCompleter<R>
/*     */ {
/*  97 */   static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final PipelineHelper<P_OUT> helper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Spliterator<P_IN> spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long targetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K leftChild;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K rightChild;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private R localResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTask(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/* 138 */     super((CountedCompleter<?>)null);
/* 139 */     this.helper = paramPipelineHelper;
/* 140 */     this.spliterator = paramSpliterator;
/* 141 */     this.targetSize = 0L;
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
/*     */   protected AbstractTask(K paramK, Spliterator<P_IN> paramSpliterator) {
/* 153 */     super((CountedCompleter<?>)paramK);
/* 154 */     this.spliterator = paramSpliterator;
/* 155 */     this.helper = ((AbstractTask)paramK).helper;
/* 156 */     this.targetSize = ((AbstractTask)paramK).targetSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract K makeChild(Spliterator<P_IN> paramSpliterator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract R doLeaf();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long suggestTargetSize(long paramLong) {
/* 184 */     long l = paramLong / LEAF_TARGET;
/* 185 */     return (l > 0L) ? l : 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long getTargetSize(long paramLong) {
/*     */     long l;
/* 194 */     return ((l = this.targetSize) != 0L) ? l : (this
/* 195 */       .targetSize = suggestTargetSize(paramLong));
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
/*     */   public R getRawResult() {
/* 209 */     return this.localResult;
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
/*     */   protected void setRawResult(R paramR) {
/* 222 */     if (paramR != null) {
/* 223 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected R getLocalResult() {
/* 233 */     return this.localResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocalResult(R paramR) {
/* 243 */     this.localResult = paramR;
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
/*     */   protected boolean isLeaf() {
/* 255 */     return (this.leftChild == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRoot() {
/* 264 */     return (getParent() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K getParent() {
/* 274 */     return (K)getCompleter();
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
/*     */   public void compute() {
/* 292 */     Spliterator<P_IN> spliterator1 = this.spliterator;
/* 293 */     long l1 = spliterator1.estimateSize();
/* 294 */     long l2 = getTargetSize(l1);
/* 295 */     boolean bool = false;
/* 296 */     Object object = this; Spliterator<P_IN> spliterator2;
/* 297 */     while (l1 > l2 && (spliterator2 = spliterator1.trySplit()) != null) {
/*     */       
/* 299 */       Object object3, object1 = object.makeChild(spliterator2);
/* 300 */       Object object2 = object.makeChild(spliterator1);
/* 301 */       object.setPendingCount(1);
/* 302 */       if (bool) {
/* 303 */         bool = false;
/* 304 */         spliterator1 = spliterator2;
/* 305 */         Object object4 = object1;
/* 306 */         object3 = object2;
/*     */       } else {
/*     */         
/* 309 */         bool = true;
/* 310 */         object = object2;
/* 311 */         object3 = object1;
/*     */       } 
/* 313 */       object3.fork();
/* 314 */       l1 = spliterator1.estimateSize();
/*     */     } 
/* 316 */     object.setLocalResult(object.doLeaf());
/* 317 */     object.tryComplete();
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
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter) {
/* 330 */     this.spliterator = null;
/* 331 */     this.leftChild = this.rightChild = null;
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
/*     */   protected boolean isLeftmostNode() {
/* 343 */     AbstractTask abstractTask = this;
/* 344 */     while (abstractTask != null) {
/* 345 */       Object object2 = abstractTask.getParent();
/* 346 */       if (object2 != null && ((AbstractTask)object2).leftChild != abstractTask)
/* 347 */         return false; 
/* 348 */       Object object1 = object2;
/*     */     } 
/* 350 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\AbstractTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */