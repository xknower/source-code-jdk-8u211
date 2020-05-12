/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
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
/*    */ 
/*    */ public class FuncTranslate
/*    */   extends Function3Args
/*    */ {
/*    */   static final long serialVersionUID = -1672834340026116482L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 48 */     String theFirstString = this.m_arg0.execute(xctxt).str();
/* 49 */     String theSecondString = this.m_arg1.execute(xctxt).str();
/* 50 */     String theThirdString = this.m_arg2.execute(xctxt).str();
/* 51 */     int theFirstStringLength = theFirstString.length();
/* 52 */     int theThirdStringLength = theThirdString.length();
/*    */ 
/*    */ 
/*    */     
/* 56 */     StringBuffer sbuffer = new StringBuffer();
/*    */     
/* 58 */     for (int i = 0; i < theFirstStringLength; i++) {
/*    */       
/* 60 */       char theCurrentChar = theFirstString.charAt(i);
/* 61 */       int theIndex = theSecondString.indexOf(theCurrentChar);
/*    */       
/* 63 */       if (theIndex < 0) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 68 */         sbuffer.append(theCurrentChar);
/*    */       }
/* 70 */       else if (theIndex < theThirdStringLength) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 75 */         sbuffer.append(theThirdString.charAt(theIndex));
/*    */       } 
/*    */     } 
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
/* 88 */     return new XString(sbuffer.toString());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\functions\FuncTranslate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */