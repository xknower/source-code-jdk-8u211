/*    */ package com.sun.jndi.ldap.pool;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ConnectionsRef
/*    */ {
/*    */   private final Connections conns;
/*    */   
/*    */   ConnectionsRef(Connections paramConnections) {
/* 52 */     this.conns = paramConnections;
/*    */   }
/*    */   
/*    */   Connections getConnections() {
/* 56 */     return this.conns;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\pool\ConnectionsRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */