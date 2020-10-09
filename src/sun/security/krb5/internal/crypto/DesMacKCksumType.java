/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.InvalidKeyException;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DesMacKCksumType
/*    */   extends CksumType
/*    */ {
/*    */   public int confounderSize() {
/* 44 */     return 0;
/*    */   }
/*    */   
/*    */   public int cksumType() {
/* 48 */     return 5;
/*    */   }
/*    */   
/*    */   public boolean isSafe() {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   public int cksumSize() {
/* 56 */     return 16;
/*    */   }
/*    */   
/*    */   public int keyType() {
/* 60 */     return 1;
/*    */   }
/*    */   
/*    */   public int keySize() {
/* 64 */     return 8;
/*    */   }
/*    */   
/*    */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) {
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*    */     try {
/* 84 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/* 85 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*    */       }
/* 87 */     } catch (InvalidKeyException invalidKeyException) {}
/*    */ 
/*    */     
/* 90 */     byte[] arrayOfByte = new byte[paramArrayOfbyte2.length];
/* 91 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, 0, paramArrayOfbyte2.length);
/* 92 */     return Des.des_cksum(arrayOfByte, paramArrayOfbyte1, paramArrayOfbyte2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 98 */     byte[] arrayOfByte = calculateKeyedChecksum(paramArrayOfbyte1, paramArrayOfbyte1.length, paramArrayOfbyte2, paramInt2);
/* 99 */     return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\DesMacKCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */