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
/*     */ abstract class SHA2
/*     */   extends DigestBase
/*     */ {
/*     */   private static final int ITERATION = 64;
/*  49 */   private static final int[] ROUND_CONSTS = new int[] { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] W;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] initialHashes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SHA2(String paramString, int paramInt, int[] paramArrayOfint) {
/*  81 */     super(paramString, paramInt, 64);
/*  82 */     this.initialHashes = paramArrayOfint;
/*  83 */     this.state = new int[8];
/*  84 */     this.W = new int[64];
/*  85 */     resetHashes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implReset() {
/*  92 */     resetHashes();
/*  93 */     Arrays.fill(this.W, 0);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/*  97 */     System.arraycopy(this.initialHashes, 0, this.state, 0, this.state.length);
/*     */   }
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/* 101 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 103 */     int i = (int)this.bytesProcessed & 0x3F;
/* 104 */     int j = (i < 56) ? (56 - i) : (120 - i);
/* 105 */     engineUpdate(padding, 0, j);
/*     */     
/* 107 */     ByteArrayAccess.i2bBig4((int)(l >>> 32L), this.buffer, 56);
/* 108 */     ByteArrayAccess.i2bBig4((int)l, this.buffer, 60);
/* 109 */     implCompress(this.buffer, 0);
/*     */     
/* 111 */     ByteArrayAccess.i2bBig(this.state, 0, paramArrayOfbyte, paramInt, engineGetDigestLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_ch(int paramInt1, int paramInt2, int paramInt3) {
/* 122 */     return paramInt1 & paramInt2 ^ (paramInt1 ^ 0xFFFFFFFF) & paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_maj(int paramInt1, int paramInt2, int paramInt3) {
/* 133 */     return paramInt1 & paramInt2 ^ paramInt1 & paramInt3 ^ paramInt2 & paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_R(int paramInt1, int paramInt2) {
/* 143 */     return paramInt1 >>> paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_S(int paramInt1, int paramInt2) {
/* 153 */     return paramInt1 >>> paramInt2 | paramInt1 << 32 - paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_sigma0(int paramInt) {
/* 162 */     return lf_S(paramInt, 2) ^ lf_S(paramInt, 13) ^ lf_S(paramInt, 22);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_sigma1(int paramInt) {
/* 171 */     return lf_S(paramInt, 6) ^ lf_S(paramInt, 11) ^ lf_S(paramInt, 25);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_delta0(int paramInt) {
/* 180 */     return lf_S(paramInt, 7) ^ lf_S(paramInt, 18) ^ lf_R(paramInt, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lf_delta1(int paramInt) {
/* 189 */     return lf_S(paramInt, 17) ^ lf_S(paramInt, 19) ^ lf_R(paramInt, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 196 */     ByteArrayAccess.b2iBig64(paramArrayOfbyte, paramInt, this.W);
/*     */     
/*     */     int i;
/*     */     
/* 200 */     for (i = 16; i < 64; i++) {
/* 201 */       this.W[i] = lf_delta1(this.W[i - 2]) + this.W[i - 7] + lf_delta0(this.W[i - 15]) + this.W[i - 16];
/*     */     }
/*     */ 
/*     */     
/* 205 */     i = this.state[0];
/* 206 */     int j = this.state[1];
/* 207 */     int k = this.state[2];
/* 208 */     int m = this.state[3];
/* 209 */     int n = this.state[4];
/* 210 */     int i1 = this.state[5];
/* 211 */     int i2 = this.state[6];
/* 212 */     int i3 = this.state[7];
/*     */     
/* 214 */     for (byte b = 0; b < 64; b++) {
/* 215 */       int i4 = i3 + lf_sigma1(n) + lf_ch(n, i1, i2) + ROUND_CONSTS[b] + this.W[b];
/* 216 */       int i5 = lf_sigma0(i) + lf_maj(i, j, k);
/* 217 */       i3 = i2;
/* 218 */       i2 = i1;
/* 219 */       i1 = n;
/* 220 */       n = m + i4;
/* 221 */       m = k;
/* 222 */       k = j;
/* 223 */       j = i;
/* 224 */       i = i4 + i5;
/*     */     } 
/* 226 */     this.state[0] = this.state[0] + i;
/* 227 */     this.state[1] = this.state[1] + j;
/* 228 */     this.state[2] = this.state[2] + k;
/* 229 */     this.state[3] = this.state[3] + m;
/* 230 */     this.state[4] = this.state[4] + n;
/* 231 */     this.state[5] = this.state[5] + i1;
/* 232 */     this.state[6] = this.state[6] + i2;
/* 233 */     this.state[7] = this.state[7] + i3;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 237 */     SHA2 sHA2 = (SHA2)super.clone();
/* 238 */     sHA2.state = (int[])sHA2.state.clone();
/* 239 */     sHA2.W = new int[64];
/* 240 */     return sHA2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class SHA224
/*     */     extends SHA2
/*     */   {
/* 247 */     private static final int[] INITIAL_HASHES = new int[] { -1056596264, 914150663, 812702999, -150054599, -4191439, 1750603025, 1694076839, -1090891868 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SHA224() {
/* 253 */       super("SHA-224", 28, INITIAL_HASHES);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class SHA256
/*     */     extends SHA2
/*     */   {
/* 261 */     private static final int[] INITIAL_HASHES = new int[] { 1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SHA256() {
/* 267 */       super("SHA-256", 32, INITIAL_HASHES);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\SHA2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */