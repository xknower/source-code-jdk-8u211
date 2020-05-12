/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SynchronizedSymbolTable
/*     */   extends SymbolTable
/*     */ {
/*     */   protected SymbolTable fSymbolTable;
/*     */   
/*     */   public SynchronizedSymbolTable(SymbolTable symbolTable) {
/*  48 */     this.fSymbolTable = symbolTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public SynchronizedSymbolTable() {
/*  53 */     this.fSymbolTable = new SymbolTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public SynchronizedSymbolTable(int size) {
/*  58 */     this.fSymbolTable = new SymbolTable(size);
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
/*     */   public String addSymbol(String symbol) {
/*  75 */     synchronized (this.fSymbolTable) {
/*  76 */       return this.fSymbolTable.addSymbol(symbol);
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
/*     */   public String addSymbol(char[] buffer, int offset, int length) {
/*  93 */     synchronized (this.fSymbolTable) {
/*  94 */       return this.fSymbolTable.addSymbol(buffer, offset, length);
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
/*     */   public boolean containsSymbol(String symbol) {
/* 107 */     synchronized (this.fSymbolTable) {
/* 108 */       return this.fSymbolTable.containsSymbol(symbol);
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
/*     */   public boolean containsSymbol(char[] buffer, int offset, int length) {
/* 123 */     synchronized (this.fSymbolTable) {
/* 124 */       return this.fSymbolTable.containsSymbol(buffer, offset, length);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\SynchronizedSymbolTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */