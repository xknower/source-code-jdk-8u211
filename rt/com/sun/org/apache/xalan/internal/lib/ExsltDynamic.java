/*     */ package com.sun.org.apache.xalan.internal.lib;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xpath.internal.NodeSet;
/*     */ import com.sun.org.apache.xpath.internal.NodeSetDTM;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExsltDynamic
/*     */   extends ExsltBase
/*     */ {
/*     */   public static final String EXSL_URI = "http://exslt.org/common";
/*     */   
/*     */   public static double max(ExpressionContext myContext, NodeList nl, String expr) throws SAXNotSupportedException {
/* 105 */     XPathContext xctxt = null;
/* 106 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/* 107 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */     } else {
/* 109 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*     */     } 
/* 111 */     if (expr == null || expr.length() == 0) {
/* 112 */       return Double.NaN;
/*     */     }
/* 114 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 115 */     xctxt.pushContextNodeList(contextNodes);
/*     */     
/* 117 */     double maxValue = -1.7976931348623157E308D;
/* 118 */     for (int i = 0; i < contextNodes.getLength(); i++) {
/*     */       
/* 120 */       int contextNode = contextNodes.item(i);
/* 121 */       xctxt.pushCurrentNode(contextNode);
/*     */       
/* 123 */       double result = 0.0D;
/*     */ 
/*     */       
/*     */       try {
/* 127 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */         
/* 129 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/*     */       }
/* 131 */       catch (TransformerException e) {
/*     */         
/* 133 */         xctxt.popCurrentNode();
/* 134 */         xctxt.popContextNodeList();
/* 135 */         return Double.NaN;
/*     */       } 
/*     */       
/* 138 */       xctxt.popCurrentNode();
/*     */       
/* 140 */       if (result > maxValue) {
/* 141 */         maxValue = result;
/*     */       }
/*     */     } 
/* 144 */     xctxt.popContextNodeList();
/* 145 */     return maxValue;
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
/*     */   
/*     */   public static double min(ExpressionContext myContext, NodeList nl, String expr) throws SAXNotSupportedException {
/* 186 */     XPathContext xctxt = null;
/* 187 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/* 188 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */     } else {
/* 190 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*     */     } 
/* 192 */     if (expr == null || expr.length() == 0) {
/* 193 */       return Double.NaN;
/*     */     }
/* 195 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 196 */     xctxt.pushContextNodeList(contextNodes);
/*     */     
/* 198 */     double minValue = Double.MAX_VALUE;
/* 199 */     for (int i = 0; i < nl.getLength(); i++) {
/*     */       
/* 201 */       int contextNode = contextNodes.item(i);
/* 202 */       xctxt.pushCurrentNode(contextNode);
/*     */       
/* 204 */       double result = 0.0D;
/*     */ 
/*     */       
/*     */       try {
/* 208 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */         
/* 210 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/*     */       }
/* 212 */       catch (TransformerException e) {
/*     */         
/* 214 */         xctxt.popCurrentNode();
/* 215 */         xctxt.popContextNodeList();
/* 216 */         return Double.NaN;
/*     */       } 
/*     */       
/* 219 */       xctxt.popCurrentNode();
/*     */       
/* 221 */       if (result < minValue) {
/* 222 */         minValue = result;
/*     */       }
/*     */     } 
/* 225 */     xctxt.popContextNodeList();
/* 226 */     return minValue;
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
/*     */   public static double sum(ExpressionContext myContext, NodeList nl, String expr) throws SAXNotSupportedException {
/* 266 */     XPathContext xctxt = null;
/* 267 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/* 268 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */     } else {
/* 270 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*     */     } 
/* 272 */     if (expr == null || expr.length() == 0) {
/* 273 */       return Double.NaN;
/*     */     }
/* 275 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 276 */     xctxt.pushContextNodeList(contextNodes);
/*     */     
/* 278 */     double sum = 0.0D;
/* 279 */     for (int i = 0; i < nl.getLength(); i++) {
/*     */       
/* 281 */       int contextNode = contextNodes.item(i);
/* 282 */       xctxt.pushCurrentNode(contextNode);
/*     */       
/* 284 */       double result = 0.0D;
/*     */ 
/*     */       
/*     */       try {
/* 288 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */         
/* 290 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/*     */       }
/* 292 */       catch (TransformerException e) {
/*     */         
/* 294 */         xctxt.popCurrentNode();
/* 295 */         xctxt.popContextNodeList();
/* 296 */         return Double.NaN;
/*     */       } 
/*     */       
/* 299 */       xctxt.popCurrentNode();
/*     */       
/* 301 */       sum += result;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     xctxt.popContextNodeList();
/* 306 */     return sum;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList map(ExpressionContext myContext, NodeList nl, String expr) throws SAXNotSupportedException {
/* 368 */     XPathContext xctxt = null;
/* 369 */     Document lDoc = null;
/*     */     
/* 371 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/* 372 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */     } else {
/* 374 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*     */     } 
/* 376 */     if (expr == null || expr.length() == 0) {
/* 377 */       return new NodeSet();
/*     */     }
/* 379 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 380 */     xctxt.pushContextNodeList(contextNodes);
/*     */     
/* 382 */     NodeSet resultSet = new NodeSet();
/* 383 */     resultSet.setShouldCacheNodes(true);
/*     */     
/* 385 */     for (int i = 0; i < nl.getLength(); i++) {
/*     */       
/* 387 */       int contextNode = contextNodes.item(i);
/* 388 */       xctxt.pushCurrentNode(contextNode);
/*     */       
/* 390 */       XObject object = null;
/*     */ 
/*     */       
/*     */       try {
/* 394 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */         
/* 396 */         object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());
/*     */         
/* 398 */         if (object instanceof XNodeSet) {
/*     */           
/* 400 */           NodeList nodelist = null;
/* 401 */           nodelist = ((XNodeSet)object).nodelist();
/*     */           
/* 403 */           for (int k = 0; k < nodelist.getLength(); k++) {
/*     */             
/* 405 */             Node n = nodelist.item(k);
/* 406 */             if (!resultSet.contains(n)) {
/* 407 */               resultSet.addNode(n);
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/* 412 */           if (lDoc == null)
/*     */           {
/* 414 */             lDoc = JdkXmlUtils.getDOMDocument();
/*     */           }
/*     */           
/* 417 */           Element element = null;
/* 418 */           if (object instanceof com.sun.org.apache.xpath.internal.objects.XNumber) {
/* 419 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:number");
/* 420 */           } else if (object instanceof com.sun.org.apache.xpath.internal.objects.XBoolean) {
/* 421 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:boolean");
/*     */           } else {
/* 423 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:string");
/*     */           } 
/* 425 */           Text textNode = lDoc.createTextNode(object.str());
/* 426 */           element.appendChild(textNode);
/* 427 */           resultSet.addNode(element);
/*     */         }
/*     */       
/* 430 */       } catch (Exception e) {
/*     */         
/* 432 */         xctxt.popCurrentNode();
/* 433 */         xctxt.popContextNodeList();
/* 434 */         return new NodeSet();
/*     */       } 
/*     */       
/* 437 */       xctxt.popCurrentNode();
/*     */     } 
/*     */ 
/*     */     
/* 441 */     xctxt.popContextNodeList();
/* 442 */     return resultSet;
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
/*     */   public static XObject evaluate(ExpressionContext myContext, String xpathExpr) throws SAXNotSupportedException {
/* 464 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/*     */       
/* 466 */       XPathContext xctxt = null;
/*     */       
/*     */       try {
/* 469 */         xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */         
/* 471 */         XPath dynamicXPath = new XPath(xpathExpr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */ 
/*     */         
/* 474 */         return dynamicXPath.execute(xctxt, myContext.getContextNode(), xctxt
/* 475 */             .getNamespaceContext());
/*     */       }
/* 477 */       catch (TransformerException e) {
/*     */         
/* 479 */         return new XNodeSet(xctxt.getDTMManager());
/*     */       } 
/*     */     } 
/*     */     
/* 483 */     throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList closure(ExpressionContext myContext, NodeList nl, String expr) throws SAXNotSupportedException {
/* 532 */     XPathContext xctxt = null;
/* 533 */     if (myContext instanceof XPathContext.XPathExpressionContext) {
/* 534 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*     */     } else {
/* 536 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*     */     } 
/* 538 */     if (expr == null || expr.length() == 0) {
/* 539 */       return new NodeSet();
/*     */     }
/* 541 */     NodeSet closureSet = new NodeSet();
/* 542 */     closureSet.setShouldCacheNodes(true);
/*     */     
/* 544 */     NodeList iterationList = nl;
/*     */ 
/*     */     
/*     */     do {
/* 548 */       NodeSet iterationSet = new NodeSet();
/*     */       
/* 550 */       NodeSetDTM contextNodes = new NodeSetDTM(iterationList, xctxt);
/* 551 */       xctxt.pushContextNodeList(contextNodes);
/*     */       int i;
/* 553 */       for (i = 0; i < iterationList.getLength(); i++) {
/*     */         
/* 555 */         int contextNode = contextNodes.item(i);
/* 556 */         xctxt.pushCurrentNode(contextNode);
/*     */         
/* 558 */         XObject object = null;
/*     */ 
/*     */         
/*     */         try {
/* 562 */           XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*     */           
/* 564 */           object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());
/*     */           
/* 566 */           if (object instanceof XNodeSet) {
/*     */             
/* 568 */             NodeList nodelist = null;
/* 569 */             nodelist = ((XNodeSet)object).nodelist();
/*     */             
/* 571 */             for (int k = 0; k < nodelist.getLength(); k++) {
/*     */               
/* 573 */               Node n = nodelist.item(k);
/* 574 */               if (!iterationSet.contains(n)) {
/* 575 */                 iterationSet.addNode(n);
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             
/* 580 */             xctxt.popCurrentNode();
/* 581 */             xctxt.popContextNodeList();
/* 582 */             return new NodeSet();
/*     */           }
/*     */         
/* 585 */         } catch (TransformerException e) {
/*     */           
/* 587 */           xctxt.popCurrentNode();
/* 588 */           xctxt.popContextNodeList();
/* 589 */           return new NodeSet();
/*     */         } 
/*     */         
/* 592 */         xctxt.popCurrentNode();
/*     */       } 
/*     */ 
/*     */       
/* 596 */       xctxt.popContextNodeList();
/*     */       
/* 598 */       iterationList = iterationSet;
/*     */       
/* 600 */       for (i = 0; i < iterationList.getLength(); i++) {
/*     */         
/* 602 */         Node n = iterationList.item(i);
/* 603 */         if (!closureSet.contains(n)) {
/* 604 */           closureSet.addNode(n);
/*     */         }
/*     */       } 
/* 607 */     } while (iterationList.getLength() > 0);
/*     */     
/* 609 */     return closureSet;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\lib\ExsltDynamic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */