/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.jgss.krb5.Krb5Util;
/*     */ import sun.security.krb5.internal.ASRep;
/*     */ import sun.security.krb5.internal.ASReq;
/*     */ import sun.security.krb5.internal.EncASRepPart;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.crypto.EType;
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
/*     */ 
/*     */ class KrbAsRep
/*     */   extends KrbKdcRep
/*     */ {
/*     */   private ASRep rep;
/*     */   private Credentials creds;
/*  54 */   private boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   KrbAsRep(byte[] paramArrayOfbyte) throws KrbException, Asn1Exception, IOException {
/*  58 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*     */     try {
/*  60 */       this.rep = new ASRep(derValue);
/*  61 */     } catch (Asn1Exception asn1Exception) {
/*  62 */       KrbException krbException; this.rep = null;
/*  63 */       KRBError kRBError = new KRBError(derValue);
/*  64 */       String str1 = kRBError.getErrorString();
/*  65 */       String str2 = null;
/*     */       
/*  67 */       if (str1 != null && str1.length() > 0) {
/*  68 */         if (str1.charAt(str1.length() - 1) == '\000') {
/*  69 */           str2 = str1.substring(0, str1.length() - 1);
/*     */         } else {
/*  71 */           str2 = str1;
/*     */         } 
/*     */       }
/*  74 */       if (str2 == null) {
/*     */         
/*  76 */         krbException = new KrbException(kRBError);
/*     */       } else {
/*  78 */         if (this.DEBUG) {
/*  79 */           System.out.println("KRBError received: " + str2);
/*     */         }
/*     */         
/*  82 */         krbException = new KrbException(kRBError, str2);
/*     */       } 
/*  84 */       krbException.initCause(asn1Exception);
/*  85 */       throw krbException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   PAData[] getPA() {
/*  91 */     return this.rep.pAData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void decryptUsingKeyTab(KeyTab paramKeyTab, KrbAsReq paramKrbAsReq, PrincipalName paramPrincipalName) throws KrbException, Asn1Exception, IOException {
/* 102 */     EncryptionKey encryptionKey = null;
/* 103 */     int i = this.rep.encPart.getEType();
/* 104 */     Integer integer = this.rep.encPart.kvno;
/*     */     try {
/* 106 */       encryptionKey = EncryptionKey.findKey(i, integer, 
/* 107 */           Krb5Util.keysFromJavaxKeyTab(paramKeyTab, paramPrincipalName));
/* 108 */     } catch (KrbException krbException) {
/* 109 */       if (krbException.returnCode() == 44)
/*     */       {
/*     */         
/* 112 */         encryptionKey = EncryptionKey.findKey(i, 
/* 113 */             Krb5Util.keysFromJavaxKeyTab(paramKeyTab, paramPrincipalName));
/*     */       }
/*     */     } 
/* 116 */     if (encryptionKey == null) {
/* 117 */       throw new KrbException(400, "Cannot find key for type/kvno to decrypt AS REP - " + 
/*     */           
/* 119 */           EType.toString(i) + "/" + integer);
/*     */     }
/* 121 */     decrypt(encryptionKey, paramKrbAsReq);
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
/*     */   void decryptUsingPassword(char[] paramArrayOfchar, KrbAsReq paramKrbAsReq, PrincipalName paramPrincipalName) throws KrbException, Asn1Exception, IOException {
/* 133 */     int i = this.rep.encPart.getEType();
/* 134 */     EncryptionKey encryptionKey = EncryptionKey.acquireSecretKey(paramPrincipalName, paramArrayOfchar, i, 
/*     */ 
/*     */ 
/*     */         
/* 138 */         PAData.getSaltAndParams(i, this.rep.pAData));
/* 139 */     decrypt(encryptionKey, paramKrbAsReq);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decrypt(EncryptionKey paramEncryptionKey, KrbAsReq paramKrbAsReq) throws KrbException, Asn1Exception, IOException {
/* 149 */     byte[] arrayOfByte1 = this.rep.encPart.decrypt(paramEncryptionKey, 3);
/*     */     
/* 151 */     byte[] arrayOfByte2 = this.rep.encPart.reset(arrayOfByte1);
/*     */     
/* 153 */     DerValue derValue = new DerValue(arrayOfByte2);
/* 154 */     EncASRepPart encASRepPart = new EncASRepPart(derValue);
/* 155 */     this.rep.encKDCRepPart = encASRepPart;
/*     */     
/* 157 */     ASReq aSReq = paramKrbAsReq.getMessage();
/* 158 */     check(true, aSReq, this.rep);
/*     */     
/* 160 */     this.creds = new Credentials(this.rep.ticket, aSReq.reqBody.cname, encASRepPart.sname, encASRepPart.key, encASRepPart.flags, encASRepPart.authtime, encASRepPart.starttime, encASRepPart.endtime, encASRepPart.renewTill, encASRepPart.caddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (this.DEBUG) {
/* 172 */       System.out.println(">>> KrbAsRep cons in KrbAsReq.getReply " + aSReq.reqBody.cname
/* 173 */           .getNameString());
/*     */     }
/*     */   }
/*     */   
/*     */   Credentials getCreds() {
/* 178 */     return Objects.<Credentials>requireNonNull(this.creds, "Creds not available yet.");
/*     */   }
/*     */   
/*     */   Credentials getCCreds() {
/* 182 */     return new Credentials(this.rep);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbAsRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */