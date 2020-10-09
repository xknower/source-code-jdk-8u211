/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MIMEParser
/*     */   implements Iterable<MIMEEvent>
/*     */ {
/*  55 */   private static final Logger LOGGER = Logger.getLogger(MIMEParser.class.getName());
/*     */   
/*     */   private static final String HEADER_ENCODING = "ISO8859-1";
/*     */   
/*     */   private static final int NO_LWSP = 1000;
/*     */   
/*     */   private enum STATE
/*     */   {
/*  63 */     START_MESSAGE, SKIP_PREAMBLE, START_PART, HEADERS, BODY, END_PART, END_MESSAGE; }
/*  64 */   private STATE state = STATE.START_MESSAGE;
/*     */   
/*     */   private final InputStream in;
/*     */   private final byte[] bndbytes;
/*     */   private final int bl;
/*     */   private final MIMEConfig config;
/*  70 */   private final int[] bcs = new int[128];
/*     */ 
/*     */   
/*     */   private final int[] gss;
/*     */ 
/*     */   
/*     */   private boolean parsed;
/*     */ 
/*     */   
/*     */   private boolean done = false;
/*     */ 
/*     */   
/*     */   private boolean eof;
/*     */   
/*     */   private final int capacity;
/*     */   
/*     */   private byte[] buf;
/*     */   
/*     */   private int len;
/*     */   
/*     */   private boolean bol;
/*     */ 
/*     */   
/*     */   MIMEParser(InputStream in, String boundary, MIMEConfig config) {
/*  94 */     this.in = in;
/*  95 */     this.bndbytes = getBytes("--" + boundary);
/*  96 */     this.bl = this.bndbytes.length;
/*  97 */     this.config = config;
/*  98 */     this.gss = new int[this.bl];
/*  99 */     compileBoundaryPattern();
/*     */ 
/*     */     
/* 102 */     this.capacity = config.chunkSize + 2 + this.bl + 4 + 1000;
/* 103 */     createBuf(this.capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<MIMEEvent> iterator() {
/* 114 */     return new MIMEEventIterator();
/*     */   }
/*     */   
/*     */   class MIMEEventIterator
/*     */     implements Iterator<MIMEEvent>
/*     */   {
/*     */     public boolean hasNext() {
/* 121 */       return !MIMEParser.this.parsed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MIMEEvent next() {
/*     */       // Byte code:
/*     */       //   0: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$1.$SwitchMap$com$sun$xml$internal$org$jvnet$mimepull$MIMEParser$STATE : [I
/*     */       //   3: aload_0
/*     */       //   4: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   7: invokestatic access$100 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   10: invokevirtual ordinal : ()I
/*     */       //   13: iaload
/*     */       //   14: tableswitch default -> 390, 1 -> 56, 2 -> 97, 3 -> 130, 4 -> 171, 5 -> 234, 6 -> 286, 7 -> 351
/*     */       //   56: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   59: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   62: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   65: ifeq -> 82
/*     */       //   68: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   71: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   74: ldc 'MIMEParser state={0}'
/*     */       //   76: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.START_MESSAGE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   79: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   82: aload_0
/*     */       //   83: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   86: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.SKIP_PREAMBLE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   89: invokestatic access$102 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   92: pop
/*     */       //   93: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent.START_MESSAGE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$StartMessage;
/*     */       //   96: areturn
/*     */       //   97: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   100: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   103: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   106: ifeq -> 123
/*     */       //   109: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   112: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   115: ldc 'MIMEParser state={0}'
/*     */       //   117: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.SKIP_PREAMBLE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   120: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   123: aload_0
/*     */       //   124: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   127: invokestatic access$300 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)V
/*     */       //   130: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   133: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   136: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   139: ifeq -> 156
/*     */       //   142: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   145: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   148: ldc 'MIMEParser state={0}'
/*     */       //   150: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.START_PART : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   153: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   156: aload_0
/*     */       //   157: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   160: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.HEADERS : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   163: invokestatic access$102 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   166: pop
/*     */       //   167: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent.START_PART : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$StartPart;
/*     */       //   170: areturn
/*     */       //   171: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   174: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   177: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   180: ifeq -> 197
/*     */       //   183: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   186: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   189: ldc 'MIMEParser state={0}'
/*     */       //   191: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.HEADERS : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   194: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   197: aload_0
/*     */       //   198: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   201: invokestatic access$400 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)Lcom/sun/xml/internal/org/jvnet/mimepull/InternetHeaders;
/*     */       //   204: astore_1
/*     */       //   205: aload_0
/*     */       //   206: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   209: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.BODY : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   212: invokestatic access$102 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   215: pop
/*     */       //   216: aload_0
/*     */       //   217: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   220: iconst_1
/*     */       //   221: invokestatic access$502 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Z)Z
/*     */       //   224: pop
/*     */       //   225: new com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$Headers
/*     */       //   228: dup
/*     */       //   229: aload_1
/*     */       //   230: invokespecial <init> : (Lcom/sun/xml/internal/org/jvnet/mimepull/InternetHeaders;)V
/*     */       //   233: areturn
/*     */       //   234: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   237: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   240: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   243: ifeq -> 260
/*     */       //   246: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   249: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   252: ldc 'MIMEParser state={0}'
/*     */       //   254: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.BODY : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   257: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   260: aload_0
/*     */       //   261: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   264: invokestatic access$600 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)Ljava/nio/ByteBuffer;
/*     */       //   267: astore_2
/*     */       //   268: aload_0
/*     */       //   269: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   272: iconst_0
/*     */       //   273: invokestatic access$502 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Z)Z
/*     */       //   276: pop
/*     */       //   277: new com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$Content
/*     */       //   280: dup
/*     */       //   281: aload_2
/*     */       //   282: invokespecial <init> : (Ljava/nio/ByteBuffer;)V
/*     */       //   285: areturn
/*     */       //   286: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   289: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   292: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   295: ifeq -> 312
/*     */       //   298: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   301: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   304: ldc 'MIMEParser state={0}'
/*     */       //   306: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.END_PART : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   309: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   312: aload_0
/*     */       //   313: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   316: invokestatic access$700 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)Z
/*     */       //   319: ifeq -> 336
/*     */       //   322: aload_0
/*     */       //   323: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   326: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.END_MESSAGE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   329: invokestatic access$102 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   332: pop
/*     */       //   333: goto -> 347
/*     */       //   336: aload_0
/*     */       //   337: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   340: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.START_PART : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   343: invokestatic access$102 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   346: pop
/*     */       //   347: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent.END_PART : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$EndPart;
/*     */       //   350: areturn
/*     */       //   351: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   354: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   357: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*     */       //   360: ifeq -> 377
/*     */       //   363: invokestatic access$200 : ()Ljava/util/logging/Logger;
/*     */       //   366: getstatic java/util/logging/Level.FINER : Ljava/util/logging/Level;
/*     */       //   369: ldc 'MIMEParser state={0}'
/*     */       //   371: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE.END_MESSAGE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   374: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   377: aload_0
/*     */       //   378: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   381: iconst_1
/*     */       //   382: invokestatic access$002 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;Z)Z
/*     */       //   385: pop
/*     */       //   386: getstatic com/sun/xml/internal/org/jvnet/mimepull/MIMEEvent.END_MESSAGE : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEEvent$EndMessage;
/*     */       //   389: areturn
/*     */       //   390: new com/sun/xml/internal/org/jvnet/mimepull/MIMEParsingException
/*     */       //   393: dup
/*     */       //   394: new java/lang/StringBuilder
/*     */       //   397: dup
/*     */       //   398: invokespecial <init> : ()V
/*     */       //   401: ldc 'Unknown Parser state = '
/*     */       //   403: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */       //   406: aload_0
/*     */       //   407: getfield this$0 : Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;
/*     */       //   410: invokestatic access$100 : (Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser;)Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$STATE;
/*     */       //   413: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */       //   416: invokevirtual toString : ()Ljava/lang/String;
/*     */       //   419: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   422: athrow
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       //   #128	-> 56
/*     */       //   #129	-> 82
/*     */       //   #130	-> 93
/*     */       //   #133	-> 97
/*     */       //   #134	-> 123
/*     */       //   #137	-> 130
/*     */       //   #138	-> 156
/*     */       //   #139	-> 167
/*     */       //   #142	-> 171
/*     */       //   #143	-> 197
/*     */       //   #144	-> 205
/*     */       //   #145	-> 216
/*     */       //   #146	-> 225
/*     */       //   #149	-> 234
/*     */       //   #150	-> 260
/*     */       //   #151	-> 268
/*     */       //   #152	-> 277
/*     */       //   #155	-> 286
/*     */       //   #156	-> 312
/*     */       //   #157	-> 322
/*     */       //   #159	-> 336
/*     */       //   #161	-> 347
/*     */       //   #164	-> 351
/*     */       //   #165	-> 377
/*     */       //   #166	-> 386
/*     */       //   #169	-> 390
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   205	29	1	ih	Lcom/sun/xml/internal/org/jvnet/mimepull/InternetHeaders;
/*     */       //   268	18	2	buf	Ljava/nio/ByteBuffer;
/*     */       //   0	423	0	this	Lcom/sun/xml/internal/org/jvnet/mimepull/MIMEParser$MIMEEventIterator;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 175 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InternetHeaders readHeaders() {
/* 185 */     if (!this.eof) {
/* 186 */       fillBuf();
/*     */     }
/* 188 */     return new InternetHeaders(new LineInputStream());
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
/*     */   private ByteBuffer readBody() {
/* 200 */     if (!this.eof) {
/* 201 */       fillBuf();
/*     */     }
/* 203 */     int start = match(this.buf, 0, this.len);
/* 204 */     if (start == -1) {
/*     */       
/* 206 */       assert this.eof || this.len >= this.config.chunkSize;
/* 207 */       int chunkSize = this.eof ? this.len : this.config.chunkSize;
/* 208 */       if (this.eof) {
/* 209 */         this.done = true;
/* 210 */         throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */       } 
/* 212 */       return adjustBuf(chunkSize, this.len - chunkSize);
/*     */     } 
/*     */ 
/*     */     
/* 216 */     int chunkLen = start;
/* 217 */     if (!this.bol || start != 0)
/*     */     {
/* 219 */       if (start > 0 && (this.buf[start - 1] == 10 || this.buf[start - 1] == 13)) {
/* 220 */         chunkLen--;
/* 221 */         if (this.buf[start - 1] == 10 && start > 1 && this.buf[start - 2] == 13) {
/* 222 */           chunkLen--;
/*     */         }
/*     */       } else {
/* 225 */         return adjustBuf(start + 1, this.len - start - 1);
/*     */       } 
/*     */     }
/* 228 */     if (start + this.bl + 1 < this.len && this.buf[start + this.bl] == 45 && this.buf[start + this.bl + 1] == 45) {
/* 229 */       this.state = STATE.END_PART;
/* 230 */       this.done = true;
/* 231 */       return adjustBuf(chunkLen, 0);
/*     */     } 
/*     */ 
/*     */     
/* 235 */     int lwsp = 0;
/* 236 */     for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 237 */       lwsp++;
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (start + this.bl + lwsp < this.len && this.buf[start + this.bl + lwsp] == 10) {
/* 242 */       this.state = STATE.END_PART;
/* 243 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 1);
/* 244 */     }  if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp] == 13 && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 245 */       this.state = STATE.END_PART;
/* 246 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 2);
/* 247 */     }  if (start + this.bl + lwsp + 1 < this.len)
/* 248 */       return adjustBuf(chunkLen + 1, this.len - chunkLen - 1); 
/* 249 */     if (this.eof) {
/* 250 */       this.done = true;
/* 251 */       throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return adjustBuf(chunkLen, this.len - chunkLen);
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
/*     */   private ByteBuffer adjustBuf(int chunkSize, int remaining) {
/* 268 */     assert this.buf != null;
/* 269 */     assert chunkSize >= 0;
/* 270 */     assert remaining >= 0;
/*     */     
/* 272 */     byte[] temp = this.buf;
/*     */     
/* 274 */     createBuf(remaining);
/* 275 */     System.arraycopy(temp, this.len - remaining, this.buf, 0, remaining);
/* 276 */     this.len = remaining;
/*     */     
/* 278 */     return ByteBuffer.wrap(temp, 0, chunkSize);
/*     */   }
/*     */   
/*     */   private void createBuf(int min) {
/* 282 */     this.buf = new byte[(min < this.capacity) ? this.capacity : min];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skipPreamble() {
/*     */     while (true) {
/* 291 */       if (!this.eof) {
/* 292 */         fillBuf();
/*     */       }
/* 294 */       int start = match(this.buf, 0, this.len);
/* 295 */       if (start == -1) {
/*     */         
/* 297 */         if (this.eof) {
/* 298 */           throw new MIMEParsingException("Missing start boundary");
/*     */         }
/* 300 */         adjustBuf(this.len - this.bl + 1, this.bl - 1);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 305 */       if (start > this.config.chunkSize) {
/* 306 */         adjustBuf(start, this.len - start);
/*     */         
/*     */         continue;
/*     */       } 
/* 310 */       int lwsp = 0;
/* 311 */       for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 312 */         lwsp++;
/*     */       }
/*     */       
/* 315 */       if (start + this.bl + lwsp < this.len && (this.buf[start + this.bl + lwsp] == 10 || this.buf[start + this.bl + lwsp] == 13)) {
/* 316 */         if (this.buf[start + this.bl + lwsp] == 10) {
/* 317 */           adjustBuf(start + this.bl + lwsp + 1, this.len - start - this.bl - lwsp - 1); break;
/*     */         } 
/* 319 */         if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 320 */           adjustBuf(start + this.bl + lwsp + 2, this.len - start - this.bl - lwsp - 2);
/*     */           break;
/*     */         } 
/*     */       } 
/* 324 */       adjustBuf(start + 1, this.len - start - 1);
/*     */     } 
/* 326 */     if (LOGGER.isLoggable(Level.FINE)) LOGGER.log(Level.FINE, "Skipped the preamble. buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private static byte[] getBytes(String s) {
/* 330 */     char[] chars = s.toCharArray();
/* 331 */     int size = chars.length;
/* 332 */     byte[] bytes = new byte[size];
/*     */     
/* 334 */     for (int i = 0; i < size;) {
/* 335 */       bytes[i] = (byte)chars[i++];
/*     */     }
/* 337 */     return bytes;
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
/*     */   private void compileBoundaryPattern() {
/*     */     int i;
/* 355 */     for (i = 0; i < this.bndbytes.length; i++) {
/* 356 */       this.bcs[this.bndbytes[i] & Byte.MAX_VALUE] = i + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 361 */     for (i = this.bndbytes.length; i > 0; i--) {
/*     */       
/* 363 */       int j = this.bndbytes.length - 1; while (true) { if (j >= i) {
/*     */           
/* 365 */           if (this.bndbytes[j] == this.bndbytes[j - i]) {
/*     */             
/* 367 */             this.gss[j - 1] = i;
/*     */ 
/*     */             
/*     */             j--;
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 377 */         while (j > 0)
/* 378 */           this.gss[--j] = i; 
/*     */         break; }
/*     */     
/*     */     } 
/* 382 */     this.gss[this.bndbytes.length - 1] = 1;
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
/*     */   private int match(byte[] mybuf, int off, int len) {
/* 396 */     int last = len - this.bndbytes.length;
/*     */ 
/*     */     
/* 399 */     label15: while (off <= last) {
/*     */       
/* 401 */       for (int j = this.bndbytes.length - 1; j >= 0; j--) {
/* 402 */         byte ch = mybuf[off + j];
/* 403 */         if (ch != this.bndbytes[j]) {
/*     */ 
/*     */           
/* 406 */           off += Math.max(j + 1 - this.bcs[ch & Byte.MAX_VALUE], this.gss[j]);
/*     */           
/*     */           continue label15;
/*     */         } 
/*     */       } 
/* 411 */       return off;
/*     */     } 
/* 413 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillBuf() {
/* 420 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "Before fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/* 421 */     assert !this.eof;
/* 422 */     while (this.len < this.buf.length) {
/*     */       int read;
/*     */       try {
/* 425 */         read = this.in.read(this.buf, this.len, this.buf.length - this.len);
/* 426 */       } catch (IOException ioe) {
/* 427 */         throw new MIMEParsingException(ioe);
/*     */       } 
/* 429 */       if (read == -1) {
/* 430 */         this.eof = true;
/*     */         try {
/* 432 */           if (LOGGER.isLoggable(Level.FINE)) LOGGER.fine("Closing the input stream."); 
/* 433 */           this.in.close();
/* 434 */         } catch (IOException ioe) {
/* 435 */           throw new MIMEParsingException(ioe);
/*     */         } 
/*     */         break;
/*     */       } 
/* 439 */       this.len += read;
/*     */     } 
/*     */     
/* 442 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "After fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private void doubleBuf() {
/* 446 */     byte[] temp = new byte[2 * this.len];
/* 447 */     System.arraycopy(this.buf, 0, temp, 0, this.len);
/* 448 */     this.buf = temp;
/* 449 */     if (!this.eof) {
/* 450 */       fillBuf();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LineInputStream
/*     */   {
/*     */     private int offset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String readLine() throws IOException {
/* 470 */       int hdrLen = 0;
/* 471 */       int lwsp = 0;
/* 472 */       while (this.offset + hdrLen < MIMEParser.this.len) {
/* 473 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 10) {
/* 474 */           lwsp = 1;
/*     */           break;
/*     */         } 
/* 477 */         if (this.offset + hdrLen + 1 == MIMEParser.this.len) {
/* 478 */           MIMEParser.this.doubleBuf();
/*     */         }
/* 480 */         if (this.offset + hdrLen + 1 >= MIMEParser.this.len) {
/* 481 */           assert MIMEParser.this.eof;
/* 482 */           return null;
/*     */         } 
/* 484 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 13 && MIMEParser.this.buf[this.offset + hdrLen + 1] == 10) {
/* 485 */           lwsp = 2;
/*     */           break;
/*     */         } 
/* 488 */         hdrLen++;
/*     */       } 
/* 490 */       if (hdrLen == 0) {
/* 491 */         MIMEParser.this.adjustBuf(this.offset + lwsp, MIMEParser.this.len - this.offset - lwsp);
/* 492 */         return null;
/*     */       } 
/*     */       
/* 495 */       String hdr = new String(MIMEParser.this.buf, this.offset, hdrLen, "ISO8859-1");
/* 496 */       this.offset += hdrLen + lwsp;
/* 497 */       return hdr;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\org\jvnet\mimepull\MIMEParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */