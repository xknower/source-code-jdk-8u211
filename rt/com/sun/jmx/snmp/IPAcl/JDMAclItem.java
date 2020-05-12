/*    */ package com.sun.jmx.snmp.IPAcl;
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
/*    */ class JDMAclItem
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected JDMAccess access = null;
/* 33 */   protected JDMCommunities com = null;
/*    */   
/*    */   JDMAclItem(int paramInt) {
/* 36 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMAclItem(Parser paramParser, int paramInt) {
/* 40 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 44 */     return new JDMAclItem(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 48 */     return new JDMAclItem(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public JDMAccess getAccess() {
/* 52 */     return this.access;
/*    */   }
/*    */   
/*    */   public JDMCommunities getCommunities() {
/* 56 */     return this.com;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMAclItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */