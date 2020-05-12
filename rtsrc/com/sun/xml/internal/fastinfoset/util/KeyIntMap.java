/*     */ package com.sun.xml.internal.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyIntMap
/*     */ {
/*     */   public static final int NOT_PRESENT = -1;
/*     */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   static final int MAXIMUM_CAPACITY = 1048576;
/*     */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*     */   int _readOnlyMapSize;
/*     */   int _size;
/*     */   int _capacity;
/*     */   int _threshold;
/*     */   final float _loadFactor;
/*     */   
/*     */   static class BaseEntry
/*     */   {
/*     */     final int _hash;
/*     */     final int _value;
/*     */     
/*     */     public BaseEntry(int hash, int value) {
/*  75 */       this._hash = hash;
/*  76 */       this._value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public KeyIntMap(int initialCapacity, float loadFactor) {
/*  81 */     if (initialCapacity < 0)
/*  82 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance()
/*  83 */           .getString("message.illegalInitialCapacity", new Object[] { Integer.valueOf(initialCapacity) })); 
/*  84 */     if (initialCapacity > 1048576)
/*  85 */       initialCapacity = 1048576; 
/*  86 */     if (loadFactor <= 0.0F || Float.isNaN(loadFactor)) {
/*  87 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance()
/*  88 */           .getString("message.illegalLoadFactor", new Object[] { Float.valueOf(loadFactor) }));
/*     */     }
/*     */     
/*  91 */     if (initialCapacity != 16) {
/*  92 */       this._capacity = 1;
/*  93 */       while (this._capacity < initialCapacity) {
/*  94 */         this._capacity <<= 1;
/*     */       }
/*  96 */       this._loadFactor = loadFactor;
/*  97 */       this._threshold = (int)(this._capacity * this._loadFactor);
/*     */     } else {
/*  99 */       this._capacity = 16;
/* 100 */       this._loadFactor = 0.75F;
/* 101 */       this._threshold = 12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public KeyIntMap(int initialCapacity) {
/* 106 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public KeyIntMap() {
/* 110 */     this._capacity = 16;
/* 111 */     this._loadFactor = 0.75F;
/* 112 */     this._threshold = 12;
/*     */   }
/*     */   
/*     */   public final int size() {
/* 116 */     return this._size + this._readOnlyMapSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void clear();
/*     */   
/*     */   public abstract void setReadOnlyMap(KeyIntMap paramKeyIntMap, boolean paramBoolean);
/*     */   
/*     */   public static final int hashHash(int h) {
/* 125 */     h += h << 9 ^ 0xFFFFFFFF;
/* 126 */     h ^= h >>> 14;
/* 127 */     h += h << 4;
/* 128 */     h ^= h >>> 10;
/* 129 */     return h;
/*     */   }
/*     */   
/*     */   public static final int indexFor(int h, int length) {
/* 133 */     return h & length - 1;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\fastinfose\\util\KeyIntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */