/*     */ package sun.management.snmp.jvmmib;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.SnmpString;
/*     */ import com.sun.jmx.snmp.SnmpValue;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibEntry;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibNode;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibSubRequest;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardMetaServer;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmRTInputArgsEntryMeta
/*     */   extends SnmpMibEntry
/*     */   implements Serializable, SnmpStandardMetaServer
/*     */ {
/*     */   static final long serialVersionUID = -7729576810347358025L;
/*     */   protected JvmRTInputArgsEntryMBean node;
/*     */   protected SnmpStandardObjectServer objectserver;
/*     */   
/*     */   public JvmRTInputArgsEntryMeta(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 249 */     this.objectserver = null;
/*     */     this.objectserver = paramSnmpStandardObjectServer;
/*     */     this.varList = new int[1];
/*     */     this.varList[0] = 2;
/*     */     SnmpMibNode.sort(this.varList);
/*     */   }
/*     */   
/*     */   public SnmpValue get(long paramLong, Object paramObject) throws SnmpStatusException {
/*     */     switch ((int)paramLong) {
/*     */       case 2:
/*     */         return new SnmpString(this.node.getJvmRTInputArgsItem());
/*     */       case 1:
/*     */         throw new SnmpStatusException(224);
/*     */     } 
/*     */     throw new SnmpStatusException(225);
/*     */   }
/*     */   
/*     */   public SnmpValue set(SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException {
/*     */     switch ((int)paramLong) {
/*     */       case 2:
/*     */         throw new SnmpStatusException(17);
/*     */       case 1:
/*     */         throw new SnmpStatusException(17);
/*     */     } 
/*     */     throw new SnmpStatusException(17);
/*     */   }
/*     */   
/*     */   public void check(SnmpValue paramSnmpValue, long paramLong, Object paramObject) throws SnmpStatusException {
/*     */     switch ((int)paramLong) {
/*     */       case 2:
/*     */         throw new SnmpStatusException(17);
/*     */       case 1:
/*     */         throw new SnmpStatusException(17);
/*     */     } 
/*     */     throw new SnmpStatusException(17);
/*     */   }
/*     */   
/*     */   protected void setInstance(JvmRTInputArgsEntryMBean paramJvmRTInputArgsEntryMBean) {
/*     */     this.node = paramJvmRTInputArgsEntryMBean;
/*     */   }
/*     */   
/*     */   public void get(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*     */     this.objectserver.get(this, paramSnmpMibSubRequest, paramInt);
/*     */   }
/*     */   
/*     */   public void set(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*     */     this.objectserver.set(this, paramSnmpMibSubRequest, paramInt);
/*     */   }
/*     */   
/*     */   public void check(SnmpMibSubRequest paramSnmpMibSubRequest, int paramInt) throws SnmpStatusException {
/*     */     this.objectserver.check(this, paramSnmpMibSubRequest, paramInt);
/*     */   }
/*     */   
/*     */   public boolean isVariable(long paramLong) {
/*     */     switch ((int)paramLong) {
/*     */       case 1:
/*     */       case 2:
/*     */         return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean isReadable(long paramLong) {
/*     */     switch ((int)paramLong) {
/*     */       case 2:
/*     */         return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean skipVariable(long paramLong, Object paramObject, int paramInt) {
/*     */     switch ((int)paramLong) {
/*     */       case 1:
/*     */         return true;
/*     */     } 
/*     */     return super.skipVariable(paramLong, paramObject, paramInt);
/*     */   }
/*     */   
/*     */   public String getAttributeName(long paramLong) throws SnmpStatusException {
/*     */     switch ((int)paramLong) {
/*     */       case 2:
/*     */         return "JvmRTInputArgsItem";
/*     */       case 1:
/*     */         return "JvmRTInputArgsIndex";
/*     */     } 
/*     */     throw new SnmpStatusException(225);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JvmRTInputArgsEntryMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */