/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import sun.management.snmp.jvmmib.JvmThreadInstanceTableMeta;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmThreadInstanceTableMetaImpl
/*     */   extends JvmThreadInstanceTableMeta
/*     */ {
/*     */   static final long serialVersionUID = -8432271929226397492L;
/*     */   public static final int MAX_STACK_TRACE_DEPTH = 0;
/*     */   protected SnmpTableCache cache;
/*     */   
/*     */   static SnmpOid makeOid(long paramLong) {
/* 100 */     long[] arrayOfLong = new long[8];
/* 101 */     arrayOfLong[0] = paramLong >> 56L & 0xFFL;
/* 102 */     arrayOfLong[1] = paramLong >> 48L & 0xFFL;
/* 103 */     arrayOfLong[2] = paramLong >> 40L & 0xFFL;
/* 104 */     arrayOfLong[3] = paramLong >> 32L & 0xFFL;
/* 105 */     arrayOfLong[4] = paramLong >> 24L & 0xFFL;
/* 106 */     arrayOfLong[5] = paramLong >> 16L & 0xFFL;
/* 107 */     arrayOfLong[6] = paramLong >> 8L & 0xFFL;
/* 108 */     arrayOfLong[7] = paramLong & 0xFFL;
/* 109 */     return new SnmpOid(arrayOfLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long makeId(SnmpOid paramSnmpOid) {
/* 118 */     long l = 0L;
/* 119 */     long[] arrayOfLong = paramSnmpOid.longValue(false);
/*     */     
/* 121 */     l |= arrayOfLong[0] << 56L;
/* 122 */     l |= arrayOfLong[1] << 48L;
/* 123 */     l |= arrayOfLong[2] << 40L;
/* 124 */     l |= arrayOfLong[3] << 32L;
/* 125 */     l |= arrayOfLong[4] << 24L;
/* 126 */     l |= arrayOfLong[5] << 16L;
/* 127 */     l |= arrayOfLong[6] << 8L;
/* 128 */     l |= arrayOfLong[7];
/*     */     
/* 130 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class JvmThreadInstanceTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = 4947330124563406878L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final JvmThreadInstanceTableMetaImpl meta;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     JvmThreadInstanceTableCache(JvmThreadInstanceTableMetaImpl param1JvmThreadInstanceTableMetaImpl, long param1Long) {
/* 149 */       this.validity = param1Long;
/* 150 */       this.meta = param1JvmThreadInstanceTableMetaImpl;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/* 157 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/* 158 */       return getTableDatas(map);
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
/*     */     protected SnmpCachedData updateCachedDatas(Object param1Object) {
/* 170 */       long[] arrayOfLong = JvmThreadingImpl.getThreadMXBean().getAllThreadIds();
/*     */ 
/*     */ 
/*     */       
/* 174 */       long l = System.currentTimeMillis();
/*     */       
/* 176 */       SnmpOid[] arrayOfSnmpOid = new SnmpOid[arrayOfLong.length];
/* 177 */       TreeMap<SnmpOid, Object> treeMap = new TreeMap<>(SnmpCachedData.oidComparator);
/*     */       
/* 179 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 180 */         JvmThreadInstanceTableMetaImpl.log.debug("", "Making index for thread id [" + arrayOfLong[b] + "]");
/*     */         
/* 182 */         SnmpOid snmpOid = JvmThreadInstanceTableMetaImpl.makeOid(arrayOfLong[b]);
/* 183 */         treeMap.put(snmpOid, snmpOid);
/*     */       } 
/*     */       
/* 186 */       return new SnmpCachedData(l, treeMap);
/*     */     }
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
/*     */   public JvmThreadInstanceTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 203 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 204 */     this
/* 205 */       .cache = new JvmThreadInstanceTableCache(this, ((JVM_MANAGEMENT_MIB_IMPL)paramSnmpMib).validity());
/* 206 */     log.debug("JvmThreadInstanceTableMetaImpl", "Create Thread meta");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 212 */     log.debug("JvmThreadInstanceTableMetaImpl", "getNextOid");
/*     */     
/* 214 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 220 */     log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 226 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 230 */       log.debug("getNextOid", "handler is null!");
/* 231 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 236 */     SnmpOid snmpOid = paramSnmpOid;
/*     */     do {
/* 238 */       snmpOid = snmpTableHandler.getNext(snmpOid);
/* 239 */     } while (snmpOid != null && 
/* 240 */       getJvmThreadInstance(paramObject, snmpOid) == null);
/*     */ 
/*     */     
/* 243 */     log.debug("*** **** **** **** getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (snmpOid == null) {
/* 248 */       throw new SnmpStatusException(224);
/*     */     }
/* 250 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 258 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (snmpTableHandler == null)
/* 263 */       return false; 
/* 264 */     if (!snmpTableHandler.contains(paramSnmpOid)) {
/* 265 */       return false;
/*     */     }
/* 267 */     JvmThreadInstanceEntryImpl jvmThreadInstanceEntryImpl = getJvmThreadInstance(paramObject, paramSnmpOid);
/* 268 */     return (jvmThreadInstanceEntryImpl != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 275 */     log.debug("*** **** **** **** getEntry", "oid [" + paramSnmpOid + "]");
/* 276 */     if (paramSnmpOid == null || paramSnmpOid.getLength() != 8) {
/* 277 */       log.debug("getEntry", "Invalid oid [" + paramSnmpOid + "]");
/* 278 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 283 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */     
/* 287 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 291 */     if (snmpTableHandler == null || !snmpTableHandler.contains(paramSnmpOid)) {
/* 292 */       throw new SnmpStatusException(224);
/*     */     }
/* 294 */     JvmThreadInstanceEntryImpl jvmThreadInstanceEntryImpl = getJvmThreadInstance(map, paramSnmpOid);
/*     */     
/* 296 */     if (jvmThreadInstanceEntryImpl == null) {
/* 297 */       throw new SnmpStatusException(224);
/*     */     }
/* 299 */     return jvmThreadInstanceEntryImpl;
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
/* 316 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 317 */     else { map = null; }
/*     */ 
/*     */     
/* 320 */     if (map != null) {
/*     */       
/* 322 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmThreadInstanceTable.handler");
/* 323 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 327 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 329 */     if (map != null && snmpTableHandler != null) {
/* 330 */       map.put("JvmThreadInstanceTable.handler", snmpTableHandler);
/*     */     }
/* 332 */     return snmpTableHandler;
/*     */   }
/*     */   
/*     */   private ThreadInfo getThreadInfo(long paramLong) {
/* 336 */     return JvmThreadingImpl.getThreadMXBean()
/* 337 */       .getThreadInfo(paramLong, 0);
/*     */   }
/*     */   
/*     */   private ThreadInfo getThreadInfo(SnmpOid paramSnmpOid) {
/* 341 */     return getThreadInfo(makeId(paramSnmpOid));
/*     */   }
/*     */ 
/*     */   
/*     */   private JvmThreadInstanceEntryImpl getJvmThreadInstance(Object paramObject, SnmpOid paramSnmpOid) {
/* 346 */     JvmThreadInstanceEntryImpl jvmThreadInstanceEntryImpl = null;
/* 347 */     String str = null;
/* 348 */     Map<String, JvmThreadInstanceEntryImpl> map = null;
/* 349 */     boolean bool = log.isDebugOn();
/*     */     
/* 351 */     if (paramObject instanceof Map) {
/* 352 */       map = Util.<Map>cast(paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 360 */       str = "JvmThreadInstanceTable.entry." + paramSnmpOid.toString();
/*     */       
/* 362 */       jvmThreadInstanceEntryImpl = (JvmThreadInstanceEntryImpl)map.get(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 367 */     if (jvmThreadInstanceEntryImpl != null) {
/* 368 */       if (bool) log.debug("*** getJvmThreadInstance", "Entry found in cache: " + str);
/*     */       
/* 370 */       return jvmThreadInstanceEntryImpl;
/*     */     } 
/*     */     
/* 373 */     if (bool) log.debug("*** getJvmThreadInstance", "Entry [" + paramSnmpOid + "] is not in cache");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     ThreadInfo threadInfo = null;
/*     */     try {
/* 380 */       threadInfo = getThreadInfo(paramSnmpOid);
/* 381 */     } catch (RuntimeException runtimeException) {
/* 382 */       log.trace("*** getJvmThreadInstance", "Failed to get thread info for rowOid: " + paramSnmpOid);
/*     */       
/* 384 */       log.debug("*** getJvmThreadInstance", runtimeException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 389 */     if (threadInfo == null) {
/* 390 */       if (bool) log.debug("*** getJvmThreadInstance", "No entry by that oid [" + paramSnmpOid + "]");
/*     */       
/* 392 */       return null;
/*     */     } 
/*     */     
/* 395 */     jvmThreadInstanceEntryImpl = new JvmThreadInstanceEntryImpl(threadInfo, paramSnmpOid.toByte());
/* 396 */     if (map != null) map.put(str, jvmThreadInstanceEntryImpl); 
/* 397 */     if (bool) log.debug("*** getJvmThreadInstance", "Entry created for Thread OID [" + paramSnmpOid + "]");
/*     */     
/* 399 */     return jvmThreadInstanceEntryImpl;
/*     */   }
/*     */   
/* 402 */   static final MibLogger log = new MibLogger(JvmThreadInstanceTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmThreadInstanceTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */