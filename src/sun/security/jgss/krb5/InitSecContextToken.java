/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import com.sun.security.jgss.AuthorizationDataEntry;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetAddress;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbApReq;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.KerberosTime;
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
/*     */ class InitSecContextToken
/*     */   extends InitialToken
/*     */ {
/*  39 */   private KrbApReq apReq = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InitSecContextToken(Krb5Context paramKrb5Context, Credentials paramCredentials1, Credentials paramCredentials2) throws KrbException, IOException, GSSException {
/*  55 */     boolean bool = paramKrb5Context.getMutualAuthState();
/*  56 */     boolean bool1 = true;
/*  57 */     boolean bool2 = true;
/*     */     
/*  59 */     InitialToken.OverloadedChecksum overloadedChecksum = new InitialToken.OverloadedChecksum(this, paramKrb5Context, paramCredentials1, paramCredentials2);
/*     */ 
/*     */     
/*  62 */     Checksum checksum = overloadedChecksum.getChecksum();
/*     */     
/*  64 */     paramKrb5Context.setTktFlags(paramCredentials2.getFlags());
/*  65 */     paramKrb5Context.setAuthTime((new KerberosTime(paramCredentials2
/*  66 */           .getAuthTime())).toString());
/*  67 */     this.apReq = new KrbApReq(paramCredentials2, bool, bool1, bool2, checksum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     paramKrb5Context.resetMySequenceNumber(this.apReq.getSeqNumber().intValue());
/*     */     
/*  75 */     EncryptionKey encryptionKey = this.apReq.getSubKey();
/*  76 */     if (encryptionKey != null) {
/*  77 */       paramKrb5Context.setKey(1, encryptionKey);
/*     */     } else {
/*  79 */       paramKrb5Context.setKey(0, paramCredentials2.getSessionKey());
/*     */     } 
/*  81 */     if (!bool) {
/*  82 */       paramKrb5Context.resetPeerSequenceNumber(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InitSecContextToken(Krb5Context paramKrb5Context, Krb5AcceptCredential paramKrb5AcceptCredential, InputStream paramInputStream) throws IOException, GSSException, KrbException {
/*  93 */     int i = paramInputStream.read() << 8 | paramInputStream.read();
/*     */     
/*  95 */     if (i != 256) {
/*  96 */       throw new GSSException(10, -1, "AP_REQ token id does not match!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 101 */     byte[] arrayOfByte = (new DerValue(paramInputStream)).toByteArray();
/*     */ 
/*     */     
/* 104 */     InetAddress inetAddress = null;
/* 105 */     if (paramKrb5Context.getChannelBinding() != null) {
/* 106 */       inetAddress = paramKrb5Context.getChannelBinding().getInitiatorAddress();
/*     */     }
/* 108 */     this.apReq = new KrbApReq(arrayOfByte, paramKrb5AcceptCredential, inetAddress);
/*     */ 
/*     */     
/* 111 */     EncryptionKey encryptionKey1 = this.apReq.getCreds().getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     EncryptionKey encryptionKey2 = this.apReq.getSubKey();
/* 119 */     if (encryptionKey2 != null) {
/* 120 */       paramKrb5Context.setKey(1, encryptionKey2);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 126 */       paramKrb5Context.setKey(0, encryptionKey1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 131 */     InitialToken.OverloadedChecksum overloadedChecksum = new InitialToken.OverloadedChecksum(this, paramKrb5Context, this.apReq.getChecksum(), encryptionKey1, encryptionKey2);
/* 132 */     overloadedChecksum.setContextFlags(paramKrb5Context);
/* 133 */     Credentials credentials = overloadedChecksum.getDelegatedCreds();
/* 134 */     if (credentials != null) {
/*     */       
/* 136 */       Krb5InitCredential krb5InitCredential = Krb5InitCredential.getInstance((Krb5NameElement)paramKrb5Context
/* 137 */           .getSrcName(), credentials);
/*     */       
/* 139 */       paramKrb5Context.setDelegCred(krb5InitCredential);
/*     */     } 
/*     */     
/* 142 */     Integer integer = this.apReq.getSeqNumber();
/*     */     
/* 144 */     boolean bool = (integer != null) ? integer.intValue() : false;
/*     */     
/* 146 */     paramKrb5Context.resetPeerSequenceNumber(bool);
/* 147 */     if (!paramKrb5Context.getMutualAuthState())
/*     */     {
/*     */       
/* 150 */       paramKrb5Context.resetMySequenceNumber(bool); } 
/* 151 */     paramKrb5Context.setAuthTime((new KerberosTime(this.apReq
/* 152 */           .getCreds().getAuthTime())).toString());
/* 153 */     paramKrb5Context.setTktFlags(this.apReq.getCreds().getFlags());
/* 154 */     AuthorizationData authorizationData = this.apReq.getCreds().getAuthzData();
/* 155 */     if (authorizationData == null) {
/* 156 */       paramKrb5Context.setAuthzData(null);
/*     */     } else {
/*     */       
/* 159 */       AuthorizationDataEntry[] arrayOfAuthorizationDataEntry = new AuthorizationDataEntry[authorizationData.count()];
/* 160 */       for (byte b = 0; b < authorizationData.count(); b++) {
/* 161 */         arrayOfAuthorizationDataEntry[b] = new AuthorizationDataEntry(
/* 162 */             (authorizationData.item(b)).adType, (authorizationData.item(b)).adData);
/*     */       }
/* 164 */       paramKrb5Context.setAuthzData(arrayOfAuthorizationDataEntry);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final KrbApReq getKrbApReq() {
/* 169 */     return this.apReq;
/*     */   }
/*     */   
/*     */   public final byte[] encode() throws IOException {
/* 173 */     byte[] arrayOfByte1 = this.apReq.getMessage();
/* 174 */     byte[] arrayOfByte2 = new byte[2 + arrayOfByte1.length];
/* 175 */     writeInt(256, arrayOfByte2, 0);
/* 176 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 2, arrayOfByte1.length);
/*     */ 
/*     */     
/* 179 */     return arrayOfByte2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\InitSecContextToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */