/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.UnknownHostException;
/*     */ import sun.security.krb5.internal.APOptions;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KDCReqBody;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.TGSReq;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.crypto.Nonce;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbTgsReq
/*     */ {
/*     */   private PrincipalName princName;
/*     */   private PrincipalName servName;
/*     */   private TGSReq tgsReqMessg;
/*     */   private KerberosTime ctime;
/*  50 */   private Ticket secondTicket = null;
/*     */   
/*     */   private boolean useSubkey = false;
/*     */   EncryptionKey tgsReqKey;
/*  54 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   private byte[] obuf;
/*     */   
/*     */   private byte[] ibuf;
/*     */ 
/*     */   
/*     */   public KrbTgsReq(Credentials paramCredentials, PrincipalName paramPrincipalName) throws KrbException, IOException {
/*  63 */     this(new KDCOptions(), paramCredentials, paramPrincipalName, null, null, null, null, null, null, null, null);
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
/*     */   public KrbTgsReq(Credentials paramCredentials, Ticket paramTicket, PrincipalName paramPrincipalName) throws KrbException, IOException {
/*  81 */     this(KDCOptions.with(new int[] { 14, 1 }, ), paramCredentials, paramPrincipalName, null, null, null, null, null, null, new Ticket[] { paramTicket }, null);
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
/*     */   public KrbTgsReq(Credentials paramCredentials, PrincipalName paramPrincipalName, PAData paramPAData) throws KrbException, IOException {
/* 100 */     this(KDCOptions.with(new int[] { 1 }, ), paramCredentials, paramCredentials
/*     */         
/* 102 */         .getClient(), paramPrincipalName, null, null, null, null, null, null, null, null, paramPAData);
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
/*     */   KrbTgsReq(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/* 128 */     this(paramKDCOptions, paramCredentials, paramCredentials.getClient(), paramPrincipalName, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramArrayOfint, paramHostAddresses, paramAuthorizationData, paramArrayOfTicket, paramEncryptionKey, null);
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
/*     */   private KrbTgsReq(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey, PAData paramPAData) throws KrbException, IOException {
/* 148 */     this.princName = paramPrincipalName1;
/* 149 */     this.servName = paramPrincipalName2;
/* 150 */     this.ctime = KerberosTime.now();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (paramKDCOptions.get(1) && 
/* 156 */       !paramCredentials.flags.get(1)) {
/* 157 */       paramKDCOptions.set(1, false);
/*     */     }
/* 159 */     if (paramKDCOptions.get(2) && 
/* 160 */       !paramCredentials.flags.get(1)) {
/* 161 */       throw new KrbException(101);
/*     */     }
/* 163 */     if (paramKDCOptions.get(3) && 
/* 164 */       !paramCredentials.flags.get(3)) {
/* 165 */       throw new KrbException(101);
/*     */     }
/* 167 */     if (paramKDCOptions.get(4) && 
/* 168 */       !paramCredentials.flags.get(3)) {
/* 169 */       throw new KrbException(101);
/*     */     }
/* 171 */     if (paramKDCOptions.get(5) && 
/* 172 */       !paramCredentials.flags.get(5)) {
/* 173 */       throw new KrbException(101);
/*     */     }
/* 175 */     if (paramKDCOptions.get(8) && 
/* 176 */       !paramCredentials.flags.get(8)) {
/* 177 */       throw new KrbException(101);
/*     */     }
/*     */     
/* 180 */     if (paramKDCOptions.get(6))
/* 181 */     { if (!paramCredentials.flags.get(6)) {
/* 182 */         throw new KrbException(101);
/*     */       } }
/* 184 */     else if (paramKerberosTime1 != null) { paramKerberosTime1 = null; }
/*     */     
/* 186 */     if (paramKDCOptions.get(8))
/* 187 */     { if (!paramCredentials.flags.get(8)) {
/* 188 */         throw new KrbException(101);
/*     */       } }
/* 190 */     else if (paramKerberosTime3 != null) { paramKerberosTime3 = null; }
/*     */     
/* 192 */     if (paramKDCOptions.get(28) || paramKDCOptions.get(14)) {
/* 193 */       if (paramArrayOfTicket == null) {
/* 194 */         throw new KrbException(101);
/*     */       }
/*     */ 
/*     */       
/* 198 */       this.secondTicket = paramArrayOfTicket[0];
/*     */     }
/* 200 */     else if (paramArrayOfTicket != null) {
/* 201 */       paramArrayOfTicket = null;
/*     */     } 
/*     */     
/* 204 */     this.tgsReqMessg = createRequest(paramKDCOptions, paramCredentials.ticket, paramCredentials.key, this.ctime, this.princName, this.servName, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramArrayOfint, paramHostAddresses, paramAuthorizationData, paramArrayOfTicket, paramEncryptionKey, paramPAData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     this.obuf = this.tgsReqMessg.asn1Encode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (paramCredentials.flags.get(2)) {
/* 231 */       paramKDCOptions.set(2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send() throws IOException, KrbException {
/* 242 */     String str = null;
/* 243 */     if (this.servName != null)
/* 244 */       str = this.servName.getRealmString(); 
/* 245 */     KdcComm kdcComm = new KdcComm(str);
/* 246 */     this.ibuf = kdcComm.send(this.obuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public KrbTgsRep getReply() throws KrbException, IOException {
/* 251 */     return new KrbTgsRep(this.ibuf, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials sendAndGetCreds() throws IOException, KrbException {
/* 259 */     KrbTgsRep krbTgsRep = null;
/* 260 */     Object object = null;
/* 261 */     send();
/* 262 */     krbTgsRep = getReply();
/* 263 */     return krbTgsRep.getCreds();
/*     */   }
/*     */   
/*     */   KerberosTime getCtime() {
/* 267 */     return this.ctime;
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
/*     */   private TGSReq createRequest(KDCOptions paramKDCOptions, Ticket paramTicket, EncryptionKey paramEncryptionKey1, KerberosTime paramKerberosTime1, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey2, PAData paramPAData) throws IOException, KrbException, UnknownHostException {
/*     */     Checksum checksum;
/* 287 */     KerberosTime kerberosTime = null;
/* 288 */     if (paramKerberosTime3 == null) {
/* 289 */       kerberosTime = new KerberosTime(0L);
/*     */     } else {
/* 291 */       kerberosTime = paramKerberosTime3;
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
/* 303 */     this.tgsReqKey = paramEncryptionKey1;
/*     */     
/* 305 */     int[] arrayOfInt = null;
/* 306 */     if (paramArrayOfint == null) {
/* 307 */       arrayOfInt = EType.getDefaults("default_tgs_enctypes");
/*     */     } else {
/* 309 */       arrayOfInt = paramArrayOfint;
/*     */     } 
/*     */     
/* 312 */     EncryptionKey encryptionKey = null;
/* 313 */     EncryptedData encryptedData = null;
/* 314 */     if (paramAuthorizationData != null) {
/* 315 */       byte[] arrayOfByte = paramAuthorizationData.asn1Encode();
/* 316 */       if (paramEncryptionKey2 != null) {
/* 317 */         encryptionKey = paramEncryptionKey2;
/* 318 */         this.tgsReqKey = paramEncryptionKey2;
/* 319 */         this.useSubkey = true;
/* 320 */         encryptedData = new EncryptedData(encryptionKey, arrayOfByte, 5);
/*     */       } else {
/*     */         
/* 323 */         encryptedData = new EncryptedData(paramEncryptionKey1, arrayOfByte, 4);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     KDCReqBody kDCReqBody = new KDCReqBody(paramKDCOptions, paramPrincipalName1, paramPrincipalName2, paramKerberosTime2, kerberosTime, paramKerberosTime4, Nonce.value(), arrayOfInt, paramHostAddresses, encryptedData, paramArrayOfTicket);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     byte[] arrayOfByte1 = kDCReqBody.asn1Encode(12);
/*     */ 
/*     */ 
/*     */     
/* 344 */     switch (Checksum.CKSUMTYPE_DEFAULT) {
/*     */       case -138:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 8:
/*     */       case 12:
/*     */       case 15:
/*     */       case 16:
/* 354 */         checksum = new Checksum(Checksum.CKSUMTYPE_DEFAULT, arrayOfByte1, paramEncryptionKey1, 6);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 361 */         checksum = new Checksum(Checksum.CKSUMTYPE_DEFAULT, arrayOfByte1);
/*     */         break;
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
/*     */     
/* 375 */     byte[] arrayOfByte2 = (new KrbApReq(new APOptions(), paramTicket, paramEncryptionKey1, paramPrincipalName1, checksum, paramKerberosTime1, encryptionKey, null, null)).getMessage();
/*     */     
/* 377 */     PAData pAData = new PAData(1, arrayOfByte2);
/* 378 */     (new PAData[2])[0] = paramPAData; (new PAData[2])[1] = pAData; (new PAData[1])[0] = pAData; return new TGSReq((paramPAData != null) ? new PAData[2] : new PAData[1], kDCReqBody);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TGSReq getMessage() {
/* 386 */     return this.tgsReqMessg;
/*     */   }
/*     */   
/*     */   Ticket getSecondTicket() {
/* 390 */     return this.secondTicket;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void debug(String paramString) {}
/*     */ 
/*     */   
/*     */   boolean usedSubkey() {
/* 398 */     return this.useSubkey;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbTgsReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */