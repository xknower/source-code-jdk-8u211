/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import sun.misc.Unsafe;
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
/*      */ public class CompletableFuture<T>
/*      */   implements Future<T>, CompletionStage<T>
/*      */ {
/*      */   volatile Object result;
/*      */   volatile Completion stack;
/*      */   
/*      */   final boolean internalComplete(Object paramObject) {
/*  222 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, paramObject);
/*      */   }
/*      */   
/*      */   final boolean casStack(Completion paramCompletion1, Completion paramCompletion2) {
/*  226 */     return UNSAFE.compareAndSwapObject(this, STACK, paramCompletion1, paramCompletion2);
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean tryPushStack(Completion paramCompletion) {
/*  231 */     Completion completion = this.stack;
/*  232 */     lazySetNext(paramCompletion, completion);
/*  233 */     return UNSAFE.compareAndSwapObject(this, STACK, completion, paramCompletion);
/*      */   }
/*      */   final void pushStack(Completion paramCompletion) {
/*      */     do {
/*      */     
/*  238 */     } while (!tryPushStack(paramCompletion));
/*      */   }
/*      */   
/*      */   static final class AltResult {
/*      */     final Throwable ex;
/*      */     
/*      */     AltResult(Throwable param1Throwable) {
/*  245 */       this.ex = param1Throwable;
/*      */     }
/*      */   }
/*      */   
/*  249 */   static final AltResult NIL = new AltResult(null);
/*      */ 
/*      */   
/*      */   final boolean completeNull() {
/*  253 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, NIL);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final Object encodeValue(T paramT) {
/*  259 */     return (paramT == null) ? NIL : paramT;
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean completeValue(T paramT) {
/*  264 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, (paramT == null) ? NIL : paramT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static AltResult encodeThrowable(Throwable paramThrowable) {
/*  273 */     return new AltResult((paramThrowable instanceof CompletionException) ? paramThrowable : new CompletionException(paramThrowable));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeThrowable(Throwable paramThrowable) {
/*  279 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  280 */         encodeThrowable(paramThrowable));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object encodeThrowable(Throwable paramThrowable, Object paramObject) {
/*  291 */     if (!(paramThrowable instanceof CompletionException)) {
/*  292 */       paramThrowable = new CompletionException(paramThrowable);
/*  293 */     } else if (paramObject instanceof AltResult && paramThrowable == ((AltResult)paramObject).ex) {
/*  294 */       return paramObject;
/*  295 */     }  return new AltResult(paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeThrowable(Throwable paramThrowable, Object paramObject) {
/*  307 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  308 */         encodeThrowable(paramThrowable, paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object encodeOutcome(T paramT, Throwable paramThrowable) {
/*  317 */     return (paramThrowable == null) ? ((paramT == null) ? NIL : paramT) : encodeThrowable(paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object encodeRelay(Object paramObject) {
/*      */     Throwable throwable;
/*  326 */     return (paramObject instanceof AltResult && (throwable = ((AltResult)paramObject).ex) != null && !(throwable instanceof CompletionException)) ? new AltResult(new CompletionException(throwable)) : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeRelay(Object paramObject) {
/*  337 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  338 */         encodeRelay(paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T reportGet(Object paramObject) throws InterruptedException, ExecutionException {
/*  346 */     if (paramObject == null)
/*  347 */       throw new InterruptedException(); 
/*  348 */     if (paramObject instanceof AltResult) {
/*      */       Throwable throwable1;
/*  350 */       if ((throwable1 = ((AltResult)paramObject).ex) == null)
/*  351 */         return null; 
/*  352 */       if (throwable1 instanceof CancellationException)
/*  353 */         throw (CancellationException)throwable1;  Throwable throwable2;
/*  354 */       if (throwable1 instanceof CompletionException && (
/*  355 */         throwable2 = throwable1.getCause()) != null)
/*  356 */         throwable1 = throwable2; 
/*  357 */       throw new ExecutionException(throwable1);
/*      */     } 
/*  359 */     return (T)paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T reportJoin(Object paramObject) {
/*  367 */     if (paramObject instanceof AltResult) {
/*      */       Throwable throwable;
/*  369 */       if ((throwable = ((AltResult)paramObject).ex) == null)
/*  370 */         return null; 
/*  371 */       if (throwable instanceof CancellationException)
/*  372 */         throw (CancellationException)throwable; 
/*  373 */       if (throwable instanceof CompletionException)
/*  374 */         throw (CompletionException)throwable; 
/*  375 */       throw new CompletionException(throwable);
/*      */     } 
/*  377 */     return (T)paramObject;
/*      */   }
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
/*  394 */   private static final boolean useCommonPool = (ForkJoinPool.getCommonPoolParallelism() > 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  400 */   private static final Executor asyncPool = useCommonPool ? 
/*  401 */     ForkJoinPool.commonPool() : new ThreadPerTaskExecutor(); static final int SYNC = 0; static final int ASYNC = 1; static final int NESTED = -1; private static final Unsafe UNSAFE; private static final long RESULT; private static final long STACK;
/*      */   private static final long NEXT;
/*      */   
/*      */   static final class ThreadPerTaskExecutor implements Executor { public void execute(Runnable param1Runnable) {
/*  405 */       (new Thread(param1Runnable)).start();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Executor screenExecutor(Executor paramExecutor) {
/*  413 */     if (!useCommonPool && paramExecutor == ForkJoinPool.commonPool())
/*  414 */       return asyncPool; 
/*  415 */     if (paramExecutor == null) throw new NullPointerException(); 
/*  416 */     return paramExecutor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class Completion
/*      */     extends ForkJoinTask<Void>
/*      */     implements Runnable, AsynchronousCompletionTask
/*      */   {
/*      */     volatile Completion next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void run() {
/*  442 */       tryFire(1);
/*  443 */     } public final boolean exec() { tryFire(1); return true; } public final Void getRawResult() {
/*  444 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {}
/*      */     abstract CompletableFuture<?> tryFire(int param1Int);
/*      */     abstract boolean isLive(); }
/*      */   static void lazySetNext(Completion paramCompletion1, Completion paramCompletion2) {
/*  449 */     UNSAFE.putOrderedObject(paramCompletion1, NEXT, paramCompletion2);
/*      */   }
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
/*      */   final void postComplete() {
/*  462 */     CompletableFuture completableFuture = this; Completion completion;
/*  463 */     while ((completion = completableFuture.stack) != null || (completableFuture != this && (completion = (completableFuture = this).stack) != null)) {
/*      */       Completion completion1;
/*      */       
/*  466 */       if (completableFuture.casStack(completion, completion1 = completion.next)) {
/*  467 */         if (completion1 != null) {
/*  468 */           if (completableFuture != this) {
/*  469 */             pushStack(completion);
/*      */             continue;
/*      */           } 
/*  472 */           completion.next = null;
/*      */         }  CompletableFuture<?> completableFuture1;
/*  474 */         completableFuture = ((completableFuture1 = completion.tryFire(-1)) == null) ? this : completableFuture1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final void cleanStack() {
/*  481 */     for (Completion completion1 = null, completion2 = this.stack; completion2 != null; ) {
/*  482 */       Completion completion = completion2.next;
/*  483 */       if (completion2.isLive()) {
/*  484 */         completion1 = completion2;
/*  485 */         completion2 = completion; continue;
/*      */       } 
/*  487 */       if (completion1 == null) {
/*  488 */         casStack(completion2, completion);
/*  489 */         completion2 = this.stack;
/*      */         continue;
/*      */       } 
/*  492 */       completion1.next = completion;
/*  493 */       if (completion1.isLive()) {
/*  494 */         completion2 = completion; continue;
/*      */       } 
/*  496 */       completion1 = null;
/*  497 */       completion2 = this.stack;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class UniCompletion<T, V>
/*      */     extends Completion
/*      */   {
/*      */     Executor executor;
/*      */     
/*      */     CompletableFuture<V> dep;
/*      */     
/*      */     CompletableFuture<T> src;
/*      */ 
/*      */     
/*      */     UniCompletion(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1) {
/*  514 */       this.executor = param1Executor; this.dep = param1CompletableFuture; this.src = param1CompletableFuture1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean claim() {
/*  524 */       Executor executor = this.executor;
/*  525 */       if (compareAndSetForkJoinTaskTag((short)0, (short)1)) {
/*  526 */         if (executor == null)
/*  527 */           return true; 
/*  528 */         this.executor = null;
/*  529 */         executor.execute(this);
/*      */       } 
/*  531 */       return false;
/*      */     }
/*      */     final boolean isLive() {
/*  534 */       return (this.dep != null);
/*      */     }
/*      */   }
/*      */   
/*      */   final void push(UniCompletion<?, ?> paramUniCompletion) {
/*  539 */     if (paramUniCompletion != null) {
/*  540 */       while (this.result == null && !tryPushStack(paramUniCompletion)) {
/*  541 */         lazySetNext(paramUniCompletion, null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final CompletableFuture<T> postFire(CompletableFuture<?> paramCompletableFuture, int paramInt) {
/*  551 */     if (paramCompletableFuture != null && paramCompletableFuture.stack != null)
/*  552 */       if (paramInt < 0 || paramCompletableFuture.result == null) {
/*  553 */         paramCompletableFuture.cleanStack();
/*      */       } else {
/*  555 */         paramCompletableFuture.postComplete();
/*      */       }  
/*  557 */     if (this.result != null && this.stack != null) {
/*  558 */       if (paramInt < 0) {
/*  559 */         return this;
/*      */       }
/*  561 */       postComplete();
/*      */     } 
/*  563 */     return null;
/*      */   }
/*      */   
/*      */   static final class UniApply<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     Function<? super T, ? extends V> fn;
/*      */     
/*      */     UniApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Function<? super T, ? extends V> param1Function) {
/*  572 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Function;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  576 */       if ((completableFuture = this.dep) == null || 
/*  577 */         !completableFuture.uniApply(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  578 */         return null; 
/*  579 */       this.dep = null; this.src = null; this.fn = null;
/*  580 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
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
/*      */   final <S> boolean uniApply(CompletableFuture<S> paramCompletableFuture, Function<? super S, ? extends T> paramFunction, UniApply<S, T> paramUniApply) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 106
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 106
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_0
/*      */     //   82: aload_2
/*      */     //   83: aload #6
/*      */     //   85: invokeinterface apply : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   90: invokevirtual completeValue : (Ljava/lang/Object;)Z
/*      */     //   93: pop
/*      */     //   94: goto -> 106
/*      */     //   97: astore #6
/*      */     //   99: aload_0
/*      */     //   100: aload #6
/*      */     //   102: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   105: pop
/*      */     //   106: iconst_1
/*      */     //   107: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #588	-> 0
/*      */     //   #589	-> 18
/*      */     //   #590	-> 20
/*      */     //   #591	-> 27
/*      */     //   #592	-> 35
/*      */     //   #593	-> 49
/*      */     //   #594	-> 58
/*      */     //   #596	-> 61
/*      */     //   #599	-> 64
/*      */     //   #600	-> 75
/*      */     //   #601	-> 77
/*      */     //   #602	-> 81
/*      */     //   #605	-> 94
/*      */     //   #603	-> 97
/*      */     //   #604	-> 99
/*      */     //   #607	-> 106
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	97	java/lang/Throwable
/*      */     //   77	94	97	java/lang/Throwable
/*      */   }
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
/*      */   private <V> CompletableFuture<V> uniApplyStage(Executor paramExecutor, Function<? super T, ? extends V> paramFunction) {
/*  612 */     if (paramFunction == null) throw new NullPointerException(); 
/*  613 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/*  614 */     if (paramExecutor != null || !completableFuture.uniApply(this, paramFunction, null)) {
/*  615 */       UniApply<T, V> uniApply = new UniApply<>(paramExecutor, completableFuture, this, paramFunction);
/*  616 */       push(uniApply);
/*  617 */       uniApply.tryFire(0);
/*      */     } 
/*  619 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniAccept<T>
/*      */     extends UniCompletion<T, Void> {
/*      */     Consumer<? super T> fn;
/*      */     
/*      */     UniAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Consumer<? super T> param1Consumer) {
/*  627 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Consumer;
/*      */     } final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  631 */       if ((completableFuture = this.dep) == null || 
/*  632 */         !completableFuture.uniAccept(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  633 */         return null; 
/*  634 */       this.dep = null; this.src = null; this.fn = null;
/*  635 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
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
/*      */   final <S> boolean uniAccept(CompletableFuture<S> paramCompletableFuture, Consumer<? super S> paramConsumer, UniAccept<S> paramUniAccept) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 106
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 106
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_2
/*      */     //   82: aload #6
/*      */     //   84: invokeinterface accept : (Ljava/lang/Object;)V
/*      */     //   89: aload_0
/*      */     //   90: invokevirtual completeNull : ()Z
/*      */     //   93: pop
/*      */     //   94: goto -> 106
/*      */     //   97: astore #6
/*      */     //   99: aload_0
/*      */     //   100: aload #6
/*      */     //   102: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   105: pop
/*      */     //   106: iconst_1
/*      */     //   107: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #642	-> 0
/*      */     //   #643	-> 18
/*      */     //   #644	-> 20
/*      */     //   #645	-> 27
/*      */     //   #646	-> 35
/*      */     //   #647	-> 49
/*      */     //   #648	-> 58
/*      */     //   #650	-> 61
/*      */     //   #653	-> 64
/*      */     //   #654	-> 75
/*      */     //   #655	-> 77
/*      */     //   #656	-> 81
/*      */     //   #657	-> 89
/*      */     //   #660	-> 94
/*      */     //   #658	-> 97
/*      */     //   #659	-> 99
/*      */     //   #662	-> 106
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	97	java/lang/Throwable
/*      */     //   77	94	97	java/lang/Throwable
/*      */   }
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
/*      */   private CompletableFuture<Void> uniAcceptStage(Executor paramExecutor, Consumer<? super T> paramConsumer) {
/*  667 */     if (paramConsumer == null) throw new NullPointerException(); 
/*  668 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  669 */     if (paramExecutor != null || !completableFuture.uniAccept(this, paramConsumer, null)) {
/*  670 */       UniAccept<T> uniAccept = new UniAccept<>(paramExecutor, completableFuture, this, paramConsumer);
/*  671 */       push(uniAccept);
/*  672 */       uniAccept.tryFire(0);
/*      */     } 
/*  674 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniRun<T>
/*      */     extends UniCompletion<T, Void> {
/*      */     Runnable fn;
/*      */     
/*      */     UniRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Runnable param1Runnable) {
/*  682 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Runnable;
/*      */     } final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  686 */       if ((completableFuture = this.dep) == null || 
/*  687 */         !completableFuture.uniRun(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  688 */         return null; 
/*  689 */       this.dep = null; this.src = null; this.fn = null;
/*  690 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean uniRun(CompletableFuture<?> paramCompletableFuture, Runnable paramRunnable, UniRun<?> paramUniRun) {
/*      */     Object object;
/*  696 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramRunnable == null)
/*  697 */       return false; 
/*  698 */     if (this.result == null) {
/*  699 */       Throwable throwable; if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null) {
/*  700 */         completeThrowable(throwable, object);
/*      */       } else {
/*      */         try {
/*  703 */           if (paramUniRun != null && !paramUniRun.claim())
/*  704 */             return false; 
/*  705 */           paramRunnable.run();
/*  706 */           completeNull();
/*  707 */         } catch (Throwable throwable1) {
/*  708 */           completeThrowable(throwable1);
/*      */         } 
/*      */       } 
/*  711 */     }  return true;
/*      */   }
/*      */   
/*      */   private CompletableFuture<Void> uniRunStage(Executor paramExecutor, Runnable paramRunnable) {
/*  715 */     if (paramRunnable == null) throw new NullPointerException(); 
/*  716 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  717 */     if (paramExecutor != null || !completableFuture.uniRun(this, paramRunnable, null)) {
/*  718 */       UniRun<?, ?> uniRun = new UniRun(paramExecutor, completableFuture, this, paramRunnable);
/*  719 */       push(uniRun);
/*  720 */       uniRun.tryFire(0);
/*      */     } 
/*  722 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniWhenComplete<T>
/*      */     extends UniCompletion<T, T>
/*      */   {
/*      */     BiConsumer<? super T, ? super Throwable> fn;
/*      */     
/*      */     UniWhenComplete(Executor param1Executor, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2, BiConsumer<? super T, ? super Throwable> param1BiConsumer) {
/*  731 */       super(param1Executor, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiConsumer;
/*      */     } final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  735 */       if ((completableFuture1 = this.dep) == null || 
/*  736 */         !completableFuture1.uniWhenComplete(completableFuture2 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  737 */         return null; 
/*  738 */       this.dep = null; this.src = null; this.fn = null;
/*  739 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean uniWhenComplete(CompletableFuture<T> paramCompletableFuture, BiConsumer<? super T, ? super Throwable> paramBiConsumer, UniWhenComplete<T> paramUniWhenComplete) {
/*  746 */     Throwable throwable = null; Object object;
/*  747 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramBiConsumer == null)
/*  748 */       return false; 
/*  749 */     if (this.result == null) {
/*      */       try {
/*  751 */         Object object1; if (paramUniWhenComplete != null && !paramUniWhenComplete.claim())
/*  752 */           return false; 
/*  753 */         if (object instanceof AltResult) {
/*  754 */           throwable = ((AltResult)object).ex;
/*  755 */           object1 = null;
/*      */         } else {
/*  757 */           Object object2 = object;
/*  758 */           object1 = object2;
/*      */         } 
/*  760 */         paramBiConsumer.accept((T)object1, throwable);
/*  761 */         if (throwable == null) {
/*  762 */           internalComplete(object);
/*  763 */           return true;
/*      */         } 
/*  765 */       } catch (Throwable throwable1) {
/*  766 */         if (throwable == null)
/*  767 */           throwable = throwable1; 
/*      */       } 
/*  769 */       completeThrowable(throwable, object);
/*      */     } 
/*  771 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<T> uniWhenCompleteStage(Executor paramExecutor, BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/*  776 */     if (paramBiConsumer == null) throw new NullPointerException(); 
/*  777 */     CompletableFuture<T> completableFuture = new CompletableFuture();
/*  778 */     if (paramExecutor != null || !completableFuture.uniWhenComplete(this, paramBiConsumer, null)) {
/*  779 */       UniWhenComplete<T> uniWhenComplete = new UniWhenComplete<>(paramExecutor, completableFuture, this, paramBiConsumer);
/*  780 */       push(uniWhenComplete);
/*  781 */       uniWhenComplete.tryFire(0);
/*      */     } 
/*  783 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniHandle<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     BiFunction<? super T, Throwable, ? extends V> fn;
/*      */     
/*      */     UniHandle(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, BiFunction<? super T, Throwable, ? extends V> param1BiFunction) {
/*  792 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1BiFunction;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  796 */       if ((completableFuture = this.dep) == null || 
/*  797 */         !completableFuture.uniHandle(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  798 */         return null; 
/*  799 */       this.dep = null; this.src = null; this.fn = null;
/*  800 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final <S> boolean uniHandle(CompletableFuture<S> paramCompletableFuture, BiFunction<? super S, Throwable, ? extends T> paramBiFunction, UniHandle<S, T> paramUniHandle) {
/*      */     Object object;
/*  808 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramBiFunction == null)
/*  809 */       return false; 
/*  810 */     if (this.result == null) {
/*      */       try {
/*  812 */         Object object1; Throwable throwable; if (paramUniHandle != null && !paramUniHandle.claim())
/*  813 */           return false; 
/*  814 */         if (object instanceof AltResult) {
/*  815 */           throwable = ((AltResult)object).ex;
/*  816 */           object1 = null;
/*      */         } else {
/*  818 */           throwable = null;
/*  819 */           Object object2 = object;
/*  820 */           object1 = object2;
/*      */         } 
/*  822 */         completeValue(paramBiFunction.apply((S)object1, throwable));
/*  823 */       } catch (Throwable throwable) {
/*  824 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/*  827 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private <V> CompletableFuture<V> uniHandleStage(Executor paramExecutor, BiFunction<? super T, Throwable, ? extends V> paramBiFunction) {
/*  832 */     if (paramBiFunction == null) throw new NullPointerException(); 
/*  833 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/*  834 */     if (paramExecutor != null || !completableFuture.uniHandle(this, paramBiFunction, null)) {
/*  835 */       UniHandle<T, V> uniHandle = new UniHandle<>(paramExecutor, completableFuture, this, paramBiFunction);
/*  836 */       push(uniHandle);
/*  837 */       uniHandle.tryFire(0);
/*      */     } 
/*  839 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniExceptionally<T>
/*      */     extends UniCompletion<T, T> {
/*      */     Function<? super Throwable, ? extends T> fn;
/*      */     
/*      */     UniExceptionally(CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2, Function<? super Throwable, ? extends T> param1Function) {
/*  847 */       super((Executor)null, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Function;
/*      */     }
/*      */     final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  852 */       if ((completableFuture1 = this.dep) == null || !completableFuture1.uniExceptionally(completableFuture2 = this.src, this.fn, this))
/*  853 */         return null; 
/*  854 */       this.dep = null; this.src = null; this.fn = null;
/*  855 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean uniExceptionally(CompletableFuture<T> paramCompletableFuture, Function<? super Throwable, ? extends T> paramFunction, UniExceptionally<T> paramUniExceptionally) {
/*      */     Object object;
/*  863 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramFunction == null)
/*  864 */       return false; 
/*  865 */     if (this.result == null) {
/*      */       try {
/*  867 */         Throwable throwable; if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null)
/*  868 */         { if (paramUniExceptionally != null && !paramUniExceptionally.claim())
/*  869 */             return false; 
/*  870 */           completeValue(paramFunction.apply(throwable)); }
/*      */         else
/*  872 */         { internalComplete(object); } 
/*  873 */       } catch (Throwable throwable) {
/*  874 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/*  877 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<T> uniExceptionallyStage(Function<Throwable, ? extends T> paramFunction) {
/*  882 */     if (paramFunction == null) throw new NullPointerException(); 
/*  883 */     CompletableFuture<T> completableFuture = new CompletableFuture();
/*  884 */     if (!completableFuture.uniExceptionally(this, paramFunction, null)) {
/*  885 */       UniExceptionally<T> uniExceptionally = new UniExceptionally<>(completableFuture, this, paramFunction);
/*  886 */       push(uniExceptionally);
/*  887 */       uniExceptionally.tryFire(0);
/*      */     } 
/*  889 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniRelay<T>
/*      */     extends UniCompletion<T, T> {
/*      */     UniRelay(CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2) {
/*  895 */       super((Executor)null, param1CompletableFuture1, param1CompletableFuture2);
/*      */     } final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  899 */       if ((completableFuture1 = this.dep) == null || !completableFuture1.uniRelay(completableFuture2 = this.src))
/*  900 */         return null; 
/*  901 */       this.src = null; this.dep = null;
/*  902 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean uniRelay(CompletableFuture<T> paramCompletableFuture) {
/*      */     Object object;
/*  908 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null)
/*  909 */       return false; 
/*  910 */     if (this.result == null)
/*  911 */       completeRelay(object); 
/*  912 */     return true;
/*      */   }
/*      */   
/*      */   static final class UniCompose<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     Function<? super T, ? extends CompletionStage<V>> fn;
/*      */     
/*      */     UniCompose(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Function<? super T, ? extends CompletionStage<V>> param1Function) {
/*  921 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Function;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  925 */       if ((completableFuture = this.dep) == null || 
/*  926 */         !completableFuture.uniCompose(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  927 */         return null; 
/*  928 */       this.dep = null; this.src = null; this.fn = null;
/*  929 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
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
/*      */   final <S> boolean uniCompose(CompletableFuture<S> paramCompletableFuture, Function<? super S, ? extends CompletionStage<T>> paramFunction, UniCompose<S, T> paramUniCompose) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 163
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 163
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_2
/*      */     //   82: aload #6
/*      */     //   84: invokeinterface apply : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   89: checkcast java/util/concurrent/CompletionStage
/*      */     //   92: invokeinterface toCompletableFuture : ()Ljava/util/concurrent/CompletableFuture;
/*      */     //   97: astore #7
/*      */     //   99: aload #7
/*      */     //   101: getfield result : Ljava/lang/Object;
/*      */     //   104: ifnull -> 116
/*      */     //   107: aload_0
/*      */     //   108: aload #7
/*      */     //   110: invokevirtual uniRelay : (Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   113: ifne -> 151
/*      */     //   116: new java/util/concurrent/CompletableFuture$UniRelay
/*      */     //   119: dup
/*      */     //   120: aload_0
/*      */     //   121: aload #7
/*      */     //   123: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   126: astore #8
/*      */     //   128: aload #7
/*      */     //   130: aload #8
/*      */     //   132: invokevirtual push : (Ljava/util/concurrent/CompletableFuture$UniCompletion;)V
/*      */     //   135: aload #8
/*      */     //   137: iconst_0
/*      */     //   138: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   141: pop
/*      */     //   142: aload_0
/*      */     //   143: getfield result : Ljava/lang/Object;
/*      */     //   146: ifnonnull -> 151
/*      */     //   149: iconst_0
/*      */     //   150: ireturn
/*      */     //   151: goto -> 163
/*      */     //   154: astore #6
/*      */     //   156: aload_0
/*      */     //   157: aload #6
/*      */     //   159: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   162: pop
/*      */     //   163: iconst_1
/*      */     //   164: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #938	-> 0
/*      */     //   #939	-> 18
/*      */     //   #940	-> 20
/*      */     //   #941	-> 27
/*      */     //   #942	-> 35
/*      */     //   #943	-> 49
/*      */     //   #944	-> 58
/*      */     //   #946	-> 61
/*      */     //   #949	-> 64
/*      */     //   #950	-> 75
/*      */     //   #951	-> 77
/*      */     //   #952	-> 81
/*      */     //   #953	-> 99
/*      */     //   #954	-> 116
/*      */     //   #955	-> 128
/*      */     //   #956	-> 135
/*      */     //   #957	-> 142
/*      */     //   #958	-> 149
/*      */     //   #962	-> 151
/*      */     //   #960	-> 154
/*      */     //   #961	-> 156
/*      */     //   #964	-> 163
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	154	java/lang/Throwable
/*      */     //   77	150	154	java/lang/Throwable
/*      */   }
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
/*      */   private <V> CompletableFuture<V> uniComposeStage(Executor paramExecutor, Function<? super T, ? extends CompletionStage<V>> paramFunction) {
/*  969 */     if (paramFunction == null) throw new NullPointerException(); 
/*      */     Object object;
/*  971 */     if (paramExecutor == null && (object = this.result) != null) {
/*      */       
/*  973 */       if (object instanceof AltResult) {
/*  974 */         Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/*  975 */           return new CompletableFuture(encodeThrowable(throwable, object));
/*      */         }
/*  977 */         object = null;
/*      */       } 
/*      */       try {
/*  980 */         Object object1 = object;
/*  981 */         CompletableFuture<?> completableFuture1 = ((CompletionStage)paramFunction.apply((T)object1)).toCompletableFuture();
/*  982 */         Object object2 = completableFuture1.result;
/*  983 */         if (object2 != null)
/*  984 */           return new CompletableFuture(encodeRelay(object2)); 
/*  985 */         CompletableFuture<?> completableFuture2 = new CompletableFuture();
/*  986 */         UniRelay<?, ?> uniRelay = new UniRelay(completableFuture2, completableFuture1);
/*  987 */         completableFuture1.push(uniRelay);
/*  988 */         uniRelay.tryFire(0);
/*  989 */         return (CompletableFuture)completableFuture2;
/*  990 */       } catch (Throwable throwable) {
/*  991 */         return new CompletableFuture(encodeThrowable(throwable));
/*      */       } 
/*      */     } 
/*  994 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/*  995 */     UniCompose<T, V> uniCompose = new UniCompose<>(paramExecutor, completableFuture, this, paramFunction);
/*  996 */     push(uniCompose);
/*  997 */     uniCompose.tryFire(0);
/*  998 */     return completableFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class BiCompletion<T, U, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     CompletableFuture<U> snd;
/*      */ 
/*      */     
/*      */     BiCompletion(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1009 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.snd = param1CompletableFuture2;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class CoCompletion extends Completion {
/*      */     CompletableFuture.BiCompletion<?, ?, ?> base;
/*      */     
/*      */     CoCompletion(CompletableFuture.BiCompletion<?, ?, ?> param1BiCompletion) {
/* 1017 */       this.base = param1BiCompletion; } final CompletableFuture<?> tryFire(int param1Int) {
/*      */       CompletableFuture.BiCompletion<?, ?, ?> biCompletion;
/*      */       CompletableFuture<?> completableFuture;
/* 1020 */       if ((biCompletion = this.base) == null || (completableFuture = biCompletion.tryFire(param1Int)) == null)
/* 1021 */         return null; 
/* 1022 */       this.base = null;
/* 1023 */       return completableFuture;
/*      */     }
/*      */     final boolean isLive() {
/*      */       CompletableFuture.BiCompletion<?, ?, ?> biCompletion;
/* 1027 */       return ((biCompletion = this.base) != null && biCompletion.dep != null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final void bipush(CompletableFuture<?> paramCompletableFuture, BiCompletion<?, ?, ?> paramBiCompletion) {
/* 1033 */     if (paramBiCompletion != null) {
/*      */       Object object;
/* 1035 */       while ((object = this.result) == null && !tryPushStack(paramBiCompletion))
/* 1036 */         lazySetNext(paramBiCompletion, null); 
/* 1037 */       if (paramCompletableFuture != null && paramCompletableFuture != this && paramCompletableFuture.result == null) {
/* 1038 */         Completion completion = (Completion)((object != null) ? paramBiCompletion : new CoCompletion(paramBiCompletion));
/* 1039 */         while (paramCompletableFuture.result == null && !paramCompletableFuture.tryPushStack(completion)) {
/* 1040 */           lazySetNext(completion, null);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final CompletableFuture<T> postFire(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, int paramInt) {
/* 1048 */     if (paramCompletableFuture2 != null && paramCompletableFuture2.stack != null)
/* 1049 */       if (paramInt < 0 || paramCompletableFuture2.result == null) {
/* 1050 */         paramCompletableFuture2.cleanStack();
/*      */       } else {
/* 1052 */         paramCompletableFuture2.postComplete();
/*      */       }  
/* 1054 */     return postFire(paramCompletableFuture1, paramInt);
/*      */   }
/*      */   
/*      */   static final class BiApply<T, U, V>
/*      */     extends BiCompletion<T, U, V>
/*      */   {
/*      */     BiFunction<? super T, ? super U, ? extends V> fn;
/*      */     
/*      */     BiApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, BiFunction<? super T, ? super U, ? extends V> param1BiFunction) {
/* 1063 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiFunction;
/*      */     }
/*      */     final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1069 */       if ((completableFuture = this.dep) == null || 
/* 1070 */         !completableFuture.biApply(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1071 */         return null; 
/* 1072 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1073 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
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
/*      */   final <R, S> boolean biApply(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, BiFunction<? super R, ? super S, ? extends T> paramBiFunction, BiApply<R, S, T> paramBiApply) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 32
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #5
/*      */     //   11: ifnull -> 32
/*      */     //   14: aload_2
/*      */     //   15: ifnull -> 32
/*      */     //   18: aload_2
/*      */     //   19: getfield result : Ljava/lang/Object;
/*      */     //   22: dup
/*      */     //   23: astore #6
/*      */     //   25: ifnull -> 32
/*      */     //   28: aload_3
/*      */     //   29: ifnonnull -> 34
/*      */     //   32: iconst_0
/*      */     //   33: ireturn
/*      */     //   34: aload_0
/*      */     //   35: getfield result : Ljava/lang/Object;
/*      */     //   38: ifnonnull -> 165
/*      */     //   41: aload #5
/*      */     //   43: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   46: ifeq -> 78
/*      */     //   49: aload #5
/*      */     //   51: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   54: getfield ex : Ljava/lang/Throwable;
/*      */     //   57: dup
/*      */     //   58: astore #7
/*      */     //   60: ifnull -> 75
/*      */     //   63: aload_0
/*      */     //   64: aload #7
/*      */     //   66: aload #5
/*      */     //   68: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   71: pop
/*      */     //   72: goto -> 165
/*      */     //   75: aconst_null
/*      */     //   76: astore #5
/*      */     //   78: aload #6
/*      */     //   80: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   83: ifeq -> 115
/*      */     //   86: aload #6
/*      */     //   88: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   91: getfield ex : Ljava/lang/Throwable;
/*      */     //   94: dup
/*      */     //   95: astore #7
/*      */     //   97: ifnull -> 112
/*      */     //   100: aload_0
/*      */     //   101: aload #7
/*      */     //   103: aload #6
/*      */     //   105: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   108: pop
/*      */     //   109: goto -> 165
/*      */     //   112: aconst_null
/*      */     //   113: astore #6
/*      */     //   115: aload #4
/*      */     //   117: ifnull -> 130
/*      */     //   120: aload #4
/*      */     //   122: invokevirtual claim : ()Z
/*      */     //   125: ifne -> 130
/*      */     //   128: iconst_0
/*      */     //   129: ireturn
/*      */     //   130: aload #5
/*      */     //   132: astore #8
/*      */     //   134: aload #6
/*      */     //   136: astore #9
/*      */     //   138: aload_0
/*      */     //   139: aload_3
/*      */     //   140: aload #8
/*      */     //   142: aload #9
/*      */     //   144: invokeinterface apply : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   149: invokevirtual completeValue : (Ljava/lang/Object;)Z
/*      */     //   152: pop
/*      */     //   153: goto -> 165
/*      */     //   156: astore #8
/*      */     //   158: aload_0
/*      */     //   159: aload #8
/*      */     //   161: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   164: pop
/*      */     //   165: iconst_1
/*      */     //   166: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1082	-> 0
/*      */     //   #1084	-> 32
/*      */     //   #1085	-> 34
/*      */     //   #1086	-> 41
/*      */     //   #1087	-> 49
/*      */     //   #1088	-> 63
/*      */     //   #1089	-> 72
/*      */     //   #1091	-> 75
/*      */     //   #1093	-> 78
/*      */     //   #1094	-> 86
/*      */     //   #1095	-> 100
/*      */     //   #1096	-> 109
/*      */     //   #1098	-> 112
/*      */     //   #1101	-> 115
/*      */     //   #1102	-> 128
/*      */     //   #1103	-> 130
/*      */     //   #1104	-> 134
/*      */     //   #1105	-> 138
/*      */     //   #1108	-> 153
/*      */     //   #1106	-> 156
/*      */     //   #1107	-> 158
/*      */     //   #1110	-> 165
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   115	129	156	java/lang/Throwable
/*      */     //   130	153	156	java/lang/Throwable
/*      */   }
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
/*      */   private <U, V> CompletableFuture<V> biApplyStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/*      */     CompletableFuture<U> completableFuture;
/* 1117 */     if (paramBiFunction == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1118 */       throw new NullPointerException(); 
/* 1119 */     CompletableFuture<V> completableFuture1 = new CompletableFuture();
/* 1120 */     if (paramExecutor != null || !completableFuture1.biApply(this, completableFuture, paramBiFunction, null)) {
/* 1121 */       BiApply<T, U, V> biApply = new BiApply<>(paramExecutor, completableFuture1, this, completableFuture, paramBiFunction);
/* 1122 */       bipush(completableFuture, biApply);
/* 1123 */       biApply.tryFire(0);
/*      */     } 
/* 1125 */     return completableFuture1;
/*      */   }
/*      */   
/*      */   static final class BiAccept<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     BiConsumer<? super T, ? super U> fn;
/*      */     
/*      */     BiAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, BiConsumer<? super T, ? super U> param1BiConsumer) {
/* 1134 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiConsumer;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1140 */       if ((completableFuture = this.dep) == null || 
/* 1141 */         !completableFuture.biAccept(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1142 */         return null; 
/* 1143 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1144 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
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
/*      */   final <R, S> boolean biAccept(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, BiConsumer<? super R, ? super S> paramBiConsumer, BiAccept<R, S> paramBiAccept) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 32
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #5
/*      */     //   11: ifnull -> 32
/*      */     //   14: aload_2
/*      */     //   15: ifnull -> 32
/*      */     //   18: aload_2
/*      */     //   19: getfield result : Ljava/lang/Object;
/*      */     //   22: dup
/*      */     //   23: astore #6
/*      */     //   25: ifnull -> 32
/*      */     //   28: aload_3
/*      */     //   29: ifnonnull -> 34
/*      */     //   32: iconst_0
/*      */     //   33: ireturn
/*      */     //   34: aload_0
/*      */     //   35: getfield result : Ljava/lang/Object;
/*      */     //   38: ifnonnull -> 165
/*      */     //   41: aload #5
/*      */     //   43: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   46: ifeq -> 78
/*      */     //   49: aload #5
/*      */     //   51: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   54: getfield ex : Ljava/lang/Throwable;
/*      */     //   57: dup
/*      */     //   58: astore #7
/*      */     //   60: ifnull -> 75
/*      */     //   63: aload_0
/*      */     //   64: aload #7
/*      */     //   66: aload #5
/*      */     //   68: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   71: pop
/*      */     //   72: goto -> 165
/*      */     //   75: aconst_null
/*      */     //   76: astore #5
/*      */     //   78: aload #6
/*      */     //   80: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   83: ifeq -> 115
/*      */     //   86: aload #6
/*      */     //   88: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   91: getfield ex : Ljava/lang/Throwable;
/*      */     //   94: dup
/*      */     //   95: astore #7
/*      */     //   97: ifnull -> 112
/*      */     //   100: aload_0
/*      */     //   101: aload #7
/*      */     //   103: aload #6
/*      */     //   105: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   108: pop
/*      */     //   109: goto -> 165
/*      */     //   112: aconst_null
/*      */     //   113: astore #6
/*      */     //   115: aload #4
/*      */     //   117: ifnull -> 130
/*      */     //   120: aload #4
/*      */     //   122: invokevirtual claim : ()Z
/*      */     //   125: ifne -> 130
/*      */     //   128: iconst_0
/*      */     //   129: ireturn
/*      */     //   130: aload #5
/*      */     //   132: astore #8
/*      */     //   134: aload #6
/*      */     //   136: astore #9
/*      */     //   138: aload_3
/*      */     //   139: aload #8
/*      */     //   141: aload #9
/*      */     //   143: invokeinterface accept : (Ljava/lang/Object;Ljava/lang/Object;)V
/*      */     //   148: aload_0
/*      */     //   149: invokevirtual completeNull : ()Z
/*      */     //   152: pop
/*      */     //   153: goto -> 165
/*      */     //   156: astore #8
/*      */     //   158: aload_0
/*      */     //   159: aload #8
/*      */     //   161: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   164: pop
/*      */     //   165: iconst_1
/*      */     //   166: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1153	-> 0
/*      */     //   #1155	-> 32
/*      */     //   #1156	-> 34
/*      */     //   #1157	-> 41
/*      */     //   #1158	-> 49
/*      */     //   #1159	-> 63
/*      */     //   #1160	-> 72
/*      */     //   #1162	-> 75
/*      */     //   #1164	-> 78
/*      */     //   #1165	-> 86
/*      */     //   #1166	-> 100
/*      */     //   #1167	-> 109
/*      */     //   #1169	-> 112
/*      */     //   #1172	-> 115
/*      */     //   #1173	-> 128
/*      */     //   #1174	-> 130
/*      */     //   #1175	-> 134
/*      */     //   #1176	-> 138
/*      */     //   #1177	-> 148
/*      */     //   #1180	-> 153
/*      */     //   #1178	-> 156
/*      */     //   #1179	-> 158
/*      */     //   #1182	-> 165
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   115	129	156	java/lang/Throwable
/*      */     //   130	153	156	java/lang/Throwable
/*      */   }
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
/*      */   private <U> CompletableFuture<Void> biAcceptStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/*      */     CompletableFuture<U> completableFuture;
/* 1189 */     if (paramBiConsumer == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1190 */       throw new NullPointerException(); 
/* 1191 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1192 */     if (paramExecutor != null || !completableFuture1.biAccept(this, completableFuture, paramBiConsumer, null)) {
/* 1193 */       BiAccept<T, U> biAccept = new BiAccept<>(paramExecutor, completableFuture1, this, completableFuture, paramBiConsumer);
/* 1194 */       bipush(completableFuture, biAccept);
/* 1195 */       biAccept.tryFire(0);
/*      */     } 
/* 1197 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BiRun<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Runnable fn;
/*      */     
/*      */     BiRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Runnable param1Runnable) {
/* 1207 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Runnable;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1213 */       if ((completableFuture = this.dep) == null || 
/* 1214 */         !completableFuture.biRun(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1215 */         return null; 
/* 1216 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1217 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean biRun(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, BiRun<?, ?> paramBiRun) {
/*      */     Object object1;
/*      */     Object object2;
/* 1224 */     if (paramCompletableFuture1 == null || (object1 = paramCompletableFuture1.result) == null || paramCompletableFuture2 == null || (object2 = paramCompletableFuture2.result) == null || paramRunnable == null)
/*      */     {
/* 1226 */       return false; } 
/* 1227 */     if (this.result == null) {
/* 1228 */       Throwable throwable; if (object1 instanceof AltResult && (throwable = ((AltResult)object1).ex) != null) {
/* 1229 */         completeThrowable(throwable, object1);
/* 1230 */       } else if (object2 instanceof AltResult && (throwable = ((AltResult)object2).ex) != null) {
/* 1231 */         completeThrowable(throwable, object2);
/*      */       } else {
/*      */         try {
/* 1234 */           if (paramBiRun != null && !paramBiRun.claim())
/* 1235 */             return false; 
/* 1236 */           paramRunnable.run();
/* 1237 */           completeNull();
/* 1238 */         } catch (Throwable throwable1) {
/* 1239 */           completeThrowable(throwable1);
/*      */         } 
/*      */       } 
/* 1242 */     }  return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> biRunStage(Executor paramExecutor, CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/*      */     CompletableFuture<?> completableFuture;
/* 1248 */     if (paramRunnable == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1249 */       throw new NullPointerException(); 
/* 1250 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1251 */     if (paramExecutor != null || !completableFuture1.biRun(this, completableFuture, paramRunnable, null)) {
/* 1252 */       BiRun<Object, Object> biRun = new BiRun<>(paramExecutor, completableFuture1, this, completableFuture, paramRunnable);
/* 1253 */       bipush(completableFuture, biRun);
/* 1254 */       biRun.tryFire(0);
/*      */     } 
/* 1256 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BiRelay<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     BiRelay(CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1264 */       super((Executor)null, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2);
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1270 */       if ((completableFuture = this.dep) == null || !completableFuture.biRelay(completableFuture1 = this.src, completableFuture2 = this.snd))
/* 1271 */         return null; 
/* 1272 */       this.src = null; this.snd = null; this.dep = null;
/* 1273 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     } }
/*      */   
/*      */   boolean biRelay(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2) {
/*      */     Object object1;
/*      */     Object object2;
/* 1279 */     if (paramCompletableFuture1 == null || (object1 = paramCompletableFuture1.result) == null || paramCompletableFuture2 == null || (object2 = paramCompletableFuture2.result) == null)
/*      */     {
/* 1281 */       return false; } 
/* 1282 */     if (this.result == null) {
/* 1283 */       Throwable throwable; if (object1 instanceof AltResult && (throwable = ((AltResult)object1).ex) != null) {
/* 1284 */         completeThrowable(throwable, object1);
/* 1285 */       } else if (object2 instanceof AltResult && (throwable = ((AltResult)object2).ex) != null) {
/* 1286 */         completeThrowable(throwable, object2);
/*      */       } else {
/* 1288 */         completeNull();
/*      */       } 
/* 1290 */     }  return true;
/*      */   }
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
/*      */   static CompletableFuture<Void> andTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2) {
/*      */     // Byte code:
/*      */     //   0: new java/util/concurrent/CompletableFuture
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore_3
/*      */     //   8: iload_1
/*      */     //   9: iload_2
/*      */     //   10: if_icmple -> 23
/*      */     //   13: aload_3
/*      */     //   14: getstatic java/util/concurrent/CompletableFuture.NIL : Ljava/util/concurrent/CompletableFuture$AltResult;
/*      */     //   17: putfield result : Ljava/lang/Object;
/*      */     //   20: goto -> 143
/*      */     //   23: iload_1
/*      */     //   24: iload_2
/*      */     //   25: iadd
/*      */     //   26: iconst_1
/*      */     //   27: iushr
/*      */     //   28: istore #6
/*      */     //   30: iload_1
/*      */     //   31: iload #6
/*      */     //   33: if_icmpne -> 42
/*      */     //   36: aload_0
/*      */     //   37: iload_1
/*      */     //   38: aaload
/*      */     //   39: goto -> 49
/*      */     //   42: aload_0
/*      */     //   43: iload_1
/*      */     //   44: iload #6
/*      */     //   46: invokestatic andTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   49: dup
/*      */     //   50: astore #4
/*      */     //   52: ifnull -> 94
/*      */     //   55: iload_1
/*      */     //   56: iload_2
/*      */     //   57: if_icmpne -> 65
/*      */     //   60: aload #4
/*      */     //   62: goto -> 88
/*      */     //   65: iload_2
/*      */     //   66: iload #6
/*      */     //   68: iconst_1
/*      */     //   69: iadd
/*      */     //   70: if_icmpne -> 79
/*      */     //   73: aload_0
/*      */     //   74: iload_2
/*      */     //   75: aaload
/*      */     //   76: goto -> 88
/*      */     //   79: aload_0
/*      */     //   80: iload #6
/*      */     //   82: iconst_1
/*      */     //   83: iadd
/*      */     //   84: iload_2
/*      */     //   85: invokestatic andTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   88: dup
/*      */     //   89: astore #5
/*      */     //   91: ifnonnull -> 102
/*      */     //   94: new java/lang/NullPointerException
/*      */     //   97: dup
/*      */     //   98: invokespecial <init> : ()V
/*      */     //   101: athrow
/*      */     //   102: aload_3
/*      */     //   103: aload #4
/*      */     //   105: aload #5
/*      */     //   107: invokevirtual biRelay : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   110: ifne -> 143
/*      */     //   113: new java/util/concurrent/CompletableFuture$BiRelay
/*      */     //   116: dup
/*      */     //   117: aload_3
/*      */     //   118: aload #4
/*      */     //   120: aload #5
/*      */     //   122: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   125: astore #7
/*      */     //   127: aload #4
/*      */     //   129: aload #5
/*      */     //   131: aload #7
/*      */     //   133: invokevirtual bipush : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture$BiCompletion;)V
/*      */     //   136: aload #7
/*      */     //   138: iconst_0
/*      */     //   139: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   142: pop
/*      */     //   143: aload_3
/*      */     //   144: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1296	-> 0
/*      */     //   #1297	-> 8
/*      */     //   #1298	-> 13
/*      */     //   #1301	-> 23
/*      */     //   #1302	-> 30
/*      */     //   #1303	-> 46
/*      */     //   #1305	-> 85
/*      */     //   #1306	-> 94
/*      */     //   #1307	-> 102
/*      */     //   #1308	-> 113
/*      */     //   #1309	-> 127
/*      */     //   #1310	-> 136
/*      */     //   #1313	-> 143
/*      */   }
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
/*      */   final void orpush(CompletableFuture<?> paramCompletableFuture, BiCompletion<?, ?, ?> paramBiCompletion) {
/* 1320 */     if (paramBiCompletion != null) {
/* 1321 */       while ((paramCompletableFuture == null || paramCompletableFuture.result == null) && this.result == null) {
/* 1322 */         if (tryPushStack(paramBiCompletion)) {
/* 1323 */           if (paramCompletableFuture != null && paramCompletableFuture != this && paramCompletableFuture.result == null) {
/* 1324 */             CoCompletion coCompletion = new CoCompletion(paramBiCompletion);
/* 1325 */             while (this.result == null && paramCompletableFuture.result == null && 
/* 1326 */               !paramCompletableFuture.tryPushStack(coCompletion))
/* 1327 */               lazySetNext(coCompletion, null); 
/*      */           } 
/*      */           break;
/*      */         } 
/* 1331 */         lazySetNext(paramBiCompletion, null);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrApply<T, U extends T, V>
/*      */     extends BiCompletion<T, U, V>
/*      */   {
/*      */     Function<? super T, ? extends V> fn;
/*      */     
/*      */     OrApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Function<? super T, ? extends V> param1Function) {
/* 1343 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Function;
/*      */     }
/*      */     final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1349 */       if ((completableFuture = this.dep) == null || 
/* 1350 */         !completableFuture.orApply(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1351 */         return null; 
/* 1352 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1353 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S extends R> boolean orApply(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, Function<? super R, ? extends T> paramFunction, OrApply<R, S, T> paramOrApply) {
/*      */     Object object;
/* 1362 */     if (paramCompletableFuture == null || paramCompletableFuture1 == null || ((object = paramCompletableFuture.result) == null && (object = paramCompletableFuture1.result) == null) || paramFunction == null)
/*      */     {
/* 1364 */       return false; } 
/* 1365 */     if (this.result == null)
/*      */       
/* 1367 */       try { if (paramOrApply != null && !paramOrApply.claim())
/* 1368 */           return false; 
/* 1369 */         if (object instanceof AltResult)
/* 1370 */         { Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/* 1371 */             completeThrowable(throwable, object);
/*      */           } else {
/*      */             
/* 1374 */             object = null;
/*      */             
/* 1376 */             Object object2 = object;
/* 1377 */             completeValue(paramFunction.apply((R)object2));
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1382 */           return true; }  Object object1 = object; completeValue(paramFunction.apply((R)object1)); } catch (Throwable throwable) { completeThrowable(throwable); }   return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <U extends T, V> CompletableFuture<V> orApplyStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, Function<? super T, ? extends V> paramFunction) {
/*      */     CompletableFuture<U> completableFuture;
/* 1389 */     if (paramFunction == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1390 */       throw new NullPointerException(); 
/* 1391 */     CompletableFuture<V> completableFuture1 = new CompletableFuture();
/* 1392 */     if (paramExecutor != null || !completableFuture1.orApply(this, completableFuture, paramFunction, null)) {
/* 1393 */       OrApply<T, U, V> orApply = new OrApply<>(paramExecutor, completableFuture1, this, completableFuture, paramFunction);
/* 1394 */       orpush(completableFuture, orApply);
/* 1395 */       orApply.tryFire(0);
/*      */     } 
/* 1397 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrAccept<T, U extends T>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Consumer<? super T> fn;
/*      */     
/*      */     OrAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Consumer<? super T> param1Consumer) {
/* 1407 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Consumer;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1413 */       if ((completableFuture = this.dep) == null || 
/* 1414 */         !completableFuture.orAccept(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1415 */         return null; 
/* 1416 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1417 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S extends R> boolean orAccept(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, Consumer<? super R> paramConsumer, OrAccept<R, S> paramOrAccept) {
/*      */     Object object;
/* 1426 */     if (paramCompletableFuture == null || paramCompletableFuture1 == null || ((object = paramCompletableFuture.result) == null && (object = paramCompletableFuture1.result) == null) || paramConsumer == null)
/*      */     {
/* 1428 */       return false; } 
/* 1429 */     if (this.result == null)
/*      */       
/* 1431 */       try { if (paramOrAccept != null && !paramOrAccept.claim())
/* 1432 */           return false; 
/* 1433 */         if (object instanceof AltResult)
/* 1434 */         { Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/* 1435 */             completeThrowable(throwable, object);
/*      */           } else {
/*      */             
/* 1438 */             object = null;
/*      */             
/* 1440 */             Object object2 = object;
/* 1441 */             paramConsumer.accept((R)object2);
/* 1442 */             completeNull();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1447 */           return true; }  Object object1 = object; paramConsumer.accept((R)object1); completeNull(); } catch (Throwable throwable) { completeThrowable(throwable); }   return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private <U extends T> CompletableFuture<Void> orAcceptStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, Consumer<? super T> paramConsumer) {
/*      */     CompletableFuture<U> completableFuture;
/* 1453 */     if (paramConsumer == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1454 */       throw new NullPointerException(); 
/* 1455 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1456 */     if (paramExecutor != null || !completableFuture1.orAccept(this, completableFuture, paramConsumer, null)) {
/* 1457 */       OrAccept<T, U> orAccept = new OrAccept<>(paramExecutor, completableFuture1, this, completableFuture, paramConsumer);
/* 1458 */       orpush(completableFuture, orAccept);
/* 1459 */       orAccept.tryFire(0);
/*      */     } 
/* 1461 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrRun<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Runnable fn;
/*      */     
/*      */     OrRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Runnable param1Runnable) {
/* 1471 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Runnable;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1477 */       if ((completableFuture = this.dep) == null || 
/* 1478 */         !completableFuture.orRun(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1479 */         return null; 
/* 1480 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1481 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean orRun(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, OrRun<?, ?> paramOrRun) {
/*      */     Object object;
/* 1488 */     if (paramCompletableFuture1 == null || paramCompletableFuture2 == null || ((object = paramCompletableFuture1.result) == null && (object = paramCompletableFuture2.result) == null) || paramRunnable == null)
/*      */     {
/* 1490 */       return false; } 
/* 1491 */     if (this.result == null) {
/*      */       try {
/* 1493 */         if (paramOrRun != null && !paramOrRun.claim())
/* 1494 */           return false;  Throwable throwable;
/* 1495 */         if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null) {
/* 1496 */           completeThrowable(throwable, object);
/*      */         } else {
/* 1498 */           paramRunnable.run();
/* 1499 */           completeNull();
/*      */         } 
/* 1501 */       } catch (Throwable throwable) {
/* 1502 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/* 1505 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> orRunStage(Executor paramExecutor, CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/*      */     CompletableFuture<?> completableFuture;
/* 1511 */     if (paramRunnable == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1512 */       throw new NullPointerException(); 
/* 1513 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1514 */     if (paramExecutor != null || !completableFuture1.orRun(this, completableFuture, paramRunnable, null)) {
/* 1515 */       OrRun<Object, Object> orRun = new OrRun<>(paramExecutor, completableFuture1, this, completableFuture, paramRunnable);
/* 1516 */       orpush(completableFuture, orRun);
/* 1517 */       orRun.tryFire(0);
/*      */     } 
/* 1519 */     return completableFuture1;
/*      */   }
/*      */   
/*      */   static final class OrRelay<T, U>
/*      */     extends BiCompletion<T, U, Object>
/*      */   {
/*      */     OrRelay(CompletableFuture<Object> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1526 */       super((Executor)null, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2);
/*      */     }
/*      */     final CompletableFuture<Object> tryFire(int param1Int) {
/*      */       CompletableFuture<Object> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1532 */       if ((completableFuture = this.dep) == null || !completableFuture.orRelay(completableFuture1 = this.src, completableFuture2 = this.snd))
/* 1533 */         return null; 
/* 1534 */       this.src = null; this.snd = null; this.dep = null;
/* 1535 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean orRelay(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2) {
/*      */     Object object;
/* 1541 */     if (paramCompletableFuture1 == null || paramCompletableFuture2 == null || ((object = paramCompletableFuture1.result) == null && (object = paramCompletableFuture2.result) == null))
/*      */     {
/* 1543 */       return false; } 
/* 1544 */     if (this.result == null)
/* 1545 */       completeRelay(object); 
/* 1546 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CompletableFuture<Object> orTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2) {
/*      */     // Byte code:
/*      */     //   0: new java/util/concurrent/CompletableFuture
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore_3
/*      */     //   8: iload_1
/*      */     //   9: iload_2
/*      */     //   10: if_icmpgt -> 133
/*      */     //   13: iload_1
/*      */     //   14: iload_2
/*      */     //   15: iadd
/*      */     //   16: iconst_1
/*      */     //   17: iushr
/*      */     //   18: istore #6
/*      */     //   20: iload_1
/*      */     //   21: iload #6
/*      */     //   23: if_icmpne -> 32
/*      */     //   26: aload_0
/*      */     //   27: iload_1
/*      */     //   28: aaload
/*      */     //   29: goto -> 39
/*      */     //   32: aload_0
/*      */     //   33: iload_1
/*      */     //   34: iload #6
/*      */     //   36: invokestatic orTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   39: dup
/*      */     //   40: astore #4
/*      */     //   42: ifnull -> 84
/*      */     //   45: iload_1
/*      */     //   46: iload_2
/*      */     //   47: if_icmpne -> 55
/*      */     //   50: aload #4
/*      */     //   52: goto -> 78
/*      */     //   55: iload_2
/*      */     //   56: iload #6
/*      */     //   58: iconst_1
/*      */     //   59: iadd
/*      */     //   60: if_icmpne -> 69
/*      */     //   63: aload_0
/*      */     //   64: iload_2
/*      */     //   65: aaload
/*      */     //   66: goto -> 78
/*      */     //   69: aload_0
/*      */     //   70: iload #6
/*      */     //   72: iconst_1
/*      */     //   73: iadd
/*      */     //   74: iload_2
/*      */     //   75: invokestatic orTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   78: dup
/*      */     //   79: astore #5
/*      */     //   81: ifnonnull -> 92
/*      */     //   84: new java/lang/NullPointerException
/*      */     //   87: dup
/*      */     //   88: invokespecial <init> : ()V
/*      */     //   91: athrow
/*      */     //   92: aload_3
/*      */     //   93: aload #4
/*      */     //   95: aload #5
/*      */     //   97: invokevirtual orRelay : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   100: ifne -> 133
/*      */     //   103: new java/util/concurrent/CompletableFuture$OrRelay
/*      */     //   106: dup
/*      */     //   107: aload_3
/*      */     //   108: aload #4
/*      */     //   110: aload #5
/*      */     //   112: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   115: astore #7
/*      */     //   117: aload #4
/*      */     //   119: aload #5
/*      */     //   121: aload #7
/*      */     //   123: invokevirtual orpush : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture$BiCompletion;)V
/*      */     //   126: aload #7
/*      */     //   128: iconst_0
/*      */     //   129: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   132: pop
/*      */     //   133: aload_3
/*      */     //   134: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1552	-> 0
/*      */     //   #1553	-> 8
/*      */     //   #1555	-> 13
/*      */     //   #1556	-> 20
/*      */     //   #1557	-> 36
/*      */     //   #1559	-> 75
/*      */     //   #1560	-> 84
/*      */     //   #1561	-> 92
/*      */     //   #1562	-> 103
/*      */     //   #1563	-> 117
/*      */     //   #1564	-> 126
/*      */     //   #1567	-> 133
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class AsyncSupply<T>
/*      */     extends ForkJoinTask<Void>
/*      */     implements Runnable, AsynchronousCompletionTask
/*      */   {
/*      */     CompletableFuture<T> dep;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Supplier<T> fn;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AsyncSupply(CompletableFuture<T> param1CompletableFuture, Supplier<T> param1Supplier) {
/* 1577 */       this.dep = param1CompletableFuture; this.fn = param1Supplier;
/*      */     }
/*      */     public final Void getRawResult() {
/* 1580 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/* 1582 */       run(); return true;
/*      */     } public void run() {
/*      */       CompletableFuture<T> completableFuture;
/*      */       Supplier<T> supplier;
/* 1586 */       if ((completableFuture = this.dep) != null && (supplier = this.fn) != null) {
/* 1587 */         this.dep = null; this.fn = null;
/* 1588 */         if (completableFuture.result == null) {
/*      */           try {
/* 1590 */             completableFuture.completeValue(supplier.get());
/* 1591 */           } catch (Throwable throwable) {
/* 1592 */             completableFuture.completeThrowable(throwable);
/*      */           } 
/*      */         }
/* 1595 */         completableFuture.postComplete();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <U> CompletableFuture<U> asyncSupplyStage(Executor paramExecutor, Supplier<U> paramSupplier) {
/* 1602 */     if (paramSupplier == null) throw new NullPointerException(); 
/* 1603 */     CompletableFuture<U> completableFuture = new CompletableFuture();
/* 1604 */     paramExecutor.execute(new AsyncSupply<>(completableFuture, paramSupplier));
/* 1605 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class AsyncRun extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask {
/*      */     CompletableFuture<Void> dep;
/*      */     Runnable fn;
/*      */     
/*      */     AsyncRun(CompletableFuture<Void> param1CompletableFuture, Runnable param1Runnable) {
/* 1613 */       this.dep = param1CompletableFuture; this.fn = param1Runnable;
/*      */     }
/*      */     public final Void getRawResult() {
/* 1616 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/* 1618 */       run(); return true;
/*      */     } public void run() {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       Runnable runnable;
/* 1622 */       if ((completableFuture = this.dep) != null && (runnable = this.fn) != null) {
/* 1623 */         this.dep = null; this.fn = null;
/* 1624 */         if (completableFuture.result == null) {
/*      */           try {
/* 1626 */             runnable.run();
/* 1627 */             completableFuture.completeNull();
/* 1628 */           } catch (Throwable throwable) {
/* 1629 */             completableFuture.completeThrowable(throwable);
/*      */           } 
/*      */         }
/* 1632 */         completableFuture.postComplete();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static CompletableFuture<Void> asyncRunStage(Executor paramExecutor, Runnable paramRunnable) {
/* 1638 */     if (paramRunnable == null) throw new NullPointerException(); 
/* 1639 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/* 1640 */     paramExecutor.execute(new AsyncRun(completableFuture, paramRunnable));
/* 1641 */     return completableFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Signaller
/*      */     extends Completion
/*      */     implements ForkJoinPool.ManagedBlocker
/*      */   {
/*      */     long nanos;
/*      */     
/*      */     final long deadline;
/*      */     
/*      */     volatile int interruptControl;
/*      */     
/*      */     volatile Thread thread;
/*      */ 
/*      */     
/*      */     Signaller(boolean param1Boolean, long param1Long1, long param1Long2) {
/* 1660 */       this.thread = Thread.currentThread();
/* 1661 */       this.interruptControl = param1Boolean ? 1 : 0;
/* 1662 */       this.nanos = param1Long1;
/* 1663 */       this.deadline = param1Long2;
/*      */     }
/*      */     final CompletableFuture<?> tryFire(int param1Int) {
/*      */       Thread thread;
/* 1667 */       if ((thread = this.thread) != null) {
/* 1668 */         this.thread = null;
/* 1669 */         LockSupport.unpark(thread);
/*      */       } 
/* 1671 */       return null;
/*      */     }
/*      */     public boolean isReleasable() {
/* 1674 */       if (this.thread == null)
/* 1675 */         return true; 
/* 1676 */       if (Thread.interrupted()) {
/* 1677 */         int i = this.interruptControl;
/* 1678 */         this.interruptControl = -1;
/* 1679 */         if (i > 0)
/* 1680 */           return true; 
/*      */       } 
/* 1682 */       if (this.deadline != 0L && (this.nanos <= 0L || (this
/* 1683 */         .nanos = this.deadline - System.nanoTime()) <= 0L)) {
/* 1684 */         this.thread = null;
/* 1685 */         return true;
/*      */       } 
/* 1687 */       return false;
/*      */     }
/*      */     public boolean block() {
/* 1690 */       if (isReleasable())
/* 1691 */         return true; 
/* 1692 */       if (this.deadline == 0L) {
/* 1693 */         LockSupport.park(this);
/* 1694 */       } else if (this.nanos > 0L) {
/* 1695 */         LockSupport.parkNanos(this, this.nanos);
/* 1696 */       }  return isReleasable();
/*      */     } final boolean isLive() {
/* 1698 */       return (this.thread != null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object waitingGet(boolean paramBoolean) {
/* 1706 */     Signaller signaller = null;
/* 1707 */     boolean bool = false;
/* 1708 */     byte b = -1;
/*      */     Object object;
/* 1710 */     while ((object = this.result) == null) {
/* 1711 */       if (b < 0) {
/* 1712 */         b = (Runtime.getRuntime().availableProcessors() > 1) ? 256 : 0; continue;
/*      */       } 
/* 1714 */       if (b > 0) {
/* 1715 */         if (ThreadLocalRandom.nextSecondarySeed() >= 0)
/* 1716 */           b--;  continue;
/*      */       } 
/* 1718 */       if (signaller == null) {
/* 1719 */         signaller = new Signaller(paramBoolean, 0L, 0L); continue;
/* 1720 */       }  if (!bool) {
/* 1721 */         bool = tryPushStack(signaller); continue;
/* 1722 */       }  if (paramBoolean && signaller.interruptControl < 0) {
/* 1723 */         signaller.thread = null;
/* 1724 */         cleanStack();
/* 1725 */         return null;
/*      */       } 
/* 1727 */       if (signaller.thread != null && this.result == null) {
/*      */         try {
/* 1729 */           ForkJoinPool.managedBlock(signaller);
/* 1730 */         } catch (InterruptedException interruptedException) {
/* 1731 */           signaller.interruptControl = -1;
/*      */         } 
/*      */       }
/*      */     } 
/* 1735 */     if (signaller != null) {
/* 1736 */       signaller.thread = null;
/* 1737 */       if (signaller.interruptControl < 0)
/* 1738 */         if (paramBoolean) {
/* 1739 */           object = null;
/*      */         } else {
/* 1741 */           Thread.currentThread().interrupt();
/*      */         }  
/*      */     } 
/* 1744 */     postComplete();
/* 1745 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object timedGet(long paramLong) throws TimeoutException {
/* 1753 */     if (Thread.interrupted())
/* 1754 */       return null; 
/* 1755 */     if (paramLong <= 0L)
/* 1756 */       throw new TimeoutException(); 
/* 1757 */     long l = System.nanoTime() + paramLong;
/* 1758 */     Signaller signaller = new Signaller(true, paramLong, (l == 0L) ? 1L : l);
/* 1759 */     boolean bool = false;
/*      */     
/*      */     Object object;
/*      */     
/* 1763 */     while ((object = this.result) == null) {
/* 1764 */       if (!bool) {
/* 1765 */         bool = tryPushStack(signaller); continue;
/* 1766 */       }  if (signaller.interruptControl < 0 || signaller.nanos <= 0L) {
/* 1767 */         signaller.thread = null;
/* 1768 */         cleanStack();
/* 1769 */         if (signaller.interruptControl < 0)
/* 1770 */           return null; 
/* 1771 */         throw new TimeoutException();
/*      */       } 
/* 1773 */       if (signaller.thread != null && this.result == null) {
/*      */         try {
/* 1775 */           ForkJoinPool.managedBlock(signaller);
/* 1776 */         } catch (InterruptedException interruptedException) {
/* 1777 */           signaller.interruptControl = -1;
/*      */         } 
/*      */       }
/*      */     } 
/* 1781 */     if (signaller.interruptControl < 0)
/* 1782 */       object = null; 
/* 1783 */     signaller.thread = null;
/* 1784 */     postComplete();
/* 1785 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture(Object paramObject) {
/* 1800 */     this.result = paramObject;
/*      */   }
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
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier) {
/* 1814 */     return asyncSupplyStage(asyncPool, paramSupplier);
/*      */   }
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
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier, Executor paramExecutor) {
/* 1830 */     return asyncSupplyStage(screenExecutor(paramExecutor), paramSupplier);
/*      */   }
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
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable) {
/* 1843 */     return asyncRunStage(asyncPool, paramRunnable);
/*      */   }
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
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable, Executor paramExecutor) {
/* 1858 */     return asyncRunStage(screenExecutor(paramExecutor), paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <U> CompletableFuture<U> completedFuture(U paramU) {
/* 1870 */     return new CompletableFuture<>((paramU == null) ? NIL : paramU);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDone() {
/* 1880 */     return (this.result != null);
/*      */   }
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
/*      */   public T get() throws InterruptedException, ExecutionException {
/*      */     Object object;
/* 1895 */     return reportGet(((object = this.result) == null) ? waitingGet(true) : object);
/*      */   }
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
/*      */   public T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 1914 */     long l = paramTimeUnit.toNanos(paramLong); Object object;
/* 1915 */     return reportGet(((object = this.result) == null) ? timedGet(l) : object);
/*      */   }
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
/*      */   public T join() {
/*      */     Object object;
/* 1934 */     return reportJoin(((object = this.result) == null) ? waitingGet(false) : object);
/*      */   }
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
/*      */   public T getNow(T paramT) {
/*      */     Object object;
/* 1949 */     return ((object = this.result) == null) ? paramT : reportJoin(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean complete(T paramT) {
/* 1961 */     boolean bool = completeValue(paramT);
/* 1962 */     postComplete();
/* 1963 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean completeExceptionally(Throwable paramThrowable) {
/* 1975 */     if (paramThrowable == null) throw new NullPointerException(); 
/* 1976 */     boolean bool = internalComplete(new AltResult(paramThrowable));
/* 1977 */     postComplete();
/* 1978 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> paramFunction) {
/* 1983 */     return uniApplyStage(null, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction) {
/* 1988 */     return uniApplyStage(asyncPool, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction, Executor paramExecutor) {
/* 1993 */     return uniApplyStage(screenExecutor(paramExecutor), paramFunction);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAccept(Consumer<? super T> paramConsumer) {
/* 1997 */     return uniAcceptStage(null, paramConsumer);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer) {
/* 2001 */     return uniAcceptStage(asyncPool, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer, Executor paramExecutor) {
/* 2006 */     return uniAcceptStage(screenExecutor(paramExecutor), paramConsumer);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRun(Runnable paramRunnable) {
/* 2010 */     return uniRunStage(null, paramRunnable);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable) {
/* 2014 */     return uniRunStage(asyncPool, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable, Executor paramExecutor) {
/* 2019 */     return uniRunStage(screenExecutor(paramExecutor), paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/* 2025 */     return biApplyStage(null, paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/* 2031 */     return biApplyStage(asyncPool, paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, Executor paramExecutor) {
/* 2037 */     return biApplyStage(screenExecutor(paramExecutor), paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/* 2043 */     return biAcceptStage(null, paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/* 2049 */     return biAcceptStage(asyncPool, paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer, Executor paramExecutor) {
/* 2055 */     return biAcceptStage(screenExecutor(paramExecutor), paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBoth(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2060 */     return biRunStage(null, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2065 */     return biRunStage(asyncPool, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor) {
/* 2071 */     return biRunStage(screenExecutor(paramExecutor), paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction) {
/* 2076 */     return (CompletableFuture)orApplyStage(null, paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction) {
/* 2081 */     return (CompletableFuture)orApplyStage(asyncPool, paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction, Executor paramExecutor) {
/* 2087 */     return (CompletableFuture)orApplyStage(screenExecutor(paramExecutor), paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer) {
/* 2092 */     return orAcceptStage(null, paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer) {
/* 2097 */     return orAcceptStage(asyncPool, paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer, Executor paramExecutor) {
/* 2103 */     return orAcceptStage(screenExecutor(paramExecutor), paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEither(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2108 */     return orRunStage(null, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2113 */     return orRunStage(asyncPool, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor) {
/* 2119 */     return orRunStage(screenExecutor(paramExecutor), paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> paramFunction) {
/* 2124 */     return uniComposeStage(null, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction) {
/* 2129 */     return uniComposeStage(asyncPool, paramFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction, Executor paramExecutor) {
/* 2135 */     return uniComposeStage(screenExecutor(paramExecutor), paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/* 2140 */     return uniWhenCompleteStage(null, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/* 2145 */     return uniWhenCompleteStage(asyncPool, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer, Executor paramExecutor) {
/* 2150 */     return uniWhenCompleteStage(screenExecutor(paramExecutor), paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> paramBiFunction) {
/* 2155 */     return uniHandleStage(null, paramBiFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction) {
/* 2160 */     return uniHandleStage(asyncPool, paramBiFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction, Executor paramExecutor) {
/* 2165 */     return uniHandleStage(screenExecutor(paramExecutor), paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> toCompletableFuture() {
/* 2174 */     return this;
/*      */   }
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
/*      */   public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> paramFunction) {
/* 2196 */     return uniExceptionallyStage(paramFunction);
/*      */   }
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
/*      */   public static CompletableFuture<Void> allOf(CompletableFuture<?>... paramVarArgs) {
/* 2225 */     return andTree(paramVarArgs, 0, paramVarArgs.length - 1);
/*      */   }
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
/*      */   public static CompletableFuture<Object> anyOf(CompletableFuture<?>... paramVarArgs) {
/* 2244 */     return orTree(paramVarArgs, 0, paramVarArgs.length - 1);
/*      */   }
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
/*      */   public boolean cancel(boolean paramBoolean) {
/* 2264 */     boolean bool = (this.result == null && internalComplete(new AltResult(new CancellationException()))) ? true : false;
/* 2265 */     postComplete();
/* 2266 */     return (bool || isCancelled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCancelled() {
/*      */     Object object;
/* 2278 */     return (object = this.result instanceof AltResult && ((AltResult)object).ex instanceof CancellationException);
/*      */   }
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
/*      */   public boolean isCompletedExceptionally() {
/*      */     Object object;
/* 2294 */     return (object = this.result instanceof AltResult && object != NIL);
/*      */   }
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
/*      */   public void obtrudeValue(T paramT) {
/* 2308 */     this.result = (paramT == null) ? NIL : paramT;
/* 2309 */     postComplete();
/*      */   }
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
/*      */   public void obtrudeException(Throwable paramThrowable) {
/* 2324 */     if (paramThrowable == null) throw new NullPointerException(); 
/* 2325 */     this.result = new AltResult(paramThrowable);
/* 2326 */     postComplete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfDependents() {
/* 2338 */     byte b = 0;
/* 2339 */     for (Completion completion = this.stack; completion != null; completion = completion.next)
/* 2340 */       b++; 
/* 2341 */     return b;
/*      */   }
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
/*      */   public String toString() {
/* 2355 */     Object object = this.result;
/*      */     int i;
/* 2357 */     return super.toString() + ((object == null) ? (
/*      */       
/* 2359 */       ((i = getNumberOfDependents()) == 0) ? "[Not completed]" : ("[Not completed, " + i + " dependents]")) : ((object instanceof AltResult && ((AltResult)object).ex != null) ? "[Completed exceptionally]" : "[Completed normally]"));
/*      */   }
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
/*      */   static {
/*      */     try {
/* 2375 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 2376 */       Class<CompletableFuture> clazz = CompletableFuture.class;
/* 2377 */       RESULT = unsafe.objectFieldOffset(clazz.getDeclaredField("result"));
/* 2378 */       STACK = unsafe.objectFieldOffset(clazz.getDeclaredField("stack"));
/*      */       
/* 2380 */       NEXT = unsafe.objectFieldOffset(Completion.class.getDeclaredField("next"));
/* 2381 */     } catch (Exception exception) {
/* 2382 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface AsynchronousCompletionTask {}
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\concurrent\CompletableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */