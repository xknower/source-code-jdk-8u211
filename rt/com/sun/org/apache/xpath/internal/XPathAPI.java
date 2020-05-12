/*     */ package com.sun.org.apache.xpath.internal;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolver;
/*     */ import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathAPI
/*     */ {
/*     */   public static Node selectSingleNode(Node contextNode, String str) throws TransformerException {
/*  71 */     return selectSingleNode(contextNode, str, contextNode);
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
/*     */   public static Node selectSingleNode(Node contextNode, String str, Node namespaceNode) throws TransformerException {
/*  91 */     NodeIterator nl = selectNodeIterator(contextNode, str, namespaceNode);
/*     */ 
/*     */     
/*  94 */     return nl.nextNode();
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
/*     */   public static NodeIterator selectNodeIterator(Node contextNode, String str) throws TransformerException {
/* 110 */     return selectNodeIterator(contextNode, str, contextNode);
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
/*     */   public static NodeIterator selectNodeIterator(Node contextNode, String str, Node namespaceNode) throws TransformerException {
/* 130 */     XObject list = eval(contextNode, str, namespaceNode);
/*     */ 
/*     */     
/* 133 */     return list.nodeset();
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
/*     */   public static NodeList selectNodeList(Node contextNode, String str) throws TransformerException {
/* 149 */     return selectNodeList(contextNode, str, contextNode);
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
/*     */   public static NodeList selectNodeList(Node contextNode, String str, Node namespaceNode) throws TransformerException {
/* 169 */     XObject list = eval(contextNode, str, namespaceNode);
/*     */ 
/*     */     
/* 172 */     return list.nodelist();
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
/*     */   public static XObject eval(Node contextNode, String str) throws TransformerException {
/* 193 */     return eval(contextNode, str, contextNode);
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
/*     */   public static XObject eval(Node contextNode, String str, Node namespaceNode) throws TransformerException {
/* 225 */     XPathContext xpathSupport = new XPathContext(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     PrefixResolverDefault prefixResolver = new PrefixResolverDefault((namespaceNode.getNodeType() == 9) ? ((Document)namespaceNode).getDocumentElement() : namespaceNode);
/*     */ 
/*     */     
/* 236 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/*     */ 
/*     */ 
/*     */     
/* 240 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/*     */     
/* 242 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
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
/*     */   public static XObject eval(Node contextNode, String str, PrefixResolver prefixResolver) throws TransformerException {
/* 277 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/*     */ 
/*     */     
/* 280 */     XPathContext xpathSupport = new XPathContext(JdkXmlUtils.OVERRIDE_PARSER_DEFAULT);
/* 281 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/*     */     
/* 283 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\XPathAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */