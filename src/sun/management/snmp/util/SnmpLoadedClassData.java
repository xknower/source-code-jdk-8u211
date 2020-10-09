/*     */ package sun.management.snmp.util;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SnmpLoadedClassData
/*     */   extends SnmpCachedData
/*     */ {
/*     */   public SnmpLoadedClassData(long paramLong, TreeMap<SnmpOid, Object> paramTreeMap) {
/*  56 */     super(paramLong, paramTreeMap, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object getData(SnmpOid paramSnmpOid) {
/*  62 */     int i = 0;
/*     */     
/*     */     try {
/*  65 */       i = (int)paramSnmpOid.getOidArc(0);
/*  66 */     } catch (SnmpStatusException snmpStatusException) {
/*  67 */       return null;
/*     */     } 
/*     */     
/*  70 */     if (i >= this.datas.length) return null; 
/*  71 */     return this.datas[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public final SnmpOid getNext(SnmpOid paramSnmpOid) {
/*  76 */     int i = 0;
/*  77 */     if (paramSnmpOid == null && 
/*  78 */       this.datas != null && this.datas.length >= 1) {
/*  79 */       return new SnmpOid(0L);
/*     */     }
/*     */     try {
/*  82 */       i = (int)paramSnmpOid.getOidArc(0);
/*  83 */     } catch (SnmpStatusException snmpStatusException) {
/*  84 */       return null;
/*     */     } 
/*     */     
/*  87 */     if (i < this.datas.length - 1) {
/*  88 */       return new SnmpOid((i + 1));
/*     */     }
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean contains(SnmpOid paramSnmpOid) {
/*  95 */     int i = 0;
/*     */     
/*     */     try {
/*  98 */       i = (int)paramSnmpOid.getOidArc(0);
/*  99 */     } catch (SnmpStatusException snmpStatusException) {
/* 100 */       return false;
/*     */     } 
/*     */     
/* 103 */     return (i < this.datas.length);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snm\\util\SnmpLoadedClassData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */