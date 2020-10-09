/*     */ package sun.management.snmp.util;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SnmpListTableCache
/*     */   extends SnmpTableCache
/*     */ {
/*     */   protected abstract SnmpOid getIndex(Object paramObject1, List<?> paramList, int paramInt, Object paramObject2);
/*     */   
/*     */   protected Object getData(Object paramObject1, List<?> paramList, int paramInt, Object paramObject2) {
/*  80 */     return paramObject2;
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
/*     */   
/*     */   protected SnmpCachedData updateCachedDatas(Object paramObject, List<?> paramList) {
/*  99 */     boolean bool = (paramList == null) ? false : paramList.size();
/* 100 */     if (!bool) return null;
/*     */     
/* 102 */     long l = System.currentTimeMillis();
/* 103 */     Iterator<?> iterator = paramList.iterator();
/* 104 */     TreeMap<SnmpOid, Object> treeMap = new TreeMap<>(SnmpCachedData.oidComparator);
/*     */     
/* 106 */     for (byte b = 0; iterator.hasNext(); b++) {
/* 107 */       Object object1 = iterator.next();
/* 108 */       SnmpOid snmpOid = getIndex(paramObject, paramList, b, object1);
/* 109 */       Object object2 = getData(paramObject, paramList, b, object1);
/* 110 */       if (snmpOid != null) {
/* 111 */         treeMap.put(snmpOid, object2);
/*     */       }
/*     */     } 
/* 114 */     return new SnmpCachedData(l, treeMap);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snm\\util\SnmpListTableCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */