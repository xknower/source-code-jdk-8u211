/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import java.text.Collator;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SortSettings
/*     */ {
/*     */   private AbstractTranslet _translet;
/*     */   private int[] _sortOrders;
/*     */   private int[] _types;
/*     */   private Locale[] _locales;
/*     */   private Collator[] _collators;
/*     */   private String[] _caseOrders;
/*     */   
/*     */   SortSettings(AbstractTranslet translet, int[] sortOrders, int[] types, Locale[] locales, Collator[] collators, String[] caseOrders) {
/*  86 */     this._translet = translet;
/*  87 */     this._sortOrders = sortOrders;
/*  88 */     this._types = types;
/*  89 */     this._locales = locales;
/*  90 */     this._collators = collators;
/*  91 */     this._caseOrders = caseOrders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AbstractTranslet getTranslet() {
/*  98 */     return this._translet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int[] getSortOrders() {
/* 106 */     return this._sortOrders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int[] getTypes() {
/* 114 */     return this._types;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Locale[] getLocales() {
/* 122 */     return this._locales;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Collator[] getCollators() {
/* 130 */     return this._collators;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String[] getCaseOrders() {
/* 138 */     return this._caseOrders;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\dom\SortSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */