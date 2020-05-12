/*    */ package com.sun.corba.se.impl.orbutil;
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
/*    */ public abstract class RepositoryIdFactory
/*    */ {
/* 33 */   private static final RepIdDelegator currentDelegator = new RepIdDelegator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RepositoryIdStrings getRepIdStringsFactory() {
/* 41 */     return currentDelegator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RepositoryIdUtility getRepIdUtility() {
/* 49 */     return currentDelegator;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\orbutil\RepositoryIdFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */