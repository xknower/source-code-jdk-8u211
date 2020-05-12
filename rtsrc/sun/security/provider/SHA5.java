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
/*     */ abstract class SHA5
/*     */   extends DigestBase
/*     */ {
/*     */   private static final int ITERATION = 80;
/*  53 */   private static final long[] ROUND_CONSTS = new long[] { 4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long[] W;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long[] state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long[] initialHashes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SHA5(String paramString, int paramInt, long[] paramArrayOflong) {
/*  96 */     super(paramString, paramInt, 128);
/*  97 */     this.initialHashes = paramArrayOflong;
/*  98 */     this.state = new long[8];
/*  99 */     this.W = new long[80];
/* 100 */     resetHashes();
/*     */   }
/*     */   
/*     */   final void implReset() {
/* 104 */     resetHashes();
/* 105 */     Arrays.fill(this.W, 0L);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/* 109 */     System.arraycopy(this.initialHashes, 0, this.state, 0, this.state.length);
/*     */   }
/*     */   
/*     */   final void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/* 113 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 115 */     int i = (int)this.bytesProcessed & 0x7F;
/* 116 */     int j = (i < 112) ? (112 - i) : (240 - i);
/* 117 */     engineUpdate(padding, 0, j + 8);
/*     */     
/* 119 */     ByteArrayAccess.i2bBig4((int)(l >>> 32L), this.buffer, 120);
/* 120 */     ByteArrayAccess.i2bBig4((int)l, this.buffer, 124);
/* 121 */     implCompress(this.buffer, 0);
/*     */     
/* 123 */     ByteArrayAccess.l2bBig(this.state, 0, paramArrayOfbyte, paramInt, engineGetDigestLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_ch(long paramLong1, long paramLong2, long paramLong3) {
/* 134 */     return paramLong1 & paramLong2 ^ (paramLong1 ^ 0xFFFFFFFFFFFFFFFFL) & paramLong3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_maj(long paramLong1, long paramLong2, long paramLong3) {
/* 145 */     return paramLong1 & paramLong2 ^ paramLong1 & paramLong3 ^ paramLong2 & paramLong3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_R(long paramLong, int paramInt) {
/* 155 */     return paramLong >>> paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_S(long paramLong, int paramInt) {
/* 165 */     return paramLong >>> paramInt | paramLong << 64 - paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_sigma0(long paramLong) {
/* 174 */     return lf_S(paramLong, 28) ^ lf_S(paramLong, 34) ^ lf_S(paramLong, 39);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_sigma1(long paramLong) {
/* 183 */     return lf_S(paramLong, 14) ^ lf_S(paramLong, 18) ^ lf_S(paramLong, 41);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_delta0(long paramLong) {
/* 192 */     return lf_S(paramLong, 1) ^ lf_S(paramLong, 8) ^ lf_R(paramLong, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lf_delta1(long paramLong) {
/* 201 */     return lf_S(paramLong, 19) ^ lf_S(paramLong, 61) ^ lf_R(paramLong, 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 212 */     ByteArrayAccess.b2lBig128(paramArrayOfbyte, paramInt, this.W);
/*     */ 
/*     */ 
/*     */     
/* 216 */     for (byte b1 = 16; b1 < 80; b1++) {
/* 217 */       this.W[b1] = lf_delta1(this.W[b1 - 2]) + this.W[b1 - 7] + lf_delta0(this.W[b1 - 15]) + this.W[b1 - 16];
/*     */     }
/*     */ 
/*     */     
/* 221 */     long l1 = this.state[0];
/* 222 */     long l2 = this.state[1];
/* 223 */     long l3 = this.state[2];
/* 224 */     long l4 = this.state[3];
/* 225 */     long l5 = this.state[4];
/* 226 */     long l6 = this.state[5];
/* 227 */     long l7 = this.state[6];
/* 228 */     long l8 = this.state[7];
/*     */     
/* 230 */     for (byte b2 = 0; b2 < 80; b2++) {
/* 231 */       long l9 = l8 + lf_sigma1(l5) + lf_ch(l5, l6, l7) + ROUND_CONSTS[b2] + this.W[b2];
/* 232 */       long l10 = lf_sigma0(l1) + lf_maj(l1, l2, l3);
/* 233 */       l8 = l7;
/* 234 */       l7 = l6;
/* 235 */       l6 = l5;
/* 236 */       l5 = l4 + l9;
/* 237 */       l4 = l3;
/* 238 */       l3 = l2;
/* 239 */       l2 = l1;
/* 240 */       l1 = l9 + l10;
/*     */     } 
/* 242 */     this.state[0] = this.state[0] + l1;
/* 243 */     this.state[1] = this.state[1] + l2;
/* 244 */     this.state[2] = this.state[2] + l3;
/* 245 */     this.state[3] = this.state[3] + l4;
/* 246 */     this.state[4] = this.state[4] + l5;
/* 247 */     this.state[5] = this.state[5] + l6;
/* 248 */     this.state[6] = this.state[6] + l7;
/* 249 */     this.state[7] = this.state[7] + l8;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 253 */     SHA5 sHA5 = (SHA5)super.clone();
/* 254 */     sHA5.state = (long[])sHA5.state.clone();
/* 255 */     sHA5.W = new long[80];
/* 256 */     return sHA5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class SHA512
/*     */     extends SHA5
/*     */   {
/* 264 */     private static final long[] INITIAL_HASHES = new long[] { 7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SHA512() {
/* 272 */       super("SHA-512", 64, INITIAL_HASHES);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class SHA384
/*     */     extends SHA5
/*     */   {
/* 281 */     private static final long[] INITIAL_HASHES = new long[] { -3766243637369397544L, 7105036623409894663L, -7973340178411365097L, 1526699215303891257L, 7436329637833083697L, -8163818279084223215L, -2662702644619276377L, 5167115440072839076L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SHA384() {
/* 289 */       super("SHA-384", 48, INITIAL_HASHES);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\SHA5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */