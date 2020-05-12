/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*    */ import com.sun.org.apache.xpath.internal.objects.XString;
/*    */ import javax.xml.transform.TransformerException;
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
/*    */ public class FuncUnparsedEntityURI
/*    */   extends FunctionOneArg
/*    */ {
/*    */   static final long serialVersionUID = 845309759097448178L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 48 */     String name = this.m_arg0.execute(xctxt).str();
/* 49 */     int context = xctxt.getCurrentNode();
/* 50 */     DTM dtm = xctxt.getDTM(context);
/* 51 */     int doc = dtm.getDocument();
/*    */     
/* 53 */     String uri = dtm.getUnparsedEntityURI(name);
/*    */     
/* 55 */     return new XString(uri);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\functions\FuncUnparsedEntityURI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */