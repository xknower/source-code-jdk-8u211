/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.EnumConstant;
/*    */ import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class EnumConstantImpl<T, C, F, M>
/*    */   implements EnumConstant<T, C>
/*    */ {
/*    */   protected final String lexical;
/*    */   protected final EnumLeafInfoImpl<T, C, F, M> owner;
/*    */   protected final String name;
/*    */   protected final EnumConstantImpl<T, C, F, M> next;
/*    */   
/*    */   public EnumConstantImpl(EnumLeafInfoImpl<T, C, F, M> owner, String name, String lexical, EnumConstantImpl<T, C, F, M> next) {
/* 45 */     this.lexical = lexical;
/* 46 */     this.owner = owner;
/* 47 */     this.name = name;
/* 48 */     this.next = next;
/*    */   }
/*    */   
/*    */   public EnumLeafInfo<T, C> getEnclosingClass() {
/* 52 */     return this.owner;
/*    */   }
/*    */   
/*    */   public final String getLexicalValue() {
/* 56 */     return this.lexical;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 60 */     return this.name;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\model\impl\EnumConstantImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */