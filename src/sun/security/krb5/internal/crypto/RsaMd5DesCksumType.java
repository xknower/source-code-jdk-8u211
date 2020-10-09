/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import sun.security.krb5.Confounder;
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
/*     */ 
/*     */ 
/*     */ public final class RsaMd5DesCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  50 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  54 */     return 8;
/*     */   }
/*     */   
/*     */   public boolean isSafe() {
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  62 */     return 24;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  66 */     return 1;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  70 */     return 8;
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
/*     */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*  85 */     byte[] arrayOfByte1 = new byte[paramInt1 + confounderSize()];
/*  86 */     byte[] arrayOfByte2 = Confounder.bytes(confounderSize());
/*  87 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, confounderSize());
/*  88 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*  91 */     byte[] arrayOfByte3 = calculateChecksum(arrayOfByte1, arrayOfByte1.length);
/*  92 */     byte[] arrayOfByte4 = new byte[cksumSize()];
/*  93 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, confounderSize());
/*  94 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, confounderSize(), 
/*  95 */         cksumSize() - confounderSize());
/*     */ 
/*     */     
/*  98 */     byte[] arrayOfByte5 = new byte[keySize()];
/*  99 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte5, 0, paramArrayOfbyte2.length);
/* 100 */     for (byte b = 0; b < arrayOfByte5.length; b++) {
/* 101 */       arrayOfByte5[b] = (byte)(arrayOfByte5[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 104 */       if (DESKeySpec.isWeak(arrayOfByte5, 0)) {
/* 105 */         arrayOfByte5[7] = (byte)(arrayOfByte5[7] ^ 0xF0);
/*     */       }
/* 107 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 110 */     byte[] arrayOfByte6 = new byte[arrayOfByte5.length];
/*     */ 
/*     */     
/* 113 */     byte[] arrayOfByte7 = new byte[arrayOfByte4.length];
/* 114 */     Des.cbc_encrypt(arrayOfByte4, arrayOfByte7, arrayOfByte5, arrayOfByte6, true);
/* 115 */     return arrayOfByte7;
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
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 131 */     byte[] arrayOfByte1 = decryptKeyedChecksum(paramArrayOfbyte3, paramArrayOfbyte2);
/*     */ 
/*     */     
/* 134 */     byte[] arrayOfByte2 = new byte[paramInt1 + confounderSize()];
/* 135 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, confounderSize());
/* 136 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, confounderSize(), paramInt1);
/*     */     
/* 138 */     byte[] arrayOfByte3 = calculateChecksum(arrayOfByte2, arrayOfByte2.length);
/*     */     
/* 140 */     byte[] arrayOfByte4 = new byte[cksumSize() - confounderSize()];
/* 141 */     System.arraycopy(arrayOfByte1, confounderSize(), arrayOfByte4, 0, 
/* 142 */         cksumSize() - confounderSize());
/*     */     
/* 144 */     return isChecksumEqual(arrayOfByte4, arrayOfByte3);
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
/*     */   private byte[] decryptKeyedChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws KrbCryptoException {
/* 157 */     byte[] arrayOfByte1 = new byte[keySize()];
/* 158 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 0, paramArrayOfbyte2.length);
/* 159 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 160 */       arrayOfByte1[b] = (byte)(arrayOfByte1[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 163 */       if (DESKeySpec.isWeak(arrayOfByte1, 0)) {
/* 164 */         arrayOfByte1[7] = (byte)(arrayOfByte1[7] ^ 0xF0);
/*     */       }
/* 166 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 169 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
/*     */     
/* 171 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte1.length];
/* 172 */     Des.cbc_encrypt(paramArrayOfbyte1, arrayOfByte3, arrayOfByte1, arrayOfByte2, false);
/* 173 */     return arrayOfByte3;
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
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException {
/*     */     MessageDigest messageDigest;
/* 186 */     byte[] arrayOfByte = null;
/*     */     try {
/* 188 */       messageDigest = MessageDigest.getInstance("MD5");
/* 189 */     } catch (Exception exception) {
/* 190 */       throw new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/*     */     } 
/*     */     try {
/* 193 */       messageDigest.update(paramArrayOfbyte);
/* 194 */       arrayOfByte = messageDigest.digest();
/* 195 */     } catch (Exception exception) {
/* 196 */       throw new KrbCryptoException(exception.getMessage());
/*     */     } 
/* 198 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\RsaMd5DesCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */