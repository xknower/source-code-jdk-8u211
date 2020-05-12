/*    */ package com.sun.jmx.snmp;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SnmpParams
/*    */   implements SnmpDefinitions
/*    */ {
/* 36 */   private int protocolVersion = 0;
/*    */   SnmpParams(int paramInt) {
/* 38 */     this.protocolVersion = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   SnmpParams() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean allowSnmpSets();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProtocolVersion() {
/* 58 */     return this.protocolVersion;
/*    */   }
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
/*    */   public void setProtocolVersion(int paramInt) {
/* 74 */     this.protocolVersion = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */