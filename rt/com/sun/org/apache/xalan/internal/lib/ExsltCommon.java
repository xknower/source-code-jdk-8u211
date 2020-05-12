/*     */ package com.sun.org.apache.xalan.internal.lib;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
/*     */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeIterator;
/*     */ import com.sun.org.apache.xpath.internal.NodeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExsltCommon
/*     */ {
/*     */   public static String objectType(Object obj) {
/*  65 */     if (obj instanceof String)
/*  66 */       return "string"; 
/*  67 */     if (obj instanceof Boolean)
/*  68 */       return "boolean"; 
/*  69 */     if (obj instanceof Number)
/*  70 */       return "number"; 
/*  71 */     if (obj instanceof DTMNodeIterator) {
/*     */       
/*  73 */       DTMIterator dtmI = ((DTMNodeIterator)obj).getDTMIterator();
/*  74 */       if (dtmI instanceof com.sun.org.apache.xpath.internal.axes.RTFIterator) {
/*  75 */         return "RTF";
/*     */       }
/*  77 */       return "node-set";
/*     */     } 
/*     */     
/*  80 */     return "unknown";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeSet nodeSet(ExpressionContext myProcessor, Object rtf) {
/* 105 */     return Extensions.nodeset(myProcessor, rtf);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\lib\ExsltCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */