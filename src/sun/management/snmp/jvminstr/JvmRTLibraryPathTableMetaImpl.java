/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmRTLibraryPathTableMeta;
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
/*     */ public class JvmRTLibraryPathTableMetaImpl
/*     */   extends JvmRTLibraryPathTableMeta
/*     */ {
/*     */   static final long serialVersionUID = 6713252710712502068L;
/*     */   private SnmpTableCache cache;
/*     */   
/*     */   private static class JvmRTLibraryPathTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = 2035304445719393195L;
/*     */     private JvmRTLibraryPathTableMetaImpl meta;
/*     */     
/*     */     JvmRTLibraryPathTableCache(JvmRTLibraryPathTableMetaImpl param1JvmRTLibraryPathTableMetaImpl, long param1Long) {
/*  86 */       this.meta = param1JvmRTLibraryPathTableMetaImpl;
/*  87 */       this.validity = param1Long;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected SnmpCachedData updateCachedDatas(Object param1Object) {
/* 108 */       String[] arrayOfString = JvmRuntimeImpl.getLibraryPath(param1Object);
/*     */ 
/*     */       
/* 111 */       long l = System.currentTimeMillis();
/* 112 */       int i = arrayOfString.length;
/*     */       
/* 114 */       SnmpOid[] arrayOfSnmpOid = new SnmpOid[i];
/*     */       
/* 116 */       for (byte b = 0; b < i; b++) {
/* 117 */         arrayOfSnmpOid[b] = new SnmpOid((b + 1));
/*     */       }
/*     */       
/* 120 */       return new SnmpCachedData(l, arrayOfSnmpOid, (Object[])arrayOfString);
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
/*     */   public JvmRTLibraryPathTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 133 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 134 */     this.cache = new JvmRTLibraryPathTableCache(this, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 141 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 147 */     boolean bool = log.isDebugOn();
/* 148 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 154 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 158 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 159 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 165 */     if (bool) log.debug("*** **** **** **** getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (snmpOid == null) {
/* 170 */       throw new SnmpStatusException(224);
/*     */     }
/* 172 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 181 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (snmpTableHandler == null) {
/* 186 */       return false;
/*     */     }
/* 188 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 194 */     boolean bool = log.isDebugOn();
/* 195 */     if (bool) log.debug("getEntry", "oid [" + paramSnmpOid + "]"); 
/* 196 */     if (paramSnmpOid == null || paramSnmpOid.getLength() != 1) {
/* 197 */       if (bool) log.debug("getEntry", "Invalid oid [" + paramSnmpOid + "]"); 
/* 198 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 203 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     String str = (map == null) ? null : ("JvmRTLibraryPathTable.entry." + paramSnmpOid.toString());
/*     */ 
/*     */ 
/*     */     
/* 217 */     if (map != null) {
/* 218 */       Object object1 = map.get(str);
/* 219 */       if (object1 != null) {
/* 220 */         if (bool)
/* 221 */           log.debug("getEntry", "Entry is already in the cache"); 
/* 222 */         return object1;
/* 223 */       }  if (bool) log.debug("getEntry", "Entry is not in the cache");
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 235 */     if (snmpTableHandler == null) {
/* 236 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 240 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (object == null) {
/* 245 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (bool) log.debug("getEntry", "data is a: " + object
/* 251 */           .getClass().getName());
/*     */     
/* 253 */     JvmRTLibraryPathEntryImpl jvmRTLibraryPathEntryImpl = new JvmRTLibraryPathEntryImpl((String)object, (int)paramSnmpOid.getOidArc(0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (map != null && jvmRTLibraryPathEntryImpl != null) {
/* 259 */       map.put(str, jvmRTLibraryPathEntryImpl);
/*     */     }
/*     */     
/* 262 */     return jvmRTLibraryPathEntryImpl;
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
/* 279 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 280 */     else { map = null; }
/*     */ 
/*     */     
/* 283 */     if (map != null) {
/*     */       
/* 285 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmRTLibraryPathTable.handler");
/* 286 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 290 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 292 */     if (map != null && snmpTableHandler != null) {
/* 293 */       map.put("JvmRTLibraryPathTable.handler", snmpTableHandler);
/*     */     }
/* 295 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 298 */   static final MibLogger log = new MibLogger(JvmRTLibraryPathTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmRTLibraryPathTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */