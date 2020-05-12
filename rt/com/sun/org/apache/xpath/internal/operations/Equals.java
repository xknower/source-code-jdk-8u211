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
/*    */ public class Equals
/*    */   extends Operation
/*    */ {
/*    */   static final long serialVersionUID = -2658315633903426134L;
/*    */   
/*    */   public XObject operate(XObject left, XObject right) throws TransformerException {
/* 50 */     return left.equals(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
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
/*    */ 
/*    */   
/*    */   public boolean bool(XPathContext xctxt) throws TransformerException {
/* 67 */     XObject left = this.m_left.execute(xctxt, true);
/* 68 */     XObject right = this.m_right.execute(xctxt, true);
/*    */     
/* 70 */     boolean result = left.equals(right);
/* 71 */     left.detach();
/* 72 */     right.detach();
/* 73 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\operations\Equals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */