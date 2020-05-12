/*    */ package com.sun.org.apache.xerces.internal.xinclude;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
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
/*    */ public class XIncludeNamespaceSupport
/*    */   extends MultipleScopeNamespaceSupport
/*    */ {
/* 38 */   private boolean[] fValidContext = new boolean[8];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XIncludeNamespaceSupport() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XIncludeNamespaceSupport(NamespaceContext context) {
/* 51 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushContext() {
/* 58 */     super.pushContext();
/* 59 */     if (this.fCurrentContext + 1 == this.fValidContext.length) {
/* 60 */       boolean[] contextarray = new boolean[this.fValidContext.length * 2];
/* 61 */       System.arraycopy(this.fValidContext, 0, contextarray, 0, this.fValidContext.length);
/* 62 */       this.fValidContext = contextarray;
/*    */     } 
/*    */     
/* 65 */     this.fValidContext[this.fCurrentContext] = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setContextInvalid() {
/* 76 */     this.fValidContext[this.fCurrentContext] = false;
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
/*    */   public String getURIFromIncludeParent(String prefix) {
/* 89 */     int lastValidContext = this.fCurrentContext - 1;
/* 90 */     while (lastValidContext > 0 && !this.fValidContext[lastValidContext]) {
/* 91 */       lastValidContext--;
/*    */     }
/* 93 */     return getURI(prefix, lastValidContext);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\xinclude\XIncludeNamespaceSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */