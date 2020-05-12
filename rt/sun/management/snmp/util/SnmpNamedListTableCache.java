/*     */ package sun.management.snmp.util;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public abstract class SnmpNamedListTableCache
/*     */   extends SnmpListTableCache
/*     */ {
/*  58 */   protected TreeMap<String, SnmpOid> names = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected long last = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean wrapped = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getKey(Object paramObject1, List<?> paramList, int paramInt, Object paramObject2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid makeIndex(Object paramObject1, List<?> paramList, int paramInt, Object paramObject2) {
/* 104 */     if (++this.last > 4294967295L) {
/*     */       
/* 106 */       log.debug("makeIndex", "Index wrapping...");
/* 107 */       this.last = 0L;
/* 108 */       this.wrapped = true;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (!this.wrapped) return new SnmpOid(this.last);
/*     */ 
/*     */     
/* 115 */     for (byte b = 1; b < 4294967295L; b++) {
/* 116 */       if (++this.last > 4294967295L) this.last = 1L; 
/* 117 */       SnmpOid snmpOid = new SnmpOid(this.last);
/*     */ 
/*     */       
/* 120 */       if (this.names == null) return snmpOid; 
/* 121 */       if (!this.names.containsValue(snmpOid)) {
/*     */ 
/*     */         
/* 124 */         if (paramObject1 == null) return snmpOid; 
/* 125 */         if (!((Map)paramObject1).containsValue(snmpOid))
/*     */         {
/*     */           
/* 128 */           return snmpOid;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getIndex(Object paramObject1, List<?> paramList, int paramInt, Object paramObject2) {
/* 156 */     String str = getKey(paramObject1, paramList, paramInt, paramObject2);
/* 157 */     V v = (this.names == null || str == null) ? null : (V)this.names.get(str);
/*     */     
/* 159 */     SnmpOid snmpOid = (v != null) ? (SnmpOid)v : makeIndex(paramObject1, paramList, paramInt, paramObject2);
/*     */     
/* 161 */     if (paramObject1 != null && str != null && snmpOid != null) {
/* 162 */       Map<String, SnmpOid> map = Util.<Map>cast(paramObject1);
/* 163 */       map.put(str, snmpOid);
/*     */     } 
/* 165 */     log.debug("getIndex", "key=" + str + ", index=" + snmpOid);
/* 166 */     return snmpOid;
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
/*     */   protected SnmpCachedData updateCachedDatas(Object paramObject, List<?> paramList) {
/* 178 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*     */     
/* 180 */     SnmpCachedData snmpCachedData = super.updateCachedDatas(paramObject, paramList);
/* 181 */     this.names = (TreeMap)treeMap;
/* 182 */     return snmpCachedData;
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
/*     */   protected abstract List<?> loadRawDatas(Map<Object, Object> paramMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getRawDatasKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<?> getRawDatas(Map<Object, Object> paramMap, String paramString) {
/* 216 */     List<?> list = null;
/*     */ 
/*     */     
/* 219 */     if (paramMap != null) {
/* 220 */       list = (List)paramMap.get(paramString);
/*     */     }
/* 222 */     if (list == null) {
/*     */       
/* 224 */       list = loadRawDatas(paramMap);
/*     */ 
/*     */ 
/*     */       
/* 228 */       if (list != null && paramMap != null) {
/* 229 */         paramMap.put(paramString, list);
/*     */       }
/*     */     } 
/* 232 */     return list;
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
/*     */   protected SnmpCachedData updateCachedDatas(Object paramObject) {
/* 250 */     Map<Object, Object> map = (paramObject instanceof Map) ? Util.<Map>cast(paramObject) : null;
/*     */ 
/*     */     
/* 253 */     List<?> list = getRawDatas(map, getRawDatasKey());
/*     */     
/* 255 */     log.debug("updateCachedDatas", "rawDatas.size()=" + ((list == null) ? "<no data>" : ("" + list
/* 256 */         .size())));
/*     */     
/* 258 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*     */     
/* 260 */     SnmpCachedData snmpCachedData = super.updateCachedDatas(treeMap, list);
/* 261 */     this.names = (TreeMap)treeMap;
/* 262 */     return snmpCachedData;
/*     */   }
/*     */   
/* 265 */   static final MibLogger log = new MibLogger(SnmpNamedListTableCache.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snm\\util\SnmpNamedListTableCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */