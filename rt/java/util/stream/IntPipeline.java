/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.IntSummaryStatistics;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ import java.util.function.IntToLongFunction;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import java.util.function.ObjIntConsumer;
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
/*     */ abstract class IntPipeline<E_IN>
/*     */   extends AbstractPipeline<E_IN, Integer, IntStream>
/*     */   implements IntStream
/*     */ {
/*     */   IntPipeline(Supplier<? extends Spliterator<Integer>> paramSupplier, int paramInt, boolean paramBoolean) {
/*  67 */     super(paramSupplier, paramInt, paramBoolean);
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
/*     */   IntPipeline(Spliterator<Integer> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  80 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IntPipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/*  91 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntConsumer adapt(Sink<Integer> paramSink) {
/*  99 */     if (paramSink instanceof IntConsumer) {
/* 100 */       return (IntConsumer)paramSink;
/*     */     }
/*     */     
/* 103 */     if (Tripwire.ENABLED) {
/* 104 */       Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Sink<Integer> s)");
/*     */     }
/* 106 */     return paramSink::accept;
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
/*     */   private static Spliterator.OfInt adapt(Spliterator<Integer> paramSpliterator) {
/* 118 */     if (paramSpliterator instanceof Spliterator.OfInt) {
/* 119 */       return (Spliterator.OfInt)paramSpliterator;
/*     */     }
/*     */     
/* 122 */     if (Tripwire.ENABLED) {
/* 123 */       Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Spliterator<Integer> s)");
/*     */     }
/* 125 */     throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 134 */     return StreamShape.INT_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<Integer> evaluateToNode(PipelineHelper<Integer> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<Integer[]> paramIntFunction) {
/* 142 */     return Nodes.collectInt(paramPipelineHelper, paramSpliterator, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<Integer> wrap(PipelineHelper<Integer> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 149 */     return new StreamSpliterators.IntWrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Spliterator.OfInt lazySpliterator(Supplier<? extends Spliterator<Integer>> paramSupplier) {
/* 155 */     return new StreamSpliterators.DelegatingSpliterator.OfInt((Supplier)paramSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   final void forEachWithCancel(Spliterator<Integer> paramSpliterator, Sink<Integer> paramSink) {
/* 160 */     Spliterator.OfInt ofInt = adapt(paramSpliterator);
/* 161 */     IntConsumer intConsumer = adapt(paramSink); do {  }
/* 162 */     while (!paramSink.cancellationRequested() && ofInt.tryAdvance(intConsumer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Node.Builder<Integer> makeNodeBuilder(long paramLong, IntFunction<Integer[]> paramIntFunction) {
/* 168 */     return Nodes.intBuilder(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrimitiveIterator.OfInt iterator() {
/* 176 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Spliterator.OfInt spliterator() {
/* 181 */     return adapt(super.spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LongStream asLongStream() {
/* 188 */     return new LongPipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 192 */           return new Sink.ChainedInt<Long>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 195 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream asDoubleStream() {
/* 204 */     return new DoublePipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 208 */           return new Sink.ChainedInt<Double>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 211 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<Integer> boxed() {
/* 220 */     return mapToObj(Integer::valueOf);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream map(final IntUnaryOperator mapper) {
/* 225 */     Objects.requireNonNull(mapper);
/* 226 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 230 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 233 */                 this.downstream.accept(mapper.applyAsInt(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <U> Stream<U> mapToObj(final IntFunction<? extends U> mapper) {
/* 242 */     Objects.requireNonNull(mapper);
/* 243 */     return new ReferencePipeline.StatelessOp<Integer, U>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<U> param1Sink)
/*     */         {
/* 247 */           return new Sink.ChainedInt(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 250 */                 this.downstream.accept(mapper.apply(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final IntToLongFunction mapper) {
/* 259 */     Objects.requireNonNull(mapper);
/* 260 */     return new LongPipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 264 */           return new Sink.ChainedInt<Long>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 267 */                 this.downstream.accept(mapper.applyAsLong(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream mapToDouble(final IntToDoubleFunction mapper) {
/* 276 */     Objects.requireNonNull(mapper);
/* 277 */     return new DoublePipeline.StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 281 */           return new Sink.ChainedInt<Double>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 284 */                 this.downstream.accept(mapper.applyAsDouble(param2Int));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream flatMap(final IntFunction<? extends IntStream> mapper) {
/* 293 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 297 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 300 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(int param2Int) {
/* 305 */                 try (IntStream null = (IntStream)mapper.apply(param2Int)) {
/*     */                   
/* 307 */                   if (intStream != null) {
/* 308 */                     intStream.sequential().forEach(param2Int -> this.downstream.accept(param2Int));
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntStream unordered() {
/* 318 */     if (!isOrdered())
/* 319 */       return this; 
/* 320 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 323 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream filter(final IntPredicate predicate) {
/* 330 */     Objects.requireNonNull(predicate);
/* 331 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 335 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 338 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(int param2Int) {
/* 343 */                 if (predicate.test(param2Int)) {
/* 344 */                   this.downstream.accept(param2Int);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final IntStream peek(final IntConsumer action) {
/* 353 */     Objects.requireNonNull(action);
/* 354 */     return new StatelessOp<Integer>(this, StreamShape.INT_VALUE, 0)
/*     */       {
/*     */         Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 358 */           return new Sink.ChainedInt<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(int param2Int) {
/* 361 */                 action.accept(param2Int);
/* 362 */                 this.downstream.accept(param2Int);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IntStream limit(long paramLong) {
/* 373 */     if (paramLong < 0L)
/* 374 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 375 */     return SliceOps.makeInt(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream skip(long paramLong) {
/* 380 */     if (paramLong < 0L)
/* 381 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 382 */     if (paramLong == 0L) {
/* 383 */       return this;
/*     */     }
/* 385 */     return SliceOps.makeInt(this, paramLong, -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream sorted() {
/* 390 */     return SortedOps.makeInt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IntStream distinct() {
/* 397 */     return boxed().distinct().mapToInt(paramInteger -> paramInteger.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(IntConsumer paramIntConsumer) {
/* 404 */     evaluate(ForEachOps.makeInt(paramIntConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(IntConsumer paramIntConsumer) {
/* 409 */     evaluate(ForEachOps.makeInt(paramIntConsumer, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int sum() {
/* 414 */     return reduce(0, Integer::sum);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt min() {
/* 419 */     return reduce(Math::min);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt max() {
/* 424 */     return reduce(Math::max);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long count() {
/* 429 */     return mapToLong(paramInt -> 1L).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble average() {
/* 434 */     long[] arrayOfLong = collect(() -> new long[2], (paramArrayOflong, paramInt) -> {
/*     */           paramArrayOflong[0] = paramArrayOflong[0] + 1L;
/*     */           
/*     */           paramArrayOflong[1] = paramArrayOflong[1] + paramInt;
/*     */         }(paramArrayOflong1, paramArrayOflong2) -> {
/*     */           paramArrayOflong1[0] = paramArrayOflong1[0] + paramArrayOflong2[0];
/*     */           
/*     */           paramArrayOflong1[1] = paramArrayOflong1[1] + paramArrayOflong2[1];
/*     */         });
/* 443 */     return (arrayOfLong[0] > 0L) ? 
/* 444 */       OptionalDouble.of(arrayOfLong[1] / arrayOfLong[0]) : 
/* 445 */       OptionalDouble.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntSummaryStatistics summaryStatistics() {
/* 450 */     return collect(IntSummaryStatistics::new, IntSummaryStatistics::accept, IntSummaryStatistics::combine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int reduce(int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/* 456 */     return ((Integer)evaluate(ReduceOps.makeInt(paramInt, paramIntBinaryOperator))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt reduce(IntBinaryOperator paramIntBinaryOperator) {
/* 461 */     return (OptionalInt)evaluate(ReduceOps.makeInt(paramIntBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, ObjIntConsumer<R> paramObjIntConsumer, BiConsumer<R, R> paramBiConsumer) {
/* 468 */     BinaryOperator<R> binaryOperator = (paramObject1, paramObject2) -> {
/*     */         paramBiConsumer.accept(paramObject1, paramObject2);
/*     */         return paramObject1;
/*     */       };
/* 472 */     return (R)evaluate(ReduceOps.makeInt(paramSupplier, paramObjIntConsumer, binaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(IntPredicate paramIntPredicate) {
/* 477 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(IntPredicate paramIntPredicate) {
/* 482 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(IntPredicate paramIntPredicate) {
/* 487 */     return ((Boolean)evaluate(MatchOps.makeInt(paramIntPredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt findFirst() {
/* 492 */     return (OptionalInt)evaluate(FindOps.makeInt(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalInt findAny() {
/* 497 */     return (OptionalInt)evaluate(FindOps.makeInt(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] toArray() {
/* 502 */     return Nodes.flattenInt((Node.OfInt)evaluateToArrayNode(paramInt -> new Integer[paramInt]))
/* 503 */       .asPrimitiveArray();
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
/*     */   static class Head<E_IN>
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<Integer>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 526 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<Integer> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 539 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 544 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 549 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer param1IntConsumer) {
/* 556 */       if (!isParallel()) {
/* 557 */         IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1IntConsumer);
/*     */       } else {
/*     */         
/* 560 */         super.forEach(param1IntConsumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(IntConsumer param1IntConsumer) {
/* 566 */       if (!isParallel()) {
/* 567 */         IntPipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1IntConsumer);
/*     */       } else {
/*     */         
/* 570 */         super.forEachOrdered(param1IntConsumer);
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
/*     */   
/*     */   static abstract class StatelessOp<E_IN>
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 592 */       super(param1AbstractPipeline, param1Int);
/* 593 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 598 */       return false;
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
/*     */     extends IntPipeline<E_IN>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 619 */       super(param1AbstractPipeline, param1Int);
/* 620 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 625 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Integer[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\IntPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */