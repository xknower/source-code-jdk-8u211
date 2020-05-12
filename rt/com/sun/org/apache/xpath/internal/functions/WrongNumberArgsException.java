/*    */ package com.sun.org.apache.xpath.internal.functions;
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
/*    */ public class WrongNumberArgsException
/*    */   extends Exception
/*    */ {
/*    */   static final long serialVersionUID = -4551577097576242432L;
/*    */   
/*    */   public WrongNumberArgsException(String argsExpected) {
/* 43 */     super(argsExpected);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\functions\WrongNumberArgsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */