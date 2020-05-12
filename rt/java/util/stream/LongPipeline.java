/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LongSummaryStatistics;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.LongToDoubleFunction;
/*     */ import java.util.function.LongToIntFunction;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ import java.util.function.ObjLongConsumer;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class LongPipeline<E_IN>
/*     */   extends AbstractPipeline<E_IN, Long, LongStream>
/*     */   implements LongStream
/*     */ {
/*     */   LongPipeline(Supplier<? extends Spliterator<Long>> paramSupplier, int paramInt, boolean paramBoolean) {
/*  68 */     super(paramSupplier, paramInt, paramBoolean);
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
/*     */   LongPipeline(Spliterator<Long> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  81 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LongPipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/*  91 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LongConsumer adapt(Sink<Long> paramSink) {
/*  99 */     if (paramSink instanceof LongConsumer) {
/* 100 */       return (LongConsumer)paramSink;
/*     */     }
/* 102 */     if (Tripwire.ENABLED) {
/* 103 */       Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Sink<Long> s)");
/*     */     }
/* 105 */     return paramSink::accept;
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
/*     */   private static Spliterator.OfLong adapt(Spliterator<Long> paramSpliterator) {
/* 117 */     if (paramSpliterator instanceof Spliterator.OfLong) {
/* 118 */       return (Spliterator.OfLong)paramSpliterator;
/*     */     }
/* 120 */     if (Tripwire.ENABLED) {
/* 121 */       Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Spliterator<Long> s)");
/*     */     }
/* 123 */     throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 132 */     return StreamShape.LONG_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<Long> evaluateToNode(PipelineHelper<Long> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<Long[]> paramIntFunction) {
/* 140 */     return Nodes.collectLong(paramPipelineHelper, paramSpliterator, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<Long> wrap(PipelineHelper<Long> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 147 */     return new StreamSpliterators.LongWrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Spliterator.OfLong lazySpliterator(Supplier<? extends Spliterator<Long>> paramSupplier) {
/* 153 */     return new StreamSpliterators.DelegatingSpliterator.OfLong((Supplier)paramSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   final void forEachWithCancel(Spliterator<Long> paramSpliterator, Sink<Long> paramSink) {
/* 158 */     Spliterator.OfLong ofLong = adapt(paramSpliterator);
/* 159 */     LongConsumer longConsumer = adapt(paramSink); do {  }
/* 160 */     while (!paramSink.cancellationRequested() && ofLong.tryAdvance(longConsumer));
/*     */   }
/*     */ 
/*     */   
/*     */   final Node.Builder<Long> makeNodeBuilder(long paramLong, IntFunction<Long[]> paramIntFunction) {
/* 165 */     return Nodes.longBuilder(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrimitiveIterator.OfLong iterator() {
/* 173 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Spliterator.OfLong spliterator() {
/* 178 */     return adapt(super.spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream asDoubleStream() {
/* 185 */     return new DoublePipeline.StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 189 */           return new Sink.ChainedLong<Double>(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 192 */                 this.downstream.accept(param2Long);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<Long> boxed() {
/* 201 */     return mapToObj(Long::valueOf);
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream map(final LongUnaryOperator mapper) {
/* 206 */     Objects.requireNonNull(mapper);
/* 207 */     return new StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 211 */           return new Sink.ChainedLong<Long>(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 214 */                 this.downstream.accept(mapper.applyAsLong(param2Long));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <U> Stream<U> mapToObj(final LongFunction<? extends U> mapper) {
/* 223 */     Objects.requireNonNull(mapper);
/* 224 */     return new ReferencePipeline.StatelessOp<Long, U>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<U> param1Sink)
/*     */         {
/* 228 */           return new Sink.ChainedLong(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 231 */                 this.downstream.accept(mapper.apply(param2Long));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream mapToInt(final LongToIntFunction mapper) {
/* 240 */     Objects.requireNonNull(mapper);
/* 241 */     return new IntPipeline.StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 245 */           return new Sink.ChainedLong<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 248 */                 this.downstream.accept(mapper.applyAsInt(param2Long));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream mapToDouble(final LongToDoubleFunction mapper) {
/* 257 */     Objects.requireNonNull(mapper);
/* 258 */     return new DoublePipeline.StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 262 */           return new Sink.ChainedLong<Double>(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 265 */                 this.downstream.accept(mapper.applyAsDouble(param2Long));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream flatMap(final LongFunction<? extends LongStream> mapper) {
/* 274 */     return new StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 278 */           return new Sink.ChainedLong<Long>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 281 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(long param2Long) {
/* 286 */                 try (LongStream null = (LongStream)mapper.apply(param2Long)) {
/*     */                   
/* 288 */                   if (longStream != null) {
/* 289 */                     longStream.sequential().forEach(param2Long -> this.downstream.accept(param2Long));
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public LongStream unordered() {
/* 299 */     if (!isOrdered())
/* 300 */       return this; 
/* 301 */     return new StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink) {
/* 304 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream filter(final LongPredicate predicate) {
/* 311 */     Objects.requireNonNull(predicate);
/* 312 */     return new StatelessOp<Long>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 316 */           return new Sink.ChainedLong<Long>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 319 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(long param2Long) {
/* 324 */                 if (predicate.test(param2Long)) {
/* 325 */                   this.downstream.accept(param2Long);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final LongStream peek(final LongConsumer action) {
/* 334 */     Objects.requireNonNull(action);
/* 335 */     return new StatelessOp<Long>(this, StreamShape.LONG_VALUE, 0)
/*     */       {
/*     */         Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 339 */           return new Sink.ChainedLong<Long>(param1Sink)
/*     */             {
/*     */               public void accept(long param2Long) {
/* 342 */                 action.accept(param2Long);
/* 343 */                 this.downstream.accept(param2Long);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LongStream limit(long paramLong) {
/* 354 */     if (paramLong < 0L)
/* 355 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 356 */     return SliceOps.makeLong(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream skip(long paramLong) {
/* 361 */     if (paramLong < 0L)
/* 362 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 363 */     if (paramLong == 0L) {
/* 364 */       return this;
/*     */     }
/* 366 */     return SliceOps.makeLong(this, paramLong, -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream sorted() {
/* 371 */     return SortedOps.makeLong(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LongStream distinct() {
/* 378 */     return boxed().distinct().mapToLong(paramLong -> paramLong.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(LongConsumer paramLongConsumer) {
/* 385 */     evaluate(ForEachOps.makeLong(paramLongConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(LongConsumer paramLongConsumer) {
/* 390 */     evaluate(ForEachOps.makeLong(paramLongConsumer, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long sum() {
/* 396 */     return reduce(0L, Long::sum);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalLong min() {
/* 401 */     return reduce(Math::min);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalLong max() {
/* 406 */     return reduce(Math::max);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble average() {
/* 411 */     long[] arrayOfLong = collect(() -> new long[2], (paramArrayOflong, paramLong) -> {
/*     */           paramArrayOflong[0] = paramArrayOflong[0] + 1L;
/*     */           
/*     */           paramArrayOflong[1] = paramArrayOflong[1] + paramLong;
/*     */         }(paramArrayOflong1, paramArrayOflong2) -> {
/*     */           paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0];
/*     */           
/*     */           paramArrayOflong1[1] = paramArrayOflong1[1] + paramArrayOflong2[1];
/*     */         });
/* 420 */     return (arrayOfLong[0] > 0L) ? 
/* 421 */       OptionalDouble.of(arrayOfLong[1] / arrayOfLong[0]) : 
/* 422 */       OptionalDouble.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long count() {
/* 427 */     return map(paramLong -> 1L).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongSummaryStatistics summaryStatistics() {
/* 432 */     return collect(LongSummaryStatistics::new, LongSummaryStatistics::accept, LongSummaryStatistics::combine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long reduce(long paramLong, LongBinaryOperator paramLongBinaryOperator) {
/* 438 */     return ((Long)evaluate(ReduceOps.makeLong(paramLong, paramLongBinaryOperator))).longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalLong reduce(LongBinaryOperator paramLongBinaryOperator) {
/* 443 */     return (OptionalLong)evaluate(ReduceOps.makeLong(paramLongBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, ObjLongConsumer<R> paramObjLongConsumer, BiConsumer<R, R> paramBiConsumer) {
/* 450 */     BinaryOperator<R> binaryOperator = (paramObject1, paramObject2) -> {
/*     */         paramBiConsumer.accept(paramObject1, paramObject2);
/*     */         return paramObject1;
/*     */       };
/* 454 */     return (R)evaluate(ReduceOps.makeLong(paramSupplier, paramObjLongConsumer, binaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(LongPredicate paramLongPredicate) {
/* 459 */     return ((Boolean)evaluate(MatchOps.makeLong(paramLongPredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(LongPredicate paramLongPredicate) {
/* 464 */     return ((Boolean)evaluate(MatchOps.makeLong(paramLongPredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(LongPredicate paramLongPredicate) {
/* 469 */     return ((Boolean)evaluate(MatchOps.makeLong(paramLongPredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalLong findFirst() {
/* 474 */     return (OptionalLong)evaluate(FindOps.makeLong(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalLong findAny() {
/* 479 */     return (OptionalLong)evaluate(FindOps.makeLong(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final long[] toArray() {
/* 484 */     return Nodes.flattenLong((Node.OfLong)evaluateToArrayNode(paramInt -> new Long[paramInt]))
/* 485 */       .asPrimitiveArray();
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
/*     */   static class Head<E_IN>
/*     */     extends LongPipeline<E_IN>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<Long>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 509 */       super(param1Supplier, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Head(Spliterator<Long> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 522 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 527 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<Long> param1Sink) {
/* 532 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer param1LongConsumer) {
/* 539 */       if (!isParallel()) {
/* 540 */         LongPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1LongConsumer);
/*     */       } else {
/* 542 */         super.forEach(param1LongConsumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(LongConsumer param1LongConsumer) {
/* 548 */       if (!isParallel()) {
/* 549 */         LongPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1LongConsumer);
/*     */       } else {
/* 551 */         super.forEachOrdered(param1LongConsumer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class StatelessOp<E_IN>
/*     */     extends LongPipeline<E_IN>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 572 */       super(param1AbstractPipeline, param1Int);
/* 573 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 578 */       return false;
/*     */     }
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
/*     */   static abstract class StatefulOp<E_IN>
/*     */     extends LongPipeline<E_IN>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 599 */       super(param1AbstractPipeline, param1Int);
/* 600 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 605 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Long[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\LongPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */