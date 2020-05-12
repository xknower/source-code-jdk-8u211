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
/*    */ class JDMNetMaskV6
/*    */   extends JDMNetMask
/*    */ {
/*    */   private static final long serialVersionUID = 4505256777680576645L;
/*    */   
/*    */   public JDMNetMaskV6(int paramInt) {
/* 36 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public JDMNetMaskV6(Parser paramParser, int paramInt) {
/* 40 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   protected PrincipalImpl createAssociatedPrincipal() throws UnknownHostException {
/* 44 */     return new NetMaskImpl(this.address.toString(), Integer.parseInt(this.mask));
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMNetMaskV6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */