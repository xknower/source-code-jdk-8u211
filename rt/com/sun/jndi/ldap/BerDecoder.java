/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public final class BerDecoder
/*     */   extends Ber
/*     */ {
/*     */   private int origOffset;
/*     */   
/*     */   public BerDecoder(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  45 */     this.buf = paramArrayOfbyte;
/*  46 */     this.bufsize = paramInt2;
/*  47 */     this.origOffset = paramInt1;
/*     */     
/*  49 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  57 */     this.offset = this.origOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParsePosition() {
/*  66 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseLength() throws Ber.DecodeException {
/*  74 */     int i = parseByte();
/*     */     
/*  76 */     if ((i & 0x80) == 128) {
/*     */       
/*  78 */       i &= 0x7F;
/*     */       
/*  80 */       if (i == 0) {
/*  81 */         throw new Ber.DecodeException("Indefinite length not supported");
/*     */       }
/*     */ 
/*     */       
/*  85 */       if (i > 4) {
/*  86 */         throw new Ber.DecodeException("encoding too long");
/*     */       }
/*     */       
/*  89 */       if (this.bufsize - this.offset < i) {
/*  90 */         throw new Ber.DecodeException("Insufficient data");
/*     */       }
/*     */       
/*  93 */       int j = 0;
/*     */       
/*  95 */       for (byte b = 0; b < i; b++) {
/*  96 */         j = (j << 8) + (this.buf[this.offset++] & 0xFF);
/*     */       }
/*  98 */       if (j < 0) {
/*  99 */         throw new Ber.DecodeException("Invalid length bytes");
/*     */       }
/* 101 */       return j;
/*     */     } 
/* 103 */     return i;
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
/*     */   public int parseSeq(int[] paramArrayOfint) throws Ber.DecodeException {
/* 115 */     int i = parseByte();
/* 116 */     int j = parseLength();
/* 117 */     if (paramArrayOfint != null) {
/* 118 */       paramArrayOfint[0] = j;
/*     */     }
/* 120 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void seek(int paramInt) throws Ber.DecodeException {
/* 129 */     if (this.offset + paramInt > this.bufsize || this.offset + paramInt < 0) {
/* 130 */       throw new Ber.DecodeException("array index out of bounds");
/*     */     }
/* 132 */     this.offset += paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseByte() throws Ber.DecodeException {
/* 140 */     if (this.bufsize - this.offset < 1) {
/* 141 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/* 143 */     return this.buf[this.offset++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int peekByte() throws Ber.DecodeException {
/* 152 */     if (this.bufsize - this.offset < 1) {
/* 153 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/* 155 */     return this.buf[this.offset] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean parseBoolean() throws Ber.DecodeException {
/* 163 */     return !(parseIntWithTag(1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseEnumeration() throws Ber.DecodeException {
/* 171 */     return parseIntWithTag(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseInt() throws Ber.DecodeException {
/* 179 */     return parseIntWithTag(2);
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
/*     */   private int parseIntWithTag(int paramInt) throws Ber.DecodeException {
/* 191 */     if (parseByte() != paramInt) {
/* 192 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + 
/* 193 */           Integer.toString(this.buf[this.offset - 1] & 0xFF) + " (expected tag " + 
/* 194 */           Integer.toString(paramInt) + ")");
/*     */     }
/*     */     
/* 197 */     int i = parseLength();
/*     */     
/* 199 */     if (i > 4)
/* 200 */       throw new Ber.DecodeException("INTEGER too long"); 
/* 201 */     if (i > this.bufsize - this.offset) {
/* 202 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */     
/* 205 */     byte b = this.buf[this.offset++];
/* 206 */     int j = 0;
/*     */     
/* 208 */     j = b & Byte.MAX_VALUE;
/* 209 */     for (byte b1 = 1; b1 < i; b1++) {
/* 210 */       j <<= 8;
/* 211 */       j |= this.buf[this.offset++] & 0xFF;
/*     */     } 
/*     */     
/* 214 */     if ((b & 0x80) == 128) {
/* 215 */       j = -j;
/*     */     }
/*     */     
/* 218 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parseString(boolean paramBoolean) throws Ber.DecodeException {
/* 225 */     return parseStringWithTag(4, paramBoolean, (int[])null);
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
/*     */   public String parseStringWithTag(int paramInt, boolean paramBoolean, int[] paramArrayOfint) throws Ber.DecodeException {
/*     */     String str;
/* 244 */     int j = this.offset;
/*     */     int i;
/* 246 */     if ((i = parseByte()) != paramInt) {
/* 247 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + 
/* 248 */           Integer.toString((byte)i) + " (expected tag " + paramInt + ")");
/*     */     }
/*     */     
/* 251 */     int k = parseLength();
/*     */     
/* 253 */     if (k > this.bufsize - this.offset) {
/* 254 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */ 
/*     */     
/* 258 */     if (k == 0) {
/* 259 */       str = "";
/*     */     } else {
/* 261 */       byte[] arrayOfByte = new byte[k];
/*     */       
/* 263 */       System.arraycopy(this.buf, this.offset, arrayOfByte, 0, k);
/* 264 */       if (paramBoolean) {
/*     */         try {
/* 266 */           str = new String(arrayOfByte, "UTF8");
/* 267 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 268 */           throw new Ber.DecodeException("UTF8 not available on platform");
/*     */         } 
/*     */       } else {
/*     */         try {
/* 272 */           str = new String(arrayOfByte, "8859_1");
/* 273 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 274 */           throw new Ber.DecodeException("8859_1 not available on platform");
/*     */         } 
/*     */       } 
/* 277 */       this.offset += k;
/*     */     } 
/*     */     
/* 280 */     if (paramArrayOfint != null) {
/* 281 */       paramArrayOfint[0] = this.offset - j;
/*     */     }
/*     */     
/* 284 */     return str;
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
/*     */   public byte[] parseOctetString(int paramInt, int[] paramArrayOfint) throws Ber.DecodeException {
/* 303 */     int i = this.offset;
/*     */     int j;
/* 305 */     if ((j = parseByte()) != paramInt)
/*     */     {
/* 307 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + 
/* 308 */           Integer.toString(j) + " (expected tag " + 
/* 309 */           Integer.toString(paramInt) + ")");
/*     */     }
/*     */     
/* 312 */     int k = parseLength();
/*     */     
/* 314 */     if (k > this.bufsize - this.offset) {
/* 315 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */     
/* 318 */     byte[] arrayOfByte = new byte[k];
/* 319 */     if (k > 0) {
/* 320 */       System.arraycopy(this.buf, this.offset, arrayOfByte, 0, k);
/* 321 */       this.offset += k;
/*     */     } 
/*     */     
/* 324 */     if (paramArrayOfint != null) {
/* 325 */       paramArrayOfint[0] = this.offset - i;
/*     */     }
/*     */     
/* 328 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int bytesLeft() {
/* 335 */     return this.bufsize - this.offset;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jndi\ldap\BerDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */