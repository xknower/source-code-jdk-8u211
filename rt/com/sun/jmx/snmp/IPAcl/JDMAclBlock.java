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
/*    */ 
/*    */ class JDMAclBlock
/*    */   extends SimpleNode
/*    */ {
/*    */   JDMAclBlock(int paramInt) {
/* 37 */     super(paramInt);
/*    */   }
/*    */   
/*    */   JDMAclBlock(Parser paramParser, int paramInt) {
/* 41 */     super(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(int paramInt) {
/* 45 */     return new JDMAclBlock(paramInt);
/*    */   }
/*    */   
/*    */   public static Node jjtCreate(Parser paramParser, int paramInt) {
/* 49 */     return new JDMAclBlock(paramParser, paramInt);
/*    */   }
/*    */   
/*    */   public void buildTrapEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {}
/*    */   
/*    */   public void buildInformEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {}
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\JDMAclBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */