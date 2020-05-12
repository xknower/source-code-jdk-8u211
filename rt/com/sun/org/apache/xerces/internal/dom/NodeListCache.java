/*    */ package com.sun.org.apache.xerces.internal.dom;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NodeListCache
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7927529254918631002L;
/* 41 */   int fLength = -1;
/*    */ 
/*    */   
/* 44 */   int fChildIndex = -1;
/*    */ 
/*    */   
/*    */   ChildNode fChild;
/*    */ 
/*    */   
/*    */   ParentNode fOwner;
/*    */ 
/*    */   
/*    */   NodeListCache next;
/*    */ 
/*    */   
/*    */   NodeListCache(ParentNode owner) {
/* 57 */     this.fOwner = owner;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\dom\NodeListCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */