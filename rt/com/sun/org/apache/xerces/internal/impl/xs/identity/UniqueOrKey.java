/*    */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
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
/*    */ public class UniqueOrKey
/*    */   extends IdentityConstraint
/*    */ {
/*    */   public UniqueOrKey(String namespace, String identityConstraintName, String elemName, short type) {
/* 43 */     super(namespace, identityConstraintName, elemName);
/* 44 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\xs\identity\UniqueOrKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */