/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Collections
/*      */ {
/*      */   private static final int BINARYSEARCH_THRESHOLD = 5000;
/*      */   private static final int REVERSE_THRESHOLD = 18;
/*      */   private static final int SHUFFLE_THRESHOLD = 5;
/*      */   private static final int FILL_THRESHOLD = 25;
/*      */   private static final int ROTATE_THRESHOLD = 100;
/*      */   private static final int COPY_THRESHOLD = 10;
/*      */   private static final int REPLACEALL_THRESHOLD = 11;
/*      */   private static final int INDEXOFSUBLIST_THRESHOLD = 35;
/*      */   private static Random r;
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(List<T> paramList) {
/*  141 */     paramList.sort(null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void sort(List<T> paramList, Comparator<? super T> paramComparator) {
/*  175 */     paramList.sort(paramComparator);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> int binarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  212 */     if (paramList instanceof RandomAccess || paramList.size() < 5000) {
/*  213 */       return indexedBinarySearch(paramList, paramT);
/*      */     }
/*  215 */     return iteratorBinarySearch(paramList, paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  220 */     int i = 0;
/*  221 */     int j = paramList.size() - 1;
/*      */     
/*  223 */     while (i <= j) {
/*  224 */       int k = i + j >>> 1;
/*  225 */       Comparable<T> comparable = (Comparable)paramList.get(k);
/*  226 */       int m = comparable.compareTo(paramT);
/*      */       
/*  228 */       if (m < 0) {
/*  229 */         i = k + 1; continue;
/*  230 */       }  if (m > 0) {
/*  231 */         j = k - 1; continue;
/*      */       } 
/*  233 */       return k;
/*      */     } 
/*  235 */     return -(i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> int iteratorBinarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  241 */     int i = 0;
/*  242 */     int j = paramList.size() - 1;
/*  243 */     ListIterator<? extends Comparable<? super T>> listIterator = paramList.listIterator();
/*      */     
/*  245 */     while (i <= j) {
/*  246 */       int k = i + j >>> 1;
/*  247 */       Comparable<T> comparable = get((ListIterator)listIterator, k);
/*  248 */       int m = comparable.compareTo(paramT);
/*      */       
/*  250 */       if (m < 0) {
/*  251 */         i = k + 1; continue;
/*  252 */       }  if (m > 0) {
/*  253 */         j = k - 1; continue;
/*      */       } 
/*  255 */       return k;
/*      */     } 
/*  257 */     return -(i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T get(ListIterator<? extends T> paramListIterator, int paramInt) {
/*  265 */     T t = null;
/*  266 */     int i = paramListIterator.nextIndex();
/*  267 */     if (i <= paramInt) {
/*      */       do {
/*  269 */         t = paramListIterator.next();
/*  270 */       } while (i++ < paramInt);
/*      */     } else {
/*      */       do {
/*  273 */         t = paramListIterator.previous();
/*  274 */       } while (--i > paramInt);
/*      */     } 
/*  276 */     return t;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> int binarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  316 */     if (paramComparator == null) {
/*  317 */       return binarySearch((List)paramList, paramT);
/*      */     }
/*  319 */     if (paramList instanceof RandomAccess || paramList.size() < 5000) {
/*  320 */       return indexedBinarySearch(paramList, paramT, paramComparator);
/*      */     }
/*  322 */     return iteratorBinarySearch(paramList, paramT, paramComparator);
/*      */   }
/*      */   
/*      */   private static <T> int indexedBinarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  326 */     int i = 0;
/*  327 */     int j = paramList.size() - 1;
/*      */     
/*  329 */     while (i <= j) {
/*  330 */       int k = i + j >>> 1;
/*  331 */       T t = paramList.get(k);
/*  332 */       int m = paramComparator.compare(t, paramT);
/*      */       
/*  334 */       if (m < 0) {
/*  335 */         i = k + 1; continue;
/*  336 */       }  if (m > 0) {
/*  337 */         j = k - 1; continue;
/*      */       } 
/*  339 */       return k;
/*      */     } 
/*  341 */     return -(i + 1);
/*      */   }
/*      */   
/*      */   private static <T> int iteratorBinarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  345 */     int i = 0;
/*  346 */     int j = paramList.size() - 1;
/*  347 */     ListIterator<? extends T> listIterator = paramList.listIterator();
/*      */     
/*  349 */     while (i <= j) {
/*  350 */       int k = i + j >>> 1;
/*  351 */       T t = (T)get((ListIterator)listIterator, k);
/*  352 */       int m = paramComparator.compare(t, paramT);
/*      */       
/*  354 */       if (m < 0) {
/*  355 */         i = k + 1; continue;
/*  356 */       }  if (m > 0) {
/*  357 */         j = k - 1; continue;
/*      */       } 
/*  359 */       return k;
/*      */     } 
/*  361 */     return -(i + 1);
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
/*      */   public static void reverse(List<?> paramList) {
/*  375 */     int i = paramList.size();
/*  376 */     if (i < 18 || paramList instanceof RandomAccess) {
/*  377 */       byte b; int j; int k; for (b = 0, j = i >> 1, k = i - 1; b < j; b++, k--) {
/*  378 */         swap(paramList, b, k);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  383 */       ListIterator<?> listIterator1 = paramList.listIterator();
/*  384 */       ListIterator<?> listIterator2 = paramList.listIterator(i); byte b; int j;
/*  385 */       for (b = 0, j = paramList.size() >> 1; b < j; b++) {
/*  386 */         Object object = listIterator1.next();
/*  387 */         listIterator1.set(listIterator2.previous());
/*  388 */         listIterator2.set(object);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void shuffle(List<?> paramList) {
/*  422 */     Random random = r;
/*  423 */     if (random == null)
/*  424 */       r = random = new Random(); 
/*  425 */     shuffle(paramList, random);
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
/*      */   
/*      */   public static void shuffle(List<?> paramList, Random paramRandom) {
/*  455 */     int i = paramList.size();
/*  456 */     if (i < 5 || paramList instanceof RandomAccess) {
/*  457 */       for (int j = i; j > 1; j--)
/*  458 */         swap(paramList, j - 1, paramRandom.nextInt(j)); 
/*      */     } else {
/*  460 */       Object[] arrayOfObject = paramList.toArray();
/*      */ 
/*      */       
/*  463 */       for (int j = i; j > 1; j--) {
/*  464 */         swap(arrayOfObject, j - 1, paramRandom.nextInt(j));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  470 */       ListIterator<?> listIterator = paramList.listIterator();
/*  471 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/*  472 */         listIterator.next();
/*  473 */         listIterator.set(arrayOfObject[b]);
/*      */       } 
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
/*      */   public static void swap(List<?> paramList, int paramInt1, int paramInt2) {
/*  496 */     List<?> list = paramList;
/*  497 */     list.set(paramInt1, list.set(paramInt2, list.get(paramInt1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/*  504 */     Object object = paramArrayOfObject[paramInt1];
/*  505 */     paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
/*  506 */     paramArrayOfObject[paramInt2] = object;
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
/*      */   public static <T> void fill(List<? super T> paramList, T paramT) {
/*  522 */     int i = paramList.size();
/*      */     
/*  524 */     if (i < 25 || paramList instanceof RandomAccess) {
/*  525 */       for (byte b = 0; b < i; b++)
/*  526 */         paramList.set(b, paramT); 
/*      */     } else {
/*  528 */       ListIterator<? super T> listIterator = paramList.listIterator();
/*  529 */       for (byte b = 0; b < i; b++) {
/*  530 */         listIterator.next();
/*  531 */         listIterator.set(paramT);
/*      */       } 
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
/*      */   public static <T> void copy(List<? super T> paramList, List<? extends T> paramList1) {
/*  554 */     int i = paramList1.size();
/*  555 */     if (i > paramList.size()) {
/*  556 */       throw new IndexOutOfBoundsException("Source does not fit in dest");
/*      */     }
/*  558 */     if (i < 10 || (paramList1 instanceof RandomAccess && paramList instanceof RandomAccess)) {
/*      */       
/*  560 */       for (byte b = 0; b < i; b++)
/*  561 */         paramList.set(b, paramList1.get(b)); 
/*      */     } else {
/*  563 */       ListIterator<? super T> listIterator = paramList.listIterator();
/*  564 */       ListIterator<? extends T> listIterator1 = paramList1.listIterator();
/*  565 */       for (byte b = 0; b < i; b++) {
/*  566 */         listIterator.next();
/*  567 */         listIterator.set(listIterator1.next());
/*      */       } 
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> T min(Collection<? extends T> paramCollection) {
/*      */     T t1;
/*  595 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  596 */     T t2 = iterator.next();
/*      */     
/*  598 */     while (iterator.hasNext()) {
/*  599 */       T t = iterator.next();
/*  600 */       if (((Comparable<T>)t).compareTo(t2) < 0)
/*  601 */         t1 = t; 
/*      */     } 
/*  603 */     return t1;
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
/*      */   public static <T> T min(Collection<? extends T> paramCollection, Comparator<? super T> paramComparator) {
/*      */     T t1;
/*  631 */     if (paramComparator == null) {
/*  632 */       return (T)min((Collection)paramCollection);
/*      */     }
/*  634 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  635 */     T t2 = iterator.next();
/*      */     
/*  637 */     while (iterator.hasNext()) {
/*  638 */       T t = iterator.next();
/*  639 */       if (paramComparator.compare(t, t2) < 0)
/*  640 */         t1 = t; 
/*      */     } 
/*  642 */     return t1;
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
/*      */   public static <T extends Comparable<? super T>> T max(Collection<? extends T> paramCollection) {
/*      */     T t1;
/*  668 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  669 */     T t2 = iterator.next();
/*      */     
/*  671 */     while (iterator.hasNext()) {
/*  672 */       T t = iterator.next();
/*  673 */       if (((Comparable<T>)t).compareTo(t2) > 0)
/*  674 */         t1 = t; 
/*      */     } 
/*  676 */     return t1;
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
/*      */   public static <T> T max(Collection<? extends T> paramCollection, Comparator<? super T> paramComparator) {
/*      */     T t1;
/*  704 */     if (paramComparator == null) {
/*  705 */       return (T)max((Collection)paramCollection);
/*      */     }
/*  707 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  708 */     T t2 = iterator.next();
/*      */     
/*  710 */     while (iterator.hasNext()) {
/*  711 */       T t = iterator.next();
/*  712 */       if (paramComparator.compare(t, t2) > 0)
/*  713 */         t1 = t; 
/*      */     } 
/*  715 */     return t1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void rotate(List<?> paramList, int paramInt) {
/*  774 */     if (paramList instanceof RandomAccess || paramList.size() < 100) {
/*  775 */       rotate1(paramList, paramInt);
/*      */     } else {
/*  777 */       rotate2(paramList, paramInt);
/*      */     } 
/*      */   }
/*      */   private static <T> void rotate1(List<T> paramList, int paramInt) {
/*  781 */     int i = paramList.size();
/*  782 */     if (i == 0)
/*      */       return; 
/*  784 */     paramInt %= i;
/*  785 */     if (paramInt < 0)
/*  786 */       paramInt += i; 
/*  787 */     if (paramInt == 0)
/*      */       return;  byte b;
/*      */     int j;
/*  790 */     for (b = 0, j = 0; j != i; ) {
/*  791 */       T t = paramList.get(b);
/*  792 */       int k = b;
/*      */       while (true) {
/*  794 */         k += paramInt;
/*  795 */         if (k >= i)
/*  796 */           k -= i; 
/*  797 */         t = paramList.set(k, t);
/*  798 */         j++;
/*  799 */         if (k == b)
/*      */           b++; 
/*      */       } 
/*      */     } 
/*      */   } private static void rotate2(List<?> paramList, int paramInt) {
/*  804 */     int i = paramList.size();
/*  805 */     if (i == 0)
/*      */       return; 
/*  807 */     int j = -paramInt % i;
/*  808 */     if (j < 0)
/*  809 */       j += i; 
/*  810 */     if (j == 0) {
/*      */       return;
/*      */     }
/*  813 */     reverse(paramList.subList(0, j));
/*  814 */     reverse(paramList.subList(j, i));
/*  815 */     reverse(paramList);
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
/*      */   public static <T> boolean replaceAll(List<T> paramList, T paramT1, T paramT2) {
/*  838 */     boolean bool = false;
/*  839 */     int i = paramList.size();
/*  840 */     if (i < 11 || paramList instanceof RandomAccess) {
/*  841 */       if (paramT1 == null) {
/*  842 */         for (byte b = 0; b < i; b++) {
/*  843 */           if (paramList.get(b) == null) {
/*  844 */             paramList.set(b, paramT2);
/*  845 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  849 */         for (byte b = 0; b < i; b++) {
/*  850 */           if (paramT1.equals(paramList.get(b))) {
/*  851 */             paramList.set(b, paramT2);
/*  852 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  857 */       ListIterator<T> listIterator = paramList.listIterator();
/*  858 */       if (paramT1 == null) {
/*  859 */         for (byte b = 0; b < i; b++) {
/*  860 */           if (listIterator.next() == null) {
/*  861 */             listIterator.set(paramT2);
/*  862 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  866 */         for (byte b = 0; b < i; b++) {
/*  867 */           if (paramT1.equals(listIterator.next())) {
/*  868 */             listIterator.set(paramT2);
/*  869 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  874 */     return bool;
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
/*      */   public static int indexOfSubList(List<?> paramList1, List<?> paramList2) {
/*  898 */     int i = paramList1.size();
/*  899 */     int j = paramList2.size();
/*  900 */     int k = i - j;
/*      */     
/*  902 */     if (i < 35 || (paramList1 instanceof RandomAccess && paramList2 instanceof RandomAccess))
/*      */     
/*      */     { 
/*  905 */       for (byte b = 0; b <= k; b++) {
/*  906 */         byte b1 = 0, b2 = b; while (true) { if (b1 < j) {
/*  907 */             if (!eq(paramList2.get(b1), paramList1.get(b2)))
/*      */               break;  b1++; b2++; continue;
/*  909 */           }  return b; }
/*      */       
/*      */       }  }
/*  912 */     else { ListIterator<?> listIterator = paramList1.listIterator();
/*      */       
/*  914 */       for (byte b = 0; b <= k; b++) {
/*  915 */         ListIterator<?> listIterator1 = paramList2.listIterator();
/*  916 */         byte b1 = 0; while (true) { if (b1 < j) {
/*  917 */             if (!eq(listIterator1.next(), listIterator.next())) {
/*      */               
/*  919 */               for (byte b2 = 0; b2 < b1; b2++)
/*  920 */                 listIterator.previous();  break;
/*      */             }  b1++;
/*      */             continue;
/*      */           } 
/*  924 */           return b; }
/*      */       
/*      */       }  }
/*  927 */      return -1;
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
/*      */   public static int lastIndexOfSubList(List<?> paramList1, List<?> paramList2) {
/*  951 */     int i = paramList1.size();
/*  952 */     int j = paramList2.size();
/*  953 */     int k = i - j;
/*      */     
/*  955 */     if (i < 35 || paramList1 instanceof RandomAccess)
/*      */     
/*      */     { 
/*  958 */       for (int m = k; m >= 0; m--) {
/*  959 */         byte b = 0; int n = m; while (true) { if (b < j) {
/*  960 */             if (!eq(paramList2.get(b), paramList1.get(n)))
/*      */               break;  b++; n++; continue;
/*  962 */           }  return m; }
/*      */       
/*      */       }  }
/*  965 */     else { if (k < 0)
/*  966 */         return -1; 
/*  967 */       ListIterator<?> listIterator = paramList1.listIterator(k);
/*      */       
/*  969 */       for (int m = k; m >= 0; m--) {
/*  970 */         ListIterator<?> listIterator1 = paramList2.listIterator();
/*  971 */         byte b = 0; while (true) { if (b < j) {
/*  972 */             if (!eq(listIterator1.next(), listIterator.next())) {
/*  973 */               if (m != 0)
/*      */               {
/*  975 */                 for (byte b1 = 0; b1 <= b + 1; b1++)
/*  976 */                   listIterator.previous();  }  break;
/*      */             } 
/*      */             b++;
/*      */             continue;
/*      */           } 
/*  981 */           return m; }
/*      */       
/*      */       }  }
/*  984 */      return -1;
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
/*      */   public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> paramCollection) {
/* 1013 */     return new UnmodifiableCollection<>(paramCollection);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1820017752578914078L;
/*      */     
/*      */     final Collection<? extends E> c;
/*      */     
/*      */     UnmodifiableCollection(Collection<? extends E> param1Collection) {
/* 1025 */       if (param1Collection == null)
/* 1026 */         throw new NullPointerException(); 
/* 1027 */       this.c = param1Collection;
/*      */     }
/*      */     
/* 1030 */     public int size() { return this.c.size(); }
/* 1031 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 1032 */     public boolean contains(Object param1Object) { return this.c.contains(param1Object); }
/* 1033 */     public Object[] toArray() { return this.c.toArray(); }
/* 1034 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.c.toArray(param1ArrayOfT); } public String toString() {
/* 1035 */       return this.c.toString();
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 1038 */       return new Iterator<E>() {
/* 1039 */           private final Iterator<? extends E> i = Collections.UnmodifiableCollection.this.c.iterator();
/*      */           
/* 1041 */           public boolean hasNext() { return this.i.hasNext(); } public E next() {
/* 1042 */             return this.i.next();
/*      */           } public void remove() {
/* 1044 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 1049 */             this.i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public boolean add(E param1E) {
/* 1055 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 1058 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 1062 */       return this.c.containsAll(param1Collection);
/*      */     }
/*      */     public boolean addAll(Collection<? extends E> param1Collection) {
/* 1065 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 1068 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 1071 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void clear() {
/* 1074 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 1080 */       this.c.forEach(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 1084 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1089 */       return (Spliterator)this.c.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> stream() {
/* 1094 */       return (Stream)this.c.stream();
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 1099 */       return (Stream)this.c.parallelStream();
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
/*      */   public static <T> Set<T> unmodifiableSet(Set<? extends T> paramSet) {
/* 1118 */     return new UnmodifiableSet<>(paramSet);
/*      */   }
/*      */   
/*      */   static class UnmodifiableSet<E>
/*      */     extends UnmodifiableCollection<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -9215047833775013803L;
/*      */     
/*      */     UnmodifiableSet(Set<? extends E> param1Set) {
/* 1128 */       super(param1Set);
/* 1129 */     } public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); } public int hashCode() {
/* 1130 */       return this.c.hashCode();
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
/*      */   public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> paramSortedSet) {
/* 1151 */     return new UnmodifiableSortedSet<>(paramSortedSet);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableSortedSet<E>
/*      */     extends UnmodifiableSet<E>
/*      */     implements SortedSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4929149591599911165L;
/*      */     private final SortedSet<E> ss;
/*      */     
/*      */     UnmodifiableSortedSet(SortedSet<E> param1SortedSet) {
/* 1163 */       super(param1SortedSet); this.ss = param1SortedSet;
/*      */     } public Comparator<? super E> comparator() {
/* 1165 */       return this.ss.comparator();
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 1168 */       return new UnmodifiableSortedSet(this.ss.subSet(param1E1, param1E2));
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 1171 */       return new UnmodifiableSortedSet(this.ss.headSet(param1E));
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 1174 */       return new UnmodifiableSortedSet(this.ss.tailSet(param1E));
/*      */     }
/*      */     
/* 1177 */     public E first() { return this.ss.first(); } public E last() {
/* 1178 */       return this.ss.last();
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
/*      */   public static <T> NavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> paramNavigableSet) {
/* 1200 */     return new UnmodifiableNavigableSet<>(paramNavigableSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableNavigableSet<E>
/*      */     extends UnmodifiableSortedSet<E>
/*      */     implements NavigableSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6027448201786391929L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class EmptyNavigableSet<E>
/*      */       extends UnmodifiableNavigableSet<E>
/*      */       implements Serializable
/*      */     {
/*      */       private static final long serialVersionUID = -6291252904449939134L;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EmptyNavigableSet() {
/* 1226 */         super(new TreeSet<>());
/*      */       }
/*      */       private Object readResolve() {
/* 1229 */         return Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
/*      */       }
/*      */     }
/*      */     
/* 1233 */     private static final NavigableSet<?> EMPTY_NAVIGABLE_SET = new EmptyNavigableSet();
/*      */ 
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */ 
/*      */     
/*      */     UnmodifiableNavigableSet(NavigableSet<E> param1NavigableSet) {
/* 1241 */       super(param1NavigableSet); this.ns = param1NavigableSet;
/*      */     }
/* 1243 */     public E lower(E param1E) { return this.ns.lower(param1E); }
/* 1244 */     public E floor(E param1E) { return this.ns.floor(param1E); }
/* 1245 */     public E ceiling(E param1E) { return this.ns.ceiling(param1E); }
/* 1246 */     public E higher(E param1E) { return this.ns.higher(param1E); }
/* 1247 */     public E pollFirst() { throw new UnsupportedOperationException(); } public E pollLast() {
/* 1248 */       throw new UnsupportedOperationException();
/*      */     } public NavigableSet<E> descendingSet() {
/* 1250 */       return new UnmodifiableNavigableSet(this.ns.descendingSet());
/*      */     } public Iterator<E> descendingIterator() {
/* 1252 */       return descendingSet().iterator();
/*      */     }
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 1255 */       return new UnmodifiableNavigableSet(this.ns
/* 1256 */           .subSet(param1E1, param1Boolean1, param1E2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 1260 */       return new UnmodifiableNavigableSet(this.ns
/* 1261 */           .headSet(param1E, param1Boolean));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 1265 */       return new UnmodifiableNavigableSet(this.ns
/* 1266 */           .tailSet(param1E, param1Boolean));
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
/*      */   public static <T> List<T> unmodifiableList(List<? extends T> paramList) {
/* 1287 */     return (paramList instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(paramList) : new UnmodifiableList<>(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableList<E>
/*      */     extends UnmodifiableCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = -283967356065247728L;
/*      */     
/*      */     final List<? extends E> list;
/*      */ 
/*      */     
/*      */     UnmodifiableList(List<? extends E> param1List) {
/* 1302 */       super(param1List);
/* 1303 */       this.list = param1List;
/*      */     }
/*      */     
/* 1306 */     public boolean equals(Object param1Object) { return (param1Object == this || this.list.equals(param1Object)); } public int hashCode() {
/* 1307 */       return this.list.hashCode();
/*      */     } public E get(int param1Int) {
/* 1309 */       return this.list.get(param1Int);
/*      */     } public E set(int param1Int, E param1E) {
/* 1311 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(int param1Int, E param1E) {
/* 1314 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public E remove(int param1Int) {
/* 1317 */       throw new UnsupportedOperationException();
/*      */     }
/* 1319 */     public int indexOf(Object param1Object) { return this.list.indexOf(param1Object); } public int lastIndexOf(Object param1Object) {
/* 1320 */       return this.list.lastIndexOf(param1Object);
/*      */     } public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 1322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 1327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 1331 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 1334 */       return listIterator(0);
/*      */     }
/*      */     public ListIterator<E> listIterator(final int index) {
/* 1337 */       return new ListIterator<E>() {
/* 1338 */           private final ListIterator<? extends E> i = Collections.UnmodifiableList.this.list
/* 1339 */             .listIterator(index);
/*      */           
/* 1341 */           public boolean hasNext() { return this.i.hasNext(); }
/* 1342 */           public E next() { return this.i.next(); }
/* 1343 */           public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 1344 */           public E previous() { return this.i.previous(); }
/* 1345 */           public int nextIndex() { return this.i.nextIndex(); } public int previousIndex() {
/* 1346 */             return this.i.previousIndex();
/*      */           }
/*      */           public void remove() {
/* 1349 */             throw new UnsupportedOperationException();
/*      */           }
/*      */           public void set(E param2E) {
/* 1352 */             throw new UnsupportedOperationException();
/*      */           }
/*      */           public void add(E param2E) {
/* 1355 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 1360 */             this.i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1366 */       return new UnmodifiableList(this.list.subList(param1Int1, param1Int2));
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
/*      */     private Object readResolve() {
/* 1382 */       return (this.list instanceof RandomAccess) ? new Collections.UnmodifiableRandomAccessList<>(this.list) : this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableRandomAccessList<E>
/*      */     extends UnmodifiableList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = -2542308836966382001L;
/*      */ 
/*      */     
/*      */     UnmodifiableRandomAccessList(List<? extends E> param1List) {
/* 1395 */       super(param1List);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1399 */       return new UnmodifiableRandomAccessList(this.list
/* 1400 */           .subList(param1Int1, param1Int2));
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
/*      */     private Object writeReplace() {
/* 1412 */       return new Collections.UnmodifiableList<>(this.list);
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
/*      */   public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> paramMap) {
/* 1433 */     return new UnmodifiableMap<>(paramMap);
/*      */   }
/*      */   
/*      */   private static class UnmodifiableMap<K, V>
/*      */     implements Map<K, V>, Serializable {
/*      */     private static final long serialVersionUID = -1034234728574286014L;
/*      */     private final Map<? extends K, ? extends V> m;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     UnmodifiableMap(Map<? extends K, ? extends V> param1Map) {
/* 1445 */       if (param1Map == null)
/* 1446 */         throw new NullPointerException(); 
/* 1447 */       this.m = param1Map;
/*      */     }
/*      */     
/* 1450 */     public int size() { return this.m.size(); }
/* 1451 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 1452 */     public boolean containsKey(Object param1Object) { return this.m.containsKey(param1Object); }
/* 1453 */     public boolean containsValue(Object param1Object) { return this.m.containsValue(param1Object); } public V get(Object param1Object) {
/* 1454 */       return this.m.get(param1Object);
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 1457 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public V remove(Object param1Object) {
/* 1460 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 1463 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void clear() {
/* 1466 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1474 */       if (this.keySet == null)
/* 1475 */         this.keySet = Collections.unmodifiableSet(this.m.keySet()); 
/* 1476 */       return this.keySet;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1480 */       if (this.entrySet == null)
/* 1481 */         this.entrySet = new UnmodifiableEntrySet<>(this.m.entrySet()); 
/* 1482 */       return this.entrySet;
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 1486 */       if (this.values == null)
/* 1487 */         this.values = Collections.unmodifiableCollection(this.m.values()); 
/* 1488 */       return this.values;
/*      */     }
/*      */     
/* 1491 */     public boolean equals(Object param1Object) { return (param1Object == this || this.m.equals(param1Object)); }
/* 1492 */     public int hashCode() { return this.m.hashCode(); } public String toString() {
/* 1493 */       return this.m.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 1500 */       return this.m.getOrDefault(param1Object, param1V);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 1505 */       this.m.forEach(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1510 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 1515 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 1520 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 1525 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 1530 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 1535 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1541 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1547 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 1553 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class UnmodifiableEntrySet<K, V>
/*      */       extends Collections.UnmodifiableSet<Map.Entry<K, V>>
/*      */     {
/*      */       private static final long serialVersionUID = 7854390611657943733L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> param2Set) {
/* 1571 */         super(param2Set);
/*      */       }
/*      */       
/*      */       static <K, V> Consumer<Map.Entry<K, V>> entryConsumer(Consumer<? super Map.Entry<K, V>> param2Consumer) {
/* 1575 */         return param2Entry -> param2Consumer.accept(new UnmodifiableEntry<>(param2Entry));
/*      */       }
/*      */       
/*      */       public void forEach(Consumer<? super Map.Entry<K, V>> param2Consumer) {
/* 1579 */         Objects.requireNonNull(param2Consumer);
/* 1580 */         this.c.forEach(entryConsumer(param2Consumer));
/*      */       }
/*      */       
/*      */       static final class UnmodifiableEntrySetSpliterator<K, V>
/*      */         implements Spliterator<Map.Entry<K, V>> {
/*      */         final Spliterator<Map.Entry<K, V>> s;
/*      */         
/*      */         UnmodifiableEntrySetSpliterator(Spliterator<Map.Entry<K, V>> param3Spliterator) {
/* 1588 */           this.s = param3Spliterator;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param3Consumer) {
/* 1593 */           Objects.requireNonNull(param3Consumer);
/* 1594 */           return this.s.tryAdvance(Collections.UnmodifiableMap.UnmodifiableEntrySet.entryConsumer(param3Consumer));
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param3Consumer) {
/* 1599 */           Objects.requireNonNull(param3Consumer);
/* 1600 */           this.s.forEachRemaining(Collections.UnmodifiableMap.UnmodifiableEntrySet.entryConsumer(param3Consumer));
/*      */         }
/*      */ 
/*      */         
/*      */         public Spliterator<Map.Entry<K, V>> trySplit() {
/* 1605 */           Spliterator<Map.Entry<K, V>> spliterator = this.s.trySplit();
/* 1606 */           return (spliterator == null) ? null : new UnmodifiableEntrySetSpliterator(spliterator);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public long estimateSize() {
/* 1613 */           return this.s.estimateSize();
/*      */         }
/*      */ 
/*      */         
/*      */         public long getExactSizeIfKnown() {
/* 1618 */           return this.s.getExactSizeIfKnown();
/*      */         }
/*      */ 
/*      */         
/*      */         public int characteristics() {
/* 1623 */           return this.s.characteristics();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasCharacteristics(int param3Int) {
/* 1628 */           return this.s.hasCharacteristics(param3Int);
/*      */         }
/*      */ 
/*      */         
/*      */         public Comparator<? super Map.Entry<K, V>> getComparator() {
/* 1633 */           return this.s.getComparator();
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1639 */         return new UnmodifiableEntrySetSpliterator<>((Spliterator)this.c
/* 1640 */             .spliterator());
/*      */       }
/*      */ 
/*      */       
/*      */       public Stream<Map.Entry<K, V>> stream() {
/* 1645 */         return StreamSupport.stream(spliterator(), false);
/*      */       }
/*      */ 
/*      */       
/*      */       public Stream<Map.Entry<K, V>> parallelStream() {
/* 1650 */         return StreamSupport.stream(spliterator(), true);
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1654 */         return new Iterator<Map.Entry<K, V>>() {
/* 1655 */             private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i = Collections.UnmodifiableMap.UnmodifiableEntrySet.this.c.iterator();
/*      */             
/*      */             public boolean hasNext() {
/* 1658 */               return this.i.hasNext();
/*      */             }
/*      */             public Map.Entry<K, V> next() {
/* 1661 */               return new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(this.i.next());
/*      */             }
/*      */             public void remove() {
/* 1664 */               throw new UnsupportedOperationException();
/*      */             }
/*      */           };
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 1671 */         Object[] arrayOfObject = this.c.toArray();
/* 1672 */         for (byte b = 0; b < arrayOfObject.length; b++)
/* 1673 */           arrayOfObject[b] = new UnmodifiableEntry<>((Map.Entry<?, ?>)arrayOfObject[b]); 
/* 1674 */         return arrayOfObject;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public <T> T[] toArray(T[] param2ArrayOfT) {
/* 1682 */         Object[] arrayOfObject = this.c.toArray((param2ArrayOfT.length == 0) ? (Object[])param2ArrayOfT : Arrays.<Object>copyOf((Object[])param2ArrayOfT, 0));
/*      */         
/* 1684 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1685 */           arrayOfObject[b] = new UnmodifiableEntry<>((Map.Entry<?, ?>)arrayOfObject[b]);
/*      */         }
/* 1687 */         if (arrayOfObject.length > param2ArrayOfT.length) {
/* 1688 */           return (T[])arrayOfObject;
/*      */         }
/* 1690 */         System.arraycopy(arrayOfObject, 0, param2ArrayOfT, 0, arrayOfObject.length);
/* 1691 */         if (param2ArrayOfT.length > arrayOfObject.length)
/* 1692 */           param2ArrayOfT[arrayOfObject.length] = null; 
/* 1693 */         return param2ArrayOfT;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object param2Object) {
/* 1703 */         if (!(param2Object instanceof Map.Entry))
/* 1704 */           return false; 
/* 1705 */         return this.c.contains(new UnmodifiableEntry<>((Map.Entry<?, ?>)param2Object));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean containsAll(Collection<?> param2Collection) {
/* 1715 */         for (Object object : param2Collection) {
/* 1716 */           if (!contains(object))
/* 1717 */             return false; 
/*      */         } 
/* 1719 */         return true;
/*      */       }
/*      */       public boolean equals(Object param2Object) {
/* 1722 */         if (param2Object == this) {
/* 1723 */           return true;
/*      */         }
/* 1725 */         if (!(param2Object instanceof Set))
/* 1726 */           return false; 
/* 1727 */         Set<?> set = (Set)param2Object;
/* 1728 */         if (set.size() != this.c.size())
/* 1729 */           return false; 
/* 1730 */         return containsAll(set);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private static class UnmodifiableEntry<K, V>
/*      */         implements Map.Entry<K, V>
/*      */       {
/*      */         private Map.Entry<? extends K, ? extends V> e;
/*      */ 
/*      */ 
/*      */         
/*      */         UnmodifiableEntry(Map.Entry<? extends K, ? extends V> param3Entry) {
/* 1744 */           this.e = Objects.<Map.Entry<? extends K, ? extends V>>requireNonNull(param3Entry);
/*      */         }
/* 1746 */         public K getKey() { return this.e.getKey(); } public V getValue() {
/* 1747 */           return this.e.getValue();
/*      */         } public V setValue(V param3V) {
/* 1749 */           throw new UnsupportedOperationException();
/*      */         } public int hashCode() {
/* 1751 */           return this.e.hashCode();
/*      */         } public boolean equals(Object param3Object) {
/* 1753 */           if (this == param3Object)
/* 1754 */             return true; 
/* 1755 */           if (!(param3Object instanceof Map.Entry))
/* 1756 */             return false; 
/* 1757 */           Map.Entry entry = (Map.Entry)param3Object;
/* 1758 */           return (Collections.eq(this.e.getKey(), entry.getKey()) && 
/* 1759 */             Collections.eq(this.e.getValue(), entry.getValue()));
/*      */         } public String toString() {
/* 1761 */           return this.e.toString();
/*      */         }
/*      */       }
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
/*      */   public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> paramSortedMap) {
/* 1785 */     return new UnmodifiableSortedMap<>(paramSortedMap);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableSortedMap<K, V>
/*      */     extends UnmodifiableMap<K, V>
/*      */     implements SortedMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -8806743815996713206L;
/*      */     
/*      */     private final SortedMap<K, ? extends V> sm;
/*      */     
/*      */     UnmodifiableSortedMap(SortedMap<K, ? extends V> param1SortedMap) {
/* 1798 */       super(param1SortedMap); this.sm = param1SortedMap; } public Comparator<? super K> comparator() {
/* 1799 */       return this.sm.comparator();
/*      */     } public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 1801 */       return new UnmodifiableSortedMap(this.sm.subMap(param1K1, param1K2));
/*      */     } public SortedMap<K, V> headMap(K param1K) {
/* 1803 */       return new UnmodifiableSortedMap(this.sm.headMap(param1K));
/*      */     }
/* 1805 */     public SortedMap<K, V> tailMap(K param1K) { return new UnmodifiableSortedMap(this.sm.tailMap(param1K)); }
/* 1806 */     public K firstKey() { return this.sm.firstKey(); } public K lastKey() {
/* 1807 */       return this.sm.lastKey();
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
/*      */   public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> paramNavigableMap) {
/* 1830 */     return new UnmodifiableNavigableMap<>(paramNavigableMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableNavigableMap<K, V>
/*      */     extends UnmodifiableSortedMap<K, V>
/*      */     implements NavigableMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4858195264774772197L;
/*      */ 
/*      */ 
/*      */     
/*      */     private static class EmptyNavigableMap<K, V>
/*      */       extends UnmodifiableNavigableMap<K, V>
/*      */       implements Serializable
/*      */     {
/*      */       private static final long serialVersionUID = -2239321462712562324L;
/*      */ 
/*      */ 
/*      */       
/*      */       EmptyNavigableMap() {
/* 1853 */         super(new TreeMap<>());
/*      */       }
/*      */       
/*      */       public NavigableSet<K> navigableKeySet() {
/* 1857 */         return Collections.emptyNavigableSet();
/*      */       } private Object readResolve() {
/* 1859 */         return Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1865 */     private static final EmptyNavigableMap<?, ?> EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap<>();
/*      */ 
/*      */ 
/*      */     
/*      */     private final NavigableMap<K, ? extends V> nm;
/*      */ 
/*      */ 
/*      */     
/*      */     UnmodifiableNavigableMap(NavigableMap<K, ? extends V> param1NavigableMap) {
/* 1874 */       super(param1NavigableMap); this.nm = param1NavigableMap;
/*      */     }
/* 1876 */     public K lowerKey(K param1K) { return this.nm.lowerKey(param1K); }
/* 1877 */     public K floorKey(K param1K) { return this.nm.floorKey(param1K); }
/* 1878 */     public K ceilingKey(K param1K) { return this.nm.ceilingKey(param1K); } public K higherKey(K param1K) {
/* 1879 */       return this.nm.higherKey(param1K);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 1883 */       Map.Entry<K, ? extends V> entry = this.nm.lowerEntry(param1K);
/* 1884 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> floorEntry(K param1K) {
/* 1891 */       Map.Entry<K, ? extends V> entry = this.nm.floorEntry(param1K);
/* 1892 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> ceilingEntry(K param1K) {
/* 1899 */       Map.Entry<K, ? extends V> entry = this.nm.ceilingEntry(param1K);
/* 1900 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> higherEntry(K param1K) {
/* 1908 */       Map.Entry<K, ? extends V> entry = this.nm.higherEntry(param1K);
/* 1909 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> firstEntry() {
/* 1916 */       Map.Entry<K, ? extends V> entry = this.nm.firstEntry();
/* 1917 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 1924 */       Map.Entry<K, ? extends V> entry = this.nm.lastEntry();
/* 1925 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 1931 */       throw new UnsupportedOperationException();
/*      */     } public Map.Entry<K, V> pollLastEntry() {
/* 1933 */       throw new UnsupportedOperationException();
/*      */     } public NavigableMap<K, V> descendingMap() {
/* 1935 */       return Collections.unmodifiableNavigableMap(this.nm.descendingMap());
/*      */     } public NavigableSet<K> navigableKeySet() {
/* 1937 */       return Collections.unmodifiableNavigableSet(this.nm.navigableKeySet());
/*      */     } public NavigableSet<K> descendingKeySet() {
/* 1939 */       return Collections.unmodifiableNavigableSet(this.nm.descendingKeySet());
/*      */     }
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 1942 */       return Collections.unmodifiableNavigableMap(this.nm
/* 1943 */           .subMap(param1K1, param1Boolean1, param1K2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 1947 */       return Collections.unmodifiableNavigableMap(this.nm.headMap(param1K, param1Boolean));
/*      */     } public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 1949 */       return Collections.unmodifiableNavigableMap(this.nm.tailMap(param1K, param1Boolean));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EmptyNavigableMap<K, V>
/*      */     extends UnmodifiableNavigableMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -2239321462712562324L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EmptyNavigableMap() {
/*      */       super(new TreeMap<>());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/*      */       return Collections.emptyNavigableSet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/*      */       return Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Collection<T> synchronizedCollection(Collection<T> paramCollection) {
/* 1988 */     return new SynchronizedCollection<>(paramCollection);
/*      */   }
/*      */   
/*      */   static <T> Collection<T> synchronizedCollection(Collection<T> paramCollection, Object paramObject) {
/* 1992 */     return new SynchronizedCollection<>(paramCollection, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static class SynchronizedCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3053995032091335093L;
/*      */     
/*      */     final Collection<E> c;
/*      */     final Object mutex;
/*      */     
/*      */     SynchronizedCollection(Collection<E> param1Collection) {
/* 2005 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection);
/* 2006 */       this.mutex = this;
/*      */     }
/*      */     
/*      */     SynchronizedCollection(Collection<E> param1Collection, Object param1Object) {
/* 2010 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection);
/* 2011 */       this.mutex = Objects.requireNonNull(param1Object);
/*      */     }
/*      */     
/*      */     public int size() {
/* 2015 */       synchronized (this.mutex) { return this.c.size(); }
/*      */     
/*      */     } public boolean isEmpty() {
/* 2018 */       synchronized (this.mutex) { return this.c.isEmpty(); }
/*      */     
/*      */     } public boolean contains(Object param1Object) {
/* 2021 */       synchronized (this.mutex) { return this.c.contains(param1Object); }
/*      */     
/*      */     } public Object[] toArray() {
/* 2024 */       synchronized (this.mutex) { return this.c.toArray(); }
/*      */     
/*      */     } public <T> T[] toArray(T[] param1ArrayOfT) {
/* 2027 */       synchronized (this.mutex) { return this.c.toArray(param1ArrayOfT); }
/*      */     
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 2031 */       return this.c.iterator();
/*      */     }
/*      */     
/*      */     public boolean add(E param1E) {
/* 2035 */       synchronized (this.mutex) { return this.c.add(param1E); }
/*      */     
/*      */     } public boolean remove(Object param1Object) {
/* 2038 */       synchronized (this.mutex) { return this.c.remove(param1Object); }
/*      */     
/*      */     }
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 2042 */       synchronized (this.mutex) { return this.c.containsAll(param1Collection); }
/*      */     
/*      */     } public boolean addAll(Collection<? extends E> param1Collection) {
/* 2045 */       synchronized (this.mutex) { return this.c.addAll(param1Collection); }
/*      */     
/*      */     } public boolean removeAll(Collection<?> param1Collection) {
/* 2048 */       synchronized (this.mutex) { return this.c.removeAll(param1Collection); }
/*      */     
/*      */     } public boolean retainAll(Collection<?> param1Collection) {
/* 2051 */       synchronized (this.mutex) { return this.c.retainAll(param1Collection); }
/*      */     
/*      */     } public void clear() {
/* 2054 */       synchronized (this.mutex) { this.c.clear(); }
/*      */     
/*      */     } public String toString() {
/* 2057 */       synchronized (this.mutex) { return this.c.toString(); }
/*      */     
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 2062 */       synchronized (this.mutex) { this.c.forEach(param1Consumer); }
/*      */     
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 2066 */       synchronized (this.mutex) { return this.c.removeIf(param1Predicate); }
/*      */     
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 2070 */       return this.c.spliterator();
/*      */     }
/*      */     
/*      */     public Stream<E> stream() {
/* 2074 */       return this.c.stream();
/*      */     }
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 2078 */       return this.c.parallelStream();
/*      */     }
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2081 */       synchronized (this.mutex) { param1ObjectOutputStream.defaultWriteObject(); }
/*      */     
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Set<T> synchronizedSet(Set<T> paramSet) {
/* 2112 */     return new SynchronizedSet<>(paramSet);
/*      */   }
/*      */   
/*      */   static <T> Set<T> synchronizedSet(Set<T> paramSet, Object paramObject) {
/* 2116 */     return new SynchronizedSet<>(paramSet, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static class SynchronizedSet<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements Set<E>
/*      */   {
/*      */     private static final long serialVersionUID = 487447009682186044L;
/*      */ 
/*      */     
/*      */     SynchronizedSet(Set<E> param1Set) {
/* 2128 */       super(param1Set);
/*      */     }
/*      */     SynchronizedSet(Set<E> param1Set, Object param1Object) {
/* 2131 */       super(param1Set, param1Object);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2135 */       if (this == param1Object)
/* 2136 */         return true; 
/* 2137 */       synchronized (this.mutex) { return this.c.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2140 */       synchronized (this.mutex) { return this.c.hashCode(); }
/*      */     
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> paramSortedSet) {
/* 2183 */     return new SynchronizedSortedSet<>(paramSortedSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedSortedSet<E>
/*      */     extends SynchronizedSet<E>
/*      */     implements SortedSet<E>
/*      */   {
/*      */     private static final long serialVersionUID = 8695801310862127406L;
/*      */     
/*      */     private final SortedSet<E> ss;
/*      */ 
/*      */     
/*      */     SynchronizedSortedSet(SortedSet<E> param1SortedSet) {
/* 2198 */       super(param1SortedSet);
/* 2199 */       this.ss = param1SortedSet;
/*      */     }
/*      */     SynchronizedSortedSet(SortedSet<E> param1SortedSet, Object param1Object) {
/* 2202 */       super(param1SortedSet, param1Object);
/* 2203 */       this.ss = param1SortedSet;
/*      */     }
/*      */     
/*      */     public Comparator<? super E> comparator() {
/* 2207 */       synchronized (this.mutex) { return this.ss.comparator(); }
/*      */     
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 2211 */       synchronized (this.mutex) {
/* 2212 */         return new SynchronizedSortedSet(this.ss
/* 2213 */             .subSet(param1E1, param1E2), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 2217 */       synchronized (this.mutex) {
/* 2218 */         return new SynchronizedSortedSet(this.ss.headSet(param1E), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 2222 */       synchronized (this.mutex) {
/* 2223 */         return new SynchronizedSortedSet(this.ss.tailSet(param1E), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public E first() {
/* 2228 */       synchronized (this.mutex) { return this.ss.first(); }
/*      */     
/*      */     } public E last() {
/* 2231 */       synchronized (this.mutex) { return this.ss.last(); }
/*      */     
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> NavigableSet<T> synchronizedNavigableSet(NavigableSet<T> paramNavigableSet) {
/* 2276 */     return new SynchronizedNavigableSet<>(paramNavigableSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedNavigableSet<E>
/*      */     extends SynchronizedSortedSet<E>
/*      */     implements NavigableSet<E>
/*      */   {
/*      */     private static final long serialVersionUID = -5505529816273629798L;
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */     
/*      */     SynchronizedNavigableSet(NavigableSet<E> param1NavigableSet) {
/* 2291 */       super(param1NavigableSet);
/* 2292 */       this.ns = param1NavigableSet;
/*      */     }
/*      */     
/*      */     SynchronizedNavigableSet(NavigableSet<E> param1NavigableSet, Object param1Object) {
/* 2296 */       super(param1NavigableSet, param1Object);
/* 2297 */       this.ns = param1NavigableSet;
/*      */     }
/* 2299 */     public E lower(E param1E) { synchronized (this.mutex) { return this.ns.lower(param1E); }
/* 2300 */        } public E floor(E param1E) { synchronized (this.mutex) { return this.ns.floor(param1E); }
/* 2301 */        } public E ceiling(E param1E) { synchronized (this.mutex) { return this.ns.ceiling(param1E); }
/* 2302 */        } public E higher(E param1E) { synchronized (this.mutex) { return this.ns.higher(param1E); }
/* 2303 */        } public E pollFirst() { synchronized (this.mutex) { return this.ns.pollFirst(); }
/* 2304 */        } public E pollLast() { synchronized (this.mutex) { return this.ns.pollLast(); }
/*      */        }
/*      */      public NavigableSet<E> descendingSet() {
/* 2307 */       synchronized (this.mutex) {
/* 2308 */         return new SynchronizedNavigableSet(this.ns.descendingSet(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Iterator<E> descendingIterator() {
/* 2313 */       synchronized (this.mutex) { return descendingSet().iterator(); }
/*      */     
/*      */     } public NavigableSet<E> subSet(E param1E1, E param1E2) {
/* 2316 */       synchronized (this.mutex) {
/* 2317 */         return new SynchronizedNavigableSet(this.ns.subSet(param1E1, true, param1E2, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public NavigableSet<E> headSet(E param1E) {
/* 2321 */       synchronized (this.mutex) {
/* 2322 */         return new SynchronizedNavigableSet(this.ns.headSet(param1E, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E) {
/* 2326 */       synchronized (this.mutex) {
/* 2327 */         return new SynchronizedNavigableSet(this.ns.tailSet(param1E, true), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 2332 */       synchronized (this.mutex) {
/* 2333 */         return new SynchronizedNavigableSet(this.ns.subSet(param1E1, param1Boolean1, param1E2, param1Boolean2), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 2338 */       synchronized (this.mutex) {
/* 2339 */         return new SynchronizedNavigableSet(this.ns.headSet(param1E, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 2344 */       synchronized (this.mutex) {
/* 2345 */         return new SynchronizedNavigableSet(this.ns.tailSet(param1E, param1Boolean), this.mutex);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> List<T> synchronizedList(List<T> paramList) {
/* 2377 */     return (paramList instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(paramList) : new SynchronizedList<>(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> List<T> synchronizedList(List<T> paramList, Object paramObject) {
/* 2383 */     return (paramList instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(paramList, paramObject) : new SynchronizedList<>(paramList, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedList<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = -7754090372962971524L;
/*      */ 
/*      */     
/*      */     final List<E> list;
/*      */ 
/*      */     
/*      */     SynchronizedList(List<E> param1List) {
/* 2399 */       super(param1List);
/* 2400 */       this.list = param1List;
/*      */     }
/*      */     SynchronizedList(List<E> param1List, Object param1Object) {
/* 2403 */       super(param1List, param1Object);
/* 2404 */       this.list = param1List;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2408 */       if (this == param1Object)
/* 2409 */         return true; 
/* 2410 */       synchronized (this.mutex) { return this.list.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2413 */       synchronized (this.mutex) { return this.list.hashCode(); }
/*      */     
/*      */     }
/*      */     public E get(int param1Int) {
/* 2417 */       synchronized (this.mutex) { return this.list.get(param1Int); }
/*      */     
/*      */     } public E set(int param1Int, E param1E) {
/* 2420 */       synchronized (this.mutex) { return this.list.set(param1Int, param1E); }
/*      */     
/*      */     } public void add(int param1Int, E param1E) {
/* 2423 */       synchronized (this.mutex) { this.list.add(param1Int, param1E); }
/*      */     
/*      */     } public E remove(int param1Int) {
/* 2426 */       synchronized (this.mutex) { return this.list.remove(param1Int); }
/*      */     
/*      */     }
/*      */     public int indexOf(Object param1Object) {
/* 2430 */       synchronized (this.mutex) { return this.list.indexOf(param1Object); }
/*      */     
/*      */     } public int lastIndexOf(Object param1Object) {
/* 2433 */       synchronized (this.mutex) { return this.list.lastIndexOf(param1Object); }
/*      */     
/*      */     }
/*      */     public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 2437 */       synchronized (this.mutex) { return this.list.addAll(param1Int, param1Collection); }
/*      */     
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 2441 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(int param1Int) {
/* 2445 */       return this.list.listIterator(param1Int);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 2449 */       synchronized (this.mutex) {
/* 2450 */         return new SynchronizedList(this.list.subList(param1Int1, param1Int2), this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 2457 */       synchronized (this.mutex) { this.list.replaceAll(param1UnaryOperator); }
/*      */     
/*      */     }
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 2461 */       synchronized (this.mutex) { this.list.sort(param1Comparator); }
/*      */     
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
/*      */     private Object readResolve() {
/* 2477 */       return (this.list instanceof RandomAccess) ? new Collections.SynchronizedRandomAccessList<>(this.list) : this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedRandomAccessList<E>
/*      */     extends SynchronizedList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = 1530674583602358482L;
/*      */ 
/*      */     
/*      */     SynchronizedRandomAccessList(List<E> param1List) {
/* 2491 */       super(param1List);
/*      */     }
/*      */     
/*      */     SynchronizedRandomAccessList(List<E> param1List, Object param1Object) {
/* 2495 */       super(param1List, param1Object);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 2499 */       synchronized (this.mutex) {
/* 2500 */         return new SynchronizedRandomAccessList(this.list
/* 2501 */             .subList(param1Int1, param1Int2), this.mutex);
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
/*      */     private Object writeReplace() {
/* 2514 */       return new Collections.SynchronizedList<>(this.list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> Map<K, V> synchronizedMap(Map<K, V> paramMap) {
/* 2548 */     return new SynchronizedMap<>(paramMap);
/*      */   }
/*      */   
/*      */   private static class SynchronizedMap<K, V>
/*      */     implements Map<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1978198479659022715L;
/*      */     private final Map<K, V> m;
/*      */     final Object mutex;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     SynchronizedMap(Map<K, V> param1Map) {
/* 2562 */       this.m = Objects.<Map<K, V>>requireNonNull(param1Map);
/* 2563 */       this.mutex = this;
/*      */     }
/*      */     
/*      */     SynchronizedMap(Map<K, V> param1Map, Object param1Object) {
/* 2567 */       this.m = param1Map;
/* 2568 */       this.mutex = param1Object;
/*      */     }
/*      */     
/*      */     public int size() {
/* 2572 */       synchronized (this.mutex) { return this.m.size(); }
/*      */     
/*      */     } public boolean isEmpty() {
/* 2575 */       synchronized (this.mutex) { return this.m.isEmpty(); }
/*      */     
/*      */     } public boolean containsKey(Object param1Object) {
/* 2578 */       synchronized (this.mutex) { return this.m.containsKey(param1Object); }
/*      */     
/*      */     } public boolean containsValue(Object param1Object) {
/* 2581 */       synchronized (this.mutex) { return this.m.containsValue(param1Object); }
/*      */     
/*      */     } public V get(Object param1Object) {
/* 2584 */       synchronized (this.mutex) { return this.m.get(param1Object); }
/*      */     
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 2588 */       synchronized (this.mutex) { return this.m.put(param1K, param1V); }
/*      */     
/*      */     } public V remove(Object param1Object) {
/* 2591 */       synchronized (this.mutex) { return this.m.remove(param1Object); }
/*      */     
/*      */     } public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 2594 */       synchronized (this.mutex) { this.m.putAll(param1Map); }
/*      */     
/*      */     } public void clear() {
/* 2597 */       synchronized (this.mutex) { this.m.clear(); }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 2605 */       synchronized (this.mutex) {
/* 2606 */         if (this.keySet == null)
/* 2607 */           this.keySet = new Collections.SynchronizedSet<>(this.m.keySet(), this.mutex); 
/* 2608 */         return this.keySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 2613 */       synchronized (this.mutex) {
/* 2614 */         if (this.entrySet == null)
/* 2615 */           this.entrySet = new Collections.SynchronizedSet<>(this.m.entrySet(), this.mutex); 
/* 2616 */         return this.entrySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 2621 */       synchronized (this.mutex) {
/* 2622 */         if (this.values == null)
/* 2623 */           this.values = new Collections.SynchronizedCollection<>(this.m.values(), this.mutex); 
/* 2624 */         return this.values;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2629 */       if (this == param1Object)
/* 2630 */         return true; 
/* 2631 */       synchronized (this.mutex) { return this.m.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2634 */       synchronized (this.mutex) { return this.m.hashCode(); }
/*      */     
/*      */     } public String toString() {
/* 2637 */       synchronized (this.mutex) { return this.m.toString(); }
/*      */     
/*      */     }
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 2643 */       synchronized (this.mutex) { return this.m.getOrDefault(param1Object, param1V); }
/*      */     
/*      */     }
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 2647 */       synchronized (this.mutex) { this.m.forEach(param1BiConsumer); }
/*      */     
/*      */     }
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2651 */       synchronized (this.mutex) { this.m.replaceAll(param1BiFunction); }
/*      */     
/*      */     }
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 2655 */       synchronized (this.mutex) { return this.m.putIfAbsent(param1K, param1V); }
/*      */     
/*      */     }
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 2659 */       synchronized (this.mutex) { return this.m.remove(param1Object1, param1Object2); }
/*      */     
/*      */     }
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 2663 */       synchronized (this.mutex) { return this.m.replace(param1K, param1V1, param1V2); }
/*      */     
/*      */     }
/*      */     public V replace(K param1K, V param1V) {
/* 2667 */       synchronized (this.mutex) { return this.m.replace(param1K, param1V); }
/*      */     
/*      */     }
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 2672 */       synchronized (this.mutex) { return this.m.computeIfAbsent(param1K, param1Function); }
/*      */     
/*      */     }
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2677 */       synchronized (this.mutex) { return this.m.computeIfPresent(param1K, param1BiFunction); }
/*      */     
/*      */     }
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2682 */       synchronized (this.mutex) { return this.m.compute(param1K, param1BiFunction); }
/*      */     
/*      */     }
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 2687 */       synchronized (this.mutex) { return this.m.merge(param1K, param1V, param1BiFunction); }
/*      */     
/*      */     }
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2691 */       synchronized (this.mutex) { param1ObjectOutputStream.defaultWriteObject(); }
/*      */     
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> paramSortedMap) {
/* 2740 */     return new SynchronizedSortedMap<>(paramSortedMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedSortedMap<K, V>
/*      */     extends SynchronizedMap<K, V>
/*      */     implements SortedMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = -8798146769416483793L;
/*      */     
/*      */     private final SortedMap<K, V> sm;
/*      */ 
/*      */     
/*      */     SynchronizedSortedMap(SortedMap<K, V> param1SortedMap) {
/* 2755 */       super(param1SortedMap);
/* 2756 */       this.sm = param1SortedMap;
/*      */     }
/*      */     SynchronizedSortedMap(SortedMap<K, V> param1SortedMap, Object param1Object) {
/* 2759 */       super(param1SortedMap, param1Object);
/* 2760 */       this.sm = param1SortedMap;
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 2764 */       synchronized (this.mutex) { return this.sm.comparator(); }
/*      */     
/*      */     }
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 2768 */       synchronized (this.mutex) {
/* 2769 */         return new SynchronizedSortedMap(this.sm
/* 2770 */             .subMap(param1K1, param1K2), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 2774 */       synchronized (this.mutex) {
/* 2775 */         return new SynchronizedSortedMap(this.sm.headMap(param1K), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 2779 */       synchronized (this.mutex) {
/* 2780 */         return new SynchronizedSortedMap(this.sm.tailMap(param1K), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 2785 */       synchronized (this.mutex) { return this.sm.firstKey(); }
/*      */     
/*      */     } public K lastKey() {
/* 2788 */       synchronized (this.mutex) { return this.sm.lastKey(); }
/*      */     
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> paramNavigableMap) {
/* 2839 */     return new SynchronizedNavigableMap<>(paramNavigableMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedNavigableMap<K, V>
/*      */     extends SynchronizedSortedMap<K, V>
/*      */     implements NavigableMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 699392247599746807L;
/*      */ 
/*      */     
/*      */     private final NavigableMap<K, V> nm;
/*      */ 
/*      */ 
/*      */     
/*      */     SynchronizedNavigableMap(NavigableMap<K, V> param1NavigableMap) {
/* 2856 */       super(param1NavigableMap);
/* 2857 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     SynchronizedNavigableMap(NavigableMap<K, V> param1NavigableMap, Object param1Object) {
/* 2860 */       super(param1NavigableMap, param1Object);
/* 2861 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 2865 */       synchronized (this.mutex) { return this.nm.lowerEntry(param1K); }
/*      */     
/* 2867 */     } public K lowerKey(K param1K) { synchronized (this.mutex) { return this.nm.lowerKey(param1K); }
/*      */        }
/* 2869 */     public Map.Entry<K, V> floorEntry(K param1K) { synchronized (this.mutex) { return this.nm.floorEntry(param1K); }
/*      */        }
/* 2871 */     public K floorKey(K param1K) { synchronized (this.mutex) { return this.nm.floorKey(param1K); }
/*      */        }
/* 2873 */     public Map.Entry<K, V> ceilingEntry(K param1K) { synchronized (this.mutex) { return this.nm.ceilingEntry(param1K); }
/*      */        }
/* 2875 */     public K ceilingKey(K param1K) { synchronized (this.mutex) { return this.nm.ceilingKey(param1K); }
/*      */        }
/* 2877 */     public Map.Entry<K, V> higherEntry(K param1K) { synchronized (this.mutex) { return this.nm.higherEntry(param1K); }
/*      */        }
/* 2879 */     public K higherKey(K param1K) { synchronized (this.mutex) { return this.nm.higherKey(param1K); }
/*      */        }
/* 2881 */     public Map.Entry<K, V> firstEntry() { synchronized (this.mutex) { return this.nm.firstEntry(); }
/*      */        }
/* 2883 */     public Map.Entry<K, V> lastEntry() { synchronized (this.mutex) { return this.nm.lastEntry(); }
/*      */        }
/* 2885 */     public Map.Entry<K, V> pollFirstEntry() { synchronized (this.mutex) { return this.nm.pollFirstEntry(); }
/*      */        } public Map.Entry<K, V> pollLastEntry() {
/* 2887 */       synchronized (this.mutex) { return this.nm.pollLastEntry(); }
/*      */     
/*      */     } public NavigableMap<K, V> descendingMap() {
/* 2890 */       synchronized (this.mutex) {
/* 2891 */         return new SynchronizedNavigableMap(this.nm
/* 2892 */             .descendingMap(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<K> keySet() {
/* 2897 */       return navigableKeySet();
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 2901 */       synchronized (this.mutex) {
/* 2902 */         return new Collections.SynchronizedNavigableSet<>(this.nm.navigableKeySet(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 2907 */       synchronized (this.mutex) {
/* 2908 */         return new Collections.SynchronizedNavigableSet<>(this.nm.descendingKeySet(), this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 2914 */       synchronized (this.mutex) {
/* 2915 */         return new SynchronizedNavigableMap(this.nm
/* 2916 */             .subMap(param1K1, true, param1K2, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 2920 */       synchronized (this.mutex) {
/* 2921 */         return new SynchronizedNavigableMap(this.nm.headMap(param1K, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 2925 */       synchronized (this.mutex) {
/* 2926 */         return new SynchronizedNavigableMap(this.nm.tailMap(param1K, true), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 2931 */       synchronized (this.mutex) {
/* 2932 */         return new SynchronizedNavigableMap(this.nm
/* 2933 */             .subMap(param1K1, param1Boolean1, param1K2, param1Boolean2), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 2938 */       synchronized (this.mutex) {
/* 2939 */         return new SynchronizedNavigableMap(this.nm
/* 2940 */             .headMap(param1K, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 2945 */       synchronized (this.mutex) {
/* 2946 */         return new SynchronizedNavigableMap(this.nm
/* 2947 */             .tailMap(param1K, param1Boolean), this.mutex);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> Collection<E> checkedCollection(Collection<E> paramCollection, Class<E> paramClass) {
/* 3017 */     return new CheckedCollection<>(paramCollection, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T[] zeroLengthArray(Class<T> paramClass) {
/* 3022 */     return (T[])Array.newInstance(paramClass, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1578914078182001775L;
/*      */     
/*      */     final Collection<E> c;
/*      */     final Class<E> type;
/*      */     private E[] zeroLengthElementArray;
/*      */     
/*      */     E typeCheck(Object param1Object) {
/* 3036 */       if (param1Object != null && !this.type.isInstance(param1Object))
/* 3037 */         throw new ClassCastException(badElementMsg(param1Object)); 
/* 3038 */       return (E)param1Object;
/*      */     }
/*      */     
/*      */     private String badElementMsg(Object param1Object) {
/* 3042 */       return "Attempt to insert " + param1Object.getClass() + " element into collection with element type " + this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     CheckedCollection(Collection<E> param1Collection, Class<E> param1Class) {
/* 3047 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection, "c");
/* 3048 */       this.type = Objects.<Class<E>>requireNonNull(param1Class, "type");
/*      */     }
/*      */     
/* 3051 */     public int size() { return this.c.size(); }
/* 3052 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 3053 */     public boolean contains(Object param1Object) { return this.c.contains(param1Object); }
/* 3054 */     public Object[] toArray() { return this.c.toArray(); }
/* 3055 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.c.toArray(param1ArrayOfT); }
/* 3056 */     public String toString() { return this.c.toString(); }
/* 3057 */     public boolean remove(Object param1Object) { return this.c.remove(param1Object); } public void clear() {
/* 3058 */       this.c.clear();
/*      */     }
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 3061 */       return this.c.containsAll(param1Collection);
/*      */     }
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 3064 */       return this.c.removeAll(param1Collection);
/*      */     }
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 3067 */       return this.c.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<E> iterator() {
/* 3073 */       final Iterator<E> it = this.c.iterator();
/* 3074 */       return new Iterator<E>() {
/* 3075 */           public boolean hasNext() { return it.hasNext(); }
/* 3076 */           public E next() { return it.next(); }
/* 3077 */           public void remove() { it.remove(); }
/*      */         };
/*      */     } public boolean add(E param1E) {
/* 3080 */       return this.c.add(typeCheck(param1E));
/*      */     }
/*      */ 
/*      */     
/*      */     private E[] zeroLengthElementArray() {
/* 3085 */       return (this.zeroLengthElementArray != null) ? this.zeroLengthElementArray : (this
/* 3086 */         .zeroLengthElementArray = Collections.zeroLengthArray(this.type));
/*      */     }
/*      */ 
/*      */     
/*      */     Collection<E> checkedCopyOf(Collection<? extends E> param1Collection) {
/*      */       Object[] arrayOfObject;
/*      */       try {
/* 3093 */         E[] arrayOfE = zeroLengthElementArray();
/* 3094 */         arrayOfObject = param1Collection.toArray((Object[])arrayOfE);
/*      */         
/* 3096 */         if (arrayOfObject.getClass() != arrayOfE.getClass())
/* 3097 */           arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, (Class)arrayOfE.getClass()); 
/* 3098 */       } catch (ArrayStoreException arrayStoreException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3104 */         arrayOfObject = (Object[])param1Collection.toArray().clone();
/* 3105 */         for (Object object : arrayOfObject) {
/* 3106 */           typeCheck(object);
/*      */         }
/*      */       } 
/* 3109 */       return Arrays.asList((E[])arrayOfObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends E> param1Collection) {
/* 3117 */       return this.c.addAll(checkedCopyOf(param1Collection));
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 3122 */       this.c.forEach(param1Consumer);
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 3125 */       return this.c.removeIf(param1Predicate);
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 3128 */       return this.c.spliterator();
/*      */     } public Stream<E> stream() {
/* 3130 */       return this.c.stream();
/*      */     } public Stream<E> parallelStream() {
/* 3132 */       return this.c.parallelStream();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> Queue<E> checkedQueue(Queue<E> paramQueue, Class<E> paramClass) {
/* 3163 */     return new CheckedQueue<>(paramQueue, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedQueue<E>
/*      */     extends CheckedCollection<E>
/*      */     implements Queue<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1433151992604707767L;
/*      */     
/*      */     final Queue<E> queue;
/*      */ 
/*      */     
/*      */     CheckedQueue(Queue<E> param1Queue, Class<E> param1Class) {
/* 3177 */       super(param1Queue, param1Class);
/* 3178 */       this.queue = param1Queue;
/*      */     }
/*      */     
/* 3181 */     public E element() { return this.queue.element(); }
/* 3182 */     public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); }
/* 3183 */     public int hashCode() { return this.c.hashCode(); }
/* 3184 */     public E peek() { return this.queue.peek(); }
/* 3185 */     public E poll() { return this.queue.poll(); }
/* 3186 */     public E remove() { return this.queue.remove(); } public boolean offer(E param1E) {
/* 3187 */       return this.queue.offer(typeCheck(param1E));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> Set<E> checkedSet(Set<E> paramSet, Class<E> paramClass) {
/* 3218 */     return new CheckedSet<>(paramSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedSet<E>
/*      */     extends CheckedCollection<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 4694047833775013803L;
/*      */     
/*      */     CheckedSet(Set<E> param1Set, Class<E> param1Class) {
/* 3229 */       super(param1Set, param1Class);
/*      */     }
/* 3231 */     public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); } public int hashCode() {
/* 3232 */       return this.c.hashCode();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> paramSortedSet, Class<E> paramClass) {
/* 3265 */     return new CheckedSortedSet<>(paramSortedSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedSortedSet<E>
/*      */     extends CheckedSet<E>
/*      */     implements SortedSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1599911165492914959L;
/*      */     
/*      */     private final SortedSet<E> ss;
/*      */ 
/*      */     
/*      */     CheckedSortedSet(SortedSet<E> param1SortedSet, Class<E> param1Class) {
/* 3279 */       super(param1SortedSet, param1Class);
/* 3280 */       this.ss = param1SortedSet;
/*      */     }
/*      */     
/* 3283 */     public Comparator<? super E> comparator() { return this.ss.comparator(); }
/* 3284 */     public E first() { return this.ss.first(); } public E last() {
/* 3285 */       return this.ss.last();
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 3288 */       return Collections.checkedSortedSet(this.ss.subSet(param1E1, param1E2), this.type);
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 3291 */       return Collections.checkedSortedSet(this.ss.headSet(param1E), this.type);
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 3294 */       return Collections.checkedSortedSet(this.ss.tailSet(param1E), this.type);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> NavigableSet<E> checkedNavigableSet(NavigableSet<E> paramNavigableSet, Class<E> paramClass) {
/* 3328 */     return new CheckedNavigableSet<>(paramNavigableSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedNavigableSet<E>
/*      */     extends CheckedSortedSet<E>
/*      */     implements NavigableSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5429120189805438922L;
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */     
/*      */     CheckedNavigableSet(NavigableSet<E> param1NavigableSet, Class<E> param1Class) {
/* 3342 */       super(param1NavigableSet, param1Class);
/* 3343 */       this.ns = param1NavigableSet;
/*      */     }
/*      */     
/* 3346 */     public E lower(E param1E) { return this.ns.lower(param1E); }
/* 3347 */     public E floor(E param1E) { return this.ns.floor(param1E); }
/* 3348 */     public E ceiling(E param1E) { return this.ns.ceiling(param1E); }
/* 3349 */     public E higher(E param1E) { return this.ns.higher(param1E); }
/* 3350 */     public E pollFirst() { return this.ns.pollFirst(); } public E pollLast() {
/* 3351 */       return this.ns.pollLast();
/*      */     } public NavigableSet<E> descendingSet() {
/* 3353 */       return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type);
/*      */     } public Iterator<E> descendingIterator() {
/* 3355 */       return Collections.<E>checkedNavigableSet(this.ns.descendingSet(), this.type).iterator();
/*      */     }
/*      */     public NavigableSet<E> subSet(E param1E1, E param1E2) {
/* 3358 */       return Collections.checkedNavigableSet(this.ns.subSet(param1E1, true, param1E2, false), this.type);
/*      */     }
/*      */     public NavigableSet<E> headSet(E param1E) {
/* 3361 */       return Collections.checkedNavigableSet(this.ns.headSet(param1E, false), this.type);
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E) {
/* 3364 */       return Collections.checkedNavigableSet(this.ns.tailSet(param1E, true), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 3368 */       return Collections.checkedNavigableSet(this.ns.subSet(param1E1, param1Boolean1, param1E2, param1Boolean2), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 3372 */       return Collections.checkedNavigableSet(this.ns.headSet(param1E, param1Boolean), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 3376 */       return Collections.checkedNavigableSet(this.ns.tailSet(param1E, param1Boolean), this.type);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> List<E> checkedList(List<E> paramList, Class<E> paramClass) {
/* 3408 */     return (paramList instanceof RandomAccess) ? new CheckedRandomAccessList<>(paramList, paramClass) : new CheckedList<>(paramList, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedList<E>
/*      */     extends CheckedCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = 65247728283967356L;
/*      */ 
/*      */     
/*      */     final List<E> list;
/*      */ 
/*      */     
/*      */     CheckedList(List<E> param1List, Class<E> param1Class) {
/* 3424 */       super(param1List, param1Class);
/* 3425 */       this.list = param1List;
/*      */     }
/*      */     
/* 3428 */     public boolean equals(Object param1Object) { return (param1Object == this || this.list.equals(param1Object)); }
/* 3429 */     public int hashCode() { return this.list.hashCode(); }
/* 3430 */     public E get(int param1Int) { return this.list.get(param1Int); }
/* 3431 */     public E remove(int param1Int) { return this.list.remove(param1Int); }
/* 3432 */     public int indexOf(Object param1Object) { return this.list.indexOf(param1Object); } public int lastIndexOf(Object param1Object) {
/* 3433 */       return this.list.lastIndexOf(param1Object);
/*      */     }
/*      */     public E set(int param1Int, E param1E) {
/* 3436 */       return this.list.set(param1Int, typeCheck(param1E));
/*      */     }
/*      */     
/*      */     public void add(int param1Int, E param1E) {
/* 3440 */       this.list.add(param1Int, typeCheck(param1E));
/*      */     }
/*      */     
/*      */     public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 3444 */       return this.list.addAll(param1Int, checkedCopyOf(param1Collection));
/*      */     } public ListIterator<E> listIterator() {
/* 3446 */       return listIterator(0);
/*      */     }
/*      */     public ListIterator<E> listIterator(int param1Int) {
/* 3449 */       final ListIterator<E> i = this.list.listIterator(param1Int);
/*      */       
/* 3451 */       return new ListIterator<E>() {
/* 3452 */           public boolean hasNext() { return i.hasNext(); }
/* 3453 */           public E next() { return i.next(); }
/* 3454 */           public boolean hasPrevious() { return i.hasPrevious(); }
/* 3455 */           public E previous() { return i.previous(); }
/* 3456 */           public int nextIndex() { return i.nextIndex(); }
/* 3457 */           public int previousIndex() { return i.previousIndex(); } public void remove() {
/* 3458 */             i.remove();
/*      */           }
/*      */           public void set(E param2E) {
/* 3461 */             i.set(Collections.CheckedList.this.typeCheck(param2E));
/*      */           }
/*      */           
/*      */           public void add(E param2E) {
/* 3465 */             i.add(Collections.CheckedList.this.typeCheck(param2E));
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 3470 */             i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 3476 */       return new CheckedList(this.list.subList(param1Int1, param1Int2), this.type);
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
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 3489 */       Objects.requireNonNull(param1UnaryOperator);
/* 3490 */       this.list.replaceAll(param1Object -> typeCheck(param1UnaryOperator.apply(param1Object)));
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 3495 */       this.list.sort(param1Comparator);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedRandomAccessList<E>
/*      */     extends CheckedList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = 1638200125423088369L;
/*      */ 
/*      */     
/*      */     CheckedRandomAccessList(List<E> param1List, Class<E> param1Class) {
/* 3508 */       super(param1List, param1Class);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 3512 */       return new CheckedRandomAccessList(this.list
/* 3513 */           .subList(param1Int1, param1Int2), this.type);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> Map<K, V> checkedMap(Map<K, V> paramMap, Class<K> paramClass, Class<V> paramClass1) {
/* 3556 */     return new CheckedMap<>(paramMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CheckedMap<K, V>
/*      */     implements Map<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5742860141034234728L;
/*      */     
/*      */     private final Map<K, V> m;
/*      */     
/*      */     final Class<K> keyType;
/*      */     
/*      */     final Class<V> valueType;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     
/*      */     private void typeCheck(Object param1Object1, Object param1Object2) {
/* 3573 */       if (param1Object1 != null && !this.keyType.isInstance(param1Object1)) {
/* 3574 */         throw new ClassCastException(badKeyMsg(param1Object1));
/*      */       }
/* 3576 */       if (param1Object2 != null && !this.valueType.isInstance(param1Object2)) {
/* 3577 */         throw new ClassCastException(badValueMsg(param1Object2));
/*      */       }
/*      */     }
/*      */     
/*      */     private BiFunction<? super K, ? super V, ? extends V> typeCheck(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3582 */       Objects.requireNonNull(param1BiFunction);
/* 3583 */       return (param1Object1, param1Object2) -> {
/*      */           Object object = param1BiFunction.apply(param1Object1, param1Object2);
/*      */           typeCheck(param1Object1, object);
/*      */           return object;
/*      */         };
/*      */     }
/*      */     
/*      */     private String badKeyMsg(Object param1Object) {
/* 3591 */       return "Attempt to insert " + param1Object.getClass() + " key into map with key type " + this.keyType;
/*      */     }
/*      */ 
/*      */     
/*      */     private String badValueMsg(Object param1Object) {
/* 3596 */       return "Attempt to insert " + param1Object.getClass() + " value into map with value type " + this.valueType;
/*      */     }
/*      */ 
/*      */     
/*      */     CheckedMap(Map<K, V> param1Map, Class<K> param1Class, Class<V> param1Class1) {
/* 3601 */       this.m = Objects.<Map<K, V>>requireNonNull(param1Map);
/* 3602 */       this.keyType = Objects.<Class<K>>requireNonNull(param1Class);
/* 3603 */       this.valueType = Objects.<Class<V>>requireNonNull(param1Class1);
/*      */     }
/*      */     
/* 3606 */     public int size() { return this.m.size(); }
/* 3607 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 3608 */     public boolean containsKey(Object param1Object) { return this.m.containsKey(param1Object); }
/* 3609 */     public boolean containsValue(Object param1Object) { return this.m.containsValue(param1Object); }
/* 3610 */     public V get(Object param1Object) { return this.m.get(param1Object); }
/* 3611 */     public V remove(Object param1Object) { return this.m.remove(param1Object); }
/* 3612 */     public void clear() { this.m.clear(); }
/* 3613 */     public Set<K> keySet() { return this.m.keySet(); }
/* 3614 */     public Collection<V> values() { return this.m.values(); }
/* 3615 */     public boolean equals(Object param1Object) { return (param1Object == this || this.m.equals(param1Object)); }
/* 3616 */     public int hashCode() { return this.m.hashCode(); } public String toString() {
/* 3617 */       return this.m.toString();
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 3620 */       typeCheck(param1K, param1V);
/* 3621 */       return this.m.put(param1K, param1V);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 3631 */       Object[] arrayOfObject = param1Map.entrySet().toArray();
/* 3632 */       ArrayList arrayList = new ArrayList(arrayOfObject.length);
/* 3633 */       for (Object object1 : arrayOfObject) {
/* 3634 */         Map.Entry entry = (Map.Entry)object1;
/* 3635 */         Object object2 = entry.getKey();
/* 3636 */         Object object3 = entry.getValue();
/* 3637 */         typeCheck(object2, object3);
/* 3638 */         arrayList.add(new AbstractMap.SimpleImmutableEntry<>(object2, object3));
/*      */       } 
/*      */       
/* 3641 */       for (Map.Entry entry : arrayList) {
/* 3642 */         this.m.put((K)entry.getKey(), (V)entry.getValue());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 3648 */       if (this.entrySet == null)
/* 3649 */         this.entrySet = new CheckedEntrySet<>(this.m.entrySet(), this.valueType); 
/* 3650 */       return this.entrySet;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 3656 */       this.m.forEach(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3661 */       this.m.replaceAll(typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 3666 */       typeCheck(param1K, param1V);
/* 3667 */       return this.m.putIfAbsent(param1K, param1V);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 3672 */       return this.m.remove(param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 3677 */       typeCheck(param1K, param1V2);
/* 3678 */       return this.m.replace(param1K, param1V1, param1V2);
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 3683 */       typeCheck(param1K, param1V);
/* 3684 */       return this.m.replace(param1K, param1V);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 3690 */       Objects.requireNonNull(param1Function);
/* 3691 */       return this.m.computeIfAbsent(param1K, param1Object -> {
/*      */             Object object = param1Function.apply(param1Object);
/*      */             typeCheck(param1Object, object);
/*      */             return object;
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3701 */       return this.m.computeIfPresent(param1K, typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3707 */       return this.m.compute(param1K, typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 3713 */       Objects.requireNonNull(param1BiFunction);
/* 3714 */       return this.m.merge(param1K, param1V, (param1Object1, param1Object2) -> {
/*      */             Object object = param1BiFunction.apply(param1Object1, param1Object2);
/*      */             typeCheck(null, object);
/*      */             return object;
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class CheckedEntrySet<K, V>
/*      */       implements Set<Map.Entry<K, V>>
/*      */     {
/*      */       private final Set<Map.Entry<K, V>> s;
/*      */ 
/*      */       
/*      */       private final Class<V> valueType;
/*      */ 
/*      */ 
/*      */       
/*      */       CheckedEntrySet(Set<Map.Entry<K, V>> param2Set, Class<V> param2Class) {
/* 3734 */         this.s = param2Set;
/* 3735 */         this.valueType = param2Class;
/*      */       }
/*      */       
/* 3738 */       public int size() { return this.s.size(); }
/* 3739 */       public boolean isEmpty() { return this.s.isEmpty(); }
/* 3740 */       public String toString() { return this.s.toString(); }
/* 3741 */       public int hashCode() { return this.s.hashCode(); } public void clear() {
/* 3742 */         this.s.clear();
/*      */       }
/*      */       public boolean add(Map.Entry<K, V> param2Entry) {
/* 3745 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       public boolean addAll(Collection<? extends Map.Entry<K, V>> param2Collection) {
/* 3748 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 3752 */         final Iterator<Map.Entry<K, V>> i = this.s.iterator();
/* 3753 */         final Class<V> valueType = this.valueType;
/*      */         
/* 3755 */         return new Iterator<Map.Entry<K, V>>() {
/* 3756 */             public boolean hasNext() { return i.hasNext(); } public void remove() {
/* 3757 */               i.remove();
/*      */             }
/*      */             public Map.Entry<K, V> next() {
/* 3760 */               return Collections.CheckedMap.CheckedEntrySet.checkedEntry(i.next(), valueType);
/*      */             }
/*      */           };
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 3767 */         Object[] arrayOfObject1 = this.s.toArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3773 */         Object[] arrayOfObject2 = CheckedEntry.class.isInstance(arrayOfObject1
/* 3774 */             .getClass().getComponentType()) ? arrayOfObject1 : new Object[arrayOfObject1.length];
/*      */ 
/*      */         
/* 3777 */         for (byte b = 0; b < arrayOfObject1.length; b++) {
/* 3778 */           arrayOfObject2[b] = checkedEntry((Map.Entry<?, ?>)arrayOfObject1[b], this.valueType);
/*      */         }
/* 3780 */         return arrayOfObject2;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public <T> T[] toArray(T[] param2ArrayOfT) {
/* 3788 */         Object[] arrayOfObject = this.s.toArray((param2ArrayOfT.length == 0) ? (Object[])param2ArrayOfT : Arrays.<Object>copyOf((Object[])param2ArrayOfT, 0));
/*      */         
/* 3790 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 3791 */           arrayOfObject[b] = checkedEntry((Map.Entry<?, ?>)arrayOfObject[b], this.valueType);
/*      */         }
/* 3793 */         if (arrayOfObject.length > param2ArrayOfT.length) {
/* 3794 */           return (T[])arrayOfObject;
/*      */         }
/* 3796 */         System.arraycopy(arrayOfObject, 0, param2ArrayOfT, 0, arrayOfObject.length);
/* 3797 */         if (param2ArrayOfT.length > arrayOfObject.length)
/* 3798 */           param2ArrayOfT[arrayOfObject.length] = null; 
/* 3799 */         return param2ArrayOfT;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object param2Object) {
/* 3809 */         if (!(param2Object instanceof Map.Entry))
/* 3810 */           return false; 
/* 3811 */         Map.Entry<?, ?> entry = (Map.Entry)param2Object;
/* 3812 */         return this.s.contains((entry instanceof CheckedEntry) ? entry : 
/* 3813 */             checkedEntry(entry, this.valueType));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean containsAll(Collection<?> param2Collection) {
/* 3822 */         for (Object object : param2Collection) {
/* 3823 */           if (!contains(object))
/* 3824 */             return false; 
/* 3825 */         }  return true;
/*      */       }
/*      */       
/*      */       public boolean remove(Object param2Object) {
/* 3829 */         if (!(param2Object instanceof Map.Entry))
/* 3830 */           return false; 
/* 3831 */         return this.s.remove(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>)param2Object));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean removeAll(Collection<?> param2Collection) {
/* 3836 */         return batchRemove(param2Collection, false);
/*      */       }
/*      */       public boolean retainAll(Collection<?> param2Collection) {
/* 3839 */         return batchRemove(param2Collection, true);
/*      */       }
/*      */       private boolean batchRemove(Collection<?> param2Collection, boolean param2Boolean) {
/* 3842 */         Objects.requireNonNull(param2Collection);
/* 3843 */         boolean bool = false;
/* 3844 */         Iterator<Map.Entry<K, V>> iterator = iterator();
/* 3845 */         while (iterator.hasNext()) {
/* 3846 */           if (param2Collection.contains(iterator.next()) != param2Boolean) {
/* 3847 */             iterator.remove();
/* 3848 */             bool = true;
/*      */           } 
/*      */         } 
/* 3851 */         return bool;
/*      */       }
/*      */       
/*      */       public boolean equals(Object param2Object) {
/* 3855 */         if (param2Object == this)
/* 3856 */           return true; 
/* 3857 */         if (!(param2Object instanceof Set))
/* 3858 */           return false; 
/* 3859 */         Set<?> set = (Set)param2Object;
/* 3860 */         return (set.size() == this.s.size() && 
/* 3861 */           containsAll(set));
/*      */       }
/*      */ 
/*      */       
/*      */       static <K, V, T> CheckedEntry<K, V, T> checkedEntry(Map.Entry<K, V> param2Entry, Class<T> param2Class) {
/* 3866 */         return new CheckedEntry<>(param2Entry, param2Class);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private static class CheckedEntry<K, V, T>
/*      */         implements Map.Entry<K, V>
/*      */       {
/*      */         private final Map.Entry<K, V> e;
/*      */ 
/*      */         
/*      */         private final Class<T> valueType;
/*      */ 
/*      */         
/*      */         CheckedEntry(Map.Entry<K, V> param3Entry, Class<T> param3Class) {
/* 3881 */           this.e = Objects.<Map.Entry<K, V>>requireNonNull(param3Entry);
/* 3882 */           this.valueType = Objects.<Class<T>>requireNonNull(param3Class);
/*      */         }
/*      */         
/* 3885 */         public K getKey() { return this.e.getKey(); }
/* 3886 */         public V getValue() { return this.e.getValue(); }
/* 3887 */         public int hashCode() { return this.e.hashCode(); } public String toString() {
/* 3888 */           return this.e.toString();
/*      */         }
/*      */         public V setValue(V param3V) {
/* 3891 */           if (param3V != null && !this.valueType.isInstance(param3V))
/* 3892 */             throw new ClassCastException(badValueMsg(param3V)); 
/* 3893 */           return this.e.setValue(param3V);
/*      */         }
/*      */         
/*      */         private String badValueMsg(Object param3Object) {
/* 3897 */           return "Attempt to insert " + param3Object.getClass() + " value into map with value type " + this.valueType;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean equals(Object param3Object) {
/* 3902 */           if (param3Object == this)
/* 3903 */             return true; 
/* 3904 */           if (!(param3Object instanceof Map.Entry))
/* 3905 */             return false; 
/* 3906 */           return this.e.equals(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>)param3Object));
/*      */         }
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> SortedMap<K, V> checkedSortedMap(SortedMap<K, V> paramSortedMap, Class<K> paramClass, Class<V> paramClass1) {
/* 3952 */     return new CheckedSortedMap<>(paramSortedMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedSortedMap<K, V>
/*      */     extends CheckedMap<K, V>
/*      */     implements SortedMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1599671320688067438L;
/*      */     
/*      */     private final SortedMap<K, V> sm;
/*      */ 
/*      */     
/*      */     CheckedSortedMap(SortedMap<K, V> param1SortedMap, Class<K> param1Class, Class<V> param1Class1) {
/* 3967 */       super(param1SortedMap, param1Class, param1Class1);
/* 3968 */       this.sm = param1SortedMap;
/*      */     }
/*      */     
/* 3971 */     public Comparator<? super K> comparator() { return this.sm.comparator(); }
/* 3972 */     public K firstKey() { return this.sm.firstKey(); } public K lastKey() {
/* 3973 */       return this.sm.lastKey();
/*      */     }
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 3976 */       return Collections.checkedSortedMap(this.sm.subMap(param1K1, param1K2), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 3980 */       return Collections.checkedSortedMap(this.sm.headMap(param1K), this.keyType, this.valueType);
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 3983 */       return Collections.checkedSortedMap(this.sm.tailMap(param1K), this.keyType, this.valueType);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K, V> NavigableMap<K, V> checkedNavigableMap(NavigableMap<K, V> paramNavigableMap, Class<K> paramClass, Class<V> paramClass1) {
/* 4026 */     return new CheckedNavigableMap<>(paramNavigableMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedNavigableMap<K, V>
/*      */     extends CheckedSortedMap<K, V>
/*      */     implements NavigableMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4852462692372534096L;
/*      */     
/*      */     private final NavigableMap<K, V> nm;
/*      */ 
/*      */     
/*      */     CheckedNavigableMap(NavigableMap<K, V> param1NavigableMap, Class<K> param1Class, Class<V> param1Class1) {
/* 4041 */       super(param1NavigableMap, param1Class, param1Class1);
/* 4042 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     
/* 4045 */     public Comparator<? super K> comparator() { return this.nm.comparator(); }
/* 4046 */     public K firstKey() { return this.nm.firstKey(); } public K lastKey() {
/* 4047 */       return this.nm.lastKey();
/*      */     }
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 4050 */       Map.Entry<K, V> entry = this.nm.lowerEntry(param1K);
/* 4051 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lowerKey(K param1K) {
/* 4056 */       return this.nm.lowerKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> floorEntry(K param1K) {
/* 4059 */       Map.Entry<K, V> entry = this.nm.floorEntry(param1K);
/* 4060 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K floorKey(K param1K) {
/* 4065 */       return this.nm.floorKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> ceilingEntry(K param1K) {
/* 4068 */       Map.Entry<K, V> entry = this.nm.ceilingEntry(param1K);
/* 4069 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K ceilingKey(K param1K) {
/* 4074 */       return this.nm.ceilingKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> higherEntry(K param1K) {
/* 4077 */       Map.Entry<K, V> entry = this.nm.higherEntry(param1K);
/* 4078 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K higherKey(K param1K) {
/* 4083 */       return this.nm.higherKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> firstEntry() {
/* 4086 */       Map.Entry<K, V> entry = this.nm.firstEntry();
/* 4087 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 4093 */       Map.Entry<K, V> entry = this.nm.lastEntry();
/* 4094 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 4100 */       Map.Entry<K, V> entry = this.nm.pollFirstEntry();
/* 4101 */       return (null == entry) ? null : new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollLastEntry() {
/* 4107 */       Map.Entry<K, V> entry = this.nm.pollLastEntry();
/* 4108 */       return (null == entry) ? null : new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 4114 */       return Collections.checkedNavigableMap(this.nm.descendingMap(), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> keySet() {
/* 4118 */       return navigableKeySet();
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 4122 */       return Collections.checkedNavigableSet(this.nm.navigableKeySet(), this.keyType);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 4126 */       return Collections.checkedNavigableSet(this.nm.descendingKeySet(), this.keyType);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, K param1K2) {
/* 4131 */       return Collections.checkedNavigableMap(this.nm.subMap(param1K1, true, param1K2, false), this.keyType, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K) {
/* 4137 */       return Collections.checkedNavigableMap(this.nm.headMap(param1K, false), this.keyType, this.valueType);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K) {
/* 4142 */       return Collections.checkedNavigableMap(this.nm.tailMap(param1K, true), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 4146 */       return Collections.checkedNavigableMap(this.nm.subMap(param1K1, param1Boolean1, param1K2, param1Boolean2), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 4150 */       return Collections.checkedNavigableMap(this.nm.headMap(param1K, param1Boolean), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 4154 */       return Collections.checkedNavigableMap(this.nm.tailMap(param1K, param1Boolean), this.keyType, this.valueType);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Iterator<T> emptyIterator() {
/* 4181 */     return EmptyIterator.EMPTY_ITERATOR;
/*      */   }
/*      */   private static class EmptyIterator<E> implements Iterator<E> { private EmptyIterator() {}
/*      */     
/* 4185 */     static final EmptyIterator<Object> EMPTY_ITERATOR = new EmptyIterator();
/*      */     
/*      */     public boolean hasNext() {
/* 4188 */       return false;
/* 4189 */     } public E next() { throw new NoSuchElementException(); } public void remove() {
/* 4190 */       throw new IllegalStateException();
/*      */     }
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 4193 */       Objects.requireNonNull(param1Consumer);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> ListIterator<T> emptyListIterator() {
/* 4225 */     return EmptyListIterator.EMPTY_ITERATOR;
/*      */   }
/*      */   
/*      */   private static class EmptyListIterator<E>
/*      */     extends EmptyIterator<E>
/*      */     implements ListIterator<E>
/*      */   {
/* 4232 */     static final EmptyListIterator<Object> EMPTY_ITERATOR = new EmptyListIterator();
/*      */     
/*      */     public boolean hasPrevious() {
/* 4235 */       return false;
/* 4236 */     } public E previous() { throw new NoSuchElementException(); }
/* 4237 */     public int nextIndex() { return 0; }
/* 4238 */     public int previousIndex() { return -1; }
/* 4239 */     public void set(E param1E) { throw new IllegalStateException(); } public void add(E param1E) {
/* 4240 */       throw new UnsupportedOperationException();
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
/*      */   public static <T> Enumeration<T> emptyEnumeration() {
/* 4262 */     return EmptyEnumeration.EMPTY_ENUMERATION;
/*      */   }
/*      */   
/*      */   private static class EmptyEnumeration<E> implements Enumeration<E> {
/* 4266 */     static final EmptyEnumeration<Object> EMPTY_ENUMERATION = new EmptyEnumeration();
/*      */     
/*      */     public boolean hasMoreElements() {
/* 4269 */       return false; } public E nextElement() {
/* 4270 */       throw new NoSuchElementException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4279 */   public static final Set EMPTY_SET = new EmptySet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final <T> Set<T> emptySet() {
/* 4302 */     return EMPTY_SET;
/*      */   }
/*      */   
/*      */   private static class EmptySet<E>
/*      */     extends AbstractSet<E>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1582296315990362920L;
/*      */     
/*      */     private EmptySet() {}
/*      */     
/*      */     public Iterator<E> iterator() {
/* 4314 */       return Collections.emptyIterator();
/*      */     }
/* 4316 */     public int size() { return 0; } public boolean isEmpty() {
/* 4317 */       return true;
/*      */     }
/* 4319 */     public boolean contains(Object param1Object) { return false; } public boolean containsAll(Collection<?> param1Collection) {
/* 4320 */       return param1Collection.isEmpty();
/*      */     } public Object[] toArray() {
/* 4322 */       return new Object[0];
/*      */     }
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 4325 */       if (param1ArrayOfT.length > 0)
/* 4326 */         param1ArrayOfT[0] = null; 
/* 4327 */       return param1ArrayOfT;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4333 */       Objects.requireNonNull(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4337 */       Objects.requireNonNull(param1Predicate);
/* 4338 */       return false;
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 4341 */       return Spliterators.emptySpliterator();
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4345 */       return Collections.EMPTY_SET;
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
/*      */   public static <E> SortedSet<E> emptySortedSet() {
/* 4367 */     return (SortedSet)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
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
/*      */   public static <E> NavigableSet<E> emptyNavigableSet() {
/* 4388 */     return (NavigableSet)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4397 */   public static final List EMPTY_LIST = new EmptyList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final <T> List<T> emptyList() {
/* 4421 */     return EMPTY_LIST;
/*      */   }
/*      */   
/*      */   private static class EmptyList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8842843931221139166L;
/*      */     
/*      */     private EmptyList() {}
/*      */     
/*      */     public Iterator<E> iterator() {
/* 4433 */       return Collections.emptyIterator();
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 4436 */       return Collections.emptyListIterator();
/*      */     }
/*      */     
/* 4439 */     public int size() { return 0; } public boolean isEmpty() {
/* 4440 */       return true;
/*      */     }
/* 4442 */     public boolean contains(Object param1Object) { return false; } public boolean containsAll(Collection<?> param1Collection) {
/* 4443 */       return param1Collection.isEmpty();
/*      */     } public Object[] toArray() {
/* 4445 */       return new Object[0];
/*      */     }
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 4448 */       if (param1ArrayOfT.length > 0)
/* 4449 */         param1ArrayOfT[0] = null; 
/* 4450 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 4454 */       throw new IndexOutOfBoundsException("Index: " + param1Int);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 4458 */       return (param1Object instanceof List && ((List)param1Object).isEmpty());
/*      */     }
/*      */     public int hashCode() {
/* 4461 */       return 1;
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4465 */       Objects.requireNonNull(param1Predicate);
/* 4466 */       return false;
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 4470 */       Objects.requireNonNull(param1UnaryOperator);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {}
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4479 */       Objects.requireNonNull(param1Consumer);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4483 */       return Spliterators.emptySpliterator();
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4487 */       return Collections.EMPTY_LIST;
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
/* 4498 */   public static final Map EMPTY_MAP = new EmptyMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final <K, V> Map<K, V> emptyMap() {
/* 4520 */     return EMPTY_MAP;
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
/*      */   public static final <K, V> SortedMap<K, V> emptySortedMap() {
/* 4541 */     return (SortedMap)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
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
/*      */   public static final <K, V> NavigableMap<K, V> emptyNavigableMap() {
/* 4562 */     return (NavigableMap)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */   }
/*      */   
/*      */   private static class EmptyMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 6428348081105594320L;
/*      */     
/*      */     private EmptyMap() {}
/*      */     
/*      */     public int size() {
/* 4574 */       return 0;
/* 4575 */     } public boolean isEmpty() { return true; }
/* 4576 */     public boolean containsKey(Object param1Object) { return false; }
/* 4577 */     public boolean containsValue(Object param1Object) { return false; }
/* 4578 */     public V get(Object param1Object) { return null; }
/* 4579 */     public Set<K> keySet() { return Collections.emptySet(); }
/* 4580 */     public Collection<V> values() { return Collections.emptySet(); } public Set<Map.Entry<K, V>> entrySet() {
/* 4581 */       return Collections.emptySet();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 4584 */       return (param1Object instanceof Map && ((Map)param1Object).isEmpty());
/*      */     }
/*      */     public int hashCode() {
/* 4587 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 4593 */       return param1V;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 4598 */       Objects.requireNonNull(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4603 */       Objects.requireNonNull(param1BiFunction);
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 4608 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 4613 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 4618 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 4623 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 4629 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4635 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4641 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 4647 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 4652 */       return Collections.EMPTY_MAP;
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
/*      */   public static <T> Set<T> singleton(T paramT) {
/* 4667 */     return new SingletonSet<>(paramT);
/*      */   }
/*      */   
/*      */   static <E> Iterator<E> singletonIterator(final E e) {
/* 4671 */     return new Iterator<E>() { private boolean hasNext = true;
/*      */         
/*      */         public boolean hasNext() {
/* 4674 */           return this.hasNext;
/*      */         }
/*      */         public E next() {
/* 4677 */           if (this.hasNext) {
/* 4678 */             this.hasNext = false;
/* 4679 */             return (E)e;
/*      */           } 
/* 4681 */           throw new NoSuchElementException();
/*      */         }
/*      */         public void remove() {
/* 4684 */           throw new UnsupportedOperationException();
/*      */         }
/*      */         
/*      */         public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 4688 */           Objects.requireNonNull(param1Consumer);
/* 4689 */           if (this.hasNext) {
/* 4690 */             param1Consumer.accept((E)e);
/* 4691 */             this.hasNext = false;
/*      */           } 
/*      */         } }
/*      */       ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Spliterator<T> singletonSpliterator(final T element) {
/* 4704 */     return new Spliterator<T>() {
/* 4705 */         long est = 1L;
/*      */ 
/*      */         
/*      */         public Spliterator<T> trySplit() {
/* 4709 */           return null;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 4714 */           Objects.requireNonNull(param1Consumer);
/* 4715 */           if (this.est > 0L) {
/* 4716 */             this.est--;
/* 4717 */             param1Consumer.accept((T)element);
/* 4718 */             return true;
/*      */           } 
/* 4720 */           return false;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 4725 */           tryAdvance(param1Consumer);
/*      */         }
/*      */ 
/*      */         
/*      */         public long estimateSize() {
/* 4730 */           return this.est;
/*      */         }
/*      */ 
/*      */         
/*      */         public int characteristics() {
/* 4735 */           boolean bool = (element != null) ? true : false;
/*      */           
/* 4737 */           return bool | 0x40 | 0x4000 | 0x400 | 0x1 | 0x10;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SingletonSet<E>
/*      */     extends AbstractSet<E>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3193687207550431679L;
/*      */     
/*      */     private final E element;
/*      */ 
/*      */     
/*      */     SingletonSet(E param1E) {
/* 4754 */       this.element = param1E;
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 4757 */       return Collections.singletonIterator(this.element);
/*      */     }
/*      */     public int size() {
/* 4760 */       return 1;
/*      */     } public boolean contains(Object param1Object) {
/* 4762 */       return Collections.eq(param1Object, this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4767 */       param1Consumer.accept(this.element);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4771 */       return Collections.singletonSpliterator(this.element);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4775 */       throw new UnsupportedOperationException();
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
/*      */   public static <T> List<T> singletonList(T paramT) {
/* 4789 */     return new SingletonList<>(paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SingletonList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3093736618740652951L;
/*      */     
/*      */     private final E element;
/*      */ 
/*      */     
/*      */     SingletonList(E param1E) {
/* 4803 */       this.element = param1E;
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 4806 */       return Collections.singletonIterator(this.element);
/*      */     }
/*      */     public int size() {
/* 4809 */       return 1;
/*      */     } public boolean contains(Object param1Object) {
/* 4811 */       return Collections.eq(param1Object, this.element);
/*      */     }
/*      */     public E get(int param1Int) {
/* 4814 */       if (param1Int != 0)
/* 4815 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: 1"); 
/* 4816 */       return this.element;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4822 */       param1Consumer.accept(this.element);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4826 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 4830 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {}
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4837 */       return Collections.singletonSpliterator(this.element);
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
/*      */   public static <K, V> Map<K, V> singletonMap(K paramK, V paramV) {
/* 4854 */     return new SingletonMap<>(paramK, paramV);
/*      */   }
/*      */   
/*      */   private static class SingletonMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6979724477215052911L;
/*      */     private final K k;
/*      */     private final V v;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     SingletonMap(K param1K, V param1V) {
/* 4869 */       this.k = param1K;
/* 4870 */       this.v = param1V;
/*      */     }
/*      */     
/* 4873 */     public int size() { return 1; }
/* 4874 */     public boolean isEmpty() { return false; }
/* 4875 */     public boolean containsKey(Object param1Object) { return Collections.eq(param1Object, this.k); }
/* 4876 */     public boolean containsValue(Object param1Object) { return Collections.eq(param1Object, this.v); } public V get(Object param1Object) {
/* 4877 */       return Collections.eq(param1Object, this.k) ? this.v : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 4884 */       if (this.keySet == null)
/* 4885 */         this.keySet = Collections.singleton(this.k); 
/* 4886 */       return this.keySet;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 4890 */       if (this.entrySet == null) {
/* 4891 */         this.entrySet = Collections.singleton(new AbstractMap.SimpleImmutableEntry<>(this.k, this.v));
/*      */       }
/* 4893 */       return this.entrySet;
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 4897 */       if (this.values == null)
/* 4898 */         this.values = Collections.singleton(this.v); 
/* 4899 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 4905 */       return Collections.eq(param1Object, this.k) ? this.v : param1V;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 4910 */       param1BiConsumer.accept(this.k, this.v);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4915 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 4920 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 4925 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 4930 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 4935 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 4941 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4947 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4953 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 4959 */       throw new UnsupportedOperationException();
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
/*      */   
/*      */   public static <T> List<T> nCopies(int paramInt, T paramT) {
/* 4983 */     if (paramInt < 0)
/* 4984 */       throw new IllegalArgumentException("List length = " + paramInt); 
/* 4985 */     return new CopiesList<>(paramInt, paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CopiesList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2739099268398711800L;
/*      */     
/*      */     final int n;
/*      */     
/*      */     final E element;
/*      */ 
/*      */     
/*      */     CopiesList(int param1Int, E param1E) {
/* 5001 */       assert param1Int >= 0;
/* 5002 */       this.n = param1Int;
/* 5003 */       this.element = param1E;
/*      */     }
/*      */     
/*      */     public int size() {
/* 5007 */       return this.n;
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 5011 */       return (this.n != 0 && Collections.eq(param1Object, this.element));
/*      */     }
/*      */     
/*      */     public int indexOf(Object param1Object) {
/* 5015 */       return contains(param1Object) ? 0 : -1;
/*      */     }
/*      */     
/*      */     public int lastIndexOf(Object param1Object) {
/* 5019 */       return contains(param1Object) ? (this.n - 1) : -1;
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 5023 */       if (param1Int < 0 || param1Int >= this.n) {
/* 5024 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: " + this.n);
/*      */       }
/* 5026 */       return this.element;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 5030 */       Object[] arrayOfObject = new Object[this.n];
/* 5031 */       if (this.element != null)
/* 5032 */         Arrays.fill(arrayOfObject, 0, this.n, this.element); 
/* 5033 */       return arrayOfObject;
/*      */     }
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 5038 */       int i = this.n;
/* 5039 */       if (param1ArrayOfT.length < i) {
/*      */         
/* 5041 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), i);
/* 5042 */         if (this.element != null)
/* 5043 */           Arrays.fill((Object[])param1ArrayOfT, 0, i, this.element); 
/*      */       } else {
/* 5045 */         Arrays.fill((Object[])param1ArrayOfT, 0, i, this.element);
/* 5046 */         if (param1ArrayOfT.length > i)
/* 5047 */           param1ArrayOfT[i] = null; 
/*      */       } 
/* 5049 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 5053 */       if (param1Int1 < 0)
/* 5054 */         throw new IndexOutOfBoundsException("fromIndex = " + param1Int1); 
/* 5055 */       if (param1Int2 > this.n)
/* 5056 */         throw new IndexOutOfBoundsException("toIndex = " + param1Int2); 
/* 5057 */       if (param1Int1 > param1Int2) {
/* 5058 */         throw new IllegalArgumentException("fromIndex(" + param1Int1 + ") > toIndex(" + param1Int2 + ")");
/*      */       }
/* 5060 */       return new CopiesList(param1Int2 - param1Int1, this.element);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Stream<E> stream() {
/* 5066 */       return IntStream.range(0, this.n).mapToObj(param1Int -> this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 5071 */       return IntStream.range(0, this.n).parallel().mapToObj(param1Int -> this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 5076 */       return stream().spliterator();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Comparator<T> reverseOrder() {
/* 5102 */     return ReverseComparator.REVERSE_ORDER;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ReverseComparator
/*      */     implements Comparator<Comparable<Object>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7207038068494060240L;
/*      */ 
/*      */     
/* 5113 */     static final ReverseComparator REVERSE_ORDER = new ReverseComparator();
/*      */ 
/*      */     
/*      */     public int compare(Comparable<Object> param1Comparable1, Comparable<Object> param1Comparable2) {
/* 5117 */       return param1Comparable2.compareTo(param1Comparable1);
/*      */     }
/*      */     private Object readResolve() {
/* 5120 */       return Collections.reverseOrder();
/*      */     }
/*      */     
/*      */     public Comparator<Comparable<Object>> reversed() {
/* 5124 */       return Comparator.naturalOrder();
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
/*      */   public static <T> Comparator<T> reverseOrder(Comparator<T> paramComparator) {
/* 5146 */     if (paramComparator == null) {
/* 5147 */       return reverseOrder();
/*      */     }
/* 5149 */     if (paramComparator instanceof ReverseComparator2) {
/* 5150 */       return ((ReverseComparator2)paramComparator).cmp;
/*      */     }
/* 5152 */     return new ReverseComparator2<>(paramComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ReverseComparator2<T>
/*      */     implements Comparator<T>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 4374092139857L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Comparator<T> cmp;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReverseComparator2(Comparator<T> param1Comparator) {
/* 5173 */       assert param1Comparator != null;
/* 5174 */       this.cmp = param1Comparator;
/*      */     }
/*      */     
/*      */     public int compare(T param1T1, T param1T2) {
/* 5178 */       return this.cmp.compare(param1T2, param1T1);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 5182 */       return (param1Object == this || (param1Object instanceof ReverseComparator2 && this.cmp
/*      */         
/* 5184 */         .equals(((ReverseComparator2)param1Object).cmp)));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 5188 */       return this.cmp.hashCode() ^ Integer.MIN_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<T> reversed() {
/* 5193 */       return this.cmp;
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
/*      */   public static <T> Enumeration<T> enumeration(final Collection<T> c) {
/* 5208 */     return new Enumeration<T>() {
/* 5209 */         private final Iterator<T> i = c.iterator();
/*      */         
/*      */         public boolean hasMoreElements() {
/* 5212 */           return this.i.hasNext();
/*      */         }
/*      */         
/*      */         public T nextElement() {
/* 5216 */           return this.i.next();
/*      */         }
/*      */       };
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
/*      */   public static <T> ArrayList<T> list(Enumeration<T> paramEnumeration) {
/* 5238 */     ArrayList<T> arrayList = new ArrayList();
/* 5239 */     while (paramEnumeration.hasMoreElements())
/* 5240 */       arrayList.add(paramEnumeration.nextElement()); 
/* 5241 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean eq(Object paramObject1, Object paramObject2) {
/* 5250 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
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
/*      */   public static int frequency(Collection<?> paramCollection, Object paramObject) {
/* 5267 */     byte b = 0;
/* 5268 */     if (paramObject == null)
/* 5269 */     { for (Object object : paramCollection) {
/* 5270 */         if (object == null)
/* 5271 */           b++; 
/*      */       }  }
/* 5273 */     else { for (Object object : paramCollection) {
/* 5274 */         if (paramObject.equals(object))
/* 5275 */           b++; 
/*      */       }  }
/* 5277 */      return b;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean disjoint(Collection<?> paramCollection1, Collection<?> paramCollection2) {
/* 5321 */     Collection<?> collection1 = paramCollection2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5327 */     Collection<?> collection2 = paramCollection1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5334 */     if (paramCollection1 instanceof Set) {
/*      */ 
/*      */       
/* 5337 */       collection2 = paramCollection2;
/* 5338 */       collection1 = paramCollection1;
/* 5339 */     } else if (!(paramCollection2 instanceof Set)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5346 */       int i = paramCollection1.size();
/* 5347 */       int j = paramCollection2.size();
/* 5348 */       if (i == 0 || j == 0)
/*      */       {
/* 5350 */         return true;
/*      */       }
/*      */       
/* 5353 */       if (i > j) {
/* 5354 */         collection2 = paramCollection2;
/* 5355 */         collection1 = paramCollection1;
/*      */       } 
/*      */     } 
/*      */     
/* 5359 */     for (Object object : collection2) {
/* 5360 */       if (collection1.contains(object))
/*      */       {
/* 5362 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5367 */     return true;
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
/*      */ 
/*      */   
/*      */   @SafeVarargs
/*      */   public static <T> boolean addAll(Collection<? super T> paramCollection, T... paramVarArgs) {
/* 5399 */     boolean bool = false;
/* 5400 */     for (T t : paramVarArgs)
/* 5401 */       bool |= paramCollection.add(t); 
/* 5402 */     return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> Set<E> newSetFromMap(Map<E, Boolean> paramMap) {
/* 5437 */     return new SetFromMap<>(paramMap);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetFromMap<E>
/*      */     extends AbstractSet<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private final Map<E, Boolean> m;
/*      */     private transient Set<E> s;
/*      */     private static final long serialVersionUID = 2454657854757543876L;
/*      */     
/*      */     SetFromMap(Map<E, Boolean> param1Map) {
/* 5450 */       if (!param1Map.isEmpty())
/* 5451 */         throw new IllegalArgumentException("Map is non-empty"); 
/* 5452 */       this.m = param1Map;
/* 5453 */       this.s = param1Map.keySet();
/*      */     }
/*      */     
/* 5456 */     public void clear() { this.m.clear(); }
/* 5457 */     public int size() { return this.m.size(); }
/* 5458 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 5459 */     public boolean contains(Object param1Object) { return this.m.containsKey(param1Object); }
/* 5460 */     public boolean remove(Object param1Object) { return (this.m.remove(param1Object) != null); }
/* 5461 */     public boolean add(E param1E) { return (this.m.put(param1E, Boolean.TRUE) == null); }
/* 5462 */     public Iterator<E> iterator() { return this.s.iterator(); }
/* 5463 */     public Object[] toArray() { return this.s.toArray(); }
/* 5464 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.s.toArray(param1ArrayOfT); }
/* 5465 */     public String toString() { return this.s.toString(); }
/* 5466 */     public int hashCode() { return this.s.hashCode(); }
/* 5467 */     public boolean equals(Object param1Object) { return (param1Object == this || this.s.equals(param1Object)); }
/* 5468 */     public boolean containsAll(Collection<?> param1Collection) { return this.s.containsAll(param1Collection); }
/* 5469 */     public boolean removeAll(Collection<?> param1Collection) { return this.s.removeAll(param1Collection); } public boolean retainAll(Collection<?> param1Collection) {
/* 5470 */       return this.s.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 5476 */       this.s.forEach(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 5480 */       return this.s.removeIf(param1Predicate);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 5484 */       return this.s.spliterator();
/*      */     } public Stream<E> stream() {
/* 5486 */       return this.s.stream();
/*      */     } public Stream<E> parallelStream() {
/* 5488 */       return this.s.parallelStream();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 5495 */       param1ObjectInputStream.defaultReadObject();
/* 5496 */       this.s = this.m.keySet();
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
/*      */   public static <T> Queue<T> asLifoQueue(Deque<T> paramDeque) {
/* 5519 */     return new AsLIFOQueue<>(paramDeque);
/*      */   }
/*      */   
/*      */   static class AsLIFOQueue<E>
/*      */     extends AbstractQueue<E>
/*      */     implements Queue<E>, Serializable {
/*      */     private static final long serialVersionUID = 1802017725587941708L;
/*      */     private final Deque<E> q;
/*      */     
/*      */     AsLIFOQueue(Deque<E> param1Deque) {
/* 5529 */       this.q = param1Deque;
/* 5530 */     } public boolean add(E param1E) { this.q.addFirst(param1E); return true; }
/* 5531 */     public boolean offer(E param1E) { return this.q.offerFirst(param1E); }
/* 5532 */     public E poll() { return this.q.pollFirst(); }
/* 5533 */     public E remove() { return this.q.removeFirst(); }
/* 5534 */     public E peek() { return this.q.peekFirst(); }
/* 5535 */     public E element() { return this.q.getFirst(); }
/* 5536 */     public void clear() { this.q.clear(); }
/* 5537 */     public int size() { return this.q.size(); }
/* 5538 */     public boolean isEmpty() { return this.q.isEmpty(); }
/* 5539 */     public boolean contains(Object param1Object) { return this.q.contains(param1Object); }
/* 5540 */     public boolean remove(Object param1Object) { return this.q.remove(param1Object); }
/* 5541 */     public Iterator<E> iterator() { return this.q.iterator(); }
/* 5542 */     public Object[] toArray() { return this.q.toArray(); }
/* 5543 */     public <T> T[] toArray(T[] param1ArrayOfT) { return (T[])this.q.toArray((Object[])param1ArrayOfT); }
/* 5544 */     public String toString() { return this.q.toString(); }
/* 5545 */     public boolean containsAll(Collection<?> param1Collection) { return this.q.containsAll(param1Collection); }
/* 5546 */     public boolean removeAll(Collection<?> param1Collection) { return this.q.removeAll(param1Collection); } public boolean retainAll(Collection<?> param1Collection) {
/* 5547 */       return this.q.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 5552 */       this.q.forEach(param1Consumer);
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 5555 */       return this.q.removeIf(param1Predicate);
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 5558 */       return this.q.spliterator();
/*      */     } public Stream<E> stream() {
/* 5560 */       return this.q.stream();
/*      */     } public Stream<E> parallelStream() {
/* 5562 */       return this.q.parallelStream();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\Collections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */