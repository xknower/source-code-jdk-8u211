/*      */ package com.sun.jmx.snmp.daemon;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.snmp.IPAcl.SnmpAcl;
/*      */ import com.sun.jmx.snmp.InetAddressAcl;
/*      */ import com.sun.jmx.snmp.SnmpDefinitions;
/*      */ import com.sun.jmx.snmp.SnmpIpAddress;
/*      */ import com.sun.jmx.snmp.SnmpMessage;
/*      */ import com.sun.jmx.snmp.SnmpOid;
/*      */ import com.sun.jmx.snmp.SnmpParameters;
/*      */ import com.sun.jmx.snmp.SnmpPduFactory;
/*      */ import com.sun.jmx.snmp.SnmpPduFactoryBER;
/*      */ import com.sun.jmx.snmp.SnmpPduPacket;
/*      */ import com.sun.jmx.snmp.SnmpPduRequest;
/*      */ import com.sun.jmx.snmp.SnmpPduTrap;
/*      */ import com.sun.jmx.snmp.SnmpPeer;
/*      */ import com.sun.jmx.snmp.SnmpStatusException;
/*      */ import com.sun.jmx.snmp.SnmpTimeticks;
/*      */ import com.sun.jmx.snmp.SnmpTooBigException;
/*      */ import com.sun.jmx.snmp.SnmpVarBind;
/*      */ import com.sun.jmx.snmp.SnmpVarBindList;
/*      */ import com.sun.jmx.snmp.agent.SnmpErrorHandlerAgent;
/*      */ import com.sun.jmx.snmp.agent.SnmpMibAgent;
/*      */ import com.sun.jmx.snmp.agent.SnmpMibHandler;
/*      */ import com.sun.jmx.snmp.agent.SnmpUserDataFactory;
/*      */ import com.sun.jmx.snmp.tasks.ThreadService;
/*      */ import java.io.IOException;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.net.DatagramPacket;
/*      */ import java.net.DatagramSocket;
/*      */ import java.net.InetAddress;
/*      */ import java.net.SocketException;
/*      */ import java.net.UnknownHostException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SnmpAdaptorServer
/*      */   extends CommunicatorServer
/*      */   implements SnmpAdaptorServerMBean, MBeanRegistration, SnmpDefinitions, SnmpMibHandler
/*      */ {
/*  141 */   private int trapPort = 162;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   private int informPort = 162;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   InetAddress address = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   private InetAddressAcl ipacl = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   private SnmpPduFactory pduFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private SnmpUserDataFactory userDataFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authRespEnabled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authTrapEnabled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   private SnmpOid enterpriseOid = new SnmpOid("1.3.6.1.4.1.42");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   int bufferSize = 1024;
/*      */   
/*  196 */   private transient long startUpTime = 0L;
/*  197 */   private transient DatagramSocket socket = null;
/*  198 */   transient DatagramSocket trapSocket = null;
/*  199 */   private transient SnmpSession informSession = null;
/*  200 */   private transient DatagramPacket packet = null;
/*  201 */   transient Vector<SnmpMibAgent> mibs = new Vector<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SnmpMibTree root;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean useAcl = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  217 */   private int maxTries = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  223 */   private int timeout = 3000;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  231 */   int snmpOutTraps = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  236 */   private int snmpOutGetResponses = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  241 */   private int snmpOutGenErrs = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   private int snmpOutBadValues = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  251 */   private int snmpOutNoSuchNames = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  256 */   private int snmpOutTooBigs = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  261 */   int snmpOutPkts = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  266 */   private int snmpInASNParseErrs = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  271 */   private int snmpInBadCommunityUses = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  276 */   private int snmpInBadCommunityNames = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   private int snmpInBadVersions = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   private int snmpInGetRequests = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   private int snmpInGetNexts = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   private int snmpInSetRequests = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   private int snmpInPkts = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  306 */   private int snmpInTotalReqVars = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  311 */   private int snmpInTotalSetVars = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  316 */   private int snmpSilentDrops = 0;
/*      */   
/*      */   private static final String InterruptSysCallMsg = "Interrupted system call";
/*      */   
/*  320 */   static final SnmpOid sysUpTimeOid = new SnmpOid("1.3.6.1.2.1.1.3.0");
/*  321 */   static final SnmpOid snmpTrapOidOid = new SnmpOid("1.3.6.1.6.3.1.1.4.1.0");
/*      */   
/*      */   private ThreadService threadService;
/*      */   
/*  325 */   private static int threadNumber = 6;
/*      */   
/*      */   static {
/*  328 */     String str = System.getProperty("com.sun.jmx.snmp.threadnumber");
/*      */     
/*  330 */     if (str != null) {
/*      */       try {
/*  332 */         threadNumber = Integer.parseInt(System.getProperty(str));
/*  333 */       } catch (Exception exception) {
/*  334 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, SnmpAdaptorServer.class
/*  335 */             .getName(), "<static init>", "Got wrong value for com.sun.jmx.snmp.threadnumber: " + str + ". Use the default value: " + threadNumber);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer() {
/*  352 */     this(true, (InetAddressAcl)null, 161, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(int paramInt) {
/*  364 */     this(true, (InetAddressAcl)null, paramInt, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(InetAddressAcl paramInetAddressAcl) {
/*  377 */     this(false, paramInetAddressAcl, 161, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(InetAddress paramInetAddress) {
/*  391 */     this(true, (InetAddressAcl)null, 161, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(InetAddressAcl paramInetAddressAcl, int paramInt) {
/*  406 */     this(false, paramInetAddressAcl, paramInt, (InetAddress)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(int paramInt, InetAddress paramInetAddress) {
/*  419 */     this(true, (InetAddressAcl)null, paramInt, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(InetAddressAcl paramInetAddressAcl, InetAddress paramInetAddress) {
/*  433 */     this(false, paramInetAddressAcl, 161, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(InetAddressAcl paramInetAddressAcl, int paramInt, InetAddress paramInetAddress) {
/*  449 */     this(false, paramInetAddressAcl, paramInt, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpAdaptorServer(boolean paramBoolean, int paramInt, InetAddress paramInetAddress) {
/*  469 */     this(paramBoolean, (InetAddressAcl)null, paramInt, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpAdaptorServer(boolean paramBoolean, InetAddressAcl paramInetAddressAcl, int paramInt, InetAddress paramInetAddress) {
/*  477 */     super(4);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  482 */     if (paramInetAddressAcl == null && paramBoolean) {
/*      */       try {
/*  484 */         paramInetAddressAcl = new SnmpAcl("SNMP protocol adaptor IP ACL");
/*  485 */       } catch (UnknownHostException unknownHostException) {
/*  486 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/*  487 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "constructor", "UnknowHostException when creating ACL", unknownHostException);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  492 */       this.useAcl = (paramInetAddressAcl != null || paramBoolean);
/*      */     } 
/*      */     
/*  495 */     init(paramInetAddressAcl, paramInt, paramInetAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getServedClientCount() {
/*  511 */     return super.getServedClientCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActiveClientCount() {
/*  523 */     return super.getActiveClientCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxActiveClientCount() {
/*  535 */     return super.getMaxActiveClientCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxActiveClientCount(int paramInt) throws IllegalStateException {
/*  550 */     super.setMaxActiveClientCount(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InetAddressAcl getInetAddressAcl() {
/*  561 */     return this.ipacl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getTrapPort() {
/*  572 */     return new Integer(this.trapPort);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrapPort(Integer paramInteger) {
/*  582 */     setTrapPort(paramInteger.intValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrapPort(int paramInt) {
/*  591 */     int i = paramInt;
/*  592 */     if (i < 0) throw new IllegalArgumentException("Trap port cannot be a negative value");
/*      */     
/*  594 */     this.trapPort = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInformPort() {
/*  605 */     return this.informPort;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInformPort(int paramInt) {
/*  616 */     if (paramInt < 0) {
/*  617 */       throw new IllegalArgumentException("Inform request port cannot be a negative value");
/*      */     }
/*  619 */     this.informPort = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProtocol() {
/*  629 */     return "snmp";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getBufferSize() {
/*  642 */     return new Integer(this.bufferSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBufferSize(Integer paramInteger) throws IllegalStateException {
/*  658 */     if (this.state == 0 || this.state == 3) {
/*  659 */       throw new IllegalStateException("Stop server before carrying out this operation");
/*      */     }
/*      */     
/*  662 */     this.bufferSize = paramInteger.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getMaxTries() {
/*  673 */     return this.maxTries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void setMaxTries(int paramInt) {
/*  683 */     if (paramInt < 0)
/*  684 */       throw new IllegalArgumentException(); 
/*  685 */     this.maxTries = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTimeout() {
/*  695 */     return this.timeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void setTimeout(int paramInt) {
/*  704 */     if (paramInt < 0)
/*  705 */       throw new IllegalArgumentException(); 
/*  706 */     this.timeout = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpPduFactory getPduFactory() {
/*  716 */     return this.pduFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPduFactory(SnmpPduFactory paramSnmpPduFactory) {
/*  726 */     if (paramSnmpPduFactory == null) {
/*  727 */       this.pduFactory = new SnmpPduFactoryBER();
/*      */     } else {
/*  729 */       this.pduFactory = paramSnmpPduFactory;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserDataFactory(SnmpUserDataFactory paramSnmpUserDataFactory) {
/*  740 */     this.userDataFactory = paramSnmpUserDataFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpUserDataFactory getUserDataFactory() {
/*  751 */     return this.userDataFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAuthTrapEnabled() {
/*  768 */     return this.authTrapEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthTrapEnabled(boolean paramBoolean) {
/*  779 */     this.authTrapEnabled = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAuthRespEnabled() {
/*  797 */     return this.authRespEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthRespEnabled(boolean paramBoolean) {
/*  808 */     this.authRespEnabled = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEnterpriseOid() {
/*  820 */     return this.enterpriseOid.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnterpriseOid(String paramString) throws IllegalArgumentException {
/*  832 */     this.enterpriseOid = new SnmpOid(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getMibs() {
/*  842 */     String[] arrayOfString = new String[this.mibs.size()];
/*  843 */     byte b = 0;
/*  844 */     for (Enumeration<SnmpMibAgent> enumeration = this.mibs.elements(); enumeration.hasMoreElements(); ) {
/*  845 */       SnmpMibAgent snmpMibAgent = enumeration.nextElement();
/*  846 */       arrayOfString[b++] = snmpMibAgent.getMibName();
/*      */     } 
/*  848 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutTraps() {
/*  861 */     return new Long(this.snmpOutTraps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutGetResponses() {
/*  871 */     return new Long(this.snmpOutGetResponses);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutGenErrs() {
/*  881 */     return new Long(this.snmpOutGenErrs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutBadValues() {
/*  891 */     return new Long(this.snmpOutBadValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutNoSuchNames() {
/*  901 */     return new Long(this.snmpOutNoSuchNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutTooBigs() {
/*  911 */     return new Long(this.snmpOutTooBigs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInASNParseErrs() {
/*  921 */     return new Long(this.snmpInASNParseErrs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInBadCommunityUses() {
/*  931 */     return new Long(this.snmpInBadCommunityUses);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInBadCommunityNames() {
/*  942 */     return new Long(this.snmpInBadCommunityNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInBadVersions() {
/*  952 */     return new Long(this.snmpInBadVersions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpOutPkts() {
/*  962 */     return new Long(this.snmpOutPkts);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInPkts() {
/*  972 */     return new Long(this.snmpInPkts);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInGetRequests() {
/*  982 */     return new Long(this.snmpInGetRequests);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInGetNexts() {
/*  992 */     return new Long(this.snmpInGetNexts);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInSetRequests() {
/* 1002 */     return new Long(this.snmpInSetRequests);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInTotalSetVars() {
/* 1012 */     return new Long(this.snmpInTotalSetVars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpInTotalReqVars() {
/* 1022 */     return new Long(this.snmpInTotalReqVars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpSilentDrops() {
/* 1035 */     return new Long(this.snmpSilentDrops);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long getSnmpProxyDrops() {
/* 1048 */     return new Long(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 1078 */     if (paramObjectName == null) {
/* 1079 */       paramObjectName = new ObjectName(paramMBeanServer.getDefaultDomain() + ":" + "name=SnmpAdaptorServer");
/*      */     }
/*      */     
/* 1082 */     return super.preRegister(paramMBeanServer, paramObjectName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postRegister(Boolean paramBoolean) {
/* 1090 */     super.postRegister(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preDeregister() throws Exception {
/* 1098 */     super.preDeregister();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postDeregister() {
/* 1106 */     super.postDeregister();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent) throws IllegalArgumentException {
/* 1121 */     if (paramSnmpMibAgent == null) {
/* 1122 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1125 */     if (!this.mibs.contains(paramSnmpMibAgent)) {
/* 1126 */       this.mibs.addElement(paramSnmpMibAgent);
/*      */     }
/* 1128 */     this.root.register(paramSnmpMibAgent);
/*      */     
/* 1130 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, SnmpOid[] paramArrayOfSnmpOid) throws IllegalArgumentException {
/* 1152 */     if (paramSnmpMibAgent == null) {
/* 1153 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */     
/* 1157 */     if (paramArrayOfSnmpOid == null) {
/* 1158 */       return addMib(paramSnmpMibAgent);
/*      */     }
/* 1160 */     if (!this.mibs.contains(paramSnmpMibAgent)) {
/* 1161 */       this.mibs.addElement(paramSnmpMibAgent);
/*      */     }
/* 1163 */     for (byte b = 0; b < paramArrayOfSnmpOid.length; b++) {
/* 1164 */       this.root.register(paramSnmpMibAgent, paramArrayOfSnmpOid[b].longValue());
/*      */     }
/* 1166 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, String paramString) throws IllegalArgumentException {
/* 1185 */     return addMib(paramSnmpMibAgent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpMibHandler addMib(SnmpMibAgent paramSnmpMibAgent, String paramString, SnmpOid[] paramArrayOfSnmpOid) throws IllegalArgumentException {
/* 1210 */     return addMib(paramSnmpMibAgent, paramArrayOfSnmpOid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeMib(SnmpMibAgent paramSnmpMibAgent, String paramString) {
/* 1229 */     return removeMib(paramSnmpMibAgent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeMib(SnmpMibAgent paramSnmpMibAgent) {
/* 1242 */     this.root.unregister(paramSnmpMibAgent);
/* 1243 */     return this.mibs.removeElement(paramSnmpMibAgent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeMib(SnmpMibAgent paramSnmpMibAgent, SnmpOid[] paramArrayOfSnmpOid) {
/* 1259 */     this.root.unregister(paramSnmpMibAgent, paramArrayOfSnmpOid);
/* 1260 */     return this.mibs.removeElement(paramSnmpMibAgent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeMib(SnmpMibAgent paramSnmpMibAgent, String paramString, SnmpOid[] paramArrayOfSnmpOid) {
/* 1279 */     return removeMib(paramSnmpMibAgent, paramArrayOfSnmpOid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doBind() throws CommunicationException, InterruptedException {
/*      */     try {
/* 1293 */       synchronized (this) {
/* 1294 */         this.socket = new DatagramSocket(this.port, this.address);
/*      */       } 
/* 1296 */       this.dbgTag = makeDebugTag();
/* 1297 */     } catch (SocketException socketException) {
/* 1298 */       if (socketException.getMessage().equals("Interrupted system call")) {
/* 1299 */         throw new InterruptedException(socketException.toString());
/*      */       }
/* 1301 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1302 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "doBind", "cannot bind on port " + this.port);
/*      */       }
/*      */       
/* 1305 */       throw new CommunicationException(socketException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/* 1318 */     synchronized (this) {
/* 1319 */       if (this.socket != null) return this.socket.getLocalPort(); 
/*      */     } 
/* 1321 */     return super.getPort();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doUnbind() throws CommunicationException, InterruptedException {
/* 1330 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1331 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "doUnbind", "Finally close the socket");
/*      */     }
/*      */     
/* 1334 */     synchronized (this) {
/* 1335 */       if (this.socket != null) {
/* 1336 */         this.socket.close();
/* 1337 */         this.socket = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1341 */     closeTrapSocketIfNeeded();
/* 1342 */     closeInformSocketIfNeeded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSnmpRequestHandler(SnmpAdaptorServer paramSnmpAdaptorServer, int paramInt, DatagramSocket paramDatagramSocket, DatagramPacket paramDatagramPacket, SnmpMibTree paramSnmpMibTree, Vector<SnmpMibAgent> paramVector, InetAddressAcl paramInetAddressAcl, SnmpPduFactory paramSnmpPduFactory, SnmpUserDataFactory paramSnmpUserDataFactory, MBeanServer paramMBeanServer, ObjectName paramObjectName) {
/* 1356 */     SnmpRequestHandler snmpRequestHandler = new SnmpRequestHandler(this, paramInt, paramDatagramSocket, paramDatagramPacket, paramSnmpMibTree, paramVector, paramInetAddressAcl, paramSnmpPduFactory, paramSnmpUserDataFactory, paramMBeanServer, paramObjectName);
/*      */ 
/*      */     
/* 1359 */     this.threadService.submitTask(snmpRequestHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReceive() throws CommunicationException, InterruptedException {
/*      */     try {
/* 1373 */       this.packet = new DatagramPacket(new byte[this.bufferSize], this.bufferSize);
/* 1374 */       this.socket.receive(this.packet);
/* 1375 */       int i = getState();
/*      */       
/* 1377 */       if (i != 0) {
/* 1378 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1379 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "doReceive", "received a message but state not online, returning.");
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1385 */       createSnmpRequestHandler(this, this.servedClientCount, this.socket, this.packet, this.root, this.mibs, this.ipacl, this.pduFactory, this.userDataFactory, this.topMBS, this.objectName);
/*      */     
/*      */     }
/* 1388 */     catch (SocketException socketException) {
/*      */ 
/*      */       
/* 1391 */       if (socketException.getMessage().equals("Interrupted system call")) {
/* 1392 */         throw new InterruptedException(socketException.toString());
/*      */       }
/* 1394 */       throw new CommunicationException(socketException);
/* 1395 */     } catch (InterruptedIOException interruptedIOException) {
/* 1396 */       throw new InterruptedException(interruptedIOException.toString());
/* 1397 */     } catch (CommunicationException communicationException) {
/* 1398 */       throw communicationException;
/* 1399 */     } catch (Exception exception) {
/* 1400 */       throw new CommunicationException(exception);
/*      */     } 
/* 1402 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1403 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "doReceive", "received a message");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doError(Exception paramException) throws CommunicationException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doProcess() throws CommunicationException, InterruptedException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getBindTries() {
/* 1429 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stop() {
/* 1442 */     int i = getPort();
/* 1443 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1444 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "stop", "Stopping: using port " + i);
/*      */     }
/*      */     
/* 1447 */     if (this.state == 0 || this.state == 3) {
/* 1448 */       super.stop();
/*      */       try {
/* 1450 */         DatagramSocket datagramSocket = new DatagramSocket(0); try {
/*      */           DatagramPacket datagramPacket;
/* 1452 */           byte[] arrayOfByte = new byte[1];
/*      */ 
/*      */           
/* 1455 */           if (this.address != null) {
/* 1456 */             datagramPacket = new DatagramPacket(arrayOfByte, 1, this.address, i);
/*      */           } else {
/*      */             
/* 1459 */             datagramPacket = new DatagramPacket(arrayOfByte, 1, InetAddress.getLocalHost(), i);
/*      */           } 
/* 1461 */           if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1462 */             JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "stop", "Sending: using port " + i);
/*      */           }
/*      */           
/* 1465 */           datagramSocket.send(datagramPacket);
/*      */         } finally {
/* 1467 */           datagramSocket.close();
/*      */         } 
/* 1469 */       } catch (Throwable throwable) {
/* 1470 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1471 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "stop", "Got unexpected Throwable", throwable);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV1Trap(int paramInt1, int paramInt2, SnmpVarBindList paramSnmpVarBindList) throws IOException, SnmpStatusException {
/* 1501 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1502 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV1Trap", "generic=" + paramInt1 + ", specific=" + paramInt2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1509 */     SnmpPduTrap snmpPduTrap = new SnmpPduTrap();
/* 1510 */     snmpPduTrap.address = null;
/* 1511 */     snmpPduTrap.port = this.trapPort;
/* 1512 */     snmpPduTrap.type = 164;
/* 1513 */     snmpPduTrap.version = 0;
/* 1514 */     snmpPduTrap.community = null;
/* 1515 */     snmpPduTrap.enterprise = this.enterpriseOid;
/* 1516 */     snmpPduTrap.genericTrap = paramInt1;
/* 1517 */     snmpPduTrap.specificTrap = paramInt2;
/* 1518 */     snmpPduTrap.timeStamp = getSysUpTime();
/*      */     
/* 1520 */     if (paramSnmpVarBindList != null) {
/* 1521 */       snmpPduTrap.varBindList = new SnmpVarBind[paramSnmpVarBindList.size()];
/* 1522 */       paramSnmpVarBindList.copyInto((Object[])snmpPduTrap.varBindList);
/*      */     } else {
/*      */       
/* 1525 */       snmpPduTrap.varBindList = null;
/*      */     } 
/*      */     
/*      */     try {
/* 1529 */       if (this.address != null)
/* 1530 */       { snmpPduTrap.agentAddr = handleMultipleIpVersion(this.address.getAddress()); }
/* 1531 */       else { snmpPduTrap
/* 1532 */           .agentAddr = handleMultipleIpVersion(InetAddress.getLocalHost().getAddress()); } 
/* 1533 */     } catch (UnknownHostException unknownHostException) {
/* 1534 */       byte[] arrayOfByte = new byte[4];
/* 1535 */       snmpPduTrap.agentAddr = handleMultipleIpVersion(arrayOfByte);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1540 */     sendTrapPdu(snmpPduTrap);
/*      */   }
/*      */   
/*      */   private SnmpIpAddress handleMultipleIpVersion(byte[] paramArrayOfbyte) {
/* 1544 */     if (paramArrayOfbyte.length == 4) {
/* 1545 */       return new SnmpIpAddress(paramArrayOfbyte);
/*      */     }
/* 1547 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 1548 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "handleMultipleIPVersion", "Not an IPv4 address, return null");
/*      */     }
/*      */ 
/*      */     
/* 1552 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV1Trap(InetAddress paramInetAddress, String paramString, int paramInt1, int paramInt2, SnmpVarBindList paramSnmpVarBindList) throws IOException, SnmpStatusException {
/* 1577 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1578 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV1Trap", "generic=" + paramInt1 + ", specific=" + paramInt2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1585 */     SnmpPduTrap snmpPduTrap = new SnmpPduTrap();
/* 1586 */     snmpPduTrap.address = null;
/* 1587 */     snmpPduTrap.port = this.trapPort;
/* 1588 */     snmpPduTrap.type = 164;
/* 1589 */     snmpPduTrap.version = 0;
/*      */     
/* 1591 */     if (paramString != null) {
/* 1592 */       snmpPduTrap.community = paramString.getBytes();
/*      */     } else {
/* 1594 */       snmpPduTrap.community = null;
/*      */     } 
/* 1596 */     snmpPduTrap.enterprise = this.enterpriseOid;
/* 1597 */     snmpPduTrap.genericTrap = paramInt1;
/* 1598 */     snmpPduTrap.specificTrap = paramInt2;
/* 1599 */     snmpPduTrap.timeStamp = getSysUpTime();
/*      */     
/* 1601 */     if (paramSnmpVarBindList != null) {
/* 1602 */       snmpPduTrap.varBindList = new SnmpVarBind[paramSnmpVarBindList.size()];
/* 1603 */       paramSnmpVarBindList.copyInto((Object[])snmpPduTrap.varBindList);
/*      */     } else {
/*      */       
/* 1606 */       snmpPduTrap.varBindList = null;
/*      */     } 
/*      */     
/*      */     try {
/* 1610 */       if (this.address != null)
/* 1611 */       { snmpPduTrap.agentAddr = handleMultipleIpVersion(this.address.getAddress()); }
/* 1612 */       else { snmpPduTrap
/* 1613 */           .agentAddr = handleMultipleIpVersion(InetAddress.getLocalHost().getAddress()); } 
/* 1614 */     } catch (UnknownHostException unknownHostException) {
/* 1615 */       byte[] arrayOfByte = new byte[4];
/* 1616 */       snmpPduTrap.agentAddr = handleMultipleIpVersion(arrayOfByte);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1621 */     if (paramInetAddress != null) {
/* 1622 */       sendTrapPdu(paramInetAddress, snmpPduTrap);
/*      */     } else {
/* 1624 */       sendTrapPdu(snmpPduTrap);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV1Trap(InetAddress paramInetAddress, SnmpIpAddress paramSnmpIpAddress, String paramString, SnmpOid paramSnmpOid, int paramInt1, int paramInt2, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/* 1659 */     snmpV1Trap(paramInetAddress, this.trapPort, paramSnmpIpAddress, paramString, paramSnmpOid, paramInt1, paramInt2, paramSnmpVarBindList, paramSnmpTimeticks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV1Trap(SnmpPeer paramSnmpPeer, SnmpIpAddress paramSnmpIpAddress, SnmpOid paramSnmpOid, int paramInt1, int paramInt2, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/* 1701 */     SnmpParameters snmpParameters = (SnmpParameters)paramSnmpPeer.getParams();
/* 1702 */     snmpV1Trap(paramSnmpPeer.getDestAddr(), paramSnmpPeer
/* 1703 */         .getDestPort(), paramSnmpIpAddress, snmpParameters
/*      */         
/* 1705 */         .getRdCommunity(), paramSnmpOid, paramInt1, paramInt2, paramSnmpVarBindList, paramSnmpTimeticks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void snmpV1Trap(InetAddress paramInetAddress, int paramInt1, SnmpIpAddress paramSnmpIpAddress, String paramString, SnmpOid paramSnmpOid, int paramInt2, int paramInt3, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/* 1724 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1725 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV1Trap", "generic=" + paramInt2 + ", specific=" + paramInt3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1732 */     SnmpPduTrap snmpPduTrap = new SnmpPduTrap();
/* 1733 */     snmpPduTrap.address = null;
/* 1734 */     snmpPduTrap.port = paramInt1;
/* 1735 */     snmpPduTrap.type = 164;
/* 1736 */     snmpPduTrap.version = 0;
/*      */ 
/*      */     
/* 1739 */     if (paramString != null) {
/* 1740 */       snmpPduTrap.community = paramString.getBytes();
/*      */     } else {
/* 1742 */       snmpPduTrap.community = null;
/*      */     } 
/*      */ 
/*      */     
/* 1746 */     if (paramSnmpOid != null) {
/* 1747 */       snmpPduTrap.enterprise = paramSnmpOid;
/*      */     } else {
/* 1749 */       snmpPduTrap.enterprise = this.enterpriseOid;
/*      */     } 
/* 1751 */     snmpPduTrap.genericTrap = paramInt2;
/* 1752 */     snmpPduTrap.specificTrap = paramInt3;
/*      */     
/* 1754 */     if (paramSnmpTimeticks != null) {
/* 1755 */       snmpPduTrap.timeStamp = paramSnmpTimeticks.longValue();
/*      */     } else {
/* 1757 */       snmpPduTrap.timeStamp = getSysUpTime();
/*      */     } 
/*      */     
/* 1760 */     if (paramSnmpVarBindList != null) {
/* 1761 */       snmpPduTrap.varBindList = new SnmpVarBind[paramSnmpVarBindList.size()];
/* 1762 */       paramSnmpVarBindList.copyInto((Object[])snmpPduTrap.varBindList);
/*      */     } else {
/*      */       
/* 1765 */       snmpPduTrap.varBindList = null;
/*      */     } 
/* 1767 */     if (paramSnmpIpAddress == null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 1772 */         InetAddress inetAddress = (this.address != null) ? this.address : InetAddress.getLocalHost();
/* 1773 */         paramSnmpIpAddress = handleMultipleIpVersion(inetAddress.getAddress());
/* 1774 */       } catch (UnknownHostException unknownHostException) {
/* 1775 */         byte[] arrayOfByte = new byte[4];
/* 1776 */         paramSnmpIpAddress = handleMultipleIpVersion(arrayOfByte);
/*      */       } 
/*      */     }
/*      */     
/* 1780 */     snmpPduTrap.agentAddr = paramSnmpIpAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1785 */     if (paramInetAddress != null) {
/* 1786 */       sendTrapPdu(paramInetAddress, snmpPduTrap);
/*      */     } else {
/* 1788 */       sendTrapPdu(snmpPduTrap);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV2Trap(SnmpPeer paramSnmpPeer, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/* 1828 */     SnmpParameters snmpParameters = (SnmpParameters)paramSnmpPeer.getParams();
/* 1829 */     snmpV2Trap(paramSnmpPeer.getDestAddr(), paramSnmpPeer
/* 1830 */         .getDestPort(), snmpParameters
/* 1831 */         .getRdCommunity(), paramSnmpOid, paramSnmpVarBindList, paramSnmpTimeticks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV2Trap(SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IOException, SnmpStatusException {
/*      */     SnmpVarBindList snmpVarBindList;
/* 1863 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1864 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV2Trap", "trapOid=" + paramSnmpOid);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1871 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/* 1872 */     snmpPduRequest.address = null;
/* 1873 */     snmpPduRequest.port = this.trapPort;
/* 1874 */     snmpPduRequest.type = 167;
/* 1875 */     snmpPduRequest.version = 1;
/* 1876 */     snmpPduRequest.community = null;
/*      */ 
/*      */     
/* 1879 */     if (paramSnmpVarBindList != null) {
/* 1880 */       snmpVarBindList = paramSnmpVarBindList.clone();
/*      */     } else {
/* 1882 */       snmpVarBindList = new SnmpVarBindList(2);
/* 1883 */     }  SnmpTimeticks snmpTimeticks = new SnmpTimeticks(getSysUpTime());
/* 1884 */     snmpVarBindList.insertElementAt(new SnmpVarBind(snmpTrapOidOid, paramSnmpOid), 0);
/* 1885 */     snmpVarBindList.insertElementAt(new SnmpVarBind(sysUpTimeOid, snmpTimeticks), 0);
/*      */     
/* 1887 */     snmpPduRequest.varBindList = new SnmpVarBind[snmpVarBindList.size()];
/* 1888 */     snmpVarBindList.copyInto((Object[])snmpPduRequest.varBindList);
/*      */ 
/*      */ 
/*      */     
/* 1892 */     sendTrapPdu(snmpPduRequest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV2Trap(InetAddress paramInetAddress, String paramString, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IOException, SnmpStatusException {
/*      */     SnmpVarBindList snmpVarBindList;
/* 1924 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 1925 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV2Trap", "trapOid=" + paramSnmpOid);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1932 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/* 1933 */     snmpPduRequest.address = null;
/* 1934 */     snmpPduRequest.port = this.trapPort;
/* 1935 */     snmpPduRequest.type = 167;
/* 1936 */     snmpPduRequest.version = 1;
/*      */     
/* 1938 */     if (paramString != null) {
/* 1939 */       snmpPduRequest.community = paramString.getBytes();
/*      */     } else {
/* 1941 */       snmpPduRequest.community = null;
/*      */     } 
/*      */     
/* 1944 */     if (paramSnmpVarBindList != null) {
/* 1945 */       snmpVarBindList = paramSnmpVarBindList.clone();
/*      */     } else {
/* 1947 */       snmpVarBindList = new SnmpVarBindList(2);
/* 1948 */     }  SnmpTimeticks snmpTimeticks = new SnmpTimeticks(getSysUpTime());
/* 1949 */     snmpVarBindList.insertElementAt(new SnmpVarBind(snmpTrapOidOid, paramSnmpOid), 0);
/* 1950 */     snmpVarBindList.insertElementAt(new SnmpVarBind(sysUpTimeOid, snmpTimeticks), 0);
/*      */     
/* 1952 */     snmpPduRequest.varBindList = new SnmpVarBind[snmpVarBindList.size()];
/* 1953 */     snmpVarBindList.copyInto((Object[])snmpPduRequest.varBindList);
/*      */ 
/*      */ 
/*      */     
/* 1957 */     if (paramInetAddress != null) {
/* 1958 */       sendTrapPdu(paramInetAddress, snmpPduRequest);
/*      */     } else {
/* 1960 */       sendTrapPdu(snmpPduRequest);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpV2Trap(InetAddress paramInetAddress, String paramString, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/* 2000 */     snmpV2Trap(paramInetAddress, this.trapPort, paramString, paramSnmpOid, paramSnmpVarBindList, paramSnmpTimeticks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void snmpV2Trap(InetAddress paramInetAddress, int paramInt, String paramString, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList, SnmpTimeticks paramSnmpTimeticks) throws IOException, SnmpStatusException {
/*      */     SnmpVarBindList snmpVarBindList;
/*      */     SnmpTimeticks snmpTimeticks;
/* 2016 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2023 */       StringBuilder stringBuilder = (new StringBuilder()).append("trapOid=").append(paramSnmpOid).append("\ncommunity=").append(paramString).append("\naddr=").append(paramInetAddress).append("\nvarBindList=").append(paramSnmpVarBindList).append("\ntime=").append(paramSnmpTimeticks).append("\ntrapPort=").append(paramInt);
/* 2024 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpV2Trap", stringBuilder
/* 2025 */           .toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2031 */     SnmpPduRequest snmpPduRequest = new SnmpPduRequest();
/* 2032 */     snmpPduRequest.address = null;
/* 2033 */     snmpPduRequest.port = paramInt;
/* 2034 */     snmpPduRequest.type = 167;
/* 2035 */     snmpPduRequest.version = 1;
/*      */     
/* 2037 */     if (paramString != null) {
/* 2038 */       snmpPduRequest.community = paramString.getBytes();
/*      */     } else {
/* 2040 */       snmpPduRequest.community = null;
/*      */     } 
/*      */     
/* 2043 */     if (paramSnmpVarBindList != null) {
/* 2044 */       snmpVarBindList = paramSnmpVarBindList.clone();
/*      */     } else {
/* 2046 */       snmpVarBindList = new SnmpVarBindList(2);
/*      */     } 
/*      */ 
/*      */     
/* 2050 */     if (paramSnmpTimeticks != null) {
/* 2051 */       snmpTimeticks = paramSnmpTimeticks;
/*      */     } else {
/* 2053 */       snmpTimeticks = new SnmpTimeticks(getSysUpTime());
/*      */     } 
/*      */     
/* 2056 */     snmpVarBindList.insertElementAt(new SnmpVarBind(snmpTrapOidOid, paramSnmpOid), 0);
/* 2057 */     snmpVarBindList.insertElementAt(new SnmpVarBind(sysUpTimeOid, snmpTimeticks), 0);
/*      */     
/* 2059 */     snmpPduRequest.varBindList = new SnmpVarBind[snmpVarBindList.size()];
/* 2060 */     snmpVarBindList.copyInto((Object[])snmpPduRequest.varBindList);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2065 */     if (paramInetAddress != null) {
/* 2066 */       sendTrapPdu(paramInetAddress, snmpPduRequest);
/*      */     } else {
/* 2068 */       sendTrapPdu(snmpPduRequest);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpPduTrap(InetAddress paramInetAddress, SnmpPduPacket paramSnmpPduPacket) throws IOException, SnmpStatusException {
/* 2086 */     if (paramInetAddress != null) {
/* 2087 */       sendTrapPdu(paramInetAddress, paramSnmpPduPacket);
/*      */     } else {
/* 2089 */       sendTrapPdu(paramSnmpPduPacket);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void snmpPduTrap(SnmpPeer paramSnmpPeer, SnmpPduPacket paramSnmpPduPacket) throws IOException, SnmpStatusException {
/* 2106 */     if (paramSnmpPeer != null) {
/* 2107 */       paramSnmpPduPacket.port = paramSnmpPeer.getDestPort();
/* 2108 */       sendTrapPdu(paramSnmpPeer.getDestAddr(), paramSnmpPduPacket);
/*      */     } else {
/*      */       
/* 2111 */       paramSnmpPduPacket.port = getTrapPort().intValue();
/* 2112 */       sendTrapPdu(paramSnmpPduPacket);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendTrapPdu(SnmpPduPacket paramSnmpPduPacket) throws SnmpStatusException, IOException {
/* 2124 */     SnmpMessage snmpMessage = null;
/*      */     try {
/* 2126 */       snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(paramSnmpPduPacket, this.bufferSize);
/* 2127 */       if (snmpMessage == null) {
/* 2128 */         throw new SnmpStatusException(16);
/*      */       
/*      */       }
/*      */     }
/* 2132 */     catch (SnmpTooBigException snmpTooBigException) {
/* 2133 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2134 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent to anyone");
/*      */       }
/*      */ 
/*      */       
/* 2138 */       throw new SnmpStatusException(1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2145 */     byte b = 0;
/* 2146 */     openTrapSocketIfNeeded();
/* 2147 */     if (this.ipacl != null) {
/* 2148 */       Enumeration<InetAddress> enumeration = this.ipacl.getTrapDestinations();
/* 2149 */       while (enumeration.hasMoreElements()) {
/* 2150 */         snmpMessage.address = enumeration.nextElement();
/* 2151 */         Enumeration<String> enumeration1 = this.ipacl.getTrapCommunities(snmpMessage.address);
/* 2152 */         while (enumeration1.hasMoreElements()) {
/* 2153 */           snmpMessage.community = ((String)enumeration1.nextElement()).getBytes();
/*      */           try {
/* 2155 */             sendTrapMessage(snmpMessage);
/* 2156 */             b++;
/*      */           }
/* 2158 */           catch (SnmpTooBigException snmpTooBigException) {
/* 2159 */             if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2160 */               JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent to " + snmpMessage.address);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2173 */     if (b == 0) {
/*      */       try {
/* 2175 */         snmpMessage.address = InetAddress.getLocalHost();
/* 2176 */         sendTrapMessage(snmpMessage);
/* 2177 */       } catch (SnmpTooBigException snmpTooBigException) {
/* 2178 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2179 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent.");
/*      */         
/*      */         }
/*      */       }
/* 2183 */       catch (UnknownHostException unknownHostException) {
/* 2184 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2185 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent.");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2192 */     closeTrapSocketIfNeeded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendTrapPdu(InetAddress paramInetAddress, SnmpPduPacket paramSnmpPduPacket) throws SnmpStatusException, IOException {
/* 2203 */     SnmpMessage snmpMessage = null;
/*      */     try {
/* 2205 */       snmpMessage = (SnmpMessage)this.pduFactory.encodeSnmpPdu(paramSnmpPduPacket, this.bufferSize);
/* 2206 */       if (snmpMessage == null) {
/* 2207 */         throw new SnmpStatusException(16);
/*      */       }
/*      */     }
/* 2210 */     catch (SnmpTooBigException snmpTooBigException) {
/* 2211 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2212 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent to the specified host.");
/*      */       }
/*      */ 
/*      */       
/* 2216 */       throw new SnmpStatusException(1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2223 */     openTrapSocketIfNeeded();
/* 2224 */     if (paramInetAddress != null) {
/* 2225 */       snmpMessage.address = paramInetAddress;
/*      */       try {
/* 2227 */         sendTrapMessage(snmpMessage);
/* 2228 */       } catch (SnmpTooBigException snmpTooBigException) {
/* 2229 */         if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINEST)) {
/* 2230 */           JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINEST, this.dbgTag, "sendTrapPdu", "Trap pdu is too big. Trap hasn't been sent to " + snmpMessage.address);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2237 */     closeTrapSocketIfNeeded();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendTrapMessage(SnmpMessage paramSnmpMessage) throws IOException, SnmpTooBigException {
/* 2246 */     byte[] arrayOfByte = new byte[this.bufferSize];
/* 2247 */     DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length);
/* 2248 */     int i = paramSnmpMessage.encodeMessage(arrayOfByte);
/* 2249 */     datagramPacket.setLength(i);
/* 2250 */     datagramPacket.setAddress(paramSnmpMessage.address);
/* 2251 */     datagramPacket.setPort(paramSnmpMessage.port);
/* 2252 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2253 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "sendTrapMessage", "sending trap to " + paramSnmpMessage.address + ":" + paramSnmpMessage.port);
/*      */     }
/*      */ 
/*      */     
/* 2257 */     this.trapSocket.send(datagramPacket);
/* 2258 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2259 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "sendTrapMessage", "sent to " + paramSnmpMessage.address + ":" + paramSnmpMessage.port);
/*      */     }
/*      */ 
/*      */     
/* 2263 */     this.snmpOutTraps++;
/* 2264 */     this.snmpOutPkts++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void openTrapSocketIfNeeded() throws SocketException {
/* 2271 */     if (this.trapSocket == null) {
/* 2272 */       this.trapSocket = new DatagramSocket(0, this.address);
/* 2273 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2274 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "openTrapSocketIfNeeded", "using port " + this.trapSocket
/*      */             
/* 2276 */             .getLocalPort() + " to send traps");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void closeTrapSocketIfNeeded() {
/* 2285 */     if (this.trapSocket != null && this.state != 0) {
/* 2286 */       this.trapSocket.close();
/* 2287 */       this.trapSocket = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector<SnmpInformRequest> snmpInformRequest(SnmpInformHandler paramSnmpInformHandler, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IllegalStateException, IOException, SnmpStatusException {
/*      */     SnmpVarBindList snmpVarBindList;
/* 2333 */     if (!isActive()) {
/* 2334 */       throw new IllegalStateException("Start SNMP adaptor server before carrying out this operation");
/*      */     }
/*      */     
/* 2337 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2338 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpInformRequest", "trapOid=" + paramSnmpOid);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2346 */     if (paramSnmpVarBindList != null) {
/* 2347 */       snmpVarBindList = paramSnmpVarBindList.clone();
/*      */     } else {
/* 2349 */       snmpVarBindList = new SnmpVarBindList(2);
/* 2350 */     }  SnmpTimeticks snmpTimeticks = new SnmpTimeticks(getSysUpTime());
/* 2351 */     snmpVarBindList.insertElementAt(new SnmpVarBind(snmpTrapOidOid, paramSnmpOid), 0);
/* 2352 */     snmpVarBindList.insertElementAt(new SnmpVarBind(sysUpTimeOid, snmpTimeticks), 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2357 */     openInformSocketIfNeeded();
/*      */ 
/*      */ 
/*      */     
/* 2361 */     Vector<SnmpInformRequest> vector = new Vector();
/*      */ 
/*      */     
/* 2364 */     if (this.ipacl != null) {
/* 2365 */       Enumeration<InetAddress> enumeration = this.ipacl.getInformDestinations();
/* 2366 */       while (enumeration.hasMoreElements()) {
/* 2367 */         InetAddress inetAddress = enumeration.nextElement();
/* 2368 */         Enumeration<String> enumeration1 = this.ipacl.getInformCommunities(inetAddress);
/* 2369 */         while (enumeration1.hasMoreElements()) {
/* 2370 */           String str = enumeration1.nextElement();
/* 2371 */           vector.addElement(this.informSession
/* 2372 */               .makeAsyncRequest(inetAddress, str, paramSnmpInformHandler, snmpVarBindList, 
/* 2373 */                 getInformPort()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2378 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpInformRequest snmpInformRequest(InetAddress paramInetAddress, String paramString, SnmpInformHandler paramSnmpInformHandler, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IllegalStateException, IOException, SnmpStatusException {
/* 2421 */     return snmpInformRequest(paramInetAddress, 
/* 2422 */         getInformPort(), paramString, paramSnmpInformHandler, paramSnmpOid, paramSnmpVarBindList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SnmpInformRequest snmpInformRequest(SnmpPeer paramSnmpPeer, SnmpInformHandler paramSnmpInformHandler, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IllegalStateException, IOException, SnmpStatusException {
/* 2471 */     SnmpParameters snmpParameters = (SnmpParameters)paramSnmpPeer.getParams();
/* 2472 */     return snmpInformRequest(paramSnmpPeer.getDestAddr(), paramSnmpPeer
/* 2473 */         .getDestPort(), snmpParameters
/* 2474 */         .getInformCommunity(), paramSnmpInformHandler, paramSnmpOid, paramSnmpVarBindList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int mapErrorStatus(int paramInt1, int paramInt2, int paramInt3) {
/* 2490 */     return SnmpSubRequestHandler.mapErrorStatus(paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SnmpInformRequest snmpInformRequest(InetAddress paramInetAddress, int paramInt, String paramString, SnmpInformHandler paramSnmpInformHandler, SnmpOid paramSnmpOid, SnmpVarBindList paramSnmpVarBindList) throws IllegalStateException, IOException, SnmpStatusException {
/*      */     SnmpVarBindList snmpVarBindList;
/* 2503 */     if (!isActive()) {
/* 2504 */       throw new IllegalStateException("Start SNMP adaptor server before carrying out this operation");
/*      */     }
/*      */     
/* 2507 */     if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2508 */       JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "snmpInformRequest", "trapOid=" + paramSnmpOid);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2516 */     if (paramSnmpVarBindList != null) {
/* 2517 */       snmpVarBindList = paramSnmpVarBindList.clone();
/*      */     } else {
/* 2519 */       snmpVarBindList = new SnmpVarBindList(2);
/* 2520 */     }  SnmpTimeticks snmpTimeticks = new SnmpTimeticks(getSysUpTime());
/* 2521 */     snmpVarBindList.insertElementAt(new SnmpVarBind(snmpTrapOidOid, paramSnmpOid), 0);
/* 2522 */     snmpVarBindList.insertElementAt(new SnmpVarBind(sysUpTimeOid, snmpTimeticks), 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2527 */     openInformSocketIfNeeded();
/* 2528 */     return this.informSession.makeAsyncRequest(paramInetAddress, paramString, paramSnmpInformHandler, snmpVarBindList, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void openInformSocketIfNeeded() throws SocketException {
/* 2536 */     if (this.informSession == null) {
/* 2537 */       this.informSession = new SnmpSession(this);
/* 2538 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2539 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "openInformSocketIfNeeded", "to send inform requests and receive inform responses");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void closeInformSocketIfNeeded() {
/* 2550 */     if (this.informSession != null && this.state != 0) {
/* 2551 */       this.informSession.destroySession();
/* 2552 */       this.informSession = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   InetAddress getAddress() {
/* 2562 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*      */     try {
/* 2579 */       if (this.socket != null) {
/* 2580 */         this.socket.close();
/* 2581 */         this.socket = null;
/*      */       } 
/*      */       
/* 2584 */       this.threadService.terminate();
/* 2585 */     } catch (Exception exception) {
/* 2586 */       if (JmxProperties.SNMP_ADAPTOR_LOGGER.isLoggable(Level.FINER)) {
/* 2587 */         JmxProperties.SNMP_ADAPTOR_LOGGER.logp(Level.FINER, this.dbgTag, "finalize", "Exception in finalizer", exception);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String makeDebugTag() {
/* 2601 */     return "SnmpAdaptorServer[" + getProtocol() + ":" + getPort() + "]";
/*      */   }
/*      */   
/*      */   void updateRequestCounters(int paramInt) {
/* 2605 */     switch (paramInt) {
/*      */       
/*      */       case 160:
/* 2608 */         this.snmpInGetRequests++;
/*      */         break;
/*      */       case 161:
/* 2611 */         this.snmpInGetNexts++;
/*      */         break;
/*      */       case 163:
/* 2614 */         this.snmpInSetRequests++;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2619 */     this.snmpInPkts++;
/*      */   }
/*      */   
/*      */   void updateErrorCounters(int paramInt) {
/* 2623 */     switch (paramInt) {
/*      */       
/*      */       case 0:
/* 2626 */         this.snmpOutGetResponses++;
/*      */         break;
/*      */       case 5:
/* 2629 */         this.snmpOutGenErrs++;
/*      */         break;
/*      */       case 3:
/* 2632 */         this.snmpOutBadValues++;
/*      */         break;
/*      */       case 2:
/* 2635 */         this.snmpOutNoSuchNames++;
/*      */         break;
/*      */       case 1:
/* 2638 */         this.snmpOutTooBigs++;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2643 */     this.snmpOutPkts++;
/*      */   }
/*      */   
/*      */   void updateVarCounters(int paramInt1, int paramInt2) {
/* 2647 */     switch (paramInt1) {
/*      */       
/*      */       case 160:
/*      */       case 161:
/*      */       case 165:
/* 2652 */         this.snmpInTotalReqVars += paramInt2;
/*      */         break;
/*      */       case 163:
/* 2655 */         this.snmpInTotalSetVars += paramInt2;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   void incSnmpInASNParseErrs(int paramInt) {
/* 2661 */     this.snmpInASNParseErrs += paramInt;
/*      */   }
/*      */   
/*      */   void incSnmpInBadVersions(int paramInt) {
/* 2665 */     this.snmpInBadVersions += paramInt;
/*      */   }
/*      */   
/*      */   void incSnmpInBadCommunityUses(int paramInt) {
/* 2669 */     this.snmpInBadCommunityUses += paramInt;
/*      */   }
/*      */   
/*      */   void incSnmpInBadCommunityNames(int paramInt) {
/* 2673 */     this.snmpInBadCommunityNames += paramInt;
/*      */   }
/*      */   
/*      */   void incSnmpSilentDrops(int paramInt) {
/* 2677 */     this.snmpSilentDrops += paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long getSysUpTime() {
/* 2687 */     return (System.currentTimeMillis() - this.startUpTime) / 10L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2698 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2704 */     this.mibs = new Vector<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(InetAddressAcl paramInetAddressAcl, int paramInt, InetAddress paramInetAddress) {
/* 2712 */     this.root = new SnmpMibTree();
/*      */ 
/*      */     
/* 2715 */     this.root.setDefaultAgent(new SnmpErrorHandlerAgent());
/*      */ 
/*      */ 
/*      */     
/* 2719 */     this.startUpTime = System.currentTimeMillis();
/* 2720 */     this.maxActiveClientCount = 10;
/*      */ 
/*      */     
/* 2723 */     this.pduFactory = new SnmpPduFactoryBER();
/*      */     
/* 2725 */     this.port = paramInt;
/* 2726 */     this.ipacl = paramInetAddressAcl;
/* 2727 */     this.address = paramInetAddress;
/*      */     
/* 2729 */     if (this.ipacl == null && this.useAcl == true) {
/* 2730 */       throw new IllegalArgumentException("ACL object cannot be null");
/*      */     }
/* 2732 */     this.threadService = new ThreadService(threadNumber);
/*      */   }
/*      */   
/*      */   SnmpMibAgent getAgentMib(SnmpOid paramSnmpOid) {
/* 2736 */     return this.root.getAgentMib(paramSnmpOid);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Thread createMainThread() {
/* 2741 */     Thread thread = super.createMainThread();
/* 2742 */     thread.setDaemon(true);
/* 2743 */     return thread;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpAdaptorServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */