/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SortedOps
/*     */ {
/*     */   static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline) {
/*  51 */     return new OfRef<>(paramAbstractPipeline);
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
/*     */   static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> paramAbstractPipeline, Comparator<? super T> paramComparator) {
/*  63 */     return new OfRef<>(paramAbstractPipeline, paramComparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> IntStream makeInt(AbstractPipeline<?, Integer, ?> paramAbstractPipeline) {
/*  73 */     return new OfInt(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> LongStream makeLong(AbstractPipeline<?, Long, ?> paramAbstractPipeline) {
/*  83 */     return new OfLong(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> DoubleStream makeDouble(AbstractPipeline<?, Double, ?> paramAbstractPipeline) {
/*  93 */     return new OfDouble(paramAbstractPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfRef<T>
/*     */     extends ReferencePipeline.StatefulOp<T, T>
/*     */   {
/*     */     private final boolean isNaturalSort;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Comparator<? super T> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     OfRef(AbstractPipeline<?, T, ?> param1AbstractPipeline) {
/* 111 */       super(param1AbstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */       
/* 113 */       this.isNaturalSort = true;
/*     */ 
/*     */       
/* 116 */       Comparator<Comparable> comparator = Comparator.naturalOrder();
/* 117 */       this.comparator = (Comparator)comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     OfRef(AbstractPipeline<?, T, ?> param1AbstractPipeline, Comparator<? super T> param1Comparator) {
/* 126 */       super(param1AbstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.NOT_SORTED);
/*     */       
/* 128 */       this.isNaturalSort = false;
/* 129 */       this.comparator = Objects.<Comparator<? super T>>requireNonNull(param1Comparator);
/*     */     }
/*     */ 
/*     */     
/*     */     public Sink<T> opWrapSink(int param1Int, Sink<T> param1Sink) {
/* 134 */       Objects.requireNonNull(param1Sink);
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (StreamOpFlag.SORTED.isKnown(param1Int) && this.isNaturalSort)
/* 139 */         return param1Sink; 
/* 140 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 141 */         return new SortedOps.SizedRefSortingSink<>(param1Sink, this.comparator);
/*     */       }
/* 143 */       return new SortedOps.RefSortingSink<>(param1Sink, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<T[]> param1IntFunction) {
/* 152 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags()) && this.isNaturalSort) {
/* 153 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */ 
/*     */       
/* 157 */       Object[] arrayOfObject = param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction).asArray((IntFunction)param1IntFunction);
/* 158 */       Arrays.parallelSort(arrayOfObject, (Comparator)this.comparator);
/* 159 */       return Nodes.node((T[])arrayOfObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfInt
/*     */     extends IntPipeline.StatefulOp<Integer>
/*     */   {
/*     */     OfInt(AbstractPipeline<?, Integer, ?> param1AbstractPipeline) {
/* 169 */       super(param1AbstractPipeline, StreamShape.INT_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Integer> opWrapSink(int param1Int, Sink<Integer> param1Sink) {
/* 175 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 177 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 178 */         return param1Sink; 
/* 179 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 180 */         return new SortedOps.SizedIntSortingSink(param1Sink);
/*     */       }
/* 182 */       return new SortedOps.IntSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Integer[]> param1IntFunction) {
/* 189 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 190 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 193 */       Node.OfInt ofInt = (Node.OfInt)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 195 */       int[] arrayOfInt = ofInt.asPrimitiveArray();
/* 196 */       Arrays.parallelSort(arrayOfInt);
/*     */       
/* 198 */       return Nodes.node(arrayOfInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfLong
/*     */     extends LongPipeline.StatefulOp<Long>
/*     */   {
/*     */     OfLong(AbstractPipeline<?, Long, ?> param1AbstractPipeline) {
/* 208 */       super(param1AbstractPipeline, StreamShape.LONG_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Long> opWrapSink(int param1Int, Sink<Long> param1Sink) {
/* 214 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 216 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 217 */         return param1Sink; 
/* 218 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 219 */         return new SortedOps.SizedLongSortingSink(param1Sink);
/*     */       }
/* 221 */       return new SortedOps.LongSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Long[]> param1IntFunction) {
/* 228 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 229 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 232 */       Node.OfLong ofLong = (Node.OfLong)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 234 */       long[] arrayOfLong = ofLong.asPrimitiveArray();
/* 235 */       Arrays.parallelSort(arrayOfLong);
/*     */       
/* 237 */       return Nodes.node(arrayOfLong);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class OfDouble
/*     */     extends DoublePipeline.StatefulOp<Double>
/*     */   {
/*     */     OfDouble(AbstractPipeline<?, Double, ?> param1AbstractPipeline) {
/* 247 */       super(param1AbstractPipeline, StreamShape.DOUBLE_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Sink<Double> opWrapSink(int param1Int, Sink<Double> param1Sink) {
/* 253 */       Objects.requireNonNull(param1Sink);
/*     */       
/* 255 */       if (StreamOpFlag.SORTED.isKnown(param1Int))
/* 256 */         return param1Sink; 
/* 257 */       if (StreamOpFlag.SIZED.isKnown(param1Int)) {
/* 258 */         return new SortedOps.SizedDoubleSortingSink(param1Sink);
/*     */       }
/* 260 */       return new SortedOps.DoubleSortingSink(param1Sink);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> param1PipelineHelper, Spliterator<P_IN> param1Spliterator, IntFunction<Double[]> param1IntFunction) {
/* 267 */       if (StreamOpFlag.SORTED.isKnown(param1PipelineHelper.getStreamAndOpFlags())) {
/* 268 */         return param1PipelineHelper.evaluate(param1Spliterator, false, param1IntFunction);
/*     */       }
/*     */       
/* 271 */       Node.OfDouble ofDouble = (Node.OfDouble)param1PipelineHelper.<P_IN>evaluate(param1Spliterator, true, param1IntFunction);
/*     */       
/* 273 */       double[] arrayOfDouble = ofDouble.asPrimitiveArray();
/* 274 */       Arrays.parallelSort(arrayOfDouble);
/*     */       
/* 276 */       return Nodes.node(arrayOfDouble);
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
/*     */   private static abstract class AbstractRefSortingSink<T>
/*     */     extends Sink.ChainedReference<T, T>
/*     */   {
/*     */     protected final Comparator<? super T> comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean cancellationWasRequested;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AbstractRefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 310 */       super(param1Sink);
/* 311 */       this.comparator = param1Comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 322 */       this.cancellationWasRequested = true;
/* 323 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedRefSortingSink<T>
/*     */     extends AbstractRefSortingSink<T>
/*     */   {
/*     */     private T[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedRefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 335 */       super(param1Sink, param1Comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 341 */       if (param1Long >= 2147483639L)
/* 342 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 343 */       this.array = (T[])new Object[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 348 */       Arrays.sort(this.array, 0, this.offset, this.comparator);
/* 349 */       this.downstream.begin(this.offset);
/* 350 */       if (!this.cancellationWasRequested) {
/* 351 */         for (byte b = 0; b < this.offset; b++) {
/* 352 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 355 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 356 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 358 */       this.downstream.end();
/* 359 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 364 */       this.array[this.offset++] = param1T;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class RefSortingSink<T>
/*     */     extends AbstractRefSortingSink<T>
/*     */   {
/*     */     private ArrayList<T> list;
/*     */     
/*     */     RefSortingSink(Sink<? super T> param1Sink, Comparator<? super T> param1Comparator) {
/* 375 */       super(param1Sink, param1Comparator);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 380 */       if (param1Long >= 2147483639L)
/* 381 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 382 */       this.list = (param1Long >= 0L) ? new ArrayList<>((int)param1Long) : new ArrayList<>();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield list : Ljava/util/ArrayList;
/*     */       //   4: aload_0
/*     */       //   5: getfield comparator : Ljava/util/Comparator;
/*     */       //   8: invokevirtual sort : (Ljava/util/Comparator;)V
/*     */       //   11: aload_0
/*     */       //   12: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   15: aload_0
/*     */       //   16: getfield list : Ljava/util/ArrayList;
/*     */       //   19: invokevirtual size : ()I
/*     */       //   22: i2l
/*     */       //   23: invokeinterface begin : (J)V
/*     */       //   28: aload_0
/*     */       //   29: getfield cancellationWasRequested : Z
/*     */       //   32: ifne -> 59
/*     */       //   35: aload_0
/*     */       //   36: getfield list : Ljava/util/ArrayList;
/*     */       //   39: aload_0
/*     */       //   40: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   43: dup
/*     */       //   44: invokevirtual getClass : ()Ljava/lang/Class;
/*     */       //   47: pop
/*     */       //   48: <illegal opcode> accept : (Ljava/util/stream/Sink;)Ljava/util/function/Consumer;
/*     */       //   53: invokevirtual forEach : (Ljava/util/function/Consumer;)V
/*     */       //   56: goto -> 111
/*     */       //   59: aload_0
/*     */       //   60: getfield list : Ljava/util/ArrayList;
/*     */       //   63: invokevirtual iterator : ()Ljava/util/Iterator;
/*     */       //   66: astore_1
/*     */       //   67: aload_1
/*     */       //   68: invokeinterface hasNext : ()Z
/*     */       //   73: ifeq -> 111
/*     */       //   76: aload_1
/*     */       //   77: invokeinterface next : ()Ljava/lang/Object;
/*     */       //   82: astore_2
/*     */       //   83: aload_0
/*     */       //   84: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   87: invokeinterface cancellationRequested : ()Z
/*     */       //   92: ifeq -> 98
/*     */       //   95: goto -> 111
/*     */       //   98: aload_0
/*     */       //   99: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   102: aload_2
/*     */       //   103: invokeinterface accept : (Ljava/lang/Object;)V
/*     */       //   108: goto -> 67
/*     */       //   111: aload_0
/*     */       //   112: getfield downstream : Ljava/util/stream/Sink;
/*     */       //   115: invokeinterface end : ()V
/*     */       //   120: aload_0
/*     */       //   121: aconst_null
/*     */       //   122: putfield list : Ljava/util/ArrayList;
/*     */       //   125: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #387	-> 0
/*     */       //   #388	-> 11
/*     */       //   #389	-> 28
/*     */       //   #390	-> 35
/*     */       //   #393	-> 59
/*     */       //   #394	-> 83
/*     */       //   #395	-> 98
/*     */       //   #396	-> 108
/*     */       //   #398	-> 111
/*     */       //   #399	-> 120
/*     */       //   #400	-> 125
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(T param1T) {
/* 404 */       this.list.add(param1T);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractIntSortingSink
/*     */     extends Sink.ChainedInt<Integer>
/*     */   {
/*     */     protected boolean cancellationWasRequested;
/*     */     
/*     */     AbstractIntSortingSink(Sink<? super Integer> param1Sink) {
/* 415 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 420 */       this.cancellationWasRequested = true;
/* 421 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedIntSortingSink
/*     */     extends AbstractIntSortingSink
/*     */   {
/*     */     private int[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedIntSortingSink(Sink<? super Integer> param1Sink) {
/* 433 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 438 */       if (param1Long >= 2147483639L)
/* 439 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 440 */       this.array = new int[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 445 */       Arrays.sort(this.array, 0, this.offset);
/* 446 */       this.downstream.begin(this.offset);
/* 447 */       if (!this.cancellationWasRequested) {
/* 448 */         for (byte b = 0; b < this.offset; b++) {
/* 449 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 452 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 453 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 455 */       this.downstream.end();
/* 456 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(int param1Int) {
/* 461 */       this.array[this.offset++] = param1Int;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class IntSortingSink
/*     */     extends AbstractIntSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfInt b;
/*     */     
/*     */     IntSortingSink(Sink<? super Integer> param1Sink) {
/* 472 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 477 */       if (param1Long >= 2147483639L)
/* 478 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 479 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfInt((int)param1Long) : new SpinedBuffer.OfInt();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 484 */       int[] arrayOfInt = this.b.asPrimitiveArray();
/* 485 */       Arrays.sort(arrayOfInt);
/* 486 */       this.downstream.begin(arrayOfInt.length);
/* 487 */       if (!this.cancellationWasRequested) {
/* 488 */         for (int i : arrayOfInt) {
/* 489 */           this.downstream.accept(i);
/*     */         }
/*     */       } else {
/* 492 */         for (int i : arrayOfInt) {
/* 493 */           if (this.downstream.cancellationRequested())
/* 494 */             break;  this.downstream.accept(i);
/*     */         } 
/*     */       } 
/* 497 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(int param1Int) {
/* 502 */       this.b.accept(param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractLongSortingSink
/*     */     extends Sink.ChainedLong<Long>
/*     */   {
/*     */     protected boolean cancellationWasRequested;
/*     */     
/*     */     AbstractLongSortingSink(Sink<? super Long> param1Sink) {
/* 513 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 518 */       this.cancellationWasRequested = true;
/* 519 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedLongSortingSink
/*     */     extends AbstractLongSortingSink
/*     */   {
/*     */     private long[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedLongSortingSink(Sink<? super Long> param1Sink) {
/* 531 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 536 */       if (param1Long >= 2147483639L)
/* 537 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 538 */       this.array = new long[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 543 */       Arrays.sort(this.array, 0, this.offset);
/* 544 */       this.downstream.begin(this.offset);
/* 545 */       if (!this.cancellationWasRequested) {
/* 546 */         for (byte b = 0; b < this.offset; b++) {
/* 547 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 550 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 551 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 553 */       this.downstream.end();
/* 554 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(long param1Long) {
/* 559 */       this.array[this.offset++] = param1Long;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class LongSortingSink
/*     */     extends AbstractLongSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfLong b;
/*     */     
/*     */     LongSortingSink(Sink<? super Long> param1Sink) {
/* 570 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 575 */       if (param1Long >= 2147483639L)
/* 576 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 577 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfLong((int)param1Long) : new SpinedBuffer.OfLong();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 582 */       long[] arrayOfLong = this.b.asPrimitiveArray();
/* 583 */       Arrays.sort(arrayOfLong);
/* 584 */       this.downstream.begin(arrayOfLong.length);
/* 585 */       if (!this.cancellationWasRequested) {
/* 586 */         for (long l : arrayOfLong) {
/* 587 */           this.downstream.accept(l);
/*     */         }
/*     */       } else {
/* 590 */         for (long l : arrayOfLong) {
/* 591 */           if (this.downstream.cancellationRequested())
/* 592 */             break;  this.downstream.accept(l);
/*     */         } 
/*     */       } 
/* 595 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(long param1Long) {
/* 600 */       this.b.accept(param1Long);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class AbstractDoubleSortingSink
/*     */     extends Sink.ChainedDouble<Double>
/*     */   {
/*     */     protected boolean cancellationWasRequested;
/*     */     
/*     */     AbstractDoubleSortingSink(Sink<? super Double> param1Sink) {
/* 611 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean cancellationRequested() {
/* 616 */       this.cancellationWasRequested = true;
/* 617 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SizedDoubleSortingSink
/*     */     extends AbstractDoubleSortingSink
/*     */   {
/*     */     private double[] array;
/*     */     private int offset;
/*     */     
/*     */     SizedDoubleSortingSink(Sink<? super Double> param1Sink) {
/* 629 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 634 */       if (param1Long >= 2147483639L)
/* 635 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 636 */       this.array = new double[(int)param1Long];
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 641 */       Arrays.sort(this.array, 0, this.offset);
/* 642 */       this.downstream.begin(this.offset);
/* 643 */       if (!this.cancellationWasRequested) {
/* 644 */         for (byte b = 0; b < this.offset; b++) {
/* 645 */           this.downstream.accept(this.array[b]);
/*     */         }
/*     */       } else {
/* 648 */         for (byte b = 0; b < this.offset && !this.downstream.cancellationRequested(); b++)
/* 649 */           this.downstream.accept(this.array[b]); 
/*     */       } 
/* 651 */       this.downstream.end();
/* 652 */       this.array = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/* 657 */       this.array[this.offset++] = param1Double;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class DoubleSortingSink
/*     */     extends AbstractDoubleSortingSink
/*     */   {
/*     */     private SpinedBuffer.OfDouble b;
/*     */     
/*     */     DoubleSortingSink(Sink<? super Double> param1Sink) {
/* 668 */       super(param1Sink);
/*     */     }
/*     */ 
/*     */     
/*     */     public void begin(long param1Long) {
/* 673 */       if (param1Long >= 2147483639L)
/* 674 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 675 */       this.b = (param1Long > 0L) ? new SpinedBuffer.OfDouble((int)param1Long) : new SpinedBuffer.OfDouble();
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 680 */       double[] arrayOfDouble = this.b.asPrimitiveArray();
/* 681 */       Arrays.sort(arrayOfDouble);
/* 682 */       this.downstream.begin(arrayOfDouble.length);
/* 683 */       if (!this.cancellationWasRequested) {
/* 684 */         for (double d : arrayOfDouble) {
/* 685 */           this.downstream.accept(d);
/*     */         }
/*     */       } else {
/* 688 */         for (double d : arrayOfDouble) {
/* 689 */           if (this.downstream.cancellationRequested())
/* 690 */             break;  this.downstream.accept(d);
/*     */         } 
/*     */       } 
/* 693 */       this.downstream.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(double param1Double) {
/* 698 */       this.b.accept(param1Double);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\SortedOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */