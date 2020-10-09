/*    */ package com.sun.istack.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FinalArrayList<T>
/*    */   extends ArrayList<T>
/*    */ {
/*    */   public FinalArrayList(int initialCapacity) {
/* 41 */     super(initialCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public FinalArrayList() {}
/*    */   
/*    */   public FinalArrayList(Collection<? extends T> ts) {
/* 48 */     super(ts);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\istack\internal\FinalArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */