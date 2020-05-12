/*    */ package com.sun.xml.internal.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnmarshallerChain
/*    */ {
/* 48 */   private int offset = 0;
/*    */   
/*    */   public final JAXBContextImpl context;
/*    */   
/*    */   public UnmarshallerChain(JAXBContextImpl context) {
/* 53 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int allocateOffset() {
/* 60 */     return this.offset++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getScopeSize() {
/* 67 */     return this.offset;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\property\UnmarshallerChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */