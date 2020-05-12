/*    */ package com.sun.jmx.snmp.daemon;
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
/*    */ final class SnmpRequestCounter
/*    */ {
/* 22 */   int reqid = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized int getNewId() {
/* 31 */     if (++this.reqid < 0)
/* 32 */       this.reqid = 1; 
/* 33 */     return this.reqid;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpRequestCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */