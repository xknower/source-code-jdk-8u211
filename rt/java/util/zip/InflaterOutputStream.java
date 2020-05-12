/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class InflaterOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   protected final Inflater inf;
/*     */   protected final byte[] buf;
/*  52 */   private final byte[] wbuf = new byte[1];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean usesDefaultInflater = false;
/*     */ 
/*     */   
/*     */   private boolean closed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  64 */     if (this.closed) {
/*  65 */       throw new IOException("Stream closed");
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
/*     */   public InflaterOutputStream(OutputStream paramOutputStream) {
/*  77 */     this(paramOutputStream, new Inflater());
/*  78 */     this.usesDefaultInflater = true;
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
/*     */   public InflaterOutputStream(OutputStream paramOutputStream, Inflater paramInflater) {
/*  90 */     this(paramOutputStream, paramInflater, 512);
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
/*     */   public InflaterOutputStream(OutputStream paramOutputStream, Inflater paramInflater, int paramInt) {
/* 104 */     super(paramOutputStream);
/*     */ 
/*     */     
/* 107 */     if (paramOutputStream == null)
/* 108 */       throw new NullPointerException("Null output"); 
/* 109 */     if (paramInflater == null)
/* 110 */       throw new NullPointerException("Null inflater"); 
/* 111 */     if (paramInt <= 0) {
/* 112 */       throw new IllegalArgumentException("Buffer size < 1");
/*     */     }
/*     */     
/* 115 */     this.inf = paramInflater;
/* 116 */     this.buf = new byte[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 126 */     if (!this.closed) {
/*     */       
/*     */       try {
/* 129 */         finish();
/*     */       } finally {
/* 131 */         this.out.close();
/* 132 */         this.closed = true;
/*     */       } 
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
/*     */   public void flush() throws IOException {
/* 145 */     ensureOpen();
/*     */ 
/*     */     
/* 148 */     if (!this.inf.finished()) {
/*     */       try {
/* 150 */         while (!this.inf.finished() && !this.inf.needsInput()) {
/*     */ 
/*     */ 
/*     */           
/* 154 */           int i = this.inf.inflate(this.buf, 0, this.buf.length);
/* 155 */           if (i < 1) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 160 */           this.out.write(this.buf, 0, i);
/*     */         } 
/* 162 */         super.flush();
/* 163 */       } catch (DataFormatException dataFormatException) {
/*     */         
/* 165 */         String str = dataFormatException.getMessage();
/* 166 */         if (str == null) {
/* 167 */           str = "Invalid ZLIB data format";
/*     */         }
/* 169 */         throw new ZipException(str);
/*     */       } 
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
/*     */   public void finish() throws IOException {
/* 183 */     ensureOpen();
/*     */ 
/*     */     
/* 186 */     flush();
/* 187 */     if (this.usesDefaultInflater) {
/* 188 */       this.inf.end();
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
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 203 */     this.wbuf[0] = (byte)paramInt;
/* 204 */     write(this.wbuf, 0, 1);
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 223 */     ensureOpen();
/* 224 */     if (paramArrayOfbyte == null)
/* 225 */       throw new NullPointerException("Null buffer for read"); 
/* 226 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/* 227 */       throw new IndexOutOfBoundsException(); 
/* 228 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/*     */         int i;
/*     */ 
/*     */         
/* 238 */         if (this.inf.needsInput()) {
/*     */ 
/*     */           
/* 241 */           if (paramInt2 < 1) {
/*     */             break;
/*     */           }
/*     */           
/* 245 */           byte b = (paramInt2 < 512) ? paramInt2 : 512;
/* 246 */           this.inf.setInput(paramArrayOfbyte, paramInt1, b);
/* 247 */           paramInt1 += b;
/* 248 */           paramInt2 -= b;
/*     */         } 
/*     */ 
/*     */         
/*     */         do {
/* 253 */           i = this.inf.inflate(this.buf, 0, this.buf.length);
/* 254 */           if (i <= 0)
/* 255 */             continue;  this.out.write(this.buf, 0, i);
/*     */         }
/* 257 */         while (i > 0);
/*     */ 
/*     */         
/* 260 */         if (this.inf.finished()) {
/*     */           break;
/*     */         }
/* 263 */         if (this.inf.needsDictionary()) {
/* 264 */           throw new ZipException("ZLIB dictionary missing");
/*     */         }
/*     */       } 
/* 267 */     } catch (DataFormatException dataFormatException) {
/*     */       
/* 269 */       String str = dataFormatException.getMessage();
/* 270 */       if (str == null) {
/* 271 */         str = "Invalid ZLIB data format";
/*     */       }
/* 273 */       throw new ZipException(str);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\zip\InflaterOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */