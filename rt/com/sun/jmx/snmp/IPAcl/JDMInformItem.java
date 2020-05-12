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
/*    */ class JDMInformItem
/*    */   extends SimpleNode
/*    */ {
/* 31 */   protected JDMInformCommunity comm = null;
/*    */   JDMInformItem(int paramInt) {
/* 33 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMInformItem(Parser paramParser, int paramInt) {
/* 37 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 41 */     return new JDMInformItem(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 45 */     return new JDMInformItem(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public JDMInformCommunity getCommunity() {
/* 49 */     return this.comm;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMInformItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */