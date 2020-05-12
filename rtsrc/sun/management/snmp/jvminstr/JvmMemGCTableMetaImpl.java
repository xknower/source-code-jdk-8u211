/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.util.Map;
/*     */ import sun.management.snmp.jvmmib.JvmMemGCTableMeta;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ import sun.management.snmp.util.SnmpCachedData;
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
/*     */ public class JvmMemGCTableMetaImpl
/*     */   extends JvmMemGCTableMeta
/*     */ {
/*     */   static final long serialVersionUID = 8250461197108867607L;
/*     */   
/*     */   protected static class GCTableFilter
/*     */   {
/*     */     public SnmpOid getNext(SnmpCachedData param1SnmpCachedData, SnmpOid param1SnmpOid) {
/*     */       int i;
/*  88 */       boolean bool = JvmMemGCTableMetaImpl.log.isDebugOn();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       byte b = (param1SnmpOid == null) ? -1 : param1SnmpCachedData.find(param1SnmpOid);
/*  97 */       if (bool) JvmMemGCTableMetaImpl.log.debug("GCTableFilter", "oid=" + param1SnmpOid + " at insertion=" + b);
/*     */ 
/*     */ 
/*     */       
/* 101 */       if (b > -1) { i = b + 1; }
/* 102 */       else { i = -b - 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       for (; i < param1SnmpCachedData.indexes.length; i++) {
/* 111 */         if (bool) JvmMemGCTableMetaImpl.log.debug("GCTableFilter", "next=" + i); 
/* 112 */         Object object = param1SnmpCachedData.datas[i];
/* 113 */         if (bool) JvmMemGCTableMetaImpl.log.debug("GCTableFilter", "value[" + i + "]=" + ((MemoryManagerMXBean)object)
/* 114 */               .getName()); 
/* 115 */         if (object instanceof GarbageCollectorMXBean) {
/*     */           
/* 117 */           if (bool) JvmMemGCTableMetaImpl.log.debug("GCTableFilter", ((MemoryManagerMXBean)object)
/* 118 */                 .getName() + " is a  GarbageCollectorMXBean.");
/*     */           
/* 120 */           return param1SnmpCachedData.indexes[i];
/*     */         } 
/* 122 */         if (bool) JvmMemGCTableMetaImpl.log.debug("GCTableFilter", ((MemoryManagerMXBean)object)
/* 123 */               .getName() + " is not a  GarbageCollectorMXBean: " + object
/*     */               
/* 125 */               .getClass().getName());
/*     */       
/*     */       } 
/* 128 */       return null;
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
/*     */     public SnmpOid getNext(SnmpTableHandler param1SnmpTableHandler, SnmpOid param1SnmpOid) {
/* 143 */       if (param1SnmpTableHandler instanceof SnmpCachedData) {
/* 144 */         return getNext((SnmpCachedData)param1SnmpTableHandler, param1SnmpOid);
/*     */       }
/*     */       
/* 147 */       SnmpOid snmpOid = param1SnmpOid;
/*     */       while (true) {
/* 149 */         snmpOid = param1SnmpTableHandler.getNext(snmpOid);
/* 150 */         Object object = param1SnmpTableHandler.getData(snmpOid);
/* 151 */         if (object instanceof GarbageCollectorMXBean)
/*     */         {
/* 153 */           return snmpOid;
/*     */         }
/* 155 */         if (snmpOid == null) {
/* 156 */           return null;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getData(SnmpTableHandler param1SnmpTableHandler, SnmpOid param1SnmpOid) {
/* 166 */       Object object = param1SnmpTableHandler.getData(param1SnmpOid);
/* 167 */       if (object instanceof GarbageCollectorMXBean) return object;
/*     */ 
/*     */       
/* 170 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(SnmpTableHandler param1SnmpTableHandler, SnmpOid param1SnmpOid) {
/* 177 */       if (param1SnmpTableHandler.getData(param1SnmpOid) instanceof GarbageCollectorMXBean) {
/* 178 */         return true;
/*     */       }
/*     */       
/* 181 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 186 */   private transient JvmMemManagerTableMetaImpl managers = null;
/* 187 */   private static GCTableFilter filter = new GCTableFilter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmMemGCTableMetaImpl(SnmpMib paramSnmpMib, SnmpStandardObjectServer paramSnmpStandardObjectServer) {
/* 195 */     super(paramSnmpMib, paramSnmpStandardObjectServer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JvmMemManagerTableMetaImpl getManagers(SnmpMib paramSnmpMib) {
/* 202 */     if (this.managers == null) {
/* 203 */       this
/* 204 */         .managers = (JvmMemManagerTableMetaImpl)paramSnmpMib.getRegisteredTableMeta("JvmMemManagerTable");
/*     */     }
/* 206 */     return this.managers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpTableHandler getHandler(Object paramObject) {
/* 213 */     JvmMemManagerTableMetaImpl jvmMemManagerTableMetaImpl = getManagers(this.theMib);
/* 214 */     return jvmMemManagerTableMetaImpl.getHandler(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(Object paramObject) throws SnmpStatusException {
/* 221 */     return getNextOid(null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SnmpOid getNextOid(SnmpOid paramSnmpOid, Object paramObject) throws SnmpStatusException {
/* 227 */     boolean bool = log.isDebugOn();
/*     */     try {
/* 229 */       if (bool) log.debug("getNextOid", "previous=" + paramSnmpOid);
/*     */ 
/*     */ 
/*     */       
/* 233 */       SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/* 234 */       if (snmpTableHandler == null) {
/*     */ 
/*     */ 
/*     */         
/* 238 */         if (bool) log.debug("getNextOid", "handler is null!"); 
/* 239 */         throw new SnmpStatusException(224);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       SnmpOid snmpOid = filter.getNext(snmpTableHandler, paramSnmpOid);
/* 247 */       if (bool) log.debug("getNextOid", "next=" + snmpOid);
/*     */ 
/*     */ 
/*     */       
/* 251 */       if (snmpOid == null) {
/* 252 */         throw new SnmpStatusException(224);
/*     */       }
/*     */       
/* 255 */       return snmpOid;
/* 256 */     } catch (RuntimeException runtimeException) {
/*     */ 
/*     */       
/* 259 */       if (bool) log.debug("getNextOid", runtimeException); 
/* 260 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contains(SnmpOid paramSnmpOid, Object paramObject) {
/* 269 */     SnmpTableHandler snmpTableHandler = getHandler(paramObject);
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (snmpTableHandler == null)
/* 274 */       return false; 
/* 275 */     return filter.contains(snmpTableHandler, paramSnmpOid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEntry(SnmpOid paramSnmpOid) throws SnmpStatusException {
/* 282 */     if (paramSnmpOid == null) {
/* 283 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 287 */     Map<Object, Object> map = JvmContextFactory.getUserData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     long l = paramSnmpOid.getOidArc(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     String str = (map == null) ? null : ("JvmMemGCTable.entry." + l);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (map != null) {
/* 310 */       Object object1 = map.get(str);
/* 311 */       if (object1 != null) return object1;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     SnmpTableHandler snmpTableHandler = getHandler(map);
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (snmpTableHandler == null) {
/* 323 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */     
/* 327 */     Object object = filter.getData(snmpTableHandler, paramSnmpOid);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (object == null) {
/* 334 */       throw new SnmpStatusException(224);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 339 */     JvmMemGCEntryImpl jvmMemGCEntryImpl = new JvmMemGCEntryImpl((GarbageCollectorMXBean)object, (int)l);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     if (map != null && jvmMemGCEntryImpl != null) {
/* 355 */       map.put(str, jvmMemGCEntryImpl);
/*     */     }
/*     */     
/* 358 */     return jvmMemGCEntryImpl;
/*     */   }
/*     */   
/* 361 */   static final MibLogger log = new MibLogger(JvmMemGCTableMetaImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemGCTableMetaImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */