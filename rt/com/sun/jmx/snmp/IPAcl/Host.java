/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.acl.NotOwnerException;
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
/*     */ abstract class Host
/*     */   extends SimpleNode
/*     */   implements Serializable
/*     */ {
/*     */   public Host(int paramInt) {
/*  50 */     super(paramInt);
/*     */   }
/*     */   
/*     */   public Host(Parser paramParser, int paramInt) {
/*  54 */     super(paramParser, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract PrincipalImpl createAssociatedPrincipal() throws UnknownHostException;
/*     */ 
/*     */   
/*     */   protected abstract String getHname();
/*     */ 
/*     */   
/*     */   public void buildAclEntries(PrincipalImpl paramPrincipalImpl, AclImpl paramAclImpl) {
/*  65 */     PrincipalImpl principalImpl = null;
/*     */     try {
/*  67 */       principalImpl = createAssociatedPrincipal();
/*  68 */     } catch (UnknownHostException unknownHostException) {
/*  69 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  70 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, Host.class.getName(), "buildAclEntries", "Cannot create ACL entry; got exception", unknownHostException);
/*     */       }
/*     */ 
/*     */       
/*  74 */       throw new IllegalArgumentException("Cannot create ACL entry for " + unknownHostException.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  79 */     AclEntryImpl aclEntryImpl = null;
/*     */     try {
/*  81 */       aclEntryImpl = new AclEntryImpl(principalImpl);
/*     */ 
/*     */       
/*  84 */       registerPermission(aclEntryImpl);
/*  85 */       paramAclImpl.addEntry(paramPrincipalImpl, aclEntryImpl);
/*  86 */     } catch (UnknownHostException unknownHostException) {
/*  87 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  88 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, Host.class.getName(), "buildAclEntries", "Cannot create ACL entry; got exception", unknownHostException);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*  93 */     } catch (NotOwnerException notOwnerException) {
/*  94 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  95 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, Host.class.getName(), "buildAclEntries", "Cannot create ACL entry; got exception", notOwnerException);
/*     */       }
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerPermission(AclEntryImpl paramAclEntryImpl) {
/* 104 */     JDMHost jDMHost = (JDMHost)jjtGetParent();
/* 105 */     JDMManagers jDMManagers = (JDMManagers)jDMHost.jjtGetParent();
/* 106 */     JDMAclItem jDMAclItem = (JDMAclItem)jDMManagers.jjtGetParent();
/* 107 */     JDMAccess jDMAccess = jDMAclItem.getAccess();
/* 108 */     jDMAccess.putPermission(paramAclEntryImpl);
/* 109 */     JDMCommunities jDMCommunities = jDMAclItem.getCommunities();
/* 110 */     jDMCommunities.buildCommunities(paramAclEntryImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildTrapEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {
/* 115 */     JDMHostTrap jDMHostTrap = (JDMHostTrap)jjtGetParent();
/* 116 */     JDMTrapInterestedHost jDMTrapInterestedHost = (JDMTrapInterestedHost)jDMHostTrap.jjtGetParent();
/* 117 */     JDMTrapItem jDMTrapItem = (JDMTrapItem)jDMTrapInterestedHost.jjtGetParent();
/* 118 */     JDMTrapCommunity jDMTrapCommunity = jDMTrapItem.getCommunity();
/* 119 */     String str = jDMTrapCommunity.getCommunity();
/*     */     
/* 121 */     InetAddress inetAddress = null;
/*     */     try {
/* 123 */       inetAddress = InetAddress.getByName(getHname());
/* 124 */     } catch (UnknownHostException unknownHostException) {
/* 125 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 126 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, Host.class.getName(), "buildTrapEntries", "Cannot create TRAP entry; got exception", unknownHostException);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 133 */     Vector<String> vector = null;
/* 134 */     if (paramHashtable.containsKey(inetAddress)) {
/* 135 */       vector = paramHashtable.get(inetAddress);
/* 136 */       if (!vector.contains(str)) {
/* 137 */         vector.addElement(str);
/*     */       }
/*     */     } else {
/* 140 */       vector = new Vector<>();
/* 141 */       vector.addElement(str);
/* 142 */       paramHashtable.put(inetAddress, vector);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildInformEntries(Hashtable<InetAddress, Vector<String>> paramHashtable) {
/* 148 */     JDMHostInform jDMHostInform = (JDMHostInform)jjtGetParent();
/* 149 */     JDMInformInterestedHost jDMInformInterestedHost = (JDMInformInterestedHost)jDMHostInform.jjtGetParent();
/* 150 */     JDMInformItem jDMInformItem = (JDMInformItem)jDMInformInterestedHost.jjtGetParent();
/* 151 */     JDMInformCommunity jDMInformCommunity = jDMInformItem.getCommunity();
/* 152 */     String str = jDMInformCommunity.getCommunity();
/*     */     
/* 154 */     InetAddress inetAddress = null;
/*     */     try {
/* 156 */       inetAddress = InetAddress.getByName(getHname());
/* 157 */     } catch (UnknownHostException unknownHostException) {
/* 158 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 159 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, Host.class.getName(), "buildTrapEntries", "Cannot create INFORM entry; got exception", unknownHostException);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 166 */     Vector<String> vector = null;
/* 167 */     if (paramHashtable.containsKey(inetAddress)) {
/* 168 */       vector = paramHashtable.get(inetAddress);
/* 169 */       if (!vector.contains(str)) {
/* 170 */         vector.addElement(str);
/*     */       }
/*     */     } else {
/* 173 */       vector = new Vector<>();
/* 174 */       vector.addElement(str);
/* 175 */       paramHashtable.put(inetAddress, vector);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\Host.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */