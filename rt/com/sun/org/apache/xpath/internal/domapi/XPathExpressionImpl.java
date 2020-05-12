/*     */ package com.sun.org.apache.xpath.internal.domapi;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import com.sun.org.apache.xpath.internal.res.XPATHMessages;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.xpath.XPathException;
/*     */ import org.w3c.dom.xpath.XPathExpression;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XPathExpressionImpl
/*     */   implements XPathExpression
/*     */ {
/*     */   private final XPath m_xpath;
/*     */   private final Document m_doc;
/*     */   
/*     */   XPathExpressionImpl(XPath xpath, Document doc) {
/*  76 */     this.m_xpath = xpath;
/*  77 */     this.m_doc = doc;
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
/*     */   public Object evaluate(Node contextNode, short type, Object result) throws XPathException, DOMException {
/* 131 */     if (this.m_doc != null) {
/*     */ 
/*     */       
/* 134 */       if (contextNode != this.m_doc && !contextNode.getOwnerDocument().equals(this.m_doc)) {
/* 135 */         String fmsg = XPATHMessages.createXPATHMessage("ER_WRONG_DOCUMENT", null);
/* 136 */         throw new DOMException((short)4, fmsg);
/*     */       } 
/*     */ 
/*     */       
/* 140 */       short nodeType = contextNode.getNodeType();
/* 141 */       if (nodeType != 9 && nodeType != 1 && nodeType != 2 && nodeType != 3 && nodeType != 4 && nodeType != 8 && nodeType != 7 && nodeType != 13) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         String fmsg = XPATHMessages.createXPATHMessage("ER_WRONG_NODETYPE", null);
/* 150 */         throw new DOMException((short)9, fmsg);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (!XPathResultImpl.isValidType(type)) {
/* 158 */       String fmsg = XPATHMessages.createXPATHMessage("ER_INVALID_XPATH_TYPE", new Object[] { new Integer(type) });
/* 159 */       throw new XPathException((short)2, fmsg);
/*     */     } 
/*     */ 
/*     */     
/* 163 */     XPathContext xpathSupport = new XPathContext();
/*     */ 
/*     */     
/* 166 */     if (null != this.m_doc) {
/* 167 */       xpathSupport.getDTMHandleFromNode(this.m_doc);
/*     */     }
/*     */     
/* 170 */     XObject xobj = null;
/*     */     try {
/* 172 */       xobj = this.m_xpath.execute(xpathSupport, contextNode, (PrefixResolver)null);
/* 173 */     } catch (TransformerException te) {
/*     */       
/* 175 */       throw new XPathException((short)1, te.getMessageAndLocation());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     return new XPathResultImpl(type, xobj, contextNode, this.m_xpath);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\domapi\XPathExpressionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */