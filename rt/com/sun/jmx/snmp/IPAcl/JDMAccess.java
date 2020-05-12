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
/*    */ 
/*    */ class JDMAccess
/*    */   extends SimpleNode
/*    */ {
/* 33 */   protected int access = -1;
/*    */   
/*    */   JDMAccess(int paramInt) {
/* 36 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMAccess(Parser paramParser, int paramInt) {
/* 40 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 44 */     return new JDMAccess(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 48 */     return new JDMAccess(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   protected void putPermission(AclEntryImpl paramAclEntryImpl) {
/* 52 */     if (this.access == 17)
/*    */     {
/*    */       
/* 55 */       paramAclEntryImpl.addPermission(SnmpAcl.getREAD());
/*    */     }
/* 57 */     if (this.access == 18) {
/*    */ 
/*    */       
/* 60 */       paramAclEntryImpl.addPermission(SnmpAcl.getREAD());
/* 61 */       paramAclEntryImpl.addPermission(SnmpAcl.getWRITE());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */