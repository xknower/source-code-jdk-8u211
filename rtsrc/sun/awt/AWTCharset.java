/*     */ package sun.awt;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AWTCharset
/*     */   extends Charset
/*     */ {
/*     */   protected Charset awtCs;
/*     */   protected Charset javaCs;
/*     */   
/*     */   public AWTCharset(String paramString, Charset paramCharset) {
/*  41 */     super(paramString, null);
/*  42 */     this.javaCs = paramCharset;
/*  43 */     this.awtCs = this;
/*     */   }
/*     */   
/*     */   public boolean contains(Charset paramCharset) {
/*  47 */     if (this.javaCs == null) return false; 
/*  48 */     return this.javaCs.contains(paramCharset);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  52 */     if (this.javaCs == null)
/*  53 */       throw new Error("Encoder is not supported by this Charset"); 
/*  54 */     return new Encoder(this.javaCs.newEncoder());
/*     */   }
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  58 */     if (this.javaCs == null)
/*  59 */       throw new Error("Decoder is not supported by this Charset"); 
/*  60 */     return new Decoder(this.javaCs.newDecoder());
/*     */   }
/*     */   
/*     */   public class Encoder extends CharsetEncoder { protected CharsetEncoder enc;
/*     */     
/*     */     protected Encoder() {
/*  66 */       this(AWTCharset.this.javaCs.newEncoder());
/*     */     }
/*     */     protected Encoder(CharsetEncoder param1CharsetEncoder) {
/*  69 */       super(AWTCharset.this.awtCs, param1CharsetEncoder
/*  70 */           .averageBytesPerChar(), param1CharsetEncoder
/*  71 */           .maxBytesPerChar());
/*  72 */       this.enc = param1CharsetEncoder;
/*     */     }
/*     */     public boolean canEncode(char param1Char) {
/*  75 */       return this.enc.canEncode(param1Char);
/*     */     }
/*     */     public boolean canEncode(CharSequence param1CharSequence) {
/*  78 */       return this.enc.canEncode(param1CharSequence);
/*     */     }
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*  81 */       return this.enc.encode(param1CharBuffer, param1ByteBuffer, true);
/*     */     }
/*     */     protected CoderResult implFlush(ByteBuffer param1ByteBuffer) {
/*  84 */       return this.enc.flush(param1ByteBuffer);
/*     */     }
/*     */     protected void implReset() {
/*  87 */       this.enc.reset();
/*     */     }
/*     */     protected void implReplaceWith(byte[] param1ArrayOfbyte) {
/*  90 */       if (this.enc != null)
/*  91 */         this.enc.replaceWith(param1ArrayOfbyte); 
/*     */     }
/*     */     protected void implOnMalformedInput(CodingErrorAction param1CodingErrorAction) {
/*  94 */       this.enc.onMalformedInput(param1CodingErrorAction);
/*     */     }
/*     */     protected void implOnUnmappableCharacter(CodingErrorAction param1CodingErrorAction) {
/*  97 */       this.enc.onUnmappableCharacter(param1CodingErrorAction);
/*     */     }
/*     */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) {
/* 100 */       return true;
/*     */     } }
/*     */   
/*     */   public class Decoder extends CharsetDecoder {
/*     */     protected CharsetDecoder dec;
/*     */     private String nr;
/*     */     ByteBuffer fbb;
/*     */     
/*     */     protected Decoder() {
/* 109 */       this(AWTCharset.this.javaCs.newDecoder());
/*     */     }
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/*     */       return this.dec.decode(param1ByteBuffer, param1CharBuffer, true);
/* 113 */     } protected Decoder(CharsetDecoder param1CharsetDecoder) { super(AWTCharset.this.awtCs, param1CharsetDecoder
/* 114 */           .averageCharsPerByte(), param1CharsetDecoder
/* 115 */           .maxCharsPerByte());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       this.fbb = ByteBuffer.allocate(0);
/*     */       this.dec = param1CharsetDecoder; } protected CoderResult implFlush(CharBuffer param1CharBuffer) {
/* 123 */       this.dec.decode(this.fbb, param1CharBuffer, true);
/* 124 */       return this.dec.flush(param1CharBuffer);
/*     */     }
/*     */     protected void implReset() {
/* 127 */       this.dec.reset();
/*     */     }
/*     */     protected void implReplaceWith(String param1String) {
/* 130 */       if (this.dec != null)
/* 131 */         this.dec.replaceWith(param1String); 
/*     */     }
/*     */     protected void implOnMalformedInput(CodingErrorAction param1CodingErrorAction) {
/* 134 */       this.dec.onMalformedInput(param1CodingErrorAction);
/*     */     }
/*     */     protected void implOnUnmappableCharacter(CodingErrorAction param1CodingErrorAction) {
/* 137 */       this.dec.onUnmappableCharacter(param1CodingErrorAction);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\AWTCharset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */