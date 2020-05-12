/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.border.MatteBorder;
/*     */ import javax.swing.border.SoftBevelBorder;
/*     */ import javax.swing.border.StrokeBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BorderFactory
/*     */ {
/*     */   public static Border createLineBorder(Color paramColor) {
/*  60 */     return new LineBorder(paramColor, 1);
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
/*     */   public static Border createLineBorder(Color paramColor, int paramInt) {
/*  75 */     return new LineBorder(paramColor, paramInt);
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
/*     */   public static Border createLineBorder(Color paramColor, int paramInt, boolean paramBoolean) {
/*  90 */     return new LineBorder(paramColor, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  95 */   static final Border sharedRaisedBevel = new BevelBorder(0);
/*  96 */   static final Border sharedLoweredBevel = new BevelBorder(1);
/*     */ 
/*     */ 
/*     */   
/*     */   private static Border sharedSoftRaisedBevel;
/*     */ 
/*     */   
/*     */   private static Border sharedSoftLoweredBevel;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createRaisedBevelBorder() {
/* 108 */     return createSharedBevel(0);
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
/*     */   public static Border createLoweredBevelBorder() {
/* 121 */     return createSharedBevel(1);
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
/*     */   public static Border createBevelBorder(int paramInt) {
/* 137 */     return createSharedBevel(paramInt);
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
/*     */   
/*     */   public static Border createBevelBorder(int paramInt, Color paramColor1, Color paramColor2) {
/* 155 */     return new BevelBorder(paramInt, paramColor1, paramColor2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createBevelBorder(int paramInt, Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4) {
/* 179 */     return new BevelBorder(paramInt, paramColor1, paramColor2, paramColor3, paramColor4);
/*     */   }
/*     */ 
/*     */   
/*     */   static Border createSharedBevel(int paramInt) {
/* 184 */     if (paramInt == 0)
/* 185 */       return sharedRaisedBevel; 
/* 186 */     if (paramInt == 1) {
/* 187 */       return sharedLoweredBevel;
/*     */     }
/* 189 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createRaisedSoftBevelBorder() {
/* 209 */     if (sharedSoftRaisedBevel == null) {
/* 210 */       sharedSoftRaisedBevel = new SoftBevelBorder(0);
/*     */     }
/* 212 */     return sharedSoftRaisedBevel;
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
/*     */   public static Border createLoweredSoftBevelBorder() {
/* 226 */     if (sharedSoftLoweredBevel == null) {
/* 227 */       sharedSoftLoweredBevel = new SoftBevelBorder(1);
/*     */     }
/* 229 */     return sharedSoftLoweredBevel;
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
/*     */   public static Border createSoftBevelBorder(int paramInt) {
/* 246 */     if (paramInt == 0) {
/* 247 */       return createRaisedSoftBevelBorder();
/*     */     }
/* 249 */     if (paramInt == 1) {
/* 250 */       return createLoweredSoftBevelBorder();
/*     */     }
/* 252 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createSoftBevelBorder(int paramInt, Color paramColor1, Color paramColor2) {
/* 273 */     return new SoftBevelBorder(paramInt, paramColor1, paramColor2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createSoftBevelBorder(int paramInt, Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4) {
/* 295 */     return new SoftBevelBorder(paramInt, paramColor1, paramColor2, paramColor3, paramColor4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 300 */   static final Border sharedEtchedBorder = new EtchedBorder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Border sharedRaisedEtchedBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createEtchedBorder() {
/* 311 */     return sharedEtchedBorder;
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
/*     */   public static Border createEtchedBorder(Color paramColor1, Color paramColor2) {
/* 323 */     return new EtchedBorder(paramColor1, paramColor2);
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
/*     */   public static Border createEtchedBorder(int paramInt) {
/* 340 */     switch (paramInt) {
/*     */       case 0:
/* 342 */         if (sharedRaisedEtchedBorder == null) {
/* 343 */           sharedRaisedEtchedBorder = new EtchedBorder(0);
/*     */         }
/*     */         
/* 346 */         return sharedRaisedEtchedBorder;
/*     */       case 1:
/* 348 */         return sharedEtchedBorder;
/*     */     } 
/* 350 */     throw new IllegalArgumentException("type must be one of EtchedBorder.RAISED or EtchedBorder.LOWERED");
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
/*     */   public static Border createEtchedBorder(int paramInt, Color paramColor1, Color paramColor2) {
/* 367 */     return new EtchedBorder(paramInt, paramColor1, paramColor2);
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
/*     */   public static TitledBorder createTitledBorder(String paramString) {
/* 382 */     return new TitledBorder(paramString);
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
/*     */   public static TitledBorder createTitledBorder(Border paramBorder) {
/* 398 */     return new TitledBorder(paramBorder);
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
/*     */   public static TitledBorder createTitledBorder(Border paramBorder, String paramString) {
/* 413 */     return new TitledBorder(paramBorder, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TitledBorder createTitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2) {
/* 451 */     return new TitledBorder(paramBorder, paramString, paramInt1, paramInt2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TitledBorder createTitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2, Font paramFont) {
/* 492 */     return new TitledBorder(paramBorder, paramString, paramInt1, paramInt2, paramFont);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TitledBorder createTitledBorder(Border paramBorder, String paramString, int paramInt1, int paramInt2, Font paramFont, Color paramColor) {
/* 534 */     return new TitledBorder(paramBorder, paramString, paramInt1, paramInt2, paramFont, paramColor);
/*     */   }
/*     */ 
/*     */   
/* 538 */   static final Border emptyBorder = new EmptyBorder(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   private static Border sharedDashedBorder;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createEmptyBorder() {
/* 547 */     return emptyBorder;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createEmptyBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 567 */     return new EmptyBorder(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundBorder createCompoundBorder() {
/* 578 */     return new CompoundBorder();
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
/*     */   public static CompoundBorder createCompoundBorder(Border paramBorder1, Border paramBorder2) {
/* 593 */     return new CompoundBorder(paramBorder1, paramBorder2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatteBorder createMatteBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 615 */     return new MatteBorder(paramInt1, paramInt2, paramInt3, paramInt4, paramColor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatteBorder createMatteBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Icon paramIcon) {
/* 639 */     return new MatteBorder(paramInt1, paramInt2, paramInt3, paramInt4, paramIcon);
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
/*     */   
/*     */   public static Border createStrokeBorder(BasicStroke paramBasicStroke) {
/* 657 */     return new StrokeBorder(paramBasicStroke);
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
/*     */   public static Border createStrokeBorder(BasicStroke paramBasicStroke, Paint paramPaint) {
/* 674 */     return new StrokeBorder(paramBasicStroke, paramPaint);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createDashedBorder(Paint paramPaint) {
/* 697 */     return createDashedBorder(paramPaint, 1.0F, 1.0F, 1.0F, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createDashedBorder(Paint paramPaint, float paramFloat1, float paramFloat2) {
/* 718 */     return createDashedBorder(paramPaint, 1.0F, paramFloat1, paramFloat2, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border createDashedBorder(Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean) {
/* 740 */     boolean bool1 = (!paramBoolean && paramPaint == null && paramFloat1 == 1.0F && paramFloat2 == 1.0F && paramFloat3 == 1.0F) ? true : false;
/* 741 */     if (bool1 && sharedDashedBorder != null) {
/* 742 */       return sharedDashedBorder;
/*     */     }
/* 744 */     if (paramFloat1 < 1.0F) {
/* 745 */       throw new IllegalArgumentException("thickness is less than 1");
/*     */     }
/* 747 */     if (paramFloat2 < 1.0F) {
/* 748 */       throw new IllegalArgumentException("length is less than 1");
/*     */     }
/* 750 */     if (paramFloat3 < 0.0F) {
/* 751 */       throw new IllegalArgumentException("spacing is less than 0");
/*     */     }
/* 753 */     boolean bool2 = paramBoolean ? true : true;
/* 754 */     boolean bool3 = paramBoolean ? true : false;
/* 755 */     float[] arrayOfFloat = { paramFloat1 * (paramFloat2 - 1.0F), paramFloat1 * (paramFloat3 + 1.0F) };
/* 756 */     Border border = createStrokeBorder(new BasicStroke(paramFloat1, bool2, bool3, paramFloat1 * 2.0F, arrayOfFloat, 0.0F), paramPaint);
/* 757 */     if (bool1) {
/* 758 */       sharedDashedBorder = border;
/*     */     }
/* 760 */     return border;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\BorderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */