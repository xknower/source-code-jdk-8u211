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
/*    */ class JDMTrapNum
/*    */   extends SimpleNode
/*    */ {
/* 32 */   protected int low = 0;
/* 33 */   protected int high = 0;
/*    */   
/*    */   JDMTrapNum(int paramInt) {
/* 36 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMTrapNum(Parser paramParser, int paramInt) {
/* 40 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 44 */     return new JDMTrapNum(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 48 */     return new JDMTrapNum(paramParser, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMTrapNum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */