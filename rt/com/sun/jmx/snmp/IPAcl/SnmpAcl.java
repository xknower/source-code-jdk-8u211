/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.snmp.InetAddressAcl;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.acl.AclEntry;
/*     */ import java.security.acl.NotOwnerException;
/*     */ import java.security.acl.Permission;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpAcl
/*     */   implements InetAddressAcl, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6702287103824397063L;
/*  68 */   static final PermissionImpl READ = new PermissionImpl("READ");
/*  69 */   static final PermissionImpl WRITE = new PermissionImpl("WRITE");
/*     */   
/*     */   private AclImpl acl;
/*     */   
/*     */   private boolean alwaysAuthorized;
/*     */   
/*     */   private String authorizedListFile;
/*     */   
/*     */   private Hashtable<InetAddress, Vector<String>> trapDestList;
/*     */   
/*     */   private Hashtable<InetAddress, Vector<String>> informDestList;
/*     */   private PrincipalImpl owner;
/*     */   
/*     */   public SnmpAcl(String paramString) throws UnknownHostException, IllegalArgumentException {
/*  83 */     this(paramString, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpAcl(String paramString1, String paramString2) throws UnknownHostException, IllegalArgumentException {
/* 466 */     this.acl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 471 */     this.alwaysAuthorized = false;
/*     */ 
/*     */ 
/*     */     
/* 475 */     this.authorizedListFile = null;
/*     */ 
/*     */ 
/*     */     
/* 479 */     this.trapDestList = null;
/*     */ 
/*     */ 
/*     */     
/* 483 */     this.informDestList = null;
/*     */     
/* 485 */     this.owner = null;
/*     */     this.trapDestList = new Hashtable<>();
/*     */     this.informDestList = new Hashtable<>();
/*     */     this.owner = new PrincipalImpl();
/*     */     try {
/*     */       this.acl = new AclImpl(this.owner, paramString1);
/*     */       AclEntryImpl aclEntryImpl = new AclEntryImpl(this.owner);
/*     */       aclEntryImpl.addPermission(READ);
/*     */       aclEntryImpl.addPermission(WRITE);
/*     */       this.acl.addEntry(this.owner, aclEntryImpl);
/*     */     } catch (NotOwnerException notOwnerException) {
/*     */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST))
/*     */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpAcl.class.getName(), "SnmpAcl(String,String)", "Should never get NotOwnerException as the owner is built in this constructor"); 
/*     */     } 
/*     */     if (paramString2 == null) {
/*     */       setDefaultFileName();
/*     */     } else {
/*     */       setAuthorizedListFile(paramString2);
/*     */     } 
/*     */     readAuthorizedListFile();
/*     */   }
/*     */   
/*     */   public Enumeration<AclEntry> entries() {
/*     */     return this.acl.entries();
/*     */   }
/*     */   
/*     */   public Enumeration<String> communities() {
/*     */     HashSet hashSet = new HashSet();
/*     */     Vector<String> vector = new Vector();
/*     */     for (Enumeration<AclEntry> enumeration = this.acl.entries(); enumeration.hasMoreElements(); ) {
/*     */       AclEntryImpl aclEntryImpl = (AclEntryImpl)enumeration.nextElement();
/*     */       Enumeration<String> enumeration1 = aclEntryImpl.communities();
/*     */       while (enumeration1.hasMoreElements())
/*     */         hashSet.add(enumeration1.nextElement()); 
/*     */     } 
/*     */     String[] arrayOfString = (String[])hashSet.toArray((Object[])new String[0]);
/*     */     for (byte b = 0; b < arrayOfString.length; b++)
/*     */       vector.addElement(arrayOfString[b]); 
/*     */     return vector.elements();
/*     */   }
/*     */   
/*     */   public String getName() {
/*     */     return this.acl.getName();
/*     */   }
/*     */   
/*     */   public static PermissionImpl getREAD() {
/*     */     return READ;
/*     */   }
/*     */   
/*     */   public static PermissionImpl getWRITE() {
/*     */     return WRITE;
/*     */   }
/*     */   
/*     */   public static String getDefaultAclFileName() {
/*     */     String str = System.getProperty("file.separator");
/*     */     StringBuffer stringBuffer = (new StringBuffer(System.getProperty("java.home"))).append(str).append("lib").append(str).append("snmp.acl");
/*     */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public void setAuthorizedListFile(String paramString) throws IllegalArgumentException {
/*     */     File file = new File(paramString);
/*     */     if (!file.isFile()) {
/*     */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST))
/*     */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpAcl.class.getName(), "setAuthorizedListFile", "ACL file not found: " + paramString); 
/*     */       throw new IllegalArgumentException("The specified file [" + file + "] doesn't exist or is not a file, no configuration loaded");
/*     */     } 
/*     */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "setAuthorizedListFile", "Default file set to " + paramString); 
/*     */     this.authorizedListFile = paramString;
/*     */   }
/*     */   
/*     */   public void rereadTheFile() throws NotOwnerException, UnknownHostException {
/*     */     this.alwaysAuthorized = false;
/*     */     this.acl.removeAll(this.owner);
/*     */     this.trapDestList.clear();
/*     */     this.informDestList.clear();
/*     */     AclEntryImpl aclEntryImpl = new AclEntryImpl(this.owner);
/*     */     aclEntryImpl.addPermission(READ);
/*     */     aclEntryImpl.addPermission(WRITE);
/*     */     this.acl.addEntry(this.owner, aclEntryImpl);
/*     */     readAuthorizedListFile();
/*     */   }
/*     */   
/*     */   public String getAuthorizedListFile() {
/*     */     return this.authorizedListFile;
/*     */   }
/*     */   
/*     */   public boolean checkReadPermission(InetAddress paramInetAddress) {
/*     */     if (this.alwaysAuthorized)
/*     */       return true; 
/*     */     PrincipalImpl principalImpl = new PrincipalImpl(paramInetAddress);
/*     */     return this.acl.checkPermission(principalImpl, READ);
/*     */   }
/*     */   
/*     */   public boolean checkReadPermission(InetAddress paramInetAddress, String paramString) {
/*     */     if (this.alwaysAuthorized)
/*     */       return true; 
/*     */     PrincipalImpl principalImpl = new PrincipalImpl(paramInetAddress);
/*     */     return this.acl.checkPermission(principalImpl, paramString, READ);
/*     */   }
/*     */   
/*     */   public boolean checkCommunity(String paramString) {
/*     */     return this.acl.checkCommunity(paramString);
/*     */   }
/*     */   
/*     */   public boolean checkWritePermission(InetAddress paramInetAddress) {
/*     */     if (this.alwaysAuthorized)
/*     */       return true; 
/*     */     PrincipalImpl principalImpl = new PrincipalImpl(paramInetAddress);
/*     */     return this.acl.checkPermission(principalImpl, WRITE);
/*     */   }
/*     */   
/*     */   public boolean checkWritePermission(InetAddress paramInetAddress, String paramString) {
/*     */     if (this.alwaysAuthorized)
/*     */       return true; 
/*     */     PrincipalImpl principalImpl = new PrincipalImpl(paramInetAddress);
/*     */     return this.acl.checkPermission(principalImpl, paramString, WRITE);
/*     */   }
/*     */   
/*     */   public Enumeration<InetAddress> getTrapDestinations() {
/*     */     return this.trapDestList.keys();
/*     */   }
/*     */   
/*     */   public Enumeration<String> getTrapCommunities(InetAddress paramInetAddress) {
/*     */     Vector<String> vector = null;
/*     */     if ((vector = this.trapDestList.get(paramInetAddress)) != null) {
/*     */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */         JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "getTrapCommunities", "[" + paramInetAddress.toString() + "] is in list"); 
/*     */       return vector.elements();
/*     */     } 
/*     */     vector = new Vector<>();
/*     */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "getTrapCommunities", "[" + paramInetAddress.toString() + "] is not in list"); 
/*     */     return vector.elements();
/*     */   }
/*     */   
/*     */   public Enumeration<InetAddress> getInformDestinations() {
/*     */     return this.informDestList.keys();
/*     */   }
/*     */   
/*     */   public Enumeration<String> getInformCommunities(InetAddress paramInetAddress) {
/*     */     Vector<String> vector = null;
/*     */     if ((vector = this.informDestList.get(paramInetAddress)) != null) {
/*     */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */         JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "getInformCommunities", "[" + paramInetAddress.toString() + "] is in list"); 
/*     */       return vector.elements();
/*     */     } 
/*     */     vector = new Vector<>();
/*     */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */       JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "getInformCommunities", "[" + paramInetAddress.toString() + "] is not in list"); 
/*     */     return vector.elements();
/*     */   }
/*     */   
/*     */   private void readAuthorizedListFile() {
/*     */     this.alwaysAuthorized = false;
/*     */     if (this.authorizedListFile == null) {
/*     */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */         JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "readAuthorizedListFile", "alwaysAuthorized set to true"); 
/*     */       this.alwaysAuthorized = true;
/*     */     } else {
/*     */       Parser parser = null;
/*     */       try {
/*     */         parser = new Parser(new FileInputStream(getAuthorizedListFile()));
/*     */       } catch (FileNotFoundException fileNotFoundException) {
/*     */         if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST))
/*     */           JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpAcl.class.getName(), "readAuthorizedListFile", "The specified file was not found, authorize everybody"); 
/*     */         this.alwaysAuthorized = true;
/*     */         return;
/*     */       } 
/*     */       try {
/*     */         JDMSecurityDefs jDMSecurityDefs = parser.SecurityDefs();
/*     */         jDMSecurityDefs.buildAclEntries(this.owner, this.acl);
/*     */         jDMSecurityDefs.buildTrapEntries(this.trapDestList);
/*     */         jDMSecurityDefs.buildInformEntries(this.informDestList);
/*     */       } catch (ParseException parseException) {
/*     */         if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST))
/*     */           JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpAcl.class.getName(), "readAuthorizedListFile", "Got parsing exception", parseException); 
/*     */         throw new IllegalArgumentException(parseException.getMessage());
/*     */       } catch (Error error) {
/*     */         if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST))
/*     */           JmxProperties.SNMP_LOGGER.logp(Level.FINEST, SnmpAcl.class.getName(), "readAuthorizedListFile", "Got unexpected error", error); 
/*     */         throw new IllegalArgumentException(error.getMessage());
/*     */       } 
/*     */       for (Enumeration<AclEntry> enumeration = this.acl.entries(); enumeration.hasMoreElements(); ) {
/*     */         AclEntryImpl aclEntryImpl = (AclEntryImpl)enumeration.nextElement();
/*     */         if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */           JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "readAuthorizedListFile", "===> " + aclEntryImpl.getPrincipal().toString()); 
/*     */         for (Enumeration<Permission> enumeration1 = aclEntryImpl.permissions(); enumeration1.hasMoreElements(); ) {
/*     */           Permission permission = enumeration1.nextElement();
/*     */           if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINER))
/*     */             JmxProperties.SNMP_LOGGER.logp(Level.FINER, SnmpAcl.class.getName(), "readAuthorizedListFile", "perm = " + permission); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setDefaultFileName() {
/*     */     try {
/*     */       setAuthorizedListFile(getDefaultAclFileName());
/*     */     } catch (IllegalArgumentException illegalArgumentException) {}
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\SnmpAcl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */