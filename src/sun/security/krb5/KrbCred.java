/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.EncKrbCredPart;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KRBCred;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbCredInfo;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbCred
/*     */ {
/*  48 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*  50 */   private byte[] obuf = null;
/*  51 */   private KRBCred credMessg = null;
/*  52 */   private Ticket ticket = null;
/*  53 */   private EncKrbCredPart encPart = null;
/*  54 */   private Credentials creds = null;
/*  55 */   private KerberosTime timeStamp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbCred(Credentials paramCredentials1, Credentials paramCredentials2, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  63 */     PrincipalName principalName1 = paramCredentials1.getClient();
/*  64 */     PrincipalName principalName2 = paramCredentials1.getServer();
/*  65 */     PrincipalName principalName3 = paramCredentials2.getServer();
/*  66 */     if (!paramCredentials2.getClient().equals(principalName1)) {
/*  67 */       throw new KrbException(60, "Client principal does not match");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     KDCOptions kDCOptions = new KDCOptions();
/*  75 */     kDCOptions.set(2, true);
/*  76 */     kDCOptions.set(1, true);
/*     */     
/*  78 */     HostAddresses hostAddresses = null;
/*     */ 
/*     */     
/*  81 */     if (principalName3.getNameType() == 3) {
/*  82 */       hostAddresses = new HostAddresses(principalName3);
/*     */     }
/*  84 */     KrbTgsReq krbTgsReq = new KrbTgsReq(kDCOptions, paramCredentials1, principalName2, null, null, null, null, hostAddresses, null, null, null);
/*     */     
/*  86 */     this.credMessg = createMessage(krbTgsReq.sendAndGetCreds(), paramEncryptionKey);
/*     */     
/*  88 */     this.obuf = this.credMessg.asn1Encode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KRBCred createMessage(Credentials paramCredentials, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  95 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/*  96 */     PrincipalName principalName1 = paramCredentials.getClient();
/*  97 */     Realm realm = principalName1.getRealm();
/*  98 */     PrincipalName principalName2 = paramCredentials.getServer();
/*     */     
/* 100 */     KrbCredInfo krbCredInfo = new KrbCredInfo(encryptionKey, principalName1, paramCredentials.flags, paramCredentials.authTime, paramCredentials.startTime, paramCredentials.endTime, paramCredentials.renewTill, principalName2, paramCredentials.cAddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.timeStamp = KerberosTime.now();
/* 107 */     KrbCredInfo[] arrayOfKrbCredInfo = { krbCredInfo };
/* 108 */     EncKrbCredPart encKrbCredPart = new EncKrbCredPart(arrayOfKrbCredInfo, this.timeStamp, null, null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     EncryptedData encryptedData = new EncryptedData(paramEncryptionKey, encKrbCredPart.asn1Encode(), 14);
/*     */     
/* 115 */     Ticket[] arrayOfTicket = { paramCredentials.ticket };
/*     */     
/* 117 */     this.credMessg = new KRBCred(arrayOfTicket, encryptedData);
/*     */     
/* 119 */     return this.credMessg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbCred(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/* 126 */     this.credMessg = new KRBCred(paramArrayOfbyte);
/*     */     
/* 128 */     this.ticket = this.credMessg.tickets[0];
/*     */     
/* 130 */     if (this.credMessg.encPart.getEType() == 0) {
/* 131 */       paramEncryptionKey = EncryptionKey.NULL_KEY;
/*     */     }
/* 133 */     byte[] arrayOfByte1 = this.credMessg.encPart.decrypt(paramEncryptionKey, 14);
/*     */     
/* 135 */     byte[] arrayOfByte2 = this.credMessg.encPart.reset(arrayOfByte1);
/* 136 */     DerValue derValue = new DerValue(arrayOfByte2);
/* 137 */     EncKrbCredPart encKrbCredPart = new EncKrbCredPart(derValue);
/*     */     
/* 139 */     this.timeStamp = encKrbCredPart.timeStamp;
/*     */     
/* 141 */     KrbCredInfo krbCredInfo = encKrbCredPart.ticketInfo[0];
/* 142 */     EncryptionKey encryptionKey = krbCredInfo.key;
/* 143 */     PrincipalName principalName1 = krbCredInfo.pname;
/* 144 */     TicketFlags ticketFlags = krbCredInfo.flags;
/* 145 */     KerberosTime kerberosTime1 = krbCredInfo.authtime;
/* 146 */     KerberosTime kerberosTime2 = krbCredInfo.starttime;
/* 147 */     KerberosTime kerberosTime3 = krbCredInfo.endtime;
/* 148 */     KerberosTime kerberosTime4 = krbCredInfo.renewTill;
/* 149 */     PrincipalName principalName2 = krbCredInfo.sname;
/* 150 */     HostAddresses hostAddresses = krbCredInfo.caddr;
/*     */     
/* 152 */     if (DEBUG) {
/* 153 */       System.out.println(">>>Delegated Creds have pname=" + principalName1 + " sname=" + principalName2 + " authtime=" + kerberosTime1 + " starttime=" + kerberosTime2 + " endtime=" + kerberosTime3 + "renewTill=" + kerberosTime4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     this.creds = new Credentials(this.ticket, principalName1, principalName2, encryptionKey, ticketFlags, kerberosTime1, kerberosTime2, kerberosTime3, kerberosTime4, hostAddresses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials[] getDelegatedCreds() {
/* 169 */     return new Credentials[] { this.creds };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMessage() {
/* 177 */     return this.obuf;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbCred.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */