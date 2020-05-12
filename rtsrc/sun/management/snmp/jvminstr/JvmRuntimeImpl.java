/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import com.sun.jmx.snmp.agent.SnmpMib;
/*     */ import java.io.File;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.management.MBeanServer;
/*     */ import sun.management.snmp.jvmmib.EnumJvmRTBootClassPathSupport;
/*     */ import sun.management.snmp.jvmmib.JvmRuntimeMBean;
/*     */ import sun.management.snmp.util.JvmContextFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JvmRuntimeImpl
/*     */   implements JvmRuntimeMBean
/*     */ {
/*  67 */   static final EnumJvmRTBootClassPathSupport JvmRTBootClassPathSupportSupported = new EnumJvmRTBootClassPathSupport("supported");
/*     */ 
/*     */   
/*  70 */   static final EnumJvmRTBootClassPathSupport JvmRTBootClassPathSupportUnSupported = new EnumJvmRTBootClassPathSupport("unsupported");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmRuntimeImpl(SnmpMib paramSnmpMib) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JvmRuntimeImpl(SnmpMib paramSnmpMib, MBeanServer paramMBeanServer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static RuntimeMXBean getRuntimeMXBean() {
/*  93 */     return ManagementFactory.getRuntimeMXBean();
/*     */   }
/*     */   
/*     */   private static String validDisplayStringTC(String paramString) {
/*  97 */     return JVM_MANAGEMENT_MIB_IMPL.validDisplayStringTC(paramString);
/*     */   }
/*     */   
/*     */   private static String validPathElementTC(String paramString) {
/* 101 */     return JVM_MANAGEMENT_MIB_IMPL.validPathElementTC(paramString);
/*     */   }
/*     */   
/*     */   private static String validJavaObjectNameTC(String paramString) {
/* 105 */     return JVM_MANAGEMENT_MIB_IMPL.validJavaObjectNameTC(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   static String[] splitPath(String paramString) {
/* 110 */     return paramString.split(File.pathSeparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String[] getClassPath(Object paramObject) {
/* 119 */     Map<String, String[]> map = Util.<Map>cast((paramObject instanceof Map) ? paramObject : null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (map != null) {
/* 125 */       String[] arrayOfString1 = (String[])map.get("JvmRuntime.getClassPath");
/* 126 */       if (arrayOfString1 != null) return arrayOfString1;
/*     */     
/*     */     } 
/* 129 */     String[] arrayOfString = splitPath(getRuntimeMXBean().getClassPath());
/*     */     
/* 131 */     if (map != null) map.put("JvmRuntime.getClassPath", arrayOfString); 
/* 132 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   static String[] getBootClassPath(Object paramObject) {
/* 136 */     if (!getRuntimeMXBean().isBootClassPathSupported()) {
/* 137 */       return new String[0];
/*     */     }
/*     */     
/* 140 */     Map<String, String[]> map = Util.<Map>cast((paramObject instanceof Map) ? paramObject : null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     if (map != null) {
/* 146 */       String[] arrayOfString1 = (String[])map.get("JvmRuntime.getBootClassPath");
/* 147 */       if (arrayOfString1 != null) return arrayOfString1;
/*     */     
/*     */     } 
/* 150 */     String[] arrayOfString = splitPath(getRuntimeMXBean().getBootClassPath());
/*     */     
/* 152 */     if (map != null) map.put("JvmRuntime.getBootClassPath", arrayOfString); 
/* 153 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   static String[] getLibraryPath(Object paramObject) {
/* 158 */     Map<String, String[]> map = Util.<Map>cast((paramObject instanceof Map) ? paramObject : null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (map != null) {
/* 164 */       String[] arrayOfString1 = (String[])map.get("JvmRuntime.getLibraryPath");
/* 165 */       if (arrayOfString1 != null) return arrayOfString1;
/*     */     
/*     */     } 
/* 168 */     String[] arrayOfString = splitPath(getRuntimeMXBean().getLibraryPath());
/*     */     
/* 170 */     if (map != null) map.put("JvmRuntime.getLibraryPath", arrayOfString); 
/* 171 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   static String[] getInputArguments(Object paramObject) {
/* 176 */     Map<String, String[]> map = Util.<Map>cast((paramObject instanceof Map) ? paramObject : null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (map != null) {
/* 182 */       String[] arrayOfString1 = (String[])map.get("JvmRuntime.getInputArguments");
/* 183 */       if (arrayOfString1 != null) return arrayOfString1;
/*     */     
/*     */     } 
/* 186 */     List<String> list = getRuntimeMXBean().getInputArguments();
/* 187 */     String[] arrayOfString = list.<String>toArray(new String[0]);
/*     */     
/* 189 */     if (map != null) map.put("JvmRuntime.getInputArguments", arrayOfString); 
/* 190 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTSpecVendor() throws SnmpStatusException {
/* 197 */     return validDisplayStringTC(getRuntimeMXBean().getSpecVendor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTSpecName() throws SnmpStatusException {
/* 204 */     return validDisplayStringTC(getRuntimeMXBean().getSpecName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTVMVersion() throws SnmpStatusException {
/* 211 */     return validDisplayStringTC(getRuntimeMXBean().getVmVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTVMVendor() throws SnmpStatusException {
/* 218 */     return validDisplayStringTC(getRuntimeMXBean().getVmVendor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTManagementSpecVersion() throws SnmpStatusException {
/* 225 */     return validDisplayStringTC(getRuntimeMXBean()
/* 226 */         .getManagementSpecVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTVMName() throws SnmpStatusException {
/* 233 */     return validJavaObjectNameTC(getRuntimeMXBean().getVmName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getJvmRTInputArgsCount() throws SnmpStatusException {
/* 242 */     String[] arrayOfString = getInputArguments(
/* 243 */         JvmContextFactory.getUserData());
/* 244 */     return new Integer(arrayOfString.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumJvmRTBootClassPathSupport getJvmRTBootClassPathSupport() throws SnmpStatusException {
/* 252 */     if (getRuntimeMXBean().isBootClassPathSupported()) {
/* 253 */       return JvmRTBootClassPathSupportSupported;
/*     */     }
/* 255 */     return JvmRTBootClassPathSupportUnSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmRTUptimeMs() throws SnmpStatusException {
/* 262 */     return new Long(getRuntimeMXBean().getUptime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmRTStartTimeMs() throws SnmpStatusException {
/* 269 */     return new Long(getRuntimeMXBean().getStartTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTSpecVersion() throws SnmpStatusException {
/* 276 */     return validDisplayStringTC(getRuntimeMXBean().getSpecVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmRTName() throws SnmpStatusException {
/* 283 */     return validDisplayStringTC(getRuntimeMXBean().getName());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmRuntimeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */