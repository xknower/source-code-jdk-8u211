/*      */ package sun.font;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CodingErrorAction;
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
/*      */ abstract class CMap
/*      */ {
/*      */   static final short ShiftJISEncoding = 2;
/*      */   static final short GBKEncoding = 3;
/*      */   static final short Big5Encoding = 4;
/*      */   static final short WansungEncoding = 5;
/*      */   static final short JohabEncoding = 6;
/*      */   static final short MSUnicodeSurrogateEncoding = 10;
/*      */   static final char noSuchChar = '�';
/*      */   static final int SHORTMASK = 65535;
/*      */   static final int INTMASK = -1;
/*  135 */   static final char[][] converterMaps = new char[7][];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   char[] xlat;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CMap initialize(TrueTypeFont paramTrueTypeFont) {
/*  146 */     CMap cMap = null;
/*      */     
/*  148 */     short s = -1;
/*      */     
/*  150 */     int i = 0, j = 0, k = 0, m = 0, n = 0, i1 = 0;
/*  151 */     int i2 = 0, i3 = 0;
/*  152 */     boolean bool = false;
/*      */     
/*  154 */     ByteBuffer byteBuffer = paramTrueTypeFont.getTableBuffer(1668112752);
/*  155 */     int i4 = paramTrueTypeFont.getTableSize(1668112752);
/*  156 */     short s1 = byteBuffer.getShort(2);
/*      */ 
/*      */     
/*  159 */     for (byte b = 0; b < s1; b++) {
/*  160 */       byteBuffer.position(b * 8 + 4);
/*  161 */       short s2 = byteBuffer.getShort();
/*  162 */       if (s2 == 3) {
/*  163 */         bool = true;
/*  164 */         s = byteBuffer.getShort();
/*  165 */         int i5 = byteBuffer.getInt();
/*  166 */         switch (s) { case 0:
/*  167 */             i = i5; break;
/*  168 */           case 1: j = i5; break;
/*  169 */           case 2: k = i5; break;
/*  170 */           case 3: m = i5; break;
/*  171 */           case 4: n = i5; break;
/*  172 */           case 5: i1 = i5; break;
/*  173 */           case 6: i2 = i5; break;
/*  174 */           case 10: i3 = i5;
/*      */             break; }
/*      */ 
/*      */       
/*      */       } 
/*      */     } 
/*  180 */     if (bool) {
/*  181 */       if (i3 != 0) {
/*  182 */         cMap = createCMap(byteBuffer, i3, null);
/*      */       }
/*  184 */       else if (i != 0) {
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
/*  214 */         cMap = createCMap(byteBuffer, i, null);
/*      */       
/*      */       }
/*  217 */       else if (j != 0) {
/*  218 */         cMap = createCMap(byteBuffer, j, null);
/*      */       }
/*  220 */       else if (k != 0) {
/*  221 */         cMap = createCMap(byteBuffer, k, 
/*  222 */             getConverterMap((short)2));
/*      */       }
/*  224 */       else if (m != 0) {
/*  225 */         cMap = createCMap(byteBuffer, m, 
/*  226 */             getConverterMap((short)3));
/*      */       }
/*  228 */       else if (n != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  235 */         if (FontUtilities.isSolaris && paramTrueTypeFont.platName != null && (paramTrueTypeFont.platName
/*  236 */           .startsWith("/usr/openwin/lib/locale/zh_CN.EUC/X11/fonts/TrueType") || paramTrueTypeFont.platName
/*      */           
/*  238 */           .startsWith("/usr/openwin/lib/locale/zh_CN/X11/fonts/TrueType") || paramTrueTypeFont.platName
/*      */           
/*  240 */           .startsWith("/usr/openwin/lib/locale/zh/X11/fonts/TrueType"))) {
/*      */           
/*  242 */           cMap = createCMap(byteBuffer, n, 
/*  243 */               getConverterMap((short)3));
/*      */         } else {
/*      */           
/*  246 */           cMap = createCMap(byteBuffer, n, 
/*  247 */               getConverterMap((short)4));
/*      */         }
/*      */       
/*  250 */       } else if (i1 != 0) {
/*  251 */         cMap = createCMap(byteBuffer, i1, 
/*  252 */             getConverterMap((short)5));
/*      */       }
/*  254 */       else if (i2 != 0) {
/*  255 */         cMap = createCMap(byteBuffer, i2, 
/*  256 */             getConverterMap((short)6));
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  263 */       cMap = createCMap(byteBuffer, byteBuffer.getInt(8), null);
/*      */     } 
/*  265 */     return cMap;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static char[] getConverter(short paramShort) {
/*      */     String str;
/*  272 */     char c1 = '耀';
/*  273 */     char c2 = '￿';
/*      */ 
/*      */     
/*  276 */     switch (paramShort) {
/*      */       case 2:
/*  278 */         c1 = '腀';
/*  279 */         c2 = 'ﳼ';
/*  280 */         str = "SJIS";
/*      */         break;
/*      */       case 3:
/*  283 */         c1 = '腀';
/*  284 */         c2 = 'ﺠ';
/*  285 */         str = "GBK";
/*      */         break;
/*      */       case 4:
/*  288 */         c1 = 'ꅀ';
/*  289 */         c2 = '﻾';
/*  290 */         str = "Big5";
/*      */         break;
/*      */       case 5:
/*  293 */         c1 = 'ꆡ';
/*  294 */         c2 = 'ﻞ';
/*  295 */         str = "EUC_KR";
/*      */         break;
/*      */       case 6:
/*  298 */         c1 = '腁';
/*  299 */         c2 = '﷾';
/*  300 */         str = "Johab";
/*      */         break;
/*      */       default:
/*  303 */         return null;
/*      */     } 
/*      */     
/*      */     try {
/*  307 */       char[] arrayOfChar1 = new char[65536];
/*  308 */       for (byte b1 = 0; b1 < 65536; b1++) {
/*  309 */         arrayOfChar1[b1] = '�';
/*      */       }
/*      */       
/*  312 */       byte[] arrayOfByte = new byte[(c2 - c1 + 1) * 2];
/*  313 */       char[] arrayOfChar2 = new char[c2 - c1 + 1];
/*      */       
/*  315 */       byte b2 = 0;
/*      */       
/*  317 */       if (paramShort == 2) {
/*  318 */         for (char c3 = c1; c3 <= c2; c3++) {
/*  319 */           int i = c3 >> 8 & 0xFF;
/*  320 */           if (i >= 161 && i <= 223) {
/*      */             
/*  322 */             arrayOfByte[b2++] = -1;
/*  323 */             arrayOfByte[b2++] = -1;
/*      */           } else {
/*  325 */             arrayOfByte[b2++] = (byte)i;
/*  326 */             arrayOfByte[b2++] = (byte)(c3 & 0xFF);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  330 */         for (char c3 = c1; c3 <= c2; c3++) {
/*  331 */           arrayOfByte[b2++] = (byte)(c3 >> 8 & 0xFF);
/*  332 */           arrayOfByte[b2++] = (byte)(c3 & 0xFF);
/*      */         } 
/*      */       } 
/*      */       
/*  336 */       Charset.forName(str).newDecoder()
/*  337 */         .onMalformedInput(CodingErrorAction.REPLACE)
/*  338 */         .onUnmappableCharacter(CodingErrorAction.REPLACE)
/*  339 */         .replaceWith("\000")
/*  340 */         .decode(ByteBuffer.wrap(arrayOfByte, 0, arrayOfByte.length), 
/*  341 */           CharBuffer.wrap(arrayOfChar2, 0, arrayOfChar2.length), true);
/*      */       
/*      */       char c;
/*      */       
/*  345 */       for (c = ' '; c <= '~'; c++) {
/*  346 */         arrayOfChar1[c] = (char)c;
/*      */       }
/*      */ 
/*      */       
/*  350 */       if (paramShort == 2) {
/*  351 */         for (c = '¡'; c <= 'ß'; c++) {
/*  352 */           arrayOfChar1[c] = (char)(c - 161 + 65377);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  364 */       System.arraycopy(arrayOfChar2, 0, arrayOfChar1, c1, arrayOfChar2.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  371 */       char[] arrayOfChar3 = new char[65536];
/*  372 */       for (byte b3 = 0; b3 < 65536; b3++) {
/*  373 */         if (arrayOfChar1[b3] != '�') {
/*  374 */           arrayOfChar3[arrayOfChar1[b3]] = (char)b3;
/*      */         }
/*      */       } 
/*  377 */       return arrayOfChar3;
/*      */     }
/*  379 */     catch (Exception exception) {
/*  380 */       exception.printStackTrace();
/*      */       
/*  382 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static char[] getConverterMap(short paramShort) {
/*  391 */     if (converterMaps[paramShort] == null) {
/*  392 */       converterMaps[paramShort] = getConverter(paramShort);
/*      */     }
/*  394 */     return converterMaps[paramShort];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CMap createCMap(ByteBuffer paramByteBuffer, int paramInt, char[] paramArrayOfchar) {
/*      */     long l;
/*  402 */     char c = paramByteBuffer.getChar(paramInt);
/*      */     
/*  404 */     if (c < '\b') {
/*  405 */       l = paramByteBuffer.getChar(paramInt + 2);
/*      */     } else {
/*  407 */       l = (paramByteBuffer.getInt(paramInt + 4) & 0xFFFFFFFF);
/*      */     } 
/*  409 */     if (paramInt + l > paramByteBuffer.capacity() && 
/*  410 */       FontUtilities.isLogging()) {
/*  411 */       FontUtilities.getLogger().warning("Cmap subtable overflows buffer.");
/*      */     }
/*      */     
/*  414 */     switch (c) { case '\000':
/*  415 */         return new CMapFormat0(paramByteBuffer, paramInt);
/*  416 */       case '\002': return new CMapFormat2(paramByteBuffer, paramInt, paramArrayOfchar);
/*  417 */       case '\004': return new CMapFormat4(paramByteBuffer, paramInt, paramArrayOfchar);
/*  418 */       case '\006': return new CMapFormat6(paramByteBuffer, paramInt, paramArrayOfchar);
/*  419 */       case '\b': return new CMapFormat8(paramByteBuffer, paramInt, paramArrayOfchar);
/*  420 */       case '\n': return new CMapFormat10(paramByteBuffer, paramInt, paramArrayOfchar);
/*  421 */       case '\f': return new CMapFormat12(paramByteBuffer, paramInt, paramArrayOfchar); }
/*  422 */      throw new RuntimeException("Cmap format unimplemented: " + paramByteBuffer
/*  423 */         .getChar(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class CMapFormat4
/*      */     extends CMap
/*      */   {
/*      */     int segCount;
/*      */ 
/*      */ 
/*      */     
/*      */     int entrySelector;
/*      */ 
/*      */ 
/*      */     
/*      */     int rangeShift;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] endCount;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] startCount;
/*      */ 
/*      */ 
/*      */     
/*      */     short[] idDelta;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] idRangeOffset;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] glyphIds;
/*      */ 
/*      */ 
/*      */     
/*      */     CMapFormat4(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  465 */       this.xlat = param1ArrayOfchar;
/*      */       
/*  467 */       param1ByteBuffer.position(param1Int);
/*  468 */       CharBuffer charBuffer = param1ByteBuffer.asCharBuffer();
/*  469 */       charBuffer.get();
/*  470 */       int i = charBuffer.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  479 */       if (param1Int + i > param1ByteBuffer.capacity()) {
/*  480 */         i = param1ByteBuffer.capacity() - param1Int;
/*      */       }
/*  482 */       charBuffer.get();
/*  483 */       this.segCount = charBuffer.get() / 2;
/*  484 */       char c = charBuffer.get();
/*  485 */       this.entrySelector = charBuffer.get();
/*  486 */       this.rangeShift = charBuffer.get() / 2;
/*  487 */       this.startCount = new char[this.segCount];
/*  488 */       this.endCount = new char[this.segCount];
/*  489 */       this.idDelta = new short[this.segCount];
/*  490 */       this.idRangeOffset = new char[this.segCount];
/*      */       int j;
/*  492 */       for (j = 0; j < this.segCount; j++) {
/*  493 */         this.endCount[j] = charBuffer.get();
/*      */       }
/*  495 */       charBuffer.get();
/*  496 */       for (j = 0; j < this.segCount; j++) {
/*  497 */         this.startCount[j] = charBuffer.get();
/*      */       }
/*      */       
/*  500 */       for (j = 0; j < this.segCount; j++) {
/*  501 */         this.idDelta[j] = (short)charBuffer.get();
/*      */       }
/*      */       
/*  504 */       for (j = 0; j < this.segCount; j++) {
/*  505 */         char c1 = charBuffer.get();
/*  506 */         this.idRangeOffset[j] = (char)(c1 >> 1 & 0xFFFF);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  511 */       j = (this.segCount * 8 + 16) / 2;
/*  512 */       charBuffer.position(j);
/*  513 */       int k = i / 2 - j;
/*  514 */       this.glyphIds = new char[k];
/*  515 */       for (byte b = 0; b < k; b++) {
/*  516 */         this.glyphIds[b] = charBuffer.get();
/*      */       }
/*      */     }
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
/*      */     char getGlyph(int param1Int) {
/*  538 */       int i = 0;
/*  539 */       char c = Character.MIN_VALUE;
/*      */       
/*  541 */       int j = getControlCodeGlyph(param1Int, true);
/*  542 */       if (j >= 0) {
/*  543 */         return (char)j;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  553 */       if (this.xlat != null) {
/*  554 */         param1Int = this.xlat[param1Int];
/*      */       }
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
/*  575 */       int k = 0, m = this.startCount.length;
/*  576 */       i = this.startCount.length >> 1;
/*  577 */       while (k < m) {
/*  578 */         if (this.endCount[i] < param1Int) {
/*  579 */           k = i + 1;
/*      */         } else {
/*  581 */           m = i;
/*      */         } 
/*  583 */         i = k + m >> 1;
/*      */       } 
/*      */       
/*  586 */       if (param1Int >= this.startCount[i] && param1Int <= this.endCount[i]) {
/*  587 */         char c1 = this.idRangeOffset[i];
/*      */         
/*  589 */         if (c1 == '\000') {
/*  590 */           c = (char)(param1Int + this.idDelta[i]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  601 */           int n = c1 - this.segCount + i + param1Int - this.startCount[i];
/*      */           
/*  603 */           c = this.glyphIds[n];
/*  604 */           if (c != '\000') {
/*  605 */             c = (char)(c + this.idDelta[i]);
/*      */           }
/*      */         } 
/*      */       } 
/*  609 */       if (c != '\000');
/*      */ 
/*      */       
/*  612 */       return c;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CMapFormat0
/*      */     extends CMap
/*      */   {
/*      */     byte[] cmap;
/*      */     
/*      */     CMapFormat0(ByteBuffer param1ByteBuffer, int param1Int) {
/*  623 */       char c = param1ByteBuffer.getChar(param1Int + 2);
/*  624 */       this.cmap = new byte[c - 6];
/*  625 */       param1ByteBuffer.position(param1Int + 6);
/*  626 */       param1ByteBuffer.get(this.cmap);
/*      */     }
/*      */     
/*      */     char getGlyph(int param1Int) {
/*  630 */       if (param1Int < 256) {
/*  631 */         if (param1Int < 16) {
/*  632 */           switch (param1Int) { case 9:
/*      */             case 10:
/*      */             case 13:
/*  635 */               return Character.MAX_VALUE; }
/*      */         
/*      */         }
/*  638 */         return (char)(0xFF & this.cmap[param1Int]);
/*      */       } 
/*  640 */       return Character.MIN_VALUE;
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
/*      */   static class CMapFormat2
/*      */     extends CMap
/*      */   {
/*  718 */     char[] subHeaderKey = new char[256];
/*      */ 
/*      */     
/*      */     char[] firstCodeArray;
/*      */ 
/*      */     
/*      */     char[] entryCountArray;
/*      */ 
/*      */     
/*      */     short[] idDeltaArray;
/*      */ 
/*      */     
/*      */     char[] idRangeOffSetArray;
/*      */     
/*      */     char[] glyphIndexArray;
/*      */ 
/*      */     
/*      */     CMapFormat2(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  736 */       this.xlat = param1ArrayOfchar;
/*      */       
/*  738 */       char c = param1ByteBuffer.getChar(param1Int + 2);
/*  739 */       param1ByteBuffer.position(param1Int + 6);
/*  740 */       CharBuffer charBuffer = param1ByteBuffer.asCharBuffer();
/*  741 */       char c1 = Character.MIN_VALUE; int i;
/*  742 */       for (i = 0; i < 256; i++) {
/*  743 */         this.subHeaderKey[i] = charBuffer.get();
/*  744 */         if (this.subHeaderKey[i] > c1) {
/*  745 */           c1 = this.subHeaderKey[i];
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  752 */       i = (c1 >> 3) + 1;
/*  753 */       this.firstCodeArray = new char[i];
/*  754 */       this.entryCountArray = new char[i];
/*  755 */       this.idDeltaArray = new short[i];
/*  756 */       this.idRangeOffSetArray = new char[i]; int j;
/*  757 */       for (j = 0; j < i; j++) {
/*  758 */         this.firstCodeArray[j] = charBuffer.get();
/*  759 */         this.entryCountArray[j] = charBuffer.get();
/*  760 */         this.idDeltaArray[j] = (short)charBuffer.get();
/*  761 */         this.idRangeOffSetArray[j] = charBuffer.get();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  768 */       j = (c - 518 - i * 8) / 2;
/*  769 */       this.glyphIndexArray = new char[j];
/*  770 */       for (byte b = 0; b < j; b++) {
/*  771 */         this.glyphIndexArray[b] = charBuffer.get();
/*      */       }
/*      */     }
/*      */     
/*      */     char getGlyph(int param1Int) {
/*  776 */       int i = getControlCodeGlyph(param1Int, true);
/*  777 */       if (i >= 0) {
/*  778 */         return (char)i;
/*      */       }
/*      */       
/*  781 */       if (this.xlat != null) {
/*  782 */         param1Int = this.xlat[param1Int];
/*      */       }
/*      */       
/*  785 */       char c1 = (char)(param1Int >> 8);
/*  786 */       char c2 = (char)(param1Int & 0xFF);
/*  787 */       int j = this.subHeaderKey[c1] >> 3;
/*      */ 
/*      */       
/*  790 */       if (j != 0) {
/*  791 */         c = c2;
/*      */       } else {
/*  793 */         c = c1;
/*  794 */         if (c == '\000') {
/*  795 */           c = c2;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  801 */       char c3 = this.firstCodeArray[j];
/*  802 */       if (c < c3) {
/*  803 */         return Character.MIN_VALUE;
/*      */       }
/*  805 */       char c = (char)(c - c3);
/*      */ 
/*      */       
/*  808 */       if (c < this.entryCountArray[j]) {
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
/*  822 */         int k = (this.idRangeOffSetArray.length - j) * 8 - 6;
/*  823 */         int m = (this.idRangeOffSetArray[j] - k) / 2;
/*      */         
/*  825 */         char c4 = this.glyphIndexArray[m + c];
/*  826 */         if (c4 != '\000') {
/*  827 */           c4 = (char)(c4 + this.idDeltaArray[j]);
/*  828 */           return c4;
/*      */         } 
/*      */       } 
/*  831 */       return Character.MIN_VALUE;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CMapFormat6
/*      */     extends CMap
/*      */   {
/*      */     char firstCode;
/*      */     char entryCount;
/*      */     char[] glyphIdArray;
/*      */     
/*      */     CMapFormat6(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  844 */       param1ByteBuffer.position(param1Int + 6);
/*  845 */       CharBuffer charBuffer = param1ByteBuffer.asCharBuffer();
/*  846 */       this.firstCode = charBuffer.get();
/*  847 */       this.entryCount = charBuffer.get();
/*  848 */       this.glyphIdArray = new char[this.entryCount];
/*  849 */       for (byte b = 0; b < this.entryCount; b++) {
/*  850 */         this.glyphIdArray[b] = charBuffer.get();
/*      */       }
/*      */     }
/*      */     
/*      */     char getGlyph(int param1Int) {
/*  855 */       int i = getControlCodeGlyph(param1Int, true);
/*  856 */       if (i >= 0) {
/*  857 */         return (char)i;
/*      */       }
/*      */       
/*  860 */       if (this.xlat != null) {
/*  861 */         param1Int = this.xlat[param1Int];
/*      */       }
/*      */       
/*  864 */       param1Int -= this.firstCode;
/*  865 */       if (param1Int < 0 || param1Int >= this.entryCount) {
/*  866 */         return Character.MIN_VALUE;
/*      */       }
/*  868 */       return this.glyphIdArray[param1Int];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class CMapFormat8
/*      */     extends CMap
/*      */   {
/*  878 */     byte[] is32 = new byte[8192];
/*      */     
/*      */     int nGroups;
/*      */     int[] startCharCode;
/*      */     int[] endCharCode;
/*      */     int[] startGlyphID;
/*      */     
/*      */     CMapFormat8(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  886 */       param1ByteBuffer.position(12);
/*  887 */       param1ByteBuffer.get(this.is32);
/*  888 */       this.nGroups = param1ByteBuffer.getInt();
/*  889 */       this.startCharCode = new int[this.nGroups];
/*  890 */       this.endCharCode = new int[this.nGroups];
/*  891 */       this.startGlyphID = new int[this.nGroups];
/*      */     }
/*      */     
/*      */     char getGlyph(int param1Int) {
/*  895 */       if (this.xlat != null) {
/*  896 */         throw new RuntimeException("xlat array for cmap fmt=8");
/*      */       }
/*  898 */       return Character.MIN_VALUE;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CMapFormat10
/*      */     extends CMap
/*      */   {
/*      */     long firstCode;
/*      */ 
/*      */     
/*      */     int entryCount;
/*      */     
/*      */     char[] glyphIdArray;
/*      */ 
/*      */     
/*      */     CMapFormat10(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  916 */       this.firstCode = (param1ByteBuffer.getInt() & 0xFFFFFFFF);
/*  917 */       this.entryCount = param1ByteBuffer.getInt() & 0xFFFFFFFF;
/*  918 */       param1ByteBuffer.position(param1Int + 20);
/*  919 */       CharBuffer charBuffer = param1ByteBuffer.asCharBuffer();
/*  920 */       this.glyphIdArray = new char[this.entryCount];
/*  921 */       for (byte b = 0; b < this.entryCount; b++) {
/*  922 */         this.glyphIdArray[b] = charBuffer.get();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     char getGlyph(int param1Int) {
/*  928 */       if (this.xlat != null) {
/*  929 */         throw new RuntimeException("xlat array for cmap fmt=10");
/*      */       }
/*      */       
/*  932 */       int i = (int)(param1Int - this.firstCode);
/*  933 */       if (i < 0 || i >= this.entryCount) {
/*  934 */         return Character.MIN_VALUE;
/*      */       }
/*  936 */       return this.glyphIdArray[i];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CMapFormat12
/*      */     extends CMap
/*      */   {
/*      */     int numGroups;
/*      */     
/*  946 */     int highBit = 0;
/*      */     int power;
/*      */     int extra;
/*      */     long[] startCharCode;
/*      */     long[] endCharCode;
/*      */     int[] startGlyphID;
/*      */     
/*      */     CMapFormat12(ByteBuffer param1ByteBuffer, int param1Int, char[] param1ArrayOfchar) {
/*  954 */       if (param1ArrayOfchar != null) {
/*  955 */         throw new RuntimeException("xlat array for cmap fmt=12");
/*      */       }
/*      */       
/*  958 */       this.numGroups = param1ByteBuffer.getInt(param1Int + 12);
/*  959 */       this.startCharCode = new long[this.numGroups];
/*  960 */       this.endCharCode = new long[this.numGroups];
/*  961 */       this.startGlyphID = new int[this.numGroups];
/*  962 */       param1ByteBuffer.position(param1Int + 16);
/*  963 */       param1ByteBuffer = param1ByteBuffer.slice();
/*  964 */       IntBuffer intBuffer = param1ByteBuffer.asIntBuffer(); int i;
/*  965 */       for (i = 0; i < this.numGroups; i++) {
/*  966 */         this.startCharCode[i] = (intBuffer.get() & 0xFFFFFFFF);
/*  967 */         this.endCharCode[i] = (intBuffer.get() & 0xFFFFFFFF);
/*  968 */         this.startGlyphID[i] = intBuffer.get() & 0xFFFFFFFF;
/*      */       } 
/*      */ 
/*      */       
/*  972 */       i = this.numGroups;
/*      */       
/*  974 */       if (i >= 65536) {
/*  975 */         i >>= 16;
/*  976 */         this.highBit += 16;
/*      */       } 
/*      */       
/*  979 */       if (i >= 256) {
/*  980 */         i >>= 8;
/*  981 */         this.highBit += 8;
/*      */       } 
/*      */       
/*  984 */       if (i >= 16) {
/*  985 */         i >>= 4;
/*  986 */         this.highBit += 4;
/*      */       } 
/*      */       
/*  989 */       if (i >= 4) {
/*  990 */         i >>= 2;
/*  991 */         this.highBit += 2;
/*      */       } 
/*      */       
/*  994 */       if (i >= 2) {
/*  995 */         i >>= 1;
/*  996 */         this.highBit++;
/*      */       } 
/*      */       
/*  999 */       this.power = 1 << this.highBit;
/* 1000 */       this.extra = this.numGroups - this.power;
/*      */     }
/*      */     
/*      */     char getGlyph(int param1Int) {
/* 1004 */       int i = getControlCodeGlyph(param1Int, false);
/* 1005 */       if (i >= 0) {
/* 1006 */         return (char)i;
/*      */       }
/* 1008 */       int j = this.power;
/* 1009 */       int k = 0;
/*      */       
/* 1011 */       if (this.startCharCode[this.extra] <= param1Int) {
/* 1012 */         k = this.extra;
/*      */       }
/*      */       
/* 1015 */       while (j > 1) {
/* 1016 */         j >>= 1;
/*      */         
/* 1018 */         if (this.startCharCode[k + j] <= param1Int) {
/* 1019 */           k += j;
/*      */         }
/*      */       } 
/*      */       
/* 1023 */       if (this.startCharCode[k] <= param1Int && this.endCharCode[k] >= param1Int)
/*      */       {
/* 1025 */         return (char)(int)(this.startGlyphID[k] + param1Int - this.startCharCode[k]);
/*      */       }
/*      */ 
/*      */       
/* 1029 */       return Character.MIN_VALUE;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class NullCMapClass
/*      */     extends CMap
/*      */   {
/*      */     char getGlyph(int param1Int) {
/* 1038 */       return Character.MIN_VALUE;
/*      */     }
/*      */   }
/*      */   
/* 1042 */   public static final NullCMapClass theNullCmap = new NullCMapClass();
/*      */   abstract char getGlyph(int paramInt);
/*      */   final int getControlCodeGlyph(int paramInt, boolean paramBoolean) {
/* 1045 */     if (paramInt < 16) {
/* 1046 */       switch (paramInt) { case 9:
/*      */         case 10:
/*      */         case 13:
/* 1049 */           return 65535; }
/*      */     
/* 1051 */     } else if (paramInt >= 8204) {
/* 1052 */       if (paramInt <= 8207 || (paramInt >= 8232 && paramInt <= 8238) || (paramInt >= 8298 && paramInt <= 8303))
/*      */       {
/*      */         
/* 1055 */         return 65535; } 
/* 1056 */       if (paramBoolean && paramInt >= 65535) {
/* 1057 */         return 0;
/*      */       }
/*      */     } 
/* 1060 */     return -1;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\font\CMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */