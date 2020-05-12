/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ public class KDCReq
/*     */ {
/*     */   public KDCReqBody reqBody;
/*     */   private int pvno;
/*     */   private int msgType;
/*  64 */   private PAData[] pAData = null;
/*     */ 
/*     */   
/*     */   public KDCReq(PAData[] paramArrayOfPAData, KDCReqBody paramKDCReqBody, int paramInt) throws IOException {
/*  68 */     this.pvno = 5;
/*  69 */     this.msgType = paramInt;
/*  70 */     if (paramArrayOfPAData != null) {
/*  71 */       this.pAData = new PAData[paramArrayOfPAData.length];
/*  72 */       for (byte b = 0; b < paramArrayOfPAData.length; b++) {
/*  73 */         if (paramArrayOfPAData[b] == null) {
/*  74 */           throw new IOException("Cannot create a KDCRep");
/*     */         }
/*  76 */         this.pAData[b] = (PAData)paramArrayOfPAData[b].clone();
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     this.reqBody = paramKDCReqBody;
/*     */   }
/*     */ 
/*     */   
/*     */   public KDCReq() {}
/*     */ 
/*     */   
/*     */   public KDCReq(byte[] paramArrayOfbyte, int paramInt) throws Asn1Exception, IOException, KrbException {
/*  88 */     init(new DerValue(paramArrayOfbyte), paramInt);
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
/*     */   public KDCReq(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, KrbException {
/* 102 */     init(paramDerValue, paramInt);
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
/*     */   protected void init(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, KrbException {
/* 120 */     if ((paramDerValue.getTag() & 0x1F) != paramInt) {
/* 121 */       throw new Asn1Exception(906);
/*     */     }
/* 123 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 124 */     if (derValue1.getTag() != 48) {
/* 125 */       throw new Asn1Exception(906);
/*     */     }
/* 127 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 128 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 129 */       BigInteger bigInteger = derValue2.getData().getBigInteger();
/* 130 */       this.pvno = bigInteger.intValue();
/* 131 */       if (this.pvno != 5) {
/* 132 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/* 135 */       throw new Asn1Exception(906);
/*     */     } 
/* 137 */     derValue2 = derValue1.getData().getDerValue();
/* 138 */     if ((derValue2.getTag() & 0x1F) == 2) {
/* 139 */       BigInteger bigInteger = derValue2.getData().getBigInteger();
/* 140 */       this.msgType = bigInteger.intValue();
/* 141 */       if (this.msgType != paramInt) {
/* 142 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 145 */       throw new Asn1Exception(906);
/*     */     } 
/* 147 */     if ((derValue1.getData().peekByte() & 0x1F) == 3) {
/* 148 */       derValue2 = derValue1.getData().getDerValue();
/* 149 */       DerValue derValue = derValue2.getData().getDerValue();
/* 150 */       if (derValue.getTag() != 48) {
/* 151 */         throw new Asn1Exception(906);
/*     */       }
/* 153 */       Vector<PAData> vector = new Vector();
/* 154 */       while (derValue.getData().available() > 0) {
/* 155 */         vector.addElement(new PAData(derValue.getData().getDerValue()));
/*     */       }
/* 157 */       if (vector.size() > 0) {
/* 158 */         this.pAData = new PAData[vector.size()];
/* 159 */         vector.copyInto((Object[])this.pAData);
/*     */       } 
/*     */     } else {
/* 162 */       this.pAData = null;
/*     */     } 
/* 164 */     derValue2 = derValue1.getData().getDerValue();
/* 165 */     if ((derValue2.getTag() & 0x1F) == 4) {
/* 166 */       DerValue derValue = derValue2.getData().getDerValue();
/* 167 */       this.reqBody = new KDCReqBody(derValue, this.msgType);
/*     */     } else {
/* 169 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 183 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 184 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 185 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 186 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     
/* 188 */     derOutputStream1 = new DerOutputStream();
/* 189 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 190 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)2), derOutputStream1);
/*     */     
/* 192 */     if (this.pAData != null && this.pAData.length > 0) {
/* 193 */       derOutputStream1 = new DerOutputStream();
/* 194 */       for (byte b = 0; b < this.pAData.length; b++) {
/* 195 */         derOutputStream1.write(this.pAData[b].asn1Encode());
/*     */       }
/* 197 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 198 */       derOutputStream.write((byte)48, derOutputStream1);
/* 199 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)3), derOutputStream);
/*     */     } 
/*     */     
/* 202 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)4), this.reqBody
/* 203 */         .asn1Encode(this.msgType));
/* 204 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 205 */     derOutputStream2.write((byte)48, derOutputStream3);
/* 206 */     derOutputStream3 = new DerOutputStream();
/* 207 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)this.msgType), derOutputStream2);
/*     */     
/* 209 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] asn1EncodeReqBody() throws Asn1Exception, IOException {
/* 213 */     return this.reqBody.asn1Encode(this.msgType);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\KDCReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */