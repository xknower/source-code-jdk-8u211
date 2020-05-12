/*      */ package java.util.stream;
/*      */ 
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleSupplier;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntSupplier;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongSupplier;
/*      */ import java.util.function.Supplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class StreamSpliterators
/*      */ {
/*      */   private static abstract class AbstractWrappingSpliterator<P_IN, P_OUT, T_BUFFER extends AbstractSpinedBuffer>
/*      */     implements Spliterator<P_OUT>
/*      */   {
/*      */     final boolean isParallel;
/*      */     final PipelineHelper<P_OUT> ph;
/*      */     private Supplier<Spliterator<P_IN>> spliteratorSupplier;
/*      */     Spliterator<P_IN> spliterator;
/*      */     Sink<P_IN> bufferSink;
/*      */     BooleanSupplier pusher;
/*      */     long nextToConsume;
/*      */     T_BUFFER buffer;
/*      */     boolean finished;
/*      */     
/*      */     AbstractWrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  118 */       this.ph = param1PipelineHelper;
/*  119 */       this.spliteratorSupplier = param1Supplier;
/*  120 */       this.spliterator = null;
/*  121 */       this.isParallel = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AbstractWrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  131 */       this.ph = param1PipelineHelper;
/*  132 */       this.spliteratorSupplier = null;
/*  133 */       this.spliterator = param1Spliterator;
/*  134 */       this.isParallel = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void init() {
/*  141 */       if (this.spliterator == null) {
/*  142 */         this.spliterator = this.spliteratorSupplier.get();
/*  143 */         this.spliteratorSupplier = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean doAdvance() {
/*  153 */       if (this.buffer == null) {
/*  154 */         if (this.finished) {
/*  155 */           return false;
/*      */         }
/*  157 */         init();
/*  158 */         initPartialTraversalState();
/*  159 */         this.nextToConsume = 0L;
/*  160 */         this.bufferSink.begin(this.spliterator.getExactSizeIfKnown());
/*  161 */         return fillBuffer();
/*      */       } 
/*      */       
/*  164 */       this.nextToConsume++;
/*  165 */       boolean bool = (this.nextToConsume < this.buffer.count());
/*  166 */       if (!bool) {
/*  167 */         this.nextToConsume = 0L;
/*  168 */         this.buffer.clear();
/*  169 */         bool = fillBuffer();
/*      */       } 
/*  171 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract AbstractWrappingSpliterator<P_IN, P_OUT, ?> wrap(Spliterator<P_IN> param1Spliterator);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void initPartialTraversalState();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Spliterator<P_OUT> trySplit() {
/*  189 */       if (this.isParallel && !this.finished) {
/*  190 */         init();
/*      */         
/*  192 */         Spliterator<P_IN> spliterator = this.spliterator.trySplit();
/*  193 */         return (spliterator == null) ? null : wrap(spliterator);
/*      */       } 
/*      */       
/*  196 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean fillBuffer() {
/*  205 */       while (this.buffer.count() == 0L) {
/*  206 */         if (this.bufferSink.cancellationRequested() || !this.pusher.getAsBoolean()) {
/*  207 */           if (this.finished) {
/*  208 */             return false;
/*      */           }
/*  210 */           this.bufferSink.end();
/*  211 */           this.finished = true;
/*      */         } 
/*      */       } 
/*      */       
/*  215 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public final long estimateSize() {
/*  220 */       init();
/*      */ 
/*      */ 
/*      */       
/*  224 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public final long getExactSizeIfKnown() {
/*  229 */       init();
/*  230 */       return StreamOpFlag.SIZED.isKnown(this.ph.getStreamAndOpFlags()) ? this.spliterator
/*  231 */         .getExactSizeIfKnown() : -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final int characteristics() {
/*  237 */       init();
/*      */ 
/*      */       
/*  240 */       int i = StreamOpFlag.toCharacteristics(StreamOpFlag.toStreamFlags(this.ph.getStreamAndOpFlags()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  248 */       if ((i & 0x40) != 0) {
/*  249 */         i &= 0xFFFFBFBF;
/*  250 */         i |= this.spliterator.characteristics() & 0x4040;
/*      */       } 
/*      */       
/*  253 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super P_OUT> getComparator() {
/*  258 */       if (!hasCharacteristics(4))
/*  259 */         throw new IllegalStateException(); 
/*  260 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public final String toString() {
/*  265 */       return String.format("%s[%s]", new Object[] { getClass().getName(), this.spliterator });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WrappingSpliterator<P_IN, P_OUT>
/*      */     extends AbstractWrappingSpliterator<P_IN, P_OUT, SpinedBuffer<P_OUT>>
/*      */   {
/*      */     WrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  275 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     WrappingSpliterator(PipelineHelper<P_OUT> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  281 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     WrappingSpliterator<P_IN, P_OUT> wrap(Spliterator<P_IN> param1Spliterator) {
/*  286 */       return new WrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  291 */       SpinedBuffer<P_OUT> spinedBuffer = new SpinedBuffer();
/*  292 */       this.buffer = spinedBuffer;
/*  293 */       this.bufferSink = this.ph.wrapSink(spinedBuffer::accept);
/*  294 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super P_OUT> param1Consumer) {
/*  299 */       Objects.requireNonNull(param1Consumer);
/*  300 */       boolean bool = doAdvance();
/*  301 */       if (bool)
/*  302 */         param1Consumer.accept(this.buffer.get(this.nextToConsume)); 
/*  303 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super P_OUT> param1Consumer) {
/*  308 */       if (this.buffer == null && !this.finished)
/*  309 */       { Objects.requireNonNull(param1Consumer);
/*  310 */         init();
/*      */         
/*  312 */         this.ph.wrapAndCopyInto(param1Consumer::accept, this.spliterator);
/*  313 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  316 */         } while (tryAdvance(param1Consumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class IntWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Integer, SpinedBuffer.OfInt>
/*      */     implements Spliterator.OfInt
/*      */   {
/*      */     IntWrappingSpliterator(PipelineHelper<Integer> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  328 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     IntWrappingSpliterator(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  334 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Integer, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  339 */       return new IntWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  344 */       SpinedBuffer.OfInt ofInt = new SpinedBuffer.OfInt();
/*  345 */       this.buffer = ofInt;
/*  346 */       this.bufferSink = this.ph.wrapSink(ofInt::accept);
/*  347 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfInt trySplit() {
/*  352 */       return (Spliterator.OfInt)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer param1IntConsumer) {
/*  357 */       Objects.requireNonNull(param1IntConsumer);
/*  358 */       boolean bool = doAdvance();
/*  359 */       if (bool)
/*  360 */         param1IntConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  361 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer param1IntConsumer) {
/*  366 */       if (this.buffer == null && !this.finished)
/*  367 */       { Objects.requireNonNull(param1IntConsumer);
/*  368 */         init();
/*      */         
/*  370 */         this.ph.wrapAndCopyInto(param1IntConsumer::accept, this.spliterator);
/*  371 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  374 */         } while (tryAdvance(param1IntConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LongWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Long, SpinedBuffer.OfLong>
/*      */     implements Spliterator.OfLong
/*      */   {
/*      */     LongWrappingSpliterator(PipelineHelper<Long> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  386 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     LongWrappingSpliterator(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  392 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Long, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  397 */       return new LongWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  402 */       SpinedBuffer.OfLong ofLong = new SpinedBuffer.OfLong();
/*  403 */       this.buffer = ofLong;
/*  404 */       this.bufferSink = this.ph.wrapSink(ofLong::accept);
/*  405 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfLong trySplit() {
/*  410 */       return (Spliterator.OfLong)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer param1LongConsumer) {
/*  415 */       Objects.requireNonNull(param1LongConsumer);
/*  416 */       boolean bool = doAdvance();
/*  417 */       if (bool)
/*  418 */         param1LongConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  419 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer param1LongConsumer) {
/*  424 */       if (this.buffer == null && !this.finished)
/*  425 */       { Objects.requireNonNull(param1LongConsumer);
/*  426 */         init();
/*      */         
/*  428 */         this.ph.wrapAndCopyInto(param1LongConsumer::accept, this.spliterator);
/*  429 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  432 */         } while (tryAdvance(param1LongConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DoubleWrappingSpliterator<P_IN>
/*      */     extends AbstractWrappingSpliterator<P_IN, Double, SpinedBuffer.OfDouble>
/*      */     implements Spliterator.OfDouble
/*      */   {
/*      */     DoubleWrappingSpliterator(PipelineHelper<Double> param1PipelineHelper, Supplier<Spliterator<P_IN>> param1Supplier, boolean param1Boolean) {
/*  444 */       super(param1PipelineHelper, param1Supplier, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleWrappingSpliterator(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, boolean param1Boolean) {
/*  450 */       super(param1PipelineHelper, param1Spliterator, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     StreamSpliterators.AbstractWrappingSpliterator<P_IN, Double, ?> wrap(Spliterator<P_IN> param1Spliterator) {
/*  455 */       return new DoubleWrappingSpliterator(this.ph, param1Spliterator, this.isParallel);
/*      */     }
/*      */ 
/*      */     
/*      */     void initPartialTraversalState() {
/*  460 */       SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
/*  461 */       this.buffer = ofDouble;
/*  462 */       this.bufferSink = this.ph.wrapSink(ofDouble::accept);
/*  463 */       this.pusher = (() -> this.spliterator.tryAdvance(this.bufferSink));
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator.OfDouble trySplit() {
/*  468 */       return (Spliterator.OfDouble)super.trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer param1DoubleConsumer) {
/*  473 */       Objects.requireNonNull(param1DoubleConsumer);
/*  474 */       boolean bool = doAdvance();
/*  475 */       if (bool)
/*  476 */         param1DoubleConsumer.accept(this.buffer.get(this.nextToConsume)); 
/*  477 */       return bool;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer param1DoubleConsumer) {
/*  482 */       if (this.buffer == null && !this.finished)
/*  483 */       { Objects.requireNonNull(param1DoubleConsumer);
/*  484 */         init();
/*      */         
/*  486 */         this.ph.wrapAndCopyInto(param1DoubleConsumer::accept, this.spliterator);
/*  487 */         this.finished = true; }
/*      */       else { do {
/*      */         
/*  490 */         } while (tryAdvance(param1DoubleConsumer)); }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class DelegatingSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     private final Supplier<? extends T_SPLITR> supplier;
/*      */ 
/*      */     
/*      */     private T_SPLITR s;
/*      */ 
/*      */ 
/*      */     
/*      */     DelegatingSpliterator(Supplier<? extends T_SPLITR> param1Supplier) {
/*  508 */       this.supplier = param1Supplier;
/*      */     }
/*      */     
/*      */     T_SPLITR get() {
/*  512 */       if (this.s == null) {
/*  513 */         this.s = this.supplier.get();
/*      */       }
/*  515 */       return this.s;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public T_SPLITR trySplit() {
/*  521 */       return (T_SPLITR)get().trySplit();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/*  526 */       return get().tryAdvance(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/*  531 */       get().forEachRemaining(param1Consumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  536 */       return get().estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  541 */       return get().characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/*  546 */       return get().getComparator();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExactSizeIfKnown() {
/*  551 */       return get().getExactSizeIfKnown();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  556 */       return getClass().getName() + "[" + get() + "]";
/*      */     }
/*      */     
/*      */     static class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
/*      */       extends DelegatingSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
/*      */       OfPrimitive(Supplier<? extends T_SPLITR> param2Supplier) {
/*  563 */         super(param2Supplier);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/*  568 */         return ((Spliterator.OfPrimitive)get()).tryAdvance(param2T_CONS);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/*  573 */         ((Spliterator.OfPrimitive)get()).forEachRemaining(param2T_CONS);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
/*      */       implements Spliterator.OfInt
/*      */     {
/*      */       OfInt(Supplier<Spliterator.OfInt> param2Supplier) {
/*  582 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
/*      */       implements Spliterator.OfLong
/*      */     {
/*      */       OfLong(Supplier<Spliterator.OfLong> param2Supplier) {
/*  591 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
/*      */       implements Spliterator.OfDouble
/*      */     {
/*      */       OfDouble(Supplier<Spliterator.OfDouble> param2Supplier) {
/*  600 */         super(param2Supplier);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class SliceSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */   {
/*      */     final long sliceOrigin;
/*      */ 
/*      */     
/*      */     final long sliceFence;
/*      */ 
/*      */     
/*      */     T_SPLITR s;
/*      */ 
/*      */     
/*      */     long index;
/*      */     
/*      */     long fence;
/*      */ 
/*      */     
/*      */     SliceSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2, long param1Long3, long param1Long4) {
/*  624 */       assert param1T_SPLITR.hasCharacteristics(16384);
/*  625 */       this.s = param1T_SPLITR;
/*  626 */       this.sliceOrigin = param1Long1;
/*  627 */       this.sliceFence = param1Long2;
/*  628 */       this.index = param1Long3;
/*  629 */       this.fence = param1Long4;
/*      */     }
/*      */     public T_SPLITR trySplit() {
/*      */       Spliterator spliterator;
/*      */       long l1;
/*      */       long l2;
/*  635 */       if (this.sliceOrigin >= this.fence) {
/*  636 */         return null;
/*      */       }
/*  638 */       if (this.index >= this.fence) {
/*  639 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  648 */         spliterator = this.s.trySplit();
/*  649 */         if (spliterator == null) {
/*  650 */           return null;
/*      */         }
/*  652 */         l1 = this.index + spliterator.estimateSize();
/*  653 */         l2 = Math.min(l1, this.sliceFence);
/*  654 */         if (this.sliceOrigin >= l2) {
/*      */ 
/*      */ 
/*      */           
/*  658 */           this.index = l2; continue;
/*      */         } 
/*  660 */         if (l2 >= this.sliceFence) {
/*      */ 
/*      */ 
/*      */           
/*  664 */           this.s = (T_SPLITR)spliterator;
/*  665 */           this.fence = l2; continue;
/*      */         }  break;
/*  667 */       }  if (this.index >= this.sliceOrigin && l1 <= this.sliceFence) {
/*      */ 
/*      */         
/*  670 */         this.index = l2;
/*  671 */         return (T_SPLITR)spliterator;
/*      */       } 
/*      */ 
/*      */       
/*  675 */       return makeSpliterator((T_SPLITR)spliterator, this.sliceOrigin, this.sliceFence, this.index, this.index = l2);
/*      */     }
/*      */     
/*      */     protected abstract T_SPLITR makeSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2, long param1Long3, long param1Long4);
/*      */     
/*      */     public long estimateSize() {
/*  681 */       return (this.sliceOrigin < this.fence) ? (this.fence - 
/*  682 */         Math.max(this.sliceOrigin, this.index)) : 0L;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/*  686 */       return this.s.characteristics();
/*      */     }
/*      */     
/*      */     static final class OfRef<T>
/*      */       extends SliceSpliterator<T, Spliterator<T>>
/*      */       implements Spliterator<T>
/*      */     {
/*      */       OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2) {
/*  694 */         this(param2Spliterator, param2Long1, param2Long2, 0L, Math.min(param2Spliterator.estimateSize(), param2Long2));
/*      */       }
/*      */ 
/*      */       
/*      */       private OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  699 */         super(param2Spliterator, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator<T> makeSpliterator(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  706 */         return new OfRef(param2Spliterator, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/*  711 */         Objects.requireNonNull(param2Consumer);
/*      */         
/*  713 */         if (this.sliceOrigin >= this.fence) {
/*  714 */           return false;
/*      */         }
/*  716 */         while (this.sliceOrigin > this.index) {
/*  717 */           this.s.tryAdvance(param2Object -> { 
/*  718 */               }); this.index++;
/*      */         } 
/*      */         
/*  721 */         if (this.index >= this.fence) {
/*  722 */           return false;
/*      */         }
/*  724 */         this.index++;
/*  725 */         return this.s.tryAdvance(param2Consumer);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/*  730 */         Objects.requireNonNull(param2Consumer);
/*      */         
/*  732 */         if (this.sliceOrigin >= this.fence) {
/*      */           return;
/*      */         }
/*  735 */         if (this.index >= this.fence) {
/*      */           return;
/*      */         }
/*  738 */         if (this.index >= this.sliceOrigin && this.index + this.s.estimateSize() <= this.sliceFence) {
/*      */           
/*  740 */           this.s.forEachRemaining(param2Consumer);
/*  741 */           this.index = this.fence;
/*      */         } else {
/*      */           
/*  744 */           while (this.sliceOrigin > this.index) {
/*  745 */             this.s.tryAdvance(param2Object -> { 
/*  746 */                 }); this.index++;
/*      */           } 
/*      */           
/*  749 */           for (; this.index < this.fence; this.index++) {
/*  750 */             this.s.tryAdvance(param2Consumer);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static abstract class OfPrimitive<T, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_CONS>
/*      */       extends SliceSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*      */     {
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2) {
/*  763 */         this(param2T_SPLITR, param2Long1, param2Long2, 0L, Math.min(param2T_SPLITR.estimateSize(), param2Long2));
/*      */       }
/*      */ 
/*      */       
/*      */       private OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  768 */         super(param2T_SPLITR, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/*  773 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/*  775 */         if (this.sliceOrigin >= this.fence) {
/*  776 */           return false;
/*      */         }
/*  778 */         while (this.sliceOrigin > this.index) {
/*  779 */           ((Spliterator.OfPrimitive)this.s).tryAdvance(emptyConsumer());
/*  780 */           this.index++;
/*      */         } 
/*      */         
/*  783 */         if (this.index >= this.fence) {
/*  784 */           return false;
/*      */         }
/*  786 */         this.index++;
/*  787 */         return ((Spliterator.OfPrimitive)this.s).tryAdvance(param2T_CONS);
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/*  792 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/*  794 */         if (this.sliceOrigin >= this.fence) {
/*      */           return;
/*      */         }
/*  797 */         if (this.index >= this.fence) {
/*      */           return;
/*      */         }
/*  800 */         if (this.index >= this.sliceOrigin && this.index + ((Spliterator.OfPrimitive)this.s).estimateSize() <= this.sliceFence) {
/*      */           
/*  802 */           ((Spliterator.OfPrimitive)this.s).forEachRemaining(param2T_CONS);
/*  803 */           this.index = this.fence;
/*      */         } else {
/*      */           
/*  806 */           while (this.sliceOrigin > this.index) {
/*  807 */             ((Spliterator.OfPrimitive)this.s).tryAdvance(emptyConsumer());
/*  808 */             this.index++;
/*      */           } 
/*      */           
/*  811 */           for (; this.index < this.fence; this.index++)
/*  812 */             ((Spliterator.OfPrimitive)this.s).tryAdvance(param2T_CONS); 
/*      */         } 
/*      */       }
/*      */       
/*      */       protected abstract T_CONS emptyConsumer();
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, Spliterator.OfInt, IntConsumer>
/*      */       implements Spliterator.OfInt {
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2) {
/*  823 */         super(param2OfInt, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  828 */         super(param2OfInt, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  835 */         return new OfInt(param2OfInt, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected IntConsumer emptyConsumer() {
/*  840 */         return param2Int -> {
/*      */           
/*      */           };
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong extends OfPrimitive<Long, Spliterator.OfLong, LongConsumer> implements Spliterator.OfLong { OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2) {
/*  847 */         super(param2OfLong, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  852 */         super(param2OfLong, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  859 */         return new OfLong(param2OfLong, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected LongConsumer emptyConsumer() {
/*  864 */         return param2Long -> {
/*      */           
/*      */           };
/*      */       } }
/*      */     
/*      */     static final class OfDouble extends OfPrimitive<Double, Spliterator.OfDouble, DoubleConsumer> implements Spliterator.OfDouble {
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2) {
/*  871 */         super(param2OfDouble, param2Long1, param2Long2);
/*      */       }
/*      */ 
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  876 */         super(param2OfDouble, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2, long param2Long3, long param2Long4) {
/*  883 */         return new OfDouble(param2OfDouble, param2Long1, param2Long2, param2Long3, param2Long4);
/*      */       }
/*      */ 
/*      */       
/*      */       protected DoubleConsumer emptyConsumer() {
/*  888 */         return param2Double -> {
/*      */           
/*      */           };
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class UnorderedSliceSpliterator<T, T_SPLITR extends Spliterator<T>>
/*      */   {
/*      */     static final int CHUNK_SIZE = 128;
/*      */ 
/*      */     
/*      */     protected final T_SPLITR s;
/*      */     
/*      */     protected final boolean unlimited;
/*      */     
/*      */     private final long skipThreshold;
/*      */     
/*      */     private final AtomicLong permits;
/*      */ 
/*      */     
/*      */     UnorderedSliceSpliterator(T_SPLITR param1T_SPLITR, long param1Long1, long param1Long2) {
/*  912 */       this.s = param1T_SPLITR;
/*  913 */       this.unlimited = (param1Long2 < 0L);
/*  914 */       this.skipThreshold = (param1Long2 >= 0L) ? param1Long2 : 0L;
/*  915 */       this.permits = new AtomicLong((param1Long2 >= 0L) ? (param1Long1 + param1Long2) : param1Long1);
/*      */     }
/*      */ 
/*      */     
/*      */     UnorderedSliceSpliterator(T_SPLITR param1T_SPLITR, UnorderedSliceSpliterator<T, T_SPLITR> param1UnorderedSliceSpliterator) {
/*  920 */       this.s = param1T_SPLITR;
/*  921 */       this.unlimited = param1UnorderedSliceSpliterator.unlimited;
/*  922 */       this.permits = param1UnorderedSliceSpliterator.permits;
/*  923 */       this.skipThreshold = param1UnorderedSliceSpliterator.skipThreshold;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final long acquirePermits(long param1Long) {
/*      */       long l1;
/*      */       long l2;
/*  944 */       assert param1Long > 0L;
/*      */       do {
/*  946 */         l1 = this.permits.get();
/*  947 */         if (l1 == 0L)
/*  948 */           return this.unlimited ? param1Long : 0L; 
/*  949 */         l2 = Math.min(l1, param1Long);
/*  950 */       } while (l2 > 0L && 
/*  951 */         !this.permits.compareAndSet(l1, l1 - l2));
/*      */       
/*  953 */       if (this.unlimited)
/*  954 */         return Math.max(param1Long - l2, 0L); 
/*  955 */       if (l1 > this.skipThreshold) {
/*  956 */         return Math.max(l2 - l1 - this.skipThreshold, 0L);
/*      */       }
/*  958 */       return l2;
/*      */     }
/*      */     
/*  961 */     enum PermitStatus { NO_MORE, MAYBE_MORE, UNLIMITED; }
/*      */ 
/*      */     
/*      */     protected final PermitStatus permitStatus() {
/*  965 */       if (this.permits.get() > 0L) {
/*  966 */         return PermitStatus.MAYBE_MORE;
/*      */       }
/*  968 */       return this.unlimited ? PermitStatus.UNLIMITED : PermitStatus.NO_MORE;
/*      */     }
/*      */ 
/*      */     
/*      */     public final T_SPLITR trySplit() {
/*  973 */       if (this.permits.get() == 0L) {
/*  974 */         return null;
/*      */       }
/*  976 */       Spliterator spliterator = this.s.trySplit();
/*  977 */       return (spliterator == null) ? null : makeSpliterator((T_SPLITR)spliterator);
/*      */     }
/*      */     
/*      */     protected abstract T_SPLITR makeSpliterator(T_SPLITR param1T_SPLITR);
/*      */     
/*      */     public final long estimateSize() {
/*  983 */       return this.s.estimateSize();
/*      */     }
/*      */     
/*      */     public final int characteristics() {
/*  987 */       return this.s.characteristics() & 0xFFFFBFAF;
/*      */     }
/*      */     
/*      */     static final class OfRef<T>
/*      */       extends UnorderedSliceSpliterator<T, Spliterator<T>>
/*      */       implements Spliterator<T>, Consumer<T> {
/*      */       T tmpSlot;
/*      */       
/*      */       OfRef(Spliterator<T> param2Spliterator, long param2Long1, long param2Long2) {
/*  996 */         super(param2Spliterator, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfRef(Spliterator<T> param2Spliterator, OfRef<T> param2OfRef) {
/* 1000 */         super(param2Spliterator, param2OfRef);
/*      */       }
/*      */ 
/*      */       
/*      */       public final void accept(T param2T) {
/* 1005 */         this.tmpSlot = param2T;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 1010 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1012 */         while (permitStatus() != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1013 */           if (!this.s.tryAdvance(this))
/* 1014 */             return false; 
/* 1015 */           if (acquirePermits(1L) == 1L) {
/* 1016 */             param2Consumer.accept(this.tmpSlot);
/* 1017 */             this.tmpSlot = null;
/* 1018 */             return true;
/*      */           } 
/*      */         } 
/* 1021 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super T> param2Consumer) {
/* 1026 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1028 */         StreamSpliterators.ArrayBuffer.OfRef<? super T> ofRef = null;
/*      */         StreamSpliterators.UnorderedSliceSpliterator.PermitStatus permitStatus;
/* 1030 */         while ((permitStatus = permitStatus()) != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1031 */           if (permitStatus == StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.MAYBE_MORE) {
/*      */             
/* 1033 */             if (ofRef == null) {
/* 1034 */               ofRef = new StreamSpliterators.ArrayBuffer.OfRef(128);
/*      */             } else {
/* 1036 */               ofRef.reset();
/* 1037 */             }  long l = 0L; do {  }
/* 1038 */             while (this.s.tryAdvance(ofRef) && ++l < 128L);
/* 1039 */             if (l == 0L)
/*      */               return; 
/* 1041 */             ofRef.forEach(param2Consumer, acquirePermits(l));
/*      */             
/*      */             continue;
/*      */           } 
/* 1045 */           this.s.forEachRemaining(param2Consumer);
/*      */           return;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected Spliterator<T> makeSpliterator(Spliterator<T> param2Spliterator) {
/* 1053 */         return new OfRef(param2Spliterator, this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static abstract class OfPrimitive<T, T_CONS, T_BUFF extends StreamSpliterators.ArrayBuffer.OfPrimitive<T_CONS>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
/*      */       extends UnorderedSliceSpliterator<T, T_SPLITR>
/*      */       implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>
/*      */     {
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, long param2Long1, long param2Long2) {
/* 1071 */         super(param2T_SPLITR, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfPrimitive(T_SPLITR param2T_SPLITR, OfPrimitive<T, T_CONS, T_BUFF, T_SPLITR> param2OfPrimitive) {
/* 1075 */         super(param2T_SPLITR, param2OfPrimitive);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(T_CONS param2T_CONS) {
/* 1080 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/* 1082 */         OfPrimitive ofPrimitive = this;
/*      */         
/* 1084 */         while (permitStatus() != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1085 */           if (!((Spliterator.OfPrimitive)this.s).tryAdvance(ofPrimitive))
/* 1086 */             return false; 
/* 1087 */           if (acquirePermits(1L) == 1L) {
/* 1088 */             acceptConsumed(param2T_CONS);
/* 1089 */             return true;
/*      */           } 
/*      */         } 
/* 1092 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(T_CONS param2T_CONS) {
/* 1099 */         Objects.requireNonNull(param2T_CONS);
/*      */         
/* 1101 */         T_BUFF t_BUFF = null;
/*      */         StreamSpliterators.UnorderedSliceSpliterator.PermitStatus permitStatus;
/* 1103 */         while ((permitStatus = permitStatus()) != StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.NO_MORE) {
/* 1104 */           if (permitStatus == StreamSpliterators.UnorderedSliceSpliterator.PermitStatus.MAYBE_MORE) {
/*      */             
/* 1106 */             if (t_BUFF == null) {
/* 1107 */               t_BUFF = bufferCreate(128);
/*      */             } else {
/* 1109 */               t_BUFF.reset();
/*      */             } 
/* 1111 */             T_BUFF t_BUFF1 = t_BUFF;
/* 1112 */             long l = 0L; do {  }
/* 1113 */             while (((Spliterator.OfPrimitive)this.s).tryAdvance(t_BUFF1) && ++l < 128L);
/* 1114 */             if (l == 0L)
/*      */               return; 
/* 1116 */             t_BUFF.forEach(param2T_CONS, acquirePermits(l));
/*      */             
/*      */             continue;
/*      */           } 
/* 1120 */           ((Spliterator.OfPrimitive)this.s).forEachRemaining(param2T_CONS);
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/*      */       protected abstract void acceptConsumed(T_CONS param2T_CONS);
/*      */       
/*      */       protected abstract T_BUFF bufferCreate(int param2Int);
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<Integer, IntConsumer, StreamSpliterators.ArrayBuffer.OfInt, Spliterator.OfInt>
/*      */       implements Spliterator.OfInt, IntConsumer {
/*      */       int tmpValue;
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, long param2Long1, long param2Long2) {
/* 1136 */         super(param2OfInt, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfInt(Spliterator.OfInt param2OfInt, OfInt param2OfInt1) {
/* 1140 */         super(param2OfInt, param2OfInt1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(int param2Int) {
/* 1145 */         this.tmpValue = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(IntConsumer param2IntConsumer) {
/* 1150 */         param2IntConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfInt bufferCreate(int param2Int) {
/* 1155 */         return new StreamSpliterators.ArrayBuffer.OfInt(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt param2OfInt) {
/* 1160 */         return new OfInt(param2OfInt, this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<Long, LongConsumer, StreamSpliterators.ArrayBuffer.OfLong, Spliterator.OfLong>
/*      */       implements Spliterator.OfLong, LongConsumer
/*      */     {
/*      */       long tmpValue;
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, long param2Long1, long param2Long2) {
/* 1171 */         super(param2OfLong, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfLong(Spliterator.OfLong param2OfLong, OfLong param2OfLong1) {
/* 1175 */         super(param2OfLong, param2OfLong1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(long param2Long) {
/* 1180 */         this.tmpValue = param2Long;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(LongConsumer param2LongConsumer) {
/* 1185 */         param2LongConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfLong bufferCreate(int param2Int) {
/* 1190 */         return new StreamSpliterators.ArrayBuffer.OfLong(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong param2OfLong) {
/* 1195 */         return new OfLong(param2OfLong, this);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<Double, DoubleConsumer, StreamSpliterators.ArrayBuffer.OfDouble, Spliterator.OfDouble>
/*      */       implements Spliterator.OfDouble, DoubleConsumer
/*      */     {
/*      */       double tmpValue;
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, long param2Long1, long param2Long2) {
/* 1206 */         super(param2OfDouble, param2Long1, param2Long2);
/*      */       }
/*      */       
/*      */       OfDouble(Spliterator.OfDouble param2OfDouble, OfDouble param2OfDouble1) {
/* 1210 */         super(param2OfDouble, param2OfDouble1);
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(double param2Double) {
/* 1215 */         this.tmpValue = param2Double;
/*      */       }
/*      */ 
/*      */       
/*      */       protected void acceptConsumed(DoubleConsumer param2DoubleConsumer) {
/* 1220 */         param2DoubleConsumer.accept(this.tmpValue);
/*      */       }
/*      */ 
/*      */       
/*      */       protected StreamSpliterators.ArrayBuffer.OfDouble bufferCreate(int param2Int) {
/* 1225 */         return new StreamSpliterators.ArrayBuffer.OfDouble(param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble param2OfDouble) {
/* 1230 */         return new OfDouble(param2OfDouble, this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class DistinctSpliterator<T>
/*      */     implements Spliterator<T>, Consumer<T>
/*      */   {
/* 1242 */     private static final Object NULL_VALUE = new Object();
/*      */ 
/*      */     
/*      */     private final Spliterator<T> s;
/*      */ 
/*      */     
/*      */     private final ConcurrentHashMap<T, Boolean> seen;
/*      */     
/*      */     private T tmpSlot;
/*      */ 
/*      */     
/*      */     DistinctSpliterator(Spliterator<T> param1Spliterator) {
/* 1254 */       this(param1Spliterator, new ConcurrentHashMap<>());
/*      */     }
/*      */     
/*      */     private DistinctSpliterator(Spliterator<T> param1Spliterator, ConcurrentHashMap<T, Boolean> param1ConcurrentHashMap) {
/* 1258 */       this.s = param1Spliterator;
/* 1259 */       this.seen = param1ConcurrentHashMap;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(T param1T) {
/* 1264 */       this.tmpSlot = param1T;
/*      */     }
/*      */ 
/*      */     
/*      */     private T mapNull(T param1T) {
/* 1269 */       return (param1T != null) ? param1T : (T)NULL_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 1274 */       while (this.s.tryAdvance(this)) {
/* 1275 */         if (this.seen.putIfAbsent(mapNull(this.tmpSlot), Boolean.TRUE) == null) {
/* 1276 */           param1Consumer.accept(this.tmpSlot);
/* 1277 */           this.tmpSlot = null;
/* 1278 */           return true;
/*      */         } 
/*      */       } 
/* 1281 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 1286 */       this.s.forEachRemaining(param1Object -> {
/*      */             if (this.seen.putIfAbsent(mapNull((T)param1Object), Boolean.TRUE) == null) {
/*      */               param1Consumer.accept(param1Object);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<T> trySplit() {
/* 1295 */       Spliterator<T> spliterator = this.s.trySplit();
/* 1296 */       return (spliterator != null) ? new DistinctSpliterator(spliterator, this.seen) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1301 */       return this.s.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1306 */       return this.s.characteristics() & 0xFFFFBFAB | 0x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<? super T> getComparator() {
/* 1313 */       return this.s.getComparator();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class InfiniteSupplyingSpliterator<T>
/*      */     implements Spliterator<T>
/*      */   {
/*      */     long estimate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected InfiniteSupplyingSpliterator(long param1Long) {
/* 1331 */       this.estimate = param1Long;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1336 */       return this.estimate;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1341 */       return 1024;
/*      */     }
/*      */     
/*      */     static final class OfRef<T> extends InfiniteSupplyingSpliterator<T> {
/*      */       final Supplier<T> s;
/*      */       
/*      */       OfRef(long param2Long, Supplier<T> param2Supplier) {
/* 1348 */         super(param2Long);
/* 1349 */         this.s = param2Supplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super T> param2Consumer) {
/* 1354 */         Objects.requireNonNull(param2Consumer);
/*      */         
/* 1356 */         param2Consumer.accept(this.s.get());
/* 1357 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator<T> trySplit() {
/* 1362 */         if (this.estimate == 0L)
/* 1363 */           return null; 
/* 1364 */         return new OfRef(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends InfiniteSupplyingSpliterator<Integer> implements Spliterator.OfInt {
/*      */       final IntSupplier s;
/*      */       
/*      */       OfInt(long param2Long, IntSupplier param2IntSupplier) {
/* 1373 */         super(param2Long);
/* 1374 */         this.s = param2IntSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(IntConsumer param2IntConsumer) {
/* 1379 */         Objects.requireNonNull(param2IntConsumer);
/*      */         
/* 1381 */         param2IntConsumer.accept(this.s.getAsInt());
/* 1382 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfInt trySplit() {
/* 1387 */         if (this.estimate == 0L)
/* 1388 */           return null; 
/* 1389 */         return new OfInt(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends InfiniteSupplyingSpliterator<Long> implements Spliterator.OfLong {
/*      */       final LongSupplier s;
/*      */       
/*      */       OfLong(long param2Long, LongSupplier param2LongSupplier) {
/* 1398 */         super(param2Long);
/* 1399 */         this.s = param2LongSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(LongConsumer param2LongConsumer) {
/* 1404 */         Objects.requireNonNull(param2LongConsumer);
/*      */         
/* 1406 */         param2LongConsumer.accept(this.s.getAsLong());
/* 1407 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfLong trySplit() {
/* 1412 */         if (this.estimate == 0L)
/* 1413 */           return null; 
/* 1414 */         return new OfLong(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends InfiniteSupplyingSpliterator<Double> implements Spliterator.OfDouble {
/*      */       final DoubleSupplier s;
/*      */       
/*      */       OfDouble(long param2Long, DoubleSupplier param2DoubleSupplier) {
/* 1423 */         super(param2Long);
/* 1424 */         this.s = param2DoubleSupplier;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(DoubleConsumer param2DoubleConsumer) {
/* 1429 */         Objects.requireNonNull(param2DoubleConsumer);
/*      */         
/* 1431 */         param2DoubleConsumer.accept(this.s.getAsDouble());
/* 1432 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator.OfDouble trySplit() {
/* 1437 */         if (this.estimate == 0L)
/* 1438 */           return null; 
/* 1439 */         return new OfDouble(this.estimate >>>= 1L, this.s);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class ArrayBuffer
/*      */   {
/*      */     int index;
/*      */     
/*      */     void reset() {
/* 1449 */       this.index = 0;
/*      */     }
/*      */     
/*      */     static final class OfRef<T> extends ArrayBuffer implements Consumer<T> {
/*      */       final Object[] array;
/*      */       
/*      */       OfRef(int param2Int) {
/* 1456 */         this.array = new Object[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(T param2T) {
/* 1461 */         this.array[this.index++] = param2T;
/*      */       }
/*      */       
/*      */       public void forEach(Consumer<? super T> param2Consumer, long param2Long) {
/* 1465 */         for (byte b = 0; b < param2Long; b++) {
/*      */           
/* 1467 */           Object object = this.array[b];
/* 1468 */           param2Consumer.accept((T)object);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     static abstract class OfPrimitive<T_CONS>
/*      */       extends ArrayBuffer {
/*      */       int index;
/*      */       
/*      */       void reset() {
/* 1478 */         this.index = 0;
/*      */       }
/*      */       
/*      */       abstract void forEach(T_CONS param2T_CONS, long param2Long);
/*      */     }
/*      */     
/*      */     static final class OfInt
/*      */       extends OfPrimitive<IntConsumer> implements IntConsumer {
/*      */       final int[] array;
/*      */       
/*      */       OfInt(int param2Int) {
/* 1489 */         this.array = new int[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(int param2Int) {
/* 1494 */         this.array[this.index++] = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEach(IntConsumer param2IntConsumer, long param2Long) {
/* 1499 */         for (byte b = 0; b < param2Long; b++)
/* 1500 */           param2IntConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfLong
/*      */       extends OfPrimitive<LongConsumer>
/*      */       implements LongConsumer {
/*      */       final long[] array;
/*      */       
/*      */       OfLong(int param2Int) {
/* 1510 */         this.array = new long[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(long param2Long) {
/* 1515 */         this.array[this.index++] = param2Long;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEach(LongConsumer param2LongConsumer, long param2Long) {
/* 1520 */         for (byte b = 0; b < param2Long; b++)
/* 1521 */           param2LongConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */     
/*      */     static final class OfDouble
/*      */       extends OfPrimitive<DoubleConsumer>
/*      */       implements DoubleConsumer {
/*      */       final double[] array;
/*      */       
/*      */       OfDouble(int param2Int) {
/* 1531 */         this.array = new double[param2Int];
/*      */       }
/*      */ 
/*      */       
/*      */       public void accept(double param2Double) {
/* 1536 */         this.array[this.index++] = param2Double;
/*      */       }
/*      */ 
/*      */       
/*      */       void forEach(DoubleConsumer param2DoubleConsumer, long param2Long) {
/* 1541 */         for (byte b = 0; b < param2Long; b++)
/* 1542 */           param2DoubleConsumer.accept(this.array[b]); 
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\StreamSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */