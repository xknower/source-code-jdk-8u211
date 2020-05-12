/*     */ package sun.security.provider;
/*     */ 
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
/*     */ public final class SHA
/*     */   extends DigestBase
/*     */ {
/*     */   private int[] W;
/*     */   private int[] state;
/*     */   private static final int round1_kt = 1518500249;
/*     */   private static final int round2_kt = 1859775393;
/*     */   private static final int round3_kt = -1894007588;
/*     */   private static final int round4_kt = -899497514;
/*     */   
/*     */   public SHA() {
/*  61 */     super("SHA-1", 20, 64);
/*  62 */     this.state = new int[5];
/*  63 */     this.W = new int[80];
/*  64 */     resetHashes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  71 */     SHA sHA = (SHA)super.clone();
/*  72 */     sHA.state = (int[])sHA.state.clone();
/*  73 */     sHA.W = new int[80];
/*  74 */     return sHA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implReset() {
/*  82 */     resetHashes();
/*     */     
/*  84 */     Arrays.fill(this.W, 0);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/*  88 */     this.state[0] = 1732584193;
/*  89 */     this.state[1] = -271733879;
/*  90 */     this.state[2] = -1732584194;
/*  91 */     this.state[3] = 271733878;
/*  92 */     this.state[4] = -1009589776;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/*  99 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 101 */     int i = (int)this.bytesProcessed & 0x3F;
/* 102 */     int j = (i < 56) ? (56 - i) : (120 - i);
/* 103 */     engineUpdate(padding, 0, j);
/*     */     
/* 105 */     ByteArrayAccess.i2bBig4((int)(l >>> 32L), this.buffer, 56);
/* 106 */     ByteArrayAccess.i2bBig4((int)l, this.buffer, 60);
/* 107 */     implCompress(this.buffer, 0);
/*     */     
/* 109 */     ByteArrayAccess.i2bBig(this.state, 0, paramArrayOfbyte, paramInt, 20);
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
/*     */   
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 126 */     ByteArrayAccess.b2iBig64(paramArrayOfbyte, paramInt, this.W);
/*     */     
/*     */     int i;
/*     */     
/* 130 */     for (i = 16; i <= 79; i++) {
/* 131 */       int i1 = this.W[i - 3] ^ this.W[i - 8] ^ this.W[i - 14] ^ this.W[i - 16];
/* 132 */       this.W[i] = i1 << 1 | i1 >>> 31;
/*     */     } 
/*     */     
/* 135 */     i = this.state[0];
/* 136 */     int j = this.state[1];
/* 137 */     int k = this.state[2];
/* 138 */     int m = this.state[3];
/* 139 */     int n = this.state[4];
/*     */     
/*     */     byte b;
/* 142 */     for (b = 0; b < 20; b++) {
/* 143 */       int i1 = (i << 5 | i >>> 27) + (j & k | (j ^ 0xFFFFFFFF) & m) + n + this.W[b] + 1518500249;
/*     */       
/* 145 */       n = m;
/* 146 */       m = k;
/* 147 */       k = j << 30 | j >>> 2;
/* 148 */       j = i;
/* 149 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     for (b = 20; b < 40; b++) {
/* 154 */       int i1 = (i << 5 | i >>> 27) + (j ^ k ^ m) + n + this.W[b] + 1859775393;
/*     */       
/* 156 */       n = m;
/* 157 */       m = k;
/* 158 */       k = j << 30 | j >>> 2;
/* 159 */       j = i;
/* 160 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 164 */     for (b = 40; b < 60; b++) {
/* 165 */       int i1 = (i << 5 | i >>> 27) + (j & k | j & m | k & m) + n + this.W[b] + -1894007588;
/*     */       
/* 167 */       n = m;
/* 168 */       m = k;
/* 169 */       k = j << 30 | j >>> 2;
/* 170 */       j = i;
/* 171 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 175 */     for (b = 60; b < 80; b++) {
/* 176 */       int i1 = (i << 5 | i >>> 27) + (j ^ k ^ m) + n + this.W[b] + -899497514;
/*     */       
/* 178 */       n = m;
/* 179 */       m = k;
/* 180 */       k = j << 30 | j >>> 2;
/* 181 */       j = i;
/* 182 */       i = i1;
/*     */     } 
/* 184 */     this.state[0] = this.state[0] + i;
/* 185 */     this.state[1] = this.state[1] + j;
/* 186 */     this.state[2] = this.state[2] + k;
/* 187 */     this.state[3] = this.state[3] + m;
/* 188 */     this.state[4] = this.state[4] + n;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\SHA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */