/*     */ package javax.smartcardio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResponseAPDU
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6962744978375594225L;
/*     */   private byte[] apdu;
/*     */   
/*     */   public ResponseAPDU(byte[] paramArrayOfbyte) {
/*  66 */     paramArrayOfbyte = (byte[])paramArrayOfbyte.clone();
/*  67 */     check(paramArrayOfbyte);
/*  68 */     this.apdu = paramArrayOfbyte;
/*     */   }
/*     */   
/*     */   private static void check(byte[] paramArrayOfbyte) {
/*  72 */     if (paramArrayOfbyte.length < 2) {
/*  73 */       throw new IllegalArgumentException("apdu must be at least 2 bytes long");
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
/*     */   public int getNr() {
/*  86 */     return this.apdu.length - 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData() {
/*  97 */     byte[] arrayOfByte = new byte[this.apdu.length - 2];
/*  98 */     System.arraycopy(this.apdu, 0, arrayOfByte, 0, arrayOfByte.length);
/*  99 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSW1() {
/* 108 */     return this.apdu[this.apdu.length - 2] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSW2() {
/* 117 */     return this.apdu[this.apdu.length - 1] & 0xFF;
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
/*     */   public int getSW() {
/* 129 */     return getSW1() << 8 | getSW2();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/* 138 */     return (byte[])this.apdu.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 147 */     return "ResponseAPDU: " + this.apdu.length + " bytes, SW=" + 
/* 148 */       Integer.toHexString(getSW());
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
/*     */   public boolean equals(Object paramObject) {
/* 160 */     if (this == paramObject) {
/* 161 */       return true;
/*     */     }
/* 163 */     if (!(paramObject instanceof ResponseAPDU)) {
/* 164 */       return false;
/*     */     }
/* 166 */     ResponseAPDU responseAPDU = (ResponseAPDU)paramObject;
/* 167 */     return Arrays.equals(this.apdu, responseAPDU.apdu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 176 */     return Arrays.hashCode(this.apdu);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 181 */     this.apdu = (byte[])paramObjectInputStream.readUnshared();
/* 182 */     check(this.apdu);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\smartcardio\ResponseAPDU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */