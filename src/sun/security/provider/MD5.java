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
/*     */ public final class MD5
/*     */   extends DigestBase
/*     */ {
/*     */   private int[] state;
/*     */   private int[] x;
/*     */   private static final int S11 = 7;
/*     */   private static final int S12 = 12;
/*     */   private static final int S13 = 17;
/*     */   private static final int S14 = 22;
/*     */   private static final int S21 = 5;
/*     */   private static final int S22 = 9;
/*     */   private static final int S23 = 14;
/*     */   private static final int S24 = 20;
/*     */   private static final int S31 = 4;
/*     */   private static final int S32 = 11;
/*     */   private static final int S33 = 16;
/*     */   private static final int S34 = 23;
/*     */   private static final int S41 = 6;
/*     */   private static final int S42 = 10;
/*     */   private static final int S43 = 15;
/*     */   private static final int S44 = 21;
/*     */   
/*     */   public MD5() {
/*  68 */     super("MD5", 16, 64);
/*  69 */     this.state = new int[4];
/*  70 */     this.x = new int[16];
/*  71 */     resetHashes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  76 */     MD5 mD5 = (MD5)super.clone();
/*  77 */     mD5.state = (int[])mD5.state.clone();
/*  78 */     mD5.x = new int[16];
/*  79 */     return mD5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implReset() {
/*  87 */     resetHashes();
/*     */     
/*  89 */     Arrays.fill(this.x, 0);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/*  93 */     this.state[0] = 1732584193;
/*  94 */     this.state[1] = -271733879;
/*  95 */     this.state[2] = -1732584194;
/*  96 */     this.state[3] = 271733878;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/* 105 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 107 */     int i = (int)this.bytesProcessed & 0x3F;
/* 108 */     int j = (i < 56) ? (56 - i) : (120 - i);
/* 109 */     engineUpdate(padding, 0, j);
/*     */     
/* 111 */     ByteArrayAccess.i2bLittle4((int)l, this.buffer, 56);
/* 112 */     ByteArrayAccess.i2bLittle4((int)(l >>> 32L), this.buffer, 60);
/* 113 */     implCompress(this.buffer, 0);
/*     */     
/* 115 */     ByteArrayAccess.i2bLittle(this.state, 0, paramArrayOfbyte, paramInt, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int FF(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 125 */     paramInt1 += (paramInt2 & paramInt3 | (paramInt2 ^ 0xFFFFFFFF) & paramInt4) + paramInt5 + paramInt7;
/* 126 */     return (paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6) + paramInt2;
/*     */   }
/*     */   
/*     */   private static int GG(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 130 */     paramInt1 += (paramInt2 & paramInt4 | paramInt3 & (paramInt4 ^ 0xFFFFFFFF)) + paramInt5 + paramInt7;
/* 131 */     return (paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6) + paramInt2;
/*     */   }
/*     */   
/*     */   private static int HH(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 135 */     paramInt1 += (paramInt2 ^ paramInt3 ^ paramInt4) + paramInt5 + paramInt7;
/* 136 */     return (paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6) + paramInt2;
/*     */   }
/*     */   
/*     */   private static int II(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 140 */     paramInt1 += (paramInt3 ^ (paramInt2 | paramInt4 ^ 0xFFFFFFFF)) + paramInt5 + paramInt7;
/* 141 */     return (paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6) + paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 150 */     ByteArrayAccess.b2iLittle64(paramArrayOfbyte, paramInt, this.x);
/*     */     
/* 152 */     int i = this.state[0];
/* 153 */     int j = this.state[1];
/* 154 */     int k = this.state[2];
/* 155 */     int m = this.state[3];
/*     */ 
/*     */     
/* 158 */     i = FF(i, j, k, m, this.x[0], 7, -680876936);
/* 159 */     m = FF(m, i, j, k, this.x[1], 12, -389564586);
/* 160 */     k = FF(k, m, i, j, this.x[2], 17, 606105819);
/* 161 */     j = FF(j, k, m, i, this.x[3], 22, -1044525330);
/* 162 */     i = FF(i, j, k, m, this.x[4], 7, -176418897);
/* 163 */     m = FF(m, i, j, k, this.x[5], 12, 1200080426);
/* 164 */     k = FF(k, m, i, j, this.x[6], 17, -1473231341);
/* 165 */     j = FF(j, k, m, i, this.x[7], 22, -45705983);
/* 166 */     i = FF(i, j, k, m, this.x[8], 7, 1770035416);
/* 167 */     m = FF(m, i, j, k, this.x[9], 12, -1958414417);
/* 168 */     k = FF(k, m, i, j, this.x[10], 17, -42063);
/* 169 */     j = FF(j, k, m, i, this.x[11], 22, -1990404162);
/* 170 */     i = FF(i, j, k, m, this.x[12], 7, 1804603682);
/* 171 */     m = FF(m, i, j, k, this.x[13], 12, -40341101);
/* 172 */     k = FF(k, m, i, j, this.x[14], 17, -1502002290);
/* 173 */     j = FF(j, k, m, i, this.x[15], 22, 1236535329);
/*     */ 
/*     */     
/* 176 */     i = GG(i, j, k, m, this.x[1], 5, -165796510);
/* 177 */     m = GG(m, i, j, k, this.x[6], 9, -1069501632);
/* 178 */     k = GG(k, m, i, j, this.x[11], 14, 643717713);
/* 179 */     j = GG(j, k, m, i, this.x[0], 20, -373897302);
/* 180 */     i = GG(i, j, k, m, this.x[5], 5, -701558691);
/* 181 */     m = GG(m, i, j, k, this.x[10], 9, 38016083);
/* 182 */     k = GG(k, m, i, j, this.x[15], 14, -660478335);
/* 183 */     j = GG(j, k, m, i, this.x[4], 20, -405537848);
/* 184 */     i = GG(i, j, k, m, this.x[9], 5, 568446438);
/* 185 */     m = GG(m, i, j, k, this.x[14], 9, -1019803690);
/* 186 */     k = GG(k, m, i, j, this.x[3], 14, -187363961);
/* 187 */     j = GG(j, k, m, i, this.x[8], 20, 1163531501);
/* 188 */     i = GG(i, j, k, m, this.x[13], 5, -1444681467);
/* 189 */     m = GG(m, i, j, k, this.x[2], 9, -51403784);
/* 190 */     k = GG(k, m, i, j, this.x[7], 14, 1735328473);
/* 191 */     j = GG(j, k, m, i, this.x[12], 20, -1926607734);
/*     */ 
/*     */     
/* 194 */     i = HH(i, j, k, m, this.x[5], 4, -378558);
/* 195 */     m = HH(m, i, j, k, this.x[8], 11, -2022574463);
/* 196 */     k = HH(k, m, i, j, this.x[11], 16, 1839030562);
/* 197 */     j = HH(j, k, m, i, this.x[14], 23, -35309556);
/* 198 */     i = HH(i, j, k, m, this.x[1], 4, -1530992060);
/* 199 */     m = HH(m, i, j, k, this.x[4], 11, 1272893353);
/* 200 */     k = HH(k, m, i, j, this.x[7], 16, -155497632);
/* 201 */     j = HH(j, k, m, i, this.x[10], 23, -1094730640);
/* 202 */     i = HH(i, j, k, m, this.x[13], 4, 681279174);
/* 203 */     m = HH(m, i, j, k, this.x[0], 11, -358537222);
/* 204 */     k = HH(k, m, i, j, this.x[3], 16, -722521979);
/* 205 */     j = HH(j, k, m, i, this.x[6], 23, 76029189);
/* 206 */     i = HH(i, j, k, m, this.x[9], 4, -640364487);
/* 207 */     m = HH(m, i, j, k, this.x[12], 11, -421815835);
/* 208 */     k = HH(k, m, i, j, this.x[15], 16, 530742520);
/* 209 */     j = HH(j, k, m, i, this.x[2], 23, -995338651);
/*     */ 
/*     */     
/* 212 */     i = II(i, j, k, m, this.x[0], 6, -198630844);
/* 213 */     m = II(m, i, j, k, this.x[7], 10, 1126891415);
/* 214 */     k = II(k, m, i, j, this.x[14], 15, -1416354905);
/* 215 */     j = II(j, k, m, i, this.x[5], 21, -57434055);
/* 216 */     i = II(i, j, k, m, this.x[12], 6, 1700485571);
/* 217 */     m = II(m, i, j, k, this.x[3], 10, -1894986606);
/* 218 */     k = II(k, m, i, j, this.x[10], 15, -1051523);
/* 219 */     j = II(j, k, m, i, this.x[1], 21, -2054922799);
/* 220 */     i = II(i, j, k, m, this.x[8], 6, 1873313359);
/* 221 */     m = II(m, i, j, k, this.x[15], 10, -30611744);
/* 222 */     k = II(k, m, i, j, this.x[6], 15, -1560198380);
/* 223 */     j = II(j, k, m, i, this.x[13], 21, 1309151649);
/* 224 */     i = II(i, j, k, m, this.x[4], 6, -145523070);
/* 225 */     m = II(m, i, j, k, this.x[11], 10, -1120210379);
/* 226 */     k = II(k, m, i, j, this.x[2], 15, 718787259);
/* 227 */     j = II(j, k, m, i, this.x[9], 21, -343485551);
/*     */     
/* 229 */     this.state[0] = this.state[0] + i;
/* 230 */     this.state[1] = this.state[1] + j;
/* 231 */     this.state[2] = this.state[2] + k;
/* 232 */     this.state[3] = this.state[3] + m;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\MD5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */