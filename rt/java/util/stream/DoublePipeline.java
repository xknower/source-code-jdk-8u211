/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.DoubleSummaryStatistics;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoublePredicate;
/*     */ import java.util.function.DoubleToIntFunction;
/*     */ import java.util.function.DoubleToLongFunction;
/*     */ import java.util.function.DoubleUnaryOperator;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.ObjDoubleConsumer;
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
/*     */ abstract class DoublePipeline<E_IN>
/*     */   extends AbstractPipeline<E_IN, Double, DoubleStream>
/*     */   implements DoubleStream
/*     */ {
/*     */   DoublePipeline(Supplier<? extends Spliterator<Double>> paramSupplier, int paramInt, boolean paramBoolean) {
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
/*     */   DoublePipeline(Spliterator<Double> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  79 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DoublePipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/*  90 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DoubleConsumer adapt(Sink<Double> paramSink) {
/*  98 */     if (paramSink instanceof DoubleConsumer) {
/*  99 */       return (DoubleConsumer)paramSink;
/*     */     }
/* 101 */     if (Tripwire.ENABLED) {
/* 102 */       Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Sink<Double> s)");
/*     */     }
/* 104 */     return paramSink::accept;
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
/*     */   private static Spliterator.OfDouble adapt(Spliterator<Double> paramSpliterator) {
/* 116 */     if (paramSpliterator instanceof Spliterator.OfDouble) {
/* 117 */       return (Spliterator.OfDouble)paramSpliterator;
/*     */     }
/* 119 */     if (Tripwire.ENABLED) {
/* 120 */       Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Spliterator<Double> s)");
/*     */     }
/* 122 */     throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 131 */     return StreamShape.DOUBLE_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<Double> evaluateToNode(PipelineHelper<Double> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<Double[]> paramIntFunction) {
/* 139 */     return Nodes.collectDouble(paramPipelineHelper, paramSpliterator, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<Double> wrap(PipelineHelper<Double> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 146 */     return new StreamSpliterators.DoubleWrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final Spliterator.OfDouble lazySpliterator(Supplier<? extends Spliterator<Double>> paramSupplier) {
/* 152 */     return new StreamSpliterators.DelegatingSpliterator.OfDouble((Supplier)paramSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   final void forEachWithCancel(Spliterator<Double> paramSpliterator, Sink<Double> paramSink) {
/* 157 */     Spliterator.OfDouble ofDouble = adapt(paramSpliterator);
/* 158 */     DoubleConsumer doubleConsumer = adapt(paramSink); do {  }
/* 159 */     while (!paramSink.cancellationRequested() && ofDouble.tryAdvance(doubleConsumer));
/*     */   }
/*     */ 
/*     */   
/*     */   final Node.Builder<Double> makeNodeBuilder(long paramLong, IntFunction<Double[]> paramIntFunction) {
/* 164 */     return Nodes.doubleBuilder(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PrimitiveIterator.OfDouble iterator() {
/* 172 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Spliterator.OfDouble spliterator() {
/* 177 */     return adapt(super.spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Stream<Double> boxed() {
/* 184 */     return mapToObj(Double::valueOf);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream map(final DoubleUnaryOperator mapper) {
/* 189 */     Objects.requireNonNull(mapper);
/* 190 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 194 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 197 */                 this.downstream.accept(mapper.applyAsDouble(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <U> Stream<U> mapToObj(final DoubleFunction<? extends U> mapper) {
/* 206 */     Objects.requireNonNull(mapper);
/* 207 */     return new ReferencePipeline.StatelessOp<Double, U>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<U> param1Sink)
/*     */         {
/* 211 */           return new Sink.ChainedDouble(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 214 */                 this.downstream.accept(mapper.apply(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream mapToInt(final DoubleToIntFunction mapper) {
/* 223 */     Objects.requireNonNull(mapper);
/* 224 */     return new IntPipeline.StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 228 */           return new Sink.ChainedDouble<Integer>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 231 */                 this.downstream.accept(mapper.applyAsInt(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final DoubleToLongFunction mapper) {
/* 240 */     Objects.requireNonNull(mapper);
/* 241 */     return new LongPipeline.StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 245 */           return new Sink.ChainedDouble<Long>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 248 */                 this.downstream.accept(mapper.applyAsLong(param2Double));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream flatMap(final DoubleFunction<? extends DoubleStream> mapper) {
/* 257 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 261 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 264 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(double param2Double) {
/* 269 */                 try (DoubleStream null = (DoubleStream)mapper.apply(param2Double)) {
/*     */                   
/* 271 */                   if (doubleStream != null) {
/* 272 */                     doubleStream.sequential().forEach(param2Double -> this.downstream.accept(param2Double));
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleStream unordered() {
/* 282 */     if (!isOrdered())
/* 283 */       return this; 
/* 284 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 287 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream filter(final DoublePredicate predicate) {
/* 294 */     Objects.requireNonNull(predicate);
/* 295 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 299 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 302 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(double param2Double) {
/* 307 */                 if (predicate.test(param2Double)) {
/* 308 */                   this.downstream.accept(param2Double);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final DoubleStream peek(final DoubleConsumer action) {
/* 317 */     Objects.requireNonNull(action);
/* 318 */     return new StatelessOp<Double>(this, StreamShape.DOUBLE_VALUE, 0)
/*     */       {
/*     */         Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 322 */           return new Sink.ChainedDouble<Double>(param1Sink)
/*     */             {
/*     */               public void accept(double param2Double) {
/* 325 */                 action.accept(param2Double);
/* 326 */                 this.downstream.accept(param2Double);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream limit(long paramLong) {
/* 337 */     if (paramLong < 0L)
/* 338 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 339 */     return SliceOps.makeDouble(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream skip(long paramLong) {
/* 344 */     if (paramLong < 0L)
/* 345 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 346 */     if (paramLong == 0L) {
/* 347 */       return this;
/*     */     }
/* 349 */     long l = -1L;
/* 350 */     return SliceOps.makeDouble(this, paramLong, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream sorted() {
/* 356 */     return SortedOps.makeDouble(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DoubleStream distinct() {
/* 363 */     return boxed().distinct().mapToDouble(paramDouble -> paramDouble.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(DoubleConsumer paramDoubleConsumer) {
/* 370 */     evaluate(ForEachOps.makeDouble(paramDoubleConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(DoubleConsumer paramDoubleConsumer) {
/* 375 */     evaluate(ForEachOps.makeDouble(paramDoubleConsumer, true));
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
/*     */   public final double sum() {
/* 388 */     double[] arrayOfDouble = collect(() -> new double[3], (paramArrayOfdouble, paramDouble) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble, paramDouble);
/*     */           
/*     */           paramArrayOfdouble[2] = paramArrayOfdouble[2] + paramDouble;
/*     */         }(paramArrayOfdouble1, paramArrayOfdouble2) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]);
/*     */           
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]);
/*     */           
/*     */           paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2];
/*     */         });
/* 399 */     return Collectors.computeFinalSum(arrayOfDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble min() {
/* 404 */     return reduce(Math::min);
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble max() {
/* 409 */     return reduce(Math::max);
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
/*     */   public final OptionalDouble average() {
/* 430 */     double[] arrayOfDouble = collect(() -> new double[4], (paramArrayOfdouble, paramDouble) -> {
/*     */           paramArrayOfdouble[2] = paramArrayOfdouble[2] + 1.0D;
/*     */           
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble, paramDouble);
/*     */           
/*     */           paramArrayOfdouble[3] = paramArrayOfdouble[3] + paramDouble;
/*     */         }(paramArrayOfdouble1, paramArrayOfdouble2) -> {
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[0]);
/*     */           Collectors.sumWithCompensation(paramArrayOfdouble1, paramArrayOfdouble2[1]);
/*     */           paramArrayOfdouble1[2] = paramArrayOfdouble1[2] + paramArrayOfdouble2[2];
/*     */           paramArrayOfdouble1[3] = paramArrayOfdouble1[3] + paramArrayOfdouble2[3];
/*     */         });
/* 442 */     return (arrayOfDouble[2] > 0.0D) ? 
/* 443 */       OptionalDouble.of(Collectors.computeFinalSum(arrayOfDouble) / arrayOfDouble[2]) : 
/* 444 */       OptionalDouble.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long count() {
/* 449 */     return mapToLong(paramDouble -> 1L).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleSummaryStatistics summaryStatistics() {
/* 454 */     return collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final double reduce(double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 460 */     return ((Double)evaluate(ReduceOps.makeDouble(paramDouble, paramDoubleBinaryOperator))).doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble reduce(DoubleBinaryOperator paramDoubleBinaryOperator) {
/* 465 */     return (OptionalDouble)evaluate(ReduceOps.makeDouble(paramDoubleBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, ObjDoubleConsumer<R> paramObjDoubleConsumer, BiConsumer<R, R> paramBiConsumer) {
/* 472 */     BinaryOperator<R> binaryOperator = (paramObject1, paramObject2) -> {
/*     */         paramBiConsumer.accept(paramObject1, paramObject2);
/*     */         return paramObject1;
/*     */       };
/* 476 */     return (R)evaluate(ReduceOps.makeDouble(paramSupplier, paramObjDoubleConsumer, binaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(DoublePredicate paramDoublePredicate) {
/* 481 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(DoublePredicate paramDoublePredicate) {
/* 486 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(DoublePredicate paramDoublePredicate) {
/* 491 */     return ((Boolean)evaluate(MatchOps.makeDouble(paramDoublePredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble findFirst() {
/* 496 */     return (OptionalDouble)evaluate(FindOps.makeDouble(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final OptionalDouble findAny() {
/* 501 */     return (OptionalDouble)evaluate(FindOps.makeDouble(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final double[] toArray() {
/* 506 */     return Nodes.flattenDouble((Node.OfDouble)evaluateToArrayNode(paramInt -> new Double[paramInt]))
/* 507 */       .asPrimitiveArray();
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
/*     */   static class Head<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<Double>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 529 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<Double> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 542 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 547 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 552 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer param1DoubleConsumer) {
/* 559 */       if (!isParallel()) {
/* 560 */         DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1DoubleConsumer);
/*     */       } else {
/*     */         
/* 563 */         super.forEach(param1DoubleConsumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(DoubleConsumer param1DoubleConsumer) {
/* 569 */       if (!isParallel()) {
/* 570 */         DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(param1DoubleConsumer);
/*     */       } else {
/*     */         
/* 573 */         super.forEachOrdered(param1DoubleConsumer);
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
/*     */ 
/*     */   
/*     */   static abstract class StatelessOp<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 597 */       super(param1AbstractPipeline, param1Int);
/* 598 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 603 */       return false;
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
/*     */   
/*     */   static abstract class StatefulOp<E_IN>
/*     */     extends DoublePipeline<E_IN>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 625 */       super(param1AbstractPipeline, param1Int);
/* 626 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 631 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Double[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\DoublePipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */