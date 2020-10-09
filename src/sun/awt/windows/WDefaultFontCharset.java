/*    */ package sun.awt.windows;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ import sun.awt.AWTCharset;
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
/*    */ final class WDefaultFontCharset
/*    */   extends AWTCharset
/*    */ {
/*    */   private String fontName;
/*    */   
/*    */   static {
/* 33 */     initIDs();
/*    */   }
/*    */ 
/*    */   
/*    */   private synchronized native boolean canConvert(char paramChar);
/*    */   
/*    */   WDefaultFontCharset(String paramString) {
/* 40 */     super("WDefaultFontCharset", Charset.forName("windows-1252"));
/* 41 */     this.fontName = paramString;
/*    */   }
/*    */   private static native void initIDs();
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 46 */     return new Encoder();
/*    */   }
/*    */   
/*    */   private class Encoder
/*    */     extends AWTCharset.Encoder {
/*    */     public boolean canEncode(char param1Char) {
/* 52 */       return WDefaultFontCharset.this.canConvert(param1Char);
/*    */     }
/*    */     
/*    */     private Encoder() {}
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WDefaultFontCharset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */