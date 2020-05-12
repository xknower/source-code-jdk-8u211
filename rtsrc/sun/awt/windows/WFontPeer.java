/*    */ package sun.awt.windows;
/*    */ 
/*    */ import sun.awt.PlatformFont;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class WFontPeer
/*    */   extends PlatformFont
/*    */ {
/*    */   private String textComponentFontName;
/*    */   
/*    */   public WFontPeer(String paramString, int paramInt) {
/* 35 */     super(paramString, paramInt);
/* 36 */     if (this.fontConfig != null) {
/* 37 */       this.textComponentFontName = ((WFontConfiguration)this.fontConfig).getTextComponentFontName(this.familyName, paramInt);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected char getMissingGlyphCharacter() {
/* 43 */     return '❑';
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 48 */     initIDs();
/*    */   }
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WFontPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */