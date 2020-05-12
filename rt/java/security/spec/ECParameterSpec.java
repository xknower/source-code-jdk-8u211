/*     */ package java.security.spec;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECParameterSpec
/*     */   implements AlgorithmParameterSpec
/*     */ {
/*     */   private final EllipticCurve curve;
/*     */   private final ECPoint g;
/*     */   private final BigInteger n;
/*     */   private final int h;
/*     */   
/*     */   public ECParameterSpec(EllipticCurve paramEllipticCurve, ECPoint paramECPoint, BigInteger paramBigInteger, int paramInt) {
/*  61 */     if (paramEllipticCurve == null) {
/*  62 */       throw new NullPointerException("curve is null");
/*     */     }
/*  64 */     if (paramECPoint == null) {
/*  65 */       throw new NullPointerException("g is null");
/*     */     }
/*  67 */     if (paramBigInteger == null) {
/*  68 */       throw new NullPointerException("n is null");
/*     */     }
/*  70 */     if (paramBigInteger.signum() != 1) {
/*  71 */       throw new IllegalArgumentException("n is not positive");
/*     */     }
/*  73 */     if (paramInt <= 0) {
/*  74 */       throw new IllegalArgumentException("h is not positive");
/*     */     }
/*  76 */     this.curve = paramEllipticCurve;
/*  77 */     this.g = paramECPoint;
/*  78 */     this.n = paramBigInteger;
/*  79 */     this.h = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EllipticCurve getCurve() {
/*  87 */     return this.curve;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECPoint getGenerator() {
/*  95 */     return this.g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getOrder() {
/* 103 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCofactor() {
/* 111 */     return this.h;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\spec\ECParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */