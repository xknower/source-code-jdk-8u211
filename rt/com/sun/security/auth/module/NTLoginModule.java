/*     */ package com.sun.security.auth.module;
/*     */ 
/*     */ import com.sun.security.auth.NTDomainPrincipal;
/*     */ import com.sun.security.auth.NTNumericCredential;
/*     */ import com.sun.security.auth.NTSidDomainPrincipal;
/*     */ import com.sun.security.auth.NTSidGroupPrincipal;
/*     */ import com.sun.security.auth.NTSidPrimaryGroupPrincipal;
/*     */ import com.sun.security.auth.NTSidUserPrincipal;
/*     */ import com.sun.security.auth.NTUserPrincipal;
/*     */ import java.security.Principal;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.login.FailedLoginException;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import javax.security.auth.spi.LoginModule;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class NTLoginModule
/*     */   implements LoginModule
/*     */ {
/*     */   private NTSystem ntSystem;
/*     */   private Subject subject;
/*     */   private CallbackHandler callbackHandler;
/*     */   private Map<String, ?> sharedState;
/*     */   private Map<String, ?> options;
/*     */   private boolean debug = false;
/*     */   private boolean debugNative = false;
/*     */   private boolean succeeded = false;
/*     */   private boolean commitSucceeded = false;
/*     */   private NTUserPrincipal userPrincipal;
/*     */   private NTSidUserPrincipal userSID;
/*     */   private NTDomainPrincipal userDomain;
/*     */   private NTSidDomainPrincipal domainSID;
/*     */   private NTSidPrimaryGroupPrincipal primaryGroup;
/*     */   private NTSidGroupPrincipal[] groups;
/*     */   private NTNumericCredential iToken;
/*     */   
/*     */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/* 111 */     this.subject = paramSubject;
/* 112 */     this.callbackHandler = paramCallbackHandler;
/* 113 */     this.sharedState = paramMap1;
/* 114 */     this.options = paramMap2;
/*     */ 
/*     */     
/* 117 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
/* 118 */     this.debugNative = "true".equalsIgnoreCase((String)paramMap2.get("debugNative"));
/*     */     
/* 120 */     if (this.debugNative == true) {
/* 121 */       this.debug = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean login() throws LoginException {
/* 140 */     this.succeeded = false;
/*     */     
/* 142 */     this.ntSystem = new NTSystem(this.debugNative);
/* 143 */     if (this.ntSystem == null) {
/* 144 */       if (this.debug) {
/* 145 */         System.out.println("\t\t[NTLoginModule] Failed in NT login");
/*     */       }
/*     */       
/* 148 */       throw new FailedLoginException("Failed in attempt to import the underlying NT system identity information");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (this.ntSystem.getName() == null) {
/* 154 */       throw new FailedLoginException("Failed in attempt to import the underlying NT system identity information");
/*     */     }
/*     */ 
/*     */     
/* 158 */     this.userPrincipal = new NTUserPrincipal(this.ntSystem.getName());
/* 159 */     if (this.debug) {
/* 160 */       System.out.println("\t\t[NTLoginModule] succeeded importing info: ");
/*     */       
/* 162 */       System.out.println("\t\t\tuser name = " + this.userPrincipal
/* 163 */           .getName());
/*     */     } 
/*     */     
/* 166 */     if (this.ntSystem.getUserSID() != null) {
/* 167 */       this.userSID = new NTSidUserPrincipal(this.ntSystem.getUserSID());
/* 168 */       if (this.debug) {
/* 169 */         System.out.println("\t\t\tuser SID = " + this.userSID
/* 170 */             .getName());
/*     */       }
/*     */     } 
/* 173 */     if (this.ntSystem.getDomain() != null) {
/* 174 */       this.userDomain = new NTDomainPrincipal(this.ntSystem.getDomain());
/* 175 */       if (this.debug) {
/* 176 */         System.out.println("\t\t\tuser domain = " + this.userDomain
/* 177 */             .getName());
/*     */       }
/*     */     } 
/* 180 */     if (this.ntSystem.getDomainSID() != null) {
/* 181 */       this
/* 182 */         .domainSID = new NTSidDomainPrincipal(this.ntSystem.getDomainSID());
/* 183 */       if (this.debug) {
/* 184 */         System.out.println("\t\t\tuser domain SID = " + this.domainSID
/* 185 */             .getName());
/*     */       }
/*     */     } 
/* 188 */     if (this.ntSystem.getPrimaryGroupID() != null) {
/* 189 */       this
/* 190 */         .primaryGroup = new NTSidPrimaryGroupPrincipal(this.ntSystem.getPrimaryGroupID());
/* 191 */       if (this.debug) {
/* 192 */         System.out.println("\t\t\tuser primary group = " + this.primaryGroup
/* 193 */             .getName());
/*     */       }
/*     */     } 
/* 196 */     if (this.ntSystem.getGroupIDs() != null && (this.ntSystem
/* 197 */       .getGroupIDs()).length > 0) {
/*     */       
/* 199 */       String[] arrayOfString = this.ntSystem.getGroupIDs();
/* 200 */       this.groups = new NTSidGroupPrincipal[arrayOfString.length];
/* 201 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 202 */         this.groups[b] = new NTSidGroupPrincipal(arrayOfString[b]);
/* 203 */         if (this.debug) {
/* 204 */           System.out.println("\t\t\tuser group = " + this.groups[b]
/* 205 */               .getName());
/*     */         }
/*     */       } 
/*     */     } 
/* 209 */     if (this.ntSystem.getImpersonationToken() != 0L) {
/* 210 */       this.iToken = new NTNumericCredential(this.ntSystem.getImpersonationToken());
/* 211 */       if (this.debug) {
/* 212 */         System.out.println("\t\t\timpersonation token = " + this.ntSystem
/* 213 */             .getImpersonationToken());
/*     */       }
/*     */     } 
/*     */     
/* 217 */     this.succeeded = true;
/* 218 */     return this.succeeded;
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
/*     */   public boolean commit() throws LoginException {
/* 244 */     if (!this.succeeded) {
/* 245 */       if (this.debug) {
/* 246 */         System.out.println("\t\t[NTLoginModule]: did not add any Principals to Subject because own authentication failed.");
/*     */       }
/*     */ 
/*     */       
/* 250 */       return false;
/*     */     } 
/* 252 */     if (this.subject.isReadOnly()) {
/* 253 */       throw new LoginException("Subject is ReadOnly");
/*     */     }
/* 255 */     Set<Principal> set = this.subject.getPrincipals();
/*     */ 
/*     */     
/* 258 */     if (!set.contains(this.userPrincipal)) {
/* 259 */       set.add(this.userPrincipal);
/*     */     }
/* 261 */     if (this.userSID != null && !set.contains(this.userSID)) {
/* 262 */       set.add(this.userSID);
/*     */     }
/*     */     
/* 265 */     if (this.userDomain != null && !set.contains(this.userDomain)) {
/* 266 */       set.add(this.userDomain);
/*     */     }
/* 268 */     if (this.domainSID != null && !set.contains(this.domainSID)) {
/* 269 */       set.add(this.domainSID);
/*     */     }
/*     */     
/* 272 */     if (this.primaryGroup != null && !set.contains(this.primaryGroup)) {
/* 273 */       set.add(this.primaryGroup);
/*     */     }
/* 275 */     for (byte b = 0; this.groups != null && b < this.groups.length; b++) {
/* 276 */       if (!set.contains(this.groups[b])) {
/* 277 */         set.add(this.groups[b]);
/*     */       }
/*     */     } 
/*     */     
/* 281 */     Set<Object> set1 = this.subject.getPublicCredentials();
/* 282 */     if (this.iToken != null && !set1.contains(this.iToken)) {
/* 283 */       set1.add(this.iToken);
/*     */     }
/* 285 */     this.commitSucceeded = true;
/* 286 */     return true;
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
/*     */   public boolean abort() throws LoginException {
/* 309 */     if (this.debug) {
/* 310 */       System.out.println("\t\t[NTLoginModule]: aborted authentication attempt");
/*     */     }
/*     */ 
/*     */     
/* 314 */     if (!this.succeeded)
/* 315 */       return false; 
/* 316 */     if (this.succeeded == true && !this.commitSucceeded) {
/* 317 */       this.ntSystem = null;
/* 318 */       this.userPrincipal = null;
/* 319 */       this.userSID = null;
/* 320 */       this.userDomain = null;
/* 321 */       this.domainSID = null;
/* 322 */       this.primaryGroup = null;
/* 323 */       this.groups = null;
/* 324 */       this.iToken = null;
/* 325 */       this.succeeded = false;
/*     */     }
/*     */     else {
/*     */       
/* 329 */       logout();
/*     */     } 
/* 331 */     return this.succeeded;
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
/*     */   public boolean logout() throws LoginException {
/* 352 */     if (this.subject.isReadOnly()) {
/* 353 */       throw new LoginException("Subject is ReadOnly");
/*     */     }
/* 355 */     Set<Principal> set = this.subject.getPrincipals();
/* 356 */     if (set.contains(this.userPrincipal)) {
/* 357 */       set.remove(this.userPrincipal);
/*     */     }
/* 359 */     if (set.contains(this.userSID)) {
/* 360 */       set.remove(this.userSID);
/*     */     }
/* 362 */     if (set.contains(this.userDomain)) {
/* 363 */       set.remove(this.userDomain);
/*     */     }
/* 365 */     if (set.contains(this.domainSID)) {
/* 366 */       set.remove(this.domainSID);
/*     */     }
/* 368 */     if (set.contains(this.primaryGroup)) {
/* 369 */       set.remove(this.primaryGroup);
/*     */     }
/* 371 */     for (byte b = 0; this.groups != null && b < this.groups.length; b++) {
/* 372 */       if (set.contains(this.groups[b])) {
/* 373 */         set.remove(this.groups[b]);
/*     */       }
/*     */     } 
/*     */     
/* 377 */     Set<Object> set1 = this.subject.getPublicCredentials();
/* 378 */     if (set1.contains(this.iToken)) {
/* 379 */       set1.remove(this.iToken);
/*     */     }
/*     */     
/* 382 */     this.succeeded = false;
/* 383 */     this.commitSucceeded = false;
/* 384 */     this.userPrincipal = null;
/* 385 */     this.userDomain = null;
/* 386 */     this.userSID = null;
/* 387 */     this.domainSID = null;
/* 388 */     this.groups = null;
/* 389 */     this.primaryGroup = null;
/* 390 */     this.iToken = null;
/* 391 */     this.ntSystem = null;
/*     */     
/* 393 */     if (this.debug) {
/* 394 */       System.out.println("\t\t[NTLoginModule] completed logout processing");
/*     */     }
/*     */     
/* 397 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\auth\module\NTLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */