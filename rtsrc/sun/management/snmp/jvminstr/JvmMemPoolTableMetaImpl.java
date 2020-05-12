/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmMemPoolTableMeta;
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
/*     */ public class JvmMemPoolTableMetaImpl
/*     */   extends JvmMemPoolTableMeta
/*     */ {
/*     */   static final long serialVersionUID = -2525820976094284957L;
/*     */   protected SnmpTableCache cache;
/*     */   
/*     */   private static class JvmMemPoolTableCache
/*     */     extends SnmpNamedListTableCache
/*     */   {
/*     */     static final long serialVersionUID = -1755520683086760574L;
/*     */     
/*     */     JvmMemPoolTableCache(long param1Long) {
/*  79 */       this.validity = param1Long;
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
/*  96 */       if (param1Object2 == null) return null; 
/*  97 */       String str = ((MemoryPoolMXBean)param1Object2).getName();
/*  98 */       JvmMemPoolTableMetaImpl.log.debug("getKey", "key=" + str);
/*  99 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/* 106 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/* 107 */       return getTableDatas(map);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getRawDatasKey() {
/* 114 */       return "JvmMemManagerTable.getMemoryPools";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<MemoryPoolMXBean> loadRawDatas(Map<Object, Object> param1Map) {
/* 122 */       return ManagementFactory.getMemoryPoolMXBeans();
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
/*     */   public JvmMemPoolTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 135 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 136 */     this
/*     */       
/* 138 */       .cache = new JvmMemPoolTableCache(((JVM_MANAGEMENT_MIB_IMPL)paramSnmpMib).validity() * 30L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 146 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 152 */     boolean bool = log.isDebugOn();
/*     */     try {
/* 154 */       if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 160 */       if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */         
/* 164 */         if (bool) log.debug("getNextOid", "handler is null!"); 
/* 165 */         throw new SnmpStatusException(224);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 172 */       if (bool) log.debug("getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (snmpOid == null) {
/* 177 */         throw new SnmpStatusException(224);
/*     */       }
/*     */       
/* 180 */       return snmpOid;
/* 181 */     } catch (SnmpStatusException snmpStatusException) {
/* 182 */       if (bool) log.debug("getNextOid", "End of MIB View: " + snmpStatusException); 
/* 183 */       throw snmpStatusException;
/* 184 */     } catch (RuntimeException runtimeException) {
/* 185 */       if (bool) log.debug("getNextOid", "Unexpected exception: " + runtimeException); 
/* 186 */       if (bool) log.debug("getNextOid", runtimeException); 
/* 187 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 197 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (snmpTableHandler == null) {
/* 202 */       return false;
/*     */     }
/* 204 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 211 */     if (paramSnmpOid == null) {
/* 212 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 216 */     Map<String, JvmMemPoolEntryImpl> map = Util.<Map>cast(JvmContextFactory.getUserData());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     long l = paramSnmpOid.getOidArc(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     String str = (map == null) ? null : ("JvmMemPoolTable.entry." + l);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (map != null) {
/* 235 */       Object object1 = map.get(str);
/* 236 */       if (object1 != null) return object1;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (snmpTableHandler == null) {
/* 248 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 252 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 256 */     if (object == null) {
/* 257 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (log.isDebugOn())
/* 263 */       log.debug("getEntry", "data is a: " + object.getClass().getName()); 
/* 264 */     JvmMemPoolEntryImpl jvmMemPoolEntryImpl = new JvmMemPoolEntryImpl((MemoryPoolMXBean)object, (int)l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     if (map != null && jvmMemPoolEntryImpl != null) {
/* 271 */       map.put(str, jvmMemPoolEntryImpl);
/*     */     }
/*     */     
/* 274 */     return jvmMemPoolEntryImpl;
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
/* 291 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 292 */     else { map = null; }
/*     */ 
/*     */     
/* 295 */     if (map != null) {
/*     */       
/* 297 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmMemPoolTable.handler");
/* 298 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 302 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 304 */     if (map != null && snmpTableHandler != null) {
/* 305 */       map.put("JvmMemPoolTable.handler", snmpTableHandler);
/*     */     }
/* 307 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 310 */   static final MibLogger log = new MibLogger(JvmMemPoolTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemPoolTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */