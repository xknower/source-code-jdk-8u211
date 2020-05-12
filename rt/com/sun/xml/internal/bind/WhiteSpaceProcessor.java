/*     */ package com.sun.xml.internal.bind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WhiteSpaceProcessor
/*     */ {
/*     */   public static String replace(String text) {
/*  54 */     return replace(text).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence replace(CharSequence text) {
/*  61 */     int i = text.length() - 1;
/*     */ 
/*     */     
/*  64 */     while (i >= 0 && !isWhiteSpaceExceptSpace(text.charAt(i))) {
/*  65 */       i--;
/*     */     }
/*  67 */     if (i < 0)
/*     */     {
/*  69 */       return text;
/*     */     }
/*     */ 
/*     */     
/*  73 */     StringBuilder buf = new StringBuilder(text);
/*     */     
/*  75 */     buf.setCharAt(i--, ' ');
/*  76 */     for (; i >= 0; i--) {
/*  77 */       if (isWhiteSpaceExceptSpace(buf.charAt(i)))
/*  78 */         buf.setCharAt(i, ' '); 
/*     */     } 
/*  80 */     return new String(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence trim(CharSequence text) {
/*  88 */     int len = text.length();
/*  89 */     int start = 0;
/*     */     
/*  91 */     while (start < len && isWhiteSpace(text.charAt(start))) {
/*  92 */       start++;
/*     */     }
/*  94 */     int end = len - 1;
/*     */     
/*  96 */     while (end > start && isWhiteSpace(text.charAt(end))) {
/*  97 */       end--;
/*     */     }
/*  99 */     if (start == 0 && end == len - 1) {
/* 100 */       return text;
/*     */     }
/* 102 */     return text.subSequence(start, end + 1);
/*     */   }
/*     */   
/*     */   public static String collapse(String text) {
/* 106 */     return collapse(text).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence collapse(CharSequence text) {
/* 115 */     int len = text.length();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     int s = 0;
/* 121 */     while (s < len && 
/* 122 */       !isWhiteSpace(text.charAt(s)))
/*     */     {
/* 124 */       s++;
/*     */     }
/* 126 */     if (s == len)
/*     */     {
/* 128 */       return text;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     StringBuilder result = new StringBuilder(len);
/*     */     
/* 135 */     if (s != 0) {
/* 136 */       for (int j = 0; j < s; j++)
/* 137 */         result.append(text.charAt(j)); 
/* 138 */       result.append(' ');
/*     */     } 
/*     */     
/* 141 */     boolean inStripMode = true;
/* 142 */     for (int i = s + 1; i < len; i++) {
/* 143 */       char ch = text.charAt(i);
/* 144 */       boolean b = isWhiteSpace(ch);
/* 145 */       if (!inStripMode || !b) {
/*     */ 
/*     */         
/* 148 */         inStripMode = b;
/* 149 */         if (inStripMode) {
/* 150 */           result.append(' ');
/*     */         } else {
/* 152 */           result.append(ch);
/*     */         } 
/*     */       } 
/*     */     } 
/* 156 */     len = result.length();
/* 157 */     if (len > 0 && result.charAt(len - 1) == ' ') {
/* 158 */       result.setLength(len - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 163 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWhiteSpace(CharSequence s) {
/* 170 */     for (int i = s.length() - 1; i >= 0; i--) {
/* 171 */       if (!isWhiteSpace(s.charAt(i)))
/* 172 */         return false; 
/* 173 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWhiteSpace(char ch) {
/* 180 */     if (ch > ' ') return false;
/*     */ 
/*     */     
/* 183 */     return (ch == '\t' || ch == '\n' || ch == '\r' || ch == ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isWhiteSpaceExceptSpace(char ch) {
/* 193 */     if (ch >= ' ') return false;
/*     */ 
/*     */     
/* 196 */     return (ch == '\t' || ch == '\n' || ch == '\r');
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\WhiteSpaceProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */