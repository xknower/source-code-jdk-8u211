/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import java.lang.management.CompilationMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import javax.management.MBeanServer;
/*     */ import sun.management.snmp.jvmmib.EnumJvmJITCompilerTimeMonitoring;
/*     */ import sun.management.snmp.jvmmib.JvmCompilationMBean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmCompilationImpl
/*     */   implements JvmCompilationMBean
/*     */ {
/*  64 */   static final EnumJvmJITCompilerTimeMonitoring JvmJITCompilerTimeMonitoringSupported = new EnumJvmJITCompilerTimeMonitoring("supported");
/*     */ 
/*     */   
/*  67 */   static final EnumJvmJITCompilerTimeMonitoring JvmJITCompilerTimeMonitoringUnsupported = new EnumJvmJITCompilerTimeMonitoring("unsupported");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmCompilationImpl(SnmpMib paramSnmpMib) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmCompilationImpl(SnmpMib paramSnmpMib, MBeanServer paramMBeanServer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CompilationMXBean getCompilationMXBean() {
/*  89 */     return ManagementFactory.getCompilationMXBean();
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
/*     */   public EnumJvmJITCompilerTimeMonitoring getJvmJITCompilerTimeMonitoring() throws SnmpStatusException {
/* 103 */     if (getCompilationMXBean().isCompilationTimeMonitoringSupported()) {
/* 104 */       return JvmJITCompilerTimeMonitoringSupported;
/*     */     }
/* 106 */     return JvmJITCompilerTimeMonitoringUnsupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmJITCompilerTimeMs() throws SnmpStatusException {
/*     */     long l;
/* 114 */     if (getCompilationMXBean().isCompilationTimeMonitoringSupported()) {
/* 115 */       l = getCompilationMXBean().getTotalCompilationTime();
/*     */     } else {
/* 117 */       l = 0L;
/* 118 */     }  return new Long(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmJITCompilerName() throws SnmpStatusException {
/* 125 */     return 
/* 126 */       JVM_MANAGEMENT_MIB_IMPL.validJavaObjectNameTC(getCompilationMXBean().getName());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmCompilationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */