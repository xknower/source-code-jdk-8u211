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
/*    */ class JDMTrapItem
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected JDMTrapCommunity comm = null;
/*    */   
/*    */   JDMTrapItem(int paramInt) {
/* 35 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMTrapItem(Parser paramParser, int paramInt) {
/* 39 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 43 */     return new JDMTrapItem(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 47 */     return new JDMTrapItem(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public JDMTrapCommunity getCommunity() {
/* 51 */     return this.comm;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMTrapItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */