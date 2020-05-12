/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.security.DigestException;
/*     */ import java.security.MessageDigestSpi;
/*     */ import java.security.ProviderException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DigestBase
/*     */   extends MessageDigestSpi
/*     */   implements Cloneable
/*     */ {
/*     */   private byte[] oneByte;
/*     */   private final String algorithm;
/*     */   private final int digestLength;
/*     */   private final int blockSize;
/*     */   byte[] buffer;
/*     */   private int bufOfs;
/*     */   long bytesProcessed;
/*     */   
/*     */   DigestBase(String paramString, int paramInt1, int paramInt2) {
/*  80 */     this.algorithm = paramString;
/*  81 */     this.digestLength = paramInt1;
/*  82 */     this.blockSize = paramInt2;
/*  83 */     this.buffer = new byte[paramInt2];
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int engineGetDigestLength() {
/*  88 */     return this.digestLength;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineUpdate(byte paramByte) {
/*  93 */     if (this.oneByte == null) {
/*  94 */       this.oneByte = new byte[1];
/*     */     }
/*  96 */     this.oneByte[0] = paramByte;
/*  97 */     engineUpdate(this.oneByte, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 102 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/* 105 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 106 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 108 */     if (this.bytesProcessed < 0L) {
/* 109 */       engineReset();
/*     */     }
/* 111 */     this.bytesProcessed += paramInt2;
/*     */     
/* 113 */     if (this.bufOfs != 0) {
/* 114 */       int i = Math.min(paramInt2, this.blockSize - this.bufOfs);
/* 115 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, this.bufOfs, i);
/* 116 */       this.bufOfs += i;
/* 117 */       paramInt1 += i;
/* 118 */       paramInt2 -= i;
/* 119 */       if (this.bufOfs >= this.blockSize) {
/*     */         
/* 121 */         implCompress(this.buffer, 0);
/* 122 */         this.bufOfs = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (paramInt2 >= this.blockSize) {
/* 127 */       int i = paramInt1 + paramInt2;
/* 128 */       paramInt1 = implCompressMultiBlock(paramArrayOfbyte, paramInt1, i - this.blockSize);
/* 129 */       paramInt2 = i - paramInt1;
/*     */     } 
/*     */     
/* 132 */     if (paramInt2 > 0) {
/* 133 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, 0, paramInt2);
/* 134 */       this.bufOfs = paramInt2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int implCompressMultiBlock(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 140 */     for (; paramInt1 <= paramInt2; paramInt1 += this.blockSize) {
/* 141 */       implCompress(paramArrayOfbyte, paramInt1);
/*     */     }
/* 143 */     return paramInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineReset() {
/* 148 */     if (this.bytesProcessed == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     implReset();
/* 153 */     this.bufOfs = 0;
/* 154 */     this.bytesProcessed = 0L;
/* 155 */     Arrays.fill(this.buffer, (byte)0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final byte[] engineDigest() {
/* 160 */     byte[] arrayOfByte = new byte[this.digestLength];
/*     */     try {
/* 162 */       engineDigest(arrayOfByte, 0, arrayOfByte.length);
/* 163 */     } catch (DigestException digestException) {
/* 164 */       throw (ProviderException)(new ProviderException("Internal error"))
/* 165 */         .initCause(digestException);
/*     */     } 
/* 167 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int engineDigest(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DigestException {
/* 173 */     if (paramInt2 < this.digestLength) {
/* 174 */       throw new DigestException("Length must be at least " + this.digestLength + " for " + this.algorithm + "digests");
/*     */     }
/*     */     
/* 177 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 178 */       throw new DigestException("Buffer too short to store digest");
/*     */     }
/* 180 */     if (this.bytesProcessed < 0L) {
/* 181 */       engineReset();
/*     */     }
/* 183 */     implDigest(paramArrayOfbyte, paramInt1);
/* 184 */     this.bytesProcessed = -1L;
/* 185 */     return this.digestLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implCompress(byte[] paramArrayOfbyte, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implDigest(byte[] paramArrayOfbyte, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implReset();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 207 */     DigestBase digestBase = (DigestBase)super.clone();
/* 208 */     digestBase.buffer = (byte[])digestBase.buffer.clone();
/* 209 */     return digestBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   static final byte[] padding = new byte[136]; static {
/* 220 */     padding[0] = Byte.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\DigestBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */