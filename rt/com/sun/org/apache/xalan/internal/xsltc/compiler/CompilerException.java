/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
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
/*    */ public final class CompilerException
/*    */   extends Exception
/*    */ {
/*    */   static final long serialVersionUID = 1732939618562742663L;
/*    */   private String _msg;
/*    */   
/*    */   public CompilerException() {}
/*    */   
/*    */   public CompilerException(Exception e) {
/* 39 */     super(e.toString());
/* 40 */     this._msg = e.toString();
/*    */   }
/*    */   
/*    */   public CompilerException(String message) {
/* 44 */     super(message);
/* 45 */     this._msg = message;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 49 */     int col = this._msg.indexOf(':');
/*    */     
/* 51 */     if (col > -1) {
/* 52 */       return this._msg.substring(col);
/*    */     }
/* 54 */     return this._msg;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xalan\internal\xsltc\compiler\CompilerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */