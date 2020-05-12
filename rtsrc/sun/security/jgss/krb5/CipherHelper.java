/*      */ package sun.security.jgss.krb5;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.CipherInputStream;
/*      */ import javax.crypto.CipherOutputStream;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.internal.crypto.Aes128;
/*      */ import sun.security.krb5.internal.crypto.Aes256;
/*      */ import sun.security.krb5.internal.crypto.ArcFourHmac;
/*      */ import sun.security.krb5.internal.crypto.Des3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CipherHelper
/*      */ {
/*      */   private static final int KG_USAGE_SEAL = 22;
/*      */   private static final int KG_USAGE_SIGN = 23;
/*      */   private static final int KG_USAGE_SEQ = 24;
/*      */   private static final int DES_CHECKSUM_SIZE = 8;
/*      */   private static final int DES_IV_SIZE = 8;
/*      */   private static final int AES_IV_SIZE = 16;
/*      */   private static final int HMAC_CHECKSUM_SIZE = 8;
/*      */   private static final int KG_USAGE_SIGN_MS = 15;
/*   67 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   private static final byte[] ZERO_IV = new byte[8];
/*   74 */   private static final byte[] ZERO_IV_AES = new byte[16];
/*      */   
/*      */   private int etype;
/*      */   
/*      */   private int sgnAlg;
/*      */   
/*      */   private int sealAlg;
/*      */   private byte[] keybytes;
/*   82 */   private int proto = 0;
/*      */   
/*      */   CipherHelper(EncryptionKey paramEncryptionKey) throws GSSException {
/*   85 */     this.etype = paramEncryptionKey.getEType();
/*   86 */     this.keybytes = paramEncryptionKey.getBytes();
/*      */     
/*   88 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*   91 */         this.sgnAlg = 0;
/*   92 */         this.sealAlg = 0;
/*      */         return;
/*      */       
/*      */       case 16:
/*   96 */         this.sgnAlg = 1024;
/*   97 */         this.sealAlg = 512;
/*      */         return;
/*      */       
/*      */       case 23:
/*  101 */         this.sgnAlg = 4352;
/*  102 */         this.sealAlg = 4096;
/*      */         return;
/*      */       
/*      */       case 17:
/*      */       case 18:
/*  107 */         this.sgnAlg = -1;
/*  108 */         this.sealAlg = -1;
/*  109 */         this.proto = 1;
/*      */         return;
/*      */     } 
/*      */     
/*  113 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getSgnAlg() {
/*  119 */     return this.sgnAlg;
/*      */   }
/*      */   
/*      */   int getSealAlg() {
/*  123 */     return this.sealAlg;
/*      */   }
/*      */   
/*      */   int getProto() {
/*  127 */     return this.proto;
/*      */   }
/*      */   
/*      */   int getEType() {
/*  131 */     return this.etype;
/*      */   }
/*      */   
/*      */   boolean isArcFour() {
/*  135 */     boolean bool = false;
/*  136 */     if (this.etype == 23) {
/*  137 */       bool = true;
/*      */     }
/*  139 */     return bool; } byte[] calculateChecksum(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, int paramInt4) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     boolean bool1;
/*      */     int i;
/*      */     byte[] arrayOfByte2;
/*      */     boolean bool2;
/*      */     int j;
/*  146 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  153 */           MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  159 */           messageDigest.update(paramArrayOfbyte1);
/*      */ 
/*      */           
/*  162 */           messageDigest.update(paramArrayOfbyte3, paramInt2, paramInt3);
/*      */           
/*  164 */           if (paramArrayOfbyte2 != null)
/*      */           {
/*      */ 
/*      */             
/*  168 */             messageDigest.update(paramArrayOfbyte2);
/*      */           }
/*      */ 
/*      */           
/*  172 */           paramArrayOfbyte3 = messageDigest.digest();
/*  173 */           paramInt2 = 0;
/*  174 */           paramInt3 = paramArrayOfbyte3.length;
/*      */ 
/*      */           
/*  177 */           paramArrayOfbyte1 = null;
/*  178 */           paramArrayOfbyte2 = null;
/*  179 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*      */           
/*  181 */           GSSException gSSException = new GSSException(11, -1, "Could not get MD5 Message Digest - " + noSuchAlgorithmException.getMessage());
/*  182 */           gSSException.initCause(noSuchAlgorithmException);
/*  183 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 512:
/*  188 */         return getDesCbcChecksum(this.keybytes, paramArrayOfbyte1, paramArrayOfbyte3, paramInt2, paramInt3);
/*      */ 
/*      */ 
/*      */       
/*      */       case 1024:
/*  193 */         if (paramArrayOfbyte1 == null && paramArrayOfbyte2 == null) {
/*  194 */           arrayOfByte1 = paramArrayOfbyte3;
/*  195 */           i = paramInt3;
/*  196 */           bool1 = paramInt2;
/*      */         } else {
/*  198 */           i = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt3 + ((paramArrayOfbyte2 != null) ? paramArrayOfbyte2.length : 0);
/*      */ 
/*      */           
/*  201 */           arrayOfByte1 = new byte[i];
/*  202 */           int k = 0;
/*  203 */           if (paramArrayOfbyte1 != null) {
/*  204 */             System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, paramArrayOfbyte1.length);
/*  205 */             k = paramArrayOfbyte1.length;
/*      */           } 
/*  207 */           System.arraycopy(paramArrayOfbyte3, paramInt2, arrayOfByte1, k, paramInt3);
/*  208 */           k += paramInt3;
/*  209 */           if (paramArrayOfbyte2 != null) {
/*  210 */             System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, k, paramArrayOfbyte2.length);
/*      */           }
/*      */           
/*  213 */           bool1 = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  231 */           arrayOfByte2 = Des3.calculateChecksum(this.keybytes, 23, arrayOfByte1, bool1, i);
/*      */ 
/*      */ 
/*      */           
/*  235 */           return arrayOfByte2;
/*  236 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  239 */           GSSException gSSException = new GSSException(11, -1, "Could not use HMAC-SHA1-DES3-KD signing algorithm - " + generalSecurityException.getMessage());
/*  240 */           gSSException.initCause(generalSecurityException);
/*  241 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  247 */         if (paramArrayOfbyte1 == null && paramArrayOfbyte2 == null) {
/*  248 */           arrayOfByte2 = paramArrayOfbyte3;
/*  249 */           j = paramInt3;
/*  250 */           bool2 = paramInt2;
/*      */         } else {
/*  252 */           j = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt3 + ((paramArrayOfbyte2 != null) ? paramArrayOfbyte2.length : 0);
/*      */ 
/*      */           
/*  255 */           arrayOfByte2 = new byte[j];
/*  256 */           int k = 0;
/*      */           
/*  258 */           if (paramArrayOfbyte1 != null) {
/*  259 */             System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, paramArrayOfbyte1.length);
/*  260 */             k = paramArrayOfbyte1.length;
/*      */           } 
/*  262 */           System.arraycopy(paramArrayOfbyte3, paramInt2, arrayOfByte2, k, paramInt3);
/*  263 */           k += paramInt3;
/*  264 */           if (paramArrayOfbyte2 != null) {
/*  265 */             System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte2, k, paramArrayOfbyte2.length);
/*      */           }
/*      */           
/*  268 */           bool2 = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  290 */           byte b = 23;
/*  291 */           if (paramInt4 == 257) {
/*  292 */             b = 15;
/*      */           }
/*  294 */           byte[] arrayOfByte3 = ArcFourHmac.calculateChecksum(this.keybytes, b, arrayOfByte2, bool2, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  300 */           byte[] arrayOfByte4 = new byte[getChecksumLength()];
/*  301 */           System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, arrayOfByte4.length);
/*      */ 
/*      */           
/*  304 */           return arrayOfByte4;
/*  305 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  308 */           GSSException gSSException = new GSSException(11, -1, "Could not use HMAC_MD5_ARCFOUR signing algorithm - " + generalSecurityException.getMessage());
/*  309 */           gSSException.initCause(generalSecurityException);
/*  310 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  314 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] calculateChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/*  324 */     int i = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt2;
/*      */ 
/*      */     
/*  327 */     byte[] arrayOfByte = new byte[i];
/*      */ 
/*      */     
/*  330 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte, 0, paramInt2);
/*      */ 
/*      */     
/*  333 */     if (paramArrayOfbyte1 != null) {
/*  334 */       System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, paramInt2, paramArrayOfbyte1.length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  339 */     switch (this.etype) {
/*      */       case 17:
/*      */         try {
/*  342 */           return Aes128.calculateChecksum(this.keybytes, paramInt3, arrayOfByte, 0, i);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  347 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  350 */           GSSException gSSException = new GSSException(11, -1, "Could not use AES128 signing algorithm - " + generalSecurityException.getMessage());
/*  351 */           gSSException.initCause(generalSecurityException);
/*  352 */           throw gSSException;
/*      */         } 
/*      */       
/*      */       case 18:
/*      */         try {
/*  357 */           return Aes256.calculateChecksum(this.keybytes, paramInt3, arrayOfByte, 0, i);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  362 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  365 */           GSSException gSSException = new GSSException(11, -1, "Could not use AES256 signing algorithm - " + generalSecurityException.getMessage());
/*  366 */           gSSException.initCause(generalSecurityException);
/*  367 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  371 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] encryptSeq(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  379 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  383 */           Cipher cipher = getInitializedDes(true, this.keybytes, paramArrayOfbyte1);
/*  384 */           return cipher.doFinal(paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  386 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  389 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using DES - " + generalSecurityException.getMessage());
/*  390 */           gSSException.initCause(generalSecurityException);
/*  391 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  396 */         if (paramArrayOfbyte1.length == 8) {
/*  397 */           arrayOfByte1 = paramArrayOfbyte1;
/*      */         } else {
/*  399 */           arrayOfByte1 = new byte[8];
/*  400 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, 8);
/*      */         } 
/*      */         try {
/*  403 */           return Des3.encryptRaw(this.keybytes, 24, arrayOfByte1, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  405 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  409 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using DES3-KD - " + exception.getMessage());
/*  410 */           gSSException.initCause(exception);
/*  411 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  417 */         if (paramArrayOfbyte1.length == 8) {
/*  418 */           arrayOfByte2 = paramArrayOfbyte1;
/*      */         } else {
/*  420 */           arrayOfByte2 = new byte[8];
/*  421 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  425 */           return ArcFourHmac.encryptSeq(this.keybytes, 24, arrayOfByte2, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  427 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  431 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using RC4-HMAC - " + exception.getMessage());
/*  432 */           gSSException.initCause(exception);
/*  433 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  437 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] decryptSeq(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  445 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  449 */           Cipher cipher = getInitializedDes(false, this.keybytes, paramArrayOfbyte1);
/*  450 */           return cipher.doFinal(paramArrayOfbyte2, paramInt1, paramInt2);
/*  451 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  454 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using DES - " + generalSecurityException.getMessage());
/*  455 */           gSSException.initCause(generalSecurityException);
/*  456 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  461 */         if (paramArrayOfbyte1.length == 8) {
/*  462 */           arrayOfByte1 = paramArrayOfbyte1;
/*      */         } else {
/*  464 */           arrayOfByte1 = new byte[8];
/*  465 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  469 */           return Des3.decryptRaw(this.keybytes, 24, arrayOfByte1, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  471 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  475 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using DES3-KD - " + exception.getMessage());
/*  476 */           gSSException.initCause(exception);
/*  477 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  483 */         if (paramArrayOfbyte1.length == 8) {
/*  484 */           arrayOfByte2 = paramArrayOfbyte1;
/*      */         } else {
/*  486 */           arrayOfByte2 = new byte[8];
/*  487 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  491 */           return ArcFourHmac.decryptSeq(this.keybytes, 24, arrayOfByte2, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  493 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  497 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using RC4-HMAC - " + exception.getMessage());
/*  498 */           gSSException.initCause(exception);
/*  499 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  503 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getChecksumLength() throws GSSException {
/*  509 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*  512 */         return 8;
/*      */       
/*      */       case 16:
/*  515 */         return Des3.getChecksumLength();
/*      */       
/*      */       case 17:
/*  518 */         return Aes128.getChecksumLength();
/*      */       case 18:
/*  520 */         return Aes256.getChecksumLength();
/*      */ 
/*      */       
/*      */       case 23:
/*  524 */         return 8;
/*      */     } 
/*      */     
/*  527 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/*  540 */     switch (this.sealAlg) {
/*      */       case 0:
/*  542 */         desCbcDecrypt(paramWrapToken, getDesEncryptionKey(this.keybytes), paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 512:
/*  547 */         des3KdDecrypt(paramWrapToken, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  551 */         arcFourDecrypt(paramWrapToken, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */     } 
/*      */     
/*  555 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/*  570 */     switch (this.etype) {
/*      */       case 17:
/*  572 */         aes128Decrypt(paramWrapToken_v2, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/*      */         return;
/*      */       
/*      */       case 18:
/*  576 */         aes256Decrypt(paramWrapToken_v2, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/*      */         return;
/*      */     } 
/*      */     
/*  580 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken paramWrapToken, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws GSSException, IOException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  589 */     switch (this.sealAlg) {
/*      */       case 0:
/*  591 */         desCbcDecrypt(paramWrapToken, getDesEncryptionKey(this.keybytes), paramInputStream, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 512:
/*  598 */         arrayOfByte1 = new byte[paramInt1];
/*      */         try {
/*  600 */           Krb5Token.readFully(paramInputStream, arrayOfByte1, 0, paramInt1);
/*  601 */         } catch (IOException iOException) {
/*  602 */           GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  605 */           gSSException.initCause(iOException);
/*  606 */           throw gSSException;
/*      */         } 
/*      */         
/*  609 */         des3KdDecrypt(paramWrapToken, arrayOfByte1, 0, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4096:
/*  615 */         arrayOfByte2 = new byte[paramInt1];
/*      */         try {
/*  617 */           Krb5Token.readFully(paramInputStream, arrayOfByte2, 0, paramInt1);
/*  618 */         } catch (IOException iOException) {
/*  619 */           GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  622 */           gSSException.initCause(iOException);
/*  623 */           throw gSSException;
/*      */         } 
/*      */         
/*  626 */         arcFourDecrypt(paramWrapToken, arrayOfByte2, 0, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */     } 
/*      */     
/*  630 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 paramWrapToken_v2, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws GSSException, IOException {
/*  640 */     byte[] arrayOfByte = new byte[paramInt1];
/*      */     try {
/*  642 */       Krb5Token.readFully(paramInputStream, arrayOfByte, 0, paramInt1);
/*  643 */     } catch (IOException iOException) {
/*  644 */       GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */       
/*  647 */       gSSException.initCause(iOException);
/*  648 */       throw gSSException;
/*      */     } 
/*  650 */     switch (this.etype) {
/*      */       case 17:
/*  652 */         aes128Decrypt(paramWrapToken_v2, arrayOfByte, 0, paramInt1, paramArrayOfbyte, paramInt2, paramInt3);
/*      */         return;
/*      */       
/*      */       case 18:
/*  656 */         aes256Decrypt(paramWrapToken_v2, arrayOfByte, 0, paramInt1, paramArrayOfbyte, paramInt2, paramInt3);
/*      */         return;
/*      */     } 
/*      */     
/*  660 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, OutputStream paramOutputStream) throws GSSException, IOException {
/*      */     Cipher cipher;
/*      */     CipherOutputStream cipherOutputStream;
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  669 */     switch (this.sealAlg) {
/*      */       
/*      */       case 0:
/*  672 */         cipher = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */         
/*  674 */         cipherOutputStream = new CipherOutputStream(paramOutputStream, cipher);
/*      */         
/*  676 */         cipherOutputStream.write(paramArrayOfbyte1);
/*      */         
/*  678 */         cipherOutputStream.write(paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         
/*  680 */         cipherOutputStream.write(paramArrayOfbyte3);
/*      */         return;
/*      */       
/*      */       case 512:
/*  684 */         arrayOfByte1 = des3KdEncrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */ 
/*      */ 
/*      */         
/*  688 */         paramOutputStream.write(arrayOfByte1);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  692 */         arrayOfByte2 = arcFourEncrypt(paramWrapToken, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */ 
/*      */ 
/*      */         
/*  696 */         paramOutputStream.write(arrayOfByte2);
/*      */         return;
/*      */     } 
/*      */     
/*  700 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] encryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/*  718 */     switch (this.etype) {
/*      */       case 17:
/*  720 */         return aes128Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt3);
/*      */       
/*      */       case 18:
/*  723 */         return aes256Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt3);
/*      */     } 
/*      */     
/*  726 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt3) throws GSSException {
/*      */     int i;
/*      */     Cipher cipher;
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  735 */     switch (this.sealAlg) {
/*      */       case 0:
/*  737 */         i = paramInt3;
/*      */         
/*  739 */         cipher = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */ 
/*      */         
/*      */         try {
/*  743 */           i += cipher.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length, paramArrayOfbyte4, i);
/*      */ 
/*      */           
/*  746 */           i += cipher.update(paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte4, i);
/*      */ 
/*      */           
/*  749 */           cipher.update(paramArrayOfbyte3, 0, paramArrayOfbyte3.length, paramArrayOfbyte4, i);
/*      */           
/*  751 */           cipher.doFinal();
/*  752 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */           
/*  754 */           GSSException gSSException = new GSSException(11, -1, "Could not use DES Cipher - " + generalSecurityException.getMessage());
/*  755 */           gSSException.initCause(generalSecurityException);
/*  756 */           throw gSSException;
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 512:
/*  761 */         arrayOfByte1 = des3KdEncrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */         
/*  763 */         System.arraycopy(arrayOfByte1, 0, paramArrayOfbyte4, paramInt3, arrayOfByte1.length);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  767 */         arrayOfByte2 = arcFourEncrypt(paramWrapToken, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */         
/*  769 */         System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte4, paramInt3, arrayOfByte2.length);
/*      */         return;
/*      */     } 
/*      */     
/*  773 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int encryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, byte[] paramArrayOfbyte4, int paramInt3, int paramInt4) throws GSSException {
/*  791 */     byte[] arrayOfByte = null;
/*  792 */     switch (this.etype) {
/*      */       case 17:
/*  794 */         arrayOfByte = aes128Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  805 */         System.arraycopy(arrayOfByte, 0, paramArrayOfbyte4, paramInt3, arrayOfByte.length);
/*  806 */         return arrayOfByte.length;case 18: arrayOfByte = aes256Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt4); System.arraycopy(arrayOfByte, 0, paramArrayOfbyte4, paramInt3, arrayOfByte.length); return arrayOfByte.length;
/*      */     } 
/*      */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getDesCbcChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2) throws GSSException {
/*  830 */     Cipher cipher = getInitializedDes(true, paramArrayOfbyte1, ZERO_IV);
/*      */     
/*  832 */     int i = cipher.getBlockSize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  840 */     byte[] arrayOfByte = new byte[i];
/*      */     
/*  842 */     int j = paramInt2 / i;
/*  843 */     int k = paramInt2 % i;
/*  844 */     if (k == 0) {
/*      */       
/*  846 */       j--;
/*  847 */       System.arraycopy(paramArrayOfbyte3, paramInt1 + j * i, arrayOfByte, 0, i);
/*      */     } else {
/*      */       
/*  850 */       System.arraycopy(paramArrayOfbyte3, paramInt1 + j * i, arrayOfByte, 0, k);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  856 */       byte[] arrayOfByte1 = new byte[Math.max(i, (paramArrayOfbyte2 == null) ? i : paramArrayOfbyte2.length)];
/*      */ 
/*      */       
/*  859 */       if (paramArrayOfbyte2 != null)
/*      */       {
/*  861 */         cipher.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length, arrayOfByte1, 0);
/*      */       }
/*      */ 
/*      */       
/*  865 */       for (byte b = 0; b < j; b++) {
/*  866 */         cipher.update(paramArrayOfbyte3, paramInt1, i, arrayOfByte1, 0);
/*      */         
/*  868 */         paramInt1 += i;
/*      */       } 
/*      */ 
/*      */       
/*  872 */       byte[] arrayOfByte2 = new byte[i];
/*  873 */       cipher.update(arrayOfByte, 0, i, arrayOfByte2, 0);
/*  874 */       cipher.doFinal();
/*      */       
/*  876 */       return arrayOfByte2;
/*  877 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/*  879 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES Cipher - " + generalSecurityException.getMessage());
/*  880 */       gSSException.initCause(generalSecurityException);
/*  881 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Cipher getInitializedDes(boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GSSException {
/*      */     try {
/*  899 */       IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2);
/*  900 */       SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "DES");
/*      */       
/*  902 */       Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
/*  903 */       cipher.init(paramBoolean ? 1 : 2, secretKeySpec, ivParameterSpec);
/*      */ 
/*      */       
/*  906 */       return cipher;
/*  907 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/*  909 */       GSSException gSSException = new GSSException(11, -1, generalSecurityException.getMessage());
/*  910 */       gSSException.initCause(generalSecurityException);
/*  911 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, int paramInt3) throws GSSException {
/*      */     try {
/*  937 */       int i = 0;
/*      */       
/*  939 */       Cipher cipher = getInitializedDes(false, paramArrayOfbyte1, ZERO_IV);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  945 */       i = cipher.update(paramArrayOfbyte2, paramInt1, 8, paramWrapToken.confounder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  951 */       paramInt1 += 8;
/*  952 */       paramInt2 -= 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  961 */       int j = cipher.getBlockSize();
/*  962 */       int k = paramInt2 / j - 1;
/*      */ 
/*      */       
/*  965 */       for (byte b = 0; b < k; b++) {
/*  966 */         i = cipher.update(paramArrayOfbyte2, paramInt1, j, paramArrayOfbyte3, paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  972 */         paramInt1 += j;
/*  973 */         paramInt3 += j;
/*      */       } 
/*      */ 
/*      */       
/*  977 */       byte[] arrayOfByte = new byte[j];
/*  978 */       cipher.update(paramArrayOfbyte2, paramInt1, j, arrayOfByte);
/*      */       
/*  980 */       cipher.doFinal();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  987 */       byte b1 = arrayOfByte[j - 1];
/*  988 */       if (b1 < 1 || b1 > 8) {
/*  989 */         throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */       }
/*  991 */       paramWrapToken.padding = WrapToken.pads[b1];
/*  992 */       j -= b1;
/*      */ 
/*      */       
/*  995 */       System.arraycopy(arrayOfByte, 0, paramArrayOfbyte3, paramInt3, j);
/*      */     
/*      */     }
/*  998 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1000 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES cipher - " + generalSecurityException.getMessage());
/* 1001 */       gSSException.initCause(generalSecurityException);
/* 1002 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws GSSException, IOException {
/* 1026 */     int i = 0;
/*      */     
/* 1028 */     Cipher cipher = getInitializedDes(false, paramArrayOfbyte1, ZERO_IV);
/*      */     
/* 1030 */     WrapTokenInputStream wrapTokenInputStream = new WrapTokenInputStream(paramInputStream, paramInt1);
/*      */     
/* 1032 */     CipherInputStream cipherInputStream = new CipherInputStream(wrapTokenInputStream, cipher);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1038 */     i = cipherInputStream.read(paramWrapToken.confounder);
/*      */     
/* 1040 */     paramInt1 -= i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1054 */     int j = cipher.getBlockSize();
/* 1055 */     int k = paramInt1 / j - 1;
/*      */ 
/*      */     
/* 1058 */     for (byte b = 0; b < k; b++) {
/*      */       
/* 1060 */       i = cipherInputStream.read(paramArrayOfbyte2, paramInt2, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1067 */       paramInt2 += j;
/*      */     } 
/*      */ 
/*      */     
/* 1071 */     byte[] arrayOfByte = new byte[j];
/*      */     
/* 1073 */     i = cipherInputStream.read(arrayOfByte);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1083 */       cipher.doFinal();
/* 1084 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1086 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES cipher - " + generalSecurityException.getMessage());
/* 1087 */       gSSException.initCause(generalSecurityException);
/* 1088 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     byte b1 = arrayOfByte[j - 1];
/* 1097 */     if (b1 < 1 || b1 > 8) {
/* 1098 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/* 1100 */     paramWrapToken.padding = WrapToken.pads[b1];
/* 1101 */     j -= b1;
/*      */ 
/*      */     
/* 1104 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte2, paramInt2, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] getDesEncryptionKey(byte[] paramArrayOfbyte) throws GSSException {
/* 1120 */     if (paramArrayOfbyte.length > 8) {
/* 1121 */       throw new GSSException(11, -100, "Invalid DES Key!");
/*      */     }
/*      */     
/* 1124 */     byte[] arrayOfByte = new byte[paramArrayOfbyte.length];
/* 1125 */     for (byte b = 0; b < paramArrayOfbyte.length; b++)
/* 1126 */       arrayOfByte[b] = (byte)(paramArrayOfbyte[b] ^ 0xF0); 
/* 1127 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void des3KdDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1136 */       arrayOfByte = Des3.decryptRaw(this.keybytes, 22, ZERO_IV, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1138 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1140 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + generalSecurityException.getMessage());
/* 1141 */       gSSException.initCause(generalSecurityException);
/* 1142 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     byte b = arrayOfByte[arrayOfByte.length - 1];
/* 1158 */     if (b < 1 || b > 8) {
/* 1159 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1162 */     paramWrapToken.padding = WrapToken.pads[b];
/* 1163 */     int i = arrayOfByte.length - 8 - b;
/*      */     
/* 1165 */     System.arraycopy(arrayOfByte, 8, paramArrayOfbyte2, paramInt3, i);
/*      */ 
/*      */ 
/*      */     
/* 1169 */     System.arraycopy(arrayOfByte, 0, paramWrapToken.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] des3KdEncrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3) throws GSSException {
/* 1178 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte3.length];
/* 1179 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1180 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1181 */     System.arraycopy(paramArrayOfbyte3, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte3.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1188 */       return Des3.encryptRaw(this.keybytes, 22, ZERO_IV, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1193 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1196 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + exception.getMessage());
/* 1197 */       gSSException.initCause(exception);
/* 1198 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void arcFourDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/* 1209 */     byte[] arrayOfByte2, arrayOfByte1 = decryptSeq(paramWrapToken.getChecksum(), paramWrapToken
/* 1210 */         .getEncSeqNumber(), 0, 8);
/*      */ 
/*      */     
/*      */     try {
/* 1214 */       arrayOfByte2 = ArcFourHmac.decryptRaw(this.keybytes, 22, ZERO_IV, paramArrayOfbyte1, paramInt1, paramInt2, arrayOfByte1);
/*      */     }
/* 1216 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1218 */       GSSException gSSException = new GSSException(11, -1, "Could not use ArcFour Cipher - " + generalSecurityException.getMessage());
/* 1219 */       gSSException.initCause(generalSecurityException);
/* 1220 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     byte b = arrayOfByte2[arrayOfByte2.length - 1];
/* 1236 */     if (b < 1) {
/* 1237 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1240 */     paramWrapToken.padding = WrapToken.pads[b];
/* 1241 */     int i = arrayOfByte2.length - 8 - b;
/*      */     
/* 1243 */     System.arraycopy(arrayOfByte2, 8, paramArrayOfbyte2, paramInt3, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1250 */     System.arraycopy(arrayOfByte2, 0, paramWrapToken.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] arcFourEncrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3) throws GSSException {
/* 1259 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte3.length];
/* 1260 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, paramArrayOfbyte1.length);
/* 1261 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte1, paramArrayOfbyte1.length, paramInt2);
/* 1262 */     System.arraycopy(paramArrayOfbyte3, 0, arrayOfByte1, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte3.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1268 */     byte[] arrayOfByte2 = new byte[4];
/* 1269 */     WrapToken.writeBigEndian(paramWrapToken.getSequenceNumber(), arrayOfByte2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1275 */       return ArcFourHmac.encryptRaw(this.keybytes, 22, arrayOfByte2, arrayOfByte1, 0, arrayOfByte1.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1280 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1283 */       GSSException gSSException = new GSSException(11, -1, "Could not use ArcFour Cipher - " + exception.getMessage());
/* 1284 */       gSSException.initCause(exception);
/* 1285 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes128Encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 1299 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte2.length];
/* 1300 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1301 */     System.arraycopy(paramArrayOfbyte3, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1302 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte2.length);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1307 */       return Aes128.encryptRaw(this.keybytes, paramInt3, ZERO_IV_AES, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1313 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1316 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + exception.getMessage());
/* 1317 */       gSSException.initCause(exception);
/* 1318 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes128Decrypt(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/* 1326 */     byte[] arrayOfByte = null;
/*      */     
/*      */     try {
/* 1329 */       arrayOfByte = Aes128.decryptRaw(this.keybytes, paramInt4, ZERO_IV_AES, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1331 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1333 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + generalSecurityException.getMessage());
/* 1334 */       gSSException.initCause(generalSecurityException);
/* 1335 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1348 */     int i = arrayOfByte.length - 16 - 16;
/*      */     
/* 1350 */     System.arraycopy(arrayOfByte, 16, paramArrayOfbyte2, paramInt3, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes256Encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 1368 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte2.length];
/* 1369 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1370 */     System.arraycopy(paramArrayOfbyte3, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1371 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte2.length);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1377 */       return Aes256.encryptRaw(this.keybytes, paramInt3, ZERO_IV_AES, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1382 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1385 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES256 Cipher - " + exception.getMessage());
/* 1386 */       gSSException.initCause(exception);
/* 1387 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes256Decrypt(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1397 */       arrayOfByte = Aes256.decryptRaw(this.keybytes, paramInt4, ZERO_IV_AES, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1399 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1401 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + generalSecurityException.getMessage());
/* 1402 */       gSSException.initCause(generalSecurityException);
/* 1403 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1416 */     int i = arrayOfByte.length - 16 - 16;
/*      */     
/* 1418 */     System.arraycopy(arrayOfByte, 16, paramArrayOfbyte2, paramInt3, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class WrapTokenInputStream
/*      */     extends InputStream
/*      */   {
/*      */     private InputStream is;
/*      */ 
/*      */ 
/*      */     
/*      */     private int length;
/*      */ 
/*      */ 
/*      */     
/*      */     private int remaining;
/*      */ 
/*      */     
/*      */     private int temp;
/*      */ 
/*      */ 
/*      */     
/*      */     public WrapTokenInputStream(InputStream param1InputStream, int param1Int) {
/* 1443 */       this.is = param1InputStream;
/* 1444 */       this.length = param1Int;
/* 1445 */       this.remaining = param1Int;
/*      */     }
/*      */     
/*      */     public final int read() throws IOException {
/* 1449 */       if (this.remaining == 0) {
/* 1450 */         return -1;
/*      */       }
/* 1452 */       this.temp = this.is.read();
/* 1453 */       if (this.temp != -1)
/* 1454 */         this.remaining -= this.temp; 
/* 1455 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int read(byte[] param1ArrayOfbyte) throws IOException {
/* 1460 */       if (this.remaining == 0) {
/* 1461 */         return -1;
/*      */       }
/* 1463 */       this.temp = Math.min(this.remaining, param1ArrayOfbyte.length);
/* 1464 */       this.temp = this.is.read(param1ArrayOfbyte, 0, this.temp);
/* 1465 */       if (this.temp != -1)
/* 1466 */         this.remaining -= this.temp; 
/* 1467 */       return this.temp;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 1474 */       if (this.remaining == 0) {
/* 1475 */         return -1;
/*      */       }
/* 1477 */       this.temp = Math.min(this.remaining, param1Int2);
/* 1478 */       this.temp = this.is.read(param1ArrayOfbyte, param1Int1, this.temp);
/* 1479 */       if (this.temp != -1)
/* 1480 */         this.remaining -= this.temp; 
/* 1481 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final long skip(long param1Long) throws IOException {
/* 1486 */       if (this.remaining == 0) {
/* 1487 */         return 0L;
/*      */       }
/* 1489 */       this.temp = (int)Math.min(this.remaining, param1Long);
/* 1490 */       this.temp = (int)this.is.skip(this.temp);
/* 1491 */       this.remaining -= this.temp;
/* 1492 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int available() throws IOException {
/* 1497 */       return Math.min(this.remaining, this.is.available());
/*      */     }
/*      */     
/*      */     public final void close() throws IOException {
/* 1501 */       this.remaining = 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\CipherHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */