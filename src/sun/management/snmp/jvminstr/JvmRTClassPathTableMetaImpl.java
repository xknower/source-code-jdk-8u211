/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmRTClassPathTableMeta;
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
/*     */ public class JvmRTClassPathTableMetaImpl
/*     */   extends JvmRTClassPathTableMeta
/*     */ {
/*     */   static final long serialVersionUID = -6914494148818455166L;
/*     */   private SnmpTableCache cache;
/*     */   
/*     */   private static class JvmRTClassPathTableCache
/*     */     extends SnmpTableCache
/*     */   {
/*     */     static final long serialVersionUID = 3805032372592117315L;
/*     */     private JvmRTClassPathTableMetaImpl meta;
/*     */     
/*     */     JvmRTClassPathTableCache(JvmRTClassPathTableMetaImpl param1JvmRTClassPathTableMetaImpl, long param1Long) {
/*  87 */       this.meta = param1JvmRTClassPathTableMetaImpl;
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
/*     */     
/*     */     protected SnmpCachedData updateCachedDatas(Object param1Object) {
/* 109 */       String[] arrayOfString = JvmRuntimeImpl.getClassPath(param1Object);
/*     */ 
/*     */       
/* 112 */       long l = System.currentTimeMillis();
/* 113 */       int i = arrayOfString.length;
/*     */       
/* 115 */       SnmpOid[] arrayOfSnmpOid = new SnmpOid[i];
/*     */       
/* 117 */       for (byte b = 0; b < i; b++) {
/* 118 */         arrayOfSnmpOid[b] = new SnmpOid((b + 1));
/*     */       }
/*     */       
/* 121 */       return new SnmpCachedData(l, arrayOfSnmpOid, (Object[])arrayOfString);
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
/*     */   public JvmRTClassPathTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 134 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/* 135 */     this.cache = new JvmRTClassPathTableCache(this, -1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 142 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 148 */     boolean bool = log.isDebugOn();
/* 149 */     if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 155 */     if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 159 */       if (bool) log.debug("getNextOid", "handler is null!"); 
/* 160 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 165 */     SnmpOid snmpOid = snmpTableHandler.getNext(paramSnmpOid);
/* 166 */     if (bool) log.debug("*** **** **** **** getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (snmpOid == null) {
/* 171 */       throw new SnmpStatusException(224);
/*     */     }
/* 173 */     return snmpOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 182 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (snmpTableHandler == null) {
/* 187 */       return false;
/*     */     }
/* 189 */     return snmpTableHandler.contains(paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 195 */     boolean bool = log.isDebugOn();
/* 196 */     if (bool) log.debug("getEntry", "oid [" + paramSnmpOid + "]"); 
/* 197 */     if (paramSnmpOid == null || paramSnmpOid.getLength() != 1) {
/* 198 */       if (bool) log.debug("getEntry", "Invalid oid [" + paramSnmpOid + "]"); 
/* 199 */       throw new SnmpStatusException(224);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 204 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     String str = (map == null) ? null : ("JvmRTClassPathTable.entry." + paramSnmpOid.toString());
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (map != null) {
/* 219 */       Object object1 = map.get(str);
/* 220 */       if (object1 != null) {
/* 221 */         if (bool)
/* 222 */           log.debug("getEntry", "Entry is already in the cache"); 
/* 223 */         return object1;
/*     */       } 
/* 225 */       if (bool) log.debug("getEntry", "Entry is not in the cache");
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (snmpTableHandler == null) {
/* 237 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 241 */     Object object = snmpTableHandler.getData(paramSnmpOid);
/*     */ 
/*     */ 
/*     */     
/* 245 */     if (object == null) {
/* 246 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (bool) {
/* 252 */       log.debug("getEntry", "data is a: " + object.getClass().getName());
/*     */     }
/* 254 */     JvmRTClassPathEntryImpl jvmRTClassPathEntryImpl = new JvmRTClassPathEntryImpl((String)object, (int)paramSnmpOid.getOidArc(0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     if (map != null && jvmRTClassPathEntryImpl != null) {
/* 260 */       map.put(str, jvmRTClassPathEntryImpl);
/*     */     }
/*     */     
/* 263 */     return jvmRTClassPathEntryImpl;
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
/* 280 */     if (paramObject instanceof Map) { map = Util.<Map>cast(paramObject); }
/* 281 */     else { map = null; }
/*     */ 
/*     */     
/* 284 */     if (map != null) {
/*     */       
/* 286 */       SnmpTableHandler snmpTableHandler1 = (SnmpTableHandler)map.get("JvmRTClassPathTable.handler");
/* 287 */       if (snmpTableHandler1 != null) return snmpTableHandler1;
/*     */     
/*     */     } 
/*     */     
/* 291 */     SnmpTableHandler snmpTableHandler = this.cache.getTableHandler();
/*     */     
/* 293 */     if (map != null && snmpTableHandler != null) {
/* 294 */       map.put("JvmRTClassPathTable.handler", snmpTableHandler);
/*     */     }
/* 296 */     return snmpTableHandler;
/*     */   }
/*     */   
/* 299 */   static final MibLogger log = new MibLogger(JvmRTClassPathTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmRTClassPathTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */