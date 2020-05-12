/*     */ package com.sun.jmx.snmp.agent;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpEngine;
/*     */ import com.sun.jmx.snmp.SnmpPdu;
/*     */ import com.sun.jmx.snmp.SnmpVarBind;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SnmpMibRequestImpl
/*     */   implements SnmpMibRequest
/*     */ {
/*     */   private Vector<SnmpVarBind> varbinds;
/*     */   private int version;
/*     */   private Object data;
/*     */   private SnmpPdu reqPdu;
/*     */   private SnmpRequestTree tree;
/*     */   private SnmpEngine engine;
/*     */   private String principal;
/*     */   private int securityLevel;
/*     */   private int securityModel;
/*     */   private byte[] contextName;
/*     */   private byte[] accessContextName;
/*     */   
/*     */   public SnmpMibRequestImpl(SnmpEngine paramSnmpEngine, SnmpPdu paramSnmpPdu, Vector<SnmpVarBind> paramVector, int paramInt1, Object paramObject, String paramString, int paramInt2, int paramInt3, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 248 */     this.reqPdu = null;
/*     */     
/* 250 */     this.tree = null;
/* 251 */     this.engine = null;
/* 252 */     this.principal = null;
/* 253 */     this.securityLevel = -1;
/* 254 */     this.securityModel = -1;
/* 255 */     this.contextName = null;
/* 256 */     this.accessContextName = null;
/*     */     this.varbinds = paramVector;
/*     */     this.version = paramInt1;
/*     */     this.data = paramObject;
/*     */     this.reqPdu = paramSnmpPdu;
/*     */     this.engine = paramSnmpEngine;
/*     */     this.principal = paramString;
/*     */     this.securityLevel = paramInt2;
/*     */     this.securityModel = paramInt3;
/*     */     this.contextName = paramArrayOfbyte1;
/*     */     this.accessContextName = paramArrayOfbyte2;
/*     */   }
/*     */   
/*     */   public SnmpEngine getEngine() {
/*     */     return this.engine;
/*     */   }
/*     */   
/*     */   public String getPrincipal() {
/*     */     return this.principal;
/*     */   }
/*     */   
/*     */   public int getSecurityLevel() {
/*     */     return this.securityLevel;
/*     */   }
/*     */   
/*     */   public int getSecurityModel() {
/*     */     return this.securityModel;
/*     */   }
/*     */   
/*     */   public byte[] getContextName() {
/*     */     return this.contextName;
/*     */   }
/*     */   
/*     */   public byte[] getAccessContextName() {
/*     */     return this.accessContextName;
/*     */   }
/*     */   
/*     */   public final SnmpPdu getPdu() {
/*     */     return this.reqPdu;
/*     */   }
/*     */   
/*     */   public final Enumeration<SnmpVarBind> getElements() {
/*     */     return this.varbinds.elements();
/*     */   }
/*     */   
/*     */   public final Vector<SnmpVarBind> getSubList() {
/*     */     return this.varbinds;
/*     */   }
/*     */   
/*     */   public final int getSize() {
/*     */     if (this.varbinds == null)
/*     */       return 0; 
/*     */     return this.varbinds.size();
/*     */   }
/*     */   
/*     */   public final int getVersion() {
/*     */     return this.version;
/*     */   }
/*     */   
/*     */   public final int getRequestPduVersion() {
/*     */     return this.reqPdu.version;
/*     */   }
/*     */   
/*     */   public final Object getUserData() {
/*     */     return this.data;
/*     */   }
/*     */   
/*     */   public final int getVarIndex(SnmpVarBind paramSnmpVarBind) {
/*     */     return this.varbinds.indexOf(paramSnmpVarBind);
/*     */   }
/*     */   
/*     */   public void addVarBind(SnmpVarBind paramSnmpVarBind) {
/*     */     this.varbinds.addElement(paramSnmpVarBind);
/*     */   }
/*     */   
/*     */   final void setRequestTree(SnmpRequestTree paramSnmpRequestTree) {
/*     */     this.tree = paramSnmpRequestTree;
/*     */   }
/*     */   
/*     */   final SnmpRequestTree getRequestTree() {
/*     */     return this.tree;
/*     */   }
/*     */   
/*     */   final Vector<SnmpVarBind> getVarbinds() {
/*     */     return this.varbinds;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\agent\SnmpMibRequestImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */