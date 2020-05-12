/*    */ package javax.xml.bind.annotation.adapters;
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
/*    */ public final class NormalizedStringAdapter
/*    */   extends XmlAdapter<String, String>
/*    */ {
/*    */   public String unmarshal(String text) {
/* 46 */     if (text == null) return null;
/*    */     
/* 48 */     int i = text.length() - 1;
/*    */ 
/*    */     
/* 51 */     while (i >= 0 && !isWhiteSpaceExceptSpace(text.charAt(i))) {
/* 52 */       i--;
/*    */     }
/* 54 */     if (i < 0)
/*    */     {
/* 56 */       return text;
/*    */     }
/*    */ 
/*    */     
/* 60 */     char[] buf = text.toCharArray();
/*    */     
/* 62 */     buf[i--] = ' ';
/* 63 */     for (; i >= 0; i--) {
/* 64 */       if (isWhiteSpaceExceptSpace(buf[i]))
/* 65 */         buf[i] = ' '; 
/*    */     } 
/* 67 */     return new String(buf);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String marshal(String s) {
/* 76 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static boolean isWhiteSpaceExceptSpace(char ch) {
/* 87 */     if (ch >= ' ') return false;
/*    */ 
/*    */     
/* 90 */     return (ch == '\t' || ch == '\n' || ch == '\r');
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\bind\annotation\adapters\NormalizedStringAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */