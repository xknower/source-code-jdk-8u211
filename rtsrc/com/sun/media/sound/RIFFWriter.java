/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
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
/*     */ public final class RIFFWriter
/*     */   extends OutputStream
/*     */ {
/*     */   private static interface RandomAccessWriter
/*     */   {
/*     */     void seek(long param1Long) throws IOException;
/*     */     
/*     */     long getPointer() throws IOException;
/*     */     
/*     */     void close() throws IOException;
/*     */     
/*     */     void write(int param1Int) throws IOException;
/*     */     
/*     */     void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException;
/*     */     
/*     */     void write(byte[] param1ArrayOfbyte) throws IOException;
/*     */     
/*     */     long length() throws IOException;
/*     */     
/*     */     void setLength(long param1Long) throws IOException;
/*     */   }
/*     */   
/*     */   private static class RandomAccessFileWriter
/*     */     implements RandomAccessWriter
/*     */   {
/*     */     RandomAccessFile raf;
/*     */     
/*     */     RandomAccessFileWriter(File param1File) throws FileNotFoundException {
/*  64 */       this.raf = new RandomAccessFile(param1File, "rw");
/*     */     }
/*     */     
/*     */     RandomAccessFileWriter(String param1String) throws FileNotFoundException {
/*  68 */       this.raf = new RandomAccessFile(param1String, "rw");
/*     */     }
/*     */     
/*     */     public void seek(long param1Long) throws IOException {
/*  72 */       this.raf.seek(param1Long);
/*     */     }
/*     */     
/*     */     public long getPointer() throws IOException {
/*  76 */       return this.raf.getFilePointer();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/*  80 */       this.raf.close();
/*     */     }
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/*  84 */       this.raf.write(param1Int);
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  88 */       this.raf.write(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/*  92 */       this.raf.write(param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public long length() throws IOException {
/*  96 */       return this.raf.length();
/*     */     }
/*     */     
/*     */     public void setLength(long param1Long) throws IOException {
/* 100 */       this.raf.setLength(param1Long);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RandomAccessByteWriter
/*     */     implements RandomAccessWriter {
/* 106 */     byte[] buff = new byte[32];
/* 107 */     int length = 0;
/* 108 */     int pos = 0;
/*     */     byte[] s;
/*     */     final OutputStream stream;
/*     */     
/*     */     RandomAccessByteWriter(OutputStream param1OutputStream) {
/* 113 */       this.stream = param1OutputStream;
/*     */     }
/*     */     
/*     */     public void seek(long param1Long) throws IOException {
/* 117 */       this.pos = (int)param1Long;
/*     */     }
/*     */     
/*     */     public long getPointer() throws IOException {
/* 121 */       return this.pos;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 125 */       this.stream.write(this.buff, 0, this.length);
/* 126 */       this.stream.close();
/*     */     }
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/* 130 */       if (this.s == null)
/* 131 */         this.s = new byte[1]; 
/* 132 */       this.s[0] = (byte)param1Int;
/* 133 */       write(this.s, 0, 1);
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 137 */       int i = this.pos + param1Int2;
/* 138 */       if (i > this.length)
/* 139 */         setLength(i); 
/* 140 */       int j = param1Int1 + param1Int2;
/* 141 */       for (int k = param1Int1; k < j; k++) {
/* 142 */         this.buff[this.pos++] = param1ArrayOfbyte[k];
/*     */       }
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 147 */       write(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     public long length() throws IOException {
/* 151 */       return this.length;
/*     */     }
/*     */     
/*     */     public void setLength(long param1Long) throws IOException {
/* 155 */       this.length = (int)param1Long;
/* 156 */       if (this.length > this.buff.length) {
/* 157 */         int i = Math.max(this.buff.length << 1, this.length);
/* 158 */         byte[] arrayOfByte = new byte[i];
/* 159 */         System.arraycopy(this.buff, 0, arrayOfByte, 0, this.buff.length);
/* 160 */         this.buff = arrayOfByte;
/*     */       } 
/*     */     }
/*     */   }
/* 164 */   private int chunktype = 0;
/*     */   private RandomAccessWriter raf;
/*     */   private final long chunksizepointer;
/*     */   private final long startpointer;
/* 168 */   private RIFFWriter childchunk = null;
/*     */   private boolean open = true;
/*     */   private boolean writeoverride = false;
/*     */   
/*     */   public RIFFWriter(String paramString1, String paramString2) throws IOException {
/* 173 */     this(new RandomAccessFileWriter(paramString1), paramString2, 0);
/*     */   }
/*     */   
/*     */   public RIFFWriter(File paramFile, String paramString) throws IOException {
/* 177 */     this(new RandomAccessFileWriter(paramFile), paramString, 0);
/*     */   }
/*     */   
/*     */   public RIFFWriter(OutputStream paramOutputStream, String paramString) throws IOException {
/* 181 */     this(new RandomAccessByteWriter(paramOutputStream), paramString, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private RIFFWriter(RandomAccessWriter paramRandomAccessWriter, String paramString, int paramInt) throws IOException {
/* 186 */     if (paramInt == 0 && 
/* 187 */       paramRandomAccessWriter.length() != 0L)
/* 188 */       paramRandomAccessWriter.setLength(0L); 
/* 189 */     this.raf = paramRandomAccessWriter;
/* 190 */     if (paramRandomAccessWriter.getPointer() % 2L != 0L) {
/* 191 */       paramRandomAccessWriter.write(0);
/*     */     }
/* 193 */     if (paramInt == 0) {
/* 194 */       paramRandomAccessWriter.write("RIFF".getBytes("ascii"));
/* 195 */     } else if (paramInt == 1) {
/* 196 */       paramRandomAccessWriter.write("LIST".getBytes("ascii"));
/*     */     } else {
/* 198 */       paramRandomAccessWriter.write((paramString + "    ").substring(0, 4).getBytes("ascii"));
/*     */     } 
/* 200 */     this.chunksizepointer = paramRandomAccessWriter.getPointer();
/* 201 */     this.chunktype = 2;
/* 202 */     writeUnsignedInt(0L);
/* 203 */     this.chunktype = paramInt;
/* 204 */     this.startpointer = paramRandomAccessWriter.getPointer();
/* 205 */     if (paramInt != 2) {
/* 206 */       paramRandomAccessWriter.write((paramString + "    ").substring(0, 4).getBytes("ascii"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void seek(long paramLong) throws IOException {
/* 211 */     this.raf.seek(paramLong);
/*     */   }
/*     */   
/*     */   public long getFilePointer() throws IOException {
/* 215 */     return this.raf.getPointer();
/*     */   }
/*     */   
/*     */   public void setWriteOverride(boolean paramBoolean) {
/* 219 */     this.writeoverride = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean getWriteOverride() {
/* 223 */     return this.writeoverride;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 227 */     if (!this.open)
/*     */       return; 
/* 229 */     if (this.childchunk != null) {
/* 230 */       this.childchunk.close();
/* 231 */       this.childchunk = null;
/*     */     } 
/*     */     
/* 234 */     int i = this.chunktype;
/* 235 */     long l = this.raf.getPointer();
/* 236 */     this.raf.seek(this.chunksizepointer);
/* 237 */     this.chunktype = 2;
/* 238 */     writeUnsignedInt(l - this.startpointer);
/*     */     
/* 240 */     if (i == 0) {
/* 241 */       this.raf.close();
/*     */     } else {
/* 243 */       this.raf.seek(l);
/* 244 */     }  this.open = false;
/* 245 */     this.raf = null;
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 249 */     if (!this.writeoverride) {
/* 250 */       if (this.chunktype != 2) {
/* 251 */         throw new IllegalArgumentException("Only chunks can write bytes!");
/*     */       }
/*     */       
/* 254 */       if (this.childchunk != null) {
/* 255 */         this.childchunk.close();
/* 256 */         this.childchunk = null;
/*     */       } 
/*     */     } 
/* 259 */     this.raf.write(paramInt);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 263 */     if (!this.writeoverride) {
/* 264 */       if (this.chunktype != 2) {
/* 265 */         throw new IllegalArgumentException("Only chunks can write bytes!");
/*     */       }
/*     */       
/* 268 */       if (this.childchunk != null) {
/* 269 */         this.childchunk.close();
/* 270 */         this.childchunk = null;
/*     */       } 
/*     */     } 
/* 273 */     this.raf.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public RIFFWriter writeList(String paramString) throws IOException {
/* 277 */     if (this.chunktype == 2) {
/* 278 */       throw new IllegalArgumentException("Only LIST and RIFF can write lists!");
/*     */     }
/*     */     
/* 281 */     if (this.childchunk != null) {
/* 282 */       this.childchunk.close();
/* 283 */       this.childchunk = null;
/*     */     } 
/* 285 */     this.childchunk = new RIFFWriter(this.raf, paramString, 1);
/* 286 */     return this.childchunk;
/*     */   }
/*     */   
/*     */   public RIFFWriter writeChunk(String paramString) throws IOException {
/* 290 */     if (this.chunktype == 2) {
/* 291 */       throw new IllegalArgumentException("Only LIST and RIFF can write chunks!");
/*     */     }
/*     */     
/* 294 */     if (this.childchunk != null) {
/* 295 */       this.childchunk.close();
/* 296 */       this.childchunk = null;
/*     */     } 
/* 298 */     this.childchunk = new RIFFWriter(this.raf, paramString, 2);
/* 299 */     return this.childchunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String paramString) throws IOException {
/* 304 */     byte[] arrayOfByte = paramString.getBytes();
/* 305 */     write(arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String paramString, int paramInt) throws IOException {
/* 310 */     byte[] arrayOfByte = paramString.getBytes();
/* 311 */     if (arrayOfByte.length > paramInt) {
/* 312 */       write(arrayOfByte, 0, paramInt);
/*     */     } else {
/* 314 */       write(arrayOfByte);
/* 315 */       for (int i = arrayOfByte.length; i < paramInt; i++) {
/* 316 */         write(0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeByte(int paramInt) throws IOException {
/* 322 */     write(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(short paramShort) throws IOException {
/* 327 */     write(paramShort >>> 0 & 0xFF);
/* 328 */     write(paramShort >>> 8 & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt(int paramInt) throws IOException {
/* 333 */     write(paramInt >>> 0 & 0xFF);
/* 334 */     write(paramInt >>> 8 & 0xFF);
/* 335 */     write(paramInt >>> 16 & 0xFF);
/* 336 */     write(paramInt >>> 24 & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long paramLong) throws IOException {
/* 341 */     write((int)(paramLong >>> 0L) & 0xFF);
/* 342 */     write((int)(paramLong >>> 8L) & 0xFF);
/* 343 */     write((int)(paramLong >>> 16L) & 0xFF);
/* 344 */     write((int)(paramLong >>> 24L) & 0xFF);
/* 345 */     write((int)(paramLong >>> 32L) & 0xFF);
/* 346 */     write((int)(paramLong >>> 40L) & 0xFF);
/* 347 */     write((int)(paramLong >>> 48L) & 0xFF);
/* 348 */     write((int)(paramLong >>> 56L) & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUnsignedByte(int paramInt) throws IOException {
/* 353 */     writeByte((byte)paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUnsignedShort(int paramInt) throws IOException {
/* 358 */     writeShort((short)paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUnsignedInt(long paramLong) throws IOException {
/* 363 */     writeInt((int)paramLong);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\media\sound\RIFFWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */