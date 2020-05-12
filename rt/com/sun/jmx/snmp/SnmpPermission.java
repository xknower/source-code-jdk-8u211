/*    */ package com.sun.jmx.snmp;
/*    */ 
/*    */ import java.security.BasicPermission;
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
/*    */ public class SnmpPermission
/*    */   extends BasicPermission
/*    */ {
/*    */   public SnmpPermission(String paramString) {
/* 25 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SnmpPermission(String paramString1, String paramString2) {
/* 35 */     super(paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */