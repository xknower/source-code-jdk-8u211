/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.io.Serializable;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import sun.management.snmp.jvmmib.JvmMemMgrPoolRelTableMeta;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ import sun.management.snmp.util.SnmpCachedData;
/*     */ import sun.management.snmp.util.SnmpTableCache;
/*     */ import sun.management.snmp.util.SnmpTableHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmMemMgrPoolRelTableMetaImpl
/*     */   extends JvmMemMgrPoolRelTableMeta
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1896509775012355443L;
/*     */   protected SnmpTableCache cache;
/*     */   
/*     */   private static class JvmMemMgrPoolRelTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = 6059937161990659184L;
/*     */     private final JvmMemMgrPoolRelTableMetaImpl meta;
/*     */     
/*     */     JvmMemMgrPoolRelTableCache(JvmMemMgrPoolRelTableMetaImpl param1JvmMemMgrPoolRelTableMetaImpl, long param1Long) {
/*  86 */       this.validity = param1Long;
/*  87 */       this.meta = param1JvmMemMgrPoolRelTableMetaImpl;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/*  94 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*  95 */       return getTableDatas(map);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Map<String, SnmpOid> buildPoolIndexMap(SnmpTableHandler param1SnmpTableHandler) {
/* 104 */       if (param1SnmpTableHandler instanceof SnmpCachedData) {
/* 105 */         return buildPoolIndexMap((SnmpCachedData)param1SnmpTableHandler);
/*     */       }
/*     */       
/* 108 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 109 */       SnmpOid snmpOid = null;
/* 110 */       while ((snmpOid = param1SnmpTableHandler.getNext(snmpOid)) != null) {
/*     */         
/* 112 */         MemoryPoolMXBean memoryPoolMXBean = (MemoryPoolMXBean)param1SnmpTableHandler.getData(snmpOid);
/* 113 */         if (memoryPoolMXBean == null)
/* 114 */           continue;  String str = memoryPoolMXBean.getName();
/* 115 */         if (str == null)
/* 116 */           continue;  hashMap.put(str, snmpOid);
/*     */       } 
/* 118 */       return (Map)hashMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Map<String, SnmpOid> buildPoolIndexMap(SnmpCachedData param1SnmpCachedData) {
/* 127 */       if (param1SnmpCachedData == null) return Collections.emptyMap(); 
/* 128 */       SnmpOid[] arrayOfSnmpOid = param1SnmpCachedData.indexes;
/* 129 */       Object[] arrayOfObject = param1SnmpCachedData.datas;
/* 130 */       int i = arrayOfSnmpOid.length;
/* 131 */       HashMap<Object, Object> hashMap = new HashMap<>(i);
/* 132 */       for (byte b = 0; b < i; b++) {
/* 133 */         SnmpOid snmpOid = arrayOfSnmpOid[b];
/* 134 */         if (snmpOid != null) {
/* 135 */           MemoryPoolMXBean memoryPoolMXBean = (MemoryPoolMXBean)arrayOfObject[b];
/*     */           
/* 137 */           if (memoryPoolMXBean != null)
/* 138 */           { String str = memoryPoolMXBean.getName();
/* 139 */             if (str != null)
/* 140 */               hashMap.put(str, snmpOid);  } 
/*     */         } 
/* 142 */       }  return (Map)hashMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected SnmpCachedData updateCachedDatas(Object param1Object) {
/* 162 */       SnmpTableHandler snmpTableHandler1 = this.meta.getManagerHandler(param1Object);
/*     */ 
/*     */ 
/*     */       
/* 166 */       SnmpTableHandler snmpTableHandler2 = this.meta.getPoolHandler(param1Object);
/*     */ 
/*     */       
/* 169 */       long l = System.currentTimeMillis();
/*     */ 
/*     */       
/* 172 */       Map<String, SnmpOid> map = buildPoolIndexMap(snmpTableHandler2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       TreeMap<SnmpOid, Object> treeMap = new TreeMap<>(SnmpCachedData.oidComparator);
/*     */       
/* 179 */       updateTreeMap(treeMap, param1Object, snmpTableHandler1, snmpTableHandler2, map);
/*     */       
/* 181 */       return new SnmpCachedData(l, treeMap);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] getMemoryPools(Object param1Object, MemoryManagerMXBean param1MemoryManagerMXBean, long param1Long) {
/* 191 */       String str = "JvmMemManager." + param1Long + ".getMemoryPools";
/*     */ 
/*     */       
/* 194 */       String[] arrayOfString = null;
/* 195 */       if (param1Object instanceof Map) {
/* 196 */         arrayOfString = (String[])((Map)param1Object).get(str);
/* 197 */         if (arrayOfString != null) return arrayOfString;
/*     */       
/*     */       } 
/* 200 */       if (param1MemoryManagerMXBean != null) {
/* 201 */         arrayOfString = param1MemoryManagerMXBean.getMemoryPoolNames();
/*     */       }
/* 203 */       if (arrayOfString != null && param1Object instanceof Map) {
/* 204 */         Map<String, String[]> map = Util.<Map>cast(param1Object);
/* 205 */         map.put(str, arrayOfString);
/*     */       } 
/*     */       
/* 208 */       return arrayOfString;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void updateTreeMap(TreeMap<SnmpOid, Object> param1TreeMap, Object param1Object, MemoryManagerMXBean param1MemoryManagerMXBean, SnmpOid param1SnmpOid, Map<String, SnmpOid> param1Map) {
/*     */       long l;
/*     */       try {
/* 220 */         l = param1SnmpOid.getOidArc(0);
/* 221 */       } catch (SnmpStatusException snmpStatusException) {
/* 222 */         JvmMemMgrPoolRelTableMetaImpl.log.debug("updateTreeMap", "Bad MemoryManager OID index: " + param1SnmpOid);
/*     */         
/* 224 */         JvmMemMgrPoolRelTableMetaImpl.log.debug("updateTreeMap", snmpStatusException);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 230 */       String[] arrayOfString = getMemoryPools(param1Object, param1MemoryManagerMXBean, l);
/* 231 */       if (arrayOfString == null || arrayOfString.length < 1)
/*     */         return; 
/* 233 */       String str = param1MemoryManagerMXBean.getName();
/* 234 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 235 */         String str1 = arrayOfString[b];
/* 236 */         if (str1 != null) {
/* 237 */           SnmpOid snmpOid = param1Map.get(str1);
/* 238 */           if (snmpOid != null) {
/*     */             long l1;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 244 */               l1 = snmpOid.getOidArc(0);
/* 245 */             } catch (SnmpStatusException snmpStatusException) {
/* 246 */               JvmMemMgrPoolRelTableMetaImpl.log.debug("updateTreeMap", "Bad MemoryPool OID index: " + snmpOid);
/*     */               
/* 248 */               JvmMemMgrPoolRelTableMetaImpl.log.debug("updateTreeMap", snmpStatusException);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 254 */             long[] arrayOfLong = { l, l1 };
/*     */             
/* 256 */             SnmpOid snmpOid1 = new SnmpOid(arrayOfLong);
/*     */             
/* 258 */             param1TreeMap.put(snmpOid1, new JvmMemMgrPoolRelEntryImpl(str, str1, (int)l, (int)l1));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void updateTreeMap(TreeMap<SnmpOid, Object> param1TreeMap, Object param1Object, SnmpTableHandler param1SnmpTableHandler1, SnmpTableHandler param1SnmpTableHandler2, Map<String, SnmpOid> param1Map) {
/* 269 */       if (param1SnmpTableHandler1 instanceof SnmpCachedData) {
/* 270 */         updateTreeMap(param1TreeMap, param1Object, (SnmpCachedData)param1SnmpTableHandler1, param1SnmpTableHandler2, param1Map);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 275 */       SnmpOid snmpOid = null;
/* 276 */       while ((snmpOid = param1SnmpTableHandler1.getNext(snmpOid)) != null) {
/*     */         
/* 278 */         MemoryManagerMXBean memoryManagerMXBean = (MemoryManagerMXBean)param1SnmpTableHandler1.getData(snmpOid);
/* 279 */         if (memoryManagerMXBean == null)
/* 280 */           continue;  updateTreeMap(param1TreeMap, param1Object, memoryManagerMXBean, snmpOid, param1Map);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void updateTreeMap(TreeMap<SnmpOid, Object> param1TreeMap, Object param1Object, SnmpCachedData param1SnmpCachedData, SnmpTableHandler param1SnmpTableHandler, Map<String, SnmpOid> param1Map) {
/* 289 */       SnmpOid[] arrayOfSnmpOid = param1SnmpCachedData.indexes;
/* 290 */       Object[] arrayOfObject = param1SnmpCachedData.datas;
/* 291 */       int i = arrayOfSnmpOid.length;
/* 292 */       for (int j = i - 1; j > -1; j--) {
/* 293 */         MemoryManagerMXBean memoryManagerMXBean = (MemoryManagerMXBean)arrayOfObject[j];
/*     */         
/* 295 */         if (memoryManagerMXBean != null) {
/* 296 */           updateTreeMap(param1TreeMap, param1Object, memoryManagerMXBean, arrayOfSnmpOid[j], param1Map);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 304 */   private transient JvmMemManagerTableMetaImpl managers = null;
/* 305 */   private transient JvmMemPoolTableMetaImpl pools = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmMemMgrPoolRelTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 316 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 317 */     this
/*     */       
/* 319 */       .cache = new JvmMemMgrPoolRelTableCache(this, ((JVM_MANAGEMENT_MIB_IMPL)paramSnmpMib).validity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JvmMemManagerTableMetaImpl getManagers(SnmpMib paramSnmpMib) {
/* 326 */     if (this.managers == null) {
/* 327 */       this
/* 328 */         .managers = (JvmMemManagerTableMetaImpl)paramSnmpMib.getRegisteredTableMeta("JvmMemManagerTable");
/*     */     }
/* 330 */     return this.managers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JvmMemPoolTableMetaImpl getPools(SnmpMib paramSnmpMib) {
/* 337 */     if (this.pools == null) {
/* 338 */       this
/* 339 */         .pools = (JvmMemPoolTableMetaImpl)paramSnmpMib.getRegisteredTableMeta("JvmMemPoolTable");
/*     */     }
/* 341 */     return this.pools;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpTableHandler getManagerHandler(Object paramObject) {
/* 348 */     JvmMemManagerTableMetaImpl jvmMemManagerTableMetaImpl = getManagers(this.theMib);
/* 349 */     return jvmMemManagerTableMetaImpl.getHandler(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpTableHandler getPoolHandler(Object paramObject) {
/* 356 */     JvmMemPoolTableMetaImpl jvmMemPoolTableMetaImpl = getPools(this.theMib);
/* 357 */     return jvmMemPoolTableMetaImpl.getHandler(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 364 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 370 */     boolean bool = log.isDebugOn();
/* 371 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 377 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 381 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 382 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 387 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 388 */     if (bool) log.debug("getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 392 */     if (snmpOid == null) {
/* 393 */       throw new SnmpStatusException(224);
/*     */     }
/* 395 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 404 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 408 */     if (snmpTableHandler == null) {
/* 409 */       return false;
/*     */     }
/* 411 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 418 */     if (paramSnmpOid == null || paramSnmpOid.getLength() < 2) {
/* 419 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 423 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 430 */     long l1 = paramSnmpOid.getOidArc(0);
/* 431 */     long l2 = paramSnmpOid.getOidArc(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 439 */     String str = (map == null) ? null : ("JvmMemMgrPoolRelTable.entry." + l1 + "." + l2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (map != null) {
/* 446 */       Object object1 = map.get(str);
/* 447 */       if (object1 != null) return object1;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 458 */     if (snmpTableHandler == null) {
/* 459 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 463 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 467 */     if (!(object instanceof JvmMemMgrPoolRelEntryImpl)) {
/* 468 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 473 */     JvmMemMgrPoolRelEntryImpl jvmMemMgrPoolRelEntryImpl = (JvmMemMgrPoolRelEntryImpl)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     if (map != null && jvmMemMgrPoolRelEntryImpl != null) {
/* 482 */       map.put(str, jvmMemMgrPoolRelEntryImpl);
/*     */     }
/*     */     
/* 485 */     return jvmMemMgrPoolRelEntryImpl;
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
/*     */   protected SnmpTableHandler getHandler(Object paramObject) {
/*     */     Map<String, SnmpTableHandler> map;
/* 502 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 503 */     else { map = null; }
/*     */ 
/*     */     
/* 506 */     if (map != null) {
/*     */       
/* 508 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmMemMgrPoolRelTable.handler");
/* 509 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 513 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 515 */     if (map != null && snmpTableHandler != null) {
/* 516 */       map.put("JvmMemMgrPoolRelTable.handler", snmpTableHandler);
/*     */     }
/* 518 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 521 */   static final MibLogger log = new MibLogger(JvmMemMgrPoolRelTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemMgrPoolRelTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */