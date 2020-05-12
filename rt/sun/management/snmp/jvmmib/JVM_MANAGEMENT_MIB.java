/*     */ package sun.management.snmp.jvmmib;
/*     */ 
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import com.sun.jmx.snmp.agent.SnmpMibTable;
/*     */ import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;
/*     */ import java.io.Serializable;
/*     */ import java.util.Hashtable;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JVM_MANAGEMENT_MIB
/*     */   extends SnmpMib
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 6895037919735816732L;
/*     */   
/*     */   public void init() throws IllegalAccessException {
/*  70 */     if (this.isInitialized == true) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  75 */       populate((MBeanServer)null, (ObjectName)null);
/*  76 */     } catch (IllegalAccessException illegalAccessException) {
/*  77 */       throw illegalAccessException;
/*  78 */     } catch (RuntimeException runtimeException) {
/*  79 */       throw runtimeException;
/*  80 */     } catch (Exception exception) {
/*  81 */       throw new Error(exception.getMessage());
/*     */     } 
/*     */     
/*  84 */     this.isInitialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  94 */     if (this.isInitialized == true) {
/*  95 */       throw new InstanceAlreadyExistsException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.server = paramMBeanServer;
/*     */     
/* 102 */     populate(paramMBeanServer, paramObjectName);
/*     */     
/* 104 */     this.isInitialized = true;
/* 105 */     return paramObjectName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 115 */     if (this.isInitialized == true) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if (this.objectserver == null) {
/* 120 */       this.objectserver = new SnmpStandardObjectServer();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     initJvmOS(paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     initJvmCompilation(paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     initJvmRuntime(paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     initJvmThreading(paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     initJvmMemory(paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     initJvmClassLoading(paramMBeanServer);
/*     */     
/* 158 */     this.isInitialized = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmOS(MBeanServer paramMBeanServer) throws Exception {
/* 180 */     String str = getGroupOid("JvmOS", "1.3.6.1.4.1.42.2.145.3.163.1.1.6");
/* 181 */     ObjectName objectName = null;
/* 182 */     if (paramMBeanServer != null) {
/* 183 */       objectName = getGroupObjectName("JvmOS", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmOS");
/*     */     }
/* 185 */     JvmOSMeta jvmOSMeta = createJvmOSMetaNode("JvmOS", str, objectName, paramMBeanServer);
/* 186 */     if (jvmOSMeta != null) {
/* 187 */       jvmOSMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       JvmOSMBean jvmOSMBean = (JvmOSMBean)createJvmOSMBean("JvmOS", str, objectName, paramMBeanServer);
/* 194 */       jvmOSMeta.setInstance(jvmOSMBean);
/* 195 */       registerGroupNode("JvmOS", str, objectName, jvmOSMeta, jvmOSMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmOSMeta createJvmOSMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 217 */     return new JvmOSMeta(this, this.objectserver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmOSMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmCompilation(MBeanServer paramMBeanServer) throws Exception {
/* 261 */     String str = getGroupOid("JvmCompilation", "1.3.6.1.4.1.42.2.145.3.163.1.1.5");
/* 262 */     ObjectName objectName = null;
/* 263 */     if (paramMBeanServer != null) {
/* 264 */       objectName = getGroupObjectName("JvmCompilation", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmCompilation");
/*     */     }
/* 266 */     JvmCompilationMeta jvmCompilationMeta = createJvmCompilationMetaNode("JvmCompilation", str, objectName, paramMBeanServer);
/* 267 */     if (jvmCompilationMeta != null) {
/* 268 */       jvmCompilationMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       JvmCompilationMBean jvmCompilationMBean = (JvmCompilationMBean)createJvmCompilationMBean("JvmCompilation", str, objectName, paramMBeanServer);
/* 275 */       jvmCompilationMeta.setInstance(jvmCompilationMBean);
/* 276 */       registerGroupNode("JvmCompilation", str, objectName, jvmCompilationMeta, jvmCompilationMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmCompilationMeta createJvmCompilationMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 298 */     return new JvmCompilationMeta(this, this.objectserver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmCompilationMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmRuntime(MBeanServer paramMBeanServer) throws Exception {
/* 342 */     String str = getGroupOid("JvmRuntime", "1.3.6.1.4.1.42.2.145.3.163.1.1.4");
/* 343 */     ObjectName objectName = null;
/* 344 */     if (paramMBeanServer != null) {
/* 345 */       objectName = getGroupObjectName("JvmRuntime", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmRuntime");
/*     */     }
/* 347 */     JvmRuntimeMeta jvmRuntimeMeta = createJvmRuntimeMetaNode("JvmRuntime", str, objectName, paramMBeanServer);
/* 348 */     if (jvmRuntimeMeta != null) {
/* 349 */       jvmRuntimeMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 355 */       JvmRuntimeMBean jvmRuntimeMBean = (JvmRuntimeMBean)createJvmRuntimeMBean("JvmRuntime", str, objectName, paramMBeanServer);
/* 356 */       jvmRuntimeMeta.setInstance(jvmRuntimeMBean);
/* 357 */       registerGroupNode("JvmRuntime", str, objectName, jvmRuntimeMeta, jvmRuntimeMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmRuntimeMeta createJvmRuntimeMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 379 */     return new JvmRuntimeMeta(this, this.objectserver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmRuntimeMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmThreading(MBeanServer paramMBeanServer) throws Exception {
/* 423 */     String str = getGroupOid("JvmThreading", "1.3.6.1.4.1.42.2.145.3.163.1.1.3");
/* 424 */     ObjectName objectName = null;
/* 425 */     if (paramMBeanServer != null) {
/* 426 */       objectName = getGroupObjectName("JvmThreading", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmThreading");
/*     */     }
/* 428 */     JvmThreadingMeta jvmThreadingMeta = createJvmThreadingMetaNode("JvmThreading", str, objectName, paramMBeanServer);
/* 429 */     if (jvmThreadingMeta != null) {
/* 430 */       jvmThreadingMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 436 */       JvmThreadingMBean jvmThreadingMBean = (JvmThreadingMBean)createJvmThreadingMBean("JvmThreading", str, objectName, paramMBeanServer);
/* 437 */       jvmThreadingMeta.setInstance(jvmThreadingMBean);
/* 438 */       registerGroupNode("JvmThreading", str, objectName, jvmThreadingMeta, jvmThreadingMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmThreadingMeta createJvmThreadingMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 460 */     return new JvmThreadingMeta(this, this.objectserver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmThreadingMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmMemory(MBeanServer paramMBeanServer) throws Exception {
/* 504 */     String str = getGroupOid("JvmMemory", "1.3.6.1.4.1.42.2.145.3.163.1.1.2");
/* 505 */     ObjectName objectName = null;
/* 506 */     if (paramMBeanServer != null) {
/* 507 */       objectName = getGroupObjectName("JvmMemory", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmMemory");
/*     */     }
/* 509 */     JvmMemoryMeta jvmMemoryMeta = createJvmMemoryMetaNode("JvmMemory", str, objectName, paramMBeanServer);
/* 510 */     if (jvmMemoryMeta != null) {
/* 511 */       jvmMemoryMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 517 */       JvmMemoryMBean jvmMemoryMBean = (JvmMemoryMBean)createJvmMemoryMBean("JvmMemory", str, objectName, paramMBeanServer);
/* 518 */       jvmMemoryMeta.setInstance(jvmMemoryMBean);
/* 519 */       registerGroupNode("JvmMemory", str, objectName, jvmMemoryMeta, jvmMemoryMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmMemoryMeta createJvmMemoryMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 541 */     return new JvmMemoryMeta(this, this.objectserver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmMemoryMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initJvmClassLoading(MBeanServer paramMBeanServer) throws Exception {
/* 585 */     String str = getGroupOid("JvmClassLoading", "1.3.6.1.4.1.42.2.145.3.163.1.1.1");
/* 586 */     ObjectName objectName = null;
/* 587 */     if (paramMBeanServer != null) {
/* 588 */       objectName = getGroupObjectName("JvmClassLoading", str, this.mibName + ":name=sun.management.snmp.jvmmib.JvmClassLoading");
/*     */     }
/* 590 */     JvmClassLoadingMeta jvmClassLoadingMeta = createJvmClassLoadingMetaNode("JvmClassLoading", str, objectName, paramMBeanServer);
/* 591 */     if (jvmClassLoadingMeta != null) {
/* 592 */       jvmClassLoadingMeta.registerTableNodes(this, paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 598 */       JvmClassLoadingMBean jvmClassLoadingMBean = (JvmClassLoadingMBean)createJvmClassLoadingMBean("JvmClassLoading", str, objectName, paramMBeanServer);
/* 599 */       jvmClassLoadingMeta.setInstance(jvmClassLoadingMBean);
/* 600 */       registerGroupNode("JvmClassLoading", str, objectName, jvmClassLoadingMeta, jvmClassLoadingMBean, paramMBeanServer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JvmClassLoadingMeta createJvmClassLoadingMetaNode(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer) {
/* 622 */     return new JvmClassLoadingMeta(this, this.objectserver);
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
/*     */ 
/*     */   
/*     */   protected abstract Object createJvmClassLoadingMBean(String paramString1, String paramString2, ObjectName paramObjectName, MBeanServer paramMBeanServer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTableMeta(String paramString, SnmpMibTable paramSnmpMibTable) {
/* 656 */     if (this.metadatas == null)
/* 657 */       return;  if (paramString == null)
/* 658 */       return;  this.metadatas.put(paramString, paramSnmpMibTable);
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
/*     */   public SnmpMibTable getRegisteredTableMeta(String paramString) {
/* 670 */     if (this.metadatas == null) return null; 
/* 671 */     if (paramString == null) return null; 
/* 672 */     return this.metadatas.get(paramString);
/*     */   }
/*     */   
/*     */   public SnmpStandardObjectServer getStandardObjectServer() {
/* 676 */     if (this.objectserver == null)
/* 677 */       this.objectserver = new SnmpStandardObjectServer(); 
/* 678 */     return this.objectserver;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInitialized = false;
/*     */   
/*     */   protected SnmpStandardObjectServer objectserver;
/* 685 */   protected final Hashtable<String, SnmpMibTable> metadatas = new Hashtable<>();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvmmib\JVM_MANAGEMENT_MIB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */