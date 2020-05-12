/*    */ package com.sun.jmx.snmp.agent;
/*    */ 
/*    */ import com.sun.jmx.snmp.SnmpOid;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SnmpEntryOid
/*    */   extends SnmpOid
/*    */ {
/*    */   private static final long serialVersionUID = 9212653887791059564L;
/*    */   
/*    */   public SnmpEntryOid(long[] paramArrayOflong, int paramInt) {
/* 45 */     int i = paramArrayOflong.length - paramInt;
/* 46 */     long[] arrayOfLong = new long[i];
/* 47 */     System.arraycopy(paramArrayOflong, paramInt, arrayOfLong, 0, i);
/* 48 */     this.components = arrayOfLong;
/* 49 */     this.componentCount = i;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpEntryOid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */