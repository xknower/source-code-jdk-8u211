/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Stack<E>
/*     */   extends Vector<E>
/*     */ {
/*     */   private static final long serialVersionUID = 1224463164541339165L;
/*     */   
/*     */   public E push(E paramE) {
/*  67 */     addElement(paramE);
/*     */     
/*  69 */     return paramE;
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
/*     */   public synchronized E pop() {
/*  82 */     int i = size();
/*     */     
/*  84 */     E e = peek();
/*  85 */     removeElementAt(i - 1);
/*     */     
/*  87 */     return e;
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
/*     */   public synchronized E peek() {
/*  99 */     int i = size();
/*     */     
/* 101 */     if (i == 0)
/* 102 */       throw new EmptyStackException(); 
/* 103 */     return elementAt(i - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean empty() {
/* 113 */     return (size() == 0);
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
/*     */   public synchronized int search(Object paramObject) {
/* 131 */     int i = lastIndexOf(paramObject);
/*     */     
/* 133 */     if (i >= 0) {
/* 134 */       return size() - i;
/*     */     }
/* 136 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\Stack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */