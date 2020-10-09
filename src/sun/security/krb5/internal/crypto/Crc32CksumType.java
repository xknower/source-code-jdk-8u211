/*    */ package sun.security.krb5.internal.crypto;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Crc32CksumType
/*    */   extends CksumType
/*    */ {
/*    */   public int confounderSize() {
/* 43 */     return 0;
/*    */   }
/*    */   
/*    */   public int cksumType() {
/* 47 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean isSafe() {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public int cksumSize() {
/* 55 */     return 4;
/*    */   }
/*    */   
/*    */   public int keyType() {
/* 59 */     return 0;
/*    */   }
/*    */   
/*    */   public int keySize() {
/* 63 */     return 0;
/*    */   }
/*    */   
/*    */   public byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) {
/* 67 */     return crc32.byte2crc32sum_bytes(paramArrayOfbyte, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] calculateKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) {
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public static byte[] int2quad(long paramLong) {
/* 81 */     byte[] arrayOfByte = new byte[4];
/* 82 */     for (byte b = 0; b < 4; b++) {
/* 83 */       arrayOfByte[b] = (byte)(int)(paramLong >>> b * 8 & 0xFFL);
/*    */     }
/* 85 */     return arrayOfByte;
/*    */   }
/*    */   
/*    */   public static long bytes2long(byte[] paramArrayOfbyte) {
/* 89 */     long l = 0L;
/*    */     
/* 91 */     l |= (paramArrayOfbyte[0] & 0xFFL) << 24L;
/* 92 */     l |= (paramArrayOfbyte[1] & 0xFFL) << 16L;
/* 93 */     l |= (paramArrayOfbyte[2] & 0xFFL) << 8L;
/* 94 */     l |= paramArrayOfbyte[3] & 0xFFL;
/* 95 */     return l;
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\crypto\Crc32CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */