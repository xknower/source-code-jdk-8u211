/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WFontMetrics
/*     */   extends FontMetrics
/*     */ {
/*     */   int[] widths;
/*     */   int ascent;
/*     */   int descent;
/*     */   int leading;
/*     */   int height;
/*     */   int maxAscent;
/*     */   int maxDescent;
/*     */   int maxHeight;
/*     */   int maxAdvance;
/*     */   
/*     */   static {
/*  39 */     initIDs();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WFontMetrics(Font paramFont) {
/* 113 */     super(paramFont);
/* 114 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeading() {
/* 122 */     return this.leading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAscent() {
/* 130 */     return this.ascent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDescent() {
/* 138 */     return this.descent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 146 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxAscent() {
/* 154 */     return this.maxAscent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDescent() {
/* 162 */     return this.maxDescent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxAdvance() {
/* 170 */     return this.maxAdvance;
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
/*     */   public int[] getWidths() {
/* 196 */     return this.widths;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 201 */   static Hashtable table = new Hashtable<>();
/*     */   
/*     */   static FontMetrics getFontMetrics(Font paramFont) {
/* 204 */     FontMetrics fontMetrics = (FontMetrics)table.get(paramFont);
/* 205 */     if (fontMetrics == null) {
/* 206 */       table.put(paramFont, fontMetrics = new WFontMetrics(paramFont));
/*     */     }
/* 208 */     return fontMetrics;
/*     */   }
/*     */   
/*     */   public native int stringWidth(String paramString);
/*     */   
/*     */   public native int charsWidth(char[] paramArrayOfchar, int paramInt1, int paramInt2);
/*     */   
/*     */   public native int bytesWidth(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   native void init();
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WFontMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */