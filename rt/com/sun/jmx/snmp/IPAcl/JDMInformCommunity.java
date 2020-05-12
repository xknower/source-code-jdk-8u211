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
/*    */ class JDMInformCommunity
/*    */   extends SimpleNode
/*    */ {
/* 31 */   protected String community = "";
/*    */   JDMInformCommunity(int paramInt) {
/* 33 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMInformCommunity(Parser paramParser, int paramInt) {
/* 37 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 41 */     return new JDMInformCommunity(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 45 */     return new JDMInformCommunity(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public String getCommunity() {
/* 49 */     return this.community;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMInformCommunity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */