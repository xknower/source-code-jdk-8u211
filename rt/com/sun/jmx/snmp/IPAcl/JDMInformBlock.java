/*    */ package com.sun.jmx.snmp.IPAcl;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Vector;
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
/*    */ class JDMInformBlock
/*    */   extends SimpleNode
/*    */ {
/*    */   JDMInformBlock(int paramInt) {
/* 36 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMInformBlock(Parser paramParser, int paramInt) {
/* 40 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 44 */     return new JDMInformBlock(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 48 */     return new JDMInformBlock(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public void buildAclEntries(PrincipalImpl paramPrincipalImpl, AclImpl paramAclImpl) {}
/*    */   
/*    */   public void buildTrapEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMInformBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */