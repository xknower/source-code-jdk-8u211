/*    */ package com.sun.org.apache.xpath.internal.operations;
/*    */ 
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
/*    */ 
/*    */ public class String
/*    */   extends UnaryOperation
/*    */ {
/*    */   static final long serialVersionUID = 2973374377453022888L;
/*    */   
/*    */   public XObject operate(XObject right) throws TransformerException {
/* 47 */     return (XString)right.xstr();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\operations\String.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */