/*     */ package sun.management.snmp.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SnmpTableCache
/*     */   implements Serializable
/*     */ {
/*     */   protected long validity;
/*     */   protected transient WeakReference<SnmpCachedData> datas;
/*     */   
/*     */   protected boolean isObsolete(SnmpCachedData paramSnmpCachedData) {
/*  65 */     if (paramSnmpCachedData == null) return true; 
/*  66 */     if (this.validity < 0L) return false; 
/*  67 */     return (System.currentTimeMillis() - paramSnmpCachedData.lastUpdated > this.validity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpCachedData getCachedDatas() {
/*  77 */     if (this.datas == null) return null; 
/*  78 */     SnmpCachedData snmpCachedData = this.datas.get();
/*  79 */     if (snmpCachedData == null || isObsolete(snmpCachedData)) return null; 
/*  80 */     return snmpCachedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized SnmpCachedData getTableDatas(Object paramObject) {
/*  98 */     SnmpCachedData snmpCachedData1 = getCachedDatas();
/*  99 */     if (snmpCachedData1 != null) return snmpCachedData1; 
/* 100 */     SnmpCachedData snmpCachedData2 = updateCachedDatas(paramObject);
/* 101 */     if (this.validity != 0L) this.datas = new WeakReference<>(snmpCachedData2); 
/* 102 */     return snmpCachedData2;
/*     */   }
/*     */   
/*     */   protected abstract SnmpCachedData updateCachedDatas(Object paramObject);
/*     */   
/*     */   public abstract SnmpTableHandler getTableHandler();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snm\\util\SnmpTableCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */