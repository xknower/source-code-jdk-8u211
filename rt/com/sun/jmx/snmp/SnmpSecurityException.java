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
/*    */ 
/*    */ 
/*    */ public class SnmpSecurityException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 5574448147432833480L;
/* 39 */   public SnmpVarBind[] list = null;
/*    */ 
/*    */ 
/*    */   
/* 43 */   public int status = 242;
/*    */ 
/*    */ 
/*    */   
/* 47 */   public SnmpSecurityParameters params = null;
/*    */ 
/*    */ 
/*    */   
/* 51 */   public byte[] contextEngineId = null;
/*    */ 
/*    */ 
/*    */   
/* 55 */   public byte[] contextName = null;
/*    */ 
/*    */ 
/*    */   
/* 59 */   public byte flags = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpSecurityException(String paramString) {
/* 65 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpSecurityException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */