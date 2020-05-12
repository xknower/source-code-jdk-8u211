/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import java.util.Stack;
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
/*    */ public final class StringStack
/*    */   extends Stack
/*    */ {
/*    */   static final long serialVersionUID = -1506910875640317898L;
/*    */   
/*    */   public String peekString() {
/* 35 */     return (String)peek();
/*    */   }
/*    */   
/*    */   public String popString() {
/* 39 */     return (String)pop();
/*    */   }
/*    */   
/*    */   public String pushString(String val) {
/* 43 */     return (String)push((E)val);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compile\\util\StringStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */