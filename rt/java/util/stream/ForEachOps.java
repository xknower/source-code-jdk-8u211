/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ForEachOps
/*     */ {
/*     */   public static <T> TerminalOp<T, Void> makeRef(Consumer<? super T> paramConsumer, boolean paramBoolean) {
/*  72 */     Objects.requireNonNull(paramConsumer);
/*  73 */     return new ForEachOp.OfRef<>(paramConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Integer, Void> makeInt(IntConsumer paramIntConsumer, boolean paramBoolean) {
/*  87 */     Objects.requireNonNull(paramIntConsumer);
/*  88 */     return new ForEachOp.OfInt(paramIntConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Long, Void> makeLong(LongConsumer paramLongConsumer, boolean paramBoolean) {
/* 102 */     Objects.requireNonNull(paramLongConsumer);
/* 103 */     return new ForEachOp.OfLong(paramLongConsumer, paramBoolean);
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
/*     */   public static TerminalOp<Double, Void> makeDouble(DoubleConsumer paramDoubleConsumer, boolean paramBoolean) {
/* 117 */     Objects.requireNonNull(paramDoubleConsumer);
/* 118 */     return new ForEachOp.OfDouble(paramDoubleConsumer, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ForEachOp<T>
/*     */     implements TerminalOp<T, Void>, TerminalSink<T, Void>
/*     */   {
/*     */     private final boolean ordered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ForEachOp(boolean param1Boolean) {
/* 138 */       this.ordered = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOpFlags() {
/* 145 */       return this.ordered ? 0 : StreamOpFlag.NOT_ORDERED;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Void evaluateSequential(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator) {
/* 151 */       return ((ForEachOp)param1PipelineHelper.wrapAndCopyInto(this, param1Spliterator)).get();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Void evaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator) {
/* 157 */       if (this.ordered) {
/* 158 */         (new ForEachOps.ForEachOrderedTask<>(param1PipelineHelper, param1Spliterator, this)).invoke();
/*     */       } else {
/* 160 */         (new ForEachOps.ForEachTask<>(param1PipelineHelper, param1Spliterator, param1PipelineHelper.wrapSink(this))).invoke();
/* 161 */       }  return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Void get() {
/* 168 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     static final class OfRef<T>
/*     */       extends ForEachOp<T>
/*     */     {
/*     */       final Consumer<? super T> consumer;
/*     */       
/*     */       OfRef(Consumer<? super T> param2Consumer, boolean param2Boolean) {
/* 178 */         super(param2Boolean);
/* 179 */         this.consumer = param2Consumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(T param2T) {
/* 184 */         this.consumer.accept(param2T);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfInt
/*     */       extends ForEachOp<Integer>
/*     */       implements Sink.OfInt {
/*     */       final IntConsumer consumer;
/*     */       
/*     */       OfInt(IntConsumer param2IntConsumer, boolean param2Boolean) {
/* 194 */         super(param2Boolean);
/* 195 */         this.consumer = param2IntConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 200 */         return StreamShape.INT_VALUE;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(int param2Int) {
/* 205 */         this.consumer.accept(param2Int);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfLong
/*     */       extends ForEachOp<Long>
/*     */       implements Sink.OfLong {
/*     */       final LongConsumer consumer;
/*     */       
/*     */       OfLong(LongConsumer param2LongConsumer, boolean param2Boolean) {
/* 215 */         super(param2Boolean);
/* 216 */         this.consumer = param2LongConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 221 */         return StreamShape.LONG_VALUE;
/*     */       }
/*     */ 
/*     */       
/*     */       public void accept(long param2Long) {
/* 226 */         this.consumer.accept(param2Long);
/*     */       }
/*     */     }
/*     */     
/*     */     static final class OfDouble
/*     */       extends ForEachOp<Double>
/*     */       implements Sink.OfDouble {
/*     */       final DoubleConsumer consumer;
/*     */       
/*     */       OfDouble(DoubleConsumer param2DoubleConsumer, boolean param2Boolean) {
/* 236 */         super(param2Boolean);
/* 237 */         this.consumer = param2DoubleConsumer;
/*     */       }
/*     */ 
/*     */       
/*     */       public StreamShape inputShape() {
/* 242 */         return StreamShape.DOUBLE_VALUE;
/*     */       }
/*     */       
/*     */       public void accept(double param2Double)
/*     */       {
/* 247 */         this.consumer.accept(param2Double); } } } static final class OfRef<T> extends ForEachOp<T> { final Consumer<? super T> consumer; OfRef(Consumer<? super T> param1Consumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1Consumer; } public void accept(T param1T) { this.consumer.accept(param1T); } } static final class OfInt extends ForEachOp<Integer> implements Sink.OfInt { final IntConsumer consumer; OfInt(IntConsumer param1IntConsumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1IntConsumer; } public StreamShape inputShape() { return StreamShape.INT_VALUE; } public void accept(int param1Int) { this.consumer.accept(param1Int); } } static final class OfLong extends ForEachOp<Long> implements Sink.OfLong { final LongConsumer consumer; OfLong(LongConsumer param1LongConsumer, boolean param1Boolean) { super(param1Boolean); this.consumer = param1LongConsumer; } public StreamShape inputShape() { return StreamShape.LONG_VALUE; } public void accept(long param1Long) { this.consumer.accept(param1Long); } } static final class OfDouble extends ForEachOp<Double> implements Sink.OfDouble { public void accept(double param1Double) { this.consumer.accept(param1Double); }
/*     */     
/*     */     final DoubleConsumer consumer;
/*     */     OfDouble(DoubleConsumer param1DoubleConsumer, boolean param1Boolean) {
/*     */       super(param1Boolean);
/*     */       this.consumer = param1DoubleConsumer;
/*     */     }
/*     */     public StreamShape inputShape() {
/*     */       return StreamShape.DOUBLE_VALUE;
/*     */     } }
/*     */   static final class ForEachTask<S, T> extends CountedCompleter<Void> { private Spliterator<S> spliterator;
/*     */     private final Sink<S> sink;
/*     */     private final PipelineHelper<T> helper;
/*     */     private long targetSize;
/*     */     
/*     */     ForEachTask(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator, Sink<S> param1Sink) {
/* 263 */       super((CountedCompleter<?>)null);
/* 264 */       this.sink = param1Sink;
/* 265 */       this.helper = param1PipelineHelper;
/* 266 */       this.spliterator = param1Spliterator;
/* 267 */       this.targetSize = 0L;
/*     */     }
/*     */     
/*     */     ForEachTask(ForEachTask<S, T> param1ForEachTask, Spliterator<S> param1Spliterator) {
/* 271 */       super(param1ForEachTask);
/* 272 */       this.spliterator = param1Spliterator;
/* 273 */       this.sink = param1ForEachTask.sink;
/* 274 */       this.targetSize = param1ForEachTask.targetSize;
/* 275 */       this.helper = param1ForEachTask.helper;
/*     */     }
/*     */ 
/*     */     
/*     */     public void compute() {
/* 280 */       Spliterator<S> spliterator = this.spliterator;
/* 281 */       long l1 = spliterator.estimateSize(); long l2;
/* 282 */       if ((l2 = this.targetSize) == 0L)
/* 283 */         this.targetSize = l2 = AbstractTask.suggestTargetSize(l1); 
/* 284 */       boolean bool = StreamOpFlag.SHORT_CIRCUIT.isKnown(this.helper.getStreamAndOpFlags());
/* 285 */       boolean bool1 = false;
/* 286 */       Sink<S> sink = this.sink;
/* 287 */       ForEachTask<S, T> forEachTask = this;
/* 288 */       while (!bool || !sink.cancellationRequested()) {
/* 289 */         ForEachTask<S, T> forEachTask2; Spliterator<S> spliterator1; if (l1 <= l2 || (
/* 290 */           spliterator1 = spliterator.trySplit()) == null) {
/* 291 */           forEachTask.helper.copyInto(sink, spliterator);
/*     */           break;
/*     */         } 
/* 294 */         ForEachTask<S, T> forEachTask1 = new ForEachTask(forEachTask, spliterator1);
/* 295 */         forEachTask.addToPendingCount(1);
/*     */         
/* 297 */         if (bool1) {
/* 298 */           bool1 = false;
/* 299 */           spliterator = spliterator1;
/* 300 */           forEachTask2 = forEachTask;
/* 301 */           forEachTask = forEachTask1;
/*     */         } else {
/*     */           
/* 304 */           bool1 = true;
/* 305 */           forEachTask2 = forEachTask1;
/*     */         } 
/* 307 */         forEachTask2.fork();
/* 308 */         l1 = spliterator.estimateSize();
/*     */       } 
/* 310 */       forEachTask.spliterator = null;
/* 311 */       forEachTask.propagateCompletion();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ForEachOrderedTask<S, T>
/*     */     extends CountedCompleter<Void>
/*     */   {
/*     */     private final PipelineHelper<T> helper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Spliterator<S> spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final long targetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ConcurrentHashMap<ForEachOrderedTask<S, T>, ForEachOrderedTask<S, T>> completionMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Sink<T> action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ForEachOrderedTask<S, T> leftPredecessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Node<T> node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ForEachOrderedTask(PipelineHelper<T> param1PipelineHelper, Spliterator<S> param1Spliterator, Sink<T> param1Sink) {
/* 376 */       super((CountedCompleter<?>)null);
/* 377 */       this.helper = param1PipelineHelper;
/* 378 */       this.spliterator = param1Spliterator;
/* 379 */       this.targetSize = AbstractTask.suggestTargetSize(param1Spliterator.estimateSize());
/*     */       
/* 381 */       this.completionMap = new ConcurrentHashMap<>(Math.max(16, AbstractTask.LEAF_TARGET << 1));
/* 382 */       this.action = param1Sink;
/* 383 */       this.leftPredecessor = null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     ForEachOrderedTask(ForEachOrderedTask<S, T> param1ForEachOrderedTask1, Spliterator<S> param1Spliterator, ForEachOrderedTask<S, T> param1ForEachOrderedTask2) {
/* 389 */       super(param1ForEachOrderedTask1);
/* 390 */       this.helper = param1ForEachOrderedTask1.helper;
/* 391 */       this.spliterator = param1Spliterator;
/* 392 */       this.targetSize = param1ForEachOrderedTask1.targetSize;
/* 393 */       this.completionMap = param1ForEachOrderedTask1.completionMap;
/* 394 */       this.action = param1ForEachOrderedTask1.action;
/* 395 */       this.leftPredecessor = param1ForEachOrderedTask2;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void compute() {
/* 400 */       doCompute(this);
/*     */     }
/*     */     
/*     */     private static <S, T> void doCompute(ForEachOrderedTask<S, T> param1ForEachOrderedTask) {
/* 404 */       Spliterator<S> spliterator1 = param1ForEachOrderedTask.spliterator;
/* 405 */       long l = param1ForEachOrderedTask.targetSize;
/* 406 */       boolean bool = false; Spliterator<S> spliterator2;
/* 407 */       while (spliterator1.estimateSize() > l && (
/* 408 */         spliterator2 = spliterator1.trySplit()) != null) {
/* 409 */         ForEachOrderedTask<S, T> forEachOrderedTask3, forEachOrderedTask1 = new ForEachOrderedTask<>(param1ForEachOrderedTask, spliterator2, param1ForEachOrderedTask.leftPredecessor);
/*     */         
/* 411 */         ForEachOrderedTask<S, T> forEachOrderedTask2 = new ForEachOrderedTask<>(param1ForEachOrderedTask, spliterator1, forEachOrderedTask1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 417 */         param1ForEachOrderedTask.addToPendingCount(1);
/*     */ 
/*     */         
/* 420 */         forEachOrderedTask2.addToPendingCount(1);
/* 421 */         param1ForEachOrderedTask.completionMap.put(forEachOrderedTask1, forEachOrderedTask2);
/*     */ 
/*     */         
/* 424 */         if (param1ForEachOrderedTask.leftPredecessor != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 434 */           forEachOrderedTask1.addToPendingCount(1);
/*     */ 
/*     */           
/* 437 */           if (param1ForEachOrderedTask.completionMap.replace(param1ForEachOrderedTask.leftPredecessor, param1ForEachOrderedTask, forEachOrderedTask1)) {
/*     */ 
/*     */             
/* 440 */             param1ForEachOrderedTask.addToPendingCount(-1);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 445 */             forEachOrderedTask1.addToPendingCount(-1);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 450 */         if (bool) {
/* 451 */           bool = false;
/* 452 */           spliterator1 = spliterator2;
/* 453 */           param1ForEachOrderedTask = forEachOrderedTask1;
/* 454 */           forEachOrderedTask3 = forEachOrderedTask2;
/*     */         } else {
/*     */           
/* 457 */           bool = true;
/* 458 */           param1ForEachOrderedTask = forEachOrderedTask2;
/* 459 */           forEachOrderedTask3 = forEachOrderedTask1;
/*     */         } 
/* 461 */         forEachOrderedTask3.fork();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 472 */       if (param1ForEachOrderedTask.getPendingCount() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 476 */         IntFunction<T[]> intFunction = param1Int -> new Object[param1Int];
/* 477 */         Node.Builder<T> builder = param1ForEachOrderedTask.helper.makeNodeBuilder(param1ForEachOrderedTask.helper
/* 478 */             .exactOutputSizeIfKnown(spliterator1), intFunction);
/*     */         
/* 480 */         param1ForEachOrderedTask.node = ((Node.Builder<T>)param1ForEachOrderedTask.helper.wrapAndCopyInto(builder, spliterator1)).build();
/* 481 */         param1ForEachOrderedTask.spliterator = null;
/*     */       } 
/* 483 */       param1ForEachOrderedTask.tryComplete();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/* 488 */       if (this.node != null) {
/*     */         
/* 490 */         this.node.forEach(this.action);
/* 491 */         this.node = null;
/*     */       }
/* 493 */       else if (this.spliterator != null) {
/*     */         
/* 495 */         this.helper.wrapAndCopyInto(this.action, this.spliterator);
/* 496 */         this.spliterator = null;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 503 */       ForEachOrderedTask forEachOrderedTask = this.completionMap.remove(this);
/* 504 */       if (forEachOrderedTask != null)
/* 505 */         forEachOrderedTask.tryComplete(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\ForEachOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */