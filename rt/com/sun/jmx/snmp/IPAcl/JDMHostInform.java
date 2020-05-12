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
/*    */ class JDMHostInform
/*    */   extends SimpleNode
/*    */ {
/* 31 */   protected String name = "";
/*    */   
/*    */   JDMHostInform(int paramInt) {
/* 34 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMHostInform(Parser paramParser, int paramInt) {
/* 38 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 42 */     return new JDMHostInform(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 46 */     return new JDMHostInform(paramParser, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMHostInform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */