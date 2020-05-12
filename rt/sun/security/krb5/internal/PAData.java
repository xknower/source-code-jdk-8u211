/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.internal.util.KerberosString;
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
/*     */ public class PAData
/*     */ {
/*     */   private int pADataType;
/*  59 */   private byte[] pADataValue = null;
/*     */   
/*     */   private static final byte TAG_PATYPE = 1;
/*     */   private static final byte TAG_PAVALUE = 2;
/*     */   
/*     */   private PAData() {}
/*     */   
/*     */   public PAData(int paramInt, byte[] paramArrayOfbyte) {
/*  67 */     this.pADataType = paramInt;
/*  68 */     if (paramArrayOfbyte != null) {
/*  69 */       this.pADataValue = (byte[])paramArrayOfbyte.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  74 */     PAData pAData = new PAData();
/*  75 */     pAData.pADataType = this.pADataType;
/*  76 */     if (this.pADataValue != null) {
/*  77 */       pAData.pADataValue = new byte[this.pADataValue.length];
/*  78 */       System.arraycopy(this.pADataValue, 0, pAData.pADataValue, 0, this.pADataValue.length);
/*     */     } 
/*     */     
/*  81 */     return pAData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PAData(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  91 */     DerValue derValue = null;
/*  92 */     if (paramDerValue.getTag() != 48) {
/*  93 */       throw new Asn1Exception(906);
/*     */     }
/*  95 */     derValue = paramDerValue.getData().getDerValue();
/*  96 */     if ((derValue.getTag() & 0x1F) == 1) {
/*  97 */       this.pADataType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 100 */       throw new Asn1Exception(906);
/* 101 */     }  derValue = paramDerValue.getData().getDerValue();
/* 102 */     if ((derValue.getTag() & 0x1F) == 2) {
/* 103 */       this.pADataValue = derValue.getData().getOctetString();
/*     */     }
/* 105 */     if (paramDerValue.getData().available() > 0) {
/* 106 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 118 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 119 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 121 */     derOutputStream2.putInteger(this.pADataType);
/* 122 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 123 */     derOutputStream2 = new DerOutputStream();
/* 124 */     derOutputStream2.putOctetString(this.pADataValue);
/* 125 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     
/* 127 */     derOutputStream2 = new DerOutputStream();
/* 128 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 129 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 134 */     return this.pADataType;
/*     */   }
/*     */   
/*     */   public byte[] getValue() {
/* 138 */     return (this.pADataValue == null) ? null : (byte[])this.pADataValue.clone();
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
/*     */   public static int getPreferredEType(PAData[] paramArrayOfPAData, int paramInt) throws IOException, Asn1Exception {
/* 154 */     if (paramArrayOfPAData == null) return paramInt;
/*     */     
/* 156 */     DerValue derValue1 = null, derValue2 = null;
/* 157 */     for (PAData pAData : paramArrayOfPAData) {
/* 158 */       if (pAData.getValue() != null)
/* 159 */         switch (pAData.getType()) {
/*     */           case 11:
/* 161 */             derValue1 = new DerValue(pAData.getValue());
/*     */             break;
/*     */           case 19:
/* 164 */             derValue2 = new DerValue(pAData.getValue());
/*     */             break;
/*     */         }  
/*     */     } 
/* 168 */     if (derValue2 != null) {
/* 169 */       while (derValue2.data.available() > 0) {
/* 170 */         DerValue derValue = derValue2.data.getDerValue();
/* 171 */         ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue);
/* 172 */         if (eTypeInfo2.getParams() == null)
/*     */         {
/* 174 */           return eTypeInfo2.getEType();
/*     */         }
/*     */       } 
/*     */     }
/* 178 */     if (derValue1 != null && 
/* 179 */       derValue1.data.available() > 0) {
/* 180 */       DerValue derValue = derValue1.data.getDerValue();
/* 181 */       ETypeInfo eTypeInfo = new ETypeInfo(derValue);
/* 182 */       return eTypeInfo.getEType();
/*     */     } 
/*     */     
/* 185 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SaltAndParams
/*     */   {
/*     */     public final String salt;
/*     */     
/*     */     public final byte[] params;
/*     */ 
/*     */     
/*     */     public SaltAndParams(String param1String, byte[] param1ArrayOfbyte) {
/* 197 */       if (param1String != null && param1String.isEmpty()) param1String = null; 
/* 198 */       this.salt = param1String;
/* 199 */       this.params = param1ArrayOfbyte;
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
/*     */   public static SaltAndParams getSaltAndParams(int paramInt, PAData[] paramArrayOfPAData) throws Asn1Exception, IOException {
/* 215 */     if (paramArrayOfPAData == null) return null;
/*     */     
/* 217 */     DerValue derValue1 = null, derValue2 = null;
/* 218 */     String str = null;
/*     */     
/* 220 */     for (PAData pAData : paramArrayOfPAData) {
/* 221 */       if (pAData.getValue() != null)
/* 222 */         switch (pAData.getType()) {
/*     */           case 3:
/* 224 */             str = new String(pAData.getValue(), KerberosString.MSNAME ? "UTF8" : "8859_1");
/*     */             break;
/*     */           
/*     */           case 11:
/* 228 */             derValue1 = new DerValue(pAData.getValue());
/*     */             break;
/*     */           case 19:
/* 231 */             derValue2 = new DerValue(pAData.getValue());
/*     */             break;
/*     */         }  
/*     */     } 
/* 235 */     if (derValue2 != null) {
/* 236 */       while (derValue2.data.available() > 0) {
/* 237 */         DerValue derValue = derValue2.data.getDerValue();
/* 238 */         ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue);
/* 239 */         if (eTypeInfo2.getParams() == null && eTypeInfo2.getEType() == paramInt)
/*     */         {
/* 241 */           return new SaltAndParams(eTypeInfo2.getSalt(), eTypeInfo2.getParams());
/*     */         }
/*     */       } 
/*     */     }
/* 245 */     if (derValue1 != null) {
/* 246 */       while (derValue1.data.available() > 0) {
/* 247 */         DerValue derValue = derValue1.data.getDerValue();
/* 248 */         ETypeInfo eTypeInfo = new ETypeInfo(derValue);
/* 249 */         if (eTypeInfo.getEType() == paramInt) {
/* 250 */           return new SaltAndParams(eTypeInfo.getSalt(), null);
/*     */         }
/*     */       } 
/*     */     }
/* 254 */     if (str != null) {
/* 255 */       return new SaltAndParams(str, null);
/*     */     }
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 262 */     StringBuilder stringBuilder = new StringBuilder();
/* 263 */     stringBuilder.append(">>>Pre-Authentication Data:\n\t PA-DATA type = ")
/* 264 */       .append(this.pADataType).append('\n');
/*     */     
/* 266 */     switch (this.pADataType) {
/*     */       case 2:
/* 268 */         stringBuilder.append("\t PA-ENC-TIMESTAMP");
/*     */         break;
/*     */       case 11:
/* 271 */         if (this.pADataValue != null) {
/*     */           try {
/* 273 */             DerValue derValue = new DerValue(this.pADataValue);
/* 274 */             while (derValue.data.available() > 0) {
/* 275 */               DerValue derValue1 = derValue.data.getDerValue();
/* 276 */               ETypeInfo eTypeInfo = new ETypeInfo(derValue1);
/* 277 */               stringBuilder.append("\t PA-ETYPE-INFO etype = ")
/* 278 */                 .append(eTypeInfo.getEType())
/* 279 */                 .append(", salt = ")
/* 280 */                 .append(eTypeInfo.getSalt())
/* 281 */                 .append('\n');
/*     */             } 
/* 283 */           } catch (IOException|Asn1Exception iOException) {
/* 284 */             stringBuilder.append("\t <Unparseable PA-ETYPE-INFO>\n");
/*     */           } 
/*     */         }
/*     */         break;
/*     */       case 19:
/* 289 */         if (this.pADataValue != null) {
/*     */           try {
/* 291 */             DerValue derValue = new DerValue(this.pADataValue);
/* 292 */             while (derValue.data.available() > 0) {
/* 293 */               DerValue derValue1 = derValue.data.getDerValue();
/* 294 */               ETypeInfo2 eTypeInfo2 = new ETypeInfo2(derValue1);
/* 295 */               stringBuilder.append("\t PA-ETYPE-INFO2 etype = ")
/* 296 */                 .append(eTypeInfo2.getEType())
/* 297 */                 .append(", salt = ")
/* 298 */                 .append(eTypeInfo2.getSalt())
/* 299 */                 .append(", s2kparams = ");
/* 300 */               byte[] arrayOfByte = eTypeInfo2.getParams();
/* 301 */               if (arrayOfByte == null) {
/* 302 */                 stringBuilder.append("null\n"); continue;
/* 303 */               }  if (arrayOfByte.length == 0) {
/* 304 */                 stringBuilder.append("empty\n"); continue;
/*     */               } 
/* 306 */               stringBuilder.append((new HexDumpEncoder())
/* 307 */                   .encodeBuffer(arrayOfByte));
/*     */             }
/*     */           
/* 310 */           } catch (IOException|Asn1Exception iOException) {
/* 311 */             stringBuilder.append("\t <Unparseable PA-ETYPE-INFO>\n");
/*     */           } 
/*     */         }
/*     */         break;
/*     */       case 129:
/* 316 */         stringBuilder.append("\t PA-FOR-USER\n");
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 322 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\PAData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */