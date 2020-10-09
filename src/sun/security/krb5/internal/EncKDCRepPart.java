/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncKDCRepPart
/*     */ {
/*     */   public EncryptionKey key;
/*     */   public LastReq lastReq;
/*     */   public int nonce;
/*     */   public KerberosTime keyExpiration;
/*     */   public TicketFlags flags;
/*     */   public KerberosTime authtime;
/*     */   public KerberosTime starttime;
/*     */   public KerberosTime endtime;
/*     */   public KerberosTime renewTill;
/*     */   public PrincipalName sname;
/*     */   public HostAddresses caddr;
/*     */   public int msgType;
/*     */   
/*     */   public EncKDCRepPart(EncryptionKey paramEncryptionKey, LastReq paramLastReq, int paramInt1, KerberosTime paramKerberosTime1, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, KerberosTime paramKerberosTime5, PrincipalName paramPrincipalName, HostAddresses paramHostAddresses, int paramInt2) {
/*  94 */     this.key = paramEncryptionKey;
/*  95 */     this.lastReq = paramLastReq;
/*  96 */     this.nonce = paramInt1;
/*  97 */     this.keyExpiration = paramKerberosTime1;
/*  98 */     this.flags = paramTicketFlags;
/*  99 */     this.authtime = paramKerberosTime2;
/* 100 */     this.starttime = paramKerberosTime3;
/* 101 */     this.endtime = paramKerberosTime4;
/* 102 */     this.renewTill = paramKerberosTime5;
/* 103 */     this.sname = paramPrincipalName;
/* 104 */     this.caddr = paramHostAddresses;
/* 105 */     this.msgType = paramInt2;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKDCRepPart() {}
/*     */ 
/*     */   
/*     */   public EncKDCRepPart(byte[] paramArrayOfbyte, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 113 */     init(new DerValue(paramArrayOfbyte), paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKDCRepPart(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 118 */     init(paramDerValue, paramInt);
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
/*     */   protected void init(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 135 */     this.msgType = paramDerValue.getTag() & 0x1F;
/* 136 */     if (this.msgType != 25 && this.msgType != 26)
/*     */     {
/* 138 */       throw new Asn1Exception(906);
/*     */     }
/* 140 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 141 */     if (derValue1.getTag() != 48) {
/* 142 */       throw new Asn1Exception(906);
/*     */     }
/* 144 */     this.key = EncryptionKey.parse(derValue1.getData(), (byte)0, false);
/* 145 */     this.lastReq = LastReq.parse(derValue1.getData(), (byte)1, false);
/* 146 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 147 */     if ((derValue2.getTag() & 0x1F) == 2) {
/* 148 */       this.nonce = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 150 */       throw new Asn1Exception(906);
/*     */     } 
/* 152 */     this.keyExpiration = KerberosTime.parse(derValue1.getData(), (byte)3, true);
/* 153 */     this.flags = TicketFlags.parse(derValue1.getData(), (byte)4, false);
/* 154 */     this.authtime = KerberosTime.parse(derValue1.getData(), (byte)5, false);
/* 155 */     this.starttime = KerberosTime.parse(derValue1.getData(), (byte)6, true);
/* 156 */     this.endtime = KerberosTime.parse(derValue1.getData(), (byte)7, false);
/* 157 */     this.renewTill = KerberosTime.parse(derValue1.getData(), (byte)8, true);
/* 158 */     Realm realm = Realm.parse(derValue1.getData(), (byte)9, false);
/* 159 */     this.sname = PrincipalName.parse(derValue1.getData(), (byte)10, false, realm);
/* 160 */     if (derValue1.getData().available() > 0) {
/* 161 */       this.caddr = HostAddresses.parse(derValue1.getData(), (byte)11, true);
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
/*     */   public byte[] asn1Encode(int paramInt) throws Asn1Exception, IOException {
/* 178 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 179 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 180 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), this.key
/* 181 */         .asn1Encode());
/* 182 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), this.lastReq
/* 183 */         .asn1Encode());
/* 184 */     derOutputStream1.putInteger(BigInteger.valueOf(this.nonce));
/* 185 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)2), derOutputStream1);
/*     */ 
/*     */     
/* 188 */     if (this.keyExpiration != null) {
/* 189 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), this.keyExpiration
/* 190 */           .asn1Encode());
/*     */     }
/* 192 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)4), this.flags
/* 193 */         .asn1Encode());
/* 194 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)5), this.authtime
/* 195 */         .asn1Encode());
/* 196 */     if (this.starttime != null) {
/* 197 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)6), this.starttime
/* 198 */           .asn1Encode());
/*     */     }
/* 200 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)7), this.endtime
/* 201 */         .asn1Encode());
/* 202 */     if (this.renewTill != null) {
/* 203 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)8), this.renewTill
/* 204 */           .asn1Encode());
/*     */     }
/* 206 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)9), this.sname
/* 207 */         .getRealm().asn1Encode());
/* 208 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)10), this.sname
/* 209 */         .asn1Encode());
/* 210 */     if (this.caddr != null) {
/* 211 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)11), this.caddr
/* 212 */           .asn1Encode());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     derOutputStream1 = new DerOutputStream();
/* 218 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 219 */     derOutputStream2 = new DerOutputStream();
/* 220 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)this.msgType), derOutputStream1);
/*     */     
/* 222 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\EncKDCRepPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */