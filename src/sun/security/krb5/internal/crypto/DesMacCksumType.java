/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.InvalidKeyException;
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
/*     */ public class DesMacCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  45 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  49 */     return 4;
/*     */   }
/*     */   
/*     */   public boolean isSafe() {
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  57 */     return 16;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  61 */     return 1;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 8;
/*     */   }
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) {
/*  69 */     return null;
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
/*     */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*  83 */     byte[] arrayOfByte1 = new byte[paramInt1 + confounderSize()];
/*  84 */     byte[] arrayOfByte2 = Confounder.bytes(confounderSize());
/*  85 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, confounderSize());
/*  86 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*     */     try {
/*  90 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/*  91 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*     */       }
/*  93 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/*  96 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte2.length];
/*  97 */     byte[] arrayOfByte4 = Des.des_cksum(arrayOfByte3, arrayOfByte1, paramArrayOfbyte2);
/*  98 */     byte[] arrayOfByte5 = new byte[cksumSize()];
/*  99 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte5, 0, confounderSize());
/* 100 */     System.arraycopy(arrayOfByte4, 0, arrayOfByte5, confounderSize(), 
/* 101 */         cksumSize() - confounderSize());
/*     */     
/* 103 */     byte[] arrayOfByte6 = new byte[keySize()];
/* 104 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte6, 0, paramArrayOfbyte2.length);
/* 105 */     for (byte b = 0; b < arrayOfByte6.length; b++) {
/* 106 */       arrayOfByte6[b] = (byte)(arrayOfByte6[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 109 */       if (DESKeySpec.isWeak(arrayOfByte6, 0)) {
/* 110 */         arrayOfByte6[7] = (byte)(arrayOfByte6[7] ^ 0xF0);
/*     */       }
/* 112 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 115 */     byte[] arrayOfByte7 = new byte[arrayOfByte6.length];
/*     */ 
/*     */     
/* 118 */     byte[] arrayOfByte8 = new byte[arrayOfByte5.length];
/* 119 */     Des.cbc_encrypt(arrayOfByte5, arrayOfByte8, arrayOfByte6, arrayOfByte7, true);
/* 120 */     return arrayOfByte8;
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
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 135 */     byte[] arrayOfByte1 = decryptKeyedChecksum(paramArrayOfbyte3, paramArrayOfbyte2);
/*     */     
/* 137 */     byte[] arrayOfByte2 = new byte[paramInt1 + confounderSize()];
/* 138 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, confounderSize());
/* 139 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*     */     try {
/* 143 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/* 144 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*     */       }
/* 146 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 149 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte2.length];
/* 150 */     byte[] arrayOfByte4 = Des.des_cksum(arrayOfByte3, arrayOfByte2, paramArrayOfbyte2);
/* 151 */     byte[] arrayOfByte5 = new byte[cksumSize() - confounderSize()];
/* 152 */     System.arraycopy(arrayOfByte1, confounderSize(), arrayOfByte5, 0, 
/* 153 */         cksumSize() - confounderSize());
/* 154 */     return isChecksumEqual(arrayOfByte5, arrayOfByte4);
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
/*     */   private byte[] decryptKeyedChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws KrbCryptoException {
/* 166 */     byte[] arrayOfByte1 = new byte[keySize()];
/* 167 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 0, paramArrayOfbyte2.length);
/* 168 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 169 */       arrayOfByte1[b] = (byte)(arrayOfByte1[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 172 */       if (DESKeySpec.isWeak(arrayOfByte1, 0)) {
/* 173 */         arrayOfByte1[7] = (byte)(arrayOfByte1[7] ^ 0xF0);
/*     */       }
/* 175 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 178 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
/* 179 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte1.length];
/* 180 */     Des.cbc_encrypt(paramArrayOfbyte1, arrayOfByte3, arrayOfByte1, arrayOfByte2, false);
/* 181 */     return arrayOfByte3;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\DesMacCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */