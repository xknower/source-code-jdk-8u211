/*    */ package com.sun.org.apache.xpath.internal.operations;
/*    */ 
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XBoolean;
/*    */ import com.sun.org.apache.xpath.internal.objects.XObject;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class And
/*    */   extends Operation
/*    */ {
/*    */   static final long serialVersionUID = 392330077126534022L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 50 */     XObject expr1 = this.m_left.execute(xctxt);
/*    */     
/* 52 */     if (expr1.bool()) {
/*    */       
/* 54 */       XObject expr2 = this.m_right.execute(xctxt);
/*    */       
/* 56 */       return expr2.bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/*    */     } 
/*    */     
/* 59 */     return XBoolean.S_FALSE;
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
/*    */ 
/*    */   
/*    */   public boolean bool(XPathContext xctxt) throws TransformerException {
/* 74 */     return (this.m_left.bool(xctxt) && this.m_right.bool(xctxt));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\operations\And.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */