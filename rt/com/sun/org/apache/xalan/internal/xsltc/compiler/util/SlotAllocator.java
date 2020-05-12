/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SlotAllocator
/*     */ {
/*     */   private int _firstAvailableSlot;
/*  35 */   private int _size = 8;
/*  36 */   private int _free = 0;
/*  37 */   private int[] _slotsTaken = new int[this._size];
/*     */   
/*     */   public void initialize(LocalVariableGen[] vars) {
/*  40 */     int length = vars.length;
/*  41 */     int slot = 0;
/*     */     
/*  43 */     for (int i = 0; i < length; i++) {
/*  44 */       int size = vars[i].getType().getSize();
/*  45 */       int index = vars[i].getIndex();
/*  46 */       slot = Math.max(slot, index + size);
/*     */     } 
/*  48 */     this._firstAvailableSlot = slot;
/*     */   }
/*     */   
/*     */   public int allocateSlot(Type type) {
/*  52 */     int size = type.getSize();
/*  53 */     int limit = this._free;
/*  54 */     int slot = this._firstAvailableSlot, where = 0;
/*     */     
/*  56 */     if (this._free + size > this._size) {
/*  57 */       int[] array = new int[this._size *= 2];
/*  58 */       for (int i = 0; i < limit; i++)
/*  59 */         array[i] = this._slotsTaken[i]; 
/*  60 */       this._slotsTaken = array;
/*     */     } 
/*     */     
/*  63 */     while (where < limit) {
/*  64 */       if (slot + size <= this._slotsTaken[where]) {
/*     */         
/*  66 */         for (int i = limit - 1; i >= where; i--) {
/*  67 */           this._slotsTaken[i + size] = this._slotsTaken[i];
/*     */         }
/*     */         break;
/*     */       } 
/*  71 */       slot = this._slotsTaken[where++] + 1;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     for (int j = 0; j < size; j++) {
/*  76 */       this._slotsTaken[where + j] = slot + j;
/*     */     }
/*  78 */     this._free += size;
/*  79 */     return slot;
/*     */   }
/*     */   
/*     */   public void releaseSlot(LocalVariableGen lvg) {
/*  83 */     int size = lvg.getType().getSize();
/*  84 */     int slot = lvg.getIndex();
/*  85 */     int limit = this._free;
/*     */     
/*  87 */     for (int i = 0; i < limit; i++) {
/*  88 */       if (this._slotsTaken[i] == slot) {
/*  89 */         int j = i + size;
/*  90 */         while (j < limit) {
/*  91 */           this._slotsTaken[i++] = this._slotsTaken[j++];
/*     */         }
/*  93 */         this._free -= size;
/*     */         return;
/*     */       } 
/*     */     } 
/*  97 */     String state = "Variable slot allocation error(size=" + size + ", slot=" + slot + ", limit=" + limit + ")";
/*     */     
/*  99 */     ErrorMsg err = new ErrorMsg("INTERNAL_ERR", state);
/* 100 */     throw new Error(err.toString());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\SlotAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */