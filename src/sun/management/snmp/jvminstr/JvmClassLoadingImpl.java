/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import javax.management.MBeanServer;
/*     */ import sun.management.snmp.jvmmib.EnumJvmClassesVerboseLevel;
/*     */ import sun.management.snmp.jvmmib.JvmClassLoadingMBean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmClassLoadingImpl
/*     */   implements JvmClassLoadingMBean
/*     */ {
/*  63 */   static final EnumJvmClassesVerboseLevel JvmClassesVerboseLevelVerbose = new EnumJvmClassesVerboseLevel("verbose");
/*     */   
/*  65 */   static final EnumJvmClassesVerboseLevel JvmClassesVerboseLevelSilent = new EnumJvmClassesVerboseLevel("silent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmClassLoadingImpl(SnmpMib paramSnmpMib) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmClassLoadingImpl(SnmpMib paramSnmpMib, MBeanServer paramMBeanServer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ClassLoadingMXBean getClassLoadingMXBean() {
/*  85 */     return ManagementFactory.getClassLoadingMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumJvmClassesVerboseLevel getJvmClassesVerboseLevel() throws SnmpStatusException {
/*  93 */     if (getClassLoadingMXBean().isVerbose()) {
/*  94 */       return JvmClassesVerboseLevelVerbose;
/*     */     }
/*  96 */     return JvmClassesVerboseLevelSilent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel paramEnumJvmClassesVerboseLevel) throws SnmpStatusException {
/*     */     boolean bool;
/* 105 */     if (JvmClassesVerboseLevelVerbose.equals(paramEnumJvmClassesVerboseLevel)) { bool = true; }
/* 106 */     else if (JvmClassesVerboseLevelSilent.equals(paramEnumJvmClassesVerboseLevel)) { bool = false; }
/*     */     else
/*     */     
/* 109 */     { throw new SnmpStatusException(10); }
/*     */     
/* 111 */     getClassLoadingMXBean().setVerbose(bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkJvmClassesVerboseLevel(EnumJvmClassesVerboseLevel paramEnumJvmClassesVerboseLevel) throws SnmpStatusException {
/* 122 */     if (JvmClassesVerboseLevelVerbose.equals(paramEnumJvmClassesVerboseLevel))
/* 123 */       return;  if (JvmClassesVerboseLevelSilent.equals(paramEnumJvmClassesVerboseLevel))
/* 124 */       return;  throw new SnmpStatusException(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmClassesUnloadedCount() throws SnmpStatusException {
/* 132 */     return new Long(getClassLoadingMXBean().getUnloadedClassCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmClassesTotalLoadedCount() throws SnmpStatusException {
/* 139 */     return new Long(getClassLoadingMXBean().getTotalLoadedClassCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmClassesLoadedCount() throws SnmpStatusException {
/* 146 */     return new Long(getClassLoadingMXBean().getLoadedClassCount());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmClassLoadingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */