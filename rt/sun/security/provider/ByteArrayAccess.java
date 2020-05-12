/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.AccessController;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ByteArrayAccess
/*     */ {
/*  62 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean littleEndianUnaligned;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean bigEndian;
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final int byteArrayOfs = unsafe.arrayBaseOffset(byte[].class);
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  79 */     boolean bool = (unsafe.arrayIndexScale(byte[].class) == 1 && unsafe.arrayIndexScale(int[].class) == 4 && unsafe.arrayIndexScale(long[].class) == 8 && (byteArrayOfs & 0x3) == 0) ? true : false;
/*     */ 
/*     */     
/*  82 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*     */     
/*  84 */     littleEndianUnaligned = (bool && unaligned() && byteOrder == ByteOrder.LITTLE_ENDIAN);
/*  85 */     bigEndian = (bool && byteOrder == ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean unaligned() {
/*  95 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.arch", ""));
/*  96 */     return (str.equals("i386") || str.equals("x86") || str.equals("amd64") || str
/*  97 */       .equals("x86_64") || str.equals("ppc64") || str.equals("ppc64le"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2iLittle(byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3) {
/* 104 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOfint.length - paramInt2 < paramInt3 / 4)
/*     */     {
/* 106 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 108 */     if (littleEndianUnaligned) {
/* 109 */       paramInt1 += byteArrayOfs;
/* 110 */       paramInt3 += paramInt1;
/* 111 */       while (paramInt1 < paramInt3) {
/* 112 */         paramArrayOfint[paramInt2++] = unsafe.getInt(paramArrayOfbyte, paramInt1);
/* 113 */         paramInt1 += 4;
/*     */       } 
/* 115 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/* 116 */       paramInt1 += byteArrayOfs;
/* 117 */       paramInt3 += paramInt1;
/* 118 */       while (paramInt1 < paramInt3) {
/* 119 */         paramArrayOfint[paramInt2++] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt1));
/* 120 */         paramInt1 += 4;
/*     */       } 
/*     */     } else {
/* 123 */       paramInt3 += paramInt1;
/* 124 */       while (paramInt1 < paramInt3) {
/* 125 */         paramArrayOfint[paramInt2++] = paramArrayOfbyte[paramInt1] & 0xFF | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 16 | paramArrayOfbyte[paramInt1 + 3] << 24;
/*     */ 
/*     */ 
/*     */         
/* 129 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2iLittle64(byte[] paramArrayOfbyte, int paramInt, int[] paramArrayOfint) {
/* 136 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 64 || paramArrayOfint.length < 16)
/*     */     {
/* 138 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 140 */     if (littleEndianUnaligned) {
/* 141 */       paramInt += byteArrayOfs;
/* 142 */       paramArrayOfint[0] = unsafe.getInt(paramArrayOfbyte, paramInt);
/* 143 */       paramArrayOfint[1] = unsafe.getInt(paramArrayOfbyte, (paramInt + 4));
/* 144 */       paramArrayOfint[2] = unsafe.getInt(paramArrayOfbyte, (paramInt + 8));
/* 145 */       paramArrayOfint[3] = unsafe.getInt(paramArrayOfbyte, (paramInt + 12));
/* 146 */       paramArrayOfint[4] = unsafe.getInt(paramArrayOfbyte, (paramInt + 16));
/* 147 */       paramArrayOfint[5] = unsafe.getInt(paramArrayOfbyte, (paramInt + 20));
/* 148 */       paramArrayOfint[6] = unsafe.getInt(paramArrayOfbyte, (paramInt + 24));
/* 149 */       paramArrayOfint[7] = unsafe.getInt(paramArrayOfbyte, (paramInt + 28));
/* 150 */       paramArrayOfint[8] = unsafe.getInt(paramArrayOfbyte, (paramInt + 32));
/* 151 */       paramArrayOfint[9] = unsafe.getInt(paramArrayOfbyte, (paramInt + 36));
/* 152 */       paramArrayOfint[10] = unsafe.getInt(paramArrayOfbyte, (paramInt + 40));
/* 153 */       paramArrayOfint[11] = unsafe.getInt(paramArrayOfbyte, (paramInt + 44));
/* 154 */       paramArrayOfint[12] = unsafe.getInt(paramArrayOfbyte, (paramInt + 48));
/* 155 */       paramArrayOfint[13] = unsafe.getInt(paramArrayOfbyte, (paramInt + 52));
/* 156 */       paramArrayOfint[14] = unsafe.getInt(paramArrayOfbyte, (paramInt + 56));
/* 157 */       paramArrayOfint[15] = unsafe.getInt(paramArrayOfbyte, (paramInt + 60));
/* 158 */     } else if (bigEndian && (paramInt & 0x3) == 0) {
/* 159 */       paramInt += byteArrayOfs;
/* 160 */       paramArrayOfint[0] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt));
/* 161 */       paramArrayOfint[1] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 4)));
/* 162 */       paramArrayOfint[2] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 8)));
/* 163 */       paramArrayOfint[3] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 12)));
/* 164 */       paramArrayOfint[4] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 16)));
/* 165 */       paramArrayOfint[5] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 20)));
/* 166 */       paramArrayOfint[6] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 24)));
/* 167 */       paramArrayOfint[7] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 28)));
/* 168 */       paramArrayOfint[8] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 32)));
/* 169 */       paramArrayOfint[9] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 36)));
/* 170 */       paramArrayOfint[10] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 40)));
/* 171 */       paramArrayOfint[11] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 44)));
/* 172 */       paramArrayOfint[12] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 48)));
/* 173 */       paramArrayOfint[13] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 52)));
/* 174 */       paramArrayOfint[14] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 56)));
/* 175 */       paramArrayOfint[15] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 60)));
/*     */     } else {
/* 177 */       b2iLittle(paramArrayOfbyte, paramInt, paramArrayOfint, 0, 64);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void i2bLittle(int[] paramArrayOfint, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 185 */     if (paramInt1 < 0 || paramArrayOfint.length - paramInt1 < paramInt3 / 4 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 187 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 189 */     if (littleEndianUnaligned) {
/* 190 */       paramInt2 += byteArrayOfs;
/* 191 */       paramInt3 += paramInt2;
/* 192 */       while (paramInt2 < paramInt3) {
/* 193 */         unsafe.putInt(paramArrayOfbyte, paramInt2, paramArrayOfint[paramInt1++]);
/* 194 */         paramInt2 += 4;
/*     */       } 
/* 196 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 197 */       paramInt2 += byteArrayOfs;
/* 198 */       paramInt3 += paramInt2;
/* 199 */       while (paramInt2 < paramInt3) {
/* 200 */         unsafe.putInt(paramArrayOfbyte, paramInt2, Integer.reverseBytes(paramArrayOfint[paramInt1++]));
/* 201 */         paramInt2 += 4;
/*     */       } 
/*     */     } else {
/* 204 */       paramInt3 += paramInt2;
/* 205 */       while (paramInt2 < paramInt3) {
/* 206 */         int i = paramArrayOfint[paramInt1++];
/* 207 */         paramArrayOfbyte[paramInt2++] = (byte)i;
/* 208 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 8);
/* 209 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 16);
/* 210 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 24);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void i2bLittle4(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 217 */     if (paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < 4) {
/* 218 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 220 */     if (littleEndianUnaligned) {
/* 221 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), paramInt1);
/* 222 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 223 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), Integer.reverseBytes(paramInt1));
/*     */     } else {
/* 225 */       paramArrayOfbyte[paramInt2] = (byte)paramInt1;
/* 226 */       paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 8);
/* 227 */       paramArrayOfbyte[paramInt2 + 2] = (byte)(paramInt1 >> 16);
/* 228 */       paramArrayOfbyte[paramInt2 + 3] = (byte)(paramInt1 >> 24);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2iBig(byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3) {
/* 236 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOfint.length - paramInt2 < paramInt3 / 4)
/*     */     {
/* 238 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 240 */     if (littleEndianUnaligned) {
/* 241 */       paramInt1 += byteArrayOfs;
/* 242 */       paramInt3 += paramInt1;
/* 243 */       while (paramInt1 < paramInt3) {
/* 244 */         paramArrayOfint[paramInt2++] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt1));
/* 245 */         paramInt1 += 4;
/*     */       } 
/* 247 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/* 248 */       paramInt1 += byteArrayOfs;
/* 249 */       paramInt3 += paramInt1;
/* 250 */       while (paramInt1 < paramInt3) {
/* 251 */         paramArrayOfint[paramInt2++] = unsafe.getInt(paramArrayOfbyte, paramInt1);
/* 252 */         paramInt1 += 4;
/*     */       } 
/*     */     } else {
/* 255 */       paramInt3 += paramInt1;
/* 256 */       while (paramInt1 < paramInt3) {
/* 257 */         paramArrayOfint[paramInt2++] = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 261 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2iBig64(byte[] paramArrayOfbyte, int paramInt, int[] paramArrayOfint) {
/* 268 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 64 || paramArrayOfint.length < 16)
/*     */     {
/* 270 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 272 */     if (littleEndianUnaligned) {
/* 273 */       paramInt += byteArrayOfs;
/* 274 */       paramArrayOfint[0] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt));
/* 275 */       paramArrayOfint[1] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 4)));
/* 276 */       paramArrayOfint[2] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 8)));
/* 277 */       paramArrayOfint[3] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 12)));
/* 278 */       paramArrayOfint[4] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 16)));
/* 279 */       paramArrayOfint[5] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 20)));
/* 280 */       paramArrayOfint[6] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 24)));
/* 281 */       paramArrayOfint[7] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 28)));
/* 282 */       paramArrayOfint[8] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 32)));
/* 283 */       paramArrayOfint[9] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 36)));
/* 284 */       paramArrayOfint[10] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 40)));
/* 285 */       paramArrayOfint[11] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 44)));
/* 286 */       paramArrayOfint[12] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 48)));
/* 287 */       paramArrayOfint[13] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 52)));
/* 288 */       paramArrayOfint[14] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 56)));
/* 289 */       paramArrayOfint[15] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 60)));
/* 290 */     } else if (bigEndian && (paramInt & 0x3) == 0) {
/* 291 */       paramInt += byteArrayOfs;
/* 292 */       paramArrayOfint[0] = unsafe.getInt(paramArrayOfbyte, paramInt);
/* 293 */       paramArrayOfint[1] = unsafe.getInt(paramArrayOfbyte, (paramInt + 4));
/* 294 */       paramArrayOfint[2] = unsafe.getInt(paramArrayOfbyte, (paramInt + 8));
/* 295 */       paramArrayOfint[3] = unsafe.getInt(paramArrayOfbyte, (paramInt + 12));
/* 296 */       paramArrayOfint[4] = unsafe.getInt(paramArrayOfbyte, (paramInt + 16));
/* 297 */       paramArrayOfint[5] = unsafe.getInt(paramArrayOfbyte, (paramInt + 20));
/* 298 */       paramArrayOfint[6] = unsafe.getInt(paramArrayOfbyte, (paramInt + 24));
/* 299 */       paramArrayOfint[7] = unsafe.getInt(paramArrayOfbyte, (paramInt + 28));
/* 300 */       paramArrayOfint[8] = unsafe.getInt(paramArrayOfbyte, (paramInt + 32));
/* 301 */       paramArrayOfint[9] = unsafe.getInt(paramArrayOfbyte, (paramInt + 36));
/* 302 */       paramArrayOfint[10] = unsafe.getInt(paramArrayOfbyte, (paramInt + 40));
/* 303 */       paramArrayOfint[11] = unsafe.getInt(paramArrayOfbyte, (paramInt + 44));
/* 304 */       paramArrayOfint[12] = unsafe.getInt(paramArrayOfbyte, (paramInt + 48));
/* 305 */       paramArrayOfint[13] = unsafe.getInt(paramArrayOfbyte, (paramInt + 52));
/* 306 */       paramArrayOfint[14] = unsafe.getInt(paramArrayOfbyte, (paramInt + 56));
/* 307 */       paramArrayOfint[15] = unsafe.getInt(paramArrayOfbyte, (paramInt + 60));
/*     */     } else {
/* 309 */       b2iBig(paramArrayOfbyte, paramInt, paramArrayOfint, 0, 64);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void i2bBig(int[] paramArrayOfint, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 317 */     if (paramInt1 < 0 || paramArrayOfint.length - paramInt1 < paramInt3 / 4 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 319 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 321 */     if (littleEndianUnaligned) {
/* 322 */       paramInt2 += byteArrayOfs;
/* 323 */       paramInt3 += paramInt2;
/* 324 */       while (paramInt2 < paramInt3) {
/* 325 */         unsafe.putInt(paramArrayOfbyte, paramInt2, Integer.reverseBytes(paramArrayOfint[paramInt1++]));
/* 326 */         paramInt2 += 4;
/*     */       } 
/* 328 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 329 */       paramInt2 += byteArrayOfs;
/* 330 */       paramInt3 += paramInt2;
/* 331 */       while (paramInt2 < paramInt3) {
/* 332 */         unsafe.putInt(paramArrayOfbyte, paramInt2, paramArrayOfint[paramInt1++]);
/* 333 */         paramInt2 += 4;
/*     */       } 
/*     */     } else {
/* 336 */       paramInt3 += paramInt2;
/* 337 */       while (paramInt2 < paramInt3) {
/* 338 */         int i = paramArrayOfint[paramInt1++];
/* 339 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 24);
/* 340 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 16);
/* 341 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 8);
/* 342 */         paramArrayOfbyte[paramInt2++] = (byte)i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void i2bBig4(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 349 */     if (paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < 4) {
/* 350 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 352 */     if (littleEndianUnaligned) {
/* 353 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), Integer.reverseBytes(paramInt1));
/* 354 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 355 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), paramInt1);
/*     */     } else {
/* 357 */       paramArrayOfbyte[paramInt2] = (byte)(paramInt1 >> 24);
/* 358 */       paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 16);
/* 359 */       paramArrayOfbyte[paramInt2 + 2] = (byte)(paramInt1 >> 8);
/* 360 */       paramArrayOfbyte[paramInt2 + 3] = (byte)paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2lBig(byte[] paramArrayOfbyte, int paramInt1, long[] paramArrayOflong, int paramInt2, int paramInt3) {
/* 368 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOflong.length - paramInt2 < paramInt3 / 8)
/*     */     {
/* 370 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 372 */     if (littleEndianUnaligned) {
/* 373 */       paramInt1 += byteArrayOfs;
/* 374 */       paramInt3 += paramInt1;
/* 375 */       while (paramInt1 < paramInt3) {
/* 376 */         paramArrayOflong[paramInt2++] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, paramInt1));
/* 377 */         paramInt1 += 8;
/*     */       } 
/* 379 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 385 */       paramInt1 += byteArrayOfs;
/* 386 */       paramInt3 += paramInt1;
/* 387 */       while (paramInt1 < paramInt3) {
/* 388 */         paramArrayOflong[paramInt2++] = unsafe
/* 389 */           .getInt(paramArrayOfbyte, paramInt1) << 32L | unsafe
/* 390 */           .getInt(paramArrayOfbyte, (paramInt1 + 4)) & 0xFFFFFFFFL;
/* 391 */         paramInt1 += 8;
/*     */       } 
/*     */     } else {
/* 394 */       paramInt3 += paramInt1;
/* 395 */       while (paramInt1 < paramInt3) {
/* 396 */         int i = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 400 */         paramInt1 += 4;
/* 401 */         int j = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 405 */         paramArrayOflong[paramInt2++] = i << 32L | j & 0xFFFFFFFFL;
/* 406 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2lBig128(byte[] paramArrayOfbyte, int paramInt, long[] paramArrayOflong) {
/* 413 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 128 || paramArrayOflong.length < 16)
/*     */     {
/* 415 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 417 */     if (littleEndianUnaligned) {
/* 418 */       paramInt += byteArrayOfs;
/* 419 */       paramArrayOflong[0] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, paramInt));
/* 420 */       paramArrayOflong[1] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 8)));
/* 421 */       paramArrayOflong[2] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 16)));
/* 422 */       paramArrayOflong[3] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 24)));
/* 423 */       paramArrayOflong[4] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 32)));
/* 424 */       paramArrayOflong[5] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 40)));
/* 425 */       paramArrayOflong[6] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 48)));
/* 426 */       paramArrayOflong[7] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 56)));
/* 427 */       paramArrayOflong[8] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 64)));
/* 428 */       paramArrayOflong[9] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 72)));
/* 429 */       paramArrayOflong[10] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 80)));
/* 430 */       paramArrayOflong[11] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 88)));
/* 431 */       paramArrayOflong[12] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 96)));
/* 432 */       paramArrayOflong[13] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 104)));
/* 433 */       paramArrayOflong[14] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 112)));
/* 434 */       paramArrayOflong[15] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 120)));
/*     */     } else {
/*     */       
/* 437 */       b2lBig(paramArrayOfbyte, paramInt, paramArrayOflong, 0, 128);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void l2bBig(long[] paramArrayOflong, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 445 */     if (paramInt1 < 0 || paramArrayOflong.length - paramInt1 < paramInt3 / 8 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 447 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 449 */     paramInt3 += paramInt2;
/* 450 */     while (paramInt2 < paramInt3) {
/* 451 */       long l = paramArrayOflong[paramInt1++];
/* 452 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 56L);
/* 453 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 48L);
/* 454 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 40L);
/* 455 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 32L);
/* 456 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 24L);
/* 457 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 16L);
/* 458 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 8L);
/* 459 */       paramArrayOfbyte[paramInt2++] = (byte)(int)l;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\ByteArrayAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */