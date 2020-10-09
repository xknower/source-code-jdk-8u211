/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CksumType
/*     */ {
/*  43 */   private static boolean DEBUG = Krb5.DEBUG; public static CksumType getInstance(int paramInt) throws KdcErrException { DesMacCksumType desMacCksumType; DesMacKCksumType desMacKCksumType; RsaMd5CksumType rsaMd5CksumType; RsaMd5DesCksumType rsaMd5DesCksumType; HmacSha1Des3KdCksumType hmacSha1Des3KdCksumType;
/*     */     HmacSha1Aes128CksumType hmacSha1Aes128CksumType;
/*     */     HmacSha1Aes256CksumType hmacSha1Aes256CksumType;
/*     */     HmacMd5ArcFourCksumType hmacMd5ArcFourCksumType;
/*  47 */     Crc32CksumType crc32CksumType = null;
/*  48 */     String str = null;
/*  49 */     switch (paramInt) {
/*     */       case 1:
/*  51 */         crc32CksumType = new Crc32CksumType();
/*  52 */         str = "sun.security.krb5.internal.crypto.Crc32CksumType";
/*     */         break;
/*     */       case 4:
/*  55 */         desMacCksumType = new DesMacCksumType();
/*  56 */         str = "sun.security.krb5.internal.crypto.DesMacCksumType";
/*     */         break;
/*     */       case 5:
/*  59 */         desMacKCksumType = new DesMacKCksumType();
/*  60 */         str = "sun.security.krb5.internal.crypto.DesMacKCksumType";
/*     */         break;
/*     */       
/*     */       case 7:
/*  64 */         rsaMd5CksumType = new RsaMd5CksumType();
/*  65 */         str = "sun.security.krb5.internal.crypto.RsaMd5CksumType";
/*     */         break;
/*     */       case 8:
/*  68 */         rsaMd5DesCksumType = new RsaMd5DesCksumType();
/*  69 */         str = "sun.security.krb5.internal.crypto.RsaMd5DesCksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 12:
/*  74 */         hmacSha1Des3KdCksumType = new HmacSha1Des3KdCksumType();
/*  75 */         str = "sun.security.krb5.internal.crypto.HmacSha1Des3KdCksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/*  80 */         hmacSha1Aes128CksumType = new HmacSha1Aes128CksumType();
/*  81 */         str = "sun.security.krb5.internal.crypto.HmacSha1Aes128CksumType";
/*     */         break;
/*     */       
/*     */       case 16:
/*  85 */         hmacSha1Aes256CksumType = new HmacSha1Aes256CksumType();
/*  86 */         str = "sun.security.krb5.internal.crypto.HmacSha1Aes256CksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case -138:
/*  91 */         hmacMd5ArcFourCksumType = new HmacMd5ArcFourCksumType();
/*  92 */         str = "sun.security.krb5.internal.crypto.HmacMd5ArcFourCksumType";
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 112 */         throw new KdcErrException(15);
/*     */     } 
/* 114 */     if (DEBUG) {
/* 115 */       System.out.println(">>> CksumType: " + str);
/*     */     }
/* 117 */     return hmacMd5ArcFourCksumType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CksumType getInstance() throws KdcErrException {
/* 126 */     int i = 7;
/*     */     try {
/* 128 */       Config config = Config.getInstance();
/* 129 */       if ((i = Config.getType(config.get(new String[] { "libdefaults", "ap_req_checksum_type" }))) == -1)
/*     */       {
/* 131 */         if ((i = Config.getType(config.get(new String[] { "libdefaults", "checksum_type" }))) == -1)
/*     */         {
/* 133 */           i = 7;
/*     */         }
/*     */       }
/* 136 */     } catch (KrbException krbException) {}
/*     */     
/* 138 */     return getInstance(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int confounderSize();
/*     */ 
/*     */   
/*     */   public abstract int cksumType();
/*     */ 
/*     */   
/*     */   public abstract boolean isSafe();
/*     */   
/*     */   public abstract int cksumSize();
/*     */   
/*     */   public abstract int keyType();
/*     */   
/*     */   public abstract int keySize();
/*     */   
/*     */   public abstract byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException;
/*     */   
/*     */   public abstract byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException;
/*     */   
/*     */   public abstract boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException;
/*     */   
/*     */   public static boolean isChecksumEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 163 */     if (paramArrayOfbyte1 == paramArrayOfbyte2)
/* 164 */       return true; 
/* 165 */     if ((paramArrayOfbyte1 == null && paramArrayOfbyte2 != null) || (paramArrayOfbyte1 != null && paramArrayOfbyte2 == null))
/*     */     {
/* 167 */       return false; } 
/* 168 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length)
/* 169 */       return false; 
/* 170 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 171 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 172 */         return false; 
/* 173 */     }  return true;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */