/*      */ package java.nio;
/*      */ 
/*      */ import java.security.AccessController;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import sun.misc.JavaLangRefAccess;
/*      */ import sun.misc.JavaNioAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Bits
/*      */ {
/*      */   static short swap(short paramShort) {
/*   49 */     return Short.reverseBytes(paramShort);
/*      */   }
/*      */   
/*      */   static char swap(char paramChar) {
/*   53 */     return Character.reverseBytes(paramChar);
/*      */   }
/*      */   
/*      */   static int swap(int paramInt) {
/*   57 */     return Integer.reverseBytes(paramInt);
/*      */   }
/*      */   
/*      */   static long swap(long paramLong) {
/*   61 */     return Long.reverseBytes(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static char makeChar(byte paramByte1, byte paramByte2) {
/*   68 */     return (char)(paramByte1 << 8 | paramByte2 & 0xFF);
/*      */   }
/*      */   
/*      */   static char getCharL(ByteBuffer paramByteBuffer, int paramInt) {
/*   72 */     return makeChar(paramByteBuffer._get(paramInt + 1), paramByteBuffer
/*   73 */         ._get(paramInt));
/*      */   }
/*      */   
/*      */   static char getCharL(long paramLong) {
/*   77 */     return makeChar(_get(paramLong + 1L), 
/*   78 */         _get(paramLong));
/*      */   }
/*      */   
/*      */   static char getCharB(ByteBuffer paramByteBuffer, int paramInt) {
/*   82 */     return makeChar(paramByteBuffer._get(paramInt), paramByteBuffer
/*   83 */         ._get(paramInt + 1));
/*      */   }
/*      */   
/*      */   static char getCharB(long paramLong) {
/*   87 */     return makeChar(_get(paramLong), 
/*   88 */         _get(paramLong + 1L));
/*      */   }
/*      */   
/*      */   static char getChar(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*   92 */     return paramBoolean ? getCharB(paramByteBuffer, paramInt) : getCharL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static char getChar(long paramLong, boolean paramBoolean) {
/*   96 */     return paramBoolean ? getCharB(paramLong) : getCharL(paramLong);
/*      */   }
/*      */   
/*   99 */   private static byte char1(char paramChar) { return (byte)(paramChar >> 8); } private static byte char0(char paramChar) {
/*  100 */     return (byte)paramChar;
/*      */   }
/*      */   static void putCharL(ByteBuffer paramByteBuffer, int paramInt, char paramChar) {
/*  103 */     paramByteBuffer._put(paramInt, char0(paramChar));
/*  104 */     paramByteBuffer._put(paramInt + 1, char1(paramChar));
/*      */   }
/*      */   
/*      */   static void putCharL(long paramLong, char paramChar) {
/*  108 */     _put(paramLong, char0(paramChar));
/*  109 */     _put(paramLong + 1L, char1(paramChar));
/*      */   }
/*      */   
/*      */   static void putCharB(ByteBuffer paramByteBuffer, int paramInt, char paramChar) {
/*  113 */     paramByteBuffer._put(paramInt, char1(paramChar));
/*  114 */     paramByteBuffer._put(paramInt + 1, char0(paramChar));
/*      */   }
/*      */   
/*      */   static void putCharB(long paramLong, char paramChar) {
/*  118 */     _put(paramLong, char1(paramChar));
/*  119 */     _put(paramLong + 1L, char0(paramChar));
/*      */   }
/*      */   
/*      */   static void putChar(ByteBuffer paramByteBuffer, int paramInt, char paramChar, boolean paramBoolean) {
/*  123 */     if (paramBoolean) {
/*  124 */       putCharB(paramByteBuffer, paramInt, paramChar);
/*      */     } else {
/*  126 */       putCharL(paramByteBuffer, paramInt, paramChar);
/*      */     } 
/*      */   }
/*      */   static void putChar(long paramLong, char paramChar, boolean paramBoolean) {
/*  130 */     if (paramBoolean) {
/*  131 */       putCharB(paramLong, paramChar);
/*      */     } else {
/*  133 */       putCharL(paramLong, paramChar);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static short makeShort(byte paramByte1, byte paramByte2) {
/*  140 */     return (short)(paramByte1 << 8 | paramByte2 & 0xFF);
/*      */   }
/*      */   
/*      */   static short getShortL(ByteBuffer paramByteBuffer, int paramInt) {
/*  144 */     return makeShort(paramByteBuffer._get(paramInt + 1), paramByteBuffer
/*  145 */         ._get(paramInt));
/*      */   }
/*      */   
/*      */   static short getShortL(long paramLong) {
/*  149 */     return makeShort(_get(paramLong + 1L), 
/*  150 */         _get(paramLong));
/*      */   }
/*      */   
/*      */   static short getShortB(ByteBuffer paramByteBuffer, int paramInt) {
/*  154 */     return makeShort(paramByteBuffer._get(paramInt), paramByteBuffer
/*  155 */         ._get(paramInt + 1));
/*      */   }
/*      */   
/*      */   static short getShortB(long paramLong) {
/*  159 */     return makeShort(_get(paramLong), 
/*  160 */         _get(paramLong + 1L));
/*      */   }
/*      */   
/*      */   static short getShort(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  164 */     return paramBoolean ? getShortB(paramByteBuffer, paramInt) : getShortL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static short getShort(long paramLong, boolean paramBoolean) {
/*  168 */     return paramBoolean ? getShortB(paramLong) : getShortL(paramLong);
/*      */   }
/*      */   
/*  171 */   private static byte short1(short paramShort) { return (byte)(paramShort >> 8); } private static byte short0(short paramShort) {
/*  172 */     return (byte)paramShort;
/*      */   }
/*      */   static void putShortL(ByteBuffer paramByteBuffer, int paramInt, short paramShort) {
/*  175 */     paramByteBuffer._put(paramInt, short0(paramShort));
/*  176 */     paramByteBuffer._put(paramInt + 1, short1(paramShort));
/*      */   }
/*      */   
/*      */   static void putShortL(long paramLong, short paramShort) {
/*  180 */     _put(paramLong, short0(paramShort));
/*  181 */     _put(paramLong + 1L, short1(paramShort));
/*      */   }
/*      */   
/*      */   static void putShortB(ByteBuffer paramByteBuffer, int paramInt, short paramShort) {
/*  185 */     paramByteBuffer._put(paramInt, short1(paramShort));
/*  186 */     paramByteBuffer._put(paramInt + 1, short0(paramShort));
/*      */   }
/*      */   
/*      */   static void putShortB(long paramLong, short paramShort) {
/*  190 */     _put(paramLong, short1(paramShort));
/*  191 */     _put(paramLong + 1L, short0(paramShort));
/*      */   }
/*      */   
/*      */   static void putShort(ByteBuffer paramByteBuffer, int paramInt, short paramShort, boolean paramBoolean) {
/*  195 */     if (paramBoolean) {
/*  196 */       putShortB(paramByteBuffer, paramInt, paramShort);
/*      */     } else {
/*  198 */       putShortL(paramByteBuffer, paramInt, paramShort);
/*      */     } 
/*      */   }
/*      */   static void putShort(long paramLong, short paramShort, boolean paramBoolean) {
/*  202 */     if (paramBoolean) {
/*  203 */       putShortB(paramLong, paramShort);
/*      */     } else {
/*  205 */       putShortL(paramLong, paramShort);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int makeInt(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/*  212 */     return paramByte1 << 24 | (paramByte2 & 0xFF) << 16 | (paramByte3 & 0xFF) << 8 | paramByte4 & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getIntL(ByteBuffer paramByteBuffer, int paramInt) {
/*  219 */     return makeInt(paramByteBuffer._get(paramInt + 3), paramByteBuffer
/*  220 */         ._get(paramInt + 2), paramByteBuffer
/*  221 */         ._get(paramInt + 1), paramByteBuffer
/*  222 */         ._get(paramInt));
/*      */   }
/*      */   
/*      */   static int getIntL(long paramLong) {
/*  226 */     return makeInt(_get(paramLong + 3L), 
/*  227 */         _get(paramLong + 2L), 
/*  228 */         _get(paramLong + 1L), 
/*  229 */         _get(paramLong));
/*      */   }
/*      */   
/*      */   static int getIntB(ByteBuffer paramByteBuffer, int paramInt) {
/*  233 */     return makeInt(paramByteBuffer._get(paramInt), paramByteBuffer
/*  234 */         ._get(paramInt + 1), paramByteBuffer
/*  235 */         ._get(paramInt + 2), paramByteBuffer
/*  236 */         ._get(paramInt + 3));
/*      */   }
/*      */   
/*      */   static int getIntB(long paramLong) {
/*  240 */     return makeInt(_get(paramLong), 
/*  241 */         _get(paramLong + 1L), 
/*  242 */         _get(paramLong + 2L), 
/*  243 */         _get(paramLong + 3L));
/*      */   }
/*      */   
/*      */   static int getInt(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  247 */     return paramBoolean ? getIntB(paramByteBuffer, paramInt) : getIntL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static int getInt(long paramLong, boolean paramBoolean) {
/*  251 */     return paramBoolean ? getIntB(paramLong) : getIntL(paramLong);
/*      */   }
/*      */   
/*  254 */   private static byte int3(int paramInt) { return (byte)(paramInt >> 24); }
/*  255 */   private static byte int2(int paramInt) { return (byte)(paramInt >> 16); }
/*  256 */   private static byte int1(int paramInt) { return (byte)(paramInt >> 8); } private static byte int0(int paramInt) {
/*  257 */     return (byte)paramInt;
/*      */   }
/*      */   static void putIntL(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  260 */     paramByteBuffer._put(paramInt1 + 3, int3(paramInt2));
/*  261 */     paramByteBuffer._put(paramInt1 + 2, int2(paramInt2));
/*  262 */     paramByteBuffer._put(paramInt1 + 1, int1(paramInt2));
/*  263 */     paramByteBuffer._put(paramInt1, int0(paramInt2));
/*      */   }
/*      */   
/*      */   static void putIntL(long paramLong, int paramInt) {
/*  267 */     _put(paramLong + 3L, int3(paramInt));
/*  268 */     _put(paramLong + 2L, int2(paramInt));
/*  269 */     _put(paramLong + 1L, int1(paramInt));
/*  270 */     _put(paramLong, int0(paramInt));
/*      */   }
/*      */   
/*      */   static void putIntB(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  274 */     paramByteBuffer._put(paramInt1, int3(paramInt2));
/*  275 */     paramByteBuffer._put(paramInt1 + 1, int2(paramInt2));
/*  276 */     paramByteBuffer._put(paramInt1 + 2, int1(paramInt2));
/*  277 */     paramByteBuffer._put(paramInt1 + 3, int0(paramInt2));
/*      */   }
/*      */   
/*      */   static void putIntB(long paramLong, int paramInt) {
/*  281 */     _put(paramLong, int3(paramInt));
/*  282 */     _put(paramLong + 1L, int2(paramInt));
/*  283 */     _put(paramLong + 2L, int1(paramInt));
/*  284 */     _put(paramLong + 3L, int0(paramInt));
/*      */   }
/*      */   
/*      */   static void putInt(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  288 */     if (paramBoolean) {
/*  289 */       putIntB(paramByteBuffer, paramInt1, paramInt2);
/*      */     } else {
/*  291 */       putIntL(paramByteBuffer, paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */   static void putInt(long paramLong, int paramInt, boolean paramBoolean) {
/*  295 */     if (paramBoolean) {
/*  296 */       putIntB(paramLong, paramInt);
/*      */     } else {
/*  298 */       putIntL(paramLong, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long makeLong(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6, byte paramByte7, byte paramByte8) {
/*  307 */     return paramByte1 << 56L | (paramByte2 & 0xFFL) << 48L | (paramByte3 & 0xFFL) << 40L | (paramByte4 & 0xFFL) << 32L | (paramByte5 & 0xFFL) << 24L | (paramByte6 & 0xFFL) << 16L | (paramByte7 & 0xFFL) << 8L | paramByte8 & 0xFFL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long getLongL(ByteBuffer paramByteBuffer, int paramInt) {
/*  318 */     return makeLong(paramByteBuffer._get(paramInt + 7), paramByteBuffer
/*  319 */         ._get(paramInt + 6), paramByteBuffer
/*  320 */         ._get(paramInt + 5), paramByteBuffer
/*  321 */         ._get(paramInt + 4), paramByteBuffer
/*  322 */         ._get(paramInt + 3), paramByteBuffer
/*  323 */         ._get(paramInt + 2), paramByteBuffer
/*  324 */         ._get(paramInt + 1), paramByteBuffer
/*  325 */         ._get(paramInt));
/*      */   }
/*      */   
/*      */   static long getLongL(long paramLong) {
/*  329 */     return makeLong(_get(paramLong + 7L), 
/*  330 */         _get(paramLong + 6L), 
/*  331 */         _get(paramLong + 5L), 
/*  332 */         _get(paramLong + 4L), 
/*  333 */         _get(paramLong + 3L), 
/*  334 */         _get(paramLong + 2L), 
/*  335 */         _get(paramLong + 1L), 
/*  336 */         _get(paramLong));
/*      */   }
/*      */   
/*      */   static long getLongB(ByteBuffer paramByteBuffer, int paramInt) {
/*  340 */     return makeLong(paramByteBuffer._get(paramInt), paramByteBuffer
/*  341 */         ._get(paramInt + 1), paramByteBuffer
/*  342 */         ._get(paramInt + 2), paramByteBuffer
/*  343 */         ._get(paramInt + 3), paramByteBuffer
/*  344 */         ._get(paramInt + 4), paramByteBuffer
/*  345 */         ._get(paramInt + 5), paramByteBuffer
/*  346 */         ._get(paramInt + 6), paramByteBuffer
/*  347 */         ._get(paramInt + 7));
/*      */   }
/*      */   
/*      */   static long getLongB(long paramLong) {
/*  351 */     return makeLong(_get(paramLong), 
/*  352 */         _get(paramLong + 1L), 
/*  353 */         _get(paramLong + 2L), 
/*  354 */         _get(paramLong + 3L), 
/*  355 */         _get(paramLong + 4L), 
/*  356 */         _get(paramLong + 5L), 
/*  357 */         _get(paramLong + 6L), 
/*  358 */         _get(paramLong + 7L));
/*      */   }
/*      */   
/*      */   static long getLong(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  362 */     return paramBoolean ? getLongB(paramByteBuffer, paramInt) : getLongL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static long getLong(long paramLong, boolean paramBoolean) {
/*  366 */     return paramBoolean ? getLongB(paramLong) : getLongL(paramLong);
/*      */   }
/*      */   
/*  369 */   private static byte long7(long paramLong) { return (byte)(int)(paramLong >> 56L); }
/*  370 */   private static byte long6(long paramLong) { return (byte)(int)(paramLong >> 48L); }
/*  371 */   private static byte long5(long paramLong) { return (byte)(int)(paramLong >> 40L); }
/*  372 */   private static byte long4(long paramLong) { return (byte)(int)(paramLong >> 32L); }
/*  373 */   private static byte long3(long paramLong) { return (byte)(int)(paramLong >> 24L); }
/*  374 */   private static byte long2(long paramLong) { return (byte)(int)(paramLong >> 16L); }
/*  375 */   private static byte long1(long paramLong) { return (byte)(int)(paramLong >> 8L); } private static byte long0(long paramLong) {
/*  376 */     return (byte)(int)paramLong;
/*      */   }
/*      */   static void putLongL(ByteBuffer paramByteBuffer, int paramInt, long paramLong) {
/*  379 */     paramByteBuffer._put(paramInt + 7, long7(paramLong));
/*  380 */     paramByteBuffer._put(paramInt + 6, long6(paramLong));
/*  381 */     paramByteBuffer._put(paramInt + 5, long5(paramLong));
/*  382 */     paramByteBuffer._put(paramInt + 4, long4(paramLong));
/*  383 */     paramByteBuffer._put(paramInt + 3, long3(paramLong));
/*  384 */     paramByteBuffer._put(paramInt + 2, long2(paramLong));
/*  385 */     paramByteBuffer._put(paramInt + 1, long1(paramLong));
/*  386 */     paramByteBuffer._put(paramInt, long0(paramLong));
/*      */   }
/*      */   
/*      */   static void putLongL(long paramLong1, long paramLong2) {
/*  390 */     _put(paramLong1 + 7L, long7(paramLong2));
/*  391 */     _put(paramLong1 + 6L, long6(paramLong2));
/*  392 */     _put(paramLong1 + 5L, long5(paramLong2));
/*  393 */     _put(paramLong1 + 4L, long4(paramLong2));
/*  394 */     _put(paramLong1 + 3L, long3(paramLong2));
/*  395 */     _put(paramLong1 + 2L, long2(paramLong2));
/*  396 */     _put(paramLong1 + 1L, long1(paramLong2));
/*  397 */     _put(paramLong1, long0(paramLong2));
/*      */   }
/*      */   
/*      */   static void putLongB(ByteBuffer paramByteBuffer, int paramInt, long paramLong) {
/*  401 */     paramByteBuffer._put(paramInt, long7(paramLong));
/*  402 */     paramByteBuffer._put(paramInt + 1, long6(paramLong));
/*  403 */     paramByteBuffer._put(paramInt + 2, long5(paramLong));
/*  404 */     paramByteBuffer._put(paramInt + 3, long4(paramLong));
/*  405 */     paramByteBuffer._put(paramInt + 4, long3(paramLong));
/*  406 */     paramByteBuffer._put(paramInt + 5, long2(paramLong));
/*  407 */     paramByteBuffer._put(paramInt + 6, long1(paramLong));
/*  408 */     paramByteBuffer._put(paramInt + 7, long0(paramLong));
/*      */   }
/*      */   
/*      */   static void putLongB(long paramLong1, long paramLong2) {
/*  412 */     _put(paramLong1, long7(paramLong2));
/*  413 */     _put(paramLong1 + 1L, long6(paramLong2));
/*  414 */     _put(paramLong1 + 2L, long5(paramLong2));
/*  415 */     _put(paramLong1 + 3L, long4(paramLong2));
/*  416 */     _put(paramLong1 + 4L, long3(paramLong2));
/*  417 */     _put(paramLong1 + 5L, long2(paramLong2));
/*  418 */     _put(paramLong1 + 6L, long1(paramLong2));
/*  419 */     _put(paramLong1 + 7L, long0(paramLong2));
/*      */   }
/*      */   
/*      */   static void putLong(ByteBuffer paramByteBuffer, int paramInt, long paramLong, boolean paramBoolean) {
/*  423 */     if (paramBoolean) {
/*  424 */       putLongB(paramByteBuffer, paramInt, paramLong);
/*      */     } else {
/*  426 */       putLongL(paramByteBuffer, paramInt, paramLong);
/*      */     } 
/*      */   }
/*      */   static void putLong(long paramLong1, long paramLong2, boolean paramBoolean) {
/*  430 */     if (paramBoolean) {
/*  431 */       putLongB(paramLong1, paramLong2);
/*      */     } else {
/*  433 */       putLongL(paramLong1, paramLong2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static float getFloatL(ByteBuffer paramByteBuffer, int paramInt) {
/*  440 */     return Float.intBitsToFloat(getIntL(paramByteBuffer, paramInt));
/*      */   }
/*      */   
/*      */   static float getFloatL(long paramLong) {
/*  444 */     return Float.intBitsToFloat(getIntL(paramLong));
/*      */   }
/*      */   
/*      */   static float getFloatB(ByteBuffer paramByteBuffer, int paramInt) {
/*  448 */     return Float.intBitsToFloat(getIntB(paramByteBuffer, paramInt));
/*      */   }
/*      */   
/*      */   static float getFloatB(long paramLong) {
/*  452 */     return Float.intBitsToFloat(getIntB(paramLong));
/*      */   }
/*      */   
/*      */   static float getFloat(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  456 */     return paramBoolean ? getFloatB(paramByteBuffer, paramInt) : getFloatL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static float getFloat(long paramLong, boolean paramBoolean) {
/*  460 */     return paramBoolean ? getFloatB(paramLong) : getFloatL(paramLong);
/*      */   }
/*      */   
/*      */   static void putFloatL(ByteBuffer paramByteBuffer, int paramInt, float paramFloat) {
/*  464 */     putIntL(paramByteBuffer, paramInt, Float.floatToRawIntBits(paramFloat));
/*      */   }
/*      */   
/*      */   static void putFloatL(long paramLong, float paramFloat) {
/*  468 */     putIntL(paramLong, Float.floatToRawIntBits(paramFloat));
/*      */   }
/*      */   
/*      */   static void putFloatB(ByteBuffer paramByteBuffer, int paramInt, float paramFloat) {
/*  472 */     putIntB(paramByteBuffer, paramInt, Float.floatToRawIntBits(paramFloat));
/*      */   }
/*      */   
/*      */   static void putFloatB(long paramLong, float paramFloat) {
/*  476 */     putIntB(paramLong, Float.floatToRawIntBits(paramFloat));
/*      */   }
/*      */   
/*      */   static void putFloat(ByteBuffer paramByteBuffer, int paramInt, float paramFloat, boolean paramBoolean) {
/*  480 */     if (paramBoolean) {
/*  481 */       putFloatB(paramByteBuffer, paramInt, paramFloat);
/*      */     } else {
/*  483 */       putFloatL(paramByteBuffer, paramInt, paramFloat);
/*      */     } 
/*      */   }
/*      */   static void putFloat(long paramLong, float paramFloat, boolean paramBoolean) {
/*  487 */     if (paramBoolean) {
/*  488 */       putFloatB(paramLong, paramFloat);
/*      */     } else {
/*  490 */       putFloatL(paramLong, paramFloat);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static double getDoubleL(ByteBuffer paramByteBuffer, int paramInt) {
/*  497 */     return Double.longBitsToDouble(getLongL(paramByteBuffer, paramInt));
/*      */   }
/*      */   
/*      */   static double getDoubleL(long paramLong) {
/*  501 */     return Double.longBitsToDouble(getLongL(paramLong));
/*      */   }
/*      */   
/*      */   static double getDoubleB(ByteBuffer paramByteBuffer, int paramInt) {
/*  505 */     return Double.longBitsToDouble(getLongB(paramByteBuffer, paramInt));
/*      */   }
/*      */   
/*      */   static double getDoubleB(long paramLong) {
/*  509 */     return Double.longBitsToDouble(getLongB(paramLong));
/*      */   }
/*      */   
/*      */   static double getDouble(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  513 */     return paramBoolean ? getDoubleB(paramByteBuffer, paramInt) : getDoubleL(paramByteBuffer, paramInt);
/*      */   }
/*      */   
/*      */   static double getDouble(long paramLong, boolean paramBoolean) {
/*  517 */     return paramBoolean ? getDoubleB(paramLong) : getDoubleL(paramLong);
/*      */   }
/*      */   
/*      */   static void putDoubleL(ByteBuffer paramByteBuffer, int paramInt, double paramDouble) {
/*  521 */     putLongL(paramByteBuffer, paramInt, Double.doubleToRawLongBits(paramDouble));
/*      */   }
/*      */   
/*      */   static void putDoubleL(long paramLong, double paramDouble) {
/*  525 */     putLongL(paramLong, Double.doubleToRawLongBits(paramDouble));
/*      */   }
/*      */   
/*      */   static void putDoubleB(ByteBuffer paramByteBuffer, int paramInt, double paramDouble) {
/*  529 */     putLongB(paramByteBuffer, paramInt, Double.doubleToRawLongBits(paramDouble));
/*      */   }
/*      */   
/*      */   static void putDoubleB(long paramLong, double paramDouble) {
/*  533 */     putLongB(paramLong, Double.doubleToRawLongBits(paramDouble));
/*      */   }
/*      */   
/*      */   static void putDouble(ByteBuffer paramByteBuffer, int paramInt, double paramDouble, boolean paramBoolean) {
/*  537 */     if (paramBoolean) {
/*  538 */       putDoubleB(paramByteBuffer, paramInt, paramDouble);
/*      */     } else {
/*  540 */       putDoubleL(paramByteBuffer, paramInt, paramDouble);
/*      */     } 
/*      */   }
/*      */   static void putDouble(long paramLong, double paramDouble, boolean paramBoolean) {
/*  544 */     if (paramBoolean) {
/*  545 */       putDoubleB(paramLong, paramDouble);
/*      */     } else {
/*  547 */       putDoubleL(paramLong, paramDouble);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  553 */   private static final Unsafe unsafe = Unsafe.getUnsafe(); private static final ByteOrder byteOrder;
/*      */   
/*      */   private static byte _get(long paramLong) {
/*  556 */     return unsafe.getByte(paramLong);
/*      */   }
/*      */   
/*      */   private static void _put(long paramLong, byte paramByte) {
/*  560 */     unsafe.putByte(paramLong, paramByte);
/*      */   }
/*      */   
/*      */   static Unsafe unsafe() {
/*  564 */     return unsafe;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ByteOrder byteOrder() {
/*  573 */     if (byteOrder == null)
/*  574 */       throw new Error("Unknown byte order"); 
/*  575 */     return byteOrder;
/*      */   }
/*      */   
/*      */   static {
/*  579 */     long l = unsafe.allocateMemory(8L);
/*      */     try {
/*  581 */       unsafe.putLong(l, 72623859790382856L);
/*  582 */       byte b = unsafe.getByte(l);
/*  583 */       switch (b) { case 1:
/*  584 */           byteOrder = ByteOrder.BIG_ENDIAN; break;
/*  585 */         case 8: byteOrder = ByteOrder.LITTLE_ENDIAN; break;
/*      */         default:
/*      */           assert false;
/*  588 */           byteOrder = null; break; }
/*      */     
/*      */     } finally {
/*  591 */       unsafe.freeMemory(l);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  596 */   private static int pageSize = -1; private static boolean unaligned;
/*      */   
/*      */   static int pageSize() {
/*  599 */     if (pageSize == -1)
/*  600 */       pageSize = unsafe().pageSize(); 
/*  601 */     return pageSize;
/*      */   }
/*      */   
/*      */   static int pageCount(long paramLong) {
/*  605 */     return (int)(paramLong + pageSize() - 1L) / pageSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean unalignedKnown = false;
/*      */   
/*      */   static boolean unaligned() {
/*  612 */     if (unalignedKnown)
/*  613 */       return unaligned; 
/*  614 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.arch"));
/*      */ 
/*      */ 
/*      */     
/*  618 */     unaligned = (str.equals("i386") || str.equals("x86") || str.equals("amd64") || str.equals("x86_64") || str.equals("ppc64") || str.equals("ppc64le"));
/*  619 */     unalignedKnown = true;
/*  620 */     return unaligned;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  629 */   private static volatile long maxMemory = VM.maxDirectMemory();
/*  630 */   private static final AtomicLong reservedMemory = new AtomicLong();
/*  631 */   private static final AtomicLong totalCapacity = new AtomicLong();
/*  632 */   private static final AtomicLong count = new AtomicLong();
/*      */   
/*      */   private static volatile boolean memoryLimitSet = false;
/*      */   
/*      */   private static final int MAX_SLEEPS = 9;
/*      */   
/*      */   static final int JNI_COPY_TO_ARRAY_THRESHOLD = 6;
/*      */   
/*      */   static final int JNI_COPY_FROM_ARRAY_THRESHOLD = 6;
/*      */   
/*      */   static final long UNSAFE_COPY_THRESHOLD = 1048576L;
/*      */   
/*      */   static void reserveMemory(long paramLong, int paramInt) {
/*  645 */     if (!memoryLimitSet && VM.isBooted()) {
/*  646 */       maxMemory = VM.maxDirectMemory();
/*  647 */       memoryLimitSet = true;
/*      */     } 
/*      */ 
/*      */     
/*  651 */     if (tryReserveMemory(paramLong, paramInt)) {
/*      */       return;
/*      */     }
/*      */     
/*  655 */     JavaLangRefAccess javaLangRefAccess = SharedSecrets.getJavaLangRefAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  660 */     while (javaLangRefAccess.tryHandlePendingReference()) {
/*  661 */       if (tryReserveMemory(paramLong, paramInt)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  667 */     System.gc();
/*      */ 
/*      */ 
/*      */     
/*  671 */     boolean bool = false;
/*      */     try {
/*  673 */       long l = 1L;
/*  674 */       byte b = 0;
/*      */       while (true) {
/*  676 */         if (tryReserveMemory(paramLong, paramInt)) {
/*      */           return;
/*      */         }
/*  679 */         if (b >= 9) {
/*      */           break;
/*      */         }
/*  682 */         if (!javaLangRefAccess.tryHandlePendingReference()) {
/*      */           try {
/*  684 */             Thread.sleep(l);
/*  685 */             l <<= 1L;
/*  686 */             b++;
/*  687 */           } catch (InterruptedException interruptedException) {
/*  688 */             bool = true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  694 */       throw new OutOfMemoryError("Direct buffer memory");
/*      */     } finally {
/*      */       
/*  697 */       if (bool)
/*      */       {
/*  699 */         Thread.currentThread().interrupt();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean tryReserveMemory(long paramLong, int paramInt) {
/*      */     long l;
/*  710 */     while (paramInt <= maxMemory - (l = totalCapacity.get())) {
/*  711 */       if (totalCapacity.compareAndSet(l, l + paramInt)) {
/*  712 */         reservedMemory.addAndGet(paramLong);
/*  713 */         count.incrementAndGet();
/*  714 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  718 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static void unreserveMemory(long paramLong, int paramInt) {
/*  723 */     long l1 = count.decrementAndGet();
/*  724 */     long l2 = reservedMemory.addAndGet(-paramLong);
/*  725 */     long l3 = totalCapacity.addAndGet(-paramInt);
/*  726 */     assert l1 >= 0L && l2 >= 0L && l3 >= 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  733 */     SharedSecrets.setJavaNioAccess(new JavaNioAccess()
/*      */         {
/*      */           public JavaNioAccess.BufferPool getDirectBufferPool()
/*      */           {
/*  737 */             return new JavaNioAccess.BufferPool()
/*      */               {
/*      */                 public String getName() {
/*  740 */                   return "direct";
/*      */                 }
/*      */                 
/*      */                 public long getCount() {
/*  744 */                   return Bits.count.get();
/*      */                 }
/*      */                 
/*      */                 public long getTotalCapacity() {
/*  748 */                   return Bits.totalCapacity.get();
/*      */                 }
/*      */                 
/*      */                 public long getMemoryUsed() {
/*  752 */                   return Bits.reservedMemory.get();
/*      */                 }
/*      */               };
/*      */           }
/*      */           
/*      */           public ByteBuffer newDirectByteBuffer(long param1Long, int param1Int, Object param1Object) {
/*  758 */             return new DirectByteBuffer(param1Long, param1Int, param1Object);
/*      */           }
/*      */           
/*      */           public void truncate(Buffer param1Buffer) {
/*  762 */             param1Buffer.truncate();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyFromArray(Object paramObject, long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/*  801 */     long l = paramLong1 + paramLong2;
/*  802 */     while (paramLong4 > 0L) {
/*  803 */       long l1 = (paramLong4 > 1048576L) ? 1048576L : paramLong4;
/*  804 */       unsafe.copyMemory(paramObject, l, null, paramLong3, l1);
/*  805 */       paramLong4 -= l1;
/*  806 */       l += l1;
/*  807 */       paramLong3 += l1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyToArray(long paramLong1, Object paramObject, long paramLong2, long paramLong3, long paramLong4) {
/*  828 */     long l = paramLong2 + paramLong3;
/*  829 */     while (paramLong4 > 0L) {
/*  830 */       long l1 = (paramLong4 > 1048576L) ? 1048576L : paramLong4;
/*  831 */       unsafe.copyMemory(null, paramLong1, paramObject, l, l1);
/*  832 */       paramLong4 -= l1;
/*  833 */       paramLong1 += l1;
/*  834 */       l += l1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyFromCharArray(Object paramObject, long paramLong1, long paramLong2, long paramLong3) {
/*  851 */     copySwapMemory(paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong1, null, paramLong2, paramLong3, 2L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyToCharArray(long paramLong1, Object paramObject, long paramLong2, long paramLong3) {
/*  867 */     copySwapMemory(null, paramLong1, paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong2, paramLong3, 2L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyFromShortArray(Object paramObject, long paramLong1, long paramLong2, long paramLong3) {
/*  883 */     copySwapMemory(paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong1, null, paramLong2, paramLong3, 2L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyToShortArray(long paramLong1, Object paramObject, long paramLong2, long paramLong3) {
/*  899 */     copySwapMemory(null, paramLong1, paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong2, paramLong3, 2L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyFromIntArray(Object paramObject, long paramLong1, long paramLong2, long paramLong3) {
/*  915 */     copySwapMemory(paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong1, null, paramLong2, paramLong3, 4L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyToIntArray(long paramLong1, Object paramObject, long paramLong2, long paramLong3) {
/*  931 */     copySwapMemory(null, paramLong1, paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong2, paramLong3, 4L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyFromLongArray(Object paramObject, long paramLong1, long paramLong2, long paramLong3) {
/*  947 */     copySwapMemory(paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong1, null, paramLong2, paramLong3, 8L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void copyToLongArray(long paramLong1, Object paramObject, long paramLong2, long paramLong3) {
/*  963 */     copySwapMemory(null, paramLong1, paramObject, unsafe.arrayBaseOffset(paramObject.getClass()) + paramLong2, paramLong3, 8L);
/*      */   }
/*      */   
/*      */   private static boolean isPrimitiveArray(Class<?> paramClass) {
/*  967 */     Class<?> clazz = paramClass.getComponentType();
/*  968 */     return (clazz != null && clazz.isPrimitive());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void copySwapMemory(Object paramObject1, long paramLong1, Object paramObject2, long paramLong2, long paramLong3, long paramLong4) {
/*  989 */     if (paramLong3 < 0L) {
/*  990 */       throw new IllegalArgumentException();
/*      */     }
/*  992 */     if (paramLong4 != 2L && paramLong4 != 4L && paramLong4 != 8L) {
/*  993 */       throw new IllegalArgumentException();
/*      */     }
/*  995 */     if (paramLong3 % paramLong4 != 0L) {
/*  996 */       throw new IllegalArgumentException();
/*      */     }
/*  998 */     if ((paramObject1 == null && paramLong1 == 0L) || (paramObject2 == null && paramLong2 == 0L))
/*      */     {
/* 1000 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/* 1004 */     if (paramObject1 != null && (paramLong1 < 0L || !isPrimitiveArray(paramObject1.getClass()))) {
/* 1005 */       throw new IllegalArgumentException();
/*      */     }
/* 1007 */     if (paramObject2 != null && (paramLong2 < 0L || !isPrimitiveArray(paramObject2.getClass()))) {
/* 1008 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1013 */     if (unsafe.addressSize() == 4 && (paramLong3 >>> 32L != 0L || paramLong1 >>> 32L != 0L || paramLong2 >>> 32L != 0L))
/*      */     {
/* 1015 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1018 */     if (paramLong3 == 0L) {
/*      */       return;
/*      */     }
/*      */     
/* 1022 */     copySwapMemory0(paramObject1, paramLong1, paramObject2, paramLong2, paramLong3, paramLong4);
/*      */   }
/*      */   
/*      */   private static native void copySwapMemory0(Object paramObject1, long paramLong1, Object paramObject2, long paramLong2, long paramLong3, long paramLong4);
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\nio\Bits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */