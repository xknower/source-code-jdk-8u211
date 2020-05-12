/*     */ package com.sun.org.apache.xalan.internal.xsltc.dom;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.TransletException;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import com.sun.org.apache.xml.internal.utils.LocaleUtility;
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
/*     */ public class NodeSortRecordFactory
/*     */ {
/*  37 */   private static int DESCENDING = "descending".length();
/*  38 */   private static int NUMBER = "number".length();
/*     */ 
/*     */ 
/*     */   
/*     */   private final DOM _dom;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String _className;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class _class;
/*     */ 
/*     */ 
/*     */   
/*     */   private SortSettings _sortSettings;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collator _collator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeSortRecordFactory(DOM dom, String className, Translet translet, String[] order, String[] type) throws TransletException {
/*  64 */     this(dom, className, translet, order, type, null, null);
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
/*     */   public NodeSortRecordFactory(DOM dom, String className, Translet translet, String[] order, String[] type, String[] lang, String[] caseOrder) throws TransletException {
/*     */     try {
/*  80 */       this._dom = dom;
/*  81 */       this._className = className;
/*     */       
/*  83 */       this._class = translet.getAuxiliaryClass(className);
/*     */       
/*  85 */       if (this._class == null) {
/*  86 */         this._class = ObjectFactory.findProviderClass(className, true);
/*     */       }
/*     */       
/*  89 */       int levels = order.length;
/*  90 */       int[] iOrder = new int[levels];
/*  91 */       int[] iType = new int[levels];
/*  92 */       for (int i = 0; i < levels; i++) {
/*  93 */         if (order[i].length() == DESCENDING) {
/*  94 */           iOrder[i] = 1;
/*     */         }
/*  96 */         if (type[i].length() == NUMBER) {
/*  97 */           iType[i] = 1;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       String[] emptyStringArray = null;
/* 105 */       if (lang == null || caseOrder == null) {
/* 106 */         int numSortKeys = order.length;
/* 107 */         emptyStringArray = new String[numSortKeys];
/*     */ 
/*     */ 
/*     */         
/* 111 */         for (int k = 0; k < numSortKeys; k++) {
/* 112 */           emptyStringArray[k] = "";
/*     */         }
/*     */       } 
/*     */       
/* 116 */       if (lang == null) {
/* 117 */         lang = emptyStringArray;
/*     */       }
/* 119 */       if (caseOrder == null) {
/* 120 */         caseOrder = emptyStringArray;
/*     */       }
/*     */       
/* 123 */       int length = lang.length;
/* 124 */       Locale[] locales = new Locale[length];
/* 125 */       Collator[] collators = new Collator[length];
/* 126 */       for (int j = 0; j < length; j++) {
/* 127 */         locales[j] = LocaleUtility.langToLocale(lang[j]);
/* 128 */         collators[j] = Collator.getInstance(locales[j]);
/*     */       } 
/*     */       
/* 131 */       this._sortSettings = new SortSettings((AbstractTranslet)translet, iOrder, iType, locales, collators, caseOrder);
/*     */     
/*     */     }
/* 134 */     catch (ClassNotFoundException e) {
/* 135 */       throw new TransletException(e);
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
/*     */ 
/*     */   
/*     */   public NodeSortRecord makeNodeSortRecord(int node, int last) throws ExceptionInInitializerError, LinkageError, IllegalAccessException, InstantiationException, SecurityException, TransletException {
/* 154 */     NodeSortRecord sortRecord = this._class.newInstance();
/* 155 */     sortRecord.initialize(node, last, this._dom, this._sortSettings);
/* 156 */     return sortRecord;
/*     */   }
/*     */   
/*     */   public String getClassName() {
/* 160 */     return this._className;
/*     */   }
/*     */   
/*     */   private final void setLang(String[] lang) {}
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\dom\NodeSortRecordFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */