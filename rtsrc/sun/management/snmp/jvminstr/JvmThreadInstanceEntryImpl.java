/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import com.sun.jmx.snmp.SnmpOid;
/*     */ import com.sun.jmx.snmp.SnmpOidRecord;
/*     */ import com.sun.jmx.snmp.SnmpStatusException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;
/*     */ import sun.management.snmp.jvmmib.JvmThreadInstanceEntryMBean;
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
/*     */ public class JvmThreadInstanceEntryImpl
/*     */   implements JvmThreadInstanceEntryMBean, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 910173589985461347L;
/*     */   private final ThreadInfo info;
/*     */   private final Byte[] index;
/*     */   
/*     */   public static final class ThreadStateMap
/*     */   {
/*     */     public static final byte mask0 = 63;
/*     */     public static final byte mask1 = -128;
/*     */     
/*     */     public static final class Byte0
/*     */     {
/*     */       public static final byte inNative = -128;
/*     */       public static final byte suspended = 64;
/*     */       public static final byte newThread = 32;
/*     */       public static final byte runnable = 16;
/*     */       public static final byte blocked = 8;
/*     */       public static final byte terminated = 4;
/*     */       public static final byte waiting = 2;
/*     */       public static final byte timedWaiting = 1;
/*     */     }
/*     */     
/*     */     public static final class Byte1
/*     */     {
/*     */       public static final byte other = -128;
/*     */       public static final byte reserved10 = 64;
/*     */       public static final byte reserved11 = 32;
/*     */       public static final byte reserved12 = 16;
/*     */       public static final byte reserved13 = 8;
/*     */       public static final byte reserved14 = 4;
/*     */       public static final byte reserved15 = 2;
/*     */       public static final byte reserved16 = 1;
/*     */     }
/*     */     
/*     */     private static void setBit(byte[] param1ArrayOfbyte, int param1Int, byte param1Byte) {
/*  84 */       param1ArrayOfbyte[param1Int] = (byte)(param1ArrayOfbyte[param1Int] | param1Byte);
/*     */     }
/*     */     public static void setNative(byte[] param1ArrayOfbyte) {
/*  87 */       setBit(param1ArrayOfbyte, 0, -128);
/*     */     }
/*     */     public static void setSuspended(byte[] param1ArrayOfbyte) {
/*  90 */       setBit(param1ArrayOfbyte, 0, (byte)64);
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
/*     */     public static void setState(byte[] param1ArrayOfbyte, Thread.State param1State) {
/*     */       // Byte code:
/*     */       //   0: getstatic sun/management/snmp/jvminstr/JvmThreadInstanceEntryImpl$1.$SwitchMap$java$lang$Thread$State : [I
/*     */       //   3: aload_1
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 93, 1 -> 48, 2 -> 56, 3 -> 64, 4 -> 72, 5 -> 79, 6 -> 86
/*     */       //   48: aload_0
/*     */       //   49: iconst_0
/*     */       //   50: bipush #8
/*     */       //   52: invokestatic setBit : ([BIB)V
/*     */       //   55: return
/*     */       //   56: aload_0
/*     */       //   57: iconst_0
/*     */       //   58: bipush #32
/*     */       //   60: invokestatic setBit : ([BIB)V
/*     */       //   63: return
/*     */       //   64: aload_0
/*     */       //   65: iconst_0
/*     */       //   66: bipush #16
/*     */       //   68: invokestatic setBit : ([BIB)V
/*     */       //   71: return
/*     */       //   72: aload_0
/*     */       //   73: iconst_0
/*     */       //   74: iconst_4
/*     */       //   75: invokestatic setBit : ([BIB)V
/*     */       //   78: return
/*     */       //   79: aload_0
/*     */       //   80: iconst_0
/*     */       //   81: iconst_1
/*     */       //   82: invokestatic setBit : ([BIB)V
/*     */       //   85: return
/*     */       //   86: aload_0
/*     */       //   87: iconst_0
/*     */       //   88: iconst_2
/*     */       //   89: invokestatic setBit : ([BIB)V
/*     */       //   92: return
/*     */       //   93: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       //   #95	-> 48
/*     */       //   #96	-> 55
/*     */       //   #98	-> 56
/*     */       //   #99	-> 63
/*     */       //   #101	-> 64
/*     */       //   #102	-> 71
/*     */       //   #104	-> 72
/*     */       //   #105	-> 78
/*     */       //   #107	-> 79
/*     */       //   #108	-> 85
/*     */       //   #110	-> 86
/*     */       //   #111	-> 92
/*     */       //   #113	-> 93
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
/*     */     public static void checkOther(byte[] param1ArrayOfbyte) {
/* 116 */       if ((param1ArrayOfbyte[0] & 0x3F) == 0 && (param1ArrayOfbyte[1] & Byte.MIN_VALUE) == 0)
/*     */       {
/* 118 */         setBit(param1ArrayOfbyte, 1, -128); } 
/*     */     }
/*     */     
/*     */     public static Byte[] getState(ThreadInfo param1ThreadInfo) {
/* 122 */       byte[] arrayOfByte = { 0, 0 };
/*     */       try {
/* 124 */         Thread.State state = param1ThreadInfo.getThreadState();
/* 125 */         boolean bool1 = param1ThreadInfo.isInNative();
/* 126 */         boolean bool2 = param1ThreadInfo.isSuspended();
/* 127 */         JvmThreadInstanceEntryImpl.log.debug("getJvmThreadInstState", "[State=" + state + ",isInNative=" + bool1 + ",isSuspended=" + bool2 + "]");
/*     */ 
/*     */ 
/*     */         
/* 131 */         setState(arrayOfByte, state);
/* 132 */         if (bool1) setNative(arrayOfByte); 
/* 133 */         if (bool2) setSuspended(arrayOfByte); 
/* 134 */         checkOther(arrayOfByte);
/* 135 */       } catch (RuntimeException runtimeException) {
/* 136 */         arrayOfByte[0] = 0;
/* 137 */         arrayOfByte[1] = Byte.MIN_VALUE;
/* 138 */         JvmThreadInstanceEntryImpl.log.trace("getJvmThreadInstState", "Unexpected exception: " + runtimeException);
/*     */         
/* 140 */         JvmThreadInstanceEntryImpl.log.debug("getJvmThreadInstState", runtimeException);
/*     */       } 
/* 142 */       return new Byte[] { new Byte(arrayOfByte[0]), new Byte(arrayOfByte[1]) };
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
/*     */   public JvmThreadInstanceEntryImpl(ThreadInfo paramThreadInfo, Byte[] paramArrayOfByte) {
/* 155 */     this.info = paramThreadInfo;
/* 156 */     this.index = paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   
/* 160 */   private static String jvmThreadInstIndexOid = null;
/*     */   
/*     */   public static String getJvmThreadInstIndexOid() throws SnmpStatusException {
/* 163 */     if (jvmThreadInstIndexOid == null) {
/* 164 */       JVM_MANAGEMENT_MIBOidTable jVM_MANAGEMENT_MIBOidTable = new JVM_MANAGEMENT_MIBOidTable();
/*     */       
/* 166 */       SnmpOidRecord snmpOidRecord = jVM_MANAGEMENT_MIBOidTable.resolveVarName("jvmThreadInstIndex");
/* 167 */       jvmThreadInstIndexOid = snmpOidRecord.getOid();
/*     */     } 
/* 169 */     return jvmThreadInstIndexOid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmThreadInstLockOwnerPtr() throws SnmpStatusException {
/* 178 */     long l = this.info.getLockOwnerId();
/*     */     
/* 180 */     if (l == -1L) {
/* 181 */       return new String("0.0");
/*     */     }
/* 183 */     SnmpOid snmpOid = JvmThreadInstanceTableMetaImpl.makeOid(l);
/*     */     
/* 185 */     return getJvmThreadInstIndexOid() + "." + snmpOid.toString();
/*     */   }
/*     */   
/*     */   private String validDisplayStringTC(String paramString) {
/* 189 */     return JVM_MANAGEMENT_MIB_IMPL.validDisplayStringTC(paramString);
/*     */   }
/*     */   
/*     */   private String validJavaObjectNameTC(String paramString) {
/* 193 */     return JVM_MANAGEMENT_MIB_IMPL.validJavaObjectNameTC(paramString);
/*     */   }
/*     */   
/*     */   private String validPathElementTC(String paramString) {
/* 197 */     return JVM_MANAGEMENT_MIB_IMPL.validPathElementTC(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmThreadInstLockName() throws SnmpStatusException {
/* 204 */     return validJavaObjectNameTC(this.info.getLockName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJvmThreadInstName() throws SnmpStatusException {
/* 211 */     return validJavaObjectNameTC(this.info.getThreadName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstCpuTimeNs() throws SnmpStatusException {
/* 218 */     long l = 0L;
/* 219 */     ThreadMXBean threadMXBean = JvmThreadingImpl.getThreadMXBean();
/*     */     
/*     */     try {
/* 222 */       if (threadMXBean.isThreadCpuTimeSupported()) {
/* 223 */         l = threadMXBean.getThreadCpuTime(this.info.getThreadId());
/* 224 */         log.debug("getJvmThreadInstCpuTimeNs", "Cpu time ns : " + l);
/*     */ 
/*     */         
/* 227 */         if (l == -1L) l = 0L; 
/*     */       } 
/* 229 */     } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/*     */       
/* 231 */       log.debug("getJvmThreadInstCpuTimeNs", "Operation not supported: " + unsatisfiedLinkError);
/*     */     } 
/*     */     
/* 234 */     return new Long(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstBlockTimeMs() throws SnmpStatusException {
/* 241 */     long l = 0L;
/*     */     
/* 243 */     ThreadMXBean threadMXBean = JvmThreadingImpl.getThreadMXBean();
/*     */     
/* 245 */     if (threadMXBean.isThreadContentionMonitoringSupported()) {
/* 246 */       l = this.info.getBlockedTime();
/*     */ 
/*     */       
/* 249 */       if (l == -1L) l = 0L; 
/*     */     } 
/* 251 */     return new Long(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstBlockCount() throws SnmpStatusException {
/* 258 */     return new Long(this.info.getBlockedCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstWaitTimeMs() throws SnmpStatusException {
/* 265 */     long l = 0L;
/*     */     
/* 267 */     ThreadMXBean threadMXBean = JvmThreadingImpl.getThreadMXBean();
/*     */     
/* 269 */     if (threadMXBean.isThreadContentionMonitoringSupported()) {
/* 270 */       l = this.info.getWaitedTime();
/*     */ 
/*     */       
/* 273 */       if (l == -1L) l = 0L; 
/*     */     } 
/* 275 */     return new Long(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstWaitCount() throws SnmpStatusException {
/* 282 */     return new Long(this.info.getWaitedCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte[] getJvmThreadInstState() throws SnmpStatusException {
/* 290 */     return ThreadStateMap.getState(this.info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getJvmThreadInstId() throws SnmpStatusException {
/* 297 */     return new Long(this.info.getThreadId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte[] getJvmThreadInstIndex() throws SnmpStatusException {
/* 304 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getJvmThreadInstStackTrace() throws SnmpStatusException {
/* 311 */     StackTraceElement[] arrayOfStackTraceElement = this.info.getStackTrace();
/*     */ 
/*     */     
/* 314 */     StringBuffer stringBuffer = new StringBuffer();
/* 315 */     int i = arrayOfStackTraceElement.length;
/* 316 */     log.debug("getJvmThreadInstStackTrace", "Stack size : " + i);
/* 317 */     for (byte b = 0; b < i; b++) {
/* 318 */       log.debug("getJvmThreadInstStackTrace", "Append " + arrayOfStackTraceElement[b]
/* 319 */           .toString());
/* 320 */       stringBuffer.append(arrayOfStackTraceElement[b].toString());
/*     */       
/* 322 */       if (b < i) {
/* 323 */         stringBuffer.append("\n");
/*     */       }
/*     */     } 
/* 326 */     return validPathElementTC(stringBuffer.toString());
/*     */   }
/* 328 */   static final MibLogger log = new MibLogger(JvmThreadInstanceEntryImpl.class);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmThreadInstanceEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */