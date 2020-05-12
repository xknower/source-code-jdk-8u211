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
/*    */ 
/*    */ class JDMIpAddress
/*    */   extends Host
/*    */ {
/*    */   private static final long serialVersionUID = 849729919486384484L;
/* 37 */   protected StringBuffer address = new StringBuffer();
/*    */   
/*    */   JDMIpAddress(int paramInt) {
/* 40 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMIpAddress(Parser paramParser, int paramInt) {
/* 44 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 48 */     return new JDMIpAddress(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 52 */     return new JDMIpAddress(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   protected String getHname() {
/* 56 */     return this.address.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   protected PrincipalImpl createAssociatedPrincipal() throws UnknownHostException {
/* 61 */     return new PrincipalImpl(this.address.toString());
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMIpAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */