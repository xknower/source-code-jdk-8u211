/*     */ package javax.swing;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RowFilter<M, I>
/*     */ {
/*     */   public enum ComparisonType
/*     */   {
/* 110 */     BEFORE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     AFTER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     EQUAL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     NOT_EQUAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkIndices(int[] paramArrayOfint) {
/* 136 */     for (int i = paramArrayOfint.length - 1; i >= 0; i--) {
/* 137 */       if (paramArrayOfint[i] < 0) {
/* 138 */         throw new IllegalArgumentException("Index must be >= 0");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <M, I> RowFilter<M, I> regexFilter(String paramString, int... paramVarArgs) {
/* 176 */     return new RegexFilter(Pattern.compile(paramString), paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> dateFilter(ComparisonType paramComparisonType, Date paramDate, int... paramVarArgs) {
/* 204 */     return new DateFilter(paramComparisonType, paramDate.getTime(), paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> numberFilter(ComparisonType paramComparisonType, Number paramNumber, int... paramVarArgs) {
/* 227 */     return new NumberFilter(paramComparisonType, paramNumber, paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> orFilter(Iterable<? extends RowFilter<? super M, ? super I>> paramIterable) {
/* 253 */     return new OrFilter<>(paramIterable);
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
/*     */   public static <M, I> RowFilter<M, I> andFilter(Iterable<? extends RowFilter<? super M, ? super I>> paramIterable) {
/* 279 */     return new AndFilter<>(paramIterable);
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
/*     */   public static <M, I> RowFilter<M, I> notFilter(RowFilter<M, I> paramRowFilter) {
/* 292 */     return new NotFilter<>(paramRowFilter);
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
/*     */   public abstract boolean include(Entry<? extends M, ? extends I> paramEntry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Entry<M, I>
/*     */   {
/*     */     public abstract M getModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract int getValueCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract Object getValue(int param1Int);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getStringValue(int param1Int) {
/* 384 */       Object object = getValue(param1Int);
/* 385 */       return (object == null) ? "" : object.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract I getIdentifier();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class GeneralFilter
/*     */     extends RowFilter<Object, Object>
/*     */   {
/*     */     private int[] columns;
/*     */ 
/*     */ 
/*     */     
/*     */     GeneralFilter(int[] param1ArrayOfint) {
/* 404 */       RowFilter.checkIndices(param1ArrayOfint);
/* 405 */       this.columns = param1ArrayOfint;
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry) {
/* 409 */       int i = param1Entry.getValueCount();
/* 410 */       if (this.columns.length > 0) {
/* 411 */         for (int j = this.columns.length - 1; j >= 0; j--) {
/* 412 */           int k = this.columns[j];
/* 413 */           if (k < i && 
/* 414 */             include(param1Entry, k)) {
/* 415 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 421 */         while (--i >= 0) {
/* 422 */           if (include(param1Entry, i)) {
/* 423 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 427 */       return false;
/*     */     }
/*     */     
/*     */     protected abstract boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int);
/*     */   }
/*     */   
/*     */   private static class RegexFilter
/*     */     extends GeneralFilter
/*     */   {
/*     */     private Matcher matcher;
/*     */     
/*     */     RegexFilter(Pattern param1Pattern, int[] param1ArrayOfint) {
/* 439 */       super(param1ArrayOfint);
/* 440 */       if (param1Pattern == null) {
/* 441 */         throw new IllegalArgumentException("Pattern must be non-null");
/*     */       }
/* 443 */       this.matcher = param1Pattern.matcher("");
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/* 448 */       this.matcher.reset(param1Entry.getStringValue(param1Int));
/* 449 */       return this.matcher.find();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DateFilter
/*     */     extends GeneralFilter {
/*     */     private long date;
/*     */     private RowFilter.ComparisonType type;
/*     */     
/*     */     DateFilter(RowFilter.ComparisonType param1ComparisonType, long param1Long, int[] param1ArrayOfint) {
/* 459 */       super(param1ArrayOfint);
/* 460 */       if (param1ComparisonType == null) {
/* 461 */         throw new IllegalArgumentException("type must be non-null");
/*     */       }
/* 463 */       this.type = param1ComparisonType;
/* 464 */       this.date = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: iload_2
/*     */       //   2: invokevirtual getValue : (I)Ljava/lang/Object;
/*     */       //   5: astore_3
/*     */       //   6: aload_3
/*     */       //   7: instanceof java/util/Date
/*     */       //   10: ifeq -> 128
/*     */       //   13: aload_3
/*     */       //   14: checkcast java/util/Date
/*     */       //   17: invokevirtual getTime : ()J
/*     */       //   20: lstore #4
/*     */       //   22: getstatic javax/swing/RowFilter$1.$SwitchMap$javax$swing$RowFilter$ComparisonType : [I
/*     */       //   25: aload_0
/*     */       //   26: getfield type : Ljavax/swing/RowFilter$ComparisonType;
/*     */       //   29: invokevirtual ordinal : ()I
/*     */       //   32: iaload
/*     */       //   33: tableswitch default -> 128, 1 -> 64, 2 -> 80, 3 -> 96, 4 -> 112
/*     */       //   64: lload #4
/*     */       //   66: aload_0
/*     */       //   67: getfield date : J
/*     */       //   70: lcmp
/*     */       //   71: ifge -> 78
/*     */       //   74: iconst_1
/*     */       //   75: goto -> 79
/*     */       //   78: iconst_0
/*     */       //   79: ireturn
/*     */       //   80: lload #4
/*     */       //   82: aload_0
/*     */       //   83: getfield date : J
/*     */       //   86: lcmp
/*     */       //   87: ifle -> 94
/*     */       //   90: iconst_1
/*     */       //   91: goto -> 95
/*     */       //   94: iconst_0
/*     */       //   95: ireturn
/*     */       //   96: lload #4
/*     */       //   98: aload_0
/*     */       //   99: getfield date : J
/*     */       //   102: lcmp
/*     */       //   103: ifne -> 110
/*     */       //   106: iconst_1
/*     */       //   107: goto -> 111
/*     */       //   110: iconst_0
/*     */       //   111: ireturn
/*     */       //   112: lload #4
/*     */       //   114: aload_0
/*     */       //   115: getfield date : J
/*     */       //   118: lcmp
/*     */       //   119: ifeq -> 126
/*     */       //   122: iconst_1
/*     */       //   123: goto -> 127
/*     */       //   126: iconst_0
/*     */       //   127: ireturn
/*     */       //   128: iconst_0
/*     */       //   129: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #469	-> 0
/*     */       //   #471	-> 6
/*     */       //   #472	-> 13
/*     */       //   #473	-> 22
/*     */       //   #475	-> 64
/*     */       //   #477	-> 80
/*     */       //   #479	-> 96
/*     */       //   #481	-> 112
/*     */       //   #486	-> 128
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class NumberFilter
/*     */     extends GeneralFilter
/*     */   {
/*     */     private boolean isComparable;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Number number;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private RowFilter.ComparisonType type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NumberFilter(RowFilter.ComparisonType param1ComparisonType, Number param1Number, int[] param1ArrayOfint) {
/* 499 */       super(param1ArrayOfint);
/* 500 */       if (param1ComparisonType == null || param1Number == null) {
/* 501 */         throw new IllegalArgumentException("type and number must be non-null");
/*     */       }
/*     */       
/* 504 */       this.type = param1ComparisonType;
/* 505 */       this.number = param1Number;
/* 506 */       this.isComparable = param1Number instanceof Comparable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: iload_2
/*     */       //   2: invokevirtual getValue : (I)Ljava/lang/Object;
/*     */       //   5: astore_3
/*     */       //   6: aload_3
/*     */       //   7: instanceof java/lang/Number
/*     */       //   10: ifeq -> 156
/*     */       //   13: iconst_1
/*     */       //   14: istore #4
/*     */       //   16: aload_3
/*     */       //   17: invokevirtual getClass : ()Ljava/lang/Class;
/*     */       //   20: astore #6
/*     */       //   22: aload_0
/*     */       //   23: getfield number : Ljava/lang/Number;
/*     */       //   26: invokevirtual getClass : ()Ljava/lang/Class;
/*     */       //   29: aload #6
/*     */       //   31: if_acmpne -> 59
/*     */       //   34: aload_0
/*     */       //   35: getfield isComparable : Z
/*     */       //   38: ifeq -> 59
/*     */       //   41: aload_0
/*     */       //   42: getfield number : Ljava/lang/Number;
/*     */       //   45: checkcast java/lang/Comparable
/*     */       //   48: aload_3
/*     */       //   49: invokeinterface compareTo : (Ljava/lang/Object;)I
/*     */       //   54: istore #5
/*     */       //   56: goto -> 69
/*     */       //   59: aload_0
/*     */       //   60: aload_3
/*     */       //   61: checkcast java/lang/Number
/*     */       //   64: invokespecial longCompare : (Ljava/lang/Number;)I
/*     */       //   67: istore #5
/*     */       //   69: getstatic javax/swing/RowFilter$1.$SwitchMap$javax$swing$RowFilter$ComparisonType : [I
/*     */       //   72: aload_0
/*     */       //   73: getfield type : Ljavax/swing/RowFilter$ComparisonType;
/*     */       //   76: invokevirtual ordinal : ()I
/*     */       //   79: iaload
/*     */       //   80: tableswitch default -> 156, 1 -> 112, 2 -> 123, 3 -> 134, 4 -> 145
/*     */       //   112: iload #5
/*     */       //   114: ifle -> 121
/*     */       //   117: iconst_1
/*     */       //   118: goto -> 122
/*     */       //   121: iconst_0
/*     */       //   122: ireturn
/*     */       //   123: iload #5
/*     */       //   125: ifge -> 132
/*     */       //   128: iconst_1
/*     */       //   129: goto -> 133
/*     */       //   132: iconst_0
/*     */       //   133: ireturn
/*     */       //   134: iload #5
/*     */       //   136: ifne -> 143
/*     */       //   139: iconst_1
/*     */       //   140: goto -> 144
/*     */       //   143: iconst_0
/*     */       //   144: ireturn
/*     */       //   145: iload #5
/*     */       //   147: ifeq -> 154
/*     */       //   150: iconst_1
/*     */       //   151: goto -> 155
/*     */       //   154: iconst_0
/*     */       //   155: ireturn
/*     */       //   156: iconst_0
/*     */       //   157: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #512	-> 0
/*     */       //   #514	-> 6
/*     */       //   #515	-> 13
/*     */       //   #517	-> 16
/*     */       //   #518	-> 22
/*     */       //   #519	-> 41
/*     */       //   #522	-> 59
/*     */       //   #524	-> 69
/*     */       //   #526	-> 112
/*     */       //   #528	-> 123
/*     */       //   #530	-> 134
/*     */       //   #532	-> 145
/*     */       //   #537	-> 156
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int longCompare(Number param1Number) {
/* 541 */       long l = this.number.longValue() - param1Number.longValue();
/*     */       
/* 543 */       if (l < 0L) {
/* 544 */         return -1;
/*     */       }
/* 546 */       if (l > 0L) {
/* 547 */         return 1;
/*     */       }
/* 549 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OrFilter<M, I>
/*     */     extends RowFilter<M, I> {
/*     */     List<RowFilter<? super M, ? super I>> filters;
/*     */     
/*     */     OrFilter(Iterable<? extends RowFilter<? super M, ? super I>> param1Iterable) {
/* 558 */       this.filters = new ArrayList<>();
/* 559 */       for (RowFilter<? super M, ? super I> rowFilter : param1Iterable) {
/* 560 */         if (rowFilter == null) {
/* 561 */           throw new IllegalArgumentException("Filter must be non-null");
/*     */         }
/*     */         
/* 564 */         this.filters.add(rowFilter);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 569 */       for (RowFilter<M, I> rowFilter : this.filters) {
/* 570 */         if (rowFilter.include(param1Entry)) {
/* 571 */           return true;
/*     */         }
/*     */       } 
/* 574 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AndFilter<M, I>
/*     */     extends OrFilter<M, I> {
/*     */     AndFilter(Iterable<? extends RowFilter<? super M, ? super I>> param1Iterable) {
/* 581 */       super(param1Iterable);
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 585 */       for (RowFilter<M, I> rowFilter : this.filters) {
/* 586 */         if (!rowFilter.include(param1Entry)) {
/* 587 */           return false;
/*     */         }
/*     */       } 
/* 590 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NotFilter<M, I>
/*     */     extends RowFilter<M, I> {
/*     */     private RowFilter<M, I> filter;
/*     */     
/*     */     NotFilter(RowFilter<M, I> param1RowFilter) {
/* 599 */       if (param1RowFilter == null) {
/* 600 */         throw new IllegalArgumentException("filter must be non-null");
/*     */       }
/*     */       
/* 603 */       this.filter = param1RowFilter;
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 607 */       return !this.filter.include(param1Entry);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\RowFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */