/*    */ package com.sun.xml.internal.stream.buffer;
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
/*    */ final class FragmentedArray<T>
/*    */ {
/*    */   private T _item;
/*    */   private FragmentedArray<T> _next;
/*    */   private FragmentedArray<T> _previous;
/*    */   
/*    */   FragmentedArray(T item) {
/* 34 */     this(item, null);
/*    */   }
/*    */   
/*    */   FragmentedArray(T item, FragmentedArray<T> previous) {
/* 38 */     setArray(item);
/* 39 */     if (previous != null) {
/* 40 */       previous._next = this;
/* 41 */       this._previous = previous;
/*    */     } 
/*    */   }
/*    */   
/*    */   T getArray() {
/* 46 */     return this._item;
/*    */   }
/*    */   
/*    */   void setArray(T item) {
/* 50 */     assert item.getClass().isArray();
/*    */     
/* 52 */     this._item = item;
/*    */   }
/*    */   
/*    */   FragmentedArray<T> getNext() {
/* 56 */     return this._next;
/*    */   }
/*    */   
/*    */   void setNext(FragmentedArray<T> next) {
/* 60 */     this._next = next;
/* 61 */     if (next != null) {
/* 62 */       next._previous = this;
/*    */     }
/*    */   }
/*    */   
/*    */   FragmentedArray<T> getPrevious() {
/* 67 */     return this._previous;
/*    */   }
/*    */   
/*    */   void setPrevious(FragmentedArray<T> previous) {
/* 71 */     this._previous = previous;
/* 72 */     if (previous != null)
/* 73 */       previous._next = this; 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\stream\buffer\FragmentedArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */