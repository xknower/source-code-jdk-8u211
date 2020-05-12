/*    */ package com.sun.xml.internal.messaging.saaj.util;
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
/*    */ public final class FinalArrayList
/*    */   extends ArrayList
/*    */ {
/*    */   public FinalArrayList(int initialCapacity) {
/* 37 */     super(initialCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public FinalArrayList() {}
/*    */   
/*    */   public FinalArrayList(Collection<? extends E> collection) {
/* 44 */     super(collection);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saa\\util\FinalArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */