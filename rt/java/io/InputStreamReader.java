/*     */ package java.io;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import sun.nio.cs.StreamDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputStreamReader
/*     */   extends Reader
/*     */ {
/*     */   private final StreamDecoder sd;
/*     */   
/*     */   public InputStreamReader(InputStream paramInputStream) {
/*  72 */     super(paramInputStream);
/*     */     try {
/*  74 */       this.sd = StreamDecoder.forInputStreamReader(paramInputStream, this, (String)null);
/*  75 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/*  77 */       throw new Error(unsupportedEncodingException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStreamReader(InputStream paramInputStream, String paramString) throws UnsupportedEncodingException {
/*  97 */     super(paramInputStream);
/*  98 */     if (paramString == null)
/*  99 */       throw new NullPointerException("charsetName"); 
/* 100 */     this.sd = StreamDecoder.forInputStreamReader(paramInputStream, this, paramString);
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
/*     */   public InputStreamReader(InputStream paramInputStream, Charset paramCharset) {
/* 113 */     super(paramInputStream);
/* 114 */     if (paramCharset == null)
/* 115 */       throw new NullPointerException("charset"); 
/* 116 */     this.sd = StreamDecoder.forInputStreamReader(paramInputStream, this, paramCharset);
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
/*     */   public InputStreamReader(InputStream paramInputStream, CharsetDecoder paramCharsetDecoder) {
/* 129 */     super(paramInputStream);
/* 130 */     if (paramCharsetDecoder == null)
/* 131 */       throw new NullPointerException("charset decoder"); 
/* 132 */     this.sd = StreamDecoder.forInputStreamReader(paramInputStream, this, paramCharsetDecoder);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 156 */     return this.sd.getEncoding();
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
/*     */   public int read() throws IOException {
/* 168 */     return this.sd.read();
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
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 184 */     return this.sd.read(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ready() throws IOException {
/* 195 */     return this.sd.ready();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 199 */     this.sd.close();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\io\InputStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */