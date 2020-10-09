/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ class DerIndefLenConverter
/*     */ {
/*     */   private static final int TAG_MASK = 31;
/*     */   private static final int FORM_MASK = 32;
/*     */   private static final int CLASS_MASK = 192;
/*     */   private static final int LEN_LONG = 128;
/*     */   private static final int LEN_MASK = 127;
/*     */   private static final int SKIP_EOC_BYTES = 2;
/*     */   private byte[] data;
/*     */   private byte[] newData;
/*     */   private int newDataPos;
/*     */   private int dataPos;
/*     */   private int dataSize;
/*     */   private int index;
/*  53 */   private int unresolved = 0;
/*     */   
/*  55 */   private ArrayList<Object> ndefsList = new ArrayList();
/*     */   
/*  57 */   private int numOfTotalLenBytes = 0;
/*     */   
/*     */   private boolean isEOC(int paramInt) {
/*  60 */     return ((paramInt & 0x1F) == 0 && (paramInt & 0x20) == 0 && (paramInt & 0xC0) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLongForm(int paramInt) {
/*  67 */     return ((paramInt & 0x80) == 128);
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
/*     */   static boolean isIndefinite(int paramInt) {
/*  85 */     return (isLongForm(paramInt) && (paramInt & 0x7F) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseTag() throws IOException {
/*  93 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/*  95 */     if (isEOC(this.data[this.dataPos]) && this.data[this.dataPos + 1] == 0) {
/*  96 */       int i = 0;
/*  97 */       Object object = null;
/*     */       int j;
/*  99 */       for (j = this.ndefsList.size() - 1; j >= 0; j--) {
/*     */ 
/*     */         
/* 102 */         object = this.ndefsList.get(j);
/* 103 */         if (object instanceof Integer) {
/*     */           break;
/*     */         }
/* 106 */         i += ((byte[])object).length - 3;
/*     */       } 
/*     */       
/* 109 */       if (j < 0) {
/* 110 */         throw new IOException("EOC does not have matching indefinite-length tag");
/*     */       }
/*     */       
/* 113 */       int k = this.dataPos - ((Integer)object).intValue() + i;
/*     */       
/* 115 */       byte[] arrayOfByte = getLengthBytes(k);
/* 116 */       this.ndefsList.set(j, arrayOfByte);
/* 117 */       this.unresolved--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       this.numOfTotalLenBytes += arrayOfByte.length - 3;
/*     */     } 
/* 125 */     this.dataPos++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeTag() {
/* 133 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/* 135 */     byte b = this.data[this.dataPos++];
/* 136 */     if (isEOC(b) && this.data[this.dataPos] == 0) {
/* 137 */       this.dataPos++;
/* 138 */       writeTag();
/*     */     } else {
/* 140 */       this.newData[this.newDataPos++] = (byte)b;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseLength() throws IOException {
/* 148 */     int i = 0;
/* 149 */     if (this.dataPos == this.dataSize)
/* 150 */       return i; 
/* 151 */     int j = this.data[this.dataPos++] & 0xFF;
/* 152 */     if (isIndefinite(j)) {
/* 153 */       this.ndefsList.add(new Integer(this.dataPos));
/* 154 */       this.unresolved++;
/* 155 */       return i;
/*     */     } 
/* 157 */     if (isLongForm(j)) {
/* 158 */       j &= 0x7F;
/* 159 */       if (j > 4) {
/* 160 */         throw new IOException("Too much data");
/*     */       }
/* 162 */       if (this.dataSize - this.dataPos < j + 1) {
/* 163 */         throw new IOException("Too little data");
/*     */       }
/* 165 */       for (byte b = 0; b < j; b++) {
/* 166 */         i = (i << 8) + (this.data[this.dataPos++] & 0xFF);
/*     */       }
/* 168 */       if (i < 0) {
/* 169 */         throw new IOException("Invalid length bytes");
/*     */       }
/*     */     } else {
/* 172 */       i = j & 0x7F;
/*     */     } 
/* 174 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeLengthAndValue() throws IOException {
/* 184 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/* 186 */     int i = 0;
/* 187 */     int j = this.data[this.dataPos++] & 0xFF;
/* 188 */     if (isIndefinite(j)) {
/* 189 */       byte[] arrayOfByte = (byte[])this.ndefsList.get(this.index++);
/* 190 */       System.arraycopy(arrayOfByte, 0, this.newData, this.newDataPos, arrayOfByte.length);
/*     */       
/* 192 */       this.newDataPos += arrayOfByte.length;
/*     */       return;
/*     */     } 
/* 195 */     if (isLongForm(j)) {
/* 196 */       j &= 0x7F;
/* 197 */       for (byte b = 0; b < j; b++) {
/* 198 */         i = (i << 8) + (this.data[this.dataPos++] & 0xFF);
/*     */       }
/* 200 */       if (i < 0) {
/* 201 */         throw new IOException("Invalid length bytes");
/*     */       }
/*     */     } else {
/* 204 */       i = j & 0x7F;
/*     */     } 
/* 206 */     writeLength(i);
/* 207 */     writeValue(i);
/*     */   }
/*     */   
/*     */   private void writeLength(int paramInt) {
/* 211 */     if (paramInt < 128) {
/* 212 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 214 */     else if (paramInt < 256) {
/* 215 */       this.newData[this.newDataPos++] = -127;
/* 216 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 218 */     else if (paramInt < 65536) {
/* 219 */       this.newData[this.newDataPos++] = -126;
/* 220 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 221 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 223 */     else if (paramInt < 16777216) {
/* 224 */       this.newData[this.newDataPos++] = -125;
/* 225 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 16);
/* 226 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 227 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     } else {
/*     */       
/* 230 */       this.newData[this.newDataPos++] = -124;
/* 231 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 24);
/* 232 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 16);
/* 233 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 234 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] getLengthBytes(int paramInt) {
/*     */     byte[] arrayOfByte;
/* 240 */     byte b = 0;
/*     */     
/* 242 */     if (paramInt < 128) {
/* 243 */       arrayOfByte = new byte[1];
/* 244 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 246 */     else if (paramInt < 256) {
/* 247 */       arrayOfByte = new byte[2];
/* 248 */       arrayOfByte[b++] = -127;
/* 249 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 251 */     else if (paramInt < 65536) {
/* 252 */       arrayOfByte = new byte[3];
/* 253 */       arrayOfByte[b++] = -126;
/* 254 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 255 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 257 */     else if (paramInt < 16777216) {
/* 258 */       arrayOfByte = new byte[4];
/* 259 */       arrayOfByte[b++] = -125;
/* 260 */       arrayOfByte[b++] = (byte)(paramInt >> 16);
/* 261 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 262 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     } else {
/*     */       
/* 265 */       arrayOfByte = new byte[5];
/* 266 */       arrayOfByte[b++] = -124;
/* 267 */       arrayOfByte[b++] = (byte)(paramInt >> 24);
/* 268 */       arrayOfByte[b++] = (byte)(paramInt >> 16);
/* 269 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 270 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     } 
/*     */     
/* 273 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getNumOfLenBytes(int paramInt) {
/* 279 */     byte b = 0;
/*     */     
/* 281 */     if (paramInt < 128) {
/* 282 */       b = 1;
/* 283 */     } else if (paramInt < 256) {
/* 284 */       b = 2;
/* 285 */     } else if (paramInt < 65536) {
/* 286 */       b = 3;
/* 287 */     } else if (paramInt < 16777216) {
/* 288 */       b = 4;
/*     */     } else {
/* 290 */       b = 5;
/*     */     } 
/* 292 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseValue(int paramInt) {
/* 299 */     this.dataPos += paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeValue(int paramInt) {
/* 306 */     for (byte b = 0; b < paramInt; b++) {
/* 307 */       this.newData[this.newDataPos++] = this.data[this.dataPos++];
/*     */     }
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
/*     */   byte[] convert(byte[] paramArrayOfbyte) throws IOException {
/* 321 */     this.data = paramArrayOfbyte;
/* 322 */     this.dataPos = 0; this.index = 0;
/* 323 */     this.dataSize = this.data.length;
/* 324 */     int i = 0;
/* 325 */     int j = 0;
/*     */ 
/*     */     
/* 328 */     while (this.dataPos < this.dataSize) {
/* 329 */       parseTag();
/* 330 */       i = parseLength();
/* 331 */       parseValue(i);
/* 332 */       if (this.unresolved == 0) {
/* 333 */         j = this.dataSize - this.dataPos;
/* 334 */         this.dataSize = this.dataPos;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 339 */     if (this.unresolved != 0) {
/* 340 */       throw new IOException("not all indef len BER resolved");
/*     */     }
/*     */     
/* 343 */     this.newData = new byte[this.dataSize + this.numOfTotalLenBytes + j];
/* 344 */     this.dataPos = 0; this.newDataPos = 0; this.index = 0;
/*     */ 
/*     */ 
/*     */     
/* 348 */     while (this.dataPos < this.dataSize) {
/* 349 */       writeTag();
/* 350 */       writeLengthAndValue();
/*     */     } 
/* 352 */     System.arraycopy(paramArrayOfbyte, this.dataSize, this.newData, this.dataSize + this.numOfTotalLenBytes, j);
/*     */ 
/*     */     
/* 355 */     return this.newData;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\securit\\util\DerIndefLenConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */