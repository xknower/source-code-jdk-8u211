/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.crypto.CksumType;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class Checksum
/*     */ {
/*     */   private int cksumType;
/*     */   private byte[] checksum;
/*     */   public static final int CKSUMTYPE_NULL = 0;
/*     */   public static final int CKSUMTYPE_CRC32 = 1;
/*     */   public static final int CKSUMTYPE_RSA_MD4 = 2;
/*     */   public static final int CKSUMTYPE_RSA_MD4_DES = 3;
/*     */   public static final int CKSUMTYPE_DES_MAC = 4;
/*     */   public static final int CKSUMTYPE_DES_MAC_K = 5;
/*     */   public static final int CKSUMTYPE_RSA_MD4_DES_K = 6;
/*     */   public static final int CKSUMTYPE_RSA_MD5 = 7;
/*     */   public static final int CKSUMTYPE_RSA_MD5_DES = 8;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_DES3_KD = 12;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_96_AES128 = 15;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_96_AES256 = 16;
/*     */   public static final int CKSUMTYPE_HMAC_MD5_ARCFOUR = -138;
/*     */   static int CKSUMTYPE_DEFAULT;
/*     */   static int SAFECKSUMTYPE_DEFAULT;
/*  75 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   static {
/*  77 */     initStatic();
/*     */   }
/*     */   
/*     */   public static void initStatic() {
/*  81 */     String str = null;
/*  82 */     Config config = null;
/*     */     try {
/*  84 */       config = Config.getInstance();
/*  85 */       str = config.get(new String[] { "libdefaults", "default_checksum" });
/*  86 */       if (str != null) {
/*     */         
/*  88 */         CKSUMTYPE_DEFAULT = Config.getType(str);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/*  96 */         CKSUMTYPE_DEFAULT = 7;
/*     */       } 
/*  98 */     } catch (Exception exception) {
/*  99 */       if (DEBUG) {
/* 100 */         System.out.println("Exception in getting default checksum value from the configuration Setting default checksum to be RSA-MD5");
/*     */ 
/*     */         
/* 103 */         exception.printStackTrace();
/*     */       } 
/* 105 */       CKSUMTYPE_DEFAULT = 7;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 110 */       str = config.get(new String[] { "libdefaults", "safe_checksum_type" });
/* 111 */       if (str != null) {
/*     */         
/* 113 */         SAFECKSUMTYPE_DEFAULT = Config.getType(str);
/*     */       } else {
/* 115 */         SAFECKSUMTYPE_DEFAULT = 8;
/*     */       } 
/* 117 */     } catch (Exception exception) {
/* 118 */       if (DEBUG) {
/* 119 */         System.out.println("Exception in getting safe default checksum value from the configuration Setting  safe default checksum to be RSA-MD5");
/*     */ 
/*     */ 
/*     */         
/* 123 */         exception.printStackTrace();
/*     */       } 
/* 125 */       SAFECKSUMTYPE_DEFAULT = 8;
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
/*     */   public Checksum(byte[] paramArrayOfbyte, int paramInt) {
/* 137 */     this.cksumType = paramInt;
/* 138 */     this.checksum = paramArrayOfbyte;
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
/*     */   public Checksum(int paramInt, byte[] paramArrayOfbyte) throws KdcErrException, KrbCryptoException {
/* 150 */     this.cksumType = paramInt;
/* 151 */     CksumType cksumType = CksumType.getInstance(this.cksumType);
/* 152 */     if (!cksumType.isSafe()) {
/* 153 */       this.checksum = cksumType.calculateChecksum(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */     } else {
/* 155 */       throw new KdcErrException(50);
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
/*     */   public Checksum(int paramInt1, byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, int paramInt2) throws KdcErrException, KrbApErrException, KrbCryptoException {
/* 169 */     this.cksumType = paramInt1;
/* 170 */     CksumType cksumType = CksumType.getInstance(this.cksumType);
/* 171 */     if (!cksumType.isSafe())
/* 172 */       throw new KrbApErrException(50); 
/* 173 */     this
/* 174 */       .checksum = cksumType.calculateKeyedChecksum(paramArrayOfbyte, paramArrayOfbyte.length, paramEncryptionKey
/*     */         
/* 176 */         .getBytes(), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, int paramInt) throws KdcErrException, KrbApErrException, KrbCryptoException {
/* 186 */     CksumType cksumType = CksumType.getInstance(this.cksumType);
/* 187 */     if (!cksumType.isSafe())
/* 188 */       throw new KrbApErrException(50); 
/* 189 */     return cksumType.verifyKeyedChecksum(paramArrayOfbyte, paramArrayOfbyte.length, paramEncryptionKey
/*     */         
/* 191 */         .getBytes(), this.checksum, paramInt);
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
/*     */   boolean isEqual(Checksum paramChecksum) throws KdcErrException {
/* 203 */     if (this.cksumType != paramChecksum.cksumType)
/* 204 */       return false; 
/* 205 */     CksumType cksumType = CksumType.getInstance(this.cksumType);
/* 206 */     return CksumType.isChecksumEqual(this.checksum, paramChecksum.checksum);
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
/*     */   private Checksum(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 219 */     if (paramDerValue.getTag() != 48) {
/* 220 */       throw new Asn1Exception(906);
/*     */     }
/* 222 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 223 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 224 */       this.cksumType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 227 */       throw new Asn1Exception(906);
/* 228 */     }  derValue = paramDerValue.getData().getDerValue();
/* 229 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 230 */       this.checksum = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/* 233 */       throw new Asn1Exception(906);
/* 234 */     }  if (paramDerValue.getData().available() > 0) {
/* 235 */       throw new Asn1Exception(906);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 261 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 262 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 263 */     derOutputStream2.putInteger(BigInteger.valueOf(this.cksumType));
/* 264 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */     
/* 266 */     derOutputStream2 = new DerOutputStream();
/* 267 */     derOutputStream2.putOctetString(this.checksum);
/* 268 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     
/* 270 */     derOutputStream2 = new DerOutputStream();
/* 271 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 272 */     return derOutputStream2.toByteArray();
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
/*     */   public static Checksum parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 296 */     if (paramBoolean && (
/* 297 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 298 */       return null;
/*     */     }
/* 300 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 301 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 302 */       throw new Asn1Exception(906);
/*     */     }
/* 304 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 305 */     return new Checksum(derValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getBytes() {
/* 313 */     return this.checksum;
/*     */   }
/*     */   
/*     */   public final int getType() {
/* 317 */     return this.cksumType;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 321 */     if (this == paramObject) {
/* 322 */       return true;
/*     */     }
/* 324 */     if (!(paramObject instanceof Checksum)) {
/* 325 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 329 */       return isEqual((Checksum)paramObject);
/* 330 */     } catch (KdcErrException kdcErrException) {
/* 331 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 336 */     int i = 17;
/* 337 */     i = 37 * i + this.cksumType;
/* 338 */     if (this.checksum != null) {
/* 339 */       i = 37 * i + Arrays.hashCode(this.checksum);
/*     */     }
/* 341 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\Checksum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */