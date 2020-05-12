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
/*    */ class JDMHostTrap
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected String name = "";
/*    */   
/*    */   JDMHostTrap(int paramInt) {
/* 35 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMHostTrap(Parser paramParser, int paramInt) {
/* 39 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 43 */     return new JDMHostTrap(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 47 */     return new JDMHostTrap(paramParser, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMHostTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */