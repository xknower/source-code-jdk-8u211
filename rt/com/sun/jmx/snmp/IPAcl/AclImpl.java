/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import java.security.acl.Acl;
/*     */ import java.security.acl.AclEntry;
/*     */ import java.security.acl.NotOwnerException;
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
/*     */ class AclImpl
/*     */   extends OwnerImpl
/*     */   implements Acl, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2250957591085270029L;
/*  58 */   private Vector<AclEntry> entryList = null;
/*  59 */   private String aclName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AclImpl(PrincipalImpl paramPrincipalImpl, String paramString) {
/*  68 */     super(paramPrincipalImpl);
/*  69 */     this.entryList = new Vector<>();
/*  70 */     this.aclName = paramString;
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
/*     */   public void setName(Principal paramPrincipal, String paramString) throws NotOwnerException {
/*  87 */     if (!isOwner(paramPrincipal))
/*  88 */       throw new NotOwnerException(); 
/*  89 */     this.aclName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return this.aclName;
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
/*     */   public boolean addEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException {
/* 121 */     if (!isOwner(paramPrincipal)) {
/* 122 */       throw new NotOwnerException();
/*     */     }
/* 124 */     if (this.entryList.contains(paramAclEntry)) {
/* 125 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.entryList.addElement(paramAclEntry);
/* 135 */     return true;
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
/*     */   public boolean removeEntry(Principal paramPrincipal, AclEntry paramAclEntry) throws NotOwnerException {
/* 153 */     if (!isOwner(paramPrincipal)) {
/* 154 */       throw new NotOwnerException();
/*     */     }
/* 156 */     return this.entryList.removeElement(paramAclEntry);
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
/*     */   public void removeAll(Principal paramPrincipal) throws NotOwnerException {
/* 170 */     if (!isOwner(paramPrincipal))
/* 171 */       throw new NotOwnerException(); 
/* 172 */     this.entryList.removeAllElements();
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
/*     */   public Enumeration<Permission> getPermissions(Principal paramPrincipal) {
/* 194 */     Vector<Permission> vector = new Vector();
/* 195 */     for (Enumeration<AclEntry> enumeration = this.entryList.elements(); enumeration.hasMoreElements(); ) {
/* 196 */       AclEntry aclEntry = enumeration.nextElement();
/* 197 */       if (aclEntry.getPrincipal().equals(paramPrincipal))
/* 198 */         return aclEntry.permissions(); 
/*     */     } 
/* 200 */     return vector.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<AclEntry> entries() {
/* 211 */     return this.entryList.elements();
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
/*     */   public boolean checkPermission(Principal paramPrincipal, Permission paramPermission) {
/* 233 */     for (Enumeration<AclEntry> enumeration = this.entryList.elements(); enumeration.hasMoreElements(); ) {
/* 234 */       AclEntry aclEntry = enumeration.nextElement();
/* 235 */       if (aclEntry.getPrincipal().equals(paramPrincipal) && 
/* 236 */         aclEntry.checkPermission(paramPermission)) return true; 
/*     */     } 
/* 238 */     return false;
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
/*     */   public boolean checkPermission(Principal paramPrincipal, String paramString, Permission paramPermission) {
/* 260 */     for (Enumeration<AclEntry> enumeration = this.entryList.elements(); enumeration.hasMoreElements(); ) {
/* 261 */       AclEntryImpl aclEntryImpl = (AclEntryImpl)enumeration.nextElement();
/* 262 */       if (aclEntryImpl.getPrincipal().equals(paramPrincipal) && 
/* 263 */         aclEntryImpl.checkPermission(paramPermission) && aclEntryImpl.checkCommunity(paramString)) return true; 
/*     */     } 
/* 265 */     return false;
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
/*     */   public boolean checkCommunity(String paramString) {
/* 279 */     for (Enumeration<AclEntry> enumeration = this.entryList.elements(); enumeration.hasMoreElements(); ) {
/* 280 */       AclEntryImpl aclEntryImpl = (AclEntryImpl)enumeration.nextElement();
/* 281 */       if (aclEntryImpl.checkCommunity(paramString)) return true; 
/*     */     } 
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 293 */     return "AclImpl: " + getName();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\AclImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */