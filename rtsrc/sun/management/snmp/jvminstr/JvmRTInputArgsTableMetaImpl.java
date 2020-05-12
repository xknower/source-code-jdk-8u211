/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmRTInputArgsTableMeta;
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
/*     */ public class JvmRTInputArgsTableMetaImpl
/*     */   extends JvmRTInputArgsTableMeta
/*     */ {
/*     */   static final long serialVersionUID = -2083438094888099238L;
/*     */   private SnmpTableCache cache;
/*     */   
/*     */   private static class JvmRTInputArgsTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = 1693751105464785192L;
/*     */     private JvmRTInputArgsTableMetaImpl meta;
/*     */     
/*     */     JvmRTInputArgsTableCache(JvmRTInputArgsTableMetaImpl param1JvmRTInputArgsTableMetaImpl, long param1Long) {
/*  87 */       this.meta = param1JvmRTInputArgsTableMetaImpl;
/*  88 */       this.validity = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnmpTableHandler getTableHandler() {
/*  95 */       Map<Object, Object> map = JvmContextFactory.getUserData();
/*  96 */       return getTableDatas(map);
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
/* 108 */       String[] arrayOfString = JvmRuntimeImpl.getInputArguments(param1Object);
/*     */ 
/*     */       
/* 111 */       long l = System.currentTimeMillis();
/* 112 */       SnmpOid[] arrayOfSnmpOid = new SnmpOid[arrayOfString.length];
/*     */       
/* 114 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 115 */         arrayOfSnmpOid[b] = new SnmpOid((b + 1));
/*     */       }
/*     */       
/* 118 */       return new SnmpCachedData(l, arrayOfSnmpOid, (Object[])arrayOfString);
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
/*     */   public JvmRTInputArgsTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 131 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 132 */     this.cache = new JvmRTInputArgsTableCache(this, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 139 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 145 */     boolean bool = log.isDebugOn();
/* 146 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 152 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 156 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 157 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 162 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 163 */     if (bool) log.debug("*** **** **** **** getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (snmpOid == null) {
/* 168 */       throw new SnmpStatusException(224);
/*     */     }
/* 170 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 179 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (snmpTableHandler == null) {
/* 184 */       return false;
/*     */     }
/* 186 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 192 */     boolean bool = log.isDebugOn();
/* 193 */     if (bool) log.debug("getEntry", "oid [" + paramSnmpOid + "]"); 
/* 194 */     if (paramSnmpOid == null || paramSnmpOid.getLength() != 1) {
/* 195 */       if (bool) log.debug("getEntry", "Invalid oid [" + paramSnmpOid + "]"); 
/* 196 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 201 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     String str = (map == null) ? null : ("JvmRTInputArgsTable.entry." + paramSnmpOid.toString());
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (map != null) {
/* 216 */       Object object1 = map.get(str);
/* 217 */       if (object1 != null) {
/* 218 */         if (bool)
/* 219 */           log.debug("getEntry", "Entry is already in the cache"); 
/* 220 */         return object1;
/* 221 */       }  if (bool) log.debug("getEntry", "Entry is not in the cache");
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (snmpTableHandler == null) {
/* 233 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 237 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 241 */     if (object == null) {
/* 242 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (bool) log.debug("getEntry", "data is a: " + object
/* 248 */           .getClass().getName());
/*     */     
/* 250 */     JvmRTInputArgsEntryImpl jvmRTInputArgsEntryImpl = new JvmRTInputArgsEntryImpl((String)object, (int)paramSnmpOid.getOidArc(0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     if (map != null && jvmRTInputArgsEntryImpl != null) {
/* 256 */       map.put(str, jvmRTInputArgsEntryImpl);
/*     */     }
/*     */     
/* 259 */     return jvmRTInputArgsEntryImpl;
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
/* 276 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 277 */     else { map = null; }
/*     */ 
/*     */     
/* 280 */     if (map != null) {
/*     */       
/* 282 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmRTInputArgsTable.handler");
/* 283 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 287 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 289 */     if (map != null && snmpTableHandler != null) {
/* 290 */       map.put("JvmRTInputArgsTable.handler", snmpTableHandler);
/*     */     }
/* 292 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 295 */   static final MibLogger log = new MibLogger(JvmRTInputArgsTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmRTInputArgsTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */