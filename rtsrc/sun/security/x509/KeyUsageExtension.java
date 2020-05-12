/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Enumeration;
/*     */ import sun.security.util.BitArray;
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
/*     */ public class KeyUsageExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.KeyUsage";
/*     */   public static final String NAME = "KeyUsage";
/*     */   public static final String DIGITAL_SIGNATURE = "digital_signature";
/*     */   public static final String NON_REPUDIATION = "non_repudiation";
/*     */   public static final String KEY_ENCIPHERMENT = "key_encipherment";
/*     */   public static final String DATA_ENCIPHERMENT = "data_encipherment";
/*     */   public static final String KEY_AGREEMENT = "key_agreement";
/*     */   public static final String KEY_CERTSIGN = "key_certsign";
/*     */   public static final String CRL_SIGN = "crl_sign";
/*     */   public static final String ENCIPHER_ONLY = "encipher_only";
/*     */   public static final String DECIPHER_ONLY = "decipher_only";
/*     */   private boolean[] bitString;
/*     */   
/*     */   private void encodeThis() throws IOException {
/*  75 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  76 */     derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
/*  77 */     this.extensionValue = derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSet(int paramInt) {
/*  86 */     return (paramInt < this.bitString.length && this.bitString[paramInt]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(int paramInt, boolean paramBoolean) {
/*  95 */     if (paramInt >= this.bitString.length) {
/*  96 */       boolean[] arrayOfBoolean = new boolean[paramInt + 1];
/*  97 */       System.arraycopy(this.bitString, 0, arrayOfBoolean, 0, this.bitString.length);
/*  98 */       this.bitString = arrayOfBoolean;
/*     */     } 
/* 100 */     this.bitString[paramInt] = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyUsageExtension(byte[] paramArrayOfbyte) throws IOException {
/* 110 */     this
/* 111 */       .bitString = (new BitArray(paramArrayOfbyte.length * 8, paramArrayOfbyte)).toBooleanArray();
/* 112 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 113 */     this.critical = true;
/* 114 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyUsageExtension(boolean[] paramArrayOfboolean) throws IOException {
/* 124 */     this.bitString = paramArrayOfboolean;
/* 125 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 126 */     this.critical = true;
/* 127 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyUsageExtension(BitArray paramBitArray) throws IOException {
/* 137 */     this.bitString = paramBitArray.toBooleanArray();
/* 138 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 139 */     this.critical = true;
/* 140 */     encodeThis();
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
/*     */   public KeyUsageExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/* 155 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 156 */     this.critical = paramBoolean.booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     byte[] arrayOfByte = (byte[])paramObject;
/* 167 */     if (arrayOfByte[0] == 4) {
/* 168 */       this.extensionValue = (new DerValue(arrayOfByte)).getOctetString();
/*     */     } else {
/* 170 */       this.extensionValue = arrayOfByte;
/*     */     } 
/* 172 */     DerValue derValue = new DerValue(this.extensionValue);
/* 173 */     this.bitString = derValue.getUnalignedBitString().toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyUsageExtension() {
/* 180 */     this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 181 */     this.critical = true;
/* 182 */     this.bitString = new boolean[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 189 */     if (!(paramObject instanceof Boolean)) {
/* 190 */       throw new IOException("Attribute must be of type Boolean.");
/*     */     }
/* 192 */     boolean bool = ((Boolean)paramObject).booleanValue();
/* 193 */     if (paramString.equalsIgnoreCase("digital_signature")) {
/* 194 */       set(0, bool);
/* 195 */     } else if (paramString.equalsIgnoreCase("non_repudiation")) {
/* 196 */       set(1, bool);
/* 197 */     } else if (paramString.equalsIgnoreCase("key_encipherment")) {
/* 198 */       set(2, bool);
/* 199 */     } else if (paramString.equalsIgnoreCase("data_encipherment")) {
/* 200 */       set(3, bool);
/* 201 */     } else if (paramString.equalsIgnoreCase("key_agreement")) {
/* 202 */       set(4, bool);
/* 203 */     } else if (paramString.equalsIgnoreCase("key_certsign")) {
/* 204 */       set(5, bool);
/* 205 */     } else if (paramString.equalsIgnoreCase("crl_sign")) {
/* 206 */       set(6, bool);
/* 207 */     } else if (paramString.equalsIgnoreCase("encipher_only")) {
/* 208 */       set(7, bool);
/* 209 */     } else if (paramString.equalsIgnoreCase("decipher_only")) {
/* 210 */       set(8, bool);
/*     */     } else {
/* 212 */       throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
/*     */     } 
/*     */     
/* 215 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean get(String paramString) throws IOException {
/* 222 */     if (paramString.equalsIgnoreCase("digital_signature"))
/* 223 */       return Boolean.valueOf(isSet(0)); 
/* 224 */     if (paramString.equalsIgnoreCase("non_repudiation"))
/* 225 */       return Boolean.valueOf(isSet(1)); 
/* 226 */     if (paramString.equalsIgnoreCase("key_encipherment"))
/* 227 */       return Boolean.valueOf(isSet(2)); 
/* 228 */     if (paramString.equalsIgnoreCase("data_encipherment"))
/* 229 */       return Boolean.valueOf(isSet(3)); 
/* 230 */     if (paramString.equalsIgnoreCase("key_agreement"))
/* 231 */       return Boolean.valueOf(isSet(4)); 
/* 232 */     if (paramString.equalsIgnoreCase("key_certsign"))
/* 233 */       return Boolean.valueOf(isSet(5)); 
/* 234 */     if (paramString.equalsIgnoreCase("crl_sign"))
/* 235 */       return Boolean.valueOf(isSet(6)); 
/* 236 */     if (paramString.equalsIgnoreCase("encipher_only"))
/* 237 */       return Boolean.valueOf(isSet(7)); 
/* 238 */     if (paramString.equalsIgnoreCase("decipher_only")) {
/* 239 */       return Boolean.valueOf(isSet(8));
/*     */     }
/* 241 */     throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 250 */     if (paramString.equalsIgnoreCase("digital_signature")) {
/* 251 */       set(0, false);
/* 252 */     } else if (paramString.equalsIgnoreCase("non_repudiation")) {
/* 253 */       set(1, false);
/* 254 */     } else if (paramString.equalsIgnoreCase("key_encipherment")) {
/* 255 */       set(2, false);
/* 256 */     } else if (paramString.equalsIgnoreCase("data_encipherment")) {
/* 257 */       set(3, false);
/* 258 */     } else if (paramString.equalsIgnoreCase("key_agreement")) {
/* 259 */       set(4, false);
/* 260 */     } else if (paramString.equalsIgnoreCase("key_certsign")) {
/* 261 */       set(5, false);
/* 262 */     } else if (paramString.equalsIgnoreCase("crl_sign")) {
/* 263 */       set(6, false);
/* 264 */     } else if (paramString.equalsIgnoreCase("encipher_only")) {
/* 265 */       set(7, false);
/* 266 */     } else if (paramString.equalsIgnoreCase("decipher_only")) {
/* 267 */       set(8, false);
/*     */     } else {
/* 269 */       throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
/*     */     } 
/*     */     
/* 272 */     encodeThis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 279 */     StringBuilder stringBuilder = new StringBuilder();
/* 280 */     stringBuilder.append(super.toString());
/* 281 */     stringBuilder.append("KeyUsage [\n");
/*     */     
/* 283 */     if (isSet(0)) {
/* 284 */       stringBuilder.append("  DigitalSignature\n");
/*     */     }
/* 286 */     if (isSet(1)) {
/* 287 */       stringBuilder.append("  Non_repudiation\n");
/*     */     }
/* 289 */     if (isSet(2)) {
/* 290 */       stringBuilder.append("  Key_Encipherment\n");
/*     */     }
/* 292 */     if (isSet(3)) {
/* 293 */       stringBuilder.append("  Data_Encipherment\n");
/*     */     }
/* 295 */     if (isSet(4)) {
/* 296 */       stringBuilder.append("  Key_Agreement\n");
/*     */     }
/* 298 */     if (isSet(5)) {
/* 299 */       stringBuilder.append("  Key_CertSign\n");
/*     */     }
/* 301 */     if (isSet(6)) {
/* 302 */       stringBuilder.append("  Crl_Sign\n");
/*     */     }
/* 304 */     if (isSet(7)) {
/* 305 */       stringBuilder.append("  Encipher_Only\n");
/*     */     }
/* 307 */     if (isSet(8)) {
/* 308 */       stringBuilder.append("  Decipher_Only\n");
/*     */     }
/* 310 */     stringBuilder.append("]\n");
/*     */     
/* 312 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(OutputStream paramOutputStream) throws IOException {
/* 322 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 324 */     if (this.extensionValue == null) {
/* 325 */       this.extensionId = PKIXExtensions.KeyUsage_Id;
/* 326 */       this.critical = true;
/* 327 */       encodeThis();
/*     */     } 
/* 329 */     encode(derOutputStream);
/* 330 */     paramOutputStream.write(derOutputStream.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 338 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 339 */     attributeNameEnumeration.addElement("digital_signature");
/* 340 */     attributeNameEnumeration.addElement("non_repudiation");
/* 341 */     attributeNameEnumeration.addElement("key_encipherment");
/* 342 */     attributeNameEnumeration.addElement("data_encipherment");
/* 343 */     attributeNameEnumeration.addElement("key_agreement");
/* 344 */     attributeNameEnumeration.addElement("key_certsign");
/* 345 */     attributeNameEnumeration.addElement("crl_sign");
/* 346 */     attributeNameEnumeration.addElement("encipher_only");
/* 347 */     attributeNameEnumeration.addElement("decipher_only");
/*     */     
/* 349 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBits() {
/* 354 */     return (boolean[])this.bitString.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 361 */     return "KeyUsage";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\KeyUsageExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */