/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmMemManagerTableMeta;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ import sun.management.snmp.util.SnmpNamedListTableCache;
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
/*     */ public class JvmMemManagerTableMetaImpl
/*     */   extends JvmMemManagerTableMeta
/*     */ {
/*     */   static final long serialVersionUID = 36176771566817592L;
/*     */   protected SnmpTableCache cache;
/*     */   
/*     */   private static class JvmMemManagerTableCache
/*     */     extends SnmpNamedListTableCache
/*     */   {
/*     */     static final long serialVersionUID = 6564294074653009240L;
/*     */     
/*     */     JvmMemManagerTableCache(long param1Long) {
/*  80 */       this.validity = param1Long;
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
/*     */     protected String getKey(Object param1Object1, List<?> param1List, int param1Int, Object param1Object2) {
/*  97 */       if (param1Object2 == null) return null; 
/*  98 */       String str = ((MemoryManagerMXBean)param1Object2).getName();
/*  99 */       JvmMemManagerTableMetaImpl.log.debug("getKey", "key=" + str);
/* 100 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/* 107 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/* 108 */       return getTableDatas(map);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getRawDatasKey() {
/* 115 */       return "JvmMemManagerTable.getMemoryManagers";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<MemoryManagerMXBean> loadRawDatas(Map<Object, Object> param1Map) {
/* 123 */       return ManagementFactory.getMemoryManagerMXBeans();
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
/*     */   public JvmMemManagerTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 140 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 141 */     this
/*     */       
/* 143 */       .cache = new JvmMemManagerTableCache(((JVM_MANAGEMENT_MIB_IMPL)paramSnmpMib).validity());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 150 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 156 */     boolean bool = log.isDebugOn();
/* 157 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 163 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 167 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 168 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 174 */     if (bool) log.debug("getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (snmpOid == null) {
/* 179 */       throw new SnmpStatusException(224);
/*     */     }
/* 181 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 190 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (snmpTableHandler == null) {
/* 195 */       return false;
/*     */     }
/* 197 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 204 */     if (paramSnmpOid == null) {
/* 205 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 209 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     long l = paramSnmpOid.getOidArc(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     String str = (map == null) ? null : ("JvmMemManagerTable.entry." + l);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     if (map != null) {
/* 228 */       Object object1 = map.get(str);
/* 229 */       if (object1 != null) return object1;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 240 */     if (snmpTableHandler == null) {
/* 241 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 245 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (object == null) {
/* 250 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 255 */     JvmMemManagerEntryImpl jvmMemManagerEntryImpl = new JvmMemManagerEntryImpl((MemoryManagerMXBean)object, (int)l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (map != null && jvmMemManagerEntryImpl != null) {
/* 262 */       map.put(str, jvmMemManagerEntryImpl);
/*     */     }
/*     */     
/* 265 */     return jvmMemManagerEntryImpl;
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
/* 282 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 283 */     else { map = null; }
/*     */ 
/*     */     
/* 286 */     if (map != null) {
/*     */       
/* 288 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmMemManagerTable.handler");
/* 289 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 293 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 295 */     if (map != null && snmpTableHandler != null) {
/* 296 */       map.put("JvmMemManagerTable.handler", snmpTableHandler);
/*     */     }
/* 298 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 301 */   static final MibLogger log = new MibLogger(JvmMemManagerTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemManagerTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */