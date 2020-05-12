/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPipeline<E_IN, E_OUT, S extends BaseStream<E_OUT, S>>
/*     */   extends PipelineHelper<E_OUT>
/*     */   implements BaseStream<E_OUT, S>
/*     */ {
/*     */   private static final String MSG_STREAM_LINKED = "stream has already been operated upon or closed";
/*     */   private static final String MSG_CONSUMED = "source already consumed or closed";
/*     */   private final AbstractPipeline sourceStage;
/*     */   private final AbstractPipeline previousStage;
/*     */   protected final int sourceOrOpFlags;
/*     */   private AbstractPipeline nextStage;
/*     */   private int depth;
/*     */   private int combinedFlags;
/*     */   private Spliterator<?> sourceSpliterator;
/*     */   private Supplier<? extends Spliterator<?>> sourceSupplier;
/*     */   private boolean linkedOrConsumed;
/*     */   private boolean sourceAnyStateful;
/*     */   private Runnable sourceCloseAction;
/*     */   private boolean parallel;
/*     */   
/*     */   AbstractPipeline(Supplier<? extends Spliterator<?>> paramSupplier, int paramInt, boolean paramBoolean) {
/* 161 */     this.previousStage = null;
/* 162 */     this.sourceSupplier = paramSupplier;
/* 163 */     this.sourceStage = this;
/* 164 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.STREAM_MASK;
/*     */ 
/*     */     
/* 167 */     this.combinedFlags = (this.sourceOrOpFlags << 1 ^ 0xFFFFFFFF) & StreamOpFlag.INITIAL_OPS_VALUE;
/* 168 */     this.depth = 0;
/* 169 */     this.parallel = paramBoolean;
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
/*     */   AbstractPipeline(Spliterator<?> paramSpliterator, int paramInt, boolean paramBoolean) {
/* 182 */     this.previousStage = null;
/* 183 */     this.sourceSpliterator = paramSpliterator;
/* 184 */     this.sourceStage = this;
/* 185 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.STREAM_MASK;
/*     */ 
/*     */     
/* 188 */     this.combinedFlags = (this.sourceOrOpFlags << 1 ^ 0xFFFFFFFF) & StreamOpFlag.INITIAL_OPS_VALUE;
/* 189 */     this.depth = 0;
/* 190 */     this.parallel = paramBoolean;
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
/*     */   AbstractPipeline(AbstractPipeline<?, E_IN, ?> paramAbstractPipeline, int paramInt) {
/* 202 */     if (paramAbstractPipeline.linkedOrConsumed)
/* 203 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 204 */     paramAbstractPipeline.linkedOrConsumed = true;
/* 205 */     paramAbstractPipeline.nextStage = this;
/*     */     
/* 207 */     this.previousStage = paramAbstractPipeline;
/* 208 */     this.sourceOrOpFlags = paramInt & StreamOpFlag.OP_MASK;
/* 209 */     this.combinedFlags = StreamOpFlag.combineOpFlags(paramInt, paramAbstractPipeline.combinedFlags);
/* 210 */     this.sourceStage = paramAbstractPipeline.sourceStage;
/* 211 */     if (opIsStateful())
/* 212 */       this.sourceStage.sourceAnyStateful = true; 
/* 213 */     this.depth = paramAbstractPipeline.depth + 1;
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
/*     */   final <R> R evaluate(TerminalOp<E_OUT, R> paramTerminalOp) {
/* 227 */     assert getOutputShape() == paramTerminalOp.inputShape();
/* 228 */     if (this.linkedOrConsumed)
/* 229 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 230 */     this.linkedOrConsumed = true;
/*     */     
/* 232 */     return isParallel() ? paramTerminalOp
/* 233 */       .evaluateParallel(this, sourceSpliterator(paramTerminalOp.getOpFlags())) : paramTerminalOp
/* 234 */       .evaluateSequential(this, sourceSpliterator(paramTerminalOp.getOpFlags()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Node<E_OUT> evaluateToArrayNode(IntFunction<E_OUT[]> paramIntFunction) {
/* 245 */     if (this.linkedOrConsumed)
/* 246 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 247 */     this.linkedOrConsumed = true;
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (isParallel() && this.previousStage != null && opIsStateful()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       this.depth = 0;
/* 257 */       return opEvaluateParallel(this.previousStage, this.previousStage.sourceSpliterator(0), paramIntFunction);
/*     */     } 
/*     */     
/* 260 */     return evaluate(sourceSpliterator(0), true, paramIntFunction);
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
/*     */   final Spliterator<E_OUT> sourceStageSpliterator() {
/* 275 */     if (this != this.sourceStage) {
/* 276 */       throw new IllegalStateException();
/*     */     }
/* 278 */     if (this.linkedOrConsumed)
/* 279 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 280 */     this.linkedOrConsumed = true;
/*     */     
/* 282 */     if (this.sourceStage.sourceSpliterator != null) {
/*     */       
/* 284 */       Spliterator<?> spliterator = this.sourceStage.sourceSpliterator;
/* 285 */       this.sourceStage.sourceSpliterator = null;
/* 286 */       return (Spliterator)spliterator;
/*     */     } 
/* 288 */     if (this.sourceStage.sourceSupplier != null) {
/*     */       
/* 290 */       Spliterator<E_OUT> spliterator = (Spliterator)this.sourceStage.sourceSupplier.get();
/* 291 */       this.sourceStage.sourceSupplier = null;
/* 292 */       return spliterator;
/*     */     } 
/*     */     
/* 295 */     throw new IllegalStateException("source already consumed or closed");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final S sequential() {
/* 304 */     this.sourceStage.parallel = false;
/* 305 */     return (S)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final S parallel() {
/* 311 */     this.sourceStage.parallel = true;
/* 312 */     return (S)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 317 */     this.linkedOrConsumed = true;
/* 318 */     this.sourceSupplier = null;
/* 319 */     this.sourceSpliterator = null;
/* 320 */     if (this.sourceStage.sourceCloseAction != null) {
/* 321 */       Runnable runnable = this.sourceStage.sourceCloseAction;
/* 322 */       this.sourceStage.sourceCloseAction = null;
/* 323 */       runnable.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public S onClose(Runnable paramRunnable) {
/* 330 */     Runnable runnable = this.sourceStage.sourceCloseAction;
/* 331 */     this.sourceStage
/*     */ 
/*     */       
/* 334 */       .sourceCloseAction = (runnable == null) ? paramRunnable : Streams.composeWithExceptions(runnable, paramRunnable);
/* 335 */     return (S)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spliterator<E_OUT> spliterator() {
/* 342 */     if (this.linkedOrConsumed)
/* 343 */       throw new IllegalStateException("stream has already been operated upon or closed"); 
/* 344 */     this.linkedOrConsumed = true;
/*     */     
/* 346 */     if (this == this.sourceStage) {
/* 347 */       if (this.sourceStage.sourceSpliterator != null) {
/*     */         
/* 349 */         Spliterator<?> spliterator = this.sourceStage.sourceSpliterator;
/* 350 */         this.sourceStage.sourceSpliterator = null;
/* 351 */         return (Spliterator)spliterator;
/*     */       } 
/* 353 */       if (this.sourceStage.sourceSupplier != null) {
/*     */         
/* 355 */         Supplier<? extends Spliterator<?>> supplier = this.sourceStage.sourceSupplier;
/* 356 */         this.sourceStage.sourceSupplier = null;
/* 357 */         return lazySpliterator((Supplier)supplier);
/*     */       } 
/*     */       
/* 360 */       throw new IllegalStateException("source already consumed or closed");
/*     */     } 
/*     */ 
/*     */     
/* 364 */     return wrap(this, () -> sourceSpliterator(0), isParallel());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isParallel() {
/* 370 */     return this.sourceStage.parallel;
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
/*     */   final int getStreamFlags() {
/* 383 */     return StreamOpFlag.toStreamFlags(this.combinedFlags);
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
/*     */   private Spliterator<?> sourceSpliterator(int paramInt) {
/* 396 */     Spliterator<?> spliterator = null;
/* 397 */     if (this.sourceStage.sourceSpliterator != null) {
/* 398 */       spliterator = this.sourceStage.sourceSpliterator;
/* 399 */       this.sourceStage.sourceSpliterator = null;
/*     */     }
/* 401 */     else if (this.sourceStage.sourceSupplier != null) {
/* 402 */       spliterator = this.sourceStage.sourceSupplier.get();
/* 403 */       this.sourceStage.sourceSupplier = null;
/*     */     } else {
/*     */       
/* 406 */       throw new IllegalStateException("source already consumed or closed");
/*     */     } 
/*     */     
/* 409 */     if (isParallel() && this.sourceStage.sourceAnyStateful) {
/*     */ 
/*     */ 
/*     */       
/* 413 */       byte b = 1;
/* 414 */       AbstractPipeline abstractPipeline1 = this.sourceStage, abstractPipeline2 = this.sourceStage.nextStage, abstractPipeline3 = this;
/* 415 */       for (; abstractPipeline1 != abstractPipeline3; 
/* 416 */         abstractPipeline1 = abstractPipeline2, abstractPipeline2 = abstractPipeline2.nextStage) {
/*     */         
/* 418 */         int i = abstractPipeline2.sourceOrOpFlags;
/* 419 */         if (abstractPipeline2.opIsStateful()) {
/* 420 */           b = 0;
/*     */           
/* 422 */           if (StreamOpFlag.SHORT_CIRCUIT.isKnown(i))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 428 */             i &= StreamOpFlag.IS_SHORT_CIRCUIT ^ 0xFFFFFFFF;
/*     */           }
/*     */           
/* 431 */           spliterator = abstractPipeline2.opEvaluateParallelLazy(abstractPipeline1, spliterator);
/*     */ 
/*     */ 
/*     */           
/* 435 */           i = spliterator.hasCharacteristics(64) ? (i & (StreamOpFlag.NOT_SIZED ^ 0xFFFFFFFF) | StreamOpFlag.IS_SIZED) : (i & (StreamOpFlag.IS_SIZED ^ 0xFFFFFFFF) | StreamOpFlag.NOT_SIZED);
/*     */         } 
/*     */ 
/*     */         
/* 439 */         abstractPipeline2.depth = b++;
/* 440 */         abstractPipeline2.combinedFlags = StreamOpFlag.combineOpFlags(i, abstractPipeline1.combinedFlags);
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     if (paramInt != 0)
/*     */     {
/* 446 */       this.combinedFlags = StreamOpFlag.combineOpFlags(paramInt, this.combinedFlags);
/*     */     }
/*     */     
/* 449 */     return spliterator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final StreamShape getSourceShape() {
/* 457 */     AbstractPipeline abstractPipeline = this;
/* 458 */     while (abstractPipeline.depth > 0) {
/* 459 */       abstractPipeline = abstractPipeline.previousStage;
/*     */     }
/* 461 */     return abstractPipeline.getOutputShape();
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN> long exactOutputSizeIfKnown(Spliterator<P_IN> paramSpliterator) {
/* 466 */     return StreamOpFlag.SIZED.isKnown(getStreamAndOpFlags()) ? paramSpliterator.getExactSizeIfKnown() : -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN, S extends Sink<E_OUT>> S wrapAndCopyInto(S paramS, Spliterator<P_IN> paramSpliterator) {
/* 471 */     copyInto(wrapSink(Objects.<Sink>requireNonNull((Sink)paramS)), paramSpliterator);
/* 472 */     return paramS;
/*     */   }
/*     */ 
/*     */   
/*     */   final <P_IN> void copyInto(Sink<P_IN> paramSink, Spliterator<P_IN> paramSpliterator) {
/* 477 */     Objects.requireNonNull(paramSink);
/*     */     
/* 479 */     if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(getStreamAndOpFlags())) {
/* 480 */       paramSink.begin(paramSpliterator.getExactSizeIfKnown());
/* 481 */       paramSpliterator.forEachRemaining(paramSink);
/* 482 */       paramSink.end();
/*     */     } else {
/*     */       
/* 485 */       copyIntoWithCancel(paramSink, paramSpliterator);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> void copyIntoWithCancel(Sink<P_IN> paramSink, Spliterator<P_IN> paramSpliterator) {
/* 493 */     AbstractPipeline abstractPipeline = this;
/* 494 */     while (abstractPipeline.depth > 0) {
/* 495 */       abstractPipeline = abstractPipeline.previousStage;
/*     */     }
/* 497 */     paramSink.begin(paramSpliterator.getExactSizeIfKnown());
/* 498 */     abstractPipeline.forEachWithCancel(paramSpliterator, paramSink);
/* 499 */     paramSink.end();
/*     */   }
/*     */ 
/*     */   
/*     */   final int getStreamAndOpFlags() {
/* 504 */     return this.combinedFlags;
/*     */   }
/*     */   
/*     */   final boolean isOrdered() {
/* 508 */     return StreamOpFlag.ORDERED.isKnown(this.combinedFlags);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Sink<P_IN> wrapSink(Sink<E_OUT> paramSink) {
/* 514 */     Objects.requireNonNull(paramSink);
/*     */     
/* 516 */     for (AbstractPipeline abstractPipeline = this; abstractPipeline.depth > 0; abstractPipeline = abstractPipeline.previousStage) {
/* 517 */       paramSink = abstractPipeline.opWrapSink(abstractPipeline.previousStage.combinedFlags, paramSink);
/*     */     }
/* 519 */     return paramSink;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Spliterator<E_OUT> wrapSpliterator(Spliterator<P_IN> paramSpliterator) {
/* 525 */     if (this.depth == 0) {
/* 526 */       return paramSpliterator;
/*     */     }
/*     */     
/* 529 */     return wrap(this, () -> paramSpliterator, isParallel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final <P_IN> Node<E_OUT> evaluate(Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<E_OUT[]> paramIntFunction) {
/* 538 */     if (isParallel())
/*     */     {
/* 540 */       return evaluateToNode(this, paramSpliterator, paramBoolean, paramIntFunction);
/*     */     }
/*     */     
/* 543 */     Node.Builder<E_OUT> builder = makeNodeBuilder(
/* 544 */         exactOutputSizeIfKnown(paramSpliterator), paramIntFunction);
/* 545 */     return ((Node.Builder<E_OUT>)wrapAndCopyInto(builder, paramSpliterator)).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, IntFunction<E_OUT[]> paramIntFunction) {
/* 678 */     throw new UnsupportedOperationException("Parallel evaluation is not supported");
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
/*     */   <P_IN> Spliterator<E_OUT> opEvaluateParallelLazy(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/* 704 */     return opEvaluateParallel(paramPipelineHelper, paramSpliterator, paramInt -> new Object[paramInt]).spliterator();
/*     */   }
/*     */   
/*     */   abstract StreamShape getOutputShape();
/*     */   
/*     */   abstract <P_IN> Node<E_OUT> evaluateToNode(PipelineHelper<E_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator, boolean paramBoolean, IntFunction<E_OUT[]> paramIntFunction);
/*     */   
/*     */   abstract <P_IN> Spliterator<E_OUT> wrap(PipelineHelper<E_OUT> paramPipelineHelper, Supplier<Spliterator<P_IN>> paramSupplier, boolean paramBoolean);
/*     */   
/*     */   abstract Spliterator<E_OUT> lazySpliterator(Supplier<? extends Spliterator<E_OUT>> paramSupplier);
/*     */   
/*     */   abstract void forEachWithCancel(Spliterator<E_OUT> paramSpliterator, Sink<E_OUT> paramSink);
/*     */   
/*     */   abstract Node.Builder<E_OUT> makeNodeBuilder(long paramLong, IntFunction<E_OUT[]> paramIntFunction);
/*     */   
/*     */   abstract boolean opIsStateful();
/*     */   
/*     */   abstract Sink<E_IN> opWrapSink(int paramInt, Sink<E_OUT> paramSink);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\AbstractPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */