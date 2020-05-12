/*    */ package com.sun.jmx.snmp.IPAcl;
/*    */ 
/*    */ import java.net.UnknownHostException;
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
/*    */ class JDMHostName
/*    */   extends Host
/*    */ {
/*    */   private static final long serialVersionUID = -9120082068923591122L;
/* 36 */   protected StringBuffer name = new StringBuffer();
/*    */   
/*    */   JDMHostName(int paramInt) {
/* 39 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMHostName(Parser paramParser, int paramInt) {
/* 43 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 47 */     return new JDMHostName(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 51 */     return new JDMHostName(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   protected String getHname() {
/* 55 */     return this.name.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   protected PrincipalImpl createAssociatedPrincipal() throws UnknownHostException {
/* 60 */     return new PrincipalImpl(this.name.toString());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMHostName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */