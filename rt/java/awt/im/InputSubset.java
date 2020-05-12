/*     */ package java.awt.im;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InputSubset
/*     */   extends Character.Subset
/*     */ {
/*     */   private InputSubset(String paramString) {
/*  41 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final InputSubset LATIN = new InputSubset("LATIN");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final InputSubset LATIN_DIGITS = new InputSubset("LATIN_DIGITS");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final InputSubset TRADITIONAL_HANZI = new InputSubset("TRADITIONAL_HANZI");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final InputSubset SIMPLIFIED_HANZI = new InputSubset("SIMPLIFIED_HANZI");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final InputSubset KANJI = new InputSubset("KANJI");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final InputSubset HANJA = new InputSubset("HANJA");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final InputSubset HALFWIDTH_KATAKANA = new InputSubset("HALFWIDTH_KATAKANA");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final InputSubset FULLWIDTH_LATIN = new InputSubset("FULLWIDTH_LATIN");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final InputSubset FULLWIDTH_DIGITS = new InputSubset("FULLWIDTH_DIGITS");
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\im\InputSubset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */