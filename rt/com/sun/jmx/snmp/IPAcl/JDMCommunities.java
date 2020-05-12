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
/*    */ 
/*    */ class JDMCommunities
/*    */   extends SimpleNode
/*    */ {
/*    */   JDMCommunities(int paramInt) {
/* 34 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMCommunities(Parser paramParser, int paramInt) {
/* 38 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 42 */     return new JDMCommunities(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 46 */     return new JDMCommunities(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public void buildCommunities(AclEntryImpl paramAclEntryImpl) {
/* 50 */     for (byte b = 0; b < this.children.length; b++)
/* 51 */       paramAclEntryImpl.addCommunity(((JDMCommunity)this.children[b]).getCommunity()); 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMCommunities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */