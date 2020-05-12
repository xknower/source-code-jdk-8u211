/*     */ package com.sun.security.auth.module;
/*     */ 
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class NTSystem
/*     */ {
/*     */   private String userName;
/*     */   private String domain;
/*     */   private String domainSID;
/*     */   private String userSID;
/*     */   private String[] groupIDs;
/*     */   private String primaryGroupID;
/*     */   private long impersonationToken;
/*     */   
/*     */   private native void getCurrent(boolean paramBoolean);
/*     */   
/*     */   private native long getImpersonationToken0();
/*     */   
/*     */   public NTSystem() {
/*  52 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NTSystem(boolean paramBoolean) {
/*  60 */     loadNative();
/*  61 */     getCurrent(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  72 */     return this.userName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDomain() {
/*  83 */     return this.domain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDomainSID() {
/*  94 */     return this.domainSID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserSID() {
/* 105 */     return this.userSID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryGroupID() {
/* 116 */     return this.primaryGroupID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getGroupIDs() {
/* 127 */     return (this.groupIDs == null) ? null : (String[])this.groupIDs.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getImpersonationToken() {
/* 138 */     if (this.impersonationToken == 0L) {
/* 139 */       this.impersonationToken = getImpersonationToken0();
/*     */     }
/* 141 */     return this.impersonationToken;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadNative() {
/* 146 */     System.loadLibrary("jaas_nt");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\auth\module\NTSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */