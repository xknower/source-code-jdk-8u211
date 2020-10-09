/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.jgss.krb5.Krb5Util;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KrbAsReqBuilder
/*     */ {
/*     */   private KDCOptions options;
/*     */   private PrincipalName cname;
/*     */   private PrincipalName sname;
/*     */   private KerberosTime from;
/*     */   private KerberosTime till;
/*     */   private KerberosTime rtime;
/*     */   private HostAddresses addresses;
/*     */   private final char[] password;
/*     */   private final KeyTab ktab;
/*     */   private PAData[] paList;
/*     */   private KrbAsReq req;
/*     */   private KrbAsRep rep;
/*     */   private State state;
/*     */   
/*     */   private enum State
/*     */   {
/*  93 */     INIT,
/*  94 */     REQ_OK,
/*  95 */     DESTROYED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(PrincipalName paramPrincipalName) throws KrbException {
/* 102 */     this.cname = paramPrincipalName;
/* 103 */     this.state = State.INIT;
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
/*     */   public KrbAsReqBuilder(PrincipalName paramPrincipalName, KeyTab paramKeyTab) throws KrbException {
/* 119 */     init(paramPrincipalName);
/* 120 */     this.ktab = paramKeyTab;
/* 121 */     this.password = null;
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
/*     */   public KrbAsReqBuilder(PrincipalName paramPrincipalName, char[] paramArrayOfchar) throws KrbException {
/* 137 */     init(paramPrincipalName);
/* 138 */     this.password = (char[])paramArrayOfchar.clone();
/* 139 */     this.ktab = null;
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
/*     */   public EncryptionKey[] getKeys(boolean paramBoolean) throws KrbException {
/* 155 */     checkState(paramBoolean ? State.REQ_OK : State.INIT, "Cannot get keys");
/* 156 */     if (this.password != null) {
/* 157 */       int[] arrayOfInt = EType.getDefaults("default_tkt_enctypes");
/* 158 */       EncryptionKey[] arrayOfEncryptionKey = new EncryptionKey[arrayOfInt.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       String str = null; try {
/*     */         byte b;
/* 178 */         for (b = 0; b < arrayOfInt.length; b++) {
/*     */ 
/*     */           
/* 181 */           PAData.SaltAndParams saltAndParams = PAData.getSaltAndParams(arrayOfInt[b], this.paList);
/* 182 */           if (saltAndParams != null) {
/*     */ 
/*     */             
/* 185 */             if (arrayOfInt[b] != 23 && saltAndParams.salt != null)
/*     */             {
/* 187 */               str = saltAndParams.salt;
/*     */             }
/* 189 */             arrayOfEncryptionKey[b] = EncryptionKey.acquireSecretKey(this.cname, this.password, arrayOfInt[b], saltAndParams);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 196 */         if (str == null) str = this.cname.getSalt(); 
/* 197 */         for (b = 0; b < arrayOfInt.length; b++)
/*     */         {
/* 199 */           if (arrayOfEncryptionKey[b] == null) {
/* 200 */             arrayOfEncryptionKey[b] = EncryptionKey.acquireSecretKey(this.password, str, arrayOfInt[b], (byte[])null);
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 206 */       catch (IOException iOException) {
/* 207 */         KrbException krbException = new KrbException(909);
/* 208 */         krbException.initCause(iOException);
/* 209 */         throw krbException;
/*     */       } 
/* 211 */       return arrayOfEncryptionKey;
/*     */     } 
/* 213 */     throw new IllegalStateException("Required password not provided");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptions(KDCOptions paramKDCOptions) {
/* 223 */     checkState(State.INIT, "Cannot specify options");
/* 224 */     this.options = paramKDCOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(PrincipalName paramPrincipalName) {
/* 233 */     checkState(State.INIT, "Cannot specify target");
/* 234 */     this.sname = paramPrincipalName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddresses(HostAddresses paramHostAddresses) {
/* 243 */     checkState(State.INIT, "Cannot specify addresses");
/* 244 */     this.addresses = paramHostAddresses;
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
/*     */   private KrbAsReq build(EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*     */     int[] arrayOfInt;
/* 257 */     if (this.password != null) {
/* 258 */       arrayOfInt = EType.getDefaults("default_tkt_enctypes");
/*     */     } else {
/* 260 */       EncryptionKey[] arrayOfEncryptionKey = Krb5Util.keysFromJavaxKeyTab(this.ktab, this.cname);
/* 261 */       arrayOfInt = EType.getDefaults("default_tkt_enctypes", arrayOfEncryptionKey);
/*     */       
/* 263 */       for (EncryptionKey encryptionKey : arrayOfEncryptionKey) encryptionKey.destroy(); 
/*     */     } 
/* 265 */     return new KrbAsReq(paramEncryptionKey, this.options, this.cname, this.sname, this.from, this.till, this.rtime, arrayOfInt, this.addresses);
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
/*     */   private KrbAsReqBuilder resolve() throws KrbException, Asn1Exception, IOException {
/* 284 */     if (this.ktab != null) {
/* 285 */       this.rep.decryptUsingKeyTab(this.ktab, this.req, this.cname);
/*     */     } else {
/* 287 */       this.rep.decryptUsingPassword(this.password, this.req, this.cname);
/*     */     } 
/* 289 */     if (this.rep.getPA() != null) {
/* 290 */       if (this.paList == null || this.paList.length == 0) {
/* 291 */         this.paList = this.rep.getPA();
/*     */       } else {
/* 293 */         int i = (this.rep.getPA()).length;
/* 294 */         if (i > 0) {
/* 295 */           int j = this.paList.length;
/* 296 */           this.paList = Arrays.<PAData>copyOf(this.paList, this.paList.length + i);
/* 297 */           System.arraycopy(this.rep.getPA(), 0, this.paList, j, i);
/*     */         } 
/*     */       } 
/*     */     }
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KrbAsReqBuilder send() throws KrbException, IOException {
/* 310 */     boolean bool = false;
/* 311 */     KdcComm kdcComm = new KdcComm(this.cname.getRealmAsString());
/* 312 */     EncryptionKey encryptionKey = null;
/*     */     while (true) {
/*     */       try {
/* 315 */         this.req = build(encryptionKey);
/* 316 */         this.rep = new KrbAsRep(kdcComm.send(this.req.encoding()));
/* 317 */         return this;
/* 318 */       } catch (KrbException krbException) {
/* 319 */         if (!bool && (krbException
/* 320 */           .returnCode() == 24 || krbException
/* 321 */           .returnCode() == 25))
/* 322 */         { if (Krb5.DEBUG) {
/* 323 */             System.out.println("KrbAsReqBuilder: PREAUTH FAILED/REQ, re-send AS-REQ");
/*     */           }
/*     */           
/* 326 */           bool = true;
/* 327 */           KRBError kRBError = krbException.getError();
/* 328 */           int i = PAData.getPreferredEType(kRBError.getPA(), 
/* 329 */               EType.getDefaults("default_tkt_enctypes")[0]);
/* 330 */           if (this.password == null) {
/* 331 */             EncryptionKey[] arrayOfEncryptionKey = Krb5Util.keysFromJavaxKeyTab(this.ktab, this.cname);
/* 332 */             encryptionKey = EncryptionKey.findKey(i, arrayOfEncryptionKey);
/* 333 */             if (encryptionKey != null) encryptionKey = (EncryptionKey)encryptionKey.clone(); 
/* 334 */             for (EncryptionKey encryptionKey1 : arrayOfEncryptionKey) encryptionKey1.destroy(); 
/*     */           } else {
/* 336 */             encryptionKey = EncryptionKey.acquireSecretKey(this.cname, this.password, i, 
/*     */ 
/*     */                 
/* 339 */                 PAData.getSaltAndParams(i, kRBError
/* 340 */                   .getPA()));
/*     */           } 
/* 342 */           this.paList = kRBError.getPA(); continue; }  break;
/*     */       } 
/* 344 */     }  throw krbException;
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
/*     */   public KrbAsReqBuilder action() throws KrbException, Asn1Exception, IOException {
/* 359 */     checkState(State.INIT, "Cannot call action");
/* 360 */     this.state = State.REQ_OK;
/* 361 */     return send().resolve();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds() {
/* 368 */     checkState(State.REQ_OK, "Cannot retrieve creds");
/* 369 */     return this.rep.getCreds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCCreds() {
/* 376 */     checkState(State.REQ_OK, "Cannot retrieve CCreds");
/* 377 */     return this.rep.getCCreds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 384 */     this.state = State.DESTROYED;
/* 385 */     if (this.password != null) {
/* 386 */       Arrays.fill(this.password, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkState(State paramState, String paramString) {
/* 397 */     if (this.state != paramState)
/* 398 */       throw new IllegalStateException(paramString + " at " + paramState + " state"); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbAsReqBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */