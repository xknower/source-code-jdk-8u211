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
/*    */ class JDMTrapCommunity
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected String community = "";
/*    */   JDMTrapCommunity(int paramInt) {
/* 34 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMTrapCommunity(Parser paramParser, int paramInt) {
/* 38 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 42 */     return new JDMTrapCommunity(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 46 */     return new JDMTrapCommunity(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public String getCommunity() {
/* 50 */     return this.community;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMTrapCommunity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */