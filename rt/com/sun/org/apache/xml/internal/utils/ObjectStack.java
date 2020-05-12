/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import java.util.EmptyStackException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectStack
/*     */   extends ObjectVector
/*     */ {
/*     */   public ObjectStack() {}
/*     */   
/*     */   public ObjectStack(int blocksize) {
/*  55 */     super(blocksize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectStack(ObjectStack v) {
/*  65 */     super(v);
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
/*     */   public Object push(Object i) {
/*  77 */     if (this.m_firstFree + 1 >= this.m_mapSize) {
/*     */       
/*  79 */       this.m_mapSize += this.m_blocksize;
/*     */       
/*  81 */       Object[] newMap = new Object[this.m_mapSize];
/*     */       
/*  83 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*     */       
/*  85 */       this.m_map = newMap;
/*     */     } 
/*     */     
/*  88 */     this.m_map[this.m_firstFree] = i;
/*     */     
/*  90 */     this.m_firstFree++;
/*     */     
/*  92 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object pop() {
/* 103 */     Object val = this.m_map[--this.m_firstFree];
/* 104 */     this.m_map[this.m_firstFree] = null;
/*     */     
/* 106 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void quickPop(int n) {
/* 115 */     this.m_firstFree -= n;
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
/*     */   public Object peek() {
/*     */     try {
/* 128 */       return this.m_map[this.m_firstFree - 1];
/*     */     }
/* 130 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 132 */       throw new EmptyStackException();
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
/*     */   public Object peek(int n) {
/*     */     try {
/* 146 */       return this.m_map[this.m_firstFree - 1 + n];
/*     */     }
/* 148 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 150 */       throw new EmptyStackException();
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
/*     */   public void setTop(Object val) {
/*     */     try {
/* 164 */       this.m_map[this.m_firstFree - 1] = val;
/*     */     }
/* 166 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */       
/* 168 */       throw new EmptyStackException();
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
/*     */   public boolean empty() {
/* 181 */     return (this.m_firstFree == 0);
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
/*     */   public int search(Object o) {
/* 196 */     int i = lastIndexOf(o);
/*     */     
/* 198 */     if (i >= 0)
/*     */     {
/* 200 */       return size() - i;
/*     */     }
/*     */     
/* 203 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 214 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xml\interna\\utils\ObjectStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */