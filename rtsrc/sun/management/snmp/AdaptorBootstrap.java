/*     */ package sun.management.snmp;
/*     */ 
/*     */ import com.sun.jmx.snmp.IPAcl.SnmpAcl;
/*     */ import com.sun.jmx.snmp.InetAddressAcl;
/*     */ import com.sun.jmx.snmp.daemon.SnmpAdaptorServer;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import sun.management.Agent;
/*     */ import sun.management.AgentConfigurationError;
/*     */ import sun.management.FileSystem;
/*     */ import sun.management.snmp.jvminstr.JVM_MANAGEMENT_MIB_IMPL;
/*     */ import sun.management.snmp.jvminstr.NotificationTarget;
/*     */ import sun.management.snmp.jvminstr.NotificationTargetImpl;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ import sun.management.snmp.util.MibLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AdaptorBootstrap
/*     */ {
/*  61 */   private static final MibLogger log = new MibLogger(AdaptorBootstrap.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SnmpAdaptorServer adaptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JVM_MANAGEMENT_MIB_IMPL jvmmib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AdaptorBootstrap(SnmpAdaptorServer paramSnmpAdaptorServer, JVM_MANAGEMENT_MIB_IMPL paramJVM_MANAGEMENT_MIB_IMPL) {
/* 103 */     this.jvmmib = paramJVM_MANAGEMENT_MIB_IMPL;
/* 104 */     this.adaptor = paramSnmpAdaptorServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getDefaultFileName(String paramString) {
/* 113 */     String str = File.separator;
/* 114 */     return System.getProperty("java.home") + str + "lib" + str + "management" + str + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<NotificationTarget> getTargetList(InetAddressAcl paramInetAddressAcl, int paramInt) {
/* 124 */     ArrayList<NotificationTargetImpl> arrayList = new ArrayList();
/*     */     
/* 126 */     if (paramInetAddressAcl != null) {
/* 127 */       if (log.isDebugOn()) {
/* 128 */         log.debug("getTargetList", Agent.getText("jmxremote.AdaptorBootstrap.getTargetList.processing"));
/*     */       }
/* 130 */       Enumeration<InetAddress> enumeration = paramInetAddressAcl.getTrapDestinations();
/* 131 */       while (enumeration.hasMoreElements()) {
/* 132 */         InetAddress inetAddress = enumeration.nextElement();
/*     */         
/* 134 */         Enumeration<String> enumeration1 = paramInetAddressAcl.getTrapCommunities(inetAddress);
/* 135 */         while (enumeration1.hasMoreElements()) {
/* 136 */           String str = enumeration1.nextElement();
/* 137 */           NotificationTargetImpl notificationTargetImpl = new NotificationTargetImpl(inetAddress, paramInt, str);
/*     */ 
/*     */ 
/*     */           
/* 141 */           if (log.isDebugOn())
/* 142 */             log.debug("getTargetList", 
/* 143 */                 Agent.getText("jmxremote.AdaptorBootstrap.getTargetList.adding", new String[] {
/* 144 */                     notificationTargetImpl.toString() })); 
/* 145 */           arrayList.add(notificationTargetImpl);
/*     */         } 
/*     */       } 
/*     */     } 
/* 149 */     return (List)arrayList;
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
/*     */   public static synchronized AdaptorBootstrap initialize() {
/* 162 */     Properties properties = Agent.loadManagementProperties();
/* 163 */     if (properties == null) return null;
/*     */     
/* 165 */     String str = properties.getProperty("com.sun.management.snmp.port");
/*     */     
/* 167 */     return initialize(str, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized AdaptorBootstrap initialize(String paramString, Properties paramProperties) {
/*     */     int i, j;
/* 177 */     if (paramString.length() == 0) paramString = "161";
/*     */     
/*     */     try {
/* 180 */       i = Integer.parseInt(paramString);
/* 181 */     } catch (NumberFormatException numberFormatException) {
/* 182 */       throw new AgentConfigurationError("agent.err.invalid.snmp.port", numberFormatException, new String[] { paramString });
/*     */     } 
/*     */     
/* 185 */     if (i < 0) {
/* 186 */       throw new AgentConfigurationError("agent.err.invalid.snmp.port", new String[] { paramString });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 191 */     String str1 = paramProperties.getProperty("com.sun.management.snmp.trap", "162");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 196 */       j = Integer.parseInt(str1);
/* 197 */     } catch (NumberFormatException numberFormatException) {
/* 198 */       throw new AgentConfigurationError("agent.err.invalid.snmp.trap.port", numberFormatException, new String[] { str1 });
/*     */     } 
/*     */     
/* 201 */     if (j < 0) {
/* 202 */       throw new AgentConfigurationError("agent.err.invalid.snmp.trap.port", new String[] { str1 });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 207 */     String str2 = paramProperties.getProperty("com.sun.management.snmp.interface", "localhost");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     String str3 = getDefaultFileName("snmp.acl");
/*     */     
/* 214 */     String str4 = paramProperties.getProperty("com.sun.management.snmp.acl.file", str3);
/*     */ 
/*     */     
/* 217 */     String str5 = paramProperties.getProperty("com.sun.management.snmp.acl", "true");
/*     */     
/* 219 */     boolean bool = Boolean.valueOf(str5).booleanValue();
/*     */     
/* 221 */     if (bool) checkAclFile(str4);
/*     */     
/* 223 */     AdaptorBootstrap adaptorBootstrap = null;
/*     */     try {
/* 225 */       adaptorBootstrap = getAdaptorBootstrap(i, j, str2, bool, str4);
/*     */     }
/* 227 */     catch (Exception exception) {
/* 228 */       throw new AgentConfigurationError("agent.err.exception", exception, new String[] { exception.getMessage() });
/*     */     } 
/* 230 */     return adaptorBootstrap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AdaptorBootstrap getAdaptorBootstrap(int paramInt1, int paramInt2, String paramString1, boolean paramBoolean, String paramString2) {
/*     */     InetAddress inetAddress;
/*     */     SnmpAcl snmpAcl;
/*     */     try {
/* 239 */       inetAddress = InetAddress.getByName(paramString1);
/* 240 */     } catch (UnknownHostException unknownHostException) {
/* 241 */       throw new AgentConfigurationError("agent.err.unknown.snmp.interface", unknownHostException, new String[] { paramString1 });
/*     */     } 
/* 243 */     if (log.isDebugOn()) {
/* 244 */       log.debug("initialize", 
/* 245 */           Agent.getText("jmxremote.AdaptorBootstrap.getTargetList.starting\n\tcom.sun.management.snmp.port=" + paramInt1 + "\n\t" + "com.sun.management.snmp.trap" + "=" + paramInt2 + "\n\t" + "com.sun.management.snmp.interface" + "=" + inetAddress + (paramBoolean ? ("\n\tcom.sun.management.snmp.acl.file=" + paramString2) : "\n\tNo ACL") + ""));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 256 */       snmpAcl = paramBoolean ? new SnmpAcl(System.getProperty("user.name"), paramString2) : null;
/*     */     }
/* 258 */     catch (UnknownHostException unknownHostException) {
/* 259 */       throw new AgentConfigurationError("agent.err.unknown.snmp.interface", unknownHostException, new String[] { unknownHostException.getMessage() });
/*     */     } 
/*     */ 
/*     */     
/* 263 */     SnmpAdaptorServer snmpAdaptorServer = new SnmpAdaptorServer(snmpAcl, paramInt1, inetAddress);
/*     */     
/* 265 */     snmpAdaptorServer.setUserDataFactory(new JvmContextFactory());
/* 266 */     snmpAdaptorServer.setTrapPort(paramInt2);
/*     */ 
/*     */ 
/*     */     
/* 270 */     JVM_MANAGEMENT_MIB_IMPL jVM_MANAGEMENT_MIB_IMPL = new JVM_MANAGEMENT_MIB_IMPL();
/*     */     try {
/* 272 */       jVM_MANAGEMENT_MIB_IMPL.init();
/* 273 */     } catch (IllegalAccessException illegalAccessException) {
/* 274 */       throw new AgentConfigurationError("agent.err.snmp.mib.init.failed", illegalAccessException, new String[] { illegalAccessException.getMessage() });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 279 */     jVM_MANAGEMENT_MIB_IMPL.addTargets(getTargetList(snmpAcl, paramInt2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 289 */       snmpAdaptorServer.start(Long.MAX_VALUE);
/* 290 */     } catch (Exception exception) {
/* 291 */       Throwable throwable = exception;
/* 292 */       if (exception instanceof com.sun.jmx.snmp.daemon.CommunicationException) {
/* 293 */         Throwable throwable1 = throwable.getCause();
/* 294 */         if (throwable1 != null) throwable = throwable1; 
/*     */       } 
/* 296 */       throw new AgentConfigurationError("agent.err.snmp.adaptor.start.failed", throwable, new String[] { inetAddress + ":" + paramInt1, "(" + throwable
/*     */             
/* 298 */             .getMessage() + ")" });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (!snmpAdaptorServer.isActive()) {
/* 305 */       throw new AgentConfigurationError("agent.err.snmp.adaptor.start.failed", new String[] { inetAddress + ":" + paramInt1 });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 312 */       snmpAdaptorServer.addMib(jVM_MANAGEMENT_MIB_IMPL);
/*     */ 
/*     */ 
/*     */       
/* 316 */       jVM_MANAGEMENT_MIB_IMPL.setSnmpAdaptor(snmpAdaptorServer);
/* 317 */     } catch (RuntimeException runtimeException) {
/* 318 */       (new AdaptorBootstrap(snmpAdaptorServer, jVM_MANAGEMENT_MIB_IMPL)).terminate();
/* 319 */       throw runtimeException;
/*     */     } 
/*     */     
/* 322 */     log.debug("initialize", 
/* 323 */         Agent.getText("jmxremote.AdaptorBootstrap.getTargetList.initialize1"));
/* 324 */     log.config("initialize", 
/* 325 */         Agent.getText("jmxremote.AdaptorBootstrap.getTargetList.initialize2", new String[] {
/* 326 */             inetAddress.toString(), Integer.toString(snmpAdaptorServer.getPort()) }));
/* 327 */     return new AdaptorBootstrap(snmpAdaptorServer, jVM_MANAGEMENT_MIB_IMPL);
/*     */   }
/*     */   
/*     */   private static void checkAclFile(String paramString) {
/* 331 */     if (paramString == null || paramString.length() == 0) {
/* 332 */       throw new AgentConfigurationError("agent.err.acl.file.notset");
/*     */     }
/* 334 */     File file = new File(paramString);
/* 335 */     if (!file.exists()) {
/* 336 */       throw new AgentConfigurationError("agent.err.acl.file.notfound", new String[] { paramString });
/*     */     }
/* 338 */     if (!file.canRead()) {
/* 339 */       throw new AgentConfigurationError("agent.err.acl.file.not.readable", new String[] { paramString });
/*     */     }
/*     */     
/* 342 */     FileSystem fileSystem = FileSystem.open();
/*     */     try {
/* 344 */       if (fileSystem.supportsFileSecurity(file) && 
/* 345 */         !fileSystem.isAccessUserOnly(file)) {
/* 346 */         throw new AgentConfigurationError("agent.err.acl.file.access.notrestricted", new String[] { paramString });
/*     */       
/*     */       }
/*     */     }
/* 350 */     catch (IOException iOException) {
/* 351 */       throw new AgentConfigurationError("agent.err.acl.file.read.failed", new String[] { paramString });
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
/*     */   public synchronized int getPort() {
/* 363 */     if (this.adaptor != null) return this.adaptor.getPort(); 
/* 364 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void terminate() {
/* 371 */     if (this.adaptor == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 377 */       this.jvmmib.terminate();
/* 378 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 381 */       log.debug("jmxremote.AdaptorBootstrap.getTargetList.terminate", exception
/* 382 */           .toString());
/*     */     } finally {
/* 384 */       this.jvmmib = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 390 */       this.adaptor.stop();
/*     */     } finally {
/* 392 */       this.adaptor = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface DefaultValues {
/*     */     public static final String PORT = "161";
/*     */     public static final String CONFIG_FILE_NAME = "management.properties";
/*     */     public static final String TRAP_PORT = "162";
/*     */     public static final String USE_ACL = "true";
/*     */     public static final String ACL_FILE_NAME = "snmp.acl";
/*     */     public static final String BIND_ADDRESS = "localhost";
/*     */   }
/*     */   
/*     */   public static interface PropertyNames {
/*     */     public static final String PORT = "com.sun.management.snmp.port";
/*     */     public static final String CONFIG_FILE_NAME = "com.sun.management.config.file";
/*     */     public static final String TRAP_PORT = "com.sun.management.snmp.trap";
/*     */     public static final String USE_ACL = "com.sun.management.snmp.acl";
/*     */     public static final String ACL_FILE_NAME = "com.sun.management.snmp.acl.file";
/*     */     public static final String BIND_ADDRESS = "com.sun.management.snmp.interface";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\AdaptorBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */