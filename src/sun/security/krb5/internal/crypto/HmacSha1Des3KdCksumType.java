/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HmacSha1Des3KdCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  41 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  45 */     return 12;
/*     */   }
/*     */   
/*     */   public boolean isSafe() {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  53 */     return 20;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  57 */     return 2;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  61 */     return 24;
/*     */   }
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) {
/*  65 */     return null;
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
/*     */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*     */     try {
/*  79 */       return Des3.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*  80 */     } catch (GeneralSecurityException generalSecurityException) {
/*  81 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  82 */       krbCryptoException.initCause(generalSecurityException);
/*  83 */       throw krbCryptoException;
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
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/*     */     try {
/*  99 */       byte[] arrayOfByte = Des3.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*     */ 
/*     */       
/* 102 */       return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/* 103 */     } catch (GeneralSecurityException generalSecurityException) {
/* 104 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 105 */       krbCryptoException.initCause(generalSecurityException);
/* 106 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\HmacSha1Des3KdCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */