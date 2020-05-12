/*     */ package com.sun.org.apache.xalan.internal.lib;
/*     */ 
/*     */ import com.sun.org.apache.xpath.internal.NodeSet;
/*     */ import java.util.StringTokenizer;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExsltStrings
/*     */   extends ExsltBase
/*     */ {
/*     */   public static String align(String targetStr, String paddingStr, String type) {
/*  82 */     if (targetStr.length() >= paddingStr.length()) {
/*  83 */       return targetStr.substring(0, paddingStr.length());
/*     */     }
/*  85 */     if (type.equals("right"))
/*     */     {
/*  87 */       return paddingStr.substring(0, paddingStr.length() - targetStr.length()) + targetStr;
/*     */     }
/*  89 */     if (type.equals("center")) {
/*     */       
/*  91 */       int startIndex = (paddingStr.length() - targetStr.length()) / 2;
/*  92 */       return paddingStr.substring(0, startIndex) + targetStr + paddingStr.substring(startIndex + targetStr.length());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  97 */     return targetStr + paddingStr.substring(targetStr.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String align(String targetStr, String paddingStr) {
/* 106 */     return align(targetStr, paddingStr, "left");
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
/*     */   public static String concat(NodeList nl) {
/* 119 */     StringBuffer sb = new StringBuffer();
/* 120 */     for (int i = 0; i < nl.getLength(); i++) {
/*     */       
/* 122 */       Node node = nl.item(i);
/* 123 */       String value = toString(node);
/*     */       
/* 125 */       if (value != null && value.length() > 0) {
/* 126 */         sb.append(value);
/*     */       }
/*     */     } 
/* 129 */     return sb.toString();
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
/*     */   public static String padding(double length, String pattern) {
/* 149 */     if (pattern == null || pattern.length() == 0) {
/* 150 */       return "";
/*     */     }
/* 152 */     StringBuffer sb = new StringBuffer();
/* 153 */     int len = (int)length;
/* 154 */     int numAdded = 0;
/* 155 */     int index = 0;
/* 156 */     while (numAdded < len) {
/*     */       
/* 158 */       if (index == pattern.length()) {
/* 159 */         index = 0;
/*     */       }
/* 161 */       sb.append(pattern.charAt(index));
/* 162 */       index++;
/* 163 */       numAdded++;
/*     */     } 
/*     */     
/* 166 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String padding(double length) {
/* 174 */     return padding(length, " ");
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList split(String str, String pattern) {
/* 202 */     NodeSet resultSet = new NodeSet();
/* 203 */     resultSet.setShouldCacheNodes(true);
/*     */     
/* 205 */     boolean done = false;
/* 206 */     int fromIndex = 0;
/* 207 */     int matchIndex = 0;
/* 208 */     String token = null;
/*     */     
/* 210 */     while (!done && fromIndex < str.length()) {
/*     */       
/* 212 */       matchIndex = str.indexOf(pattern, fromIndex);
/* 213 */       if (matchIndex >= 0) {
/*     */         
/* 215 */         token = str.substring(fromIndex, matchIndex);
/* 216 */         fromIndex = matchIndex + pattern.length();
/*     */       }
/*     */       else {
/*     */         
/* 220 */         done = true;
/* 221 */         token = str.substring(fromIndex);
/*     */       } 
/*     */       
/* 224 */       Document doc = JdkXmlUtils.getDOMDocument();
/* 225 */       synchronized (doc) {
/*     */         
/* 227 */         Element element = doc.createElement("token");
/* 228 */         Text text = doc.createTextNode(token);
/* 229 */         element.appendChild(text);
/* 230 */         resultSet.addNode(element);
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     return resultSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList split(String str) {
/* 242 */     return split(str, " ");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList tokenize(String toTokenize, String delims) {
/* 282 */     NodeSet resultSet = new NodeSet();
/*     */     
/* 284 */     if (delims != null && delims.length() > 0) {
/*     */       
/* 286 */       StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);
/*     */       
/* 288 */       Document doc = JdkXmlUtils.getDOMDocument();
/* 289 */       synchronized (doc)
/*     */       {
/* 291 */         while (lTokenizer.hasMoreTokens())
/*     */         {
/* 293 */           Element element = doc.createElement("token");
/* 294 */           element.appendChild(doc.createTextNode(lTokenizer.nextToken()));
/* 295 */           resultSet.addNode(element);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 304 */       Document doc = JdkXmlUtils.getDOMDocument();
/* 305 */       synchronized (doc) {
/*     */         
/* 307 */         for (int i = 0; i < toTokenize.length(); i++) {
/*     */           
/* 309 */           Element element = doc.createElement("token");
/* 310 */           element.appendChild(doc.createTextNode(toTokenize.substring(i, i + 1)));
/* 311 */           resultSet.addNode(element);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     return resultSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList tokenize(String toTokenize) {
/* 324 */     return tokenize(toTokenize, " \t\n\r");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\lib\ExsltStrings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */