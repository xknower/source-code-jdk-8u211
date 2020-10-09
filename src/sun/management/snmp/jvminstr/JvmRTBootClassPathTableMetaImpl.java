/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmRTBootClassPathTableMeta;
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
/*     */ public class JvmRTBootClassPathTableMetaImpl
/*     */   extends JvmRTBootClassPathTableMeta
/*     */ {
/*     */   static final long serialVersionUID = -8659886610487538299L;
/*     */   private SnmpTableCache cache;
/*     */   
/*     */   private static class JvmRTBootClassPathTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = -2637458695413646098L;
/*     */     private JvmRTBootClassPathTableMetaImpl meta;
/*     */     
/*     */     JvmRTBootClassPathTableCache(JvmRTBootClassPathTableMetaImpl param1JvmRTBootClassPathTableMetaImpl, long param1Long) {
/*  88 */       this.meta = param1JvmRTBootClassPathTableMetaImpl;
/*  89 */       this.validity = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/*  96 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*  97 */       return getTableDatas(map);
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
/*     */     protected SnmpCachedData updateCachedDatas(Object param1Object) {
/* 110 */       String[] arrayOfString = JvmRuntimeImpl.getBootClassPath(param1Object);
/*     */ 
/*     */       
/* 113 */       long l = System.currentTimeMillis();
/* 114 */       int i = arrayOfString.length;
/*     */       
/* 116 */       SnmpOid[] arrayOfSnmpOid = new SnmpOid[i];
/*     */       
/* 118 */       for (byte b = 0; b < i; b++) {
/* 119 */         arrayOfSnmpOid[b] = new SnmpOid((b + 1));
/*     */       }
/*     */       
/* 122 */       return new SnmpCachedData(l, arrayOfSnmpOid, (Object[])arrayOfString);
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
/*     */   public JvmRTBootClassPathTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 135 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 136 */     this.cache = new JvmRTBootClassPathTableCache(this, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 143 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 149 */     boolean bool = log.isDebugOn();
/* 150 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 156 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 160 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 161 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 167 */     if (bool) log.debug("*** **** **** **** getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (snmpOid == null) {
/* 172 */       throw new SnmpStatusException(224);
/*     */     }
/* 174 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 183 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (snmpTableHandler == null) {
/* 188 */       return false;
/*     */     }
/* 190 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 196 */     boolean bool = log.isDebugOn();
/* 197 */     if (bool) log.debug("getEntry", "oid [" + paramSnmpOid + "]"); 
/* 198 */     if (paramSnmpOid == null || paramSnmpOid.getLength() != 1) {
/* 199 */       if (bool) log.debug("getEntry", "Invalid oid [" + paramSnmpOid + "]"); 
/* 200 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 205 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     String str = (map == null) ? null : ("JvmRTBootClassPathTable.entry." + paramSnmpOid.toString());
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (map != null) {
/* 220 */       Object object1 = map.get(str);
/* 221 */       if (object1 != null) {
/* 222 */         if (bool)
/* 223 */           log.debug("getEntry", "Entry is already in the cache"); 
/* 224 */         return object1;
/*     */       } 
/* 226 */       if (bool) log.debug("getEntry", "Entry is not in the cache");
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (snmpTableHandler == null) {
/* 238 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 242 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (object == null) {
/* 247 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (bool) {
/* 253 */       log.debug("getEntry", "data is a: " + object.getClass().getName());
/*     */     }
/*     */     
/* 256 */     JvmRTBootClassPathEntryImpl jvmRTBootClassPathEntryImpl = new JvmRTBootClassPathEntryImpl((String)object, (int)paramSnmpOid.getOidArc(0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (map != null && jvmRTBootClassPathEntryImpl != null) {
/* 262 */       map.put(str, jvmRTBootClassPathEntryImpl);
/*     */     }
/*     */     
/* 265 */     return jvmRTBootClassPathEntryImpl;
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
/* 288 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmRTBootClassPathTable.handler");
/* 289 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 293 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 295 */     if (map != null && snmpTableHandler != null) {
/* 296 */       map.put("JvmRTBootClassPathTable.handler", snmpTableHandler);
/*     */     }
/* 298 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 301 */   static final MibLogger log = new MibLogger(JvmRTBootClassPathTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmRTBootClassPathTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */