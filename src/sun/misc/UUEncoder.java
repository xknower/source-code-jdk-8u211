/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUEncoder
/*     */   extends CharacterEncoder
/*     */ {
/*     */   private String bufferName;
/*     */   private int mode;
/*     */   
/*     */   public UUEncoder() {
/*  94 */     this.bufferName = "encoder.buf";
/*  95 */     this.mode = 644;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUEncoder(String paramString) {
/* 105 */     this.bufferName = paramString;
/* 106 */     this.mode = 644;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUEncoder(String paramString, int paramInt) {
/* 116 */     this.bufferName = paramString;
/* 117 */     this.mode = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bytesPerAtom() {
/* 122 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bytesPerLine() {
/* 127 */     return 45;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeAtom(OutputStream paramOutputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 138 */     byte b1 = 1, b2 = 1;
/*     */ 
/*     */     
/* 141 */     byte b = paramArrayOfbyte[paramInt1];
/* 142 */     if (paramInt2 > 1) {
/* 143 */       b1 = paramArrayOfbyte[paramInt1 + 1];
/*     */     }
/* 145 */     if (paramInt2 > 2) {
/* 146 */       b2 = paramArrayOfbyte[paramInt1 + 2];
/*     */     }
/*     */     
/* 149 */     int i = b >>> 2 & 0x3F;
/* 150 */     int j = b << 4 & 0x30 | b1 >>> 4 & 0xF;
/* 151 */     int k = b1 << 2 & 0x3C | b2 >>> 6 & 0x3;
/* 152 */     int m = b2 & 0x3F;
/* 153 */     paramOutputStream.write(i + 32);
/* 154 */     paramOutputStream.write(j + 32);
/* 155 */     paramOutputStream.write(k + 32);
/* 156 */     paramOutputStream.write(m + 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLinePrefix(OutputStream paramOutputStream, int paramInt) throws IOException {
/* 166 */     paramOutputStream.write((paramInt & 0x3F) + 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLineSuffix(OutputStream paramOutputStream) throws IOException {
/* 174 */     this.pStream.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeBufferPrefix(OutputStream paramOutputStream) throws IOException {
/* 181 */     this.pStream = new PrintStream(paramOutputStream);
/* 182 */     this.pStream.print("begin " + this.mode + " ");
/* 183 */     if (this.bufferName != null) {
/* 184 */       this.pStream.println(this.bufferName);
/*     */     } else {
/* 186 */       this.pStream.println("encoder.bin");
/*     */     } 
/* 188 */     this.pStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeBufferSuffix(OutputStream paramOutputStream) throws IOException {
/* 196 */     this.pStream.println(" \nend");
/* 197 */     this.pStream.flush();
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\misc\UUEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */