/*     */ package com.sun.org.apache.xpath.internal.domapi;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.res.XPATHMessages;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.xpath.XPathEvaluator;
/*     */ import org.w3c.dom.xpath.XPathException;
/*     */ import org.w3c.dom.xpath.XPathExpression;
/*     */ import org.w3c.dom.xpath.XPathNSResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XPathEvaluatorImpl
/*     */   implements XPathEvaluator
/*     */ {
/*     */   private final Document m_doc;
/*     */   
/*     */   private class DummyPrefixResolver
/*     */     implements PrefixResolver
/*     */   {
/*     */     public String getNamespaceForPrefix(String prefix, Node context) {
/*  83 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NULL_RESOLVER", null);
/*  84 */       throw new DOMException((short)14, fmsg);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNamespaceForPrefix(String prefix) {
/*  94 */       return getNamespaceForPrefix(prefix, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean handlesNullPrefixes() {
/* 101 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getBaseIdentifier() {
/* 108 */       return null;
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
/*     */   public XPathEvaluatorImpl(Document doc) {
/* 126 */     this.m_doc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathEvaluatorImpl() {
/* 135 */     this.m_doc = null;
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
/*     */   public XPathExpression createExpression(String expression, XPathNSResolver resolver) throws XPathException, DOMException {
/*     */     try {
/* 170 */       XPath xpath = new XPath(expression, null, (null == resolver) ? new DummyPrefixResolver() : (PrefixResolver)resolver, 0);
/*     */ 
/*     */ 
/*     */       
/* 174 */       return new XPathExpressionImpl(xpath, this.m_doc);
/*     */     }
/* 176 */     catch (TransformerException e) {
/*     */ 
/*     */       
/* 179 */       if (e instanceof XPathStylesheetDOM3Exception) {
/* 180 */         throw new DOMException((short)14, e.getMessageAndLocation());
/*     */       }
/* 182 */       throw new XPathException((short)1, e.getMessageAndLocation());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathNSResolver createNSResolver(Node nodeResolver) {
/* 205 */     return new XPathNSResolverImpl((nodeResolver.getNodeType() == 9) ? ((Document)nodeResolver)
/* 206 */         .getDocumentElement() : nodeResolver);
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
/*     */   public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result) throws XPathException, DOMException {
/* 268 */     XPathExpression xpathExpression = createExpression(expression, resolver);
/*     */     
/* 270 */     return xpathExpression.evaluate(contextNode, type, result);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\domapi\XPathEvaluatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */