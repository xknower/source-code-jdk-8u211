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
/*    */ class JDMCommunity
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected String communityString = "";
/*    */   
/*    */   JDMCommunity(int paramInt) {
/* 35 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMCommunity(Parser paramParser, int paramInt) {
/* 39 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 43 */     return new JDMCommunity(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 47 */     return new JDMCommunity(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public String getCommunity() {
/* 51 */     return this.communityString;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMCommunity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */