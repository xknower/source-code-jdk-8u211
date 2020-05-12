/*     */ package sun.net.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPAddressUtil
/*     */ {
/*     */   private static final int INADDR4SZ = 4;
/*     */   private static final int INADDR16SZ = 16;
/*     */   private static final int INT16SZ = 2;
/*     */   
/*     */   public static byte[] textToNumericFormatV4(String paramString) {
/*  43 */     byte[] arrayOfByte = new byte[4];
/*     */     
/*  45 */     long l = 0L;
/*  46 */     byte b1 = 0;
/*  47 */     boolean bool = true;
/*     */     
/*  49 */     int i = paramString.length();
/*  50 */     if (i == 0 || i > 15) {
/*  51 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     for (byte b2 = 0; b2 < i; b2++) {
/*  79 */       char c = paramString.charAt(b2);
/*  80 */       if (c == '.') {
/*  81 */         if (bool || l < 0L || l > 255L || b1 == 3) {
/*  82 */           return null;
/*     */         }
/*  84 */         arrayOfByte[b1++] = (byte)(int)(l & 0xFFL);
/*  85 */         l = 0L;
/*  86 */         bool = true;
/*     */       } else {
/*  88 */         int j = Character.digit(c, 10);
/*  89 */         if (j < 0) {
/*  90 */           return null;
/*     */         }
/*  92 */         l *= 10L;
/*  93 */         l += j;
/*  94 */         bool = false;
/*     */       } 
/*     */     } 
/*  97 */     if (bool || l < 0L || l >= 1L << (4 - b1) * 8) {
/*  98 */       return null;
/*     */     }
/* 100 */     switch (b1) {
/*     */       case 0:
/* 102 */         arrayOfByte[0] = (byte)(int)(l >> 24L & 0xFFL);
/*     */       case 1:
/* 104 */         arrayOfByte[1] = (byte)(int)(l >> 16L & 0xFFL);
/*     */       case 2:
/* 106 */         arrayOfByte[2] = (byte)(int)(l >> 8L & 0xFFL);
/*     */       case 3:
/* 108 */         arrayOfByte[3] = (byte)(int)(l >> 0L & 0xFFL); break;
/*     */     } 
/* 110 */     return arrayOfByte;
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
/*     */   public static byte[] textToNumericFormatV6(String paramString) {
/* 126 */     if (paramString.length() < 2) {
/* 127 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     char[] arrayOfChar = paramString.toCharArray();
/* 135 */     byte[] arrayOfByte1 = new byte[16];
/*     */     
/* 137 */     int j = arrayOfChar.length;
/* 138 */     int k = paramString.indexOf("%");
/* 139 */     if (k == j - 1) {
/* 140 */       return null;
/*     */     }
/*     */     
/* 143 */     if (k != -1) {
/* 144 */       j = k;
/*     */     }
/*     */     
/* 147 */     byte b = -1;
/* 148 */     byte b1 = 0, b2 = 0;
/*     */     
/* 150 */     if (arrayOfChar[b1] == ':' && 
/* 151 */       arrayOfChar[++b1] != ':')
/* 152 */       return null; 
/* 153 */     byte b3 = b1;
/* 154 */     boolean bool = false;
/* 155 */     int i = 0;
/* 156 */     while (b1 < j) {
/* 157 */       char c = arrayOfChar[b1++];
/* 158 */       int m = Character.digit(c, 16);
/* 159 */       if (m != -1) {
/* 160 */         i <<= 4;
/* 161 */         i |= m;
/* 162 */         if (i > 65535)
/* 163 */           return null; 
/* 164 */         bool = true;
/*     */         continue;
/*     */       } 
/* 167 */       if (c == ':') {
/* 168 */         b3 = b1;
/* 169 */         if (!bool) {
/* 170 */           if (b != -1)
/* 171 */             return null; 
/* 172 */           b = b2; continue;
/*     */         } 
/* 174 */         if (b1 == j) {
/* 175 */           return null;
/*     */         }
/* 177 */         if (b2 + 2 > 16)
/* 178 */           return null; 
/* 179 */         arrayOfByte1[b2++] = (byte)(i >> 8 & 0xFF);
/* 180 */         arrayOfByte1[b2++] = (byte)(i & 0xFF);
/* 181 */         bool = false;
/* 182 */         i = 0;
/*     */         continue;
/*     */       } 
/* 185 */       if (c == '.' && b2 + 4 <= 16) {
/* 186 */         String str = paramString.substring(b3, j);
/*     */         
/* 188 */         byte b4 = 0; int n = 0;
/* 189 */         while ((n = str.indexOf('.', n)) != -1) {
/* 190 */           b4++;
/* 191 */           n++;
/*     */         } 
/* 193 */         if (b4 != 3) {
/* 194 */           return null;
/*     */         }
/* 196 */         byte[] arrayOfByte = textToNumericFormatV4(str);
/* 197 */         if (arrayOfByte == null) {
/* 198 */           return null;
/*     */         }
/* 200 */         for (byte b5 = 0; b5 < 4; b5++) {
/* 201 */           arrayOfByte1[b2++] = arrayOfByte[b5];
/*     */         }
/* 203 */         bool = false;
/*     */         break;
/*     */       } 
/* 206 */       return null;
/*     */     } 
/* 208 */     if (bool) {
/* 209 */       if (b2 + 2 > 16)
/* 210 */         return null; 
/* 211 */       arrayOfByte1[b2++] = (byte)(i >> 8 & 0xFF);
/* 212 */       arrayOfByte1[b2++] = (byte)(i & 0xFF);
/*     */     } 
/*     */     
/* 215 */     if (b != -1) {
/* 216 */       int m = b2 - b;
/*     */       
/* 218 */       if (b2 == 16)
/* 219 */         return null; 
/* 220 */       for (b1 = 1; b1 <= m; b1++) {
/* 221 */         arrayOfByte1[16 - b1] = arrayOfByte1[b + m - b1];
/* 222 */         arrayOfByte1[b + m - b1] = 0;
/*     */       } 
/* 224 */       b2 = 16;
/*     */     } 
/* 226 */     if (b2 != 16)
/* 227 */       return null; 
/* 228 */     byte[] arrayOfByte2 = convertFromIPv4MappedAddress(arrayOfByte1);
/* 229 */     if (arrayOfByte2 != null) {
/* 230 */       return arrayOfByte2;
/*     */     }
/* 232 */     return arrayOfByte1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIPv4LiteralAddress(String paramString) {
/* 241 */     return (textToNumericFormatV4(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIPv6LiteralAddress(String paramString) {
/* 249 */     return (textToNumericFormatV6(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] convertFromIPv4MappedAddress(byte[] paramArrayOfbyte) {
/* 260 */     if (isIPv4MappedAddress(paramArrayOfbyte)) {
/* 261 */       byte[] arrayOfByte = new byte[4];
/* 262 */       System.arraycopy(paramArrayOfbyte, 12, arrayOfByte, 0, 4);
/* 263 */       return arrayOfByte;
/*     */     } 
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIPv4MappedAddress(byte[] paramArrayOfbyte) {
/* 276 */     if (paramArrayOfbyte.length < 16) {
/* 277 */       return false;
/*     */     }
/* 279 */     if (paramArrayOfbyte[0] == 0 && paramArrayOfbyte[1] == 0 && paramArrayOfbyte[2] == 0 && paramArrayOfbyte[3] == 0 && paramArrayOfbyte[4] == 0 && paramArrayOfbyte[5] == 0 && paramArrayOfbyte[6] == 0 && paramArrayOfbyte[7] == 0 && paramArrayOfbyte[8] == 0 && paramArrayOfbyte[9] == 0 && paramArrayOfbyte[10] == -1 && paramArrayOfbyte[11] == -1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       return true;
/*     */     }
/* 288 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\ne\\util\IPAddressUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */