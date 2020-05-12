/*    */ package com.sun.org.apache.xalan.internal.lib;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExsltBase
/*    */ {
/*    */   protected static String toString(Node n) {
/* 44 */     if (n instanceof DTMNodeProxy) {
/* 45 */       return ((DTMNodeProxy)n).getStringValue();
/*    */     }
/*    */     
/* 48 */     String value = n.getNodeValue();
/* 49 */     if (value == null) {
/*    */       
/* 51 */       NodeList nodelist = n.getChildNodes();
/* 52 */       StringBuffer buf = new StringBuffer();
/* 53 */       for (int i = 0; i < nodelist.getLength(); i++) {
/*    */         
/* 55 */         Node childNode = nodelist.item(i);
/* 56 */         buf.append(toString(childNode));
/*    */       } 
/* 58 */       return buf.toString();
/*    */     } 
/*    */     
/* 61 */     return value;
/*    */   }
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
/*    */   protected static double toNumber(Node n) {
/* 74 */     double d = 0.0D;
/* 75 */     String str = toString(n);
/*    */     
/*    */     try {
/* 78 */       d = Double.valueOf(str).doubleValue();
/*    */     }
/* 80 */     catch (NumberFormatException e) {
/*    */       
/* 82 */       d = Double.NaN;
/*    */     } 
/* 84 */     return d;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\lib\ExsltBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */