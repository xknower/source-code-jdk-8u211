/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.function.ToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ReferencePipeline<P_IN, P_OUT>
/*     */   extends AbstractPipeline<P_IN, P_OUT, Stream<P_OUT>>
/*     */   implements Stream<P_OUT>
/*     */ {
/*     */   ReferencePipeline(Supplier<? extends Spliterator<?>> paramSupplier, int paramInt, boolean paramBoolean) {
/*  71 */     super(paramSupplier, paramInt, paramBoolean);
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
/*     */   ReferencePipeline(Spliterator<?> paramSpliterator, int paramInt, boolean paramBoolean) {
/*  84 */     super(paramSpliterator, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReferencePipeline(AbstractPipeline<?, P_IN, ?> paramAbstractPipeline, int paramInt) {
/*  94 */     super(paramAbstractPipeline, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getOutputShape() {
/* 101 */     return StreamShape.REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<P_OUT> evaluateToNode(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<P_OUT[]> paramIntFunction) {
/* 109 */     return Nodes.collect(paramPipelineHelper, paramSpliterator, paramBoolean, paramIntFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<P_OUT> wrap(PipelineHelper<P_OUT> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean) {
/* 116 */     return new StreamSpliterators.WrappingSpliterator<>(paramPipelineHelper, paramSupplier, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   final Spliterator<P_OUT> lazySpliterator(Supplier<? extends Spliterator<P_OUT>> paramSupplier) {
/* 121 */     return new StreamSpliterators.DelegatingSpliterator<>(paramSupplier);
/*     */   }
/*     */   final void forEachWithCancel(Spliterator<P_OUT> paramSpliterator, Sink<P_OUT> paramSink) {
/*     */     do {
/*     */     
/* 126 */     } while (!paramSink.cancellationRequested() && paramSpliterator.tryAdvance(paramSink));
/*     */   }
/*     */ 
/*     */   
/*     */   final Node.Builder<P_OUT> makeNodeBuilder(long paramLong, IntFunction<P_OUT[]> paramIntFunction) {
/* 131 */     return Nodes.builder(paramLong, paramIntFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Iterator<P_OUT> iterator() {
/* 139 */     return Spliterators.iterator(spliterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<P_OUT> unordered() {
/* 149 */     if (!isOrdered())
/* 150 */       return this; 
/* 151 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_ORDERED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink) {
/* 154 */           return param1Sink;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> filter(final Predicate<? super P_OUT> predicate) {
/* 161 */     Objects.requireNonNull(predicate);
/* 162 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink)
/*     */         {
/* 166 */           return new Sink.ChainedReference<P_OUT, P_OUT>(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 169 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 174 */                 if (predicate.test(param2P_OUT)) {
/* 175 */                   this.downstream.accept(param2P_OUT);
/*     */                 }
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> Stream<R> map(final Function<? super P_OUT, ? extends R> mapper) {
/* 185 */     Objects.requireNonNull(mapper);
/* 186 */     return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<R> param1Sink)
/*     */         {
/* 190 */           return new Sink.ChainedReference(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 193 */                 this.downstream.accept(mapper.apply(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final IntStream mapToInt(final ToIntFunction<? super P_OUT> mapper) {
/* 202 */     Objects.requireNonNull(mapper);
/* 203 */     return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 207 */           return new Sink.ChainedReference<P_OUT, Integer>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 210 */                 this.downstream.accept(mapper.applyAsInt(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final LongStream mapToLong(final ToLongFunction<? super P_OUT> mapper) {
/* 219 */     Objects.requireNonNull(mapper);
/* 220 */     return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 224 */           return new Sink.ChainedReference<P_OUT, Long>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 227 */                 this.downstream.accept(mapper.applyAsLong(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final DoubleStream mapToDouble(final ToDoubleFunction<? super P_OUT> mapper) {
/* 236 */     Objects.requireNonNull(mapper);
/* 237 */     return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 241 */           return new Sink.ChainedReference<P_OUT, Double>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 244 */                 this.downstream.accept(mapper.applyAsDouble(param2P_OUT));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> Stream<R> flatMap(final Function<? super P_OUT, ? extends Stream<? extends R>> mapper) {
/* 253 */     Objects.requireNonNull(mapper);
/*     */     
/* 255 */     return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<R> param1Sink)
/*     */         {
/* 259 */           return new Sink.ChainedReference(param1Sink)
/*     */             {
/*     */               public void begin(long param2Long) {
/* 262 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 267 */                 try (Stream<R> null = (Stream)mapper.apply(param2P_OUT)) {
/*     */                   
/* 269 */                   if (stream != null) {
/* 270 */                     stream.sequential().forEach(this.downstream);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final IntStream flatMapToInt(final Function<? super P_OUT, ? extends IntStream> mapper) {
/* 280 */     Objects.requireNonNull(mapper);
/*     */     
/* 282 */     return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Integer> param1Sink)
/*     */         {
/* 286 */           return new Sink.ChainedReference<P_OUT, Integer>(param1Sink) {
/* 287 */               IntConsumer downstreamAsInt = this.downstream::accept;
/*     */               
/*     */               public void begin(long param2Long) {
/* 290 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 295 */                 try (IntStream null = (IntStream)mapper.apply(param2P_OUT)) {
/*     */                   
/* 297 */                   if (intStream != null) {
/* 298 */                     intStream.sequential().forEach(this.downstreamAsInt);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final DoubleStream flatMapToDouble(final Function<? super P_OUT, ? extends DoubleStream> mapper) {
/* 308 */     Objects.requireNonNull(mapper);
/*     */     
/* 310 */     return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Double> param1Sink)
/*     */         {
/* 314 */           return new Sink.ChainedReference<P_OUT, Double>(param1Sink) {
/* 315 */               DoubleConsumer downstreamAsDouble = this.downstream::accept;
/*     */               
/*     */               public void begin(long param2Long) {
/* 318 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 323 */                 try (DoubleStream null = (DoubleStream)mapper.apply(param2P_OUT)) {
/*     */                   
/* 325 */                   if (doubleStream != null) {
/* 326 */                     doubleStream.sequential().forEach(this.downstreamAsDouble);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final LongStream flatMapToLong(final Function<? super P_OUT, ? extends LongStream> mapper) {
/* 336 */     Objects.requireNonNull(mapper);
/*     */     
/* 338 */     return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<Long> param1Sink)
/*     */         {
/* 342 */           return new Sink.ChainedReference<P_OUT, Long>(param1Sink) {
/* 343 */               LongConsumer downstreamAsLong = this.downstream::accept;
/*     */               
/*     */               public void begin(long param2Long) {
/* 346 */                 this.downstream.begin(-1L);
/*     */               }
/*     */ 
/*     */               
/*     */               public void accept(P_OUT param2P_OUT) {
/* 351 */                 try (LongStream null = (LongStream)mapper.apply(param2P_OUT)) {
/*     */                   
/* 353 */                   if (longStream != null) {
/* 354 */                     longStream.sequential().forEach(this.downstreamAsLong);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final Stream<P_OUT> peek(final Consumer<? super P_OUT> action) {
/* 364 */     Objects.requireNonNull(action);
/* 365 */     return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, 0)
/*     */       {
/*     */         Sink<P_OUT> opWrapSink(int param1Int, Sink<P_OUT> param1Sink)
/*     */         {
/* 369 */           return new Sink.ChainedReference<P_OUT, P_OUT>(param1Sink)
/*     */             {
/*     */               public void accept(P_OUT param2P_OUT) {
/* 372 */                 action.accept(param2P_OUT);
/* 373 */                 this.downstream.accept(param2P_OUT);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> distinct() {
/* 384 */     return DistinctOps.makeRef(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> sorted() {
/* 389 */     return SortedOps.makeRef(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> sorted(Comparator<? super P_OUT> paramComparator) {
/* 394 */     return SortedOps.makeRef(this, paramComparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> limit(long paramLong) {
/* 399 */     if (paramLong < 0L)
/* 400 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 401 */     return SliceOps.makeRef(this, 0L, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Stream<P_OUT> skip(long paramLong) {
/* 406 */     if (paramLong < 0L)
/* 407 */       throw new IllegalArgumentException(Long.toString(paramLong)); 
/* 408 */     if (paramLong == 0L) {
/* 409 */       return this;
/*     */     }
/* 411 */     return SliceOps.makeRef(this, paramLong, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super P_OUT> paramConsumer) {
/* 418 */     evaluate(ForEachOps.makeRef(paramConsumer, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachOrdered(Consumer<? super P_OUT> paramConsumer) {
/* 423 */     evaluate(ForEachOps.makeRef(paramConsumer, true));
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
/*     */   public final <A> A[] toArray(IntFunction<A[]> paramIntFunction) {
/* 437 */     IntFunction<A[]> intFunction = paramIntFunction;
/* 438 */     return (A[])Nodes.<Object>flatten(evaluateToArrayNode(intFunction), (IntFunction)intFunction)
/* 439 */       .asArray((IntFunction)intFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object[] toArray() {
/* 444 */     return toArray(paramInt -> new Object[paramInt]);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean anyMatch(Predicate<? super P_OUT> paramPredicate) {
/* 449 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.ANY))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean allMatch(Predicate<? super P_OUT> paramPredicate) {
/* 454 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.ALL))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean noneMatch(Predicate<? super P_OUT> paramPredicate) {
/* 459 */     return ((Boolean)evaluate(MatchOps.makeRef(paramPredicate, MatchOps.MatchKind.NONE))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> findFirst() {
/* 464 */     return (Optional<P_OUT>)evaluate(FindOps.makeRef(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> findAny() {
/* 469 */     return (Optional<P_OUT>)evaluate(FindOps.makeRef(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public final P_OUT reduce(P_OUT paramP_OUT, BinaryOperator<P_OUT> paramBinaryOperator) {
/* 474 */     return (P_OUT)evaluate(ReduceOps.makeRef(paramP_OUT, paramBinaryOperator, paramBinaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> reduce(BinaryOperator<P_OUT> paramBinaryOperator) {
/* 479 */     return (Optional<P_OUT>)evaluate(ReduceOps.makeRef(paramBinaryOperator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final <R> R reduce(R paramR, BiFunction<R, ? super P_OUT, R> paramBiFunction, BinaryOperator<R> paramBinaryOperator) {
/* 484 */     return (R)evaluate(ReduceOps.makeRef(paramR, paramBiFunction, paramBinaryOperator));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R, A> R collect(Collector<? super P_OUT, A, R> paramCollector) {
/*     */     Object object;
/* 491 */     if (isParallel() && paramCollector
/* 492 */       .characteristics().contains(Collector.Characteristics.CONCURRENT) && (
/* 493 */       !isOrdered() || paramCollector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
/* 494 */       object = paramCollector.supplier().get();
/* 495 */       BiConsumer<A, ? super P_OUT> biConsumer = paramCollector.accumulator();
/* 496 */       forEach(paramObject2 -> paramBiConsumer.accept(paramObject1, paramObject2));
/*     */     } else {
/*     */       
/* 499 */       object = evaluate(ReduceOps.makeRef(paramCollector));
/*     */     } 
/* 501 */     return paramCollector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? (R)object : paramCollector
/*     */       
/* 503 */       .finisher().apply(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <R> R collect(Supplier<R> paramSupplier, BiConsumer<R, ? super P_OUT> paramBiConsumer, BiConsumer<R, R> paramBiConsumer1) {
/* 510 */     return (R)evaluate(ReduceOps.makeRef(paramSupplier, paramBiConsumer, paramBiConsumer1));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> max(Comparator<? super P_OUT> paramComparator) {
/* 515 */     return reduce(BinaryOperator.maxBy(paramComparator));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Optional<P_OUT> min(Comparator<? super P_OUT> paramComparator) {
/* 520 */     return reduce(BinaryOperator.minBy(paramComparator));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long count() {
/* 526 */     return mapToLong(paramObject -> 1L).sum();
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
/*     */   static class Head<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     Head(Supplier<? extends Spliterator<?>> param1Supplier, int param1Int, boolean param1Boolean) {
/* 550 */       super(param1Supplier, param1Int, param1Boolean);
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
/*     */     Head(Spliterator<?> param1Spliterator, int param1Int, boolean param1Boolean) {
/* 562 */       super(param1Spliterator, param1Int, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 567 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     final Sink<E_IN> opWrapSink(int param1Int, Sink<E_OUT> param1Sink) {
/* 572 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super E_OUT> param1Consumer) {
/* 579 */       if (!isParallel()) {
/* 580 */         sourceStageSpliterator().forEachRemaining(param1Consumer);
/*     */       } else {
/*     */         
/* 583 */         super.forEach(param1Consumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachOrdered(Consumer<? super E_OUT> param1Consumer) {
/* 589 */       if (!isParallel()) {
/* 590 */         sourceStageSpliterator().forEachRemaining(param1Consumer);
/*     */       } else {
/*     */         
/* 593 */         super.forEachOrdered(param1Consumer);
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
/*     */   
/*     */   static abstract class StatelessOp<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     StatelessOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 618 */       super(param1AbstractPipeline, param1Int);
/* 619 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 624 */       return false;
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
/*     */   static abstract class StatefulOp<E_IN, E_OUT>
/*     */     extends ReferencePipeline<E_IN, E_OUT>
/*     */   {
/*     */     StatefulOp(AbstractPipeline<?, E_IN, ?> param1AbstractPipeline, StreamShape param1StreamShape, int param1Int) {
/* 647 */       super(param1AbstractPipeline, param1Int);
/* 648 */       assert param1AbstractPipeline.getOutputShape() == param1StreamShape;
/*     */     }
/*     */ 
/*     */     
/*     */     final boolean opIsStateful() {
/* 653 */       return true;
/*     */     }
/*     */     
/*     */     abstract <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<E_OUT[]> param1IntFunction);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\ReferencePipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */