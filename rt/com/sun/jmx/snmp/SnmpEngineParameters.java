/*     */ package com.sun.jmx.snmp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SnmpEngineParameters
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3720556613478400808L;
/*  41 */   private UserAcl uacl = null;
/*  42 */   private String securityFile = null;
/*     */   private boolean encrypt = false;
/*  44 */   private SnmpEngineId engineId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecurityFile(String paramString) {
/*  50 */     this.securityFile = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecurityFile() {
/*  58 */     return this.securityFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserAcl(UserAcl paramUserAcl) {
/*  67 */     this.uacl = paramUserAcl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserAcl getUserAcl() {
/*  75 */     return this.uacl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activateEncryption() {
/*  83 */     this.encrypt = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivateEncryption() {
/*  91 */     this.encrypt = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEncryptionEnabled() {
/*  99 */     return this.encrypt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEngineId(SnmpEngineId paramSnmpEngineId) {
/* 107 */     this.engineId = paramSnmpEngineId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SnmpEngineId getEngineId() {
/* 115 */     return this.engineId;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpEngineParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */