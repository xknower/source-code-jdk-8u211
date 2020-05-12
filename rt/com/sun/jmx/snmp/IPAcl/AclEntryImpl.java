/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.Principal;
/*     */ import java.security.acl.AclEntry;
/*     */ import java.security.acl.Permission;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AclEntryImpl
/*     */   implements AclEntry, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5047185131260073216L;
/*     */   private Principal princ;
/*     */   private boolean neg;
/*     */   private Vector<Permission> permList;
/*     */   private Vector<String> commList;
/*     */   
/*     */   private AclEntryImpl(AclEntryImpl paramAclEntryImpl) throws UnknownHostException {
/* 259 */     this.princ = null;
/* 260 */     this.neg = false;
/* 261 */     this.permList = null;
/* 262 */     this.commList = null; setPrincipal(paramAclEntryImpl.getPrincipal()); this.permList = new Vector<>(); this.commList = new Vector<>(); Enumeration<String> enumeration; for (enumeration = paramAclEntryImpl.communities(); enumeration.hasMoreElements();) addCommunity(enumeration.nextElement());  for (enumeration = (Enumeration)paramAclEntryImpl.permissions(); enumeration.hasMoreElements();) addPermission((Permission)enumeration.nextElement());  if (paramAclEntryImpl.isNegative()) setNegativePermissions();  } public AclEntryImpl() { this.princ = null; this.neg = false; this.permList = null; this.commList = null; this.princ = null; this.permList = new Vector<>(); this.commList = new Vector<>(); } public AclEntryImpl(Principal paramPrincipal) throws UnknownHostException { this.princ = null; this.neg = false; this.permList = null; this.commList = null;
/*     */     this.princ = paramPrincipal;
/*     */     this.permList = new Vector<>();
/*     */     this.commList = new Vector<>(); }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     Object object;
/*     */     try {
/*     */       object = new AclEntryImpl(this);
/*     */     } catch (UnknownHostException unknownHostException) {
/*     */       object = null;
/*     */     } 
/*     */     return object;
/*     */   }
/*     */   
/*     */   public boolean isNegative() {
/*     */     return this.neg;
/*     */   }
/*     */   
/*     */   public boolean addPermission(Permission paramPermission) {
/*     */     if (this.permList.contains(paramPermission))
/*     */       return false; 
/*     */     this.permList.addElement(paramPermission);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean removePermission(Permission paramPermission) {
/*     */     if (!this.permList.contains(paramPermission))
/*     */       return false; 
/*     */     this.permList.removeElement(paramPermission);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean checkPermission(Permission paramPermission) {
/*     */     return this.permList.contains(paramPermission);
/*     */   }
/*     */   
/*     */   public Enumeration<Permission> permissions() {
/*     */     return this.permList.elements();
/*     */   }
/*     */   
/*     */   public void setNegativePermissions() {
/*     */     this.neg = true;
/*     */   }
/*     */   
/*     */   public Principal getPrincipal() {
/*     */     return this.princ;
/*     */   }
/*     */   
/*     */   public boolean setPrincipal(Principal paramPrincipal) {
/*     */     if (this.princ != null)
/*     */       return false; 
/*     */     this.princ = paramPrincipal;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return "AclEntry:" + this.princ.toString();
/*     */   }
/*     */   
/*     */   public Enumeration<String> communities() {
/*     */     return this.commList.elements();
/*     */   }
/*     */   
/*     */   public boolean addCommunity(String paramString) {
/*     */     if (this.commList.contains(paramString))
/*     */       return false; 
/*     */     this.commList.addElement(paramString);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeCommunity(String paramString) {
/*     */     if (!this.commList.contains(paramString))
/*     */       return false; 
/*     */     this.commList.removeElement(paramString);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean checkCommunity(String paramString) {
/*     */     return this.commList.contains(paramString);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\AclEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */