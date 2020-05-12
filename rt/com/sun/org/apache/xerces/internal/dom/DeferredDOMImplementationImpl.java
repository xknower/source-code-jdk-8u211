/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMImplementation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeferredDOMImplementationImpl
/*    */   extends DOMImplementationImpl
/*    */ {
/* 47 */   static DeferredDOMImplementationImpl singleton = new DeferredDOMImplementationImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DOMImplementation getDOMImplementation() {
/* 56 */     return singleton;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\DeferredDOMImplementationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */