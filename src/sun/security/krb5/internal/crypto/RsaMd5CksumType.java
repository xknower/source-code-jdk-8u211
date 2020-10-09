/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.MessageDigest;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RsaMd5CksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  45 */     return 0;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  49 */     return 7;
/*     */   }
/*     */   
/*     */   public boolean isSafe() {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  57 */     return 16;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  61 */     return 0;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 0;
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
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException {
/*     */     MessageDigest messageDigest;
/*  79 */     byte[] arrayOfByte = null;
/*     */     try {
/*  81 */       messageDigest = MessageDigest.getInstance("MD5");
/*  82 */     } catch (Exception exception) {
/*  83 */       throw new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/*     */     } 
/*     */     try {
/*  86 */       messageDigest.update(paramArrayOfbyte);
/*  87 */       arrayOfByte = messageDigest.digest();
/*  88 */     } catch (Exception exception) {
/*  89 */       throw new KrbCryptoException(exception.getMessage());
/*     */     } 
/*  91 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\RsaMd5CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */