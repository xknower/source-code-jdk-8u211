/*    */ package org.w3c.dom.xpath;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XPathException
/*    */   extends RuntimeException
/*    */ {
/*    */   public short code;
/*    */   public static final short INVALID_EXPRESSION_ERR = 1;
/*    */   public static final short TYPE_ERR = 2;
/*    */   
/*    */   public XPathException(short code, String message) {
/* 51 */     super(message);
/* 52 */     this.code = code;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\w3c\dom\xpath\XPathException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */