/*     */ package com.sun.org.apache.xalan.internal.lib;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
/*     */ import com.sun.org.apache.xpath.internal.NodeSet;
/*     */ import com.sun.org.apache.xpath.internal.objects.XBoolean;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNumber;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.util.StringTokenizer;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Extensions
/*     */ {
/*     */   public static NodeSet nodeset(ExpressionContext myProcessor, Object rtf) {
/*     */     String textNodeValue;
/*  88 */     if (rtf instanceof NodeIterator)
/*     */     {
/*  90 */       return new NodeSet((NodeIterator)rtf);
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (rtf instanceof String) {
/*     */       
/*  96 */       textNodeValue = (String)rtf;
/*     */     }
/*  98 */     else if (rtf instanceof Boolean) {
/*     */       
/* 100 */       textNodeValue = (new XBoolean(((Boolean)rtf).booleanValue())).str();
/*     */     }
/* 102 */     else if (rtf instanceof Double) {
/*     */       
/* 104 */       textNodeValue = (new XNumber(((Double)rtf).doubleValue())).str();
/*     */     }
/*     */     else {
/*     */       
/* 108 */       textNodeValue = rtf.toString();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     Document myDoc = JdkXmlUtils.getDOMDocument();
/*     */     
/* 115 */     Text textNode = myDoc.createTextNode(textNodeValue);
/* 116 */     DocumentFragment docFrag = myDoc.createDocumentFragment();
/*     */     
/* 118 */     docFrag.appendChild(textNode);
/*     */     
/* 120 */     return new NodeSet(docFrag);
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
/*     */   public static NodeList intersection(NodeList nl1, NodeList nl2) {
/* 137 */     return ExsltSets.intersection(nl1, nl2);
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
/*     */   public static NodeList difference(NodeList nl1, NodeList nl2) {
/* 153 */     return ExsltSets.difference(nl1, nl2);
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
/*     */   public static NodeList distinct(NodeList nl) {
/* 170 */     return ExsltSets.distinct(nl);
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
/*     */   public static boolean hasSameNodes(NodeList nl1, NodeList nl2) {
/* 183 */     NodeSet ns1 = new NodeSet(nl1);
/* 184 */     NodeSet ns2 = new NodeSet(nl2);
/*     */     
/* 186 */     if (ns1.getLength() != ns2.getLength()) {
/* 187 */       return false;
/*     */     }
/* 189 */     for (int i = 0; i < ns1.getLength(); i++) {
/*     */       
/* 191 */       Node n = ns1.elementAt(i);
/*     */       
/* 193 */       if (!ns2.contains(n)) {
/* 194 */         return false;
/*     */       }
/*     */     } 
/* 197 */     return true;
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
/*     */   public static XObject evaluate(ExpressionContext myContext, String xpathExpr) throws SAXNotSupportedException {
/* 220 */     return ExsltDynamic.evaluate(myContext, xpathExpr);
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
/*     */   public static NodeList tokenize(String toTokenize, String delims) {
/* 239 */     Document doc = JdkXmlUtils.getDOMDocument();
/*     */     
/* 241 */     StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);
/* 242 */     NodeSet resultSet = new NodeSet();
/*     */     
/* 244 */     synchronized (doc) {
/*     */       
/* 246 */       while (lTokenizer.hasMoreTokens())
/*     */       {
/* 248 */         resultSet.addNode(doc.createTextNode(lTokenizer.nextToken()));
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return resultSet;
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
/*     */   public static NodeList tokenize(String toTokenize) {
/* 270 */     return tokenize(toTokenize, " \t\n\r");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\lib\Extensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */