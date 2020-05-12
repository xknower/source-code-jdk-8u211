/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  52 */     if (b == null) {
/*  53 */       throw new NumberFormatException("null");
/*     */     }
/*  55 */     int result = 0;
/*  56 */     boolean negative = false;
/*  57 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (end > start) {
/*  63 */       int limit; if (b[i] == 45) {
/*  64 */         negative = true;
/*  65 */         limit = Integer.MIN_VALUE;
/*  66 */         i++;
/*     */       } else {
/*  68 */         limit = -2147483647;
/*     */       } 
/*  70 */       int multmin = limit / radix;
/*  71 */       if (i < end) {
/*  72 */         int digit = Character.digit((char)b[i++], radix);
/*  73 */         if (digit < 0) {
/*  74 */           throw new NumberFormatException("illegal number: " + 
/*  75 */               toString(b, start, end));
/*     */         }
/*     */         
/*  78 */         result = -digit;
/*     */       } 
/*     */       
/*  81 */       while (i < end) {
/*     */         
/*  83 */         int digit = Character.digit((char)b[i++], radix);
/*  84 */         if (digit < 0) {
/*  85 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  87 */         if (result < multmin) {
/*  88 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  90 */         result *= radix;
/*  91 */         if (result < limit + digit) {
/*  92 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  94 */         result -= digit;
/*     */       } 
/*     */     } else {
/*  97 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*  99 */     if (negative) {
/* 100 */       if (i > start + 1) {
/* 101 */         return result;
/*     */       }
/* 103 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/* 106 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 116 */     int size = end - start;
/* 117 */     char[] theChars = new char[size];
/*     */     
/* 119 */     for (int i = 0, j = start; i < size;) {
/* 120 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/* 122 */     return new String(theChars);
/*     */   }
/*     */   
/*     */   public static byte[] getBytes(String s) {
/* 126 */     char[] chars = s.toCharArray();
/* 127 */     int size = chars.length;
/* 128 */     byte[] bytes = new byte[size];
/*     */     
/* 130 */     for (int i = 0; i < size;)
/* 131 */       bytes[i] = (byte)chars[i++]; 
/* 132 */     return bytes;
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
/*     */   public static byte[] getBytes(InputStream is) throws IOException {
/* 144 */     ByteOutputStream bos = new ByteOutputStream();
/*     */     try {
/* 146 */       bos.write(is);
/*     */     } finally {
/* 148 */       is.close();
/*     */     } 
/* 150 */     return bos.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\messaging\saaj\packaging\mim\\util\ASCIIUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */